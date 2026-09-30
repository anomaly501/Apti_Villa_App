package app.apti.villa.data.local.dao

import androidx.room.*
import app.apti.villa.data.local.entity.MistakeEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface MistakeDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsert(mistake: MistakeEntity)

    @Query("SELECT * FROM mistake WHERE question_id = :questionId LIMIT 1")
    suspend fun getMistakeByQuestionId(questionId: Long): MistakeEntity?

    @Query("SELECT * FROM mistake WHERE status = 'ACTIVE' ORDER BY last_wrong_at DESC")
    fun getActiveMistakes(): Flow<List<MistakeEntity>>

    @Query("SELECT * FROM mistake WHERE status = 'ACTIVE' ORDER BY last_wrong_at DESC")
    suspend fun getActiveMistakesOnce(): List<MistakeEntity>

    @Query("SELECT * FROM mistake WHERE status = 'RESOLVED' ORDER BY last_wrong_at DESC")
    fun getResolvedMistakes(): Flow<List<MistakeEntity>>

    @Query("SELECT COUNT(*) FROM mistake WHERE status = 'ACTIVE'")
    fun countActiveMistakes(): Flow<Int>

    @Query("SELECT COUNT(*) FROM mistake WHERE status = 'RESOLVED'")
    fun countResolvedMistakes(): Flow<Int>

    @Query("UPDATE mistake SET status = :status WHERE question_id = :questionId")
    suspend fun updateStatus(questionId: Long, status: String)

    @Query("DELETE FROM mistake WHERE question_id = :questionId")
    suspend fun delete(questionId: Long)

    @Query("DELETE FROM mistake")
    suspend fun deleteAll()
}
