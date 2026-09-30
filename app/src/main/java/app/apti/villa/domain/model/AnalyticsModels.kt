package app.apti.villa.domain.model

import app.apti.villa.domain.engine.analytics.PerformanceEngine

data class AnalyticsDashboardData(
    val coreMetrics: PerformanceEngine.CoreMetrics,
    val hintDependency: PerformanceEngine.HintDependency,
    val difficultyStats: List<PerformanceEngine.DifficultyStat>,
    val weakTopics: List<PerformanceEngine.ClassifiedTopic>,
    val strongTopics: List<PerformanceEngine.ClassifiedTopic>,
    val allClassifiedTopics: List<PerformanceEngine.ClassifiedTopic>,
    val currentStreak: Int,
    val activeDaysCount: Int,
    val recentStudyDays: List<StudyDayStat>,
)

data class StudyDayStat(
    val date: String,
    val questionsAttempted: Int,
    val correct: Int,
    val activeMs: Long,
)
