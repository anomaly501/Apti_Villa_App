package app.apti.villa.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import app.apti.villa.data.local.entity.QuestionTagEntity
import app.apti.villa.data.local.entity.TagEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface QuestionTagDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(questionTag: QuestionTagEntity)

    @Query("DELETE FROM question_tag WHERE question_id = :questionId AND tag_id = :tagId")
    suspend fun delete(questionId: Long, tagId: Long)

    @Query("""
        SELECT t.* FROM tag t
        INNER JOIN question_tag qt ON qt.tag_id = t.id
        WHERE qt.question_id = :questionId
        ORDER BY t.is_system DESC, t.name ASC
    """)
    fun getTagsForQuestion(questionId: Long): Flow<List<TagEntity>>

    @Query("""
        SELECT t.* FROM tag t
        INNER JOIN question_tag qt ON qt.tag_id = t.id
        WHERE qt.question_id = :questionId
        ORDER BY t.is_system DESC, t.name ASC
    """)
    suspend fun getTagsForQuestionOnce(questionId: Long): List<TagEntity>

    @Query("""
        SELECT t.name FROM tag t
        INNER JOIN question_tag qt ON qt.tag_id = t.id
        WHERE qt.question_id = :questionId
    """)
    fun getTagNamesForQuestion(questionId: Long): Flow<List<String>>

    @Query("SELECT question_id FROM question_tag WHERE tag_id = :tagId")
    fun getQuestionIdsForTag(tagId: Long): Flow<List<Long>>

    @Query("SELECT COUNT(*) > 0 FROM question_tag WHERE question_id = :questionId AND tag_id = :tagId")
    suspend fun hasTag(questionId: Long, tagId: Long): Boolean
}
