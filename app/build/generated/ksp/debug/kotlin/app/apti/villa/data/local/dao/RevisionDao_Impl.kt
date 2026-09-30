package app.apti.villa.`data`.local.dao

import androidx.room.EntityInsertAdapter
import androidx.room.RoomDatabase
import androidx.room.coroutines.createFlow
import androidx.room.util.getColumnIndexOrThrow
import androidx.room.util.performSuspending
import androidx.sqlite.SQLiteStatement
import app.apti.villa.`data`.local.entity.RevisionItemEntity
import app.apti.villa.`data`.local.entity.RevisionReasonEntity
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
public class RevisionDao_Impl(
  __db: RoomDatabase,
) : RevisionDao {
  private val __db: RoomDatabase

  private val __insertAdapterOfRevisionItemEntity: EntityInsertAdapter<RevisionItemEntity>

  private val __insertAdapterOfRevisionReasonEntity: EntityInsertAdapter<RevisionReasonEntity>
  init {
    this.__db = __db
    this.__insertAdapterOfRevisionItemEntity = object : EntityInsertAdapter<RevisionItemEntity>() {
      protected override fun createQuery(): String =
          "INSERT OR REPLACE INTO `revision_item` (`question_id`,`box`,`due_at`,`last_reviewed_at`,`review_count`,`suspended`) VALUES (?,?,?,?,?,?)"

      protected override fun bind(statement: SQLiteStatement, entity: RevisionItemEntity) {
        statement.bindLong(1, entity.questionId)
        statement.bindLong(2, entity.box.toLong())
        statement.bindLong(3, entity.dueAt)
        val _tmpLastReviewedAt: Long? = entity.lastReviewedAt
        if (_tmpLastReviewedAt == null) {
          statement.bindNull(4)
        } else {
          statement.bindLong(4, _tmpLastReviewedAt)
        }
        statement.bindLong(5, entity.reviewCount.toLong())
        val _tmp: Int = if (entity.suspended) 1 else 0
        statement.bindLong(6, _tmp.toLong())
      }
    }
    this.__insertAdapterOfRevisionReasonEntity = object :
        EntityInsertAdapter<RevisionReasonEntity>() {
      protected override fun createQuery(): String =
          "INSERT OR IGNORE INTO `revision_reason` (`question_id`,`reason`,`created_at`) VALUES (?,?,?)"

      protected override fun bind(statement: SQLiteStatement, entity: RevisionReasonEntity) {
        statement.bindLong(1, entity.questionId)
        statement.bindText(2, entity.reason)
        statement.bindLong(3, entity.createdAt)
      }
    }
  }

  public override suspend fun upsertItem(item: RevisionItemEntity): Unit = performSuspending(__db,
      false, true) { _connection ->
    __insertAdapterOfRevisionItemEntity.insert(_connection, item)
  }

  public override suspend fun insertReason(reason: RevisionReasonEntity): Unit =
      performSuspending(__db, false, true) { _connection ->
    __insertAdapterOfRevisionReasonEntity.insert(_connection, reason)
  }

  public override suspend fun getItemByQuestionId(questionId: Long): RevisionItemEntity? {
    val _sql: String = "SELECT * FROM revision_item WHERE question_id = ? LIMIT 1"
    return performSuspending(__db, true, false) { _connection ->
      val _stmt: SQLiteStatement = _connection.prepare(_sql)
      try {
        var _argIndex: Int = 1
        _stmt.bindLong(_argIndex, questionId)
        val _columnIndexOfQuestionId: Int = getColumnIndexOrThrow(_stmt, "question_id")
        val _columnIndexOfBox: Int = getColumnIndexOrThrow(_stmt, "box")
        val _columnIndexOfDueAt: Int = getColumnIndexOrThrow(_stmt, "due_at")
        val _columnIndexOfLastReviewedAt: Int = getColumnIndexOrThrow(_stmt, "last_reviewed_at")
        val _columnIndexOfReviewCount: Int = getColumnIndexOrThrow(_stmt, "review_count")
        val _columnIndexOfSuspended: Int = getColumnIndexOrThrow(_stmt, "suspended")
        val _result: RevisionItemEntity?
        if (_stmt.step()) {
          val _tmpQuestionId: Long
          _tmpQuestionId = _stmt.getLong(_columnIndexOfQuestionId)
          val _tmpBox: Int
          _tmpBox = _stmt.getLong(_columnIndexOfBox).toInt()
          val _tmpDueAt: Long
          _tmpDueAt = _stmt.getLong(_columnIndexOfDueAt)
          val _tmpLastReviewedAt: Long?
          if (_stmt.isNull(_columnIndexOfLastReviewedAt)) {
            _tmpLastReviewedAt = null
          } else {
            _tmpLastReviewedAt = _stmt.getLong(_columnIndexOfLastReviewedAt)
          }
          val _tmpReviewCount: Int
          _tmpReviewCount = _stmt.getLong(_columnIndexOfReviewCount).toInt()
          val _tmpSuspended: Boolean
          val _tmp: Int
          _tmp = _stmt.getLong(_columnIndexOfSuspended).toInt()
          _tmpSuspended = _tmp != 0
          _result =
              RevisionItemEntity(_tmpQuestionId,_tmpBox,_tmpDueAt,_tmpLastReviewedAt,_tmpReviewCount,_tmpSuspended)
        } else {
          _result = null
        }
        _result
      } finally {
        _stmt.close()
      }
    }
  }

  public override fun getDueItems(dueBefore: Long): Flow<List<RevisionItemEntity>> {
    val _sql: String =
        "SELECT * FROM revision_item WHERE suspended = 0 AND due_at <= ? ORDER BY due_at ASC"
    return createFlow(__db, false, arrayOf("revision_item")) { _connection ->
      val _stmt: SQLiteStatement = _connection.prepare(_sql)
      try {
        var _argIndex: Int = 1
        _stmt.bindLong(_argIndex, dueBefore)
        val _columnIndexOfQuestionId: Int = getColumnIndexOrThrow(_stmt, "question_id")
        val _columnIndexOfBox: Int = getColumnIndexOrThrow(_stmt, "box")
        val _columnIndexOfDueAt: Int = getColumnIndexOrThrow(_stmt, "due_at")
        val _columnIndexOfLastReviewedAt: Int = getColumnIndexOrThrow(_stmt, "last_reviewed_at")
        val _columnIndexOfReviewCount: Int = getColumnIndexOrThrow(_stmt, "review_count")
        val _columnIndexOfSuspended: Int = getColumnIndexOrThrow(_stmt, "suspended")
        val _result: MutableList<RevisionItemEntity> = mutableListOf()
        while (_stmt.step()) {
          val _item: RevisionItemEntity
          val _tmpQuestionId: Long
          _tmpQuestionId = _stmt.getLong(_columnIndexOfQuestionId)
          val _tmpBox: Int
          _tmpBox = _stmt.getLong(_columnIndexOfBox).toInt()
          val _tmpDueAt: Long
          _tmpDueAt = _stmt.getLong(_columnIndexOfDueAt)
          val _tmpLastReviewedAt: Long?
          if (_stmt.isNull(_columnIndexOfLastReviewedAt)) {
            _tmpLastReviewedAt = null
          } else {
            _tmpLastReviewedAt = _stmt.getLong(_columnIndexOfLastReviewedAt)
          }
          val _tmpReviewCount: Int
          _tmpReviewCount = _stmt.getLong(_columnIndexOfReviewCount).toInt()
          val _tmpSuspended: Boolean
          val _tmp: Int
          _tmp = _stmt.getLong(_columnIndexOfSuspended).toInt()
          _tmpSuspended = _tmp != 0
          _item =
              RevisionItemEntity(_tmpQuestionId,_tmpBox,_tmpDueAt,_tmpLastReviewedAt,_tmpReviewCount,_tmpSuspended)
          _result.add(_item)
        }
        _result
      } finally {
        _stmt.close()
      }
    }
  }

  public override suspend fun getDueItemsOnce(dueBefore: Long): List<RevisionItemEntity> {
    val _sql: String =
        "SELECT * FROM revision_item WHERE suspended = 0 AND due_at <= ? ORDER BY due_at ASC"
    return performSuspending(__db, true, false) { _connection ->
      val _stmt: SQLiteStatement = _connection.prepare(_sql)
      try {
        var _argIndex: Int = 1
        _stmt.bindLong(_argIndex, dueBefore)
        val _columnIndexOfQuestionId: Int = getColumnIndexOrThrow(_stmt, "question_id")
        val _columnIndexOfBox: Int = getColumnIndexOrThrow(_stmt, "box")
        val _columnIndexOfDueAt: Int = getColumnIndexOrThrow(_stmt, "due_at")
        val _columnIndexOfLastReviewedAt: Int = getColumnIndexOrThrow(_stmt, "last_reviewed_at")
        val _columnIndexOfReviewCount: Int = getColumnIndexOrThrow(_stmt, "review_count")
        val _columnIndexOfSuspended: Int = getColumnIndexOrThrow(_stmt, "suspended")
        val _result: MutableList<RevisionItemEntity> = mutableListOf()
        while (_stmt.step()) {
          val _item: RevisionItemEntity
          val _tmpQuestionId: Long
          _tmpQuestionId = _stmt.getLong(_columnIndexOfQuestionId)
          val _tmpBox: Int
          _tmpBox = _stmt.getLong(_columnIndexOfBox).toInt()
          val _tmpDueAt: Long
          _tmpDueAt = _stmt.getLong(_columnIndexOfDueAt)
          val _tmpLastReviewedAt: Long?
          if (_stmt.isNull(_columnIndexOfLastReviewedAt)) {
            _tmpLastReviewedAt = null
          } else {
            _tmpLastReviewedAt = _stmt.getLong(_columnIndexOfLastReviewedAt)
          }
          val _tmpReviewCount: Int
          _tmpReviewCount = _stmt.getLong(_columnIndexOfReviewCount).toInt()
          val _tmpSuspended: Boolean
          val _tmp: Int
          _tmp = _stmt.getLong(_columnIndexOfSuspended).toInt()
          _tmpSuspended = _tmp != 0
          _item =
              RevisionItemEntity(_tmpQuestionId,_tmpBox,_tmpDueAt,_tmpLastReviewedAt,_tmpReviewCount,_tmpSuspended)
          _result.add(_item)
        }
        _result
      } finally {
        _stmt.close()
      }
    }
  }

  public override fun countDueItems(dueBefore: Long): Flow<Int> {
    val _sql: String = "SELECT COUNT(*) FROM revision_item WHERE suspended = 0 AND due_at <= ?"
    return createFlow(__db, false, arrayOf("revision_item")) { _connection ->
      val _stmt: SQLiteStatement = _connection.prepare(_sql)
      try {
        var _argIndex: Int = 1
        _stmt.bindLong(_argIndex, dueBefore)
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

  public override fun countItemsInBox(box: Int): Flow<Int> {
    val _sql: String = "SELECT COUNT(*) FROM revision_item WHERE box = ? AND suspended = 0"
    return createFlow(__db, false, arrayOf("revision_item")) { _connection ->
      val _stmt: SQLiteStatement = _connection.prepare(_sql)
      try {
        var _argIndex: Int = 1
        _stmt.bindLong(_argIndex, box.toLong())
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

  public override suspend fun countItemsInBoxOnce(box: Int): Int {
    val _sql: String = "SELECT COUNT(*) FROM revision_item WHERE box = ? AND suspended = 0"
    return performSuspending(__db, true, false) { _connection ->
      val _stmt: SQLiteStatement = _connection.prepare(_sql)
      try {
        var _argIndex: Int = 1
        _stmt.bindLong(_argIndex, box.toLong())
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

  public override fun getAllItems(): Flow<List<RevisionItemEntity>> {
    val _sql: String = "SELECT * FROM revision_item ORDER BY due_at ASC"
    return createFlow(__db, false, arrayOf("revision_item")) { _connection ->
      val _stmt: SQLiteStatement = _connection.prepare(_sql)
      try {
        val _columnIndexOfQuestionId: Int = getColumnIndexOrThrow(_stmt, "question_id")
        val _columnIndexOfBox: Int = getColumnIndexOrThrow(_stmt, "box")
        val _columnIndexOfDueAt: Int = getColumnIndexOrThrow(_stmt, "due_at")
        val _columnIndexOfLastReviewedAt: Int = getColumnIndexOrThrow(_stmt, "last_reviewed_at")
        val _columnIndexOfReviewCount: Int = getColumnIndexOrThrow(_stmt, "review_count")
        val _columnIndexOfSuspended: Int = getColumnIndexOrThrow(_stmt, "suspended")
        val _result: MutableList<RevisionItemEntity> = mutableListOf()
        while (_stmt.step()) {
          val _item: RevisionItemEntity
          val _tmpQuestionId: Long
          _tmpQuestionId = _stmt.getLong(_columnIndexOfQuestionId)
          val _tmpBox: Int
          _tmpBox = _stmt.getLong(_columnIndexOfBox).toInt()
          val _tmpDueAt: Long
          _tmpDueAt = _stmt.getLong(_columnIndexOfDueAt)
          val _tmpLastReviewedAt: Long?
          if (_stmt.isNull(_columnIndexOfLastReviewedAt)) {
            _tmpLastReviewedAt = null
          } else {
            _tmpLastReviewedAt = _stmt.getLong(_columnIndexOfLastReviewedAt)
          }
          val _tmpReviewCount: Int
          _tmpReviewCount = _stmt.getLong(_columnIndexOfReviewCount).toInt()
          val _tmpSuspended: Boolean
          val _tmp: Int
          _tmp = _stmt.getLong(_columnIndexOfSuspended).toInt()
          _tmpSuspended = _tmp != 0
          _item =
              RevisionItemEntity(_tmpQuestionId,_tmpBox,_tmpDueAt,_tmpLastReviewedAt,_tmpReviewCount,_tmpSuspended)
          _result.add(_item)
        }
        _result
      } finally {
        _stmt.close()
      }
    }
  }

  public override suspend fun getReasonsForQuestion(questionId: Long): List<RevisionReasonEntity> {
    val _sql: String = "SELECT * FROM revision_reason WHERE question_id = ?"
    return performSuspending(__db, true, false) { _connection ->
      val _stmt: SQLiteStatement = _connection.prepare(_sql)
      try {
        var _argIndex: Int = 1
        _stmt.bindLong(_argIndex, questionId)
        val _columnIndexOfQuestionId: Int = getColumnIndexOrThrow(_stmt, "question_id")
        val _columnIndexOfReason: Int = getColumnIndexOrThrow(_stmt, "reason")
        val _columnIndexOfCreatedAt: Int = getColumnIndexOrThrow(_stmt, "created_at")
        val _result: MutableList<RevisionReasonEntity> = mutableListOf()
        while (_stmt.step()) {
          val _item: RevisionReasonEntity
          val _tmpQuestionId: Long
          _tmpQuestionId = _stmt.getLong(_columnIndexOfQuestionId)
          val _tmpReason: String
          _tmpReason = _stmt.getText(_columnIndexOfReason)
          val _tmpCreatedAt: Long
          _tmpCreatedAt = _stmt.getLong(_columnIndexOfCreatedAt)
          _item = RevisionReasonEntity(_tmpQuestionId,_tmpReason,_tmpCreatedAt)
          _result.add(_item)
        }
        _result
      } finally {
        _stmt.close()
      }
    }
  }

  public override suspend fun updateBoxAndDue(
    questionId: Long,
    box: Int,
    dueAt: Long,
    lastReviewedAt: Long,
  ) {
    val _sql: String = """
        |
        |        UPDATE revision_item 
        |        SET box = ?, due_at = ?, last_reviewed_at = ?, review_count = review_count + 1 
        |        WHERE question_id = ?
        |    
        """.trimMargin()
    return performSuspending(__db, false, true) { _connection ->
      val _stmt: SQLiteStatement = _connection.prepare(_sql)
      try {
        var _argIndex: Int = 1
        _stmt.bindLong(_argIndex, box.toLong())
        _argIndex = 2
        _stmt.bindLong(_argIndex, dueAt)
        _argIndex = 3
        _stmt.bindLong(_argIndex, lastReviewedAt)
        _argIndex = 4
        _stmt.bindLong(_argIndex, questionId)
        _stmt.step()
      } finally {
        _stmt.close()
      }
    }
  }

  public override suspend fun setSuspended(questionId: Long, suspended: Boolean) {
    val _sql: String = "UPDATE revision_item SET suspended = ? WHERE question_id = ?"
    return performSuspending(__db, false, true) { _connection ->
      val _stmt: SQLiteStatement = _connection.prepare(_sql)
      try {
        var _argIndex: Int = 1
        val _tmp: Int = if (suspended) 1 else 0
        _stmt.bindLong(_argIndex, _tmp.toLong())
        _argIndex = 2
        _stmt.bindLong(_argIndex, questionId)
        _stmt.step()
      } finally {
        _stmt.close()
      }
    }
  }

  public override suspend fun deleteItem(questionId: Long) {
    val _sql: String = "DELETE FROM revision_item WHERE question_id = ?"
    return performSuspending(__db, false, true) { _connection ->
      val _stmt: SQLiteStatement = _connection.prepare(_sql)
      try {
        var _argIndex: Int = 1
        _stmt.bindLong(_argIndex, questionId)
        _stmt.step()
      } finally {
        _stmt.close()
      }
    }
  }

  public override suspend fun deleteAllItems() {
    val _sql: String = "DELETE FROM revision_item"
    return performSuspending(__db, false, true) { _connection ->
      val _stmt: SQLiteStatement = _connection.prepare(_sql)
      try {
        _stmt.step()
      } finally {
        _stmt.close()
      }
    }
  }

  public override suspend fun deleteAllReasons() {
    val _sql: String = "DELETE FROM revision_reason"
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
