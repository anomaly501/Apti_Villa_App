package app.apti.villa.domain.model

data class Question(
    val id: Long,
    val uuid: String,
    val sourceId: Long,
    val externalId: String,
    val text: String,
    val format: String,
    val subjectId: Long?,
    val topicId: Long?,
    val subTopicId: Long?,
    val difficulty: String?,
    val sourceTimestampSec: Int?,
    val sourceUrl: String?,
    val language: String?,
    val hasSolution: Boolean,
    val options: List<QuestionOption> = emptyList(),
    val hints: List<Hint> = emptyList(),
    val tricks: List<Trick> = emptyList(),
    val solutionSteps: List<SolutionStep> = emptyList(),
    val tags: List<Tag> = emptyList(),
    val isDifficult: Boolean = false,
    val isBookmarked: Boolean = false,
)

data class QuestionOption(
    val id: Long,
    val questionId: Long,
    val position: Int,
    val text: String,
    val isCorrect: Boolean,
)

data class Hint(
    val id: Long,
    val questionId: Long,
    val number: Int,
    val title: String,
    val description: String,
    val isUnlocked: Boolean = false,
)

data class Trick(
    val id: Long,
    val questionId: Long,
    val name: String,
    val description: String,
    val isSaved: Boolean = false,
)

data class SolutionStep(
    val id: Long,
    val questionId: Long,
    val number: Int,
    val action: String,
)
