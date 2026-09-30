package app.apti.villa.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "study_day")
data class StudyDayEntity(
    @PrimaryKey
    val date: String, // YYYY-MM-DD
    @ColumnInfo(name = "questions_attempted", defaultValue = "0") val questionsAttempted: Int = 0,
    @ColumnInfo(defaultValue = "0") val correct: Int = 0,
    @ColumnInfo(name = "active_ms", defaultValue = "0") val activeMs: Long = 0,
)
