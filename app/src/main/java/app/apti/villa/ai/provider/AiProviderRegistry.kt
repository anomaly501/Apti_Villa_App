package app.apti.villa.ai.provider

import app.apti.villa.domain.model.AiProviderType
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AiProviderRegistry @Inject constructor(
    private val openAiProvider: OpenAiCompatibleProvider,
    private val geminiProvider: GeminiProvider,
    private val anthropicProvider: AnthropicProvider,
) {
    fun getProvider(type: AiProviderType): AIProvider = when (type) {
        AiProviderType.GEMINI -> geminiProvider
        AiProviderType.OPENAI_COMPATIBLE -> openAiProvider
        AiProviderType.ANTHROPIC -> anthropicProvider
    }
}
