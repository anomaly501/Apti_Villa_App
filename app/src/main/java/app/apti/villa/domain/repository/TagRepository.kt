package app.apti.villa.domain.repository

import app.apti.villa.domain.model.Tag
import kotlinx.coroutines.flow.Flow

interface TagRepository {
    fun getAllTags(): Flow<List<Tag>>
    fun getSystemTags(): Flow<List<Tag>>
    fun getUserTags(): Flow<List<Tag>>
    suspend fun getTagByUuid(uuid: String): Tag?
    suspend fun getTagByName(name: String): Tag?
    suspend fun upsertTag(tag: Tag): Long
    suspend fun deleteTag(tag: Tag)
}
