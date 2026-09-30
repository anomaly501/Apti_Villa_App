package app.apti.villa.`data`.local.dao

import androidx.room.EntityInsertAdapter
import androidx.room.RoomDatabase
import androidx.room.coroutines.createFlow
import androidx.room.util.getColumnIndexOrThrow
import androidx.room.util.performSuspending
import androidx.sqlite.SQLiteStatement
import app.apti.villa.`data`.local.entity.QuestionEntity
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
public class QuestionDao_Impl(
  __db: RoomDatabase,
) : QuestionDao {
  private val __db: RoomDatabase

  private val __insertAdapterOfQuestionEntity: EntityInsertAdapter<QuestionEntity>
  init {
    this.__db = __db
    this.__insertAdapterOfQuestionEntity = object : EntityInsertAdapter<QuestionEntity>() {
      protected override fun createQuery(): String =
          "INSERT OR REPLACE INTO `question` (`id`,`uuid`,`source_id`,`external_id`,`content_hash`,`text`,`format`,`subject_id`,`topic_id`,`sub_topic_id`,`difficulty`,`source_timestamp_sec`,`source_url`,`language`,`has_solution`,`created_at`,`updated_at`) VALUES (nullif(?, 0),?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)"

      protected override fun bind(statement: SQLiteStatement, entity: QuestionEntity) {
        statement.bindLong(1, entity.id)
        statement.bindText(2, entity.uuid)
        statement.bindLong(3, entity.sourceId)
        statement.bindText(4, entity.externalId)
        statement.bindText(5, entity.contentHash)
        statement.bindText(6, entity.text)
        statement.bindText(7, entity.format)
        val _tmpSubjectId: Long? = entity.subjectId
        if (_tmpSubjectId == null) {
          statement.bindNull(8)
        } else {
          statement.bindLong(8, _tmpSubjectId)
        }
        val _tmpTopicId: Long? = entity.topicId
        if (_tmpTopicId == null) {
          statement.bindNull(9)
        } else {
          statement.bindLong(9, _tmpTopicId)
        }
        val _tmpSubTopicId: Long? = entity.subTopicId
        if (_tmpSubTopicId == null) {
          statement.bindNull(10)
        } else {
          statement.bindLong(10, _tmpSubTopicId)
        }
        val _tmpDifficulty: String? = entity.difficulty
        if (_tmpDifficulty == null) {
          statement.bindNull(11)
        } else {
          statement.bindText(11, _tmpDifficulty)
        }
        val _tmpSourceTimestampSec: Int? = entity.sourceTimestampSec
        if (_tmpSourceTimestampSec == null) {
          statement.bindNull(12)
        } else {
          statement.bindLong(12, _tmpSourceTimestampSec.toLong())
        }
        val _tmpSourceUrl: String? = entity.sourceUrl
        if (_tmpSourceUrl == null) {
          statement.bindNull(13)
        } else {
          statement.bindText(13, _tmpSourceUrl)
        }
        val _tmpLanguage: String? = entity.language
        if (_tmpLanguage == null) {
          statement.bindNull(14)
        } else {
          statement.bindText(14, _tmpLanguage)
        }
        val _tmp: Int = if (entity.hasSolution) 1 else 0
        statement.bindLong(15, _tmp.toLong())
        statement.bindLong(16, entity.createdAt)
        statement.bindLong(17, entity.updatedAt)
      }
    }
  }

  public override suspend fun insert(question: QuestionEntity): Long = performSuspending(__db,
      false, true) { _connection ->
    val _result: Long = __insertAdapterOfQuestionEntity.insertAndReturnId(_connection, question)
    _result
  }

  public override suspend fun insertAll(questions: List<QuestionEntity>): List<Long> =
      performSuspending(__db, false, true) { _connection ->
    val _result: List<Long> = __insertAdapterOfQuestionEntity.insertAndReturnIdsList(_connection,
        questions)
    _result
  }

  public override fun getAllQuestions(): Flow<List<QuestionEntity>> {
    val _sql: String = "SELECT * FROM question ORDER BY id ASC"
    return createFlow(__db, false, arrayOf("question")) { _connection ->
      val _stmt: SQLiteStatement = _connection.prepare(_sql)
      try {
        val _columnIndexOfId: Int = getColumnIndexOrThrow(_stmt, "id")
        val _columnIndexOfUuid: Int = getColumnIndexOrThrow(_stmt, "uuid")
        val _columnIndexOfSourceId: Int = getColumnIndexOrThrow(_stmt, "source_id")
        val _columnIndexOfExternalId: Int = getColumnIndexOrThrow(_stmt, "external_id")
        val _columnIndexOfContentHash: Int = getColumnIndexOrThrow(_stmt, "content_hash")
        val _columnIndexOfText: Int = getColumnIndexOrThrow(_stmt, "text")
        val _columnIndexOfFormat: Int = getColumnIndexOrThrow(_stmt, "format")
        val _columnIndexOfSubjectId: Int = getColumnIndexOrThrow(_stmt, "subject_id")
        val _columnIndexOfTopicId: Int = getColumnIndexOrThrow(_stmt, "topic_id")
        val _columnIndexOfSubTopicId: Int = getColumnIndexOrThrow(_stmt, "sub_topic_id")
        val _columnIndexOfDifficulty: Int = getColumnIndexOrThrow(_stmt, "difficulty")
        val _columnIndexOfSourceTimestampSec: Int = getColumnIndexOrThrow(_stmt,
            "source_timestamp_sec")
        val _columnIndexOfSourceUrl: Int = getColumnIndexOrThrow(_stmt, "source_url")
        val _columnIndexOfLanguage: Int = getColumnIndexOrThrow(_stmt, "language")
        val _columnIndexOfHasSolution: Int = getColumnIndexOrThrow(_stmt, "has_solution")
        val _columnIndexOfCreatedAt: Int = getColumnIndexOrThrow(_stmt, "created_at")
        val _columnIndexOfUpdatedAt: Int = getColumnIndexOrThrow(_stmt, "updated_at")
        val _result: MutableList<QuestionEntity> = mutableListOf()
        while (_stmt.step()) {
          val _item: QuestionEntity
          val _tmpId: Long
          _tmpId = _stmt.getLong(_columnIndexOfId)
          val _tmpUuid: String
          _tmpUuid = _stmt.getText(_columnIndexOfUuid)
          val _tmpSourceId: Long
          _tmpSourceId = _stmt.getLong(_columnIndexOfSourceId)
          val _tmpExternalId: String
          _tmpExternalId = _stmt.getText(_columnIndexOfExternalId)
          val _tmpContentHash: String
          _tmpContentHash = _stmt.getText(_columnIndexOfContentHash)
          val _tmpText: String
          _tmpText = _stmt.getText(_columnIndexOfText)
          val _tmpFormat: String
          _tmpFormat = _stmt.getText(_columnIndexOfFormat)
          val _tmpSubjectId: Long?
          if (_stmt.isNull(_columnIndexOfSubjectId)) {
            _tmpSubjectId = null
          } else {
            _tmpSubjectId = _stmt.getLong(_columnIndexOfSubjectId)
          }
          val _tmpTopicId: Long?
          if (_stmt.isNull(_columnIndexOfTopicId)) {
            _tmpTopicId = null
          } else {
            _tmpTopicId = _stmt.getLong(_columnIndexOfTopicId)
          }
          val _tmpSubTopicId: Long?
          if (_stmt.isNull(_columnIndexOfSubTopicId)) {
            _tmpSubTopicId = null
          } else {
            _tmpSubTopicId = _stmt.getLong(_columnIndexOfSubTopicId)
          }
          val _tmpDifficulty: String?
          if (_stmt.isNull(_columnIndexOfDifficulty)) {
            _tmpDifficulty = null
          } else {
            _tmpDifficulty = _stmt.getText(_columnIndexOfDifficulty)
          }
          val _tmpSourceTimestampSec: Int?
          if (_stmt.isNull(_columnIndexOfSourceTimestampSec)) {
            _tmpSourceTimestampSec = null
          } else {
            _tmpSourceTimestampSec = _stmt.getLong(_columnIndexOfSourceTimestampSec).toInt()
          }
          val _tmpSourceUrl: String?
          if (_stmt.isNull(_columnIndexOfSourceUrl)) {
            _tmpSourceUrl = null
          } else {
            _tmpSourceUrl = _stmt.getText(_columnIndexOfSourceUrl)
          }
          val _tmpLanguage: String?
          if (_stmt.isNull(_columnIndexOfLanguage)) {
            _tmpLanguage = null
          } else {
            _tmpLanguage = _stmt.getText(_columnIndexOfLanguage)
          }
          val _tmpHasSolution: Boolean
          val _tmp: Int
          _tmp = _stmt.getLong(_columnIndexOfHasSolution).toInt()
          _tmpHasSolution = _tmp != 0
          val _tmpCreatedAt: Long
          _tmpCreatedAt = _stmt.getLong(_columnIndexOfCreatedAt)
          val _tmpUpdatedAt: Long
          _tmpUpdatedAt = _stmt.getLong(_columnIndexOfUpdatedAt)
          _item =
              QuestionEntity(_tmpId,_tmpUuid,_tmpSourceId,_tmpExternalId,_tmpContentHash,_tmpText,_tmpFormat,_tmpSubjectId,_tmpTopicId,_tmpSubTopicId,_tmpDifficulty,_tmpSourceTimestampSec,_tmpSourceUrl,_tmpLanguage,_tmpHasSolution,_tmpCreatedAt,_tmpUpdatedAt)
          _result.add(_item)
        }
        _result
      } finally {
        _stmt.close()
      }
    }
  }

  public override suspend fun getQuestionById(id: Long): QuestionEntity? {
    val _sql: String = "SELECT * FROM question WHERE id = ?"
    return performSuspending(__db, true, false) { _connection ->
      val _stmt: SQLiteStatement = _connection.prepare(_sql)
      try {
        var _argIndex: Int = 1
        _stmt.bindLong(_argIndex, id)
        val _columnIndexOfId: Int = getColumnIndexOrThrow(_stmt, "id")
        val _columnIndexOfUuid: Int = getColumnIndexOrThrow(_stmt, "uuid")
        val _columnIndexOfSourceId: Int = getColumnIndexOrThrow(_stmt, "source_id")
        val _columnIndexOfExternalId: Int = getColumnIndexOrThrow(_stmt, "external_id")
        val _columnIndexOfContentHash: Int = getColumnIndexOrThrow(_stmt, "content_hash")
        val _columnIndexOfText: Int = getColumnIndexOrThrow(_stmt, "text")
        val _columnIndexOfFormat: Int = getColumnIndexOrThrow(_stmt, "format")
        val _columnIndexOfSubjectId: Int = getColumnIndexOrThrow(_stmt, "subject_id")
        val _columnIndexOfTopicId: Int = getColumnIndexOrThrow(_stmt, "topic_id")
        val _columnIndexOfSubTopicId: Int = getColumnIndexOrThrow(_stmt, "sub_topic_id")
        val _columnIndexOfDifficulty: Int = getColumnIndexOrThrow(_stmt, "difficulty")
        val _columnIndexOfSourceTimestampSec: Int = getColumnIndexOrThrow(_stmt,
            "source_timestamp_sec")
        val _columnIndexOfSourceUrl: Int = getColumnIndexOrThrow(_stmt, "source_url")
        val _columnIndexOfLanguage: Int = getColumnIndexOrThrow(_stmt, "language")
        val _columnIndexOfHasSolution: Int = getColumnIndexOrThrow(_stmt, "has_solution")
        val _columnIndexOfCreatedAt: Int = getColumnIndexOrThrow(_stmt, "created_at")
        val _columnIndexOfUpdatedAt: Int = getColumnIndexOrThrow(_stmt, "updated_at")
        val _result: QuestionEntity?
        if (_stmt.step()) {
          val _tmpId: Long
          _tmpId = _stmt.getLong(_columnIndexOfId)
          val _tmpUuid: String
          _tmpUuid = _stmt.getText(_columnIndexOfUuid)
          val _tmpSourceId: Long
          _tmpSourceId = _stmt.getLong(_columnIndexOfSourceId)
          val _tmpExternalId: String
          _tmpExternalId = _stmt.getText(_columnIndexOfExternalId)
          val _tmpContentHash: String
          _tmpContentHash = _stmt.getText(_columnIndexOfContentHash)
          val _tmpText: String
          _tmpText = _stmt.getText(_columnIndexOfText)
          val _tmpFormat: String
          _tmpFormat = _stmt.getText(_columnIndexOfFormat)
          val _tmpSubjectId: Long?
          if (_stmt.isNull(_columnIndexOfSubjectId)) {
            _tmpSubjectId = null
          } else {
            _tmpSubjectId = _stmt.getLong(_columnIndexOfSubjectId)
          }
          val _tmpTopicId: Long?
          if (_stmt.isNull(_columnIndexOfTopicId)) {
            _tmpTopicId = null
          } else {
            _tmpTopicId = _stmt.getLong(_columnIndexOfTopicId)
          }
          val _tmpSubTopicId: Long?
          if (_stmt.isNull(_columnIndexOfSubTopicId)) {
            _tmpSubTopicId = null
          } else {
            _tmpSubTopicId = _stmt.getLong(_columnIndexOfSubTopicId)
          }
          val _tmpDifficulty: String?
          if (_stmt.isNull(_columnIndexOfDifficulty)) {
            _tmpDifficulty = null
          } else {
            _tmpDifficulty = _stmt.getText(_columnIndexOfDifficulty)
          }
          val _tmpSourceTimestampSec: Int?
          if (_stmt.isNull(_columnIndexOfSourceTimestampSec)) {
            _tmpSourceTimestampSec = null
          } else {
            _tmpSourceTimestampSec = _stmt.getLong(_columnIndexOfSourceTimestampSec).toInt()
          }
          val _tmpSourceUrl: String?
          if (_stmt.isNull(_columnIndexOfSourceUrl)) {
            _tmpSourceUrl = null
          } else {
            _tmpSourceUrl = _stmt.getText(_columnIndexOfSourceUrl)
          }
          val _tmpLanguage: String?
          if (_stmt.isNull(_columnIndexOfLanguage)) {
            _tmpLanguage = null
          } else {
            _tmpLanguage = _stmt.getText(_columnIndexOfLanguage)
          }
          val _tmpHasSolution: Boolean
          val _tmp: Int
          _tmp = _stmt.getLong(_columnIndexOfHasSolution).toInt()
          _tmpHasSolution = _tmp != 0
          val _tmpCreatedAt: Long
          _tmpCreatedAt = _stmt.getLong(_columnIndexOfCreatedAt)
          val _tmpUpdatedAt: Long
          _tmpUpdatedAt = _stmt.getLong(_columnIndexOfUpdatedAt)
          _result =
              QuestionEntity(_tmpId,_tmpUuid,_tmpSourceId,_tmpExternalId,_tmpContentHash,_tmpText,_tmpFormat,_tmpSubjectId,_tmpTopicId,_tmpSubTopicId,_tmpDifficulty,_tmpSourceTimestampSec,_tmpSourceUrl,_tmpLanguage,_tmpHasSolution,_tmpCreatedAt,_tmpUpdatedAt)
        } else {
          _result = null
        }
        _result
      } finally {
        _stmt.close()
      }
    }
  }

  public override suspend fun getQuestionByUuid(uuid: String): QuestionEntity? {
    val _sql: String = "SELECT * FROM question WHERE uuid = ?"
    return performSuspending(__db, true, false) { _connection ->
      val _stmt: SQLiteStatement = _connection.prepare(_sql)
      try {
        var _argIndex: Int = 1
        _stmt.bindText(_argIndex, uuid)
        val _columnIndexOfId: Int = getColumnIndexOrThrow(_stmt, "id")
        val _columnIndexOfUuid: Int = getColumnIndexOrThrow(_stmt, "uuid")
        val _columnIndexOfSourceId: Int = getColumnIndexOrThrow(_stmt, "source_id")
        val _columnIndexOfExternalId: Int = getColumnIndexOrThrow(_stmt, "external_id")
        val _columnIndexOfContentHash: Int = getColumnIndexOrThrow(_stmt, "content_hash")
        val _columnIndexOfText: Int = getColumnIndexOrThrow(_stmt, "text")
        val _columnIndexOfFormat: Int = getColumnIndexOrThrow(_stmt, "format")
        val _columnIndexOfSubjectId: Int = getColumnIndexOrThrow(_stmt, "subject_id")
        val _columnIndexOfTopicId: Int = getColumnIndexOrThrow(_stmt, "topic_id")
        val _columnIndexOfSubTopicId: Int = getColumnIndexOrThrow(_stmt, "sub_topic_id")
        val _columnIndexOfDifficulty: Int = getColumnIndexOrThrow(_stmt, "difficulty")
        val _columnIndexOfSourceTimestampSec: Int = getColumnIndexOrThrow(_stmt,
            "source_timestamp_sec")
        val _columnIndexOfSourceUrl: Int = getColumnIndexOrThrow(_stmt, "source_url")
        val _columnIndexOfLanguage: Int = getColumnIndexOrThrow(_stmt, "language")
        val _columnIndexOfHasSolution: Int = getColumnIndexOrThrow(_stmt, "has_solution")
        val _columnIndexOfCreatedAt: Int = getColumnIndexOrThrow(_stmt, "created_at")
        val _columnIndexOfUpdatedAt: Int = getColumnIndexOrThrow(_stmt, "updated_at")
        val _result: QuestionEntity?
        if (_stmt.step()) {
          val _tmpId: Long
          _tmpId = _stmt.getLong(_columnIndexOfId)
          val _tmpUuid: String
          _tmpUuid = _stmt.getText(_columnIndexOfUuid)
          val _tmpSourceId: Long
          _tmpSourceId = _stmt.getLong(_columnIndexOfSourceId)
          val _tmpExternalId: String
          _tmpExternalId = _stmt.getText(_columnIndexOfExternalId)
          val _tmpContentHash: String
          _tmpContentHash = _stmt.getText(_columnIndexOfContentHash)
          val _tmpText: String
          _tmpText = _stmt.getText(_columnIndexOfText)
          val _tmpFormat: String
          _tmpFormat = _stmt.getText(_columnIndexOfFormat)
          val _tmpSubjectId: Long?
          if (_stmt.isNull(_columnIndexOfSubjectId)) {
            _tmpSubjectId = null
          } else {
            _tmpSubjectId = _stmt.getLong(_columnIndexOfSubjectId)
          }
          val _tmpTopicId: Long?
          if (_stmt.isNull(_columnIndexOfTopicId)) {
            _tmpTopicId = null
          } else {
            _tmpTopicId = _stmt.getLong(_columnIndexOfTopicId)
          }
          val _tmpSubTopicId: Long?
          if (_stmt.isNull(_columnIndexOfSubTopicId)) {
            _tmpSubTopicId = null
          } else {
            _tmpSubTopicId = _stmt.getLong(_columnIndexOfSubTopicId)
          }
          val _tmpDifficulty: String?
          if (_stmt.isNull(_columnIndexOfDifficulty)) {
            _tmpDifficulty = null
          } else {
            _tmpDifficulty = _stmt.getText(_columnIndexOfDifficulty)
          }
          val _tmpSourceTimestampSec: Int?
          if (_stmt.isNull(_columnIndexOfSourceTimestampSec)) {
            _tmpSourceTimestampSec = null
          } else {
            _tmpSourceTimestampSec = _stmt.getLong(_columnIndexOfSourceTimestampSec).toInt()
          }
          val _tmpSourceUrl: String?
          if (_stmt.isNull(_columnIndexOfSourceUrl)) {
            _tmpSourceUrl = null
          } else {
            _tmpSourceUrl = _stmt.getText(_columnIndexOfSourceUrl)
          }
          val _tmpLanguage: String?
          if (_stmt.isNull(_columnIndexOfLanguage)) {
            _tmpLanguage = null
          } else {
            _tmpLanguage = _stmt.getText(_columnIndexOfLanguage)
          }
          val _tmpHasSolution: Boolean
          val _tmp: Int
          _tmp = _stmt.getLong(_columnIndexOfHasSolution).toInt()
          _tmpHasSolution = _tmp != 0
          val _tmpCreatedAt: Long
          _tmpCreatedAt = _stmt.getLong(_columnIndexOfCreatedAt)
          val _tmpUpdatedAt: Long
          _tmpUpdatedAt = _stmt.getLong(_columnIndexOfUpdatedAt)
          _result =
              QuestionEntity(_tmpId,_tmpUuid,_tmpSourceId,_tmpExternalId,_tmpContentHash,_tmpText,_tmpFormat,_tmpSubjectId,_tmpTopicId,_tmpSubTopicId,_tmpDifficulty,_tmpSourceTimestampSec,_tmpSourceUrl,_tmpLanguage,_tmpHasSolution,_tmpCreatedAt,_tmpUpdatedAt)
        } else {
          _result = null
        }
        _result
      } finally {
        _stmt.close()
      }
    }
  }

  public override suspend fun getQuestionByHash(hash: String): QuestionEntity? {
    val _sql: String = "SELECT * FROM question WHERE content_hash = ? LIMIT 1"
    return performSuspending(__db, true, false) { _connection ->
      val _stmt: SQLiteStatement = _connection.prepare(_sql)
      try {
        var _argIndex: Int = 1
        _stmt.bindText(_argIndex, hash)
        val _columnIndexOfId: Int = getColumnIndexOrThrow(_stmt, "id")
        val _columnIndexOfUuid: Int = getColumnIndexOrThrow(_stmt, "uuid")
        val _columnIndexOfSourceId: Int = getColumnIndexOrThrow(_stmt, "source_id")
        val _columnIndexOfExternalId: Int = getColumnIndexOrThrow(_stmt, "external_id")
        val _columnIndexOfContentHash: Int = getColumnIndexOrThrow(_stmt, "content_hash")
        val _columnIndexOfText: Int = getColumnIndexOrThrow(_stmt, "text")
        val _columnIndexOfFormat: Int = getColumnIndexOrThrow(_stmt, "format")
        val _columnIndexOfSubjectId: Int = getColumnIndexOrThrow(_stmt, "subject_id")
        val _columnIndexOfTopicId: Int = getColumnIndexOrThrow(_stmt, "topic_id")
        val _columnIndexOfSubTopicId: Int = getColumnIndexOrThrow(_stmt, "sub_topic_id")
        val _columnIndexOfDifficulty: Int = getColumnIndexOrThrow(_stmt, "difficulty")
        val _columnIndexOfSourceTimestampSec: Int = getColumnIndexOrThrow(_stmt,
            "source_timestamp_sec")
        val _columnIndexOfSourceUrl: Int = getColumnIndexOrThrow(_stmt, "source_url")
        val _columnIndexOfLanguage: Int = getColumnIndexOrThrow(_stmt, "language")
        val _columnIndexOfHasSolution: Int = getColumnIndexOrThrow(_stmt, "has_solution")
        val _columnIndexOfCreatedAt: Int = getColumnIndexOrThrow(_stmt, "created_at")
        val _columnIndexOfUpdatedAt: Int = getColumnIndexOrThrow(_stmt, "updated_at")
        val _result: QuestionEntity?
        if (_stmt.step()) {
          val _tmpId: Long
          _tmpId = _stmt.getLong(_columnIndexOfId)
          val _tmpUuid: String
          _tmpUuid = _stmt.getText(_columnIndexOfUuid)
          val _tmpSourceId: Long
          _tmpSourceId = _stmt.getLong(_columnIndexOfSourceId)
          val _tmpExternalId: String
          _tmpExternalId = _stmt.getText(_columnIndexOfExternalId)
          val _tmpContentHash: String
          _tmpContentHash = _stmt.getText(_columnIndexOfContentHash)
          val _tmpText: String
          _tmpText = _stmt.getText(_columnIndexOfText)
          val _tmpFormat: String
          _tmpFormat = _stmt.getText(_columnIndexOfFormat)
          val _tmpSubjectId: Long?
          if (_stmt.isNull(_columnIndexOfSubjectId)) {
            _tmpSubjectId = null
          } else {
            _tmpSubjectId = _stmt.getLong(_columnIndexOfSubjectId)
          }
          val _tmpTopicId: Long?
          if (_stmt.isNull(_columnIndexOfTopicId)) {
            _tmpTopicId = null
          } else {
            _tmpTopicId = _stmt.getLong(_columnIndexOfTopicId)
          }
          val _tmpSubTopicId: Long?
          if (_stmt.isNull(_columnIndexOfSubTopicId)) {
            _tmpSubTopicId = null
          } else {
            _tmpSubTopicId = _stmt.getLong(_columnIndexOfSubTopicId)
          }
          val _tmpDifficulty: String?
          if (_stmt.isNull(_columnIndexOfDifficulty)) {
            _tmpDifficulty = null
          } else {
            _tmpDifficulty = _stmt.getText(_columnIndexOfDifficulty)
          }
          val _tmpSourceTimestampSec: Int?
          if (_stmt.isNull(_columnIndexOfSourceTimestampSec)) {
            _tmpSourceTimestampSec = null
          } else {
            _tmpSourceTimestampSec = _stmt.getLong(_columnIndexOfSourceTimestampSec).toInt()
          }
          val _tmpSourceUrl: String?
          if (_stmt.isNull(_columnIndexOfSourceUrl)) {
            _tmpSourceUrl = null
          } else {
            _tmpSourceUrl = _stmt.getText(_columnIndexOfSourceUrl)
          }
          val _tmpLanguage: String?
          if (_stmt.isNull(_columnIndexOfLanguage)) {
            _tmpLanguage = null
          } else {
            _tmpLanguage = _stmt.getText(_columnIndexOfLanguage)
          }
          val _tmpHasSolution: Boolean
          val _tmp: Int
          _tmp = _stmt.getLong(_columnIndexOfHasSolution).toInt()
          _tmpHasSolution = _tmp != 0
          val _tmpCreatedAt: Long
          _tmpCreatedAt = _stmt.getLong(_columnIndexOfCreatedAt)
          val _tmpUpdatedAt: Long
          _tmpUpdatedAt = _stmt.getLong(_columnIndexOfUpdatedAt)
          _result =
              QuestionEntity(_tmpId,_tmpUuid,_tmpSourceId,_tmpExternalId,_tmpContentHash,_tmpText,_tmpFormat,_tmpSubjectId,_tmpTopicId,_tmpSubTopicId,_tmpDifficulty,_tmpSourceTimestampSec,_tmpSourceUrl,_tmpLanguage,_tmpHasSolution,_tmpCreatedAt,_tmpUpdatedAt)
        } else {
          _result = null
        }
        _result
      } finally {
        _stmt.close()
      }
    }
  }

  public override suspend fun getQuestionBySourceAndExternalId(sourceId: Long, externalId: String):
      QuestionEntity? {
    val _sql: String = "SELECT * FROM question WHERE source_id = ? AND external_id = ? LIMIT 1"
    return performSuspending(__db, true, false) { _connection ->
      val _stmt: SQLiteStatement = _connection.prepare(_sql)
      try {
        var _argIndex: Int = 1
        _stmt.bindLong(_argIndex, sourceId)
        _argIndex = 2
        _stmt.bindText(_argIndex, externalId)
        val _columnIndexOfId: Int = getColumnIndexOrThrow(_stmt, "id")
        val _columnIndexOfUuid: Int = getColumnIndexOrThrow(_stmt, "uuid")
        val _columnIndexOfSourceId: Int = getColumnIndexOrThrow(_stmt, "source_id")
        val _columnIndexOfExternalId: Int = getColumnIndexOrThrow(_stmt, "external_id")
        val _columnIndexOfContentHash: Int = getColumnIndexOrThrow(_stmt, "content_hash")
        val _columnIndexOfText: Int = getColumnIndexOrThrow(_stmt, "text")
        val _columnIndexOfFormat: Int = getColumnIndexOrThrow(_stmt, "format")
        val _columnIndexOfSubjectId: Int = getColumnIndexOrThrow(_stmt, "subject_id")
        val _columnIndexOfTopicId: Int = getColumnIndexOrThrow(_stmt, "topic_id")
        val _columnIndexOfSubTopicId: Int = getColumnIndexOrThrow(_stmt, "sub_topic_id")
        val _columnIndexOfDifficulty: Int = getColumnIndexOrThrow(_stmt, "difficulty")
        val _columnIndexOfSourceTimestampSec: Int = getColumnIndexOrThrow(_stmt,
            "source_timestamp_sec")
        val _columnIndexOfSourceUrl: Int = getColumnIndexOrThrow(_stmt, "source_url")
        val _columnIndexOfLanguage: Int = getColumnIndexOrThrow(_stmt, "language")
        val _columnIndexOfHasSolution: Int = getColumnIndexOrThrow(_stmt, "has_solution")
        val _columnIndexOfCreatedAt: Int = getColumnIndexOrThrow(_stmt, "created_at")
        val _columnIndexOfUpdatedAt: Int = getColumnIndexOrThrow(_stmt, "updated_at")
        val _result: QuestionEntity?
        if (_stmt.step()) {
          val _tmpId: Long
          _tmpId = _stmt.getLong(_columnIndexOfId)
          val _tmpUuid: String
          _tmpUuid = _stmt.getText(_columnIndexOfUuid)
          val _tmpSourceId: Long
          _tmpSourceId = _stmt.getLong(_columnIndexOfSourceId)
          val _tmpExternalId: String
          _tmpExternalId = _stmt.getText(_columnIndexOfExternalId)
          val _tmpContentHash: String
          _tmpContentHash = _stmt.getText(_columnIndexOfContentHash)
          val _tmpText: String
          _tmpText = _stmt.getText(_columnIndexOfText)
          val _tmpFormat: String
          _tmpFormat = _stmt.getText(_columnIndexOfFormat)
          val _tmpSubjectId: Long?
          if (_stmt.isNull(_columnIndexOfSubjectId)) {
            _tmpSubjectId = null
          } else {
            _tmpSubjectId = _stmt.getLong(_columnIndexOfSubjectId)
          }
          val _tmpTopicId: Long?
          if (_stmt.isNull(_columnIndexOfTopicId)) {
            _tmpTopicId = null
          } else {
            _tmpTopicId = _stmt.getLong(_columnIndexOfTopicId)
          }
          val _tmpSubTopicId: Long?
          if (_stmt.isNull(_columnIndexOfSubTopicId)) {
            _tmpSubTopicId = null
          } else {
            _tmpSubTopicId = _stmt.getLong(_columnIndexOfSubTopicId)
          }
          val _tmpDifficulty: String?
          if (_stmt.isNull(_columnIndexOfDifficulty)) {
            _tmpDifficulty = null
          } else {
            _tmpDifficulty = _stmt.getText(_columnIndexOfDifficulty)
          }
          val _tmpSourceTimestampSec: Int?
          if (_stmt.isNull(_columnIndexOfSourceTimestampSec)) {
            _tmpSourceTimestampSec = null
          } else {
            _tmpSourceTimestampSec = _stmt.getLong(_columnIndexOfSourceTimestampSec).toInt()
          }
          val _tmpSourceUrl: String?
          if (_stmt.isNull(_columnIndexOfSourceUrl)) {
            _tmpSourceUrl = null
          } else {
            _tmpSourceUrl = _stmt.getText(_columnIndexOfSourceUrl)
          }
          val _tmpLanguage: String?
          if (_stmt.isNull(_columnIndexOfLanguage)) {
            _tmpLanguage = null
          } else {
            _tmpLanguage = _stmt.getText(_columnIndexOfLanguage)
          }
          val _tmpHasSolution: Boolean
          val _tmp: Int
          _tmp = _stmt.getLong(_columnIndexOfHasSolution).toInt()
          _tmpHasSolution = _tmp != 0
          val _tmpCreatedAt: Long
          _tmpCreatedAt = _stmt.getLong(_columnIndexOfCreatedAt)
          val _tmpUpdatedAt: Long
          _tmpUpdatedAt = _stmt.getLong(_columnIndexOfUpdatedAt)
          _result =
              QuestionEntity(_tmpId,_tmpUuid,_tmpSourceId,_tmpExternalId,_tmpContentHash,_tmpText,_tmpFormat,_tmpSubjectId,_tmpTopicId,_tmpSubTopicId,_tmpDifficulty,_tmpSourceTimestampSec,_tmpSourceUrl,_tmpLanguage,_tmpHasSolution,_tmpCreatedAt,_tmpUpdatedAt)
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
    val _sql: String = "SELECT COUNT(*) FROM question"
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

  public override suspend fun getAllQuestionsOnce(): List<QuestionEntity> {
    val _sql: String = "SELECT * FROM question ORDER BY id ASC"
    return performSuspending(__db, true, false) { _connection ->
      val _stmt: SQLiteStatement = _connection.prepare(_sql)
      try {
        val _columnIndexOfId: Int = getColumnIndexOrThrow(_stmt, "id")
        val _columnIndexOfUuid: Int = getColumnIndexOrThrow(_stmt, "uuid")
        val _columnIndexOfSourceId: Int = getColumnIndexOrThrow(_stmt, "source_id")
        val _columnIndexOfExternalId: Int = getColumnIndexOrThrow(_stmt, "external_id")
        val _columnIndexOfContentHash: Int = getColumnIndexOrThrow(_stmt, "content_hash")
        val _columnIndexOfText: Int = getColumnIndexOrThrow(_stmt, "text")
        val _columnIndexOfFormat: Int = getColumnIndexOrThrow(_stmt, "format")
        val _columnIndexOfSubjectId: Int = getColumnIndexOrThrow(_stmt, "subject_id")
        val _columnIndexOfTopicId: Int = getColumnIndexOrThrow(_stmt, "topic_id")
        val _columnIndexOfSubTopicId: Int = getColumnIndexOrThrow(_stmt, "sub_topic_id")
        val _columnIndexOfDifficulty: Int = getColumnIndexOrThrow(_stmt, "difficulty")
        val _columnIndexOfSourceTimestampSec: Int = getColumnIndexOrThrow(_stmt,
            "source_timestamp_sec")
        val _columnIndexOfSourceUrl: Int = getColumnIndexOrThrow(_stmt, "source_url")
        val _columnIndexOfLanguage: Int = getColumnIndexOrThrow(_stmt, "language")
        val _columnIndexOfHasSolution: Int = getColumnIndexOrThrow(_stmt, "has_solution")
        val _columnIndexOfCreatedAt: Int = getColumnIndexOrThrow(_stmt, "created_at")
        val _columnIndexOfUpdatedAt: Int = getColumnIndexOrThrow(_stmt, "updated_at")
        val _result: MutableList<QuestionEntity> = mutableListOf()
        while (_stmt.step()) {
          val _item: QuestionEntity
          val _tmpId: Long
          _tmpId = _stmt.getLong(_columnIndexOfId)
          val _tmpUuid: String
          _tmpUuid = _stmt.getText(_columnIndexOfUuid)
          val _tmpSourceId: Long
          _tmpSourceId = _stmt.getLong(_columnIndexOfSourceId)
          val _tmpExternalId: String
          _tmpExternalId = _stmt.getText(_columnIndexOfExternalId)
          val _tmpContentHash: String
          _tmpContentHash = _stmt.getText(_columnIndexOfContentHash)
          val _tmpText: String
          _tmpText = _stmt.getText(_columnIndexOfText)
          val _tmpFormat: String
          _tmpFormat = _stmt.getText(_columnIndexOfFormat)
          val _tmpSubjectId: Long?
          if (_stmt.isNull(_columnIndexOfSubjectId)) {
            _tmpSubjectId = null
          } else {
            _tmpSubjectId = _stmt.getLong(_columnIndexOfSubjectId)
          }
          val _tmpTopicId: Long?
          if (_stmt.isNull(_columnIndexOfTopicId)) {
            _tmpTopicId = null
          } else {
            _tmpTopicId = _stmt.getLong(_columnIndexOfTopicId)
          }
          val _tmpSubTopicId: Long?
          if (_stmt.isNull(_columnIndexOfSubTopicId)) {
            _tmpSubTopicId = null
          } else {
            _tmpSubTopicId = _stmt.getLong(_columnIndexOfSubTopicId)
          }
          val _tmpDifficulty: String?
          if (_stmt.isNull(_columnIndexOfDifficulty)) {
            _tmpDifficulty = null
          } else {
            _tmpDifficulty = _stmt.getText(_columnIndexOfDifficulty)
          }
          val _tmpSourceTimestampSec: Int?
          if (_stmt.isNull(_columnIndexOfSourceTimestampSec)) {
            _tmpSourceTimestampSec = null
          } else {
            _tmpSourceTimestampSec = _stmt.getLong(_columnIndexOfSourceTimestampSec).toInt()
          }
          val _tmpSourceUrl: String?
          if (_stmt.isNull(_columnIndexOfSourceUrl)) {
            _tmpSourceUrl = null
          } else {
            _tmpSourceUrl = _stmt.getText(_columnIndexOfSourceUrl)
          }
          val _tmpLanguage: String?
          if (_stmt.isNull(_columnIndexOfLanguage)) {
            _tmpLanguage = null
          } else {
            _tmpLanguage = _stmt.getText(_columnIndexOfLanguage)
          }
          val _tmpHasSolution: Boolean
          val _tmp: Int
          _tmp = _stmt.getLong(_columnIndexOfHasSolution).toInt()
          _tmpHasSolution = _tmp != 0
          val _tmpCreatedAt: Long
          _tmpCreatedAt = _stmt.getLong(_columnIndexOfCreatedAt)
          val _tmpUpdatedAt: Long
          _tmpUpdatedAt = _stmt.getLong(_columnIndexOfUpdatedAt)
          _item =
              QuestionEntity(_tmpId,_tmpUuid,_tmpSourceId,_tmpExternalId,_tmpContentHash,_tmpText,_tmpFormat,_tmpSubjectId,_tmpTopicId,_tmpSubTopicId,_tmpDifficulty,_tmpSourceTimestampSec,_tmpSourceUrl,_tmpLanguage,_tmpHasSolution,_tmpCreatedAt,_tmpUpdatedAt)
          _result.add(_item)
        }
        _result
      } finally {
        _stmt.close()
      }
    }
  }

  public override suspend fun getQuestionsFiltered(
    topicId: Long?,
    difficulty: String?,
    limit: Int,
  ): List<QuestionEntity> {
    val _sql: String = """
        |
        |        SELECT * FROM question
        |        WHERE (? IS NULL OR topic_id = ?)
        |          AND (? IS NULL OR difficulty = ? COLLATE NOCASE)
        |        ORDER BY id ASC
        |        LIMIT ?
        |    
        """.trimMargin()
    return performSuspending(__db, true, false) { _connection ->
      val _stmt: SQLiteStatement = _connection.prepare(_sql)
      try {
        var _argIndex: Int = 1
        if (topicId == null) {
          _stmt.bindNull(_argIndex)
        } else {
          _stmt.bindLong(_argIndex, topicId)
        }
        _argIndex = 2
        if (topicId == null) {
          _stmt.bindNull(_argIndex)
        } else {
          _stmt.bindLong(_argIndex, topicId)
        }
        _argIndex = 3
        if (difficulty == null) {
          _stmt.bindNull(_argIndex)
        } else {
          _stmt.bindText(_argIndex, difficulty)
        }
        _argIndex = 4
        if (difficulty == null) {
          _stmt.bindNull(_argIndex)
        } else {
          _stmt.bindText(_argIndex, difficulty)
        }
        _argIndex = 5
        _stmt.bindLong(_argIndex, limit.toLong())
        val _columnIndexOfId: Int = getColumnIndexOrThrow(_stmt, "id")
        val _columnIndexOfUuid: Int = getColumnIndexOrThrow(_stmt, "uuid")
        val _columnIndexOfSourceId: Int = getColumnIndexOrThrow(_stmt, "source_id")
        val _columnIndexOfExternalId: Int = getColumnIndexOrThrow(_stmt, "external_id")
        val _columnIndexOfContentHash: Int = getColumnIndexOrThrow(_stmt, "content_hash")
        val _columnIndexOfText: Int = getColumnIndexOrThrow(_stmt, "text")
        val _columnIndexOfFormat: Int = getColumnIndexOrThrow(_stmt, "format")
        val _columnIndexOfSubjectId: Int = getColumnIndexOrThrow(_stmt, "subject_id")
        val _columnIndexOfTopicId: Int = getColumnIndexOrThrow(_stmt, "topic_id")
        val _columnIndexOfSubTopicId: Int = getColumnIndexOrThrow(_stmt, "sub_topic_id")
        val _columnIndexOfDifficulty: Int = getColumnIndexOrThrow(_stmt, "difficulty")
        val _columnIndexOfSourceTimestampSec: Int = getColumnIndexOrThrow(_stmt,
            "source_timestamp_sec")
        val _columnIndexOfSourceUrl: Int = getColumnIndexOrThrow(_stmt, "source_url")
        val _columnIndexOfLanguage: Int = getColumnIndexOrThrow(_stmt, "language")
        val _columnIndexOfHasSolution: Int = getColumnIndexOrThrow(_stmt, "has_solution")
        val _columnIndexOfCreatedAt: Int = getColumnIndexOrThrow(_stmt, "created_at")
        val _columnIndexOfUpdatedAt: Int = getColumnIndexOrThrow(_stmt, "updated_at")
        val _result: MutableList<QuestionEntity> = mutableListOf()
        while (_stmt.step()) {
          val _item: QuestionEntity
          val _tmpId: Long
          _tmpId = _stmt.getLong(_columnIndexOfId)
          val _tmpUuid: String
          _tmpUuid = _stmt.getText(_columnIndexOfUuid)
          val _tmpSourceId: Long
          _tmpSourceId = _stmt.getLong(_columnIndexOfSourceId)
          val _tmpExternalId: String
          _tmpExternalId = _stmt.getText(_columnIndexOfExternalId)
          val _tmpContentHash: String
          _tmpContentHash = _stmt.getText(_columnIndexOfContentHash)
          val _tmpText: String
          _tmpText = _stmt.getText(_columnIndexOfText)
          val _tmpFormat: String
          _tmpFormat = _stmt.getText(_columnIndexOfFormat)
          val _tmpSubjectId: Long?
          if (_stmt.isNull(_columnIndexOfSubjectId)) {
            _tmpSubjectId = null
          } else {
            _tmpSubjectId = _stmt.getLong(_columnIndexOfSubjectId)
          }
          val _tmpTopicId: Long?
          if (_stmt.isNull(_columnIndexOfTopicId)) {
            _tmpTopicId = null
          } else {
            _tmpTopicId = _stmt.getLong(_columnIndexOfTopicId)
          }
          val _tmpSubTopicId: Long?
          if (_stmt.isNull(_columnIndexOfSubTopicId)) {
            _tmpSubTopicId = null
          } else {
            _tmpSubTopicId = _stmt.getLong(_columnIndexOfSubTopicId)
          }
          val _tmpDifficulty: String?
          if (_stmt.isNull(_columnIndexOfDifficulty)) {
            _tmpDifficulty = null
          } else {
            _tmpDifficulty = _stmt.getText(_columnIndexOfDifficulty)
          }
          val _tmpSourceTimestampSec: Int?
          if (_stmt.isNull(_columnIndexOfSourceTimestampSec)) {
            _tmpSourceTimestampSec = null
          } else {
            _tmpSourceTimestampSec = _stmt.getLong(_columnIndexOfSourceTimestampSec).toInt()
          }
          val _tmpSourceUrl: String?
          if (_stmt.isNull(_columnIndexOfSourceUrl)) {
            _tmpSourceUrl = null
          } else {
            _tmpSourceUrl = _stmt.getText(_columnIndexOfSourceUrl)
          }
          val _tmpLanguage: String?
          if (_stmt.isNull(_columnIndexOfLanguage)) {
            _tmpLanguage = null
          } else {
            _tmpLanguage = _stmt.getText(_columnIndexOfLanguage)
          }
          val _tmpHasSolution: Boolean
          val _tmp: Int
          _tmp = _stmt.getLong(_columnIndexOfHasSolution).toInt()
          _tmpHasSolution = _tmp != 0
          val _tmpCreatedAt: Long
          _tmpCreatedAt = _stmt.getLong(_columnIndexOfCreatedAt)
          val _tmpUpdatedAt: Long
          _tmpUpdatedAt = _stmt.getLong(_columnIndexOfUpdatedAt)
          _item =
              QuestionEntity(_tmpId,_tmpUuid,_tmpSourceId,_tmpExternalId,_tmpContentHash,_tmpText,_tmpFormat,_tmpSubjectId,_tmpTopicId,_tmpSubTopicId,_tmpDifficulty,_tmpSourceTimestampSec,_tmpSourceUrl,_tmpLanguage,_tmpHasSolution,_tmpCreatedAt,_tmpUpdatedAt)
          _result.add(_item)
        }
        _result
      } finally {
        _stmt.close()
      }
    }
  }

  public override fun getQuestionCountByTopic(topicId: Long): Flow<Int> {
    val _sql: String = "SELECT COUNT(*) FROM question WHERE topic_id = ?"
    return createFlow(__db, false, arrayOf("question")) { _connection ->
      val _stmt: SQLiteStatement = _connection.prepare(_sql)
      try {
        var _argIndex: Int = 1
        _stmt.bindLong(_argIndex, topicId)
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

  public override suspend fun getQuestionCountByTopicOnce(topicId: Long): Int {
    val _sql: String = "SELECT COUNT(*) FROM question WHERE topic_id = ?"
    return performSuspending(__db, true, false) { _connection ->
      val _stmt: SQLiteStatement = _connection.prepare(_sql)
      try {
        var _argIndex: Int = 1
        _stmt.bindLong(_argIndex, topicId)
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
