package app.apti.villa.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "question",
    foreignKeys = [
        ForeignKey(
            entity = SourceEntity::class,
            parentColumns = ["id"],
            childColumns = ["source_id"],
            onDelete = ForeignKey.CASCADE,
        ),
        ForeignKey(
            entity = SubjectEntity::class,
            parentColumns = ["id"],
            childColumns = ["subject_id"],
            onDelete = ForeignKey.SET_NULL,
        ),
        ForeignKey(
            entity = TopicEntity::class,
            parentColumns = ["id"],
            childColumns = ["topic_id"],
            onDelete = ForeignKey.SET_NULL,
        ),
        ForeignKey(
            entity = SubTopicEntity::class,
            parentColumns = ["id"],
            childColumns = ["sub_topic_id"],
            onDelete = ForeignKey.SET_NULL,
        ),
    ],
    indices = [
        Index(value = ["uuid"], unique = true),
        Index(value = ["source_id", "external_id"], unique = true),
        Index(value = ["content_hash"]),
        Index(value = ["subject_id"]),
        Index(value = ["topic_id"]),
        Index(value = ["sub_topic_id"]),
    ],
)
data class QuestionEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val uuid: String,
    @ColumnInfo(name = "source_id") val sourceId: Long,
    @ColumnInfo(name = "external_id") val externalId: String,
    @ColumnInfo(name = "content_hash") val contentHash: String,
    val text: String,
    val format: String = "MCQ_SINGLE",
    @ColumnInfo(name = "subject_id") val subjectId: Long? = null,
    @ColumnInfo(name = "topic_id") val topicId: Long? = null,
    @ColumnInfo(name = "sub_topic_id") val subTopicId: Long? = null,
    val difficulty: String? = null,
    @ColumnInfo(name = "source_timestamp_sec") val sourceTimestampSec: Int? = null,
    @ColumnInfo(name = "source_url") val sourceUrl: String? = null,
    val language: String? = null,
    @ColumnInfo(name = "has_solution") val hasSolution: Boolean = false,
    @ColumnInfo(name = "created_at") val createdAt: Long,
    @ColumnInfo(name = "updated_at") val updatedAt: Long,
)
