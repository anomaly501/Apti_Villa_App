package app.apti.villa.`data`.local.dao

import androidx.room.EntityInsertAdapter
import androidx.room.RoomDatabase
import androidx.room.coroutines.createFlow
import androidx.room.util.getColumnIndexOrThrow
import androidx.room.util.performSuspending
import androidx.sqlite.SQLiteStatement
import app.apti.villa.`data`.local.entity.SourceEntity
import javax.`annotation`.processing.Generated
import kotlin.Int
import kotlin.Long
import kotlin.String
import kotlin.Suppress
import kotlin.collections.List
import kotlin.collections.MutableList
import kotlin.collections.mutableListOf
import kotlin.reflect.KClass
import kotlinx.coroutines.flow.Flow

@Generated(value = ["androidx.room.RoomProcessor"])
@Suppress(names = ["UNCHECKED_CAST", "DEPRECATION", "REDUNDANT_PROJECTION", "REMOVAL"])
public class SourceDao_Impl(
  __db: RoomDatabase,
) : SourceDao {
  private val __db: RoomDatabase

  private val __insertAdapterOfSourceEntity: EntityInsertAdapter<SourceEntity>
  init {
    this.__db = __db
    this.__insertAdapterOfSourceEntity = object : EntityInsertAdapter<SourceEntity>() {
      protected override fun createQuery(): String =
          "INSERT OR REPLACE INTO `source` (`id`,`uuid`,`title`,`type`,`url`,`channel`,`schema_version`,`content_hash`,`imported_at`,`import_report_json`,`created_at`,`updated_at`) VALUES (nullif(?, 0),?,?,?,?,?,?,?,?,?,?,?)"

      protected override fun bind(statement: SQLiteStatement, entity: SourceEntity) {
        statement.bindLong(1, entity.id)
        statement.bindText(2, entity.uuid)
        statement.bindText(3, entity.title)
        statement.bindText(4, entity.type)
        val _tmpUrl: String? = entity.url
        if (_tmpUrl == null) {
          statement.bindNull(5)
        } else {
          statement.bindText(5, _tmpUrl)
        }
        val _tmpChannel: String? = entity.channel
        if (_tmpChannel == null) {
          statement.bindNull(6)
        } else {
          statement.bindText(6, _tmpChannel)
        }
        statement.bindLong(7, entity.schemaVersion.toLong())
        statement.bindText(8, entity.contentHash)
        statement.bindLong(9, entity.importedAt)
        val _tmpImportReportJson: String? = entity.importReportJson
        if (_tmpImportReportJson == null) {
          statement.bindNull(10)
        } else {
          statement.bindText(10, _tmpImportReportJson)
        }
        statement.bindLong(11, entity.createdAt)
        statement.bindLong(12, entity.updatedAt)
      }
    }
  }

  public override suspend fun insert(source: SourceEntity): Long = performSuspending(__db, false,
      true) { _connection ->
    val _result: Long = __insertAdapterOfSourceEntity.insertAndReturnId(_connection, source)
    _result
  }

  public override fun getAllSources(): Flow<List<SourceEntity>> {
    val _sql: String = "SELECT * FROM source ORDER BY imported_at DESC"
    return createFlow(__db, false, arrayOf("source")) { _connection ->
      val _stmt: SQLiteStatement = _connection.prepare(_sql)
      try {
        val _columnIndexOfId: Int = getColumnIndexOrThrow(_stmt, "id")
        val _columnIndexOfUuid: Int = getColumnIndexOrThrow(_stmt, "uuid")
        val _columnIndexOfTitle: Int = getColumnIndexOrThrow(_stmt, "title")
        val _columnIndexOfType: Int = getColumnIndexOrThrow(_stmt, "type")
        val _columnIndexOfUrl: Int = getColumnIndexOrThrow(_stmt, "url")
        val _columnIndexOfChannel: Int = getColumnIndexOrThrow(_stmt, "channel")
        val _columnIndexOfSchemaVersion: Int = getColumnIndexOrThrow(_stmt, "schema_version")
        val _columnIndexOfContentHash: Int = getColumnIndexOrThrow(_stmt, "content_hash")
        val _columnIndexOfImportedAt: Int = getColumnIndexOrThrow(_stmt, "imported_at")
        val _columnIndexOfImportReportJson: Int = getColumnIndexOrThrow(_stmt, "import_report_json")
        val _columnIndexOfCreatedAt: Int = getColumnIndexOrThrow(_stmt, "created_at")
        val _columnIndexOfUpdatedAt: Int = getColumnIndexOrThrow(_stmt, "updated_at")
        val _result: MutableList<SourceEntity> = mutableListOf()
        while (_stmt.step()) {
          val _item: SourceEntity
          val _tmpId: Long
          _tmpId = _stmt.getLong(_columnIndexOfId)
          val _tmpUuid: String
          _tmpUuid = _stmt.getText(_columnIndexOfUuid)
          val _tmpTitle: String
          _tmpTitle = _stmt.getText(_columnIndexOfTitle)
          val _tmpType: String
          _tmpType = _stmt.getText(_columnIndexOfType)
          val _tmpUrl: String?
          if (_stmt.isNull(_columnIndexOfUrl)) {
            _tmpUrl = null
          } else {
            _tmpUrl = _stmt.getText(_columnIndexOfUrl)
          }
          val _tmpChannel: String?
          if (_stmt.isNull(_columnIndexOfChannel)) {
            _tmpChannel = null
          } else {
            _tmpChannel = _stmt.getText(_columnIndexOfChannel)
          }
          val _tmpSchemaVersion: Int
          _tmpSchemaVersion = _stmt.getLong(_columnIndexOfSchemaVersion).toInt()
          val _tmpContentHash: String
          _tmpContentHash = _stmt.getText(_columnIndexOfContentHash)
          val _tmpImportedAt: Long
          _tmpImportedAt = _stmt.getLong(_columnIndexOfImportedAt)
          val _tmpImportReportJson: String?
          if (_stmt.isNull(_columnIndexOfImportReportJson)) {
            _tmpImportReportJson = null
          } else {
            _tmpImportReportJson = _stmt.getText(_columnIndexOfImportReportJson)
          }
          val _tmpCreatedAt: Long
          _tmpCreatedAt = _stmt.getLong(_columnIndexOfCreatedAt)
          val _tmpUpdatedAt: Long
          _tmpUpdatedAt = _stmt.getLong(_columnIndexOfUpdatedAt)
          _item =
              SourceEntity(_tmpId,_tmpUuid,_tmpTitle,_tmpType,_tmpUrl,_tmpChannel,_tmpSchemaVersion,_tmpContentHash,_tmpImportedAt,_tmpImportReportJson,_tmpCreatedAt,_tmpUpdatedAt)
          _result.add(_item)
        }
        _result
      } finally {
        _stmt.close()
      }
    }
  }

  public override suspend fun getSourceById(id: Long): SourceEntity? {
    val _sql: String = "SELECT * FROM source WHERE id = ?"
    return performSuspending(__db, true, false) { _connection ->
      val _stmt: SQLiteStatement = _connection.prepare(_sql)
      try {
        var _argIndex: Int = 1
        _stmt.bindLong(_argIndex, id)
        val _columnIndexOfId: Int = getColumnIndexOrThrow(_stmt, "id")
        val _columnIndexOfUuid: Int = getColumnIndexOrThrow(_stmt, "uuid")
        val _columnIndexOfTitle: Int = getColumnIndexOrThrow(_stmt, "title")
        val _columnIndexOfType: Int = getColumnIndexOrThrow(_stmt, "type")
        val _columnIndexOfUrl: Int = getColumnIndexOrThrow(_stmt, "url")
        val _columnIndexOfChannel: Int = getColumnIndexOrThrow(_stmt, "channel")
        val _columnIndexOfSchemaVersion: Int = getColumnIndexOrThrow(_stmt, "schema_version")
        val _columnIndexOfContentHash: Int = getColumnIndexOrThrow(_stmt, "content_hash")
        val _columnIndexOfImportedAt: Int = getColumnIndexOrThrow(_stmt, "imported_at")
        val _columnIndexOfImportReportJson: Int = getColumnIndexOrThrow(_stmt, "import_report_json")
        val _columnIndexOfCreatedAt: Int = getColumnIndexOrThrow(_stmt, "created_at")
        val _columnIndexOfUpdatedAt: Int = getColumnIndexOrThrow(_stmt, "updated_at")
        val _result: SourceEntity?
        if (_stmt.step()) {
          val _tmpId: Long
          _tmpId = _stmt.getLong(_columnIndexOfId)
          val _tmpUuid: String
          _tmpUuid = _stmt.getText(_columnIndexOfUuid)
          val _tmpTitle: String
          _tmpTitle = _stmt.getText(_columnIndexOfTitle)
          val _tmpType: String
          _tmpType = _stmt.getText(_columnIndexOfType)
          val _tmpUrl: String?
          if (_stmt.isNull(_columnIndexOfUrl)) {
            _tmpUrl = null
          } else {
            _tmpUrl = _stmt.getText(_columnIndexOfUrl)
          }
          val _tmpChannel: String?
          if (_stmt.isNull(_columnIndexOfChannel)) {
            _tmpChannel = null
          } else {
            _tmpChannel = _stmt.getText(_columnIndexOfChannel)
          }
          val _tmpSchemaVersion: Int
          _tmpSchemaVersion = _stmt.getLong(_columnIndexOfSchemaVersion).toInt()
          val _tmpContentHash: String
          _tmpContentHash = _stmt.getText(_columnIndexOfContentHash)
          val _tmpImportedAt: Long
          _tmpImportedAt = _stmt.getLong(_columnIndexOfImportedAt)
          val _tmpImportReportJson: String?
          if (_stmt.isNull(_columnIndexOfImportReportJson)) {
            _tmpImportReportJson = null
          } else {
            _tmpImportReportJson = _stmt.getText(_columnIndexOfImportReportJson)
          }
          val _tmpCreatedAt: Long
          _tmpCreatedAt = _stmt.getLong(_columnIndexOfCreatedAt)
          val _tmpUpdatedAt: Long
          _tmpUpdatedAt = _stmt.getLong(_columnIndexOfUpdatedAt)
          _result =
              SourceEntity(_tmpId,_tmpUuid,_tmpTitle,_tmpType,_tmpUrl,_tmpChannel,_tmpSchemaVersion,_tmpContentHash,_tmpImportedAt,_tmpImportReportJson,_tmpCreatedAt,_tmpUpdatedAt)
        } else {
          _result = null
        }
        _result
      } finally {
        _stmt.close()
      }
    }
  }

  public override suspend fun getSourceByHash(hash: String): SourceEntity? {
    val _sql: String = "SELECT * FROM source WHERE content_hash = ? LIMIT 1"
    return performSuspending(__db, true, false) { _connection ->
      val _stmt: SQLiteStatement = _connection.prepare(_sql)
      try {
        var _argIndex: Int = 1
        _stmt.bindText(_argIndex, hash)
        val _columnIndexOfId: Int = getColumnIndexOrThrow(_stmt, "id")
        val _columnIndexOfUuid: Int = getColumnIndexOrThrow(_stmt, "uuid")
        val _columnIndexOfTitle: Int = getColumnIndexOrThrow(_stmt, "title")
        val _columnIndexOfType: Int = getColumnIndexOrThrow(_stmt, "type")
        val _columnIndexOfUrl: Int = getColumnIndexOrThrow(_stmt, "url")
        val _columnIndexOfChannel: Int = getColumnIndexOrThrow(_stmt, "channel")
        val _columnIndexOfSchemaVersion: Int = getColumnIndexOrThrow(_stmt, "schema_version")
        val _columnIndexOfContentHash: Int = getColumnIndexOrThrow(_stmt, "content_hash")
        val _columnIndexOfImportedAt: Int = getColumnIndexOrThrow(_stmt, "imported_at")
        val _columnIndexOfImportReportJson: Int = getColumnIndexOrThrow(_stmt, "import_report_json")
        val _columnIndexOfCreatedAt: Int = getColumnIndexOrThrow(_stmt, "created_at")
        val _columnIndexOfUpdatedAt: Int = getColumnIndexOrThrow(_stmt, "updated_at")
        val _result: SourceEntity?
        if (_stmt.step()) {
          val _tmpId: Long
          _tmpId = _stmt.getLong(_columnIndexOfId)
          val _tmpUuid: String
          _tmpUuid = _stmt.getText(_columnIndexOfUuid)
          val _tmpTitle: String
          _tmpTitle = _stmt.getText(_columnIndexOfTitle)
          val _tmpType: String
          _tmpType = _stmt.getText(_columnIndexOfType)
          val _tmpUrl: String?
          if (_stmt.isNull(_columnIndexOfUrl)) {
            _tmpUrl = null
          } else {
            _tmpUrl = _stmt.getText(_columnIndexOfUrl)
          }
          val _tmpChannel: String?
          if (_stmt.isNull(_columnIndexOfChannel)) {
            _tmpChannel = null
          } else {
            _tmpChannel = _stmt.getText(_columnIndexOfChannel)
          }
          val _tmpSchemaVersion: Int
          _tmpSchemaVersion = _stmt.getLong(_columnIndexOfSchemaVersion).toInt()
          val _tmpContentHash: String
          _tmpContentHash = _stmt.getText(_columnIndexOfContentHash)
          val _tmpImportedAt: Long
          _tmpImportedAt = _stmt.getLong(_columnIndexOfImportedAt)
          val _tmpImportReportJson: String?
          if (_stmt.isNull(_columnIndexOfImportReportJson)) {
            _tmpImportReportJson = null
          } else {
            _tmpImportReportJson = _stmt.getText(_columnIndexOfImportReportJson)
          }
          val _tmpCreatedAt: Long
          _tmpCreatedAt = _stmt.getLong(_columnIndexOfCreatedAt)
          val _tmpUpdatedAt: Long
          _tmpUpdatedAt = _stmt.getLong(_columnIndexOfUpdatedAt)
          _result =
              SourceEntity(_tmpId,_tmpUuid,_tmpTitle,_tmpType,_tmpUrl,_tmpChannel,_tmpSchemaVersion,_tmpContentHash,_tmpImportedAt,_tmpImportReportJson,_tmpCreatedAt,_tmpUpdatedAt)
        } else {
          _result = null
        }
        _result
      } finally {
        _stmt.close()
      }
    }
  }

  public companion object {
    public fun getRequiredConverters(): List<KClass<*>> = emptyList()
  }
}
