package app.apti.villa.ai.config

import app.apti.villa.domain.model.AiProviderType

object AiPresets {

    fun defaultEndpoint(type: AiProviderType): String = when (type) {
        AiProviderType.GEMINI -> "https://generativelanguage.googleapis.com"
        AiProviderType.OPENAI_COMPATIBLE -> "https://api.openai.com/v1"
        AiProviderType.ANTHROPIC -> "https://api.anthropic.com/v1"
    }

    fun defaultModel(type: AiProviderType): String = when (type) {
        AiProviderType.GEMINI -> "gemini-1.5-flash"
        AiProviderType.OPENAI_COMPATIBLE -> "gpt-4o-mini"
        AiProviderType.ANTHROPIC -> "claude-3-5-haiku-20241022"
    }

    fun suggestedModels(type: AiProviderType): List<String> = when (type) {
        AiProviderType.GEMINI -> listOf(
            "gemini-1.5-flash",
            "gemini-1.5-pro",
            "gemini-2.0-flash-exp",
        )
        AiProviderType.OPENAI_COMPATIBLE -> listOf(
            "gpt-4o-mini",
            "gpt-4o",
            "grok-beta",
            "meta-llama/llama-3.1-70b-instruct",
        )
        AiProviderType.ANTHROPIC -> listOf(
            "claude-3-5-haiku-20241022",
            "claude-3-5-sonnet-20241022",
        )
    }

    val openAiCompatibleEndpoints = listOf(
        "https://api.openai.com/v1" to "OpenAI Official",
        "https://api.x.ai/v1" to "xAI Grok",
        "https://openrouter.ai/api/v1" to "OpenRouter",
        "http://localhost:11434/v1" to "Local Ollama",
    )
}
