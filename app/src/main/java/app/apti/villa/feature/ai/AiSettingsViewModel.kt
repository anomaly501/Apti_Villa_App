package app.apti.villa.feature.ai

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import app.apti.villa.ai.config.AiConfigRepository
import app.apti.villa.ai.config.AiPresets
import app.apti.villa.ai.provider.AiProviderRegistry
import app.apti.villa.domain.model.AiProviderType
import app.apti.villa.domain.model.ProviderConfig
import app.apti.villa.domain.repository.AiConversationRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

data class AiSettingsUiState(
    val selectedProvider: AiProviderType = AiProviderType.GEMINI,
    val endpointUrl: String = "",
    val modelName: String = "",
    val apiKeyInput: String = "",
    val maskedApiKey: String? = null,
    val hasConfiguredKey: Boolean = false,
    val isSaveHistoryEnabled: Boolean = true,
    val isTesting: Boolean = false,
    val isSaving: Boolean = false,
    val testSuccess: Boolean? = null,
    val infoMessage: String? = null,
    val errorMessage: String? = null,
)

@HiltViewModel
class AiSettingsViewModel @Inject constructor(
    private val aiConfigRepository: AiConfigRepository,
    private val aiProviderRegistry: AiProviderRegistry,
    private val aiConversationRepository: AiConversationRepository,
) : ViewModel() {

    private val _uiState = MutableStateFlow(AiSettingsUiState())
    val uiState: StateFlow<AiSettingsUiState> = _uiState.asStateFlow()

    init {
        observeConfig()
    }

    private fun observeConfig() {
        viewModelScope.launch {
            aiConfigRepository.selectedProvider.collectLatest { provider ->
                _uiState.update { it.copy(selectedProvider = provider) }
                loadProviderDetails(provider)
            }
        }
        viewModelScope.launch {
            aiConfigRepository.isSaveHistoryEnabled.collectLatest { enabled ->
                _uiState.update { it.copy(isSaveHistoryEnabled = enabled) }
            }
        }
    }

    private fun loadProviderDetails(provider: AiProviderType) {
        viewModelScope.launch {
            aiConfigRepository.getEndpoint(provider).collectLatest { ep ->
                _uiState.update { it.copy(endpointUrl = ep) }
            }
        }
        viewModelScope.launch {
            aiConfigRepository.getModel(provider).collectLatest { model ->
                _uiState.update { it.copy(modelName = model) }
            }
        }
        viewModelScope.launch {
            aiConfigRepository.getMaskedApiKey(provider).collectLatest { masked ->
                _uiState.update { it.copy(maskedApiKey = masked) }
            }
        }
        viewModelScope.launch {
            aiConfigRepository.hasConfiguredKey(provider).collectLatest { hasKey ->
                _uiState.update { it.copy(hasConfiguredKey = hasKey) }
            }
        }
    }

    fun onProviderSelected(type: AiProviderType) {
        viewModelScope.launch {
            aiConfigRepository.setSelectedProvider(type)
            _uiState.update {
                it.copy(
                    selectedProvider = type,
                    apiKeyInput = "",
                    testSuccess = null,
                    infoMessage = null,
                    errorMessage = null,
                )
            }
            loadProviderDetails(type)
        }
    }

    fun onEndpointChanged(url: String) {
        _uiState.update { it.copy(endpointUrl = url) }
    }

    fun onModelChanged(model: String) {
        _uiState.update { it.copy(modelName = model) }
    }

    fun onApiKeyInputChanged(key: String) {
        _uiState.update { it.copy(apiKeyInput = key) }
    }

    fun onSaveHistoryToggle(enabled: Boolean) {
        viewModelScope.launch {
            aiConfigRepository.setSaveHistoryEnabled(enabled)
        }
    }

    fun saveConfiguration() {
        val state = _uiState.value
        viewModelScope.launch {
            _uiState.update { it.copy(isSaving = true, infoMessage = null, errorMessage = null) }
            try {
                aiConfigRepository.setEndpoint(state.selectedProvider, state.endpointUrl)
                aiConfigRepository.setModel(state.selectedProvider, state.modelName)
                if (state.apiKeyInput.isNotBlank()) {
                    aiConfigRepository.saveApiKey(state.selectedProvider, state.apiKeyInput.trim())
                    _uiState.update { it.copy(apiKeyInput = "") }
                }
                _uiState.update {
                    it.copy(
                        isSaving = false,
                        infoMessage = "AI configuration saved successfully",
                    )
                }
            } catch (e: Exception) {
                _uiState.update {
                    it.copy(
                        isSaving = false,
                        errorMessage = "Failed to save: ${e.localizedMessage ?: "Unknown error"}",
                    )
                }
            }
        }
    }

    fun testConnection() {
        val state = _uiState.value
        val provider = aiProviderRegistry.getProvider(state.selectedProvider)

        viewModelScope.launch {
            _uiState.update { it.copy(isTesting = true, infoMessage = null, errorMessage = null, testSuccess = null) }
            try {
                val effectiveKey = if (state.apiKeyInput.isNotBlank()) {
                    state.apiKeyInput.trim()
                } else {
                    aiConfigRepository.getApiKey(state.selectedProvider) ?: ""
                }

                if (effectiveKey.isBlank()) {
                    _uiState.update {
                        it.copy(
                            isTesting = false,
                            errorMessage = "Please enter an API key to test connection",
                            testSuccess = false,
                        )
                    }
                    return@launch
                }

                val config = ProviderConfig(
                    providerType = state.selectedProvider,
                    endpointUrl = state.endpointUrl.ifBlank { AiPresets.defaultEndpoint(state.selectedProvider) },
                    modelName = state.modelName.ifBlank { AiPresets.defaultModel(state.selectedProvider) },
                    apiKey = effectiveKey,
                )

                val result = provider.validateKey(config)
                if (result.isSuccess) {
                    _uiState.update {
                        it.copy(
                            isTesting = false,
                            testSuccess = true,
                            infoMessage = "Connection successful! Provider is ready.",
                        )
                    }
                } else {
                    _uiState.update {
                        it.copy(
                            isTesting = false,
                            testSuccess = false,
                            errorMessage = result.exceptionOrNull()?.localizedMessage ?: "Validation failed",
                        )
                    }
                }
            } catch (e: Exception) {
                _uiState.update {
                    it.copy(
                        isTesting = false,
                        testSuccess = false,
                        errorMessage = "Connection test failed: ${e.localizedMessage ?: "Network error"}",
                    )
                }
            }
        }
    }

    fun deleteApiKey() {
        val state = _uiState.value
        viewModelScope.launch {
            aiConfigRepository.deleteApiKey(state.selectedProvider)
            _uiState.update {
                it.copy(
                    apiKeyInput = "",
                    maskedApiKey = null,
                    hasConfiguredKey = false,
                    infoMessage = "API key deleted from device Keystore",
                )
            }
        }
    }

    fun deleteAllHistory() {
        viewModelScope.launch {
            val success = aiConversationRepository.deleteAllConversations()
            if (success) {
                _uiState.update { it.copy(infoMessage = "All AI conversation history deleted") }
            } else {
                _uiState.update { it.copy(errorMessage = "Failed to clear AI history") }
            }
        }
    }

    fun clearMessages() {
        _uiState.update { it.copy(infoMessage = null, errorMessage = null) }
    }
}
