package app.apti.villa.domain.model

data class QuestionInventoryStats(
    val totalCount: Int,
    val attemptedCount: Int,
    val unattemptedCount: Int,
    val correctCount: Int,
    val wrongCount: Int,
    val bookmarkedCount: Int,
    val difficultCount: Int,
    val needsRevisionCount: Int,
)

data class TestInventoryItem(
    val testId: Long,
    val title: String,
    val examName: String?,
    val questionCount: Int,
    val durationMinutes: Int?,
    val attemptCount: Int,
    val bestScore: Float?,
    val maxScore: Float?,
    val lastAttemptStatus: String?,
    val lastAttemptAt: Long?,
)

data class LearningInventoryStats(
    val topicsStudiedCount: Int,
    val totalTopicsCount: Int,
    val savedTricksCount: Int,
    val revisionQueueCount: Int,
    val activeMistakesCount: Int,
    val topMistakeTopics: List<TopicMistakeCount>,
)

data class TopicMistakeCount(
    val topicId: Long,
    val topicName: String,
    val mistakeCount: Int,
)

data class InventoryQuestionItem(
    val questionId: Long,
    val text: String,
    val subjectName: String?,
    val topicName: String?,
    val difficulty: String?,
    val isAttempted: Boolean,
    val isCorrect: Boolean?,
    val isBookmarked: Boolean,
    val isDifficult: Boolean,
    val isMistake: Boolean,
)

data class InventoryFullData(
    val questionStats: QuestionInventoryStats,
    val testItems: List<TestInventoryItem>,
    val learningStats: LearningInventoryStats,
)
