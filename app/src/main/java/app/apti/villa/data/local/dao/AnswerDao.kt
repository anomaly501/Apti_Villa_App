package app.apti.villa.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import app.apti.villa.data.local.entity.AnswerEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface AnswerDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(answer: AnswerEntity): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(answers: List<AnswerEntity>)

    @Update
    suspend fun update(answer: AnswerEntity)

    @Query("SELECT * FROM answer WHERE id = :id")
    suspend fun getAnswerById(id: Long): AnswerEntity?

    @Query("SELECT * FROM answer WHERE attempt_id = :attemptId ORDER BY position ASC")
    fun getAnswersForAttempt(attemptId: Long): Flow<List<AnswerEntity>>

    @Query("SELECT * FROM answer WHERE attempt_id = :attemptId ORDER BY position ASC")
    suspend fun getAnswersForAttemptOnce(attemptId: Long): List<AnswerEntity>

    @Query("SELECT * FROM answer WHERE attempt_id = :attemptId AND question_id = :questionId LIMIT 1")
    suspend fun getAnswer(attemptId: Long, questionId: Long): AnswerEntity?

    @Query("SELECT * FROM answer WHERE attempt_id = :attemptId AND question_id = :questionId LIMIT 1")
    fun getAnswerFlow(attemptId: Long, questionId: Long): Flow<AnswerEntity?>

    @Query("SELECT * FROM answer WHERE attempt_id = :attemptId AND position = :position LIMIT 1")
    suspend fun getAnswerAtPosition(attemptId: Long, position: Int): AnswerEntity?

    @Query("SELECT COUNT(*) FROM answer WHERE attempt_id = :attemptId AND is_correct = 1")
    suspend fun countCorrectAnswers(attemptId: Long): Int

    @Query("SELECT COUNT(*) FROM answer WHERE attempt_id = :attemptId AND status = 'ANSWERED'")
    suspend fun countAnswered(attemptId: Long): Int
}
