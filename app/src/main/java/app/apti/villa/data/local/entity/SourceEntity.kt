package app.apti.villa.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "source",
    indices = [
        Index(value = ["uuid"], unique = true),
        Index(value = ["content_hash"]),
    ],
)
data class SourceEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val uuid: String,
    val title: String,
    val type: String = "JSON",
    val url: String? = null,
    val channel: String? = null,
    @ColumnInfo(name = "schema_version") val schemaVersion: Int = 1,
    @ColumnInfo(name = "content_hash") val contentHash: String,
    @ColumnInfo(name = "imported_at") val importedAt: Long,
    @ColumnInfo(name = "import_report_json") val importReportJson: String? = null,
    @ColumnInfo(name = "created_at") val createdAt: Long,
    @ColumnInfo(name = "updated_at") val updatedAt: Long,
)
