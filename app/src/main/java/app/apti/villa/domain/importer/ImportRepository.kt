package app.apti.villa.domain.importer

import app.apti.villa.core.common.Outcome

data class ImportResult(
    val sourceId: Long,
    val testId: Long,
    val questionsImported: Int,
    val duplicateCount: Int = 0,
)

interface ImportRepository {
    suspend fun parseAndValidate(jsonString: String): Outcome<ImportPlan>
    suspend fun deduplicate(plan: ImportPlan): ImportPlan
    suspend fun commit(
        plan: ImportPlan,
        examId: Long?,
        subjectId: Long?,
        customTestTitle: String? = null,
        durationMinutes: Int? = null,
        negativeMarks: Double? = null,
        positiveMarks: Double? = 1.0,
    ): Outcome<ImportResult>
}
