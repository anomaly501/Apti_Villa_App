package app.apti.villa.domain.repository

import app.apti.villa.domain.model.InventoryFullData
import app.apti.villa.domain.model.InventoryQuestionItem
import kotlinx.coroutines.flow.Flow

interface InventoryRepository {
    fun getInventoryFullData(): Flow<InventoryFullData>
    fun getFilteredQuestions(
        subjectId: Long?,
        topicId: Long?,
        difficulty: String?,
        filterMode: String?, // ALL, ATTEMPTED, UNATTEMPTED, BOOKMARKED, DIFFICULT, MISTAKES
    ): Flow<List<InventoryQuestionItem>>
}
