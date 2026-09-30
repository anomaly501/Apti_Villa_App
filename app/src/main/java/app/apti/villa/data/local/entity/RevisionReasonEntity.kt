package app.apti.villa.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey

@Entity(
    tableName = "revision_reason",
    primaryKeys = ["question_id", "reason"],
    foreignKeys = [
        ForeignKey(
            entity = QuestionEntity::class,
            parentColumns = ["id"],
            childColumns = ["question_id"],
            onDelete = ForeignKey.CASCADE,
        ),
    ],
)
data class RevisionReasonEntity(
    @ColumnInfo(name = "question_id") val questionId: Long,
    val reason: String, // WRONG, DIFFICULT, MULTI_HINT, BOOKMARKED, MANUAL
    @ColumnInfo(name = "created_at") val createdAt: Long = System.currentTimeMillis(),
)
