package app.apti.villa.data.repository

import app.apti.villa.data.local.dao.TagDao
import app.apti.villa.data.local.entity.TagEntity
import app.apti.villa.domain.model.Tag
import app.apti.villa.domain.repository.TagRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TagRepositoryImpl @Inject constructor(
    private val tagDao: TagDao,
) : TagRepository {

    override fun getAllTags(): Flow<List<Tag>> {
        return tagDao.getAllTags().map { entities ->
            entities.map { it.toDomain() }
        }
    }

    override fun getSystemTags(): Flow<List<Tag>> {
        return tagDao.getSystemTags().map { entities ->
            entities.map { it.toDomain() }
        }
    }

    override fun getUserTags(): Flow<List<Tag>> {
        return tagDao.getUserTags().map { entities ->
            entities.map { it.toDomain() }
        }
    }

    override suspend fun getTagByUuid(uuid: String): Tag? {
        return tagDao.getTagByUuid(uuid)?.toDomain()
    }

    override suspend fun getTagByName(name: String): Tag? {
        return tagDao.getTagByName(name)?.toDomain()
    }

    override suspend fun upsertTag(tag: Tag): Long {
        return tagDao.upsert(tag.toEntity())
    }

    override suspend fun deleteTag(tag: Tag) {
        tagDao.delete(tag.toEntity())
    }

    private fun TagEntity.toDomain() = Tag(
        id = id,
        uuid = uuid,
        name = name,
        color = color,
        isSystem = isSystem,
        createdAt = createdAt,
        updatedAt = updatedAt,
    )

    private fun Tag.toEntity() = TagEntity(
        id = id,
        uuid = uuid,
        name = name,
        color = color,
        isSystem = isSystem,
        createdAt = createdAt,
        updatedAt = updatedAt,
    )
}

