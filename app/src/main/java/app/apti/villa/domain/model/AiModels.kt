package app.apti.villa.domain.model

enum class AiProviderType(val displayName: String) {
    GEMINI("Google Gemini"),
    OPENAI_COMPATIBLE("OpenAI Compatible (OpenAI, Grok, OpenRouter)"),
    ANTHROPIC("Anthropic Claude"),
}

data class ProviderConfig(
    val providerType: AiProviderType = AiProviderType.GEMINI,
    val endpointUrl: String,
    val modelName: String,
    val apiKey: String,
)

enum class AIChatRole {
    SYSTEM,
    USER,
    ASSISTANT,
}

data class AIChatMessage(
    val role: AIChatRole,
    val content: String,
)

data class AIChatRequest(
    val config: ProviderConfig,
    val messages: List<AIChatMessage>,
    val temperature: Float = 0.7f,
)

sealed interface AIChunk {
    data class Delta(val text: String) : AIChunk
    data class Error(val message: String, val isRetryable: Boolean = false) : AIChunk
    data object Done : AIChunk
}

data class AiContextPreview(
    val systemPrompt: String,
    val userPayload: String,
    val isAnswerGated: Boolean,
    val approximateTokens: Int,
)

data class AiConversation(
    val id: Long = 0,
    val uuid: String,
    val questionId: Long,
    val questionText: String,
    val topicName: String,
    val providerId: String,
    val model: String,
    val title: String,
    val createdAt: Long,
    val updatedAt: Long,
    val messages: List<AiMessage> = emptyList(),
)

data class AiMessage(
    val id: Long = 0,
    val conversationId: Long,
    val role: AIChatRole,
    val content: String,
    val createdAt: Long,
)
