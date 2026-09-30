package app.apti.villa.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "answer_submission",
    foreignKeys = [
        ForeignKey(
            entity = AnswerEntity::class,
            parentColumns = ["id"],
            childColumns = ["answer_id"],
            onDelete = ForeignKey.CASCADE,
        ),
        ForeignKey(
            entity = QuestionOptionEntity::class,
            parentColumns = ["id"],
            childColumns = ["option_id"],
            onDelete = ForeignKey.CASCADE,
        ),
    ],
    indices = [
        Index(value = ["answer_id"]),
        Index(value = ["option_id"]),
    ],
)
data class AnswerSubmissionEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    @ColumnInfo(name = "answer_id") val answerId: Long,
    @ColumnInfo(name = "option_id") val optionId: Long,
    @ColumnInfo(name = "submitted_at") val submittedAt: Long,
    @ColumnInfo(name = "hints_open_at_submit") val hintsOpenAtSubmit: Int,
    @ColumnInfo(name = "ms_since_first_seen") val msSinceFirstSeen: Long,
    @ColumnInfo(name = "is_correct") val isCorrect: Boolean,
)
