package app.apti.villa.data.local.dao

import androidx.room.*
import app.apti.villa.data.local.entity.RevisionItemEntity
import app.apti.villa.data.local.entity.RevisionReasonEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface RevisionDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsertItem(item: RevisionItemEntity)

    @Query("SELECT * FROM revision_item WHERE question_id = :questionId LIMIT 1")
    suspend fun getItemByQuestionId(questionId: Long): RevisionItemEntity?

    @Query("SELECT * FROM revision_item WHERE suspended = 0 AND due_at <= :dueBefore ORDER BY due_at ASC")
    fun getDueItems(dueBefore: Long): Flow<List<RevisionItemEntity>>

    @Query("SELECT * FROM revision_item WHERE suspended = 0 AND due_at <= :dueBefore ORDER BY due_at ASC")
    suspend fun getDueItemsOnce(dueBefore: Long): List<RevisionItemEntity>

    @Query("SELECT COUNT(*) FROM revision_item WHERE suspended = 0 AND due_at <= :dueBefore")
    fun countDueItems(dueBefore: Long): Flow<Int>

    @Query("SELECT COUNT(*) FROM revision_item WHERE box = :box AND suspended = 0")
    fun countItemsInBox(box: Int): Flow<Int>

    @Query("SELECT COUNT(*) FROM revision_item WHERE box = :box AND suspended = 0")
    suspend fun countItemsInBoxOnce(box: Int): Int

    @Query("SELECT * FROM revision_item ORDER BY due_at ASC")
    fun getAllItems(): Flow<List<RevisionItemEntity>>

    @Query("""
        UPDATE revision_item 
        SET box = :box, due_at = :dueAt, last_reviewed_at = :lastReviewedAt, review_count = review_count + 1 
        WHERE question_id = :questionId
    """)
    suspend fun updateBoxAndDue(questionId: Long, box: Int, dueAt: Long, lastReviewedAt: Long)

    @Query("UPDATE revision_item SET suspended = :suspended WHERE question_id = :questionId")
    suspend fun setSuspended(questionId: Long, suspended: Boolean)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertReason(reason: RevisionReasonEntity)

    @Query("SELECT * FROM revision_reason WHERE question_id = :questionId")
    suspend fun getReasonsForQuestion(questionId: Long): List<RevisionReasonEntity>

    @Query("DELETE FROM revision_item WHERE question_id = :questionId")
    suspend fun deleteItem(questionId: Long)

    @Query("DELETE FROM revision_item")
    suspend fun deleteAllItems()

    @Query("DELETE FROM revision_reason")
    suspend fun deleteAllReasons()
}
