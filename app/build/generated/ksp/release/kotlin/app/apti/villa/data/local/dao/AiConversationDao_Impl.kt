package app.apti.villa.`data`.local.dao

import androidx.room.EntityDeleteOrUpdateAdapter
import androidx.room.EntityInsertAdapter
import androidx.room.RoomDatabase
import androidx.room.coroutines.createFlow
import androidx.room.util.getColumnIndexOrThrow
import androidx.room.util.getTotalChangedRows
import androidx.room.util.performSuspending
import androidx.sqlite.SQLiteStatement
import app.apti.villa.`data`.local.entity.AiConversationEntity
import app.apti.villa.`data`.local.entity.AiMessageEntity
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
public class AiConversationDao_Impl(
  __db: RoomDatabase,
) : AiConversationDao {
  private val __db: RoomDatabase

  private val __insertAdapterOfAiConversationEntity: EntityInsertAdapter<AiConversationEntity>

  private val __insertAdapterOfAiMessageEntity: EntityInsertAdapter<AiMessageEntity>

  private val __updateAdapterOfAiConversationEntity:
      EntityDeleteOrUpdateAdapter<AiConversationEntity>
  init {
    this.__db = __db
    this.__insertAdapterOfAiConversationEntity = object :
        EntityInsertAdapter<AiConversationEntity>() {
      protected override fun createQuery(): String =
          "INSERT OR REPLACE INTO `ai_conversations` (`id`,`uuid`,`question_id`,`provider_id`,`model`,`title`,`created_at`,`updated_at`) VALUES (nullif(?, 0),?,?,?,?,?,?,?)"

      protected override fun bind(statement: SQLiteStatement, entity: AiConversationEntity) {
        statement.bindLong(1, entity.id)
        statement.bindText(2, entity.uuid)
        statement.bindLong(3, entity.questionId)
        statement.bindText(4, entity.providerId)
        statement.bindText(5, entity.model)
        statement.bindText(6, entity.title)
        statement.bindLong(7, entity.createdAt)
        statement.bindLong(8, entity.updatedAt)
      }
    }
    this.__insertAdapterOfAiMessageEntity = object : EntityInsertAdapter<AiMessageEntity>() {
      protected override fun createQuery(): String =
          "INSERT OR REPLACE INTO `ai_messages` (`id`,`conversation_id`,`role`,`content`,`created_at`) VALUES (nullif(?, 0),?,?,?,?)"

      protected override fun bind(statement: SQLiteStatement, entity: AiMessageEntity) {
        statement.bindLong(1, entity.id)
        statement.bindLong(2, entity.conversationId)
        statement.bindText(3, entity.role)
        statement.bindText(4, entity.content)
        statement.bindLong(5, entity.createdAt)
      }
    }
    this.__updateAdapterOfAiConversationEntity = object :
        EntityDeleteOrUpdateAdapter<AiConversationEntity>() {
      protected override fun createQuery(): String =
          "UPDATE OR ABORT `ai_conversations` SET `id` = ?,`uuid` = ?,`question_id` = ?,`provider_id` = ?,`model` = ?,`title` = ?,`created_at` = ?,`updated_at` = ? WHERE `id` = ?"

      protected override fun bind(statement: SQLiteStatement, entity: AiConversationEntity) {
        statement.bindLong(1, entity.id)
        statement.bindText(2, entity.uuid)
        statement.bindLong(3, entity.questionId)
        statement.bindText(4, entity.providerId)
        statement.bindText(5, entity.model)
        statement.bindText(6, entity.title)
        statement.bindLong(7, entity.createdAt)
        statement.bindLong(8, entity.updatedAt)
        statement.bindLong(9, entity.id)
      }
    }
  }

  public override suspend fun insertConversation(conversation: AiConversationEntity): Long =
      performSuspending(__db, false, true) { _connection ->
    val _result: Long = __insertAdapterOfAiConversationEntity.insertAndReturnId(_connection,
        conversation)
    _result
  }

  public override suspend fun insertMessage(message: AiMessageEntity): Long =
      performSuspending(__db, false, true) { _connection ->
    val _result: Long = __insertAdapterOfAiMessageEntity.insertAndReturnId(_connection, message)
    _result
  }

  public override suspend fun insertMessages(messages: List<AiMessageEntity>): List<Long> =
      performSuspending(__db, false, true) { _connection ->
    val _result: List<Long> = __insertAdapterOfAiMessageEntity.insertAndReturnIdsList(_connection,
        messages)
    _result
  }

  public override suspend fun updateConversation(conversation: AiConversationEntity): Unit =
      performSuspending(__db, false, true) { _connection ->
    __updateAdapterOfAiConversationEntity.handle(_connection, conversation)
  }

  public override suspend fun getConversationById(id: Long): AiConversationEntity? {
    val _sql: String = "SELECT * FROM ai_conversations WHERE id = ? LIMIT 1"
    return performSuspending(__db, true, false) { _connection ->
      val _stmt: SQLiteStatement = _connection.prepare(_sql)
      try {
        var _argIndex: Int = 1
        _stmt.bindLong(_argIndex, id)
        val _columnIndexOfId: Int = getColumnIndexOrThrow(_stmt, "id")
        val _columnIndexOfUuid: Int = getColumnIndexOrThrow(_stmt, "uuid")
        val _columnIndexOfQuestionId: Int = getColumnIndexOrThrow(_stmt, "question_id")
        val _columnIndexOfProviderId: Int = getColumnIndexOrThrow(_stmt, "provider_id")
        val _columnIndexOfModel: Int = getColumnIndexOrThrow(_stmt, "model")
        val _columnIndexOfTitle: Int = getColumnIndexOrThrow(_stmt, "title")
        val _columnIndexOfCreatedAt: Int = getColumnIndexOrThrow(_stmt, "created_at")
        val _columnIndexOfUpdatedAt: Int = getColumnIndexOrThrow(_stmt, "updated_at")
        val _result: AiConversationEntity?
        if (_stmt.step()) {
          val _tmpId: Long
          _tmpId = _stmt.getLong(_columnIndexOfId)
          val _tmpUuid: String
          _tmpUuid = _stmt.getText(_columnIndexOfUuid)
          val _tmpQuestionId: Long
          _tmpQuestionId = _stmt.getLong(_columnIndexOfQuestionId)
          val _tmpProviderId: String
          _tmpProviderId = _stmt.getText(_columnIndexOfProviderId)
          val _tmpModel: String
          _tmpModel = _stmt.getText(_columnIndexOfModel)
          val _tmpTitle: String
          _tmpTitle = _stmt.getText(_columnIndexOfTitle)
          val _tmpCreatedAt: Long
          _tmpCreatedAt = _stmt.getLong(_columnIndexOfCreatedAt)
          val _tmpUpdatedAt: Long
          _tmpUpdatedAt = _stmt.getLong(_columnIndexOfUpdatedAt)
          _result =
              AiConversationEntity(_tmpId,_tmpUuid,_tmpQuestionId,_tmpProviderId,_tmpModel,_tmpTitle,_tmpCreatedAt,_tmpUpdatedAt)
        } else {
          _result = null
        }
        _result
      } finally {
        _stmt.close()
      }
    }
  }

  public override fun getConversationsForQuestion(questionId: Long):
      Flow<List<AiConversationEntity>> {
    val _sql: String =
        "SELECT * FROM ai_conversations WHERE question_id = ? ORDER BY updated_at DESC"
    return createFlow(__db, false, arrayOf("ai_conversations")) { _connection ->
      val _stmt: SQLiteStatement = _connection.prepare(_sql)
      try {
        var _argIndex: Int = 1
        _stmt.bindLong(_argIndex, questionId)
        val _columnIndexOfId: Int = getColumnIndexOrThrow(_stmt, "id")
        val _columnIndexOfUuid: Int = getColumnIndexOrThrow(_stmt, "uuid")
        val _columnIndexOfQuestionId: Int = getColumnIndexOrThrow(_stmt, "question_id")
        val _columnIndexOfProviderId: Int = getColumnIndexOrThrow(_stmt, "provider_id")
        val _columnIndexOfModel: Int = getColumnIndexOrThrow(_stmt, "model")
        val _columnIndexOfTitle: Int = getColumnIndexOrThrow(_stmt, "title")
        val _columnIndexOfCreatedAt: Int = getColumnIndexOrThrow(_stmt, "created_at")
        val _columnIndexOfUpdatedAt: Int = getColumnIndexOrThrow(_stmt, "updated_at")
        val _result: MutableList<AiConversationEntity> = mutableListOf()
        while (_stmt.step()) {
          val _item: AiConversationEntity
          val _tmpId: Long
          _tmpId = _stmt.getLong(_columnIndexOfId)
          val _tmpUuid: String
          _tmpUuid = _stmt.getText(_columnIndexOfUuid)
          val _tmpQuestionId: Long
          _tmpQuestionId = _stmt.getLong(_columnIndexOfQuestionId)
          val _tmpProviderId: String
          _tmpProviderId = _stmt.getText(_columnIndexOfProviderId)
          val _tmpModel: String
          _tmpModel = _stmt.getText(_columnIndexOfModel)
          val _tmpTitle: String
          _tmpTitle = _stmt.getText(_columnIndexOfTitle)
          val _tmpCreatedAt: Long
          _tmpCreatedAt = _stmt.getLong(_columnIndexOfCreatedAt)
          val _tmpUpdatedAt: Long
          _tmpUpdatedAt = _stmt.getLong(_columnIndexOfUpdatedAt)
          _item =
              AiConversationEntity(_tmpId,_tmpUuid,_tmpQuestionId,_tmpProviderId,_tmpModel,_tmpTitle,_tmpCreatedAt,_tmpUpdatedAt)
          _result.add(_item)
        }
        _result
      } finally {
        _stmt.close()
      }
    }
  }

  public override fun getAllConversations(): Flow<List<AiConversationEntity>> {
    val _sql: String = "SELECT * FROM ai_conversations ORDER BY updated_at DESC"
    return createFlow(__db, false, arrayOf("ai_conversations")) { _connection ->
      val _stmt: SQLiteStatement = _connection.prepare(_sql)
      try {
        val _columnIndexOfId: Int = getColumnIndexOrThrow(_stmt, "id")
        val _columnIndexOfUuid: Int = getColumnIndexOrThrow(_stmt, "uuid")
        val _columnIndexOfQuestionId: Int = getColumnIndexOrThrow(_stmt, "question_id")
        val _columnIndexOfProviderId: Int = getColumnIndexOrThrow(_stmt, "provider_id")
        val _columnIndexOfModel: Int = getColumnIndexOrThrow(_stmt, "model")
        val _columnIndexOfTitle: Int = getColumnIndexOrThrow(_stmt, "title")
        val _columnIndexOfCreatedAt: Int = getColumnIndexOrThrow(_stmt, "created_at")
        val _columnIndexOfUpdatedAt: Int = getColumnIndexOrThrow(_stmt, "updated_at")
        val _result: MutableList<AiConversationEntity> = mutableListOf()
        while (_stmt.step()) {
          val _item: AiConversationEntity
          val _tmpId: Long
          _tmpId = _stmt.getLong(_columnIndexOfId)
          val _tmpUuid: String
          _tmpUuid = _stmt.getText(_columnIndexOfUuid)
          val _tmpQuestionId: Long
          _tmpQuestionId = _stmt.getLong(_columnIndexOfQuestionId)
          val _tmpProviderId: String
          _tmpProviderId = _stmt.getText(_columnIndexOfProviderId)
          val _tmpModel: String
          _tmpModel = _stmt.getText(_columnIndexOfModel)
          val _tmpTitle: String
          _tmpTitle = _stmt.getText(_columnIndexOfTitle)
          val _tmpCreatedAt: Long
          _tmpCreatedAt = _stmt.getLong(_columnIndexOfCreatedAt)
          val _tmpUpdatedAt: Long
          _tmpUpdatedAt = _stmt.getLong(_columnIndexOfUpdatedAt)
          _item =
              AiConversationEntity(_tmpId,_tmpUuid,_tmpQuestionId,_tmpProviderId,_tmpModel,_tmpTitle,_tmpCreatedAt,_tmpUpdatedAt)
          _result.add(_item)
        }
        _result
      } finally {
        _stmt.close()
      }
    }
  }

  public override suspend fun getAllConversationsOnce(): List<AiConversationEntity> {
    val _sql: String = "SELECT * FROM ai_conversations ORDER BY updated_at DESC"
    return performSuspending(__db, true, false) { _connection ->
      val _stmt: SQLiteStatement = _connection.prepare(_sql)
      try {
        val _columnIndexOfId: Int = getColumnIndexOrThrow(_stmt, "id")
        val _columnIndexOfUuid: Int = getColumnIndexOrThrow(_stmt, "uuid")
        val _columnIndexOfQuestionId: Int = getColumnIndexOrThrow(_stmt, "question_id")
        val _columnIndexOfProviderId: Int = getColumnIndexOrThrow(_stmt, "provider_id")
        val _columnIndexOfModel: Int = getColumnIndexOrThrow(_stmt, "model")
        val _columnIndexOfTitle: Int = getColumnIndexOrThrow(_stmt, "title")
        val _columnIndexOfCreatedAt: Int = getColumnIndexOrThrow(_stmt, "created_at")
        val _columnIndexOfUpdatedAt: Int = getColumnIndexOrThrow(_stmt, "updated_at")
        val _result: MutableList<AiConversationEntity> = mutableListOf()
        while (_stmt.step()) {
          val _item: AiConversationEntity
          val _tmpId: Long
          _tmpId = _stmt.getLong(_columnIndexOfId)
          val _tmpUuid: String
          _tmpUuid = _stmt.getText(_columnIndexOfUuid)
          val _tmpQuestionId: Long
          _tmpQuestionId = _stmt.getLong(_columnIndexOfQuestionId)
          val _tmpProviderId: String
          _tmpProviderId = _stmt.getText(_columnIndexOfProviderId)
          val _tmpModel: String
          _tmpModel = _stmt.getText(_columnIndexOfModel)
          val _tmpTitle: String
          _tmpTitle = _stmt.getText(_columnIndexOfTitle)
          val _tmpCreatedAt: Long
          _tmpCreatedAt = _stmt.getLong(_columnIndexOfCreatedAt)
          val _tmpUpdatedAt: Long
          _tmpUpdatedAt = _stmt.getLong(_columnIndexOfUpdatedAt)
          _item =
              AiConversationEntity(_tmpId,_tmpUuid,_tmpQuestionId,_tmpProviderId,_tmpModel,_tmpTitle,_tmpCreatedAt,_tmpUpdatedAt)
          _result.add(_item)
        }
        _result
      } finally {
        _stmt.close()
      }
    }
  }

  public override fun getMessagesForConversation(conversationId: Long):
      Flow<List<AiMessageEntity>> {
    val _sql: String = "SELECT * FROM ai_messages WHERE conversation_id = ? ORDER BY created_at ASC"
    return createFlow(__db, false, arrayOf("ai_messages")) { _connection ->
      val _stmt: SQLiteStatement = _connection.prepare(_sql)
      try {
        var _argIndex: Int = 1
        _stmt.bindLong(_argIndex, conversationId)
        val _columnIndexOfId: Int = getColumnIndexOrThrow(_stmt, "id")
        val _columnIndexOfConversationId: Int = getColumnIndexOrThrow(_stmt, "conversation_id")
        val _columnIndexOfRole: Int = getColumnIndexOrThrow(_stmt, "role")
        val _columnIndexOfContent: Int = getColumnIndexOrThrow(_stmt, "content")
        val _columnIndexOfCreatedAt: Int = getColumnIndexOrThrow(_stmt, "created_at")
        val _result: MutableList<AiMessageEntity> = mutableListOf()
        while (_stmt.step()) {
          val _item: AiMessageEntity
          val _tmpId: Long
          _tmpId = _stmt.getLong(_columnIndexOfId)
          val _tmpConversationId: Long
          _tmpConversationId = _stmt.getLong(_columnIndexOfConversationId)
          val _tmpRole: String
          _tmpRole = _stmt.getText(_columnIndexOfRole)
          val _tmpContent: String
          _tmpContent = _stmt.getText(_columnIndexOfContent)
          val _tmpCreatedAt: Long
          _tmpCreatedAt = _stmt.getLong(_columnIndexOfCreatedAt)
          _item = AiMessageEntity(_tmpId,_tmpConversationId,_tmpRole,_tmpContent,_tmpCreatedAt)
          _result.add(_item)
        }
        _result
      } finally {
        _stmt.close()
      }
    }
  }

  public override suspend fun getMessagesForConversationOnce(conversationId: Long):
      List<AiMessageEntity> {
    val _sql: String = "SELECT * FROM ai_messages WHERE conversation_id = ? ORDER BY created_at ASC"
    return performSuspending(__db, true, false) { _connection ->
      val _stmt: SQLiteStatement = _connection.prepare(_sql)
      try {
        var _argIndex: Int = 1
        _stmt.bindLong(_argIndex, conversationId)
        val _columnIndexOfId: Int = getColumnIndexOrThrow(_stmt, "id")
        val _columnIndexOfConversationId: Int = getColumnIndexOrThrow(_stmt, "conversation_id")
        val _columnIndexOfRole: Int = getColumnIndexOrThrow(_stmt, "role")
        val _columnIndexOfContent: Int = getColumnIndexOrThrow(_stmt, "content")
        val _columnIndexOfCreatedAt: Int = getColumnIndexOrThrow(_stmt, "created_at")
        val _result: MutableList<AiMessageEntity> = mutableListOf()
        while (_stmt.step()) {
          val _item: AiMessageEntity
          val _tmpId: Long
          _tmpId = _stmt.getLong(_columnIndexOfId)
          val _tmpConversationId: Long
          _tmpConversationId = _stmt.getLong(_columnIndexOfConversationId)
          val _tmpRole: String
          _tmpRole = _stmt.getText(_columnIndexOfRole)
          val _tmpContent: String
          _tmpContent = _stmt.getText(_columnIndexOfContent)
          val _tmpCreatedAt: Long
          _tmpCreatedAt = _stmt.getLong(_columnIndexOfCreatedAt)
          _item = AiMessageEntity(_tmpId,_tmpConversationId,_tmpRole,_tmpContent,_tmpCreatedAt)
          _result.add(_item)
        }
        _result
      } finally {
        _stmt.close()
      }
    }
  }

  public override suspend fun deleteConversationById(id: Long): Int {
    val _sql: String = "DELETE FROM ai_conversations WHERE id = ?"
    return performSuspending(__db, false, true) { _connection ->
      val _stmt: SQLiteStatement = _connection.prepare(_sql)
      try {
        var _argIndex: Int = 1
        _stmt.bindLong(_argIndex, id)
        _stmt.step()
        getTotalChangedRows(_connection)
      } finally {
        _stmt.close()
      }
    }
  }

  public override suspend fun deleteAllConversations(): Int {
    val _sql: String = "DELETE FROM ai_conversations"
    return performSuspending(__db, false, true) { _connection ->
      val _stmt: SQLiteStatement = _connection.prepare(_sql)
      try {
        _stmt.step()
        getTotalChangedRows(_connection)
      } finally {
        _stmt.close()
      }
    }
  }

  public companion object {
    public fun getRequiredConverters(): List<KClass<*>> = emptyList()
  }
}
