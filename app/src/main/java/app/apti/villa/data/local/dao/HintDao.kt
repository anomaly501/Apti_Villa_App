package app.apti.villa.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import app.apti.villa.data.local.entity.HintEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface HintDao {
    @Query("SELECT * FROM hint WHERE question_id = :questionId ORDER BY number ASC")
    fun getHintsForQuestion(questionId: Long): Flow<List<HintEntity>>

    @Query("SELECT * FROM hint WHERE question_id = :questionId ORDER BY number ASC")
    suspend fun getHintsForQuestionOnce(questionId: Long): List<HintEntity>


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(hints: List<HintEntity>)
}
