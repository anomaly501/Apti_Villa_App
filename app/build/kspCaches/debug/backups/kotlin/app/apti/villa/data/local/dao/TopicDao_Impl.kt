package app.apti.villa.`data`.local.dao

import androidx.room.EntityInsertAdapter
import androidx.room.RoomDatabase
import androidx.room.coroutines.createFlow
import androidx.room.util.getColumnIndexOrThrow
import androidx.room.util.performSuspending
import androidx.sqlite.SQLiteStatement
import app.apti.villa.`data`.local.entity.TopicEntity
import javax.`annotation`.processing.Generated
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
public class TopicDao_Impl(
  __db: RoomDatabase,
) : TopicDao {
  private val __db: RoomDatabase

  private val __insertAdapterOfTopicEntity: EntityInsertAdapter<TopicEntity>
  init {
    this.__db = __db
    this.__insertAdapterOfTopicEntity = object : EntityInsertAdapter<TopicEntity>() {
      protected override fun createQuery(): String =
          "INSERT OR IGNORE INTO `topic` (`id`,`subject_id`,`name`,`created_at`,`updated_at`) VALUES (nullif(?, 0),?,?,?,?)"

      protected override fun bind(statement: SQLiteStatement, entity: TopicEntity) {
        statement.bindLong(1, entity.id)
        val _tmpSubjectId: Long? = entity.subjectId
        if (_tmpSubjectId == null) {
          statement.bindNull(2)
        } else {
          statement.bindLong(2, _tmpSubjectId)
        }
        statement.bindText(3, entity.name)
        statement.bindLong(4, entity.createdAt)
        statement.bindLong(5, entity.updatedAt)
      }
    }
  }

  public override suspend fun insert(topic: TopicEntity): Long = performSuspending(__db, false,
      true) { _connection ->
    val _result: Long = __insertAdapterOfTopicEntity.insertAndReturnId(_connection, topic)
    _result
  }

  public override fun getAllTopics(): Flow<List<TopicEntity>> {
    val _sql: String = "SELECT * FROM topic ORDER BY name"
    return createFlow(__db, false, arrayOf("topic")) { _connection ->
      val _stmt: SQLiteStatement = _connection.prepare(_sql)
      try {
        val _columnIndexOfId: Int = getColumnIndexOrThrow(_stmt, "id")
        val _columnIndexOfSubjectId: Int = getColumnIndexOrThrow(_stmt, "subject_id")
        val _columnIndexOfName: Int = getColumnIndexOrThrow(_stmt, "name")
        val _columnIndexOfCreatedAt: Int = getColumnIndexOrThrow(_stmt, "created_at")
        val _columnIndexOfUpdatedAt: Int = getColumnIndexOrThrow(_stmt, "updated_at")
        val _result: MutableList<TopicEntity> = mutableListOf()
        while (_stmt.step()) {
          val _item: TopicEntity
          val _tmpId: Long
          _tmpId = _stmt.getLong(_columnIndexOfId)
          val _tmpSubjectId: Long?
          if (_stmt.isNull(_columnIndexOfSubjectId)) {
            _tmpSubjectId = null
          } else {
            _tmpSubjectId = _stmt.getLong(_columnIndexOfSubjectId)
          }
          val _tmpName: String
          _tmpName = _stmt.getText(_columnIndexOfName)
          val _tmpCreatedAt: Long
          _tmpCreatedAt = _stmt.getLong(_columnIndexOfCreatedAt)
          val _tmpUpdatedAt: Long
          _tmpUpdatedAt = _stmt.getLong(_columnIndexOfUpdatedAt)
          _item = TopicEntity(_tmpId,_tmpSubjectId,_tmpName,_tmpCreatedAt,_tmpUpdatedAt)
          _result.add(_item)
        }
        _result
      } finally {
        _stmt.close()
      }
    }
  }

  public override suspend fun getAllTopicsOnce(): List<TopicEntity> {
    val _sql: String = "SELECT * FROM topic ORDER BY name"
    return performSuspending(__db, true, false) { _connection ->
      val _stmt: SQLiteStatement = _connection.prepare(_sql)
      try {
        val _columnIndexOfId: Int = getColumnIndexOrThrow(_stmt, "id")
        val _columnIndexOfSubjectId: Int = getColumnIndexOrThrow(_stmt, "subject_id")
        val _columnIndexOfName: Int = getColumnIndexOrThrow(_stmt, "name")
        val _columnIndexOfCreatedAt: Int = getColumnIndexOrThrow(_stmt, "created_at")
        val _columnIndexOfUpdatedAt: Int = getColumnIndexOrThrow(_stmt, "updated_at")
        val _result: MutableList<TopicEntity> = mutableListOf()
        while (_stmt.step()) {
          val _item: TopicEntity
          val _tmpId: Long
          _tmpId = _stmt.getLong(_columnIndexOfId)
          val _tmpSubjectId: Long?
          if (_stmt.isNull(_columnIndexOfSubjectId)) {
            _tmpSubjectId = null
          } else {
            _tmpSubjectId = _stmt.getLong(_columnIndexOfSubjectId)
          }
          val _tmpName: String
          _tmpName = _stmt.getText(_columnIndexOfName)
          val _tmpCreatedAt: Long
          _tmpCreatedAt = _stmt.getLong(_columnIndexOfCreatedAt)
          val _tmpUpdatedAt: Long
          _tmpUpdatedAt = _stmt.getLong(_columnIndexOfUpdatedAt)
          _item = TopicEntity(_tmpId,_tmpSubjectId,_tmpName,_tmpCreatedAt,_tmpUpdatedAt)
          _result.add(_item)
        }
        _result
      } finally {
        _stmt.close()
      }
    }
  }

  public override fun getTopicsBySubject(subjectId: Long): Flow<List<TopicEntity>> {
    val _sql: String = "SELECT * FROM topic WHERE subject_id = ? ORDER BY name"
    return createFlow(__db, false, arrayOf("topic")) { _connection ->
      val _stmt: SQLiteStatement = _connection.prepare(_sql)
      try {
        var _argIndex: Int = 1
        _stmt.bindLong(_argIndex, subjectId)
        val _columnIndexOfId: Int = getColumnIndexOrThrow(_stmt, "id")
        val _columnIndexOfSubjectId: Int = getColumnIndexOrThrow(_stmt, "subject_id")
        val _columnIndexOfName: Int = getColumnIndexOrThrow(_stmt, "name")
        val _columnIndexOfCreatedAt: Int = getColumnIndexOrThrow(_stmt, "created_at")
        val _columnIndexOfUpdatedAt: Int = getColumnIndexOrThrow(_stmt, "updated_at")
        val _result: MutableList<TopicEntity> = mutableListOf()
        while (_stmt.step()) {
          val _item: TopicEntity
          val _tmpId: Long
          _tmpId = _stmt.getLong(_columnIndexOfId)
          val _tmpSubjectId: Long?
          if (_stmt.isNull(_columnIndexOfSubjectId)) {
            _tmpSubjectId = null
          } else {
            _tmpSubjectId = _stmt.getLong(_columnIndexOfSubjectId)
          }
          val _tmpName: String
          _tmpName = _stmt.getText(_columnIndexOfName)
          val _tmpCreatedAt: Long
          _tmpCreatedAt = _stmt.getLong(_columnIndexOfCreatedAt)
          val _tmpUpdatedAt: Long
          _tmpUpdatedAt = _stmt.getLong(_columnIndexOfUpdatedAt)
          _item = TopicEntity(_tmpId,_tmpSubjectId,_tmpName,_tmpCreatedAt,_tmpUpdatedAt)
          _result.add(_item)
        }
        _result
      } finally {
        _stmt.close()
      }
    }
  }

  public override fun getUnassignedTopics(): Flow<List<TopicEntity>> {
    val _sql: String = "SELECT * FROM topic WHERE subject_id IS NULL ORDER BY name"
    return createFlow(__db, false, arrayOf("topic")) { _connection ->
      val _stmt: SQLiteStatement = _connection.prepare(_sql)
      try {
        val _columnIndexOfId: Int = getColumnIndexOrThrow(_stmt, "id")
        val _columnIndexOfSubjectId: Int = getColumnIndexOrThrow(_stmt, "subject_id")
        val _columnIndexOfName: Int = getColumnIndexOrThrow(_stmt, "name")
        val _columnIndexOfCreatedAt: Int = getColumnIndexOrThrow(_stmt, "created_at")
        val _columnIndexOfUpdatedAt: Int = getColumnIndexOrThrow(_stmt, "updated_at")
        val _result: MutableList<TopicEntity> = mutableListOf()
        while (_stmt.step()) {
          val _item: TopicEntity
          val _tmpId: Long
          _tmpId = _stmt.getLong(_columnIndexOfId)
          val _tmpSubjectId: Long?
          if (_stmt.isNull(_columnIndexOfSubjectId)) {
            _tmpSubjectId = null
          } else {
            _tmpSubjectId = _stmt.getLong(_columnIndexOfSubjectId)
          }
          val _tmpName: String
          _tmpName = _stmt.getText(_columnIndexOfName)
          val _tmpCreatedAt: Long
          _tmpCreatedAt = _stmt.getLong(_columnIndexOfCreatedAt)
          val _tmpUpdatedAt: Long
          _tmpUpdatedAt = _stmt.getLong(_columnIndexOfUpdatedAt)
          _item = TopicEntity(_tmpId,_tmpSubjectId,_tmpName,_tmpCreatedAt,_tmpUpdatedAt)
          _result.add(_item)
        }
        _result
      } finally {
        _stmt.close()
      }
    }
  }

  public override suspend fun getTopicById(id: Long): TopicEntity? {
    val _sql: String = "SELECT * FROM topic WHERE id = ?"
    return performSuspending(__db, true, false) { _connection ->
      val _stmt: SQLiteStatement = _connection.prepare(_sql)
      try {
        var _argIndex: Int = 1
        _stmt.bindLong(_argIndex, id)
        val _columnIndexOfId: Int = getColumnIndexOrThrow(_stmt, "id")
        val _columnIndexOfSubjectId: Int = getColumnIndexOrThrow(_stmt, "subject_id")
        val _columnIndexOfName: Int = getColumnIndexOrThrow(_stmt, "name")
        val _columnIndexOfCreatedAt: Int = getColumnIndexOrThrow(_stmt, "created_at")
        val _columnIndexOfUpdatedAt: Int = getColumnIndexOrThrow(_stmt, "updated_at")
        val _result: TopicEntity?
        if (_stmt.step()) {
          val _tmpId: Long
          _tmpId = _stmt.getLong(_columnIndexOfId)
          val _tmpSubjectId: Long?
          if (_stmt.isNull(_columnIndexOfSubjectId)) {
            _tmpSubjectId = null
          } else {
            _tmpSubjectId = _stmt.getLong(_columnIndexOfSubjectId)
          }
          val _tmpName: String
          _tmpName = _stmt.getText(_columnIndexOfName)
          val _tmpCreatedAt: Long
          _tmpCreatedAt = _stmt.getLong(_columnIndexOfCreatedAt)
          val _tmpUpdatedAt: Long
          _tmpUpdatedAt = _stmt.getLong(_columnIndexOfUpdatedAt)
          _result = TopicEntity(_tmpId,_tmpSubjectId,_tmpName,_tmpCreatedAt,_tmpUpdatedAt)
        } else {
          _result = null
        }
        _result
      } finally {
        _stmt.close()
      }
    }
  }

  public override suspend fun getTopicBySubjectAndName(subjectId: Long?, name: String):
      TopicEntity? {
    val _sql: String = "SELECT * FROM topic WHERE subject_id = ? AND name = ? COLLATE NOCASE"
    return performSuspending(__db, true, false) { _connection ->
      val _stmt: SQLiteStatement = _connection.prepare(_sql)
      try {
        var _argIndex: Int = 1
        if (subjectId == null) {
          _stmt.bindNull(_argIndex)
        } else {
          _stmt.bindLong(_argIndex, subjectId)
        }
        _argIndex = 2
        _stmt.bindText(_argIndex, name)
        val _columnIndexOfId: Int = getColumnIndexOrThrow(_stmt, "id")
        val _columnIndexOfSubjectId: Int = getColumnIndexOrThrow(_stmt, "subject_id")
        val _columnIndexOfName: Int = getColumnIndexOrThrow(_stmt, "name")
        val _columnIndexOfCreatedAt: Int = getColumnIndexOrThrow(_stmt, "created_at")
        val _columnIndexOfUpdatedAt: Int = getColumnIndexOrThrow(_stmt, "updated_at")
        val _result: TopicEntity?
        if (_stmt.step()) {
          val _tmpId: Long
          _tmpId = _stmt.getLong(_columnIndexOfId)
          val _tmpSubjectId: Long?
          if (_stmt.isNull(_columnIndexOfSubjectId)) {
            _tmpSubjectId = null
          } else {
            _tmpSubjectId = _stmt.getLong(_columnIndexOfSubjectId)
          }
          val _tmpName: String
          _tmpName = _stmt.getText(_columnIndexOfName)
          val _tmpCreatedAt: Long
          _tmpCreatedAt = _stmt.getLong(_columnIndexOfCreatedAt)
          val _tmpUpdatedAt: Long
          _tmpUpdatedAt = _stmt.getLong(_columnIndexOfUpdatedAt)
          _result = TopicEntity(_tmpId,_tmpSubjectId,_tmpName,_tmpCreatedAt,_tmpUpdatedAt)
        } else {
          _result = null
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
