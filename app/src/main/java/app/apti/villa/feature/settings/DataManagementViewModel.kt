package app.apti.villa.feature.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import app.apti.villa.domain.repository.BackupRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

data class DataManagementUiState(
    val isLoading: Boolean = false,
    val infoMessage: String? = null,
    val errorMessage: String? = null,
    val showClearHistoryDialog: Boolean = false,
    val showClearMistakesDialog: Boolean = false,
    val showWipeAllDialog: Boolean = false,
    val wipeConfirmationInput: String = "",
)

@HiltViewModel
class DataManagementViewModel @Inject constructor(
    private val backupRepository: BackupRepository,
) : ViewModel() {

    private val _uiState = MutableStateFlow(DataManagementUiState())
    val uiState: StateFlow<DataManagementUiState> = _uiState.asStateFlow()

    fun showClearHistoryDialog() {
        _uiState.update { it.copy(showClearHistoryDialog = true) }
    }

    fun hideClearHistoryDialog() {
        _uiState.update { it.copy(showClearHistoryDialog = false) }
    }

    fun showClearMistakesDialog() {
        _uiState.update { it.copy(showClearMistakesDialog = true) }
    }

    fun hideClearMistakesDialog() {
        _uiState.update { it.copy(showClearMistakesDialog = false) }
    }

    fun showWipeAllDialog() {
        _uiState.update { it.copy(showWipeAllDialog = true, wipeConfirmationInput = "") }
    }

    fun hideWipeAllDialog() {
        _uiState.update { it.copy(showWipeAllDialog = false, wipeConfirmationInput = "") }
    }

    fun onWipeConfirmationInputChanged(input: String) {
        _uiState.update { it.copy(wipeConfirmationInput = input) }
    }

    fun clearAttemptHistory() {
        hideClearHistoryDialog()
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, infoMessage = null, errorMessage = null) }
            try {
                val success = backupRepository.clearAttemptHistory()
                if (success) {
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            infoMessage = "Attempt history cleared successfully",
                        )
                    }
                } else {
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            errorMessage = "Failed to clear attempt history",
                        )
                    }
                }
            } catch (e: Exception) {
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        errorMessage = "Error: ${e.localizedMessage ?: "Unknown error"}",
                    )
                }
            }
        }
    }

    fun clearMistakesAndRevision() {
        hideClearMistakesDialog()
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, infoMessage = null, errorMessage = null) }
            try {
                val success = backupRepository.clearMistakesAndRevision()
                if (success) {
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            infoMessage = "Mistake Book and Spaced Revision queue reset successfully",
                        )
                    }
                } else {
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            errorMessage = "Failed to reset mistake book",
                        )
                    }
                }
            } catch (e: Exception) {
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        errorMessage = "Error: ${e.localizedMessage ?: "Unknown error"}",
                    )
                }
            }
        }
    }

    fun wipeAllUserData() {
        if (_uiState.value.wipeConfirmationInput.trim() != "DELETE") return
        hideWipeAllDialog()
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, infoMessage = null, errorMessage = null) }
            try {
                val success = backupRepository.wipeAllUserData()
                if (success) {
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            infoMessage = "All user study data wiped successfully (Factory Reset complete)",
                        )
                    }
                } else {
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            errorMessage = "Failed to wipe user data",
                        )
                    }
                }
            } catch (e: Exception) {
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        errorMessage = "Error: ${e.localizedMessage ?: "Unknown error"}",
                    )
                }
            }
        }
    }

    fun clearMessages() {
        _uiState.update { it.copy(infoMessage = null, errorMessage = null) }
    }
}
