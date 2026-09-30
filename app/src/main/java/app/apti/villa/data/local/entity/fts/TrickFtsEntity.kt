package app.apti.villa.data.local.entity.fts

import androidx.room.Entity
import androidx.room.Fts4
import app.apti.villa.data.local.entity.TrickEntity

@Entity(tableName = "trick_fts")
@Fts4(contentEntity = TrickEntity::class, tokenizer = "unicode61")
data class TrickFtsEntity(
    val name: String,
    val description: String,
)
