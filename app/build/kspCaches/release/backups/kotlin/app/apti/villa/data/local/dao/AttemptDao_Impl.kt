package app.apti.villa.`data`.local.dao

import androidx.room.EntityDeleteOrUpdateAdapter
import androidx.room.EntityInsertAdapter
import androidx.room.RoomDatabase
import androidx.room.coroutines.createFlow
import androidx.room.util.getColumnIndexOrThrow
import androidx.room.util.performSuspending
import androidx.sqlite.SQLiteStatement
import app.apti.villa.`data`.local.entity.AttemptEntity
import javax.`annotation`.processing.Generated
import kotlin.Boolean
import kotlin.Float
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
public class AttemptDao_Impl(
  __db: RoomDatabase,
) : AttemptDao {
  private val __db: RoomDatabase

  private val __insertAdapterOfAttemptEntity: EntityInsertAdapter<AttemptEntity>

  private val __updateAdapterOfAttemptEntity: EntityDeleteOrUpdateAdapter<AttemptEntity>
  init {
    this.__db = __db
    this.__insertAdapterOfAttemptEntity = object : EntityInsertAdapter<AttemptEntity>() {
      protected override fun createQuery(): String =
          "INSERT OR REPLACE INTO `attempt` (`id`,`uuid`,`test_id`,`mode`,`scope_desc`,`status`,`started_at`,`submitted_at`,`time_limit_sec`,`active_elapsed_ms`,`score`,`is_retry`,`created_at`,`updated_at`) VALUES (nullif(?, 0),?,?,?,?,?,?,?,?,?,?,?,?,?)"

      protected override fun bind(statement: SQLiteStatement, entity: AttemptEntity) {
        statement.bindLong(1, entity.id)
        statement.bindText(2, entity.uuid)
        val _tmpTestId: Long? = entity.testId
        if (_tmpTestId == null) {
          statement.bindNull(3)
        } else {
          statement.bindLong(3, _tmpTestId)
        }
        statement.bindText(4, entity.mode)
        val _tmpScopeDesc: String? = entity.scopeDesc
        if (_tmpScopeDesc == null) {
          statement.bindNull(5)
        } else {
          statement.bindText(5, _tmpScopeDesc)
        }
        statement.bindText(6, entity.status)
        statement.bindLong(7, entity.startedAt)
        val _tmpSubmittedAt: Long? = entity.submittedAt
        if (_tmpSubmittedAt == null) {
          statement.bindNull(8)
        } else {
          statement.bindLong(8, _tmpSubmittedAt)
        }
        val _tmpTimeLimitSec: Int? = entity.timeLimitSec
        if (_tmpTimeLimitSec == null) {
          statement.bindNull(9)
        } else {
          statement.bindLong(9, _tmpTimeLimitSec.toLong())
        }
        statement.bindLong(10, entity.activeElapsedMs)
        val _tmpScore: Float? = entity.score
        if (_tmpScore == null) {
          statement.bindNull(11)
        } else {
          statement.bindDouble(11, _tmpScore.toDouble())
        }
        val _tmp: Int = if (entity.isRetry) 1 else 0
        statement.bindLong(12, _tmp.toLong())
        statement.bindLong(13, entity.createdAt)
        statement.bindLong(14, entity.updatedAt)
      }
    }
    this.__updateAdapterOfAttemptEntity = object : EntityDeleteOrUpdateAdapter<AttemptEntity>() {
      protected override fun createQuery(): String =
          "UPDATE OR ABORT `attempt` SET `id` = ?,`uuid` = ?,`test_id` = ?,`mode` = ?,`scope_desc` = ?,`status` = ?,`started_at` = ?,`submitted_at` = ?,`time_limit_sec` = ?,`active_elapsed_ms` = ?,`score` = ?,`is_retry` = ?,`created_at` = ?,`updated_at` = ? WHERE `id` = ?"

      protected override fun bind(statement: SQLiteStatement, entity: AttemptEntity) {
        statement.bindLong(1, entity.id)
        statement.bindText(2, entity.uuid)
        val _tmpTestId: Long? = entity.testId
        if (_tmpTestId == null) {
          statement.bindNull(3)
        } else {
          statement.bindLong(3, _tmpTestId)
        }
        statement.bindText(4, entity.mode)
        val _tmpScopeDesc: String? = entity.scopeDesc
        if (_tmpScopeDesc == null) {
          statement.bindNull(5)
        } else {
          statement.bindText(5, _tmpScopeDesc)
        }
        statement.bindText(6, entity.status)
        statement.bindLong(7, entity.startedAt)
        val _tmpSubmittedAt: Long? = entity.submittedAt
        if (_tmpSubmittedAt == null) {
          statement.bindNull(8)
        } else {
          statement.bindLong(8, _tmpSubmittedAt)
        }
        val _tmpTimeLimitSec: Int? = entity.timeLimitSec
        if (_tmpTimeLimitSec == null) {
          statement.bindNull(9)
        } else {
          statement.bindLong(9, _tmpTimeLimitSec.toLong())
        }
        statement.bindLong(10, entity.activeElapsedMs)
        val _tmpScore: Float? = entity.score
        if (_tmpScore == null) {
          statement.bindNull(11)
        } else {
          statement.bindDouble(11, _tmpScore.toDouble())
        }
        val _tmp: Int = if (entity.isRetry) 1 else 0
        statement.bindLong(12, _tmp.toLong())
        statement.bindLong(13, entity.createdAt)
        statement.bindLong(14, entity.updatedAt)
        statement.bindLong(15, entity.id)
      }
    }
  }

  public override suspend fun insert(attempt: AttemptEntity): Long = performSuspending(__db, false,
      true) { _connection ->
    val _result: Long = __insertAdapterOfAttemptEntity.insertAndReturnId(_connection, attempt)
    _result
  }

  public override suspend fun update(attempt: AttemptEntity): Unit = performSuspending(__db, false,
      true) { _connection ->
    __updateAdapterOfAttemptEntity.handle(_connection, attempt)
  }

  public override suspend fun getAttemptById(id: Long): AttemptEntity? {
    val _sql: String = "SELECT * FROM attempt WHERE id = ?"
    return performSuspending(__db, true, false) { _connection ->
      val _stmt: SQLiteStatement = _connection.prepare(_sql)
      try {
        var _argIndex: Int = 1
        _stmt.bindLong(_argIndex, id)
        val _columnIndexOfId: Int = getColumnIndexOrThrow(_stmt, "id")
        val _columnIndexOfUuid: Int = getColumnIndexOrThrow(_stmt, "uuid")
        val _columnIndexOfTestId: Int = getColumnIndexOrThrow(_stmt, "test_id")
        val _columnIndexOfMode: Int = getColumnIndexOrThrow(_stmt, "mode")
        val _columnIndexOfScopeDesc: Int = getColumnIndexOrThrow(_stmt, "scope_desc")
        val _columnIndexOfStatus: Int = getColumnIndexOrThrow(_stmt, "status")
        val _columnIndexOfStartedAt: Int = getColumnIndexOrThrow(_stmt, "started_at")
        val _columnIndexOfSubmittedAt: Int = getColumnIndexOrThrow(_stmt, "submitted_at")
        val _columnIndexOfTimeLimitSec: Int = getColumnIndexOrThrow(_stmt, "time_limit_sec")
        val _columnIndexOfActiveElapsedMs: Int = getColumnIndexOrThrow(_stmt, "active_elapsed_ms")
        val _columnIndexOfScore: Int = getColumnIndexOrThrow(_stmt, "score")
        val _columnIndexOfIsRetry: Int = getColumnIndexOrThrow(_stmt, "is_retry")
        val _columnIndexOfCreatedAt: Int = getColumnIndexOrThrow(_stmt, "created_at")
        val _columnIndexOfUpdatedAt: Int = getColumnIndexOrThrow(_stmt, "updated_at")
        val _result: AttemptEntity?
        if (_stmt.step()) {
          val _tmpId: Long
          _tmpId = _stmt.getLong(_columnIndexOfId)
          val _tmpUuid: String
          _tmpUuid = _stmt.getText(_columnIndexOfUuid)
          val _tmpTestId: Long?
          if (_stmt.isNull(_columnIndexOfTestId)) {
            _tmpTestId = null
          } else {
            _tmpTestId = _stmt.getLong(_columnIndexOfTestId)
          }
          val _tmpMode: String
          _tmpMode = _stmt.getText(_columnIndexOfMode)
          val _tmpScopeDesc: String?
          if (_stmt.isNull(_columnIndexOfScopeDesc)) {
            _tmpScopeDesc = null
          } else {
            _tmpScopeDesc = _stmt.getText(_columnIndexOfScopeDesc)
          }
          val _tmpStatus: String
          _tmpStatus = _stmt.getText(_columnIndexOfStatus)
          val _tmpStartedAt: Long
          _tmpStartedAt = _stmt.getLong(_columnIndexOfStartedAt)
          val _tmpSubmittedAt: Long?
          if (_stmt.isNull(_columnIndexOfSubmittedAt)) {
            _tmpSubmittedAt = null
          } else {
            _tmpSubmittedAt = _stmt.getLong(_columnIndexOfSubmittedAt)
          }
          val _tmpTimeLimitSec: Int?
          if (_stmt.isNull(_columnIndexOfTimeLimitSec)) {
            _tmpTimeLimitSec = null
          } else {
            _tmpTimeLimitSec = _stmt.getLong(_columnIndexOfTimeLimitSec).toInt()
          }
          val _tmpActiveElapsedMs: Long
          _tmpActiveElapsedMs = _stmt.getLong(_columnIndexOfActiveElapsedMs)
          val _tmpScore: Float?
          if (_stmt.isNull(_columnIndexOfScore)) {
            _tmpScore = null
          } else {
            _tmpScore = _stmt.getDouble(_columnIndexOfScore).toFloat()
          }
          val _tmpIsRetry: Boolean
          val _tmp: Int
          _tmp = _stmt.getLong(_columnIndexOfIsRetry).toInt()
          _tmpIsRetry = _tmp != 0
          val _tmpCreatedAt: Long
          _tmpCreatedAt = _stmt.getLong(_columnIndexOfCreatedAt)
          val _tmpUpdatedAt: Long
          _tmpUpdatedAt = _stmt.getLong(_columnIndexOfUpdatedAt)
          _result =
              AttemptEntity(_tmpId,_tmpUuid,_tmpTestId,_tmpMode,_tmpScopeDesc,_tmpStatus,_tmpStartedAt,_tmpSubmittedAt,_tmpTimeLimitSec,_tmpActiveElapsedMs,_tmpScore,_tmpIsRetry,_tmpCreatedAt,_tmpUpdatedAt)
        } else {
          _result = null
        }
        _result
      } finally {
        _stmt.close()
      }
    }
  }

  public override fun getAttemptByIdFlow(id: Long): Flow<AttemptEntity?> {
    val _sql: String = "SELECT * FROM attempt WHERE id = ?"
    return createFlow(__db, false, arrayOf("attempt")) { _connection ->
      val _stmt: SQLiteStatement = _connection.prepare(_sql)
      try {
        var _argIndex: Int = 1
        _stmt.bindLong(_argIndex, id)
        val _columnIndexOfId: Int = getColumnIndexOrThrow(_stmt, "id")
        val _columnIndexOfUuid: Int = getColumnIndexOrThrow(_stmt, "uuid")
        val _columnIndexOfTestId: Int = getColumnIndexOrThrow(_stmt, "test_id")
        val _columnIndexOfMode: Int = getColumnIndexOrThrow(_stmt, "mode")
        val _columnIndexOfScopeDesc: Int = getColumnIndexOrThrow(_stmt, "scope_desc")
        val _columnIndexOfStatus: Int = getColumnIndexOrThrow(_stmt, "status")
        val _columnIndexOfStartedAt: Int = getColumnIndexOrThrow(_stmt, "started_at")
        val _columnIndexOfSubmittedAt: Int = getColumnIndexOrThrow(_stmt, "submitted_at")
        val _columnIndexOfTimeLimitSec: Int = getColumnIndexOrThrow(_stmt, "time_limit_sec")
        val _columnIndexOfActiveElapsedMs: Int = getColumnIndexOrThrow(_stmt, "active_elapsed_ms")
        val _columnIndexOfScore: Int = getColumnIndexOrThrow(_stmt, "score")
        val _columnIndexOfIsRetry: Int = getColumnIndexOrThrow(_stmt, "is_retry")
        val _columnIndexOfCreatedAt: Int = getColumnIndexOrThrow(_stmt, "created_at")
        val _columnIndexOfUpdatedAt: Int = getColumnIndexOrThrow(_stmt, "updated_at")
        val _result: AttemptEntity?
        if (_stmt.step()) {
          val _tmpId: Long
          _tmpId = _stmt.getLong(_columnIndexOfId)
          val _tmpUuid: String
          _tmpUuid = _stmt.getText(_columnIndexOfUuid)
          val _tmpTestId: Long?
          if (_stmt.isNull(_columnIndexOfTestId)) {
            _tmpTestId = null
          } else {
            _tmpTestId = _stmt.getLong(_columnIndexOfTestId)
          }
          val _tmpMode: String
          _tmpMode = _stmt.getText(_columnIndexOfMode)
          val _tmpScopeDesc: String?
          if (_stmt.isNull(_columnIndexOfScopeDesc)) {
            _tmpScopeDesc = null
          } else {
            _tmpScopeDesc = _stmt.getText(_columnIndexOfScopeDesc)
          }
          val _tmpStatus: String
          _tmpStatus = _stmt.getText(_columnIndexOfStatus)
          val _tmpStartedAt: Long
          _tmpStartedAt = _stmt.getLong(_columnIndexOfStartedAt)
          val _tmpSubmittedAt: Long?
          if (_stmt.isNull(_columnIndexOfSubmittedAt)) {
            _tmpSubmittedAt = null
          } else {
            _tmpSubmittedAt = _stmt.getLong(_columnIndexOfSubmittedAt)
          }
          val _tmpTimeLimitSec: Int?
          if (_stmt.isNull(_columnIndexOfTimeLimitSec)) {
            _tmpTimeLimitSec = null
          } else {
            _tmpTimeLimitSec = _stmt.getLong(_columnIndexOfTimeLimitSec).toInt()
          }
          val _tmpActiveElapsedMs: Long
          _tmpActiveElapsedMs = _stmt.getLong(_columnIndexOfActiveElapsedMs)
          val _tmpScore: Float?
          if (_stmt.isNull(_columnIndexOfScore)) {
            _tmpScore = null
          } else {
            _tmpScore = _stmt.getDouble(_columnIndexOfScore).toFloat()
          }
          val _tmpIsRetry: Boolean
          val _tmp: Int
          _tmp = _stmt.getLong(_columnIndexOfIsRetry).toInt()
          _tmpIsRetry = _tmp != 0
          val _tmpCreatedAt: Long
          _tmpCreatedAt = _stmt.getLong(_columnIndexOfCreatedAt)
          val _tmpUpdatedAt: Long
          _tmpUpdatedAt = _stmt.getLong(_columnIndexOfUpdatedAt)
          _result =
              AttemptEntity(_tmpId,_tmpUuid,_tmpTestId,_tmpMode,_tmpScopeDesc,_tmpStatus,_tmpStartedAt,_tmpSubmittedAt,_tmpTimeLimitSec,_tmpActiveElapsedMs,_tmpScore,_tmpIsRetry,_tmpCreatedAt,_tmpUpdatedAt)
        } else {
          _result = null
        }
        _result
      } finally {
        _stmt.close()
      }
    }
  }

  public override suspend fun getAttemptByUuid(uuid: String): AttemptEntity? {
    val _sql: String = "SELECT * FROM attempt WHERE uuid = ? LIMIT 1"
    return performSuspending(__db, true, false) { _connection ->
      val _stmt: SQLiteStatement = _connection.prepare(_sql)
      try {
        var _argIndex: Int = 1
        _stmt.bindText(_argIndex, uuid)
        val _columnIndexOfId: Int = getColumnIndexOrThrow(_stmt, "id")
        val _columnIndexOfUuid: Int = getColumnIndexOrThrow(_stmt, "uuid")
        val _columnIndexOfTestId: Int = getColumnIndexOrThrow(_stmt, "test_id")
        val _columnIndexOfMode: Int = getColumnIndexOrThrow(_stmt, "mode")
        val _columnIndexOfScopeDesc: Int = getColumnIndexOrThrow(_stmt, "scope_desc")
        val _columnIndexOfStatus: Int = getColumnIndexOrThrow(_stmt, "status")
        val _columnIndexOfStartedAt: Int = getColumnIndexOrThrow(_stmt, "started_at")
        val _columnIndexOfSubmittedAt: Int = getColumnIndexOrThrow(_stmt, "submitted_at")
        val _columnIndexOfTimeLimitSec: Int = getColumnIndexOrThrow(_stmt, "time_limit_sec")
        val _columnIndexOfActiveElapsedMs: Int = getColumnIndexOrThrow(_stmt, "active_elapsed_ms")
        val _columnIndexOfScore: Int = getColumnIndexOrThrow(_stmt, "score")
        val _columnIndexOfIsRetry: Int = getColumnIndexOrThrow(_stmt, "is_retry")
        val _columnIndexOfCreatedAt: Int = getColumnIndexOrThrow(_stmt, "created_at")
        val _columnIndexOfUpdatedAt: Int = getColumnIndexOrThrow(_stmt, "updated_at")
        val _result: AttemptEntity?
        if (_stmt.step()) {
          val _tmpId: Long
          _tmpId = _stmt.getLong(_columnIndexOfId)
          val _tmpUuid: String
          _tmpUuid = _stmt.getText(_columnIndexOfUuid)
          val _tmpTestId: Long?
          if (_stmt.isNull(_columnIndexOfTestId)) {
            _tmpTestId = null
          } else {
            _tmpTestId = _stmt.getLong(_columnIndexOfTestId)
          }
          val _tmpMode: String
          _tmpMode = _stmt.getText(_columnIndexOfMode)
          val _tmpScopeDesc: String?
          if (_stmt.isNull(_columnIndexOfScopeDesc)) {
            _tmpScopeDesc = null
          } else {
            _tmpScopeDesc = _stmt.getText(_columnIndexOfScopeDesc)
          }
          val _tmpStatus: String
          _tmpStatus = _stmt.getText(_columnIndexOfStatus)
          val _tmpStartedAt: Long
          _tmpStartedAt = _stmt.getLong(_columnIndexOfStartedAt)
          val _tmpSubmittedAt: Long?
          if (_stmt.isNull(_columnIndexOfSubmittedAt)) {
            _tmpSubmittedAt = null
          } else {
            _tmpSubmittedAt = _stmt.getLong(_columnIndexOfSubmittedAt)
          }
          val _tmpTimeLimitSec: Int?
          if (_stmt.isNull(_columnIndexOfTimeLimitSec)) {
            _tmpTimeLimitSec = null
          } else {
            _tmpTimeLimitSec = _stmt.getLong(_columnIndexOfTimeLimitSec).toInt()
          }
          val _tmpActiveElapsedMs: Long
          _tmpActiveElapsedMs = _stmt.getLong(_columnIndexOfActiveElapsedMs)
          val _tmpScore: Float?
          if (_stmt.isNull(_columnIndexOfScore)) {
            _tmpScore = null
          } else {
            _tmpScore = _stmt.getDouble(_columnIndexOfScore).toFloat()
          }
          val _tmpIsRetry: Boolean
          val _tmp: Int
          _tmp = _stmt.getLong(_columnIndexOfIsRetry).toInt()
          _tmpIsRetry = _tmp != 0
          val _tmpCreatedAt: Long
          _tmpCreatedAt = _stmt.getLong(_columnIndexOfCreatedAt)
          val _tmpUpdatedAt: Long
          _tmpUpdatedAt = _stmt.getLong(_columnIndexOfUpdatedAt)
          _result =
              AttemptEntity(_tmpId,_tmpUuid,_tmpTestId,_tmpMode,_tmpScopeDesc,_tmpStatus,_tmpStartedAt,_tmpSubmittedAt,_tmpTimeLimitSec,_tmpActiveElapsedMs,_tmpScore,_tmpIsRetry,_tmpCreatedAt,_tmpUpdatedAt)
        } else {
          _result = null
        }
        _result
      } finally {
        _stmt.close()
      }
    }
  }

  public override suspend fun getLatestInProgressAttempt(mode: String): AttemptEntity? {
    val _sql: String =
        "SELECT * FROM attempt WHERE mode = ? AND status = 'IN_PROGRESS' ORDER BY updated_at DESC LIMIT 1"
    return performSuspending(__db, true, false) { _connection ->
      val _stmt: SQLiteStatement = _connection.prepare(_sql)
      try {
        var _argIndex: Int = 1
        _stmt.bindText(_argIndex, mode)
        val _columnIndexOfId: Int = getColumnIndexOrThrow(_stmt, "id")
        val _columnIndexOfUuid: Int = getColumnIndexOrThrow(_stmt, "uuid")
        val _columnIndexOfTestId: Int = getColumnIndexOrThrow(_stmt, "test_id")
        val _columnIndexOfMode: Int = getColumnIndexOrThrow(_stmt, "mode")
        val _columnIndexOfScopeDesc: Int = getColumnIndexOrThrow(_stmt, "scope_desc")
        val _columnIndexOfStatus: Int = getColumnIndexOrThrow(_stmt, "status")
        val _columnIndexOfStartedAt: Int = getColumnIndexOrThrow(_stmt, "started_at")
        val _columnIndexOfSubmittedAt: Int = getColumnIndexOrThrow(_stmt, "submitted_at")
        val _columnIndexOfTimeLimitSec: Int = getColumnIndexOrThrow(_stmt, "time_limit_sec")
        val _columnIndexOfActiveElapsedMs: Int = getColumnIndexOrThrow(_stmt, "active_elapsed_ms")
        val _columnIndexOfScore: Int = getColumnIndexOrThrow(_stmt, "score")
        val _columnIndexOfIsRetry: Int = getColumnIndexOrThrow(_stmt, "is_retry")
        val _columnIndexOfCreatedAt: Int = getColumnIndexOrThrow(_stmt, "created_at")
        val _columnIndexOfUpdatedAt: Int = getColumnIndexOrThrow(_stmt, "updated_at")
        val _result: AttemptEntity?
        if (_stmt.step()) {
          val _tmpId: Long
          _tmpId = _stmt.getLong(_columnIndexOfId)
          val _tmpUuid: String
          _tmpUuid = _stmt.getText(_columnIndexOfUuid)
          val _tmpTestId: Long?
          if (_stmt.isNull(_columnIndexOfTestId)) {
            _tmpTestId = null
          } else {
            _tmpTestId = _stmt.getLong(_columnIndexOfTestId)
          }
          val _tmpMode: String
          _tmpMode = _stmt.getText(_columnIndexOfMode)
          val _tmpScopeDesc: String?
          if (_stmt.isNull(_columnIndexOfScopeDesc)) {
            _tmpScopeDesc = null
          } else {
            _tmpScopeDesc = _stmt.getText(_columnIndexOfScopeDesc)
          }
          val _tmpStatus: String
          _tmpStatus = _stmt.getText(_columnIndexOfStatus)
          val _tmpStartedAt: Long
          _tmpStartedAt = _stmt.getLong(_columnIndexOfStartedAt)
          val _tmpSubmittedAt: Long?
          if (_stmt.isNull(_columnIndexOfSubmittedAt)) {
            _tmpSubmittedAt = null
          } else {
            _tmpSubmittedAt = _stmt.getLong(_columnIndexOfSubmittedAt)
          }
          val _tmpTimeLimitSec: Int?
          if (_stmt.isNull(_columnIndexOfTimeLimitSec)) {
            _tmpTimeLimitSec = null
          } else {
            _tmpTimeLimitSec = _stmt.getLong(_columnIndexOfTimeLimitSec).toInt()
          }
          val _tmpActiveElapsedMs: Long
          _tmpActiveElapsedMs = _stmt.getLong(_columnIndexOfActiveElapsedMs)
          val _tmpScore: Float?
          if (_stmt.isNull(_columnIndexOfScore)) {
            _tmpScore = null
          } else {
            _tmpScore = _stmt.getDouble(_columnIndexOfScore).toFloat()
          }
          val _tmpIsRetry: Boolean
          val _tmp: Int
          _tmp = _stmt.getLong(_columnIndexOfIsRetry).toInt()
          _tmpIsRetry = _tmp != 0
          val _tmpCreatedAt: Long
          _tmpCreatedAt = _stmt.getLong(_columnIndexOfCreatedAt)
          val _tmpUpdatedAt: Long
          _tmpUpdatedAt = _stmt.getLong(_columnIndexOfUpdatedAt)
          _result =
              AttemptEntity(_tmpId,_tmpUuid,_tmpTestId,_tmpMode,_tmpScopeDesc,_tmpStatus,_tmpStartedAt,_tmpSubmittedAt,_tmpTimeLimitSec,_tmpActiveElapsedMs,_tmpScore,_tmpIsRetry,_tmpCreatedAt,_tmpUpdatedAt)
        } else {
          _result = null
        }
        _result
      } finally {
        _stmt.close()
      }
    }
  }

  public override fun getLatestInProgressAttemptFlow(mode: String): Flow<AttemptEntity?> {
    val _sql: String =
        "SELECT * FROM attempt WHERE mode = ? AND status = 'IN_PROGRESS' ORDER BY updated_at DESC LIMIT 1"
    return createFlow(__db, false, arrayOf("attempt")) { _connection ->
      val _stmt: SQLiteStatement = _connection.prepare(_sql)
      try {
        var _argIndex: Int = 1
        _stmt.bindText(_argIndex, mode)
        val _columnIndexOfId: Int = getColumnIndexOrThrow(_stmt, "id")
        val _columnIndexOfUuid: Int = getColumnIndexOrThrow(_stmt, "uuid")
        val _columnIndexOfTestId: Int = getColumnIndexOrThrow(_stmt, "test_id")
        val _columnIndexOfMode: Int = getColumnIndexOrThrow(_stmt, "mode")
        val _columnIndexOfScopeDesc: Int = getColumnIndexOrThrow(_stmt, "scope_desc")
        val _columnIndexOfStatus: Int = getColumnIndexOrThrow(_stmt, "status")
        val _columnIndexOfStartedAt: Int = getColumnIndexOrThrow(_stmt, "started_at")
        val _columnIndexOfSubmittedAt: Int = getColumnIndexOrThrow(_stmt, "submitted_at")
        val _columnIndexOfTimeLimitSec: Int = getColumnIndexOrThrow(_stmt, "time_limit_sec")
        val _columnIndexOfActiveElapsedMs: Int = getColumnIndexOrThrow(_stmt, "active_elapsed_ms")
        val _columnIndexOfScore: Int = getColumnIndexOrThrow(_stmt, "score")
        val _columnIndexOfIsRetry: Int = getColumnIndexOrThrow(_stmt, "is_retry")
        val _columnIndexOfCreatedAt: Int = getColumnIndexOrThrow(_stmt, "created_at")
        val _columnIndexOfUpdatedAt: Int = getColumnIndexOrThrow(_stmt, "updated_at")
        val _result: AttemptEntity?
        if (_stmt.step()) {
          val _tmpId: Long
          _tmpId = _stmt.getLong(_columnIndexOfId)
          val _tmpUuid: String
          _tmpUuid = _stmt.getText(_columnIndexOfUuid)
          val _tmpTestId: Long?
          if (_stmt.isNull(_columnIndexOfTestId)) {
            _tmpTestId = null
          } else {
            _tmpTestId = _stmt.getLong(_columnIndexOfTestId)
          }
          val _tmpMode: String
          _tmpMode = _stmt.getText(_columnIndexOfMode)
          val _tmpScopeDesc: String?
          if (_stmt.isNull(_columnIndexOfScopeDesc)) {
            _tmpScopeDesc = null
          } else {
            _tmpScopeDesc = _stmt.getText(_columnIndexOfScopeDesc)
          }
          val _tmpStatus: String
          _tmpStatus = _stmt.getText(_columnIndexOfStatus)
          val _tmpStartedAt: Long
          _tmpStartedAt = _stmt.getLong(_columnIndexOfStartedAt)
          val _tmpSubmittedAt: Long?
          if (_stmt.isNull(_columnIndexOfSubmittedAt)) {
            _tmpSubmittedAt = null
          } else {
            _tmpSubmittedAt = _stmt.getLong(_columnIndexOfSubmittedAt)
          }
          val _tmpTimeLimitSec: Int?
          if (_stmt.isNull(_columnIndexOfTimeLimitSec)) {
            _tmpTimeLimitSec = null
          } else {
            _tmpTimeLimitSec = _stmt.getLong(_columnIndexOfTimeLimitSec).toInt()
          }
          val _tmpActiveElapsedMs: Long
          _tmpActiveElapsedMs = _stmt.getLong(_columnIndexOfActiveElapsedMs)
          val _tmpScore: Float?
          if (_stmt.isNull(_columnIndexOfScore)) {
            _tmpScore = null
          } else {
            _tmpScore = _stmt.getDouble(_columnIndexOfScore).toFloat()
          }
          val _tmpIsRetry: Boolean
          val _tmp: Int
          _tmp = _stmt.getLong(_columnIndexOfIsRetry).toInt()
          _tmpIsRetry = _tmp != 0
          val _tmpCreatedAt: Long
          _tmpCreatedAt = _stmt.getLong(_columnIndexOfCreatedAt)
          val _tmpUpdatedAt: Long
          _tmpUpdatedAt = _stmt.getLong(_columnIndexOfUpdatedAt)
          _result =
              AttemptEntity(_tmpId,_tmpUuid,_tmpTestId,_tmpMode,_tmpScopeDesc,_tmpStatus,_tmpStartedAt,_tmpSubmittedAt,_tmpTimeLimitSec,_tmpActiveElapsedMs,_tmpScore,_tmpIsRetry,_tmpCreatedAt,_tmpUpdatedAt)
        } else {
          _result = null
        }
        _result
      } finally {
        _stmt.close()
      }
    }
  }

  public override fun getAttemptsForTest(testId: Long): Flow<List<AttemptEntity>> {
    val _sql: String = "SELECT * FROM attempt WHERE test_id = ? ORDER BY created_at DESC"
    return createFlow(__db, false, arrayOf("attempt")) { _connection ->
      val _stmt: SQLiteStatement = _connection.prepare(_sql)
      try {
        var _argIndex: Int = 1
        _stmt.bindLong(_argIndex, testId)
        val _columnIndexOfId: Int = getColumnIndexOrThrow(_stmt, "id")
        val _columnIndexOfUuid: Int = getColumnIndexOrThrow(_stmt, "uuid")
        val _columnIndexOfTestId: Int = getColumnIndexOrThrow(_stmt, "test_id")
        val _columnIndexOfMode: Int = getColumnIndexOrThrow(_stmt, "mode")
        val _columnIndexOfScopeDesc: Int = getColumnIndexOrThrow(_stmt, "scope_desc")
        val _columnIndexOfStatus: Int = getColumnIndexOrThrow(_stmt, "status")
        val _columnIndexOfStartedAt: Int = getColumnIndexOrThrow(_stmt, "started_at")
        val _columnIndexOfSubmittedAt: Int = getColumnIndexOrThrow(_stmt, "submitted_at")
        val _columnIndexOfTimeLimitSec: Int = getColumnIndexOrThrow(_stmt, "time_limit_sec")
        val _columnIndexOfActiveElapsedMs: Int = getColumnIndexOrThrow(_stmt, "active_elapsed_ms")
        val _columnIndexOfScore: Int = getColumnIndexOrThrow(_stmt, "score")
        val _columnIndexOfIsRetry: Int = getColumnIndexOrThrow(_stmt, "is_retry")
        val _columnIndexOfCreatedAt: Int = getColumnIndexOrThrow(_stmt, "created_at")
        val _columnIndexOfUpdatedAt: Int = getColumnIndexOrThrow(_stmt, "updated_at")
        val _result: MutableList<AttemptEntity> = mutableListOf()
        while (_stmt.step()) {
          val _item: AttemptEntity
          val _tmpId: Long
          _tmpId = _stmt.getLong(_columnIndexOfId)
          val _tmpUuid: String
          _tmpUuid = _stmt.getText(_columnIndexOfUuid)
          val _tmpTestId: Long?
          if (_stmt.isNull(_columnIndexOfTestId)) {
            _tmpTestId = null
          } else {
            _tmpTestId = _stmt.getLong(_columnIndexOfTestId)
          }
          val _tmpMode: String
          _tmpMode = _stmt.getText(_columnIndexOfMode)
          val _tmpScopeDesc: String?
          if (_stmt.isNull(_columnIndexOfScopeDesc)) {
            _tmpScopeDesc = null
          } else {
            _tmpScopeDesc = _stmt.getText(_columnIndexOfScopeDesc)
          }
          val _tmpStatus: String
          _tmpStatus = _stmt.getText(_columnIndexOfStatus)
          val _tmpStartedAt: Long
          _tmpStartedAt = _stmt.getLong(_columnIndexOfStartedAt)
          val _tmpSubmittedAt: Long?
          if (_stmt.isNull(_columnIndexOfSubmittedAt)) {
            _tmpSubmittedAt = null
          } else {
            _tmpSubmittedAt = _stmt.getLong(_columnIndexOfSubmittedAt)
          }
          val _tmpTimeLimitSec: Int?
          if (_stmt.isNull(_columnIndexOfTimeLimitSec)) {
            _tmpTimeLimitSec = null
          } else {
            _tmpTimeLimitSec = _stmt.getLong(_columnIndexOfTimeLimitSec).toInt()
          }
          val _tmpActiveElapsedMs: Long
          _tmpActiveElapsedMs = _stmt.getLong(_columnIndexOfActiveElapsedMs)
          val _tmpScore: Float?
          if (_stmt.isNull(_columnIndexOfScore)) {
            _tmpScore = null
          } else {
            _tmpScore = _stmt.getDouble(_columnIndexOfScore).toFloat()
          }
          val _tmpIsRetry: Boolean
          val _tmp: Int
          _tmp = _stmt.getLong(_columnIndexOfIsRetry).toInt()
          _tmpIsRetry = _tmp != 0
          val _tmpCreatedAt: Long
          _tmpCreatedAt = _stmt.getLong(_columnIndexOfCreatedAt)
          val _tmpUpdatedAt: Long
          _tmpUpdatedAt = _stmt.getLong(_columnIndexOfUpdatedAt)
          _item =
              AttemptEntity(_tmpId,_tmpUuid,_tmpTestId,_tmpMode,_tmpScopeDesc,_tmpStatus,_tmpStartedAt,_tmpSubmittedAt,_tmpTimeLimitSec,_tmpActiveElapsedMs,_tmpScore,_tmpIsRetry,_tmpCreatedAt,_tmpUpdatedAt)
          _result.add(_item)
        }
        _result
      } finally {
        _stmt.close()
      }
    }
  }

  public override suspend fun deleteAttempt(id: Long) {
    val _sql: String = "DELETE FROM attempt WHERE id = ?"
    return performSuspending(__db, false, true) { _connection ->
      val _stmt: SQLiteStatement = _connection.prepare(_sql)
      try {
        var _argIndex: Int = 1
        _stmt.bindLong(_argIndex, id)
        _stmt.step()
      } finally {
        _stmt.close()
      }
    }
  }

  public override suspend fun deleteAllAttempts() {
    val _sql: String = "DELETE FROM attempt"
    return performSuspending(__db, false, true) { _connection ->
      val _stmt: SQLiteStatement = _connection.prepare(_sql)
      try {
        _stmt.step()
      } finally {
        _stmt.close()
      }
    }
  }

  public companion object {
    public fun getRequiredConverters(): List<KClass<*>> = emptyList()
  }
}
