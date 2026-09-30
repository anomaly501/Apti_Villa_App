package app.apti.villa.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import app.apti.villa.data.local.entity.TopicEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface TopicDao {
    @Query("SELECT * FROM topic ORDER BY name")
    fun getAllTopics(): Flow<List<TopicEntity>>

    @Query("SELECT * FROM topic ORDER BY name")
    suspend fun getAllTopicsOnce(): List<TopicEntity>

    @Query("SELECT * FROM topic WHERE subject_id = :subjectId ORDER BY name")
    fun getTopicsBySubject(subjectId: Long): Flow<List<TopicEntity>>

    @Query("SELECT * FROM topic WHERE subject_id IS NULL ORDER BY name")
    fun getUnassignedTopics(): Flow<List<TopicEntity>>

    @Query("SELECT * FROM topic WHERE id = :id")
    suspend fun getTopicById(id: Long): TopicEntity?

    @Query("SELECT * FROM topic WHERE subject_id = :subjectId AND name = :name COLLATE NOCASE")
    suspend fun getTopicBySubjectAndName(subjectId: Long?, name: String): TopicEntity?

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(topic: TopicEntity): Long
}
