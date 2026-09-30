package app.apti.villa.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import app.apti.villa.data.local.entity.ExamEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ExamDao {
    @Query("SELECT * FROM exam ORDER BY sort_order, name")
    fun getAllExams(): Flow<List<ExamEntity>>

    @Query("SELECT * FROM exam ORDER BY sort_order, name")
    suspend fun getAllExamsOnce(): List<ExamEntity>

    @Query("SELECT * FROM exam WHERE id = :id")
    suspend fun getExamById(id: Long): ExamEntity?

    @Query("SELECT * FROM exam WHERE name = :name COLLATE NOCASE")
    suspend fun getExamByName(name: String): ExamEntity?

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertAll(exams: List<ExamEntity>)

    @Query("SELECT * FROM exam WHERE uuid = :uuid")
    suspend fun getExamByUuid(uuid: String): ExamEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsert(exam: ExamEntity): Long

    @androidx.room.Delete
    suspend fun delete(exam: ExamEntity)

    @Query("SELECT COUNT(*) FROM exam")
    suspend fun count(): Int
}
