package app.apti.villa.`data`.local.dao

import androidx.room.EntityDeleteOrUpdateAdapter
import androidx.room.EntityInsertAdapter
import androidx.room.RoomDatabase
import androidx.room.coroutines.createFlow
import androidx.room.util.getColumnIndexOrThrow
import androidx.room.util.performSuspending
import androidx.sqlite.SQLiteStatement
import app.apti.villa.`data`.local.entity.TagEntity
import javax.`annotation`.processing.Generated
import kotlin.Boolean
import kotlin.Int
import kotlin.Long
import kotlin.String
import kotlin.Suppress
import kotlin.Unit
import kotlin.collections.List
import kotlin.collections.MutableList
import kotlin.collections.mutableListOf
import kotlin.reflect.KClass
import kotlinx.coroutines.flow.Flow

@Generated(value = ["androidx.room.RoomProcessor"])
@Suppress(names = ["UNCHECKED_CAST", "DEPRECATION", "REDUNDANT_PROJECTION", "REMOVAL"])
public class TagDao_Impl(
  __db: RoomDatabase,
) : TagDao {
  private val __db: RoomDatabase

  private val __insertAdapterOfTagEntity: EntityInsertAdapter<TagEntity>

  private val __insertAdapterOfTagEntity_1: EntityInsertAdapter<TagEntity>

  private val __deleteAdapterOfTagEntity: EntityDeleteOrUpdateAdapter<TagEntity>
  init {
    this.__db = __db
    this.__insertAdapterOfTagEntity = object : EntityInsertAdapter<TagEntity>() {
      protected override fun createQuery(): String =
          "INSERT OR IGNORE INTO `tag` (`id`,`uuid`,`name`,`color`,`is_system`,`created_at`,`updated_at`) VALUES (nullif(?, 0),?,?,?,?,?,?)"

      protected override fun bind(statement: SQLiteStatement, entity: TagEntity) {
        statement.bindLong(1, entity.id)
        statement.bindText(2, entity.uuid)
        statement.bindText(3, entity.name)
        val _tmpColor: String? = entity.color
        if (_tmpColor == null) {
          statement.bindNull(4)
        } else {
          statement.bindText(4, _tmpColor)
        }
        val _tmp: Int = if (entity.isSystem) 1 else 0
        statement.bindLong(5, _tmp.toLong())
        statement.bindLong(6, entity.createdAt)
        statement.bindLong(7, entity.updatedAt)
      }
    }
    this.__insertAdapterOfTagEntity_1 = object : EntityInsertAdapter<TagEntity>() {
      protected override fun createQuery(): String =
          "INSERT OR REPLACE INTO `tag` (`id`,`uuid`,`name`,`color`,`is_system`,`created_at`,`updated_at`) VALUES (nullif(?, 0),?,?,?,?,?,?)"

      protected override fun bind(statement: SQLiteStatement, entity: TagEntity) {
        statement.bindLong(1, entity.id)
        statement.bindText(2, entity.uuid)
        statement.bindText(3, entity.name)
        val _tmpColor: String? = entity.color
        if (_tmpColor == null) {
          statement.bindNull(4)
        } else {
          statement.bindText(4, _tmpColor)
        }
        val _tmp: Int = if (entity.isSystem) 1 else 0
        statement.bindLong(5, _tmp.toLong())
        statement.bindLong(6, entity.createdAt)
        statement.bindLong(7, entity.updatedAt)
      }
    }
    this.__deleteAdapterOfTagEntity = object : EntityDeleteOrUpdateAdapter<TagEntity>() {
      protected override fun createQuery(): String = "DELETE FROM `tag` WHERE `id` = ?"

      protected override fun bind(statement: SQLiteStatement, entity: TagEntity) {
        statement.bindLong(1, entity.id)
      }
    }
  }

  public override suspend fun insertAll(tags: List<TagEntity>): Unit = performSuspending(__db,
      false, true) { _connection ->
    __insertAdapterOfTagEntity.insert(_connection, tags)
  }

  public override suspend fun upsert(tag: TagEntity): Long = performSuspending(__db, false, true) {
      _connection ->
    val _result: Long = __insertAdapterOfTagEntity_1.insertAndReturnId(_connection, tag)
    _result
  }

  public override suspend fun delete(tag: TagEntity): Unit = performSuspending(__db, false, true) {
      _connection ->
    __deleteAdapterOfTagEntity.handle(_connection, tag)
  }

  public override fun getAllTags(): Flow<List<TagEntity>> {
    val _sql: String = "SELECT * FROM tag ORDER BY is_system DESC, name"
    return createFlow(__db, false, arrayOf("tag")) { _connection ->
      val _stmt: SQLiteStatement = _connection.prepare(_sql)
      try {
        val _columnIndexOfId: Int = getColumnIndexOrThrow(_stmt, "id")
        val _columnIndexOfUuid: Int = getColumnIndexOrThrow(_stmt, "uuid")
        val _columnIndexOfName: Int = getColumnIndexOrThrow(_stmt, "name")
        val _columnIndexOfColor: Int = getColumnIndexOrThrow(_stmt, "color")
        val _columnIndexOfIsSystem: Int = getColumnIndexOrThrow(_stmt, "is_system")
        val _columnIndexOfCreatedAt: Int = getColumnIndexOrThrow(_stmt, "created_at")
        val _columnIndexOfUpdatedAt: Int = getColumnIndexOrThrow(_stmt, "updated_at")
        val _result: MutableList<TagEntity> = mutableListOf()
        while (_stmt.step()) {
          val _item: TagEntity
          val _tmpId: Long
          _tmpId = _stmt.getLong(_columnIndexOfId)
          val _tmpUuid: String
          _tmpUuid = _stmt.getText(_columnIndexOfUuid)
          val _tmpName: String
          _tmpName = _stmt.getText(_columnIndexOfName)
          val _tmpColor: String?
          if (_stmt.isNull(_columnIndexOfColor)) {
            _tmpColor = null
          } else {
            _tmpColor = _stmt.getText(_columnIndexOfColor)
          }
          val _tmpIsSystem: Boolean
          val _tmp: Int
          _tmp = _stmt.getLong(_columnIndexOfIsSystem).toInt()
          _tmpIsSystem = _tmp != 0
          val _tmpCreatedAt: Long
          _tmpCreatedAt = _stmt.getLong(_columnIndexOfCreatedAt)
          val _tmpUpdatedAt: Long
          _tmpUpdatedAt = _stmt.getLong(_columnIndexOfUpdatedAt)
          _item =
              TagEntity(_tmpId,_tmpUuid,_tmpName,_tmpColor,_tmpIsSystem,_tmpCreatedAt,_tmpUpdatedAt)
          _result.add(_item)
        }
        _result
      } finally {
        _stmt.close()
      }
    }
  }

  public override fun getSystemTags(): Flow<List<TagEntity>> {
    val _sql: String = "SELECT * FROM tag WHERE is_system = 1 ORDER BY name"
    return createFlow(__db, false, arrayOf("tag")) { _connection ->
      val _stmt: SQLiteStatement = _connection.prepare(_sql)
      try {
        val _columnIndexOfId: Int = getColumnIndexOrThrow(_stmt, "id")
        val _columnIndexOfUuid: Int = getColumnIndexOrThrow(_stmt, "uuid")
        val _columnIndexOfName: Int = getColumnIndexOrThrow(_stmt, "name")
        val _columnIndexOfColor: Int = getColumnIndexOrThrow(_stmt, "color")
        val _columnIndexOfIsSystem: Int = getColumnIndexOrThrow(_stmt, "is_system")
        val _columnIndexOfCreatedAt: Int = getColumnIndexOrThrow(_stmt, "created_at")
        val _columnIndexOfUpdatedAt: Int = getColumnIndexOrThrow(_stmt, "updated_at")
        val _result: MutableList<TagEntity> = mutableListOf()
        while (_stmt.step()) {
          val _item: TagEntity
          val _tmpId: Long
          _tmpId = _stmt.getLong(_columnIndexOfId)
          val _tmpUuid: String
          _tmpUuid = _stmt.getText(_columnIndexOfUuid)
          val _tmpName: String
          _tmpName = _stmt.getText(_columnIndexOfName)
          val _tmpColor: String?
          if (_stmt.isNull(_columnIndexOfColor)) {
            _tmpColor = null
          } else {
            _tmpColor = _stmt.getText(_columnIndexOfColor)
          }
          val _tmpIsSystem: Boolean
          val _tmp: Int
          _tmp = _stmt.getLong(_columnIndexOfIsSystem).toInt()
          _tmpIsSystem = _tmp != 0
          val _tmpCreatedAt: Long
          _tmpCreatedAt = _stmt.getLong(_columnIndexOfCreatedAt)
          val _tmpUpdatedAt: Long
          _tmpUpdatedAt = _stmt.getLong(_columnIndexOfUpdatedAt)
          _item =
              TagEntity(_tmpId,_tmpUuid,_tmpName,_tmpColor,_tmpIsSystem,_tmpCreatedAt,_tmpUpdatedAt)
          _result.add(_item)
        }
        _result
      } finally {
        _stmt.close()
      }
    }
  }

  public override fun getUserTags(): Flow<List<TagEntity>> {
    val _sql: String = "SELECT * FROM tag WHERE is_system = 0 ORDER BY name"
    return createFlow(__db, false, arrayOf("tag")) { _connection ->
      val _stmt: SQLiteStatement = _connection.prepare(_sql)
      try {
        val _columnIndexOfId: Int = getColumnIndexOrThrow(_stmt, "id")
        val _columnIndexOfUuid: Int = getColumnIndexOrThrow(_stmt, "uuid")
        val _columnIndexOfName: Int = getColumnIndexOrThrow(_stmt, "name")
        val _columnIndexOfColor: Int = getColumnIndexOrThrow(_stmt, "color")
        val _columnIndexOfIsSystem: Int = getColumnIndexOrThrow(_stmt, "is_system")
        val _columnIndexOfCreatedAt: Int = getColumnIndexOrThrow(_stmt, "created_at")
        val _columnIndexOfUpdatedAt: Int = getColumnIndexOrThrow(_stmt, "updated_at")
        val _result: MutableList<TagEntity> = mutableListOf()
        while (_stmt.step()) {
          val _item: TagEntity
          val _tmpId: Long
          _tmpId = _stmt.getLong(_columnIndexOfId)
          val _tmpUuid: String
          _tmpUuid = _stmt.getText(_columnIndexOfUuid)
          val _tmpName: String
          _tmpName = _stmt.getText(_columnIndexOfName)
          val _tmpColor: String?
          if (_stmt.isNull(_columnIndexOfColor)) {
            _tmpColor = null
          } else {
            _tmpColor = _stmt.getText(_columnIndexOfColor)
          }
          val _tmpIsSystem: Boolean
          val _tmp: Int
          _tmp = _stmt.getLong(_columnIndexOfIsSystem).toInt()
          _tmpIsSystem = _tmp != 0
          val _tmpCreatedAt: Long
          _tmpCreatedAt = _stmt.getLong(_columnIndexOfCreatedAt)
          val _tmpUpdatedAt: Long
          _tmpUpdatedAt = _stmt.getLong(_columnIndexOfUpdatedAt)
          _item =
              TagEntity(_tmpId,_tmpUuid,_tmpName,_tmpColor,_tmpIsSystem,_tmpCreatedAt,_tmpUpdatedAt)
          _result.add(_item)
        }
        _result
      } finally {
        _stmt.close()
      }
    }
  }

  public override suspend fun getTagByUuid(uuid: String): TagEntity? {
    val _sql: String = "SELECT * FROM tag WHERE uuid = ?"
    return performSuspending(__db, true, false) { _connection ->
      val _stmt: SQLiteStatement = _connection.prepare(_sql)
      try {
        var _argIndex: Int = 1
        _stmt.bindText(_argIndex, uuid)
        val _columnIndexOfId: Int = getColumnIndexOrThrow(_stmt, "id")
        val _columnIndexOfUuid: Int = getColumnIndexOrThrow(_stmt, "uuid")
        val _columnIndexOfName: Int = getColumnIndexOrThrow(_stmt, "name")
        val _columnIndexOfColor: Int = getColumnIndexOrThrow(_stmt, "color")
        val _columnIndexOfIsSystem: Int = getColumnIndexOrThrow(_stmt, "is_system")
        val _columnIndexOfCreatedAt: Int = getColumnIndexOrThrow(_stmt, "created_at")
        val _columnIndexOfUpdatedAt: Int = getColumnIndexOrThrow(_stmt, "updated_at")
        val _result: TagEntity?
        if (_stmt.step()) {
          val _tmpId: Long
          _tmpId = _stmt.getLong(_columnIndexOfId)
          val _tmpUuid: String
          _tmpUuid = _stmt.getText(_columnIndexOfUuid)
          val _tmpName: String
          _tmpName = _stmt.getText(_columnIndexOfName)
          val _tmpColor: String?
          if (_stmt.isNull(_columnIndexOfColor)) {
            _tmpColor = null
          } else {
            _tmpColor = _stmt.getText(_columnIndexOfColor)
          }
          val _tmpIsSystem: Boolean
          val _tmp: Int
          _tmp = _stmt.getLong(_columnIndexOfIsSystem).toInt()
          _tmpIsSystem = _tmp != 0
          val _tmpCreatedAt: Long
          _tmpCreatedAt = _stmt.getLong(_columnIndexOfCreatedAt)
          val _tmpUpdatedAt: Long
          _tmpUpdatedAt = _stmt.getLong(_columnIndexOfUpdatedAt)
          _result =
              TagEntity(_tmpId,_tmpUuid,_tmpName,_tmpColor,_tmpIsSystem,_tmpCreatedAt,_tmpUpdatedAt)
        } else {
          _result = null
        }
        _result
      } finally {
        _stmt.close()
      }
    }
  }

  public override suspend fun getTagById(id: Long): TagEntity? {
    val _sql: String = "SELECT * FROM tag WHERE id = ?"
    return performSuspending(__db, true, false) { _connection ->
      val _stmt: SQLiteStatement = _connection.prepare(_sql)
      try {
        var _argIndex: Int = 1
        _stmt.bindLong(_argIndex, id)
        val _columnIndexOfId: Int = getColumnIndexOrThrow(_stmt, "id")
        val _columnIndexOfUuid: Int = getColumnIndexOrThrow(_stmt, "uuid")
        val _columnIndexOfName: Int = getColumnIndexOrThrow(_stmt, "name")
        val _columnIndexOfColor: Int = getColumnIndexOrThrow(_stmt, "color")
        val _columnIndexOfIsSystem: Int = getColumnIndexOrThrow(_stmt, "is_system")
        val _columnIndexOfCreatedAt: Int = getColumnIndexOrThrow(_stmt, "created_at")
        val _columnIndexOfUpdatedAt: Int = getColumnIndexOrThrow(_stmt, "updated_at")
        val _result: TagEntity?
        if (_stmt.step()) {
          val _tmpId: Long
          _tmpId = _stmt.getLong(_columnIndexOfId)
          val _tmpUuid: String
          _tmpUuid = _stmt.getText(_columnIndexOfUuid)
          val _tmpName: String
          _tmpName = _stmt.getText(_columnIndexOfName)
          val _tmpColor: String?
          if (_stmt.isNull(_columnIndexOfColor)) {
            _tmpColor = null
          } else {
            _tmpColor = _stmt.getText(_columnIndexOfColor)
          }
          val _tmpIsSystem: Boolean
          val _tmp: Int
          _tmp = _stmt.getLong(_columnIndexOfIsSystem).toInt()
          _tmpIsSystem = _tmp != 0
          val _tmpCreatedAt: Long
          _tmpCreatedAt = _stmt.getLong(_columnIndexOfCreatedAt)
          val _tmpUpdatedAt: Long
          _tmpUpdatedAt = _stmt.getLong(_columnIndexOfUpdatedAt)
          _result =
              TagEntity(_tmpId,_tmpUuid,_tmpName,_tmpColor,_tmpIsSystem,_tmpCreatedAt,_tmpUpdatedAt)
        } else {
          _result = null
        }
        _result
      } finally {
        _stmt.close()
      }
    }
  }

  public override suspend fun getTagByName(name: String): TagEntity? {
    val _sql: String = "SELECT * FROM tag WHERE name = ? COLLATE NOCASE"
    return performSuspending(__db, true, false) { _connection ->
      val _stmt: SQLiteStatement = _connection.prepare(_sql)
      try {
        var _argIndex: Int = 1
        _stmt.bindText(_argIndex, name)
        val _columnIndexOfId: Int = getColumnIndexOrThrow(_stmt, "id")
        val _columnIndexOfUuid: Int = getColumnIndexOrThrow(_stmt, "uuid")
        val _columnIndexOfName: Int = getColumnIndexOrThrow(_stmt, "name")
        val _columnIndexOfColor: Int = getColumnIndexOrThrow(_stmt, "color")
        val _columnIndexOfIsSystem: Int = getColumnIndexOrThrow(_stmt, "is_system")
        val _columnIndexOfCreatedAt: Int = getColumnIndexOrThrow(_stmt, "created_at")
        val _columnIndexOfUpdatedAt: Int = getColumnIndexOrThrow(_stmt, "updated_at")
        val _result: TagEntity?
        if (_stmt.step()) {
          val _tmpId: Long
          _tmpId = _stmt.getLong(_columnIndexOfId)
          val _tmpUuid: String
          _tmpUuid = _stmt.getText(_columnIndexOfUuid)
          val _tmpName: String
          _tmpName = _stmt.getText(_columnIndexOfName)
          val _tmpColor: String?
          if (_stmt.isNull(_columnIndexOfColor)) {
            _tmpColor = null
          } else {
            _tmpColor = _stmt.getText(_columnIndexOfColor)
          }
          val _tmpIsSystem: Boolean
          val _tmp: Int
          _tmp = _stmt.getLong(_columnIndexOfIsSystem).toInt()
          _tmpIsSystem = _tmp != 0
          val _tmpCreatedAt: Long
          _tmpCreatedAt = _stmt.getLong(_columnIndexOfCreatedAt)
          val _tmpUpdatedAt: Long
          _tmpUpdatedAt = _stmt.getLong(_columnIndexOfUpdatedAt)
          _result =
              TagEntity(_tmpId,_tmpUuid,_tmpName,_tmpColor,_tmpIsSystem,_tmpCreatedAt,_tmpUpdatedAt)
        } else {
          _result = null
        }
        _result
      } finally {
        _stmt.close()
      }
    }
  }

  public override suspend fun systemTagCount(): Int {
    val _sql: String = "SELECT COUNT(*) FROM tag WHERE is_system = 1"
    return performSuspending(__db, true, false) { _connection ->
      val _stmt: SQLiteStatement = _connection.prepare(_sql)
      try {
        val _result: Int
        if (_stmt.step()) {
          val _tmp: Int
          _tmp = _stmt.getLong(0).toInt()
          _result = _tmp
        } else {
          _result = 0
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
