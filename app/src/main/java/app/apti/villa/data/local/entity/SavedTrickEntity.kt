package app.apti.villa.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    tableName = "saved_trick",
    foreignKeys = [
        ForeignKey(
            entity = TrickEntity::class,
            parentColumns = ["id"],
            childColumns = ["trick_id"],
            onDelete = ForeignKey.CASCADE,
        ),
    ],
)
data class SavedTrickEntity(
    @PrimaryKey
    @ColumnInfo(name = "trick_id") val trickId: Long,
    @ColumnInfo(name = "created_at") val createdAt: Long,
)
