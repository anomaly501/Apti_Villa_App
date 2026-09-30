package app.apti.villa.data.repository

import app.apti.villa.core.common.IoDispatcher
import app.apti.villa.data.local.dao.AiConversationDao
import app.apti.villa.data.local.dao.QuestionDao
import app.apti.villa.data.local.dao.TopicDao
import app.apti.villa.data.local.entity.AiConversationEntity
import app.apti.villa.data.local.entity.AiMessageEntity
import app.apti.villa.domain.model.AIChatRole
import app.apti.villa.domain.model.AiConversation
import app.apti.villa.domain.model.AiMessage
import app.apti.villa.domain.repository.AiConversationRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AiConversationRepositoryImpl @Inject constructor(
    private val aiConversationDao: AiConversationDao,
    private val questionDao: QuestionDao,
    private val topicDao: TopicDao,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher,
) : AiConversationRepository {

    override suspend fun createConversation(
        questionId: Long,
        providerId: String,
        model: String,
        title: String,
    ): Long = withContext(ioDispatcher) {
        val entity = AiConversationEntity(
            questionId = questionId,
            providerId = providerId,
            model = model,
            title = title,
            createdAt = System.currentTimeMillis(),
            updatedAt = System.currentTimeMillis(),
        )
        aiConversationDao.insertConversation(entity)
    }

    override suspend fun addMessage(
        conversationId: Long,
        role: AIChatRole,
        content: String,
    ): Long = withContext(ioDispatcher) {
        val roleStr = when (role) {
            AIChatRole.SYSTEM -> "system"
            AIChatRole.USER -> "user"
            AIChatRole.ASSISTANT -> "assistant"
        }
        val entity = AiMessageEntity(
            conversationId = conversationId,
            role = roleStr,
            content = content,
            createdAt = System.currentTimeMillis(),
        )
        // Also update conversation updatedAt timestamp
        val conv = aiConversationDao.getConversationById(conversationId)
        if (conv != null) {
            aiConversationDao.updateConversation(conv.copy(updatedAt = System.currentTimeMillis()))
        }
        aiConversationDao.insertMessage(entity)
    }

    override fun getConversationsForQuestion(questionId: Long): Flow<List<AiConversation>> {
        return aiConversationDao.getConversationsForQuestion(questionId).map { entities ->
            entities.map { it.toDomain(questionDao, topicDao) }
        }
    }

    override fun getAllConversations(): Flow<List<AiConversation>> {
        return aiConversationDao.getAllConversations().map { entities ->
            entities.map { it.toDomain(questionDao, topicDao) }
        }
    }

    override fun getMessages(conversationId: Long): Flow<List<AiMessage>> {
        return aiConversationDao.getMessagesForConversation(conversationId).map { entities ->
            entities.map { entity ->
                val role = when (entity.role) {
                    "system" -> AIChatRole.SYSTEM
                    "assistant" -> AIChatRole.ASSISTANT
                    else -> AIChatRole.USER
                }
                AiMessage(
                    id = entity.id,
                    conversationId = entity.conversationId,
                    role = role,
                    content = entity.content,
                    createdAt = entity.createdAt,
                )
            }
        }
    }

    override suspend fun deleteConversation(id: Long): Boolean = withContext(ioDispatcher) {
        aiConversationDao.deleteConversationById(id) > 0
    }

    override suspend fun deleteAllConversations(): Boolean = withContext(ioDispatcher) {
        aiConversationDao.deleteAllConversations() >= 0
    }

    private suspend fun AiConversationEntity.toDomain(
        qDao: QuestionDao,
        tDao: TopicDao,
    ): AiConversation {
        val question = qDao.getQuestionById(questionId)
        val qText = question?.text ?: "Question #$questionId"
        val topicName = question?.topicId?.let { tDao.getTopicById(it)?.name } ?: "General"

        return AiConversation(
            id = id,
            uuid = uuid,
            questionId = questionId,
            questionText = qText,
            topicName = topicName,
            providerId = providerId,
            model = model,
            title = title,
            createdAt = createdAt,
            updatedAt = updatedAt,
        )
    }
}
