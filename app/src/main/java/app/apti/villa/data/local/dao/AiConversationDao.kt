package app.apti.villa.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import app.apti.villa.data.local.entity.AiConversationEntity
import app.apti.villa.data.local.entity.AiMessageEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface AiConversationDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertConversation(conversation: AiConversationEntity): Long

    @Update
    suspend fun updateConversation(conversation: AiConversationEntity)

    @Query("SELECT * FROM ai_conversations WHERE id = :id LIMIT 1")
    suspend fun getConversationById(id: Long): AiConversationEntity?

    @Query("SELECT * FROM ai_conversations WHERE question_id = :questionId ORDER BY updated_at DESC")
    fun getConversationsForQuestion(questionId: Long): Flow<List<AiConversationEntity>>

    @Query("SELECT * FROM ai_conversations ORDER BY updated_at DESC")
    fun getAllConversations(): Flow<List<AiConversationEntity>>

    @Query("SELECT * FROM ai_conversations ORDER BY updated_at DESC")
    suspend fun getAllConversationsOnce(): List<AiConversationEntity>

    @Query("DELETE FROM ai_conversations WHERE id = :id")
    suspend fun deleteConversationById(id: Long): Int

    @Query("DELETE FROM ai_conversations")
    suspend fun deleteAllConversations(): Int

    // Messages
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMessage(message: AiMessageEntity): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMessages(messages: List<AiMessageEntity>): List<Long>

    @Query("SELECT * FROM ai_messages WHERE conversation_id = :conversationId ORDER BY created_at ASC")
    fun getMessagesForConversation(conversationId: Long): Flow<List<AiMessageEntity>>

    @Query("SELECT * FROM ai_messages WHERE conversation_id = :conversationId ORDER BY created_at ASC")
    suspend fun getMessagesForConversationOnce(conversationId: Long): List<AiMessageEntity>
}
