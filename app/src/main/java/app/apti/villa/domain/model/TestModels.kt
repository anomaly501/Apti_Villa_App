package app.apti.villa.domain.model

data class Test(
    val id: Long = 0,
    val uuid: String,
    val sourceId: Long? = null,
    val examId: Long? = null,
    val title: String,
    val kind: String = "FULL_MOCK", // SECTIONAL, FULL_MOCK, TOPIC_TEST
    val durationSec: Int? = null,
    val marksCorrect: Float = 1.0f,
    val negativeMarks: Float = 0.25f,
    val archived: Boolean = false,
    val createdAt: Long = System.currentTimeMillis(),
    val updatedAt: Long = System.currentTimeMillis(),
)

data class TestWithStats(
    val test: Test,
    val questionCount: Int,
    val examName: String? = null,
    val latestAttempt: Attempt? = null,
    val bestScore: Float? = null,
)

data class ExamQuestionItem(
    val question: Question,
    val answer: Answer,
)

data class TestScoreReport(
    val attemptId: Long,
    val testTitle: String,
    val totalQuestions: Int,
    val attemptedCount: Int,
    val correctCount: Int,
    val wrongCount: Int,
    val unattemptedCount: Int,
    val markedCount: Int,
    val score: Float,
    val maxScore: Float,
    val negativeMarksDeducted: Float,
    val accuracyPercentage: Float,
    val timeSpentSec: Long,
    val questionReviews: List<QuestionReviewItem> = emptyList(),
)

data class QuestionReviewItem(
    val position: Int,
    val question: Question,
    val answer: Answer,
    val selectedOption: QuestionOption?,
    val correctOption: QuestionOption?,
    val isCorrect: Boolean,
    val timeSpentSec: Long,
)
