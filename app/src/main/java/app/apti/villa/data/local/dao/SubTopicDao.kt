package app.apti.villa.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import app.apti.villa.data.local.entity.SubTopicEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface SubTopicDao {
    @Query("SELECT * FROM sub_topic WHERE topic_id = :topicId ORDER BY name")
    fun getSubTopicsByTopic(topicId: Long): Flow<List<SubTopicEntity>>

    @Query("SELECT * FROM sub_topic WHERE id = :id")
    suspend fun getSubTopicById(id: Long): SubTopicEntity?

    @Query("SELECT * FROM sub_topic WHERE topic_id = :topicId AND name = :name COLLATE NOCASE")
    suspend fun getSubTopicByTopicAndName(topicId: Long, name: String): SubTopicEntity?

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(subTopic: SubTopicEntity): Long
}
