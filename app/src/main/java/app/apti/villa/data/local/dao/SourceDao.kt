package app.apti.villa.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import app.apti.villa.data.local.entity.SourceEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface SourceDao {
    @Query("SELECT * FROM source ORDER BY imported_at DESC")
    fun getAllSources(): Flow<List<SourceEntity>>

    @Query("SELECT * FROM source WHERE id = :id")
    suspend fun getSourceById(id: Long): SourceEntity?

    @Query("SELECT * FROM source WHERE content_hash = :hash LIMIT 1")
    suspend fun getSourceByHash(hash: String): SourceEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(source: SourceEntity): Long
}
