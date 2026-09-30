package app.apti.villa.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "answer",
    foreignKeys = [
        ForeignKey(
            entity = AttemptEntity::class,
            parentColumns = ["id"],
            childColumns = ["attempt_id"],
            onDelete = ForeignKey.CASCADE,
        ),
        ForeignKey(
            entity = QuestionEntity::class,
            parentColumns = ["id"],
            childColumns = ["question_id"],
            onDelete = ForeignKey.CASCADE,
        ),
        ForeignKey(
            entity = QuestionOptionEntity::class,
            parentColumns = ["id"],
            childColumns = ["selected_option_id"],
            onDelete = ForeignKey.SET_NULL,
        ),
    ],
    indices = [
        Index(value = ["attempt_id", "question_id"], unique = true),
        Index(value = ["question_id"]),
        Index(value = ["is_correct"]),
        Index(value = ["attempt_id"]),
        Index(value = ["selected_option_id"]),
    ],
)
data class AnswerEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    @ColumnInfo(name = "attempt_id") val attemptId: Long,
    @ColumnInfo(name = "question_id") val questionId: Long,
    val position: Int,
    @ColumnInfo(defaultValue = "UNSEEN") val status: String = "UNSEEN", // UNSEEN, SEEN, ANSWERED, SKIPPED, MARKED, ANSWERED_MARKED
    @ColumnInfo(name = "selected_option_id") val selectedOptionId: Long? = null,
    @ColumnInfo(name = "is_correct") val isCorrect: Boolean? = null,
    @ColumnInfo(name = "time_spent_ms", defaultValue = "0") val timeSpentMs: Long = 0,
    @ColumnInfo(name = "first_seen_at") val firstSeenAt: Long? = null,
    @ColumnInfo(name = "answered_at") val answeredAt: Long? = null,
    @ColumnInfo(name = "first_submission_correct") val firstSubmissionCorrect: Boolean? = null,
    @ColumnInfo(name = "hints_opened", defaultValue = "0") val hintsOpened: Int = 0,
    @ColumnInfo(name = "solved_at_hint_level") val solvedAtHintLevel: Int? = null,
    @ColumnInfo(defaultValue = "0") val revealed: Boolean = false,
)
