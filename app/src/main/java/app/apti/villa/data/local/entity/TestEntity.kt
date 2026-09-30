package app.apti.villa.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "test",
    foreignKeys = [
        ForeignKey(
            entity = SourceEntity::class,
            parentColumns = ["id"],
            childColumns = ["source_id"],
            onDelete = ForeignKey.SET_NULL,
        ),
        ForeignKey(
            entity = ExamEntity::class,
            parentColumns = ["id"],
            childColumns = ["exam_id"],
            onDelete = ForeignKey.SET_NULL,
        ),
    ],
    indices = [
        Index(value = ["uuid"], unique = true),
        Index(value = ["source_id"]),
        Index(value = ["exam_id"]),
    ],
)
data class TestEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val uuid: String,
    @ColumnInfo(name = "source_id") val sourceId: Long? = null,
    @ColumnInfo(name = "exam_id") val examId: Long? = null,
    val title: String,
    val kind: String = "IMPORTED",
    @ColumnInfo(name = "duration_sec") val durationSec: Int? = null,
    @ColumnInfo(name = "marks_correct") val marksCorrect: Double = 1.0,
    @ColumnInfo(name = "negative_marks") val negativeMarks: Double = 0.0,
    val archived: Boolean = false,
    @ColumnInfo(name = "created_at") val createdAt: Long,
    @ColumnInfo(name = "updated_at") val updatedAt: Long,
)
