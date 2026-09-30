package app.apti.villa.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import app.apti.villa.data.local.entity.TrickEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface TrickDao {
    @Query("SELECT * FROM trick WHERE question_id = :questionId ORDER BY id ASC")
    fun getTricksForQuestion(questionId: Long): Flow<List<TrickEntity>>

    @Query("SELECT * FROM trick WHERE question_id = :questionId ORDER BY id ASC")
    suspend fun getTricksForQuestionOnce(questionId: Long): List<TrickEntity>

    @Query("SELECT * FROM trick WHERE id = :id LIMIT 1")
    suspend fun getTrickById(id: Long): TrickEntity?

    @Query("""
        SELECT t.* FROM trick t
        INNER JOIN saved_trick st ON st.trick_id = t.id
        ORDER BY st.created_at DESC
    """)
    fun getSavedTricks(): Flow<List<TrickEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(tricks: List<TrickEntity>)

}
