package app.apti.villa.`data`.local.dao

import androidx.room.EntityInsertAdapter
import androidx.room.RoomDatabase
import androidx.room.coroutines.createFlow
import androidx.room.util.getColumnIndexOrThrow
import androidx.room.util.performSuspending
import androidx.sqlite.SQLiteStatement
import app.apti.villa.`data`.local.entity.QuestionOptionEntity
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
public class QuestionOptionDao_Impl(
  __db: RoomDatabase,
) : QuestionOptionDao {
  private val __db: RoomDatabase

  private val __insertAdapterOfQuestionOptionEntity: EntityInsertAdapter<QuestionOptionEntity>
  init {
    this.__db = __db
    this.__insertAdapterOfQuestionOptionEntity = object :
        EntityInsertAdapter<QuestionOptionEntity>() {
      protected override fun createQuery(): String =
          "INSERT OR REPLACE INTO `question_option` (`id`,`question_id`,`position`,`text`,`is_correct`) VALUES (nullif(?, 0),?,?,?,?)"

      protected override fun bind(statement: SQLiteStatement, entity: QuestionOptionEntity) {
        statement.bindLong(1, entity.id)
        statement.bindLong(2, entity.questionId)
        statement.bindLong(3, entity.position.toLong())
        statement.bindText(4, entity.text)
        val _tmp: Int = if (entity.isCorrect) 1 else 0
        statement.bindLong(5, _tmp.toLong())
      }
    }
  }

  public override suspend fun insert(option: QuestionOptionEntity): Long = performSuspending(__db,
      false, true) { _connection ->
    val _result: Long = __insertAdapterOfQuestionOptionEntity.insertAndReturnId(_connection, option)
    _result
  }

  public override suspend fun insertAll(options: List<QuestionOptionEntity>): Unit =
      performSuspending(__db, false, true) { _connection ->
    __insertAdapterOfQuestionOptionEntity.insert(_connection, options)
  }

  public override fun getOptionsForQuestion(questionId: Long): Flow<List<QuestionOptionEntity>> {
    val _sql: String = "SELECT * FROM question_option WHERE question_id = ? ORDER BY position ASC"
    return createFlow(__db, false, arrayOf("question_option")) { _connection ->
      val _stmt: SQLiteStatement = _connection.prepare(_sql)
      try {
        var _argIndex: Int = 1
        _stmt.bindLong(_argIndex, questionId)
        val _columnIndexOfId: Int = getColumnIndexOrThrow(_stmt, "id")
        val _columnIndexOfQuestionId: Int = getColumnIndexOrThrow(_stmt, "question_id")
        val _columnIndexOfPosition: Int = getColumnIndexOrThrow(_stmt, "position")
        val _columnIndexOfText: Int = getColumnIndexOrThrow(_stmt, "text")
        val _columnIndexOfIsCorrect: Int = getColumnIndexOrThrow(_stmt, "is_correct")
        val _result: MutableList<QuestionOptionEntity> = mutableListOf()
        while (_stmt.step()) {
          val _item: QuestionOptionEntity
          val _tmpId: Long
          _tmpId = _stmt.getLong(_columnIndexOfId)
          val _tmpQuestionId: Long
          _tmpQuestionId = _stmt.getLong(_columnIndexOfQuestionId)
          val _tmpPosition: Int
          _tmpPosition = _stmt.getLong(_columnIndexOfPosition).toInt()
          val _tmpText: String
          _tmpText = _stmt.getText(_columnIndexOfText)
          val _tmpIsCorrect: Boolean
          val _tmp: Int
          _tmp = _stmt.getLong(_columnIndexOfIsCorrect).toInt()
          _tmpIsCorrect = _tmp != 0
          _item = QuestionOptionEntity(_tmpId,_tmpQuestionId,_tmpPosition,_tmpText,_tmpIsCorrect)
          _result.add(_item)
        }
        _result
      } finally {
        _stmt.close()
      }
    }
  }

  public override suspend fun getOptionsForQuestionOnce(questionId: Long):
      List<QuestionOptionEntity> {
    val _sql: String = "SELECT * FROM question_option WHERE question_id = ? ORDER BY position ASC"
    return performSuspending(__db, true, false) { _connection ->
      val _stmt: SQLiteStatement = _connection.prepare(_sql)
      try {
        var _argIndex: Int = 1
        _stmt.bindLong(_argIndex, questionId)
        val _columnIndexOfId: Int = getColumnIndexOrThrow(_stmt, "id")
        val _columnIndexOfQuestionId: Int = getColumnIndexOrThrow(_stmt, "question_id")
        val _columnIndexOfPosition: Int = getColumnIndexOrThrow(_stmt, "position")
        val _columnIndexOfText: Int = getColumnIndexOrThrow(_stmt, "text")
        val _columnIndexOfIsCorrect: Int = getColumnIndexOrThrow(_stmt, "is_correct")
        val _result: MutableList<QuestionOptionEntity> = mutableListOf()
        while (_stmt.step()) {
          val _item: QuestionOptionEntity
          val _tmpId: Long
          _tmpId = _stmt.getLong(_columnIndexOfId)
          val _tmpQuestionId: Long
          _tmpQuestionId = _stmt.getLong(_columnIndexOfQuestionId)
          val _tmpPosition: Int
          _tmpPosition = _stmt.getLong(_columnIndexOfPosition).toInt()
          val _tmpText: String
          _tmpText = _stmt.getText(_columnIndexOfText)
          val _tmpIsCorrect: Boolean
          val _tmp: Int
          _tmp = _stmt.getLong(_columnIndexOfIsCorrect).toInt()
          _tmpIsCorrect = _tmp != 0
          _item = QuestionOptionEntity(_tmpId,_tmpQuestionId,_tmpPosition,_tmpText,_tmpIsCorrect)
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
