package app.apti.villa.`data`.local.dao

import androidx.room.EntityInsertAdapter
import androidx.room.RoomDatabase
import androidx.room.coroutines.createFlow
import androidx.room.util.getColumnIndexOrThrow
import androidx.room.util.performSuspending
import androidx.sqlite.SQLiteStatement
import app.apti.villa.`data`.local.entity.TrickEntity
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
public class TrickDao_Impl(
  __db: RoomDatabase,
) : TrickDao {
  private val __db: RoomDatabase

  private val __insertAdapterOfTrickEntity: EntityInsertAdapter<TrickEntity>
  init {
    this.__db = __db
    this.__insertAdapterOfTrickEntity = object : EntityInsertAdapter<TrickEntity>() {
      protected override fun createQuery(): String =
          "INSERT OR REPLACE INTO `trick` (`id`,`question_id`,`name`,`description`) VALUES (nullif(?, 0),?,?,?)"

      protected override fun bind(statement: SQLiteStatement, entity: TrickEntity) {
        statement.bindLong(1, entity.id)
        statement.bindLong(2, entity.questionId)
        statement.bindText(3, entity.name)
        statement.bindText(4, entity.description)
      }
    }
  }

  public override suspend fun insertAll(tricks: List<TrickEntity>): Unit = performSuspending(__db,
      false, true) { _connection ->
    __insertAdapterOfTrickEntity.insert(_connection, tricks)
  }

  public override fun getTricksForQuestion(questionId: Long): Flow<List<TrickEntity>> {
    val _sql: String = "SELECT * FROM trick WHERE question_id = ? ORDER BY id ASC"
    return createFlow(__db, false, arrayOf("trick")) { _connection ->
      val _stmt: SQLiteStatement = _connection.prepare(_sql)
      try {
        var _argIndex: Int = 1
        _stmt.bindLong(_argIndex, questionId)
        val _columnIndexOfId: Int = getColumnIndexOrThrow(_stmt, "id")
        val _columnIndexOfQuestionId: Int = getColumnIndexOrThrow(_stmt, "question_id")
        val _columnIndexOfName: Int = getColumnIndexOrThrow(_stmt, "name")
        val _columnIndexOfDescription: Int = getColumnIndexOrThrow(_stmt, "description")
        val _result: MutableList<TrickEntity> = mutableListOf()
        while (_stmt.step()) {
          val _item: TrickEntity
          val _tmpId: Long
          _tmpId = _stmt.getLong(_columnIndexOfId)
          val _tmpQuestionId: Long
          _tmpQuestionId = _stmt.getLong(_columnIndexOfQuestionId)
          val _tmpName: String
          _tmpName = _stmt.getText(_columnIndexOfName)
          val _tmpDescription: String
          _tmpDescription = _stmt.getText(_columnIndexOfDescription)
          _item = TrickEntity(_tmpId,_tmpQuestionId,_tmpName,_tmpDescription)
          _result.add(_item)
        }
        _result
      } finally {
        _stmt.close()
      }
    }
  }

  public override suspend fun getTricksForQuestionOnce(questionId: Long): List<TrickEntity> {
    val _sql: String = "SELECT * FROM trick WHERE question_id = ? ORDER BY id ASC"
    return performSuspending(__db, true, false) { _connection ->
      val _stmt: SQLiteStatement = _connection.prepare(_sql)
      try {
        var _argIndex: Int = 1
        _stmt.bindLong(_argIndex, questionId)
        val _columnIndexOfId: Int = getColumnIndexOrThrow(_stmt, "id")
        val _columnIndexOfQuestionId: Int = getColumnIndexOrThrow(_stmt, "question_id")
        val _columnIndexOfName: Int = getColumnIndexOrThrow(_stmt, "name")
        val _columnIndexOfDescription: Int = getColumnIndexOrThrow(_stmt, "description")
        val _result: MutableList<TrickEntity> = mutableListOf()
        while (_stmt.step()) {
          val _item: TrickEntity
          val _tmpId: Long
          _tmpId = _stmt.getLong(_columnIndexOfId)
          val _tmpQuestionId: Long
          _tmpQuestionId = _stmt.getLong(_columnIndexOfQuestionId)
          val _tmpName: String
          _tmpName = _stmt.getText(_columnIndexOfName)
          val _tmpDescription: String
          _tmpDescription = _stmt.getText(_columnIndexOfDescription)
          _item = TrickEntity(_tmpId,_tmpQuestionId,_tmpName,_tmpDescription)
          _result.add(_item)
        }
        _result
      } finally {
        _stmt.close()
      }
    }
  }

  public override suspend fun getTrickById(id: Long): TrickEntity? {
    val _sql: String = "SELECT * FROM trick WHERE id = ? LIMIT 1"
    return performSuspending(__db, true, false) { _connection ->
      val _stmt: SQLiteStatement = _connection.prepare(_sql)
      try {
        var _argIndex: Int = 1
        _stmt.bindLong(_argIndex, id)
        val _columnIndexOfId: Int = getColumnIndexOrThrow(_stmt, "id")
        val _columnIndexOfQuestionId: Int = getColumnIndexOrThrow(_stmt, "question_id")
        val _columnIndexOfName: Int = getColumnIndexOrThrow(_stmt, "name")
        val _columnIndexOfDescription: Int = getColumnIndexOrThrow(_stmt, "description")
        val _result: TrickEntity?
        if (_stmt.step()) {
          val _tmpId: Long
          _tmpId = _stmt.getLong(_columnIndexOfId)
          val _tmpQuestionId: Long
          _tmpQuestionId = _stmt.getLong(_columnIndexOfQuestionId)
          val _tmpName: String
          _tmpName = _stmt.getText(_columnIndexOfName)
          val _tmpDescription: String
          _tmpDescription = _stmt.getText(_columnIndexOfDescription)
          _result = TrickEntity(_tmpId,_tmpQuestionId,_tmpName,_tmpDescription)
        } else {
          _result = null
        }
        _result
      } finally {
        _stmt.close()
      }
    }
  }

  public override fun getSavedTricks(): Flow<List<TrickEntity>> {
    val _sql: String = """
        |
        |        SELECT t.* FROM trick t
        |        INNER JOIN saved_trick st ON st.trick_id = t.id
        |        ORDER BY st.created_at DESC
        |    
        """.trimMargin()
    return createFlow(__db, false, arrayOf("trick", "saved_trick")) { _connection ->
      val _stmt: SQLiteStatement = _connection.prepare(_sql)
      try {
        val _columnIndexOfId: Int = getColumnIndexOrThrow(_stmt, "id")
        val _columnIndexOfQuestionId: Int = getColumnIndexOrThrow(_stmt, "question_id")
        val _columnIndexOfName: Int = getColumnIndexOrThrow(_stmt, "name")
        val _columnIndexOfDescription: Int = getColumnIndexOrThrow(_stmt, "description")
        val _result: MutableList<TrickEntity> = mutableListOf()
        while (_stmt.step()) {
          val _item: TrickEntity
          val _tmpId: Long
          _tmpId = _stmt.getLong(_columnIndexOfId)
          val _tmpQuestionId: Long
          _tmpQuestionId = _stmt.getLong(_columnIndexOfQuestionId)
          val _tmpName: String
          _tmpName = _stmt.getText(_columnIndexOfName)
          val _tmpDescription: String
          _tmpDescription = _stmt.getText(_columnIndexOfDescription)
          _item = TrickEntity(_tmpId,_tmpQuestionId,_tmpName,_tmpDescription)
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
