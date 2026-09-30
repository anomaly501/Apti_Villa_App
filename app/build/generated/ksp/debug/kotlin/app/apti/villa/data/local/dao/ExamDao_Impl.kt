package app.apti.villa.`data`.local.dao

import androidx.room.EntityDeleteOrUpdateAdapter
import androidx.room.EntityInsertAdapter
import androidx.room.RoomDatabase
import androidx.room.coroutines.createFlow
import androidx.room.util.getColumnIndexOrThrow
import androidx.room.util.performSuspending
import androidx.sqlite.SQLiteStatement
import app.apti.villa.`data`.local.entity.ExamEntity
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
public class ExamDao_Impl(
  __db: RoomDatabase,
) : ExamDao {
  private val __db: RoomDatabase

  private val __insertAdapterOfExamEntity: EntityInsertAdapter<ExamEntity>

  private val __insertAdapterOfExamEntity_1: EntityInsertAdapter<ExamEntity>

  private val __deleteAdapterOfExamEntity: EntityDeleteOrUpdateAdapter<ExamEntity>
  init {
    this.__db = __db
    this.__insertAdapterOfExamEntity = object : EntityInsertAdapter<ExamEntity>() {
      protected override fun createQuery(): String =
          "INSERT OR IGNORE INTO `exam` (`id`,`uuid`,`name`,`is_builtin`,`sort_order`,`created_at`,`updated_at`) VALUES (nullif(?, 0),?,?,?,?,?,?)"

      protected override fun bind(statement: SQLiteStatement, entity: ExamEntity) {
        statement.bindLong(1, entity.id)
        statement.bindText(2, entity.uuid)
        statement.bindText(3, entity.name)
        val _tmp: Int = if (entity.isBuiltin) 1 else 0
        statement.bindLong(4, _tmp.toLong())
        statement.bindLong(5, entity.sortOrder.toLong())
        statement.bindLong(6, entity.createdAt)
        statement.bindLong(7, entity.updatedAt)
      }
    }
    this.__insertAdapterOfExamEntity_1 = object : EntityInsertAdapter<ExamEntity>() {
      protected override fun createQuery(): String =
          "INSERT OR REPLACE INTO `exam` (`id`,`uuid`,`name`,`is_builtin`,`sort_order`,`created_at`,`updated_at`) VALUES (nullif(?, 0),?,?,?,?,?,?)"

      protected override fun bind(statement: SQLiteStatement, entity: ExamEntity) {
        statement.bindLong(1, entity.id)
        statement.bindText(2, entity.uuid)
        statement.bindText(3, entity.name)
        val _tmp: Int = if (entity.isBuiltin) 1 else 0
        statement.bindLong(4, _tmp.toLong())
        statement.bindLong(5, entity.sortOrder.toLong())
        statement.bindLong(6, entity.createdAt)
        statement.bindLong(7, entity.updatedAt)
      }
    }
    this.__deleteAdapterOfExamEntity = object : EntityDeleteOrUpdateAdapter<ExamEntity>() {
      protected override fun createQuery(): String = "DELETE FROM `exam` WHERE `id` = ?"

      protected override fun bind(statement: SQLiteStatement, entity: ExamEntity) {
        statement.bindLong(1, entity.id)
      }
    }
  }

  public override suspend fun insertAll(exams: List<ExamEntity>): Unit = performSuspending(__db,
      false, true) { _connection ->
    __insertAdapterOfExamEntity.insert(_connection, exams)
  }

  public override suspend fun upsert(exam: ExamEntity): Long = performSuspending(__db, false, true)
      { _connection ->
    val _result: Long = __insertAdapterOfExamEntity_1.insertAndReturnId(_connection, exam)
    _result
  }

  public override suspend fun delete(exam: ExamEntity): Unit = performSuspending(__db, false, true)
      { _connection ->
    __deleteAdapterOfExamEntity.handle(_connection, exam)
  }

  public override fun getAllExams(): Flow<List<ExamEntity>> {
    val _sql: String = "SELECT * FROM exam ORDER BY sort_order, name"
    return createFlow(__db, false, arrayOf("exam")) { _connection ->
      val _stmt: SQLiteStatement = _connection.prepare(_sql)
      try {
        val _columnIndexOfId: Int = getColumnIndexOrThrow(_stmt, "id")
        val _columnIndexOfUuid: Int = getColumnIndexOrThrow(_stmt, "uuid")
        val _columnIndexOfName: Int = getColumnIndexOrThrow(_stmt, "name")
        val _columnIndexOfIsBuiltin: Int = getColumnIndexOrThrow(_stmt, "is_builtin")
        val _columnIndexOfSortOrder: Int = getColumnIndexOrThrow(_stmt, "sort_order")
        val _columnIndexOfCreatedAt: Int = getColumnIndexOrThrow(_stmt, "created_at")
        val _columnIndexOfUpdatedAt: Int = getColumnIndexOrThrow(_stmt, "updated_at")
        val _result: MutableList<ExamEntity> = mutableListOf()
        while (_stmt.step()) {
          val _item: ExamEntity
          val _tmpId: Long
          _tmpId = _stmt.getLong(_columnIndexOfId)
          val _tmpUuid: String
          _tmpUuid = _stmt.getText(_columnIndexOfUuid)
          val _tmpName: String
          _tmpName = _stmt.getText(_columnIndexOfName)
          val _tmpIsBuiltin: Boolean
          val _tmp: Int
          _tmp = _stmt.getLong(_columnIndexOfIsBuiltin).toInt()
          _tmpIsBuiltin = _tmp != 0
          val _tmpSortOrder: Int
          _tmpSortOrder = _stmt.getLong(_columnIndexOfSortOrder).toInt()
          val _tmpCreatedAt: Long
          _tmpCreatedAt = _stmt.getLong(_columnIndexOfCreatedAt)
          val _tmpUpdatedAt: Long
          _tmpUpdatedAt = _stmt.getLong(_columnIndexOfUpdatedAt)
          _item =
              ExamEntity(_tmpId,_tmpUuid,_tmpName,_tmpIsBuiltin,_tmpSortOrder,_tmpCreatedAt,_tmpUpdatedAt)
          _result.add(_item)
        }
        _result
      } finally {
        _stmt.close()
      }
    }
  }

  public override suspend fun getAllExamsOnce(): List<ExamEntity> {
    val _sql: String = "SELECT * FROM exam ORDER BY sort_order, name"
    return performSuspending(__db, true, false) { _connection ->
      val _stmt: SQLiteStatement = _connection.prepare(_sql)
      try {
        val _columnIndexOfId: Int = getColumnIndexOrThrow(_stmt, "id")
        val _columnIndexOfUuid: Int = getColumnIndexOrThrow(_stmt, "uuid")
        val _columnIndexOfName: Int = getColumnIndexOrThrow(_stmt, "name")
        val _columnIndexOfIsBuiltin: Int = getColumnIndexOrThrow(_stmt, "is_builtin")
        val _columnIndexOfSortOrder: Int = getColumnIndexOrThrow(_stmt, "sort_order")
        val _columnIndexOfCreatedAt: Int = getColumnIndexOrThrow(_stmt, "created_at")
        val _columnIndexOfUpdatedAt: Int = getColumnIndexOrThrow(_stmt, "updated_at")
        val _result: MutableList<ExamEntity> = mutableListOf()
        while (_stmt.step()) {
          val _item: ExamEntity
          val _tmpId: Long
          _tmpId = _stmt.getLong(_columnIndexOfId)
          val _tmpUuid: String
          _tmpUuid = _stmt.getText(_columnIndexOfUuid)
          val _tmpName: String
          _tmpName = _stmt.getText(_columnIndexOfName)
          val _tmpIsBuiltin: Boolean
          val _tmp: Int
          _tmp = _stmt.getLong(_columnIndexOfIsBuiltin).toInt()
          _tmpIsBuiltin = _tmp != 0
          val _tmpSortOrder: Int
          _tmpSortOrder = _stmt.getLong(_columnIndexOfSortOrder).toInt()
          val _tmpCreatedAt: Long
          _tmpCreatedAt = _stmt.getLong(_columnIndexOfCreatedAt)
          val _tmpUpdatedAt: Long
          _tmpUpdatedAt = _stmt.getLong(_columnIndexOfUpdatedAt)
          _item =
              ExamEntity(_tmpId,_tmpUuid,_tmpName,_tmpIsBuiltin,_tmpSortOrder,_tmpCreatedAt,_tmpUpdatedAt)
          _result.add(_item)
        }
        _result
      } finally {
        _stmt.close()
      }
    }
  }

  public override suspend fun getExamById(id: Long): ExamEntity? {
    val _sql: String = "SELECT * FROM exam WHERE id = ?"
    return performSuspending(__db, true, false) { _connection ->
      val _stmt: SQLiteStatement = _connection.prepare(_sql)
      try {
        var _argIndex: Int = 1
        _stmt.bindLong(_argIndex, id)
        val _columnIndexOfId: Int = getColumnIndexOrThrow(_stmt, "id")
        val _columnIndexOfUuid: Int = getColumnIndexOrThrow(_stmt, "uuid")
        val _columnIndexOfName: Int = getColumnIndexOrThrow(_stmt, "name")
        val _columnIndexOfIsBuiltin: Int = getColumnIndexOrThrow(_stmt, "is_builtin")
        val _columnIndexOfSortOrder: Int = getColumnIndexOrThrow(_stmt, "sort_order")
        val _columnIndexOfCreatedAt: Int = getColumnIndexOrThrow(_stmt, "created_at")
        val _columnIndexOfUpdatedAt: Int = getColumnIndexOrThrow(_stmt, "updated_at")
        val _result: ExamEntity?
        if (_stmt.step()) {
          val _tmpId: Long
          _tmpId = _stmt.getLong(_columnIndexOfId)
          val _tmpUuid: String
          _tmpUuid = _stmt.getText(_columnIndexOfUuid)
          val _tmpName: String
          _tmpName = _stmt.getText(_columnIndexOfName)
          val _tmpIsBuiltin: Boolean
          val _tmp: Int
          _tmp = _stmt.getLong(_columnIndexOfIsBuiltin).toInt()
          _tmpIsBuiltin = _tmp != 0
          val _tmpSortOrder: Int
          _tmpSortOrder = _stmt.getLong(_columnIndexOfSortOrder).toInt()
          val _tmpCreatedAt: Long
          _tmpCreatedAt = _stmt.getLong(_columnIndexOfCreatedAt)
          val _tmpUpdatedAt: Long
          _tmpUpdatedAt = _stmt.getLong(_columnIndexOfUpdatedAt)
          _result =
              ExamEntity(_tmpId,_tmpUuid,_tmpName,_tmpIsBuiltin,_tmpSortOrder,_tmpCreatedAt,_tmpUpdatedAt)
        } else {
          _result = null
        }
        _result
      } finally {
        _stmt.close()
      }
    }
  }

  public override suspend fun getExamByName(name: String): ExamEntity? {
    val _sql: String = "SELECT * FROM exam WHERE name = ? COLLATE NOCASE"
    return performSuspending(__db, true, false) { _connection ->
      val _stmt: SQLiteStatement = _connection.prepare(_sql)
      try {
        var _argIndex: Int = 1
        _stmt.bindText(_argIndex, name)
        val _columnIndexOfId: Int = getColumnIndexOrThrow(_stmt, "id")
        val _columnIndexOfUuid: Int = getColumnIndexOrThrow(_stmt, "uuid")
        val _columnIndexOfName: Int = getColumnIndexOrThrow(_stmt, "name")
        val _columnIndexOfIsBuiltin: Int = getColumnIndexOrThrow(_stmt, "is_builtin")
        val _columnIndexOfSortOrder: Int = getColumnIndexOrThrow(_stmt, "sort_order")
        val _columnIndexOfCreatedAt: Int = getColumnIndexOrThrow(_stmt, "created_at")
        val _columnIndexOfUpdatedAt: Int = getColumnIndexOrThrow(_stmt, "updated_at")
        val _result: ExamEntity?
        if (_stmt.step()) {
          val _tmpId: Long
          _tmpId = _stmt.getLong(_columnIndexOfId)
          val _tmpUuid: String
          _tmpUuid = _stmt.getText(_columnIndexOfUuid)
          val _tmpName: String
          _tmpName = _stmt.getText(_columnIndexOfName)
          val _tmpIsBuiltin: Boolean
          val _tmp: Int
          _tmp = _stmt.getLong(_columnIndexOfIsBuiltin).toInt()
          _tmpIsBuiltin = _tmp != 0
          val _tmpSortOrder: Int
          _tmpSortOrder = _stmt.getLong(_columnIndexOfSortOrder).toInt()
          val _tmpCreatedAt: Long
          _tmpCreatedAt = _stmt.getLong(_columnIndexOfCreatedAt)
          val _tmpUpdatedAt: Long
          _tmpUpdatedAt = _stmt.getLong(_columnIndexOfUpdatedAt)
          _result =
              ExamEntity(_tmpId,_tmpUuid,_tmpName,_tmpIsBuiltin,_tmpSortOrder,_tmpCreatedAt,_tmpUpdatedAt)
        } else {
          _result = null
        }
        _result
      } finally {
        _stmt.close()
      }
    }
  }

  public override suspend fun getExamByUuid(uuid: String): ExamEntity? {
    val _sql: String = "SELECT * FROM exam WHERE uuid = ?"
    return performSuspending(__db, true, false) { _connection ->
      val _stmt: SQLiteStatement = _connection.prepare(_sql)
      try {
        var _argIndex: Int = 1
        _stmt.bindText(_argIndex, uuid)
        val _columnIndexOfId: Int = getColumnIndexOrThrow(_stmt, "id")
        val _columnIndexOfUuid: Int = getColumnIndexOrThrow(_stmt, "uuid")
        val _columnIndexOfName: Int = getColumnIndexOrThrow(_stmt, "name")
        val _columnIndexOfIsBuiltin: Int = getColumnIndexOrThrow(_stmt, "is_builtin")
        val _columnIndexOfSortOrder: Int = getColumnIndexOrThrow(_stmt, "sort_order")
        val _columnIndexOfCreatedAt: Int = getColumnIndexOrThrow(_stmt, "created_at")
        val _columnIndexOfUpdatedAt: Int = getColumnIndexOrThrow(_stmt, "updated_at")
        val _result: ExamEntity?
        if (_stmt.step()) {
          val _tmpId: Long
          _tmpId = _stmt.getLong(_columnIndexOfId)
          val _tmpUuid: String
          _tmpUuid = _stmt.getText(_columnIndexOfUuid)
          val _tmpName: String
          _tmpName = _stmt.getText(_columnIndexOfName)
          val _tmpIsBuiltin: Boolean
          val _tmp: Int
          _tmp = _stmt.getLong(_columnIndexOfIsBuiltin).toInt()
          _tmpIsBuiltin = _tmp != 0
          val _tmpSortOrder: Int
          _tmpSortOrder = _stmt.getLong(_columnIndexOfSortOrder).toInt()
          val _tmpCreatedAt: Long
          _tmpCreatedAt = _stmt.getLong(_columnIndexOfCreatedAt)
          val _tmpUpdatedAt: Long
          _tmpUpdatedAt = _stmt.getLong(_columnIndexOfUpdatedAt)
          _result =
              ExamEntity(_tmpId,_tmpUuid,_tmpName,_tmpIsBuiltin,_tmpSortOrder,_tmpCreatedAt,_tmpUpdatedAt)
        } else {
          _result = null
        }
        _result
      } finally {
        _stmt.close()
      }
    }
  }

  public override suspend fun count(): Int {
    val _sql: String = "SELECT COUNT(*) FROM exam"
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
