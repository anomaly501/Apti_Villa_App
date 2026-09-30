package app.apti.villa.data.local.entity.fts

import androidx.room.Entity
import androidx.room.Fts4
import app.apti.villa.data.local.entity.QuestionEntity

@Entity(tableName = "question_fts")
@Fts4(contentEntity = QuestionEntity::class, tokenizer = "unicode61")
data class QuestionFtsEntity(
    val text: String,
)
