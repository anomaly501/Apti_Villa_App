package app.apti.villa.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user_profile")
data class UserProfileEntity(
    @PrimaryKey val id: Long = 1,
    val name: String? = null,
    @ColumnInfo(name = "preferred_difficulty") val preferredDifficulty: String? = null,
    @ColumnInfo(name = "daily_goal_questions") val dailyGoalQuestions: Int = 20,
    @ColumnInfo(name = "daily_goal_minutes") val dailyGoalMinutes: Int = 30,
    @ColumnInfo(name = "prep_start_date") val prepStartDate: String? = null,
    @ColumnInfo(name = "target_exam_date") val targetExamDate: String? = null,
    @ColumnInfo(name = "created_at") val createdAt: Long,
    @ColumnInfo(name = "updated_at") val updatedAt: Long,
)
