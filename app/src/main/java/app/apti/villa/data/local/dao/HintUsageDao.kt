package app.apti.villa.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import app.apti.villa.data.local.entity.HintUsageEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface HintUsageDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(hintUsage: HintUsageEntity): Long

    @Query("SELECT * FROM hint_usage WHERE answer_id = :answerId ORDER BY opened_at ASC")
    fun getUsagesForAnswer(answerId: Long): Flow<List<HintUsageEntity>>

    @Query("SELECT * FROM hint_usage WHERE answer_id = :answerId ORDER BY opened_at ASC")
    suspend fun getUsagesForAnswerOnce(answerId: Long): List<HintUsageEntity>

    @Query("SELECT COUNT(*) > 0 FROM hint_usage WHERE answer_id = :answerId AND hint_id = :hintId")
    suspend fun isHintUsed(answerId: Long, hintId: Long): Boolean
}
