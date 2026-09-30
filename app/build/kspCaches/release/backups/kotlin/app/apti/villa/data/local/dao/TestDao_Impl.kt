package app.apti.villa.`data`.local.dao

import androidx.room.EntityInsertAdapter
import androidx.room.RoomDatabase
import androidx.room.coroutines.createFlow
import androidx.room.util.getColumnIndexOrThrow
import androidx.room.util.performSuspending
import androidx.sqlite.SQLiteStatement
import app.apti.villa.`data`.local.entity.TestEntity
import javax.`annotation`.processing.Generated
import kotlin.Boolean
import kotlin.Double
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
public class TestDao_Impl(
  __db: RoomDatabase,
) : TestDao {
  private val __db: RoomDatabase

  private val __insertAdapterOfTestEntity: EntityInsertAdapter<TestEntity>
  init {
    this.__db = __db
    this.__insertAdapterOfTestEntity = object : EntityInsertAdapter<TestEntity>() {
      protected override fun createQuery(): String =
          "INSERT OR REPLACE INTO `test` (`id`,`uuid`,`source_id`,`exam_id`,`title`,`kind`,`duration_sec`,`marks_correct`,`negative_marks`,`archived`,`created_at`,`updated_at`) VALUES (nullif(?, 0),?,?,?,?,?,?,?,?,?,?,?)"

      protected override fun bind(statement: SQLiteStatement, entity: TestEntity) {
        statement.bindLong(1, entity.id)
        statement.bindText(2, entity.uuid)
        val _tmpSourceId: Long? = entity.sourceId
        if (_tmpSourceId == null) {
          statement.bindNull(3)
        } else {
          statement.bindLong(3, _tmpSourceId)
        }
        val _tmpExamId: Long? = entity.examId
        if (_tmpExamId == null) {
          statement.bindNull(4)
        } else {
          statement.bindLong(4, _tmpExamId)
        }
        statement.bindText(5, entity.title)
        statement.bindText(6, entity.kind)
        val _tmpDurationSec: Int? = entity.durationSec
        if (_tmpDurationSec == null) {
          statement.bindNull(7)
        } else {
          statement.bindLong(7, _tmpDurationSec.toLong())
        }
        statement.bindDouble(8, entity.marksCorrect)
        statement.bindDouble(9, entity.negativeMarks)
        val _tmp: Int = if (entity.archived) 1 else 0
        statement.bindLong(10, _tmp.toLong())
        statement.bindLong(11, entity.createdAt)
        statement.bindLong(12, entity.updatedAt)
      }
    }
  }

  public override suspend fun insert(test: TestEntity): Long = performSuspending(__db, false, true)
      { _connection ->
    val _result: Long = __insertAdapterOfTestEntity.insertAndReturnId(_connection, test)
    _result
  }

  public override fun getActiveTests(): Flow<List<TestEntity>> {
    val _sql: String = "SELECT * FROM test WHERE archived = 0 ORDER BY created_at DESC"
    return createFlow(__db, false, arrayOf("test")) { _connection ->
      val _stmt: SQLiteStatement = _connection.prepare(_sql)
      try {
        val _columnIndexOfId: Int = getColumnIndexOrThrow(_stmt, "id")
        val _columnIndexOfUuid: Int = getColumnIndexOrThrow(_stmt, "uuid")
        val _columnIndexOfSourceId: Int = getColumnIndexOrThrow(_stmt, "source_id")
        val _columnIndexOfExamId: Int = getColumnIndexOrThrow(_stmt, "exam_id")
        val _columnIndexOfTitle: Int = getColumnIndexOrThrow(_stmt, "title")
        val _columnIndexOfKind: Int = getColumnIndexOrThrow(_stmt, "kind")
        val _columnIndexOfDurationSec: Int = getColumnIndexOrThrow(_stmt, "duration_sec")
        val _columnIndexOfMarksCorrect: Int = getColumnIndexOrThrow(_stmt, "marks_correct")
        val _columnIndexOfNegativeMarks: Int = getColumnIndexOrThrow(_stmt, "negative_marks")
        val _columnIndexOfArchived: Int = getColumnIndexOrThrow(_stmt, "archived")
        val _columnIndexOfCreatedAt: Int = getColumnIndexOrThrow(_stmt, "created_at")
        val _columnIndexOfUpdatedAt: Int = getColumnIndexOrThrow(_stmt, "updated_at")
        val _result: MutableList<TestEntity> = mutableListOf()
        while (_stmt.step()) {
          val _item: TestEntity
          val _tmpId: Long
          _tmpId = _stmt.getLong(_columnIndexOfId)
          val _tmpUuid: String
          _tmpUuid = _stmt.getText(_columnIndexOfUuid)
          val _tmpSourceId: Long?
          if (_stmt.isNull(_columnIndexOfSourceId)) {
            _tmpSourceId = null
          } else {
            _tmpSourceId = _stmt.getLong(_columnIndexOfSourceId)
          }
          val _tmpExamId: Long?
          if (_stmt.isNull(_columnIndexOfExamId)) {
            _tmpExamId = null
          } else {
            _tmpExamId = _stmt.getLong(_columnIndexOfExamId)
          }
          val _tmpTitle: String
          _tmpTitle = _stmt.getText(_columnIndexOfTitle)
          val _tmpKind: String
          _tmpKind = _stmt.getText(_columnIndexOfKind)
          val _tmpDurationSec: Int?
          if (_stmt.isNull(_columnIndexOfDurationSec)) {
            _tmpDurationSec = null
          } else {
            _tmpDurationSec = _stmt.getLong(_columnIndexOfDurationSec).toInt()
          }
          val _tmpMarksCorrect: Double
          _tmpMarksCorrect = _stmt.getDouble(_columnIndexOfMarksCorrect)
          val _tmpNegativeMarks: Double
          _tmpNegativeMarks = _stmt.getDouble(_columnIndexOfNegativeMarks)
          val _tmpArchived: Boolean
          val _tmp: Int
          _tmp = _stmt.getLong(_columnIndexOfArchived).toInt()
          _tmpArchived = _tmp != 0
          val _tmpCreatedAt: Long
          _tmpCreatedAt = _stmt.getLong(_columnIndexOfCreatedAt)
          val _tmpUpdatedAt: Long
          _tmpUpdatedAt = _stmt.getLong(_columnIndexOfUpdatedAt)
          _item =
              TestEntity(_tmpId,_tmpUuid,_tmpSourceId,_tmpExamId,_tmpTitle,_tmpKind,_tmpDurationSec,_tmpMarksCorrect,_tmpNegativeMarks,_tmpArchived,_tmpCreatedAt,_tmpUpdatedAt)
          _result.add(_item)
        }
        _result
      } finally {
        _stmt.close()
      }
    }
  }

  public override suspend fun getActiveTestsOnce(): List<TestEntity> {
    val _sql: String = "SELECT * FROM test WHERE archived = 0 ORDER BY created_at DESC"
    return performSuspending(__db, true, false) { _connection ->
      val _stmt: SQLiteStatement = _connection.prepare(_sql)
      try {
        val _columnIndexOfId: Int = getColumnIndexOrThrow(_stmt, "id")
        val _columnIndexOfUuid: Int = getColumnIndexOrThrow(_stmt, "uuid")
        val _columnIndexOfSourceId: Int = getColumnIndexOrThrow(_stmt, "source_id")
        val _columnIndexOfExamId: Int = getColumnIndexOrThrow(_stmt, "exam_id")
        val _columnIndexOfTitle: Int = getColumnIndexOrThrow(_stmt, "title")
        val _columnIndexOfKind: Int = getColumnIndexOrThrow(_stmt, "kind")
        val _columnIndexOfDurationSec: Int = getColumnIndexOrThrow(_stmt, "duration_sec")
        val _columnIndexOfMarksCorrect: Int = getColumnIndexOrThrow(_stmt, "marks_correct")
        val _columnIndexOfNegativeMarks: Int = getColumnIndexOrThrow(_stmt, "negative_marks")
        val _columnIndexOfArchived: Int = getColumnIndexOrThrow(_stmt, "archived")
        val _columnIndexOfCreatedAt: Int = getColumnIndexOrThrow(_stmt, "created_at")
        val _columnIndexOfUpdatedAt: Int = getColumnIndexOrThrow(_stmt, "updated_at")
        val _result: MutableList<TestEntity> = mutableListOf()
        while (_stmt.step()) {
          val _item: TestEntity
          val _tmpId: Long
          _tmpId = _stmt.getLong(_columnIndexOfId)
          val _tmpUuid: String
          _tmpUuid = _stmt.getText(_columnIndexOfUuid)
          val _tmpSourceId: Long?
          if (_stmt.isNull(_columnIndexOfSourceId)) {
            _tmpSourceId = null
          } else {
            _tmpSourceId = _stmt.getLong(_columnIndexOfSourceId)
          }
          val _tmpExamId: Long?
          if (_stmt.isNull(_columnIndexOfExamId)) {
            _tmpExamId = null
          } else {
            _tmpExamId = _stmt.getLong(_columnIndexOfExamId)
          }
          val _tmpTitle: String
          _tmpTitle = _stmt.getText(_columnIndexOfTitle)
          val _tmpKind: String
          _tmpKind = _stmt.getText(_columnIndexOfKind)
          val _tmpDurationSec: Int?
          if (_stmt.isNull(_columnIndexOfDurationSec)) {
            _tmpDurationSec = null
          } else {
            _tmpDurationSec = _stmt.getLong(_columnIndexOfDurationSec).toInt()
          }
          val _tmpMarksCorrect: Double
          _tmpMarksCorrect = _stmt.getDouble(_columnIndexOfMarksCorrect)
          val _tmpNegativeMarks: Double
          _tmpNegativeMarks = _stmt.getDouble(_columnIndexOfNegativeMarks)
          val _tmpArchived: Boolean
          val _tmp: Int
          _tmp = _stmt.getLong(_columnIndexOfArchived).toInt()
          _tmpArchived = _tmp != 0
          val _tmpCreatedAt: Long
          _tmpCreatedAt = _stmt.getLong(_columnIndexOfCreatedAt)
          val _tmpUpdatedAt: Long
          _tmpUpdatedAt = _stmt.getLong(_columnIndexOfUpdatedAt)
          _item =
              TestEntity(_tmpId,_tmpUuid,_tmpSourceId,_tmpExamId,_tmpTitle,_tmpKind,_tmpDurationSec,_tmpMarksCorrect,_tmpNegativeMarks,_tmpArchived,_tmpCreatedAt,_tmpUpdatedAt)
          _result.add(_item)
        }
        _result
      } finally {
        _stmt.close()
      }
    }
  }

  public override fun getTestsByExam(examId: Long): Flow<List<TestEntity>> {
    val _sql: String =
        "SELECT * FROM test WHERE exam_id = ? AND archived = 0 ORDER BY created_at DESC"
    return createFlow(__db, false, arrayOf("test")) { _connection ->
      val _stmt: SQLiteStatement = _connection.prepare(_sql)
      try {
        var _argIndex: Int = 1
        _stmt.bindLong(_argIndex, examId)
        val _columnIndexOfId: Int = getColumnIndexOrThrow(_stmt, "id")
        val _columnIndexOfUuid: Int = getColumnIndexOrThrow(_stmt, "uuid")
        val _columnIndexOfSourceId: Int = getColumnIndexOrThrow(_stmt, "source_id")
        val _columnIndexOfExamId: Int = getColumnIndexOrThrow(_stmt, "exam_id")
        val _columnIndexOfTitle: Int = getColumnIndexOrThrow(_stmt, "title")
        val _columnIndexOfKind: Int = getColumnIndexOrThrow(_stmt, "kind")
        val _columnIndexOfDurationSec: Int = getColumnIndexOrThrow(_stmt, "duration_sec")
        val _columnIndexOfMarksCorrect: Int = getColumnIndexOrThrow(_stmt, "marks_correct")
        val _columnIndexOfNegativeMarks: Int = getColumnIndexOrThrow(_stmt, "negative_marks")
        val _columnIndexOfArchived: Int = getColumnIndexOrThrow(_stmt, "archived")
        val _columnIndexOfCreatedAt: Int = getColumnIndexOrThrow(_stmt, "created_at")
        val _columnIndexOfUpdatedAt: Int = getColumnIndexOrThrow(_stmt, "updated_at")
        val _result: MutableList<TestEntity> = mutableListOf()
        while (_stmt.step()) {
          val _item: TestEntity
          val _tmpId: Long
          _tmpId = _stmt.getLong(_columnIndexOfId)
          val _tmpUuid: String
          _tmpUuid = _stmt.getText(_columnIndexOfUuid)
          val _tmpSourceId: Long?
          if (_stmt.isNull(_columnIndexOfSourceId)) {
            _tmpSourceId = null
          } else {
            _tmpSourceId = _stmt.getLong(_columnIndexOfSourceId)
          }
          val _tmpExamId: Long?
          if (_stmt.isNull(_columnIndexOfExamId)) {
            _tmpExamId = null
          } else {
            _tmpExamId = _stmt.getLong(_columnIndexOfExamId)
          }
          val _tmpTitle: String
          _tmpTitle = _stmt.getText(_columnIndexOfTitle)
          val _tmpKind: String
          _tmpKind = _stmt.getText(_columnIndexOfKind)
          val _tmpDurationSec: Int?
          if (_stmt.isNull(_columnIndexOfDurationSec)) {
            _tmpDurationSec = null
          } else {
            _tmpDurationSec = _stmt.getLong(_columnIndexOfDurationSec).toInt()
          }
          val _tmpMarksCorrect: Double
          _tmpMarksCorrect = _stmt.getDouble(_columnIndexOfMarksCorrect)
          val _tmpNegativeMarks: Double
          _tmpNegativeMarks = _stmt.getDouble(_columnIndexOfNegativeMarks)
          val _tmpArchived: Boolean
          val _tmp: Int
          _tmp = _stmt.getLong(_columnIndexOfArchived).toInt()
          _tmpArchived = _tmp != 0
          val _tmpCreatedAt: Long
          _tmpCreatedAt = _stmt.getLong(_columnIndexOfCreatedAt)
          val _tmpUpdatedAt: Long
          _tmpUpdatedAt = _stmt.getLong(_columnIndexOfUpdatedAt)
          _item =
              TestEntity(_tmpId,_tmpUuid,_tmpSourceId,_tmpExamId,_tmpTitle,_tmpKind,_tmpDurationSec,_tmpMarksCorrect,_tmpNegativeMarks,_tmpArchived,_tmpCreatedAt,_tmpUpdatedAt)
          _result.add(_item)
        }
        _result
      } finally {
        _stmt.close()
      }
    }
  }

  public override suspend fun getTestById(id: Long): TestEntity? {
    val _sql: String = "SELECT * FROM test WHERE id = ?"
    return performSuspending(__db, true, false) { _connection ->
      val _stmt: SQLiteStatement = _connection.prepare(_sql)
      try {
        var _argIndex: Int = 1
        _stmt.bindLong(_argIndex, id)
        val _columnIndexOfId: Int = getColumnIndexOrThrow(_stmt, "id")
        val _columnIndexOfUuid: Int = getColumnIndexOrThrow(_stmt, "uuid")
        val _columnIndexOfSourceId: Int = getColumnIndexOrThrow(_stmt, "source_id")
        val _columnIndexOfExamId: Int = getColumnIndexOrThrow(_stmt, "exam_id")
        val _columnIndexOfTitle: Int = getColumnIndexOrThrow(_stmt, "title")
        val _columnIndexOfKind: Int = getColumnIndexOrThrow(_stmt, "kind")
        val _columnIndexOfDurationSec: Int = getColumnIndexOrThrow(_stmt, "duration_sec")
        val _columnIndexOfMarksCorrect: Int = getColumnIndexOrThrow(_stmt, "marks_correct")
        val _columnIndexOfNegativeMarks: Int = getColumnIndexOrThrow(_stmt, "negative_marks")
        val _columnIndexOfArchived: Int = getColumnIndexOrThrow(_stmt, "archived")
        val _columnIndexOfCreatedAt: Int = getColumnIndexOrThrow(_stmt, "created_at")
        val _columnIndexOfUpdatedAt: Int = getColumnIndexOrThrow(_stmt, "updated_at")
        val _result: TestEntity?
        if (_stmt.step()) {
          val _tmpId: Long
          _tmpId = _stmt.getLong(_columnIndexOfId)
          val _tmpUuid: String
          _tmpUuid = _stmt.getText(_columnIndexOfUuid)
          val _tmpSourceId: Long?
          if (_stmt.isNull(_columnIndexOfSourceId)) {
            _tmpSourceId = null
          } else {
            _tmpSourceId = _stmt.getLong(_columnIndexOfSourceId)
          }
          val _tmpExamId: Long?
          if (_stmt.isNull(_columnIndexOfExamId)) {
            _tmpExamId = null
          } else {
            _tmpExamId = _stmt.getLong(_columnIndexOfExamId)
          }
          val _tmpTitle: String
          _tmpTitle = _stmt.getText(_columnIndexOfTitle)
          val _tmpKind: String
          _tmpKind = _stmt.getText(_columnIndexOfKind)
          val _tmpDurationSec: Int?
          if (_stmt.isNull(_columnIndexOfDurationSec)) {
            _tmpDurationSec = null
          } else {
            _tmpDurationSec = _stmt.getLong(_columnIndexOfDurationSec).toInt()
          }
          val _tmpMarksCorrect: Double
          _tmpMarksCorrect = _stmt.getDouble(_columnIndexOfMarksCorrect)
          val _tmpNegativeMarks: Double
          _tmpNegativeMarks = _stmt.getDouble(_columnIndexOfNegativeMarks)
          val _tmpArchived: Boolean
          val _tmp: Int
          _tmp = _stmt.getLong(_columnIndexOfArchived).toInt()
          _tmpArchived = _tmp != 0
          val _tmpCreatedAt: Long
          _tmpCreatedAt = _stmt.getLong(_columnIndexOfCreatedAt)
          val _tmpUpdatedAt: Long
          _tmpUpdatedAt = _stmt.getLong(_columnIndexOfUpdatedAt)
          _result =
              TestEntity(_tmpId,_tmpUuid,_tmpSourceId,_tmpExamId,_tmpTitle,_tmpKind,_tmpDurationSec,_tmpMarksCorrect,_tmpNegativeMarks,_tmpArchived,_tmpCreatedAt,_tmpUpdatedAt)
        } else {
          _result = null
        }
        _result
      } finally {
        _stmt.close()
      }
    }
  }

  public override suspend fun getTestByUuid(uuid: String): TestEntity? {
    val _sql: String = "SELECT * FROM test WHERE uuid = ?"
    return performSuspending(__db, true, false) { _connection ->
      val _stmt: SQLiteStatement = _connection.prepare(_sql)
      try {
        var _argIndex: Int = 1
        _stmt.bindText(_argIndex, uuid)
        val _columnIndexOfId: Int = getColumnIndexOrThrow(_stmt, "id")
        val _columnIndexOfUuid: Int = getColumnIndexOrThrow(_stmt, "uuid")
        val _columnIndexOfSourceId: Int = getColumnIndexOrThrow(_stmt, "source_id")
        val _columnIndexOfExamId: Int = getColumnIndexOrThrow(_stmt, "exam_id")
        val _columnIndexOfTitle: Int = getColumnIndexOrThrow(_stmt, "title")
        val _columnIndexOfKind: Int = getColumnIndexOrThrow(_stmt, "kind")
        val _columnIndexOfDurationSec: Int = getColumnIndexOrThrow(_stmt, "duration_sec")
        val _columnIndexOfMarksCorrect: Int = getColumnIndexOrThrow(_stmt, "marks_correct")
        val _columnIndexOfNegativeMarks: Int = getColumnIndexOrThrow(_stmt, "negative_marks")
        val _columnIndexOfArchived: Int = getColumnIndexOrThrow(_stmt, "archived")
        val _columnIndexOfCreatedAt: Int = getColumnIndexOrThrow(_stmt, "created_at")
        val _columnIndexOfUpdatedAt: Int = getColumnIndexOrThrow(_stmt, "updated_at")
        val _result: TestEntity?
        if (_stmt.step()) {
          val _tmpId: Long
          _tmpId = _stmt.getLong(_columnIndexOfId)
          val _tmpUuid: String
          _tmpUuid = _stmt.getText(_columnIndexOfUuid)
          val _tmpSourceId: Long?
          if (_stmt.isNull(_columnIndexOfSourceId)) {
            _tmpSourceId = null
          } else {
            _tmpSourceId = _stmt.getLong(_columnIndexOfSourceId)
          }
          val _tmpExamId: Long?
          if (_stmt.isNull(_columnIndexOfExamId)) {
            _tmpExamId = null
          } else {
            _tmpExamId = _stmt.getLong(_columnIndexOfExamId)
          }
          val _tmpTitle: String
          _tmpTitle = _stmt.getText(_columnIndexOfTitle)
          val _tmpKind: String
          _tmpKind = _stmt.getText(_columnIndexOfKind)
          val _tmpDurationSec: Int?
          if (_stmt.isNull(_columnIndexOfDurationSec)) {
            _tmpDurationSec = null
          } else {
            _tmpDurationSec = _stmt.getLong(_columnIndexOfDurationSec).toInt()
          }
          val _tmpMarksCorrect: Double
          _tmpMarksCorrect = _stmt.getDouble(_columnIndexOfMarksCorrect)
          val _tmpNegativeMarks: Double
          _tmpNegativeMarks = _stmt.getDouble(_columnIndexOfNegativeMarks)
          val _tmpArchived: Boolean
          val _tmp: Int
          _tmp = _stmt.getLong(_columnIndexOfArchived).toInt()
          _tmpArchived = _tmp != 0
          val _tmpCreatedAt: Long
          _tmpCreatedAt = _stmt.getLong(_columnIndexOfCreatedAt)
          val _tmpUpdatedAt: Long
          _tmpUpdatedAt = _stmt.getLong(_columnIndexOfUpdatedAt)
          _result =
              TestEntity(_tmpId,_tmpUuid,_tmpSourceId,_tmpExamId,_tmpTitle,_tmpKind,_tmpDurationSec,_tmpMarksCorrect,_tmpNegativeMarks,_tmpArchived,_tmpCreatedAt,_tmpUpdatedAt)
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
    val _sql: String = "SELECT COUNT(*) FROM test WHERE archived = 0"
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
