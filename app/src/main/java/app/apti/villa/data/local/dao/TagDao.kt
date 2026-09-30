package app.apti.villa.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import app.apti.villa.data.local.entity.TagEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface TagDao {
    @Query("SELECT * FROM tag ORDER BY is_system DESC, name")
    fun getAllTags(): Flow<List<TagEntity>>

    @Query("SELECT * FROM tag WHERE is_system = 1 ORDER BY name")
    fun getSystemTags(): Flow<List<TagEntity>>

    @Query("SELECT * FROM tag WHERE is_system = 0 ORDER BY name")
    fun getUserTags(): Flow<List<TagEntity>>

    @Query("SELECT * FROM tag WHERE uuid = :uuid")
    suspend fun getTagByUuid(uuid: String): TagEntity?

    @Query("SELECT * FROM tag WHERE id = :id")
    suspend fun getTagById(id: Long): TagEntity?

    @Query("SELECT * FROM tag WHERE name = :name COLLATE NOCASE")
    suspend fun getTagByName(name: String): TagEntity?

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertAll(tags: List<TagEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsert(tag: TagEntity): Long

    @androidx.room.Delete
    suspend fun delete(tag: TagEntity)

    @Query("SELECT COUNT(*) FROM tag WHERE is_system = 1")
    suspend fun systemTagCount(): Int
}
