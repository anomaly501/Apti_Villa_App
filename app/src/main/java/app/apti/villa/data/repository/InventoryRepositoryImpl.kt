package app.apti.villa.data.repository

import app.apti.villa.core.common.IoDispatcher
import app.apti.villa.data.local.dao.*
import app.apti.villa.domain.model.*
import app.apti.villa.domain.repository.InventoryRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class InventoryRepositoryImpl @Inject constructor(
    private val inventoryDao: InventoryDao,
    private val testDao: TestDao,
    private val testQuestionDao: TestQuestionDao,
    private val examDao: ExamDao,
    private val attemptDao: AttemptDao,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher,
) : InventoryRepository {

    override fun getInventoryFullData(): Flow<InventoryFullData> {
        val questionCountsFlow = combine(
            listOf(
                inventoryDao.countTotalQuestions(),
                inventoryDao.countAttemptedQuestions(),
                inventoryDao.countCorrectQuestions(),
                inventoryDao.countWrongQuestions(),
                inventoryDao.countBookmarkedQuestions(),
                inventoryDao.countDifficultQuestions(),
                inventoryDao.countRevisionQuestions(),
            )
        ) { counts ->
            val total = counts[0]
            val attempted = counts[1]
            val correct = counts[2]
            val wrong = counts[3]
            val bookmarked = counts[4]
            val difficult = counts[5]
            val revision = counts[6]

            QuestionInventoryStats(
                totalCount = total,
                attemptedCount = attempted,
                unattemptedCount = (total - attempted).coerceAtLeast(0),
                correctCount = correct,
                wrongCount = wrong,
                bookmarkedCount = bookmarked,
                difficultCount = difficult,
                needsRevisionCount = revision,
            )
        }

        val fiveCountsFlow = combine(
            listOf(
                inventoryDao.countTopicsStudied(),
                inventoryDao.countTotalTopics(),
                inventoryDao.countSavedTricks(),
                inventoryDao.countRevisionQuestions(),
                inventoryDao.countActiveMistakes(),
            )
        ) { counts -> counts }

        val learningCountsFlow = combine(
            fiveCountsFlow,
            inventoryDao.getTopMistakeTopics(),
        ) { counts, topMistakes ->
            LearningInventoryStats(
                topicsStudiedCount = counts[0],
                totalTopicsCount = counts[1],
                savedTricksCount = counts[2],
                revisionQueueCount = counts[3],
                activeMistakesCount = counts[4],
                topMistakeTopics = topMistakes.map {
                    TopicMistakeCount(
                        topicId = it.topicId,
                        topicName = it.topicName,
                        mistakeCount = it.mistakeCount,
                    )
                },
            )
        }

        val testsFlow = testDao.getActiveTests().map { tests ->
            val examsMap = examDao.getAllExamsOnce().associateBy { it.id }
            tests.map { testEntity ->
                val qCount = testQuestionDao.getQuestionCountForTest(testEntity.id)
                val exam = testEntity.examId?.let { examsMap[it] }

                TestInventoryItem(
                    testId = testEntity.id,
                    title = testEntity.title,
                    examName = exam?.name,
                    questionCount = qCount,
                    durationMinutes = testEntity.durationSec?.let { it / 60 },
                    attemptCount = 0,
                    bestScore = null,
                    maxScore = (qCount * testEntity.marksCorrect).toFloat(),
                    lastAttemptStatus = null,
                    lastAttemptAt = null,
                )
            }
        }

        return combine(
            questionCountsFlow,
            testsFlow,
            learningCountsFlow,
        ) { qStats, tests, learning ->
            InventoryFullData(
                questionStats = qStats,
                testItems = tests,
                learningStats = learning,
            )
        }.flowOn(ioDispatcher)
    }

    override fun getFilteredQuestions(
        subjectId: Long?,
        topicId: Long?,
        difficulty: String?,
        filterMode: String?,
    ): Flow<List<InventoryQuestionItem>> {
        return inventoryDao.getQuestionsWithDetails(
            subjectId = subjectId,
            topicId = topicId,
            difficulty = difficulty,
        ).map { rows ->
            val filtered = when (filterMode) {
                "ATTEMPTED" -> rows.filter { it.isAttempted }
                "UNATTEMPTED" -> rows.filter { !it.isAttempted }
                "BOOKMARKED" -> rows.filter { it.isBookmarked }
                "DIFFICULT" -> rows.filter { it.isDifficult }
                "MISTAKES" -> rows.filter { it.isMistake }
                else -> rows
            }

            filtered.map { row ->
                InventoryQuestionItem(
                    questionId = row.questionId,
                    text = row.text,
                    subjectName = row.subjectName,
                    topicName = row.topicName,
                    difficulty = row.difficulty,
                    isAttempted = row.isAttempted,
                    isCorrect = row.isCorrect,
                    isBookmarked = row.isBookmarked,
                    isDifficult = row.isDifficult,
                    isMistake = row.isMistake,
                )
            }
        }.flowOn(ioDispatcher)
    }
}
