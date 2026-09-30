package app.apti.villa.data.local.entity.fts

import androidx.room.Entity
import androidx.room.Fts4
import app.apti.villa.data.local.entity.TestEntity

@Entity(tableName = "test_fts")
@Fts4(contentEntity = TestEntity::class, tokenizer = "unicode61")
data class TestFtsEntity(
    val title: String,
)
