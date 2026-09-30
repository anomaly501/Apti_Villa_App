package app.apti.villa.`data`.local.dao

import androidx.room.EntityInsertAdapter
import androidx.room.RoomDatabase
import androidx.room.coroutines.createFlow
import androidx.room.util.getColumnIndexOrThrow
import androidx.room.util.performSuspending
import androidx.sqlite.SQLiteStatement
import app.apti.villa.`data`.local.entity.MistakeEntity
import javax.`annotation`.processing.Generated
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
public class MistakeDao_Impl(
  __db: RoomDatabase,
) : MistakeDao {
  private val __db: RoomDatabase

  private val __insertAdapterOfMistakeEntity: EntityInsertAdapter<MistakeEntity>
  init {
    this.__db = __db
    this.__insertAdapterOfMistakeEntity = object : EntityInsertAdapter<MistakeEntity>() {
      protected override fun createQuery(): String =
          "INSERT OR REPLACE INTO `mistake` (`question_id`,`first_wrong_at`,`last_wrong_at`,`wrong_count`,`consecutive_correct`,`status`) VALUES (?,?,?,?,?,?)"

      protected override fun bind(statement: SQLiteStatement, entity: MistakeEntity) {
        statement.bindLong(1, entity.questionId)
        statement.bindLong(2, entity.firstWrongAt)
        statement.bindLong(3, entity.lastWrongAt)
        statement.bindLong(4, entity.wrongCount.toLong())
        statement.bindLong(5, entity.consecutiveCorrect.toLong())
        statement.bindText(6, entity.status)
      }
    }
  }

  public override suspend fun upsert(mistake: MistakeEntity): Unit = performSuspending(__db, false,
      true) { _connection ->
    __insertAdapterOfMistakeEntity.insert(_connection, mistake)
  }

  public override suspend fun getMistakeByQuestionId(questionId: Long): MistakeEntity? {
    val _sql: String = "SELECT * FROM mistake WHERE question_id = ? LIMIT 1"
    return performSuspending(__db, true, false) { _connection ->
      val _stmt: SQLiteStatement = _connection.prepare(_sql)
      try {
        var _argIndex: Int = 1
        _stmt.bindLong(_argIndex, questionId)
        val _columnIndexOfQuestionId: Int = getColumnIndexOrThrow(_stmt, "question_id")
        val _columnIndexOfFirstWrongAt: Int = getColumnIndexOrThrow(_stmt, "first_wrong_at")
        val _columnIndexOfLastWrongAt: Int = getColumnIndexOrThrow(_stmt, "last_wrong_at")
        val _columnIndexOfWrongCount: Int = getColumnIndexOrThrow(_stmt, "wrong_count")
        val _columnIndexOfConsecutiveCorrect: Int = getColumnIndexOrThrow(_stmt,
            "consecutive_correct")
        val _columnIndexOfStatus: Int = getColumnIndexOrThrow(_stmt, "status")
        val _result: MistakeEntity?
        if (_stmt.step()) {
          val _tmpQuestionId: Long
          _tmpQuestionId = _stmt.getLong(_columnIndexOfQuestionId)
          val _tmpFirstWrongAt: Long
          _tmpFirstWrongAt = _stmt.getLong(_columnIndexOfFirstWrongAt)
          val _tmpLastWrongAt: Long
          _tmpLastWrongAt = _stmt.getLong(_columnIndexOfLastWrongAt)
          val _tmpWrongCount: Int
          _tmpWrongCount = _stmt.getLong(_columnIndexOfWrongCount).toInt()
          val _tmpConsecutiveCorrect: Int
          _tmpConsecutiveCorrect = _stmt.getLong(_columnIndexOfConsecutiveCorrect).toInt()
          val _tmpStatus: String
          _tmpStatus = _stmt.getText(_columnIndexOfStatus)
          _result =
              MistakeEntity(_tmpQuestionId,_tmpFirstWrongAt,_tmpLastWrongAt,_tmpWrongCount,_tmpConsecutiveCorrect,_tmpStatus)
        } else {
          _result = null
        }
        _result
      } finally {
        _stmt.close()
      }
    }
  }

  public override fun getActiveMistakes(): Flow<List<MistakeEntity>> {
    val _sql: String = "SELECT * FROM mistake WHERE status = 'ACTIVE' ORDER BY last_wrong_at DESC"
    return createFlow(__db, false, arrayOf("mistake")) { _connection ->
      val _stmt: SQLiteStatement = _connection.prepare(_sql)
      try {
        val _columnIndexOfQuestionId: Int = getColumnIndexOrThrow(_stmt, "question_id")
        val _columnIndexOfFirstWrongAt: Int = getColumnIndexOrThrow(_stmt, "first_wrong_at")
        val _columnIndexOfLastWrongAt: Int = getColumnIndexOrThrow(_stmt, "last_wrong_at")
        val _columnIndexOfWrongCount: Int = getColumnIndexOrThrow(_stmt, "wrong_count")
        val _columnIndexOfConsecutiveCorrect: Int = getColumnIndexOrThrow(_stmt,
            "consecutive_correct")
        val _columnIndexOfStatus: Int = getColumnIndexOrThrow(_stmt, "status")
        val _result: MutableList<MistakeEntity> = mutableListOf()
        while (_stmt.step()) {
          val _item: MistakeEntity
          val _tmpQuestionId: Long
          _tmpQuestionId = _stmt.getLong(_columnIndexOfQuestionId)
          val _tmpFirstWrongAt: Long
          _tmpFirstWrongAt = _stmt.getLong(_columnIndexOfFirstWrongAt)
          val _tmpLastWrongAt: Long
          _tmpLastWrongAt = _stmt.getLong(_columnIndexOfLastWrongAt)
          val _tmpWrongCount: Int
          _tmpWrongCount = _stmt.getLong(_columnIndexOfWrongCount).toInt()
          val _tmpConsecutiveCorrect: Int
          _tmpConsecutiveCorrect = _stmt.getLong(_columnIndexOfConsecutiveCorrect).toInt()
          val _tmpStatus: String
          _tmpStatus = _stmt.getText(_columnIndexOfStatus)
          _item =
              MistakeEntity(_tmpQuestionId,_tmpFirstWrongAt,_tmpLastWrongAt,_tmpWrongCount,_tmpConsecutiveCorrect,_tmpStatus)
          _result.add(_item)
        }
        _result
      } finally {
        _stmt.close()
      }
    }
  }

  public override suspend fun getActiveMistakesOnce(): List<MistakeEntity> {
    val _sql: String = "SELECT * FROM mistake WHERE status = 'ACTIVE' ORDER BY last_wrong_at DESC"
    return performSuspending(__db, true, false) { _connection ->
      val _stmt: SQLiteStatement = _connection.prepare(_sql)
      try {
        val _columnIndexOfQuestionId: Int = getColumnIndexOrThrow(_stmt, "question_id")
        val _columnIndexOfFirstWrongAt: Int = getColumnIndexOrThrow(_stmt, "first_wrong_at")
        val _columnIndexOfLastWrongAt: Int = getColumnIndexOrThrow(_stmt, "last_wrong_at")
        val _columnIndexOfWrongCount: Int = getColumnIndexOrThrow(_stmt, "wrong_count")
        val _columnIndexOfConsecutiveCorrect: Int = getColumnIndexOrThrow(_stmt,
            "consecutive_correct")
        val _columnIndexOfStatus: Int = getColumnIndexOrThrow(_stmt, "status")
        val _result: MutableList<MistakeEntity> = mutableListOf()
        while (_stmt.step()) {
          val _item: MistakeEntity
          val _tmpQuestionId: Long
          _tmpQuestionId = _stmt.getLong(_columnIndexOfQuestionId)
          val _tmpFirstWrongAt: Long
          _tmpFirstWrongAt = _stmt.getLong(_columnIndexOfFirstWrongAt)
          val _tmpLastWrongAt: Long
          _tmpLastWrongAt = _stmt.getLong(_columnIndexOfLastWrongAt)
          val _tmpWrongCount: Int
          _tmpWrongCount = _stmt.getLong(_columnIndexOfWrongCount).toInt()
          val _tmpConsecutiveCorrect: Int
          _tmpConsecutiveCorrect = _stmt.getLong(_columnIndexOfConsecutiveCorrect).toInt()
          val _tmpStatus: String
          _tmpStatus = _stmt.getText(_columnIndexOfStatus)
          _item =
              MistakeEntity(_tmpQuestionId,_tmpFirstWrongAt,_tmpLastWrongAt,_tmpWrongCount,_tmpConsecutiveCorrect,_tmpStatus)
          _result.add(_item)
        }
        _result
      } finally {
        _stmt.close()
      }
    }
  }

  public override fun getResolvedMistakes(): Flow<List<MistakeEntity>> {
    val _sql: String = "SELECT * FROM mistake WHERE status = 'RESOLVED' ORDER BY last_wrong_at DESC"
    return createFlow(__db, false, arrayOf("mistake")) { _connection ->
      val _stmt: SQLiteStatement = _connection.prepare(_sql)
      try {
        val _columnIndexOfQuestionId: Int = getColumnIndexOrThrow(_stmt, "question_id")
        val _columnIndexOfFirstWrongAt: Int = getColumnIndexOrThrow(_stmt, "first_wrong_at")
        val _columnIndexOfLastWrongAt: Int = getColumnIndexOrThrow(_stmt, "last_wrong_at")
        val _columnIndexOfWrongCount: Int = getColumnIndexOrThrow(_stmt, "wrong_count")
        val _columnIndexOfConsecutiveCorrect: Int = getColumnIndexOrThrow(_stmt,
            "consecutive_correct")
        val _columnIndexOfStatus: Int = getColumnIndexOrThrow(_stmt, "status")
        val _result: MutableList<MistakeEntity> = mutableListOf()
        while (_stmt.step()) {
          val _item: MistakeEntity
          val _tmpQuestionId: Long
          _tmpQuestionId = _stmt.getLong(_columnIndexOfQuestionId)
          val _tmpFirstWrongAt: Long
          _tmpFirstWrongAt = _stmt.getLong(_columnIndexOfFirstWrongAt)
          val _tmpLastWrongAt: Long
          _tmpLastWrongAt = _stmt.getLong(_columnIndexOfLastWrongAt)
          val _tmpWrongCount: Int
          _tmpWrongCount = _stmt.getLong(_columnIndexOfWrongCount).toInt()
          val _tmpConsecutiveCorrect: Int
          _tmpConsecutiveCorrect = _stmt.getLong(_columnIndexOfConsecutiveCorrect).toInt()
          val _tmpStatus: String
          _tmpStatus = _stmt.getText(_columnIndexOfStatus)
          _item =
              MistakeEntity(_tmpQuestionId,_tmpFirstWrongAt,_tmpLastWrongAt,_tmpWrongCount,_tmpConsecutiveCorrect,_tmpStatus)
          _result.add(_item)
        }
        _result
      } finally {
        _stmt.close()
      }
    }
  }

  public override fun countActiveMistakes(): Flow<Int> {
    val _sql: String = "SELECT COUNT(*) FROM mistake WHERE status = 'ACTIVE'"
    return createFlow(__db, false, arrayOf("mistake")) { _connection ->
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

  public override fun countResolvedMistakes(): Flow<Int> {
    val _sql: String = "SELECT COUNT(*) FROM mistake WHERE status = 'RESOLVED'"
    return createFlow(__db, false, arrayOf("mistake")) { _connection ->
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

  public override suspend fun updateStatus(questionId: Long, status: String) {
    val _sql: String = "UPDATE mistake SET status = ? WHERE question_id = ?"
    return performSuspending(__db, false, true) { _connection ->
      val _stmt: SQLiteStatement = _connection.prepare(_sql)
      try {
        var _argIndex: Int = 1
        _stmt.bindText(_argIndex, status)
        _argIndex = 2
        _stmt.bindLong(_argIndex, questionId)
        _stmt.step()
      } finally {
        _stmt.close()
      }
    }
  }

  public override suspend fun delete(questionId: Long) {
    val _sql: String = "DELETE FROM mistake WHERE question_id = ?"
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

  public override suspend fun deleteAll() {
    val _sql: String = "DELETE FROM mistake"
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
