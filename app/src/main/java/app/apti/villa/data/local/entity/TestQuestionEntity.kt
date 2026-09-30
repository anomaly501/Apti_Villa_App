package app.apti.villa.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index

@Entity(
    tableName = "test_question",
    primaryKeys = ["test_id", "question_id"],
    foreignKeys = [
        ForeignKey(
            entity = TestEntity::class,
            parentColumns = ["id"],
            childColumns = ["test_id"],
            onDelete = ForeignKey.CASCADE,
        ),
        ForeignKey(
            entity = QuestionEntity::class,
            parentColumns = ["id"],
            childColumns = ["question_id"],
            onDelete = ForeignKey.CASCADE,
        ),
    ],
    indices = [
        Index(value = ["test_id"]),
        Index(value = ["question_id"]),
    ],
)
data class TestQuestionEntity(
    @ColumnInfo(name = "test_id") val testId: Long,
    @ColumnInfo(name = "question_id") val questionId: Long,
    val position: Int,
)
