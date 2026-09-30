package app.apti.villa.data.local.entity.fts

import androidx.room.Entity
import androidx.room.Fts4
import app.apti.villa.data.local.entity.QuestionOptionEntity

@Entity(tableName = "question_option_fts")
@Fts4(contentEntity = QuestionOptionEntity::class, tokenizer = "unicode61")
data class QuestionOptionFtsEntity(
    val text: String,
)
