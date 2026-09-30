package app.apti.villa.ai.provider

import app.apti.villa.domain.model.AIChatRequest
import app.apti.villa.domain.model.AIChunk
import app.apti.villa.domain.model.AiProviderType
import app.apti.villa.domain.model.ProviderConfig
import kotlinx.coroutines.flow.Flow

interface AIProvider {
    val providerType: AiProviderType
    suspend fun validateKey(config: ProviderConfig): Result<Unit>
    fun streamChat(request: AIChatRequest): Flow<AIChunk>
}
