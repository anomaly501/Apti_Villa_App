package app.apti.villa.domain.repository

import app.apti.villa.domain.model.SearchCategory
import app.apti.villa.domain.model.SearchResultItem
import kotlinx.coroutines.flow.Flow

interface SearchRepository {
    suspend fun search(rawQuery: String, category: SearchCategory): List<SearchResultItem>
    fun searchFlow(rawQuery: String, category: SearchCategory): Flow<List<SearchResultItem>>
}
