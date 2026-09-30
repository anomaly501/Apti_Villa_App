package app.apti.villa.domain.repository

import app.apti.villa.domain.model.AIChatRole
import app.apti.villa.domain.model.AiConversation
import app.apti.villa.domain.model.AiMessage
import kotlinx.coroutines.flow.Flow

interface AiConversationRepository {
    suspend fun createConversation(
        questionId: Long,
        providerId: String,
        model: String,
        title: String,
    ): Long

    suspend fun addMessage(
        conversationId: Long,
        role: AIChatRole,
        content: String,
    ): Long

    fun getConversationsForQuestion(questionId: Long): Flow<List<AiConversation>>
    fun getAllConversations(): Flow<List<AiConversation>>
    fun getMessages(conversationId: Long): Flow<List<AiMessage>>
    suspend fun deleteConversation(id: Long): Boolean
    suspend fun deleteAllConversations(): Boolean
}
