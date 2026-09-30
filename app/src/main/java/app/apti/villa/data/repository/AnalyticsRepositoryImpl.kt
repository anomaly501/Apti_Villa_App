package app.apti.villa.data.repository

import app.apti.villa.core.common.IoDispatcher
import app.apti.villa.data.local.dao.AnalyticsDao
import app.apti.villa.data.local.dao.StudyDayDao
import app.apti.villa.data.local.dao.SubjectDao
import app.apti.villa.data.local.dao.TopicDao
import app.apti.villa.domain.engine.analytics.PerformanceEngine
import app.apti.villa.domain.model.AnalyticsDashboardData
import app.apti.villa.domain.model.StudyDayStat
import app.apti.villa.domain.repository.AnalyticsRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flowOn
import java.time.LocalDate
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AnalyticsRepositoryImpl @Inject constructor(
    private val analyticsDao: AnalyticsDao,
    private val studyDayDao: StudyDayDao,
    private val topicDao: TopicDao,
    private val subjectDao: SubjectDao,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher,
) : AnalyticsRepository {

    override fun getAnalyticsDashboard(): Flow<AnalyticsDashboardData> {
        return combine(
            analyticsDao.getAnswerPerformanceRecords(),
            studyDayDao.getAllStudyDays(),
        ) { records, studyDays ->
            val metricInputs = records.map {
                PerformanceEngine.AnswerMetricInput(
                    isCorrect = it.isCorrect,
                    firstSubmissionCorrect = it.firstSubmissionCorrect,
                    timeSpentMs = it.timeSpentMs,
                    isRetry = it.isRetry,
                    hintsOpened = it.hintsOpened,
                    solvedAtHintLevel = it.solvedAtHintLevel,
                    revealed = it.revealed,
                    answeredAt = it.answeredAt,
                    topicId = it.topicId,
                    difficulty = it.difficulty,
                )
            }

            val coreMetrics = PerformanceEngine.calculateCoreMetrics(metricInputs)
            val hintDependency = PerformanceEngine.calculateHintDependency(metricInputs)
            val difficultyStats = PerformanceEngine.calculateDifficultyStats(metricInputs)

            // Topic classification
            val topicsMap = topicDao.getAllTopicsOnce().associateBy { it.id }
            val subjectsMap = subjectDao.getAllSubjectsOnce().associateBy { it.id }

            val groupedByTopic = metricInputs.filter { it.topicId != null }.groupBy { it.topicId!! }
            val classifiedTopics = groupedByTopic.map { (topicId, answers) ->
                val topicEntity = topicsMap[topicId]
                val topicName = topicEntity?.name ?: "Topic #$topicId"
                val subjectName = topicEntity?.subjectId?.let { subjectsMap[it]?.name }

                PerformanceEngine.classifyTopic(
                    topicId = topicId,
                    topicName = topicName,
                    subjectName = subjectName,
                    answers = answers,
                )
            }

            val weakTopics = classifiedTopics
                .filter { it.status == PerformanceEngine.TopicMasteryStatus.WEAK }
                .sortedWith(
                    compareByDescending<PerformanceEngine.ClassifiedTopic> { it.weakRankScore }
                        .thenByDescending { it.lastPracticedAt ?: 0L }
                )

            val strongTopics = classifiedTopics
                .filter { it.status == PerformanceEngine.TopicMasteryStatus.STRONG }
                .sortedByDescending { it.recentAccuracy }

            // Streak calculation
            val todayStr = LocalDate.now().toString()
            val yesterdayStr = LocalDate.now().minusDays(1).toString()
            val activeDates = studyDays.filter { it.questionsAttempted > 0 }.map { it.date }.toSet()
            val streak = PerformanceEngine.calculateStreak(activeDates, todayStr, yesterdayStr)

            val recentStudyStats = studyDays.take(14).map {
                StudyDayStat(
                    date = it.date,
                    questionsAttempted = it.questionsAttempted,
                    correct = it.correct,
                    activeMs = it.activeMs,
                )
            }

            AnalyticsDashboardData(
                coreMetrics = coreMetrics,
                hintDependency = hintDependency,
                difficultyStats = difficultyStats,
                weakTopics = weakTopics,
                strongTopics = strongTopics,
                allClassifiedTopics = classifiedTopics.sortedByDescending { it.attempted },
                currentStreak = streak,
                activeDaysCount = activeDates.size,
                recentStudyDays = recentStudyStats,
            )
        }.flowOn(ioDispatcher)
    }
}
