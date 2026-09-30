package app.apti.villa.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import app.apti.villa.data.local.entity.SavedTrickEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface SavedTrickDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(savedTrick: SavedTrickEntity)

    @Query("DELETE FROM saved_trick WHERE trick_id = :trickId")
    suspend fun delete(trickId: Long)

    @Query("SELECT COUNT(*) > 0 FROM saved_trick WHERE trick_id = :trickId")
    suspend fun isTrickSaved(trickId: Long): Boolean

    @Query("SELECT COUNT(*) > 0 FROM saved_trick WHERE trick_id = :trickId")
    fun isTrickSavedFlow(trickId: Long): Flow<Boolean>

    @Query("SELECT trick_id FROM saved_trick")
    fun getAllSavedTrickIds(): Flow<List<Long>>

    @Query("SELECT trick_id FROM saved_trick")
    suspend fun getAllSavedTrickIdsOnce(): List<Long>

    @Query("DELETE FROM saved_trick")
    suspend fun deleteAll()
}
