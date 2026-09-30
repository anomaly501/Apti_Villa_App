package app.apti.villa.domain.importer

data class ImportIssue(
    val path: String,
    val code: String,
    val severity: IssueSeverity,
    val questionIndex: Int? = null,
    val questionId: String? = null,
    val message: String,
)
