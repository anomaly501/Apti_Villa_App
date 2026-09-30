package app.apti.villa.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "hint_usage",
    foreignKeys = [
        ForeignKey(
            entity = AnswerEntity::class,
            parentColumns = ["id"],
            childColumns = ["answer_id"],
            onDelete = ForeignKey.CASCADE,
        ),
        ForeignKey(
            entity = HintEntity::class,
            parentColumns = ["id"],
            childColumns = ["hint_id"],
            onDelete = ForeignKey.CASCADE,
        ),
    ],
    indices = [
        Index(value = ["answer_id", "hint_id"], unique = true),
        Index(value = ["hint_id"]),
    ],
)
data class HintUsageEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    @ColumnInfo(name = "answer_id") val answerId: Long,
    @ColumnInfo(name = "hint_id") val hintId: Long,
    @ColumnInfo(name = "opened_at") val openedAt: Long,
    @ColumnInfo(name = "ms_since_first_seen") val msSinceFirstSeen: Long,
)
