package app.apti.villa.`data`.local.dao

import androidx.room.EntityInsertAdapter
import androidx.room.RoomDatabase
import androidx.room.coroutines.createFlow
import androidx.room.util.getColumnIndexOrThrow
import androidx.room.util.performSuspending
import androidx.sqlite.SQLiteStatement
import app.apti.villa.`data`.local.entity.HintUsageEntity
import javax.`annotation`.processing.Generated
import kotlin.Boolean
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
public class HintUsageDao_Impl(
  __db: RoomDatabase,
) : HintUsageDao {
  private val __db: RoomDatabase

  private val __insertAdapterOfHintUsageEntity: EntityInsertAdapter<HintUsageEntity>
  init {
    this.__db = __db
    this.__insertAdapterOfHintUsageEntity = object : EntityInsertAdapter<HintUsageEntity>() {
      protected override fun createQuery(): String =
          "INSERT OR IGNORE INTO `hint_usage` (`id`,`answer_id`,`hint_id`,`opened_at`,`ms_since_first_seen`) VALUES (nullif(?, 0),?,?,?,?)"

      protected override fun bind(statement: SQLiteStatement, entity: HintUsageEntity) {
        statement.bindLong(1, entity.id)
        statement.bindLong(2, entity.answerId)
        statement.bindLong(3, entity.hintId)
        statement.bindLong(4, entity.openedAt)
        statement.bindLong(5, entity.msSinceFirstSeen)
      }
    }
  }

  public override suspend fun insert(hintUsage: HintUsageEntity): Long = performSuspending(__db,
      false, true) { _connection ->
    val _result: Long = __insertAdapterOfHintUsageEntity.insertAndReturnId(_connection, hintUsage)
    _result
  }

  public override fun getUsagesForAnswer(answerId: Long): Flow<List<HintUsageEntity>> {
    val _sql: String = "SELECT * FROM hint_usage WHERE answer_id = ? ORDER BY opened_at ASC"
    return createFlow(__db, false, arrayOf("hint_usage")) { _connection ->
      val _stmt: SQLiteStatement = _connection.prepare(_sql)
      try {
        var _argIndex: Int = 1
        _stmt.bindLong(_argIndex, answerId)
        val _columnIndexOfId: Int = getColumnIndexOrThrow(_stmt, "id")
        val _columnIndexOfAnswerId: Int = getColumnIndexOrThrow(_stmt, "answer_id")
        val _columnIndexOfHintId: Int = getColumnIndexOrThrow(_stmt, "hint_id")
        val _columnIndexOfOpenedAt: Int = getColumnIndexOrThrow(_stmt, "opened_at")
        val _columnIndexOfMsSinceFirstSeen: Int = getColumnIndexOrThrow(_stmt,
            "ms_since_first_seen")
        val _result: MutableList<HintUsageEntity> = mutableListOf()
        while (_stmt.step()) {
          val _item: HintUsageEntity
          val _tmpId: Long
          _tmpId = _stmt.getLong(_columnIndexOfId)
          val _tmpAnswerId: Long
          _tmpAnswerId = _stmt.getLong(_columnIndexOfAnswerId)
          val _tmpHintId: Long
          _tmpHintId = _stmt.getLong(_columnIndexOfHintId)
          val _tmpOpenedAt: Long
          _tmpOpenedAt = _stmt.getLong(_columnIndexOfOpenedAt)
          val _tmpMsSinceFirstSeen: Long
          _tmpMsSinceFirstSeen = _stmt.getLong(_columnIndexOfMsSinceFirstSeen)
          _item = HintUsageEntity(_tmpId,_tmpAnswerId,_tmpHintId,_tmpOpenedAt,_tmpMsSinceFirstSeen)
          _result.add(_item)
        }
        _result
      } finally {
        _stmt.close()
      }
    }
  }

  public override suspend fun getUsagesForAnswerOnce(answerId: Long): List<HintUsageEntity> {
    val _sql: String = "SELECT * FROM hint_usage WHERE answer_id = ? ORDER BY opened_at ASC"
    return performSuspending(__db, true, false) { _connection ->
      val _stmt: SQLiteStatement = _connection.prepare(_sql)
      try {
        var _argIndex: Int = 1
        _stmt.bindLong(_argIndex, answerId)
        val _columnIndexOfId: Int = getColumnIndexOrThrow(_stmt, "id")
        val _columnIndexOfAnswerId: Int = getColumnIndexOrThrow(_stmt, "answer_id")
        val _columnIndexOfHintId: Int = getColumnIndexOrThrow(_stmt, "hint_id")
        val _columnIndexOfOpenedAt: Int = getColumnIndexOrThrow(_stmt, "opened_at")
        val _columnIndexOfMsSinceFirstSeen: Int = getColumnIndexOrThrow(_stmt,
            "ms_since_first_seen")
        val _result: MutableList<HintUsageEntity> = mutableListOf()
        while (_stmt.step()) {
          val _item: HintUsageEntity
          val _tmpId: Long
          _tmpId = _stmt.getLong(_columnIndexOfId)
          val _tmpAnswerId: Long
          _tmpAnswerId = _stmt.getLong(_columnIndexOfAnswerId)
          val _tmpHintId: Long
          _tmpHintId = _stmt.getLong(_columnIndexOfHintId)
          val _tmpOpenedAt: Long
          _tmpOpenedAt = _stmt.getLong(_columnIndexOfOpenedAt)
          val _tmpMsSinceFirstSeen: Long
          _tmpMsSinceFirstSeen = _stmt.getLong(_columnIndexOfMsSinceFirstSeen)
          _item = HintUsageEntity(_tmpId,_tmpAnswerId,_tmpHintId,_tmpOpenedAt,_tmpMsSinceFirstSeen)
          _result.add(_item)
        }
        _result
      } finally {
        _stmt.close()
      }
    }
  }

  public override suspend fun isHintUsed(answerId: Long, hintId: Long): Boolean {
    val _sql: String = "SELECT COUNT(*) > 0 FROM hint_usage WHERE answer_id = ? AND hint_id = ?"
    return performSuspending(__db, true, false) { _connection ->
      val _stmt: SQLiteStatement = _connection.prepare(_sql)
      try {
        var _argIndex: Int = 1
        _stmt.bindLong(_argIndex, answerId)
        _argIndex = 2
        _stmt.bindLong(_argIndex, hintId)
        val _result: Boolean
        if (_stmt.step()) {
          val _tmp: Int
          _tmp = _stmt.getLong(0).toInt()
          _result = _tmp != 0
        } else {
          _result = false
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
