package app.apti.villa.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    tableName = "mistake",
    foreignKeys = [
        ForeignKey(
            entity = QuestionEntity::class,
            parentColumns = ["id"],
            childColumns = ["question_id"],
            onDelete = ForeignKey.CASCADE,
        ),
    ],
)
data class MistakeEntity(
    @PrimaryKey
    @ColumnInfo(name = "question_id") val questionId: Long,
    @ColumnInfo(name = "first_wrong_at") val firstWrongAt: Long,
    @ColumnInfo(name = "last_wrong_at") val lastWrongAt: Long,
    @ColumnInfo(name = "wrong_count", defaultValue = "1") val wrongCount: Int = 1,
    @ColumnInfo(name = "consecutive_correct", defaultValue = "0") val consecutiveCorrect: Int = 0,
    @ColumnInfo(defaultValue = "ACTIVE") val status: String = "ACTIVE", // ACTIVE, RESOLVED, REMOVED
)
