package app.apti.villa.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import app.apti.villa.data.local.entity.SolutionStepEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface SolutionStepDao {
    @Query("SELECT * FROM solution_step WHERE question_id = :questionId ORDER BY number ASC")
    fun getStepsForQuestion(questionId: Long): Flow<List<SolutionStepEntity>>

    @Query("SELECT * FROM solution_step WHERE question_id = :questionId ORDER BY number ASC")
    suspend fun getStepsForQuestionOnce(questionId: Long): List<SolutionStepEntity>


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(steps: List<SolutionStepEntity>)
}
