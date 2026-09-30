package app.apti.villa.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "revision_item",
    foreignKeys = [
        ForeignKey(
            entity = QuestionEntity::class,
            parentColumns = ["id"],
            childColumns = ["question_id"],
            onDelete = ForeignKey.CASCADE,
        ),
    ],
    indices = [
        Index(value = ["due_at"]),
        Index(value = ["box"]),
    ],
)
data class RevisionItemEntity(
    @PrimaryKey
    @ColumnInfo(name = "question_id") val questionId: Long,
    @ColumnInfo(defaultValue = "0") val box: Int = 0,
    @ColumnInfo(name = "due_at") val dueAt: Long,
    @ColumnInfo(name = "last_reviewed_at") val lastReviewedAt: Long? = null,
    @ColumnInfo(name = "review_count", defaultValue = "0") val reviewCount: Int = 0,
    @ColumnInfo(defaultValue = "0") val suspended: Boolean = false,
)
