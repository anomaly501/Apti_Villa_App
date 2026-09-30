package app.apti.villa.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import app.apti.villa.data.local.entity.AnswerSubmissionEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface AnswerSubmissionDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(submission: AnswerSubmissionEntity): Long

    @Query("SELECT * FROM answer_submission WHERE answer_id = :answerId ORDER BY submitted_at ASC")
    fun getSubmissionsForAnswer(answerId: Long): Flow<List<AnswerSubmissionEntity>>

    @Query("SELECT * FROM answer_submission WHERE answer_id = :answerId ORDER BY submitted_at ASC")
    suspend fun getSubmissionsForAnswerOnce(answerId: Long): List<AnswerSubmissionEntity>

    @Query("SELECT COUNT(*) FROM answer_submission WHERE answer_id = :answerId")
    suspend fun countSubmissionsForAnswer(answerId: Long): Int
}
