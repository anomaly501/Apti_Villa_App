package app.apti.villa.`data`.local.dao

import androidx.room.EntityInsertAdapter
import androidx.room.RoomDatabase
import androidx.room.coroutines.createFlow
import androidx.room.util.getColumnIndexOrThrow
import androidx.room.util.performSuspending
import androidx.sqlite.SQLiteStatement
import app.apti.villa.`data`.local.entity.QuestionTagEntity
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
public class QuestionTagDao_Impl(
  __db: RoomDatabase,
) : QuestionTagDao {
  private val __db: RoomDatabase

  private val __insertAdapterOfQuestionTagEntity: EntityInsertAdapter<QuestionTagEntity>
  init {
    this.__db = __db
    this.__insertAdapterOfQuestionTagEntity = object : EntityInsertAdapter<QuestionTagEntity>() {
      protected override fun createQuery(): String =
          "INSERT OR IGNORE INTO `question_tag` (`question_id`,`tag_id`,`created_at`) VALUES (?,?,?)"

      protected override fun bind(statement: SQLiteStatement, entity: QuestionTagEntity) {
        statement.bindLong(1, entity.questionId)
        statement.bindLong(2, entity.tagId)
        statement.bindLong(3, entity.createdAt)
      }
    }
  }

  public override suspend fun insert(questionTag: QuestionTagEntity): Unit = performSuspending(__db,
      false, true) { _connection ->
    __insertAdapterOfQuestionTagEntity.insert(_connection, questionTag)
  }

  public override fun getTagsForQuestion(questionId: Long): Flow<List<TagEntity>> {
    val _sql: String = """
        |
        |        SELECT t.* FROM tag t
        |        INNER JOIN question_tag qt ON qt.tag_id = t.id
        |        WHERE qt.question_id = ?
        |        ORDER BY t.is_system DESC, t.name ASC
        |    
        """.trimMargin()
    return createFlow(__db, false, arrayOf("tag", "question_tag")) { _connection ->
      val _stmt: SQLiteStatement = _connection.prepare(_sql)
      try {
        var _argIndex: Int = 1
        _stmt.bindLong(_argIndex, questionId)
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

  public override suspend fun getTagsForQuestionOnce(questionId: Long): List<TagEntity> {
    val _sql: String = """
        |
        |        SELECT t.* FROM tag t
        |        INNER JOIN question_tag qt ON qt.tag_id = t.id
        |        WHERE qt.question_id = ?
        |        ORDER BY t.is_system DESC, t.name ASC
        |    
        """.trimMargin()
    return performSuspending(__db, true, false) { _connection ->
      val _stmt: SQLiteStatement = _connection.prepare(_sql)
      try {
        var _argIndex: Int = 1
        _stmt.bindLong(_argIndex, questionId)
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

  public override fun getTagNamesForQuestion(questionId: Long): Flow<List<String>> {
    val _sql: String = """
        |
        |        SELECT t.name FROM tag t
        |        INNER JOIN question_tag qt ON qt.tag_id = t.id
        |        WHERE qt.question_id = ?
        |    
        """.trimMargin()
    return createFlow(__db, false, arrayOf("tag", "question_tag")) { _connection ->
      val _stmt: SQLiteStatement = _connection.prepare(_sql)
      try {
        var _argIndex: Int = 1
        _stmt.bindLong(_argIndex, questionId)
        val _result: MutableList<String> = mutableListOf()
        while (_stmt.step()) {
          val _item: String
          _item = _stmt.getText(0)
          _result.add(_item)
        }
        _result
      } finally {
        _stmt.close()
      }
    }
  }

  public override fun getQuestionIdsForTag(tagId: Long): Flow<List<Long>> {
    val _sql: String = "SELECT question_id FROM question_tag WHERE tag_id = ?"
    return createFlow(__db, false, arrayOf("question_tag")) { _connection ->
      val _stmt: SQLiteStatement = _connection.prepare(_sql)
      try {
        var _argIndex: Int = 1
        _stmt.bindLong(_argIndex, tagId)
        val _result: MutableList<Long> = mutableListOf()
        while (_stmt.step()) {
          val _item: Long
          _item = _stmt.getLong(0)
          _result.add(_item)
        }
        _result
      } finally {
        _stmt.close()
      }
    }
  }

  public override suspend fun hasTag(questionId: Long, tagId: Long): Boolean {
    val _sql: String = "SELECT COUNT(*) > 0 FROM question_tag WHERE question_id = ? AND tag_id = ?"
    return performSuspending(__db, true, false) { _connection ->
      val _stmt: SQLiteStatement = _connection.prepare(_sql)
      try {
        var _argIndex: Int = 1
        _stmt.bindLong(_argIndex, questionId)
        _argIndex = 2
        _stmt.bindLong(_argIndex, tagId)
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

  public override suspend fun delete(questionId: Long, tagId: Long) {
    val _sql: String = "DELETE FROM question_tag WHERE question_id = ? AND tag_id = ?"
    return performSuspending(__db, false, true) { _connection ->
      val _stmt: SQLiteStatement = _connection.prepare(_sql)
      try {
        var _argIndex: Int = 1
        _stmt.bindLong(_argIndex, questionId)
        _argIndex = 2
        _stmt.bindLong(_argIndex, tagId)
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
