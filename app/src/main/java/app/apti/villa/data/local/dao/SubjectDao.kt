package app.apti.villa.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import app.apti.villa.data.local.entity.SubjectEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface SubjectDao {
    @Query("SELECT * FROM subject ORDER BY name")
    fun getAllSubjects(): Flow<List<SubjectEntity>>

    @Query("SELECT * FROM subject ORDER BY name")
    suspend fun getAllSubjectsOnce(): List<SubjectEntity>

    @Query("SELECT * FROM subject WHERE id = :id")
    suspend fun getSubjectById(id: Long): SubjectEntity?

    @Query("SELECT * FROM subject WHERE name = :name COLLATE NOCASE")
    suspend fun getSubjectByName(name: String): SubjectEntity?

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(subject: SubjectEntity): Long
}
