package app.apti.villa.`data`.local.dao

import androidx.room.EntityInsertAdapter
import androidx.room.RoomDatabase
import androidx.room.coroutines.createFlow
import androidx.room.util.getColumnIndexOrThrow
import androidx.room.util.performSuspending
import androidx.sqlite.SQLiteStatement
import app.apti.villa.`data`.local.entity.HintEntity
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
public class HintDao_Impl(
  __db: RoomDatabase,
) : HintDao {
  private val __db: RoomDatabase

  private val __insertAdapterOfHintEntity: EntityInsertAdapter<HintEntity>
  init {
    this.__db = __db
    this.__insertAdapterOfHintEntity = object : EntityInsertAdapter<HintEntity>() {
      protected override fun createQuery(): String =
          "INSERT OR REPLACE INTO `hint` (`id`,`question_id`,`number`,`title`,`description`) VALUES (nullif(?, 0),?,?,?,?)"

      protected override fun bind(statement: SQLiteStatement, entity: HintEntity) {
        statement.bindLong(1, entity.id)
        statement.bindLong(2, entity.questionId)
        statement.bindLong(3, entity.number.toLong())
        statement.bindText(4, entity.title)
        statement.bindText(5, entity.description)
      }
    }
  }

  public override suspend fun insertAll(hints: List<HintEntity>): Unit = performSuspending(__db,
      false, true) { _connection ->
    __insertAdapterOfHintEntity.insert(_connection, hints)
  }

  public override fun getHintsForQuestion(questionId: Long): Flow<List<HintEntity>> {
    val _sql: String = "SELECT * FROM hint WHERE question_id = ? ORDER BY number ASC"
    return createFlow(__db, false, arrayOf("hint")) { _connection ->
      val _stmt: SQLiteStatement = _connection.prepare(_sql)
      try {
        var _argIndex: Int = 1
        _stmt.bindLong(_argIndex, questionId)
        val _columnIndexOfId: Int = getColumnIndexOrThrow(_stmt, "id")
        val _columnIndexOfQuestionId: Int = getColumnIndexOrThrow(_stmt, "question_id")
        val _columnIndexOfNumber: Int = getColumnIndexOrThrow(_stmt, "number")
        val _columnIndexOfTitle: Int = getColumnIndexOrThrow(_stmt, "title")
        val _columnIndexOfDescription: Int = getColumnIndexOrThrow(_stmt, "description")
        val _result: MutableList<HintEntity> = mutableListOf()
        while (_stmt.step()) {
          val _item: HintEntity
          val _tmpId: Long
          _tmpId = _stmt.getLong(_columnIndexOfId)
          val _tmpQuestionId: Long
          _tmpQuestionId = _stmt.getLong(_columnIndexOfQuestionId)
          val _tmpNumber: Int
          _tmpNumber = _stmt.getLong(_columnIndexOfNumber).toInt()
          val _tmpTitle: String
          _tmpTitle = _stmt.getText(_columnIndexOfTitle)
          val _tmpDescription: String
          _tmpDescription = _stmt.getText(_columnIndexOfDescription)
          _item = HintEntity(_tmpId,_tmpQuestionId,_tmpNumber,_tmpTitle,_tmpDescription)
          _result.add(_item)
        }
        _result
      } finally {
        _stmt.close()
      }
    }
  }

  public override suspend fun getHintsForQuestionOnce(questionId: Long): List<HintEntity> {
    val _sql: String = "SELECT * FROM hint WHERE question_id = ? ORDER BY number ASC"
    return performSuspending(__db, true, false) { _connection ->
      val _stmt: SQLiteStatement = _connection.prepare(_sql)
      try {
        var _argIndex: Int = 1
        _stmt.bindLong(_argIndex, questionId)
        val _columnIndexOfId: Int = getColumnIndexOrThrow(_stmt, "id")
        val _columnIndexOfQuestionId: Int = getColumnIndexOrThrow(_stmt, "question_id")
        val _columnIndexOfNumber: Int = getColumnIndexOrThrow(_stmt, "number")
        val _columnIndexOfTitle: Int = getColumnIndexOrThrow(_stmt, "title")
        val _columnIndexOfDescription: Int = getColumnIndexOrThrow(_stmt, "description")
        val _result: MutableList<HintEntity> = mutableListOf()
        while (_stmt.step()) {
          val _item: HintEntity
          val _tmpId: Long
          _tmpId = _stmt.getLong(_columnIndexOfId)
          val _tmpQuestionId: Long
          _tmpQuestionId = _stmt.getLong(_columnIndexOfQuestionId)
          val _tmpNumber: Int
          _tmpNumber = _stmt.getLong(_columnIndexOfNumber).toInt()
          val _tmpTitle: String
          _tmpTitle = _stmt.getText(_columnIndexOfTitle)
          val _tmpDescription: String
          _tmpDescription = _stmt.getText(_columnIndexOfDescription)
          _item = HintEntity(_tmpId,_tmpQuestionId,_tmpNumber,_tmpTitle,_tmpDescription)
          _result.add(_item)
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
