package app.apti.villa.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "attempt",
    foreignKeys = [
        ForeignKey(
            entity = TestEntity::class,
            parentColumns = ["id"],
            childColumns = ["test_id"],
            onDelete = ForeignKey.SET_NULL,
        ),
    ],
    indices = [
        Index(value = ["uuid"], unique = true),
        Index(value = ["status"]),
        Index(value = ["submitted_at"]),
        Index(value = ["test_id"]),
        Index(value = ["mode"]),
    ],
)
data class AttemptEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val uuid: String,
    @ColumnInfo(name = "test_id") val testId: Long? = null,
    val mode: String, // PRACTICE, EXAM, REVIEW
    @ColumnInfo(name = "scope_desc") val scopeDesc: String? = null,
    val status: String, // IN_PROGRESS, PAUSED, SUBMITTED, ABANDONED
    @ColumnInfo(name = "started_at") val startedAt: Long,
    @ColumnInfo(name = "submitted_at") val submittedAt: Long? = null,
    @ColumnInfo(name = "time_limit_sec") val timeLimitSec: Int? = null,
    @ColumnInfo(name = "active_elapsed_ms", defaultValue = "0") val activeElapsedMs: Long = 0,
    val score: Float? = null,
    @ColumnInfo(name = "is_retry", defaultValue = "0") val isRetry: Boolean = false,
    @ColumnInfo(name = "created_at") val createdAt: Long = startedAt,
    @ColumnInfo(name = "updated_at") val updatedAt: Long = startedAt,
)

