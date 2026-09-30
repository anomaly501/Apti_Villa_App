package app.apti.villa.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import app.apti.villa.data.local.entity.AttemptEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface AttemptDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(attempt: AttemptEntity): Long

    @Update
    suspend fun update(attempt: AttemptEntity)

    @Query("SELECT * FROM attempt WHERE id = :id")
    suspend fun getAttemptById(id: Long): AttemptEntity?

    @Query("SELECT * FROM attempt WHERE id = :id")
    fun getAttemptByIdFlow(id: Long): Flow<AttemptEntity?>

    @Query("SELECT * FROM attempt WHERE uuid = :uuid LIMIT 1")
    suspend fun getAttemptByUuid(uuid: String): AttemptEntity?

    @Query("SELECT * FROM attempt WHERE mode = :mode AND status = 'IN_PROGRESS' ORDER BY updated_at DESC LIMIT 1")
    suspend fun getLatestInProgressAttempt(mode: String): AttemptEntity?

    @Query("SELECT * FROM attempt WHERE mode = :mode AND status = 'IN_PROGRESS' ORDER BY updated_at DESC LIMIT 1")
    fun getLatestInProgressAttemptFlow(mode: String): Flow<AttemptEntity?>

    @Query("SELECT * FROM attempt WHERE test_id = :testId ORDER BY created_at DESC")
    fun getAttemptsForTest(testId: Long): Flow<List<AttemptEntity>>

    @Query("DELETE FROM attempt WHERE id = :id")
    suspend fun deleteAttempt(id: Long)

    @Query("DELETE FROM attempt")
    suspend fun deleteAllAttempts()
}
