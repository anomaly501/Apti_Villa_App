package app.apti.villa.domain.importer

import kotlinx.serialization.json.JsonElement

data class RawSourceMetadata(
    val title: String,
    val topic: String,
    val subTopic: String? = null,
    val subject: String? = null,
    val exam: String? = null,
    val sourceType: String = "JSON",
    val sourceUrl: String? = null,
    val channel: String? = null,
    val language: String? = null,
)

data class RawHint(
    val hintNumber: Int,
    val title: String,
    val description: String,
)

data class RawTrick(
    val trickName: String,
    val description: String,
)

data class RawSolutionStep(
    val stepNumber: Int,
    val action: String,
)

data class RawQuestion(
    val index: Int,
    val id: String,
    val questionText: String,
    val options: List<String>,
    val correctAnswer: String,
    val timestamp: String? = null,
    val category: String? = null,
    val difficulty: String? = null,
    val hintCount: Int? = null,
    val hints: List<RawHint> = emptyList(),
    val tricks: List<RawTrick> = emptyList(),
    val solutionSteps: List<RawSolutionStep> = emptyList(),
    val subject: String? = null,
    val exam: String? = null,
    val rawJsonElement: JsonElement? = null,
)

data class NormalizedOption(
    val position: Int,
    val text: String,
    val isCorrect: Boolean,
)

data class NormalizedHint(
    val number: Int,
    val title: String,
    val description: String,
)

data class NormalizedTrick(
    val name: String,
    val description: String,
)

data class NormalizedSolutionStep(
    val number: Int,
    val action: String,
)

data class NormalizedQuestion(
    val externalId: String,
    val text: String,
    val options: List<NormalizedOption>,
    val contentHash: String,
    val difficulty: String? = null, // "EASY", "MEDIUM", "HARD"
    val sourceTimestampSec: Int? = null,
    val sourceUrl: String? = null,
    val language: String? = null,
    val subTopic: String? = null,
    val subject: String? = null,
    val exam: String? = null,
    val hints: List<NormalizedHint> = emptyList(),
    val tricks: List<NormalizedTrick> = emptyList(),
    val solutionSteps: List<NormalizedSolutionStep> = emptyList(),
    val hasSolution: Boolean = false,
)

data class SkippedQuestion(
    val index: Int,
    val id: String?,
    val reason: String,
    val rawJson: String? = null,
)

data class ImportStats(
    val totalQuestionsInFile: Int,
    val validQuestionsCount: Int,
    val skippedQuestionsCount: Int,
    val errorCount: Int,
    val warningCount: Int,
)

data class ImportPlan(
    val sourceMetadata: RawSourceMetadata,
    val validQuestions: List<NormalizedQuestion>,
    val skippedQuestions: List<SkippedQuestion>,
    val issues: List<ImportIssue>,
    val stats: ImportStats,
) {
    val hasErrors: Boolean
        get() = issues.any { it.severity == IssueSeverity.ERROR }
}
