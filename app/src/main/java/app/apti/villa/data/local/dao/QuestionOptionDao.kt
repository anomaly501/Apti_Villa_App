package app.apti.villa.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import app.apti.villa.data.local.entity.QuestionOptionEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface QuestionOptionDao {
    @Query("SELECT * FROM question_option WHERE question_id = :questionId ORDER BY position ASC")
    fun getOptionsForQuestion(questionId: Long): Flow<List<QuestionOptionEntity>>

    @Query("SELECT * FROM question_option WHERE question_id = :questionId ORDER BY position ASC")
    suspend fun getOptionsForQuestionOnce(questionId: Long): List<QuestionOptionEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(option: QuestionOptionEntity): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(options: List<QuestionOptionEntity>)
}
