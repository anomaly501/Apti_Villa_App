package app.apti.villa.feature.ai

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import app.apti.villa.ai.config.AiConfigRepository
import app.apti.villa.ai.context.AiContextBuilder
import app.apti.villa.ai.provider.AiProviderRegistry
import app.apti.villa.domain.model.AIChatMessage
import app.apti.villa.domain.model.AIChatRole
import app.apti.villa.domain.model.AIChunk
import app.apti.villa.domain.model.AiContextPreview
import app.apti.villa.domain.model.Question
import app.apti.villa.domain.repository.AiConversationRepository
import app.apti.villa.domain.repository.PracticeRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

data class AiChatUiState(
    val question: Question? = null,
    val hasConfiguredKey: Boolean = false,
    val isLoading: Boolean = false,
    val isStreaming: Boolean = false,
    val messages: List<AIChatMessage> = emptyList(),
    val streamingCurrentText: String = "",
    val contextPreview: AiContextPreview? = null,
    val showPreviewDialog: Boolean = false,
    val errorMessage: String? = null,
    val isAnswerGated: Boolean = true, // default open in review/detail, closed if in practice without submit
)

@HiltViewModel
class AiChatViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val practiceRepository: PracticeRepository,
    private val aiConfigRepository: AiConfigRepository,
    private val aiProviderRegistry: AiProviderRegistry,
    private val aiContextBuilder: AiContextBuilder,
    private val aiConversationRepository: AiConversationRepository,
) : ViewModel() {

    private val questionId: Long = savedStateHandle.get<Long>("questionId") ?: 0L

    private val _uiState = MutableStateFlow(AiChatUiState())
    val uiState: StateFlow<AiChatUiState> = _uiState.asStateFlow()

    private var currentStreamJob: Job? = null
    private var conversationDbId: Long? = null

    init {
        loadQuestionAndConfig()
    }

    private fun loadQuestionAndConfig() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }
            val q = practiceRepository.getQuestionDetail(questionId)
            val config = aiConfigRepository.getActiveConfig()

            _uiState.update {
                it.copy(
                    question = q,
                    hasConfiguredKey = config != null && config.apiKey.isNotBlank(),
                    isLoading = false,
                )
            }
        }
    }

    fun togglePreviewDialog(query: String = "") {
        val q = _uiState.value.question ?: return
        val preview = aiContextBuilder.buildPreview(
            question = q,
            isAnswerGated = _uiState.value.isAnswerGated,
            userQuery = query.ifBlank { "What is the concept behind this problem?" },
        )
        _uiState.update {
            it.copy(
                contextPreview = preview,
                showPreviewDialog = !it.showPreviewDialog,
            )
        }
    }

    fun dismissPreviewDialog() {
        _uiState.update { it.copy(showPreviewDialog = false) }
    }

    fun sendMessage(query: String) {
        val clean = query.trim()
        if (clean.isBlank()) return
        val q = _uiState.value.question ?: return

        currentStreamJob?.cancel()

        currentStreamJob = viewModelScope.launch {
            val config = aiConfigRepository.getActiveConfig()
            if (config == null || config.apiKey.isBlank()) {
                _uiState.update {
                    it.copy(errorMessage = "Please configure your AI API key in Settings first.")
                }
                return@launch
            }

            // Append user message to state
            val userMsg = AIChatMessage(role = AIChatRole.USER, content = clean)
            _uiState.update {
                it.copy(
                    messages = it.messages + userMsg,
                    isStreaming = true,
                    streamingCurrentText = "",
                    errorMessage = null,
                )
            }

            // Build chat request
            val request = aiContextBuilder.buildChatRequest(
                config = config,
                question = q,
                isAnswerGated = _uiState.value.isAnswerGated,
                userQuery = clean,
                previousMessages = _uiState.value.messages,
            )

            val provider = aiProviderRegistry.getProvider(config.providerType)
            val assistantAccumulator = StringBuilder()

            try {
                // Ensure conversation DB entity exists if save history is enabled
                if (aiConfigRepository.isSaveHistoryEnabled.first()) {
                    if (conversationDbId == null) {
                        conversationDbId = aiConversationRepository.createConversation(
                            questionId = q.id,
                            providerId = config.providerType.name,
                            model = config.modelName,
                            title = clean.take(40),
                        )
                    }
                    conversationDbId?.let { cId ->
                        aiConversationRepository.addMessage(cId, AIChatRole.USER, clean)
                    }
                }

                provider.streamChat(request).collect { chunk ->
                    when (chunk) {
                        is AIChunk.Delta -> {
                            assistantAccumulator.append(chunk.text)
                            _uiState.update {
                                it.copy(streamingCurrentText = assistantAccumulator.toString())
                            }
                        }
                        is AIChunk.Error -> {
                            _uiState.update {
                                it.copy(
                                    isStreaming = false,
                                    errorMessage = chunk.message,
                                )
                            }
                        }
                        is AIChunk.Done -> {
                            val finalAnswer = assistantAccumulator.toString()
                            if (finalAnswer.isNotBlank()) {
                                val assistantMsg = AIChatMessage(
                                    role = AIChatRole.ASSISTANT,
                                    content = finalAnswer,
                                )
                                _uiState.update {
                                    it.copy(
                                        messages = it.messages + assistantMsg,
                                        isStreaming = false,
                                        streamingCurrentText = "",
                                    )
                                }
                                conversationDbId?.let { cId ->
                                    aiConversationRepository.addMessage(cId, AIChatRole.ASSISTANT, finalAnswer)
                                }
                            } else {
                                _uiState.update { it.copy(isStreaming = false) }
                            }
                        }
                    }
                }
            } catch (e: Exception) {
                _uiState.update {
                    it.copy(
                        isStreaming = false,
                        errorMessage = "Stream error: ${e.localizedMessage ?: "Unknown failure"}",
                    )
                }
            }
        }
    }

    fun clearError() {
        _uiState.update { it.copy(errorMessage = null) }
    }
}
