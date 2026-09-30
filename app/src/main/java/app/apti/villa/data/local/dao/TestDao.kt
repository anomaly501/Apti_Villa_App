package app.apti.villa.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import app.apti.villa.data.local.entity.TestEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface TestDao {
    @Query("SELECT * FROM test WHERE archived = 0 ORDER BY created_at DESC")
    fun getActiveTests(): Flow<List<TestEntity>>

    @Query("SELECT * FROM test WHERE archived = 0 ORDER BY created_at DESC")
    suspend fun getActiveTestsOnce(): List<TestEntity>

    @Query("SELECT * FROM test WHERE exam_id = :examId AND archived = 0 ORDER BY created_at DESC")
    fun getTestsByExam(examId: Long): Flow<List<TestEntity>>

    @Query("SELECT * FROM test WHERE id = :id")
    suspend fun getTestById(id: Long): TestEntity?


    @Query("SELECT * FROM test WHERE uuid = :uuid")
    suspend fun getTestByUuid(uuid: String): TestEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(test: TestEntity): Long

    @Query("SELECT COUNT(*) FROM test WHERE archived = 0")
    suspend fun count(): Int
}
