package app.apti.villa.domain.model

enum class AttemptMode {
    PRACTICE, EXAM, REVIEW
}

enum class AttemptStatus {
    IN_PROGRESS, PAUSED, SUBMITTED, ABANDONED
}

enum class AnswerStatus {
    UNSEEN, SEEN, ANSWERED, SKIPPED, MARKED, ANSWERED_MARKED
}

data class Attempt(
    val id: Long = 0,
    val uuid: String,
    val testId: Long? = null,
    val mode: AttemptMode = AttemptMode.PRACTICE,
    val scopeDesc: String? = null,
    val status: AttemptStatus = AttemptStatus.IN_PROGRESS,
    val startedAt: Long,
    val submittedAt: Long? = null,
    val timeLimitSec: Int? = null,
    val activeElapsedMs: Long = 0,
    val score: Float? = null,
    val isRetry: Boolean = false,
    val createdAt: Long = startedAt,
    val updatedAt: Long = startedAt,
)

data class Answer(
    val id: Long = 0,
    val attemptId: Long,
    val questionId: Long,
    val position: Int,
    val status: AnswerStatus = AnswerStatus.UNSEEN,
    val selectedOptionId: Long? = null,
    val isCorrect: Boolean? = null,
    val timeSpentMs: Long = 0,
    val firstSeenAt: Long? = null,
    val answeredAt: Long? = null,
    val firstSubmissionCorrect: Boolean? = null,
    val hintsOpened: Int = 0,
    val solvedAtHintLevel: Int? = null,
    val revealed: Boolean = false,
)

data class AnswerSubmission(
    val id: Long = 0,
    val answerId: Long,
    val optionId: Long,
    val submittedAt: Long,
    val hintsOpenAtSubmit: Int,
    val msSinceFirstSeen: Long,
    val isCorrect: Boolean,
)

data class HintUsage(
    val id: Long = 0,
    val answerId: Long,
    val hintId: Long,
    val openedAt: Long,
    val msSinceFirstSeen: Long,
)

data class PracticeFilter(
    val topicId: Long? = null,
    val difficulty: String? = null,
    val count: Int = 10,
    val filterMode: String = "ALL", // ALL, UNATTEMPTED, MISTAKES, BOOKMARKED
)

data class PracticeSessionItem(
    val question: Question,
    val answer: Answer,
    val submissions: List<AnswerSubmission> = emptyList(),
    val hintUsages: List<HintUsage> = emptyList(),
)

data class TopicWithQuestionCount(
    val topic: Topic,
    val questionCount: Int,
)

