package app.apti.villa.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import app.apti.villa.data.local.entity.QuestionEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface QuestionDao {
    @Query("SELECT * FROM question ORDER BY id ASC")
    fun getAllQuestions(): Flow<List<QuestionEntity>>

    @Query("SELECT * FROM question WHERE id = :id")
    suspend fun getQuestionById(id: Long): QuestionEntity?

    @Query("SELECT * FROM question WHERE uuid = :uuid")
    suspend fun getQuestionByUuid(uuid: String): QuestionEntity?

    @Query("SELECT * FROM question WHERE content_hash = :hash LIMIT 1")
    suspend fun getQuestionByHash(hash: String): QuestionEntity?

    @Query("SELECT * FROM question WHERE source_id = :sourceId AND external_id = :externalId LIMIT 1")
    suspend fun getQuestionBySourceAndExternalId(sourceId: Long, externalId: String): QuestionEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(question: QuestionEntity): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(questions: List<QuestionEntity>): List<Long>

    @Query("SELECT COUNT(*) FROM question")
    suspend fun count(): Int

    @Query("SELECT * FROM question ORDER BY id ASC")
    suspend fun getAllQuestionsOnce(): List<QuestionEntity>

    @Query("""
        SELECT * FROM question
        WHERE (:topicId IS NULL OR topic_id = :topicId)
          AND (:difficulty IS NULL OR difficulty = :difficulty COLLATE NOCASE)
        ORDER BY id ASC
        LIMIT :limit
    """)
    suspend fun getQuestionsFiltered(
        topicId: Long?,
        difficulty: String?,
        limit: Int,
    ): List<QuestionEntity>

    @Query("SELECT COUNT(*) FROM question WHERE topic_id = :topicId")
    fun getQuestionCountByTopic(topicId: Long): Flow<Int>

    @Query("SELECT COUNT(*) FROM question WHERE topic_id = :topicId")
    suspend fun getQuestionCountByTopicOnce(topicId: Long): Int
}

