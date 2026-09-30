package app.apti.villa.data.local.dao

import androidx.room.*
import app.apti.villa.data.local.entity.StudyDayEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface StudyDayDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsert(studyDay: StudyDayEntity)

    @Query("SELECT * FROM study_day WHERE date = :date LIMIT 1")
    suspend fun getStudyDay(date: String): StudyDayEntity?

    @Query("SELECT * FROM study_day ORDER BY date DESC")
    fun getAllStudyDays(): Flow<List<StudyDayEntity>>

    @Query("SELECT COUNT(*) FROM study_day WHERE questions_attempted > 0")
    suspend fun countActiveDays(): Int

    @Query("""
        INSERT INTO study_day (date, questions_attempted, correct, active_ms)
        VALUES (:date, :attempted, :correct, :activeMs)
        ON CONFLICT(date) DO UPDATE SET
            questions_attempted = questions_attempted + :attempted,
            correct = correct + :correct,
            active_ms = active_ms + :activeMs
    """)
    suspend fun recordProgress(date: String, attempted: Int, correct: Int, activeMs: Long)

    @Query("SELECT * FROM study_day ORDER BY date DESC")
    suspend fun getAllStudyDaysOnce(): List<StudyDayEntity>

    @Query("DELETE FROM study_day")
    suspend fun deleteAll()
}
