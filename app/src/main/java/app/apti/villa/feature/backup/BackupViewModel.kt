package app.apti.villa.feature.backup

import android.content.Context
import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import app.apti.villa.domain.model.BackupPreview
import app.apti.villa.domain.model.RestoreMode
import app.apti.villa.domain.repository.BackupRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

data class BackupUiState(
    val isLoading: Boolean = false,
    val infoMessage: String? = null,
    val errorMessage: String? = null,
    val preview: BackupPreview? = null,
    val pendingRestoreUri: Uri? = null,
    val restoreMode: RestoreMode = RestoreMode.MERGE,
)

@HiltViewModel
class BackupViewModel @Inject constructor(
    private val backupRepository: BackupRepository,
    @ApplicationContext private val context: Context,
) : ViewModel() {

    private val _uiState = MutableStateFlow(BackupUiState())
    val uiState: StateFlow<BackupUiState> = _uiState.asStateFlow()

    fun exportCompleteBackup(uri: Uri) {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, infoMessage = null, errorMessage = null) }
            try {
                val success = context.contentResolver.openOutputStream(uri)?.use { stream ->
                    backupRepository.exportCompleteBackup(stream)
                } ?: false

                if (success) {
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            infoMessage = "Complete backup (.aptbackup) exported successfully",
                        )
                    }
                } else {
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            errorMessage = "Failed to export complete backup",
                        )
                    }
                }
            } catch (e: Exception) {
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        errorMessage = "Export failed: ${e.localizedMessage ?: "Unknown error"}",
                    )
                }
            }
        }
    }

    fun exportQuestionsJson(uri: Uri) {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, infoMessage = null, errorMessage = null) }
            try {
                val success = context.contentResolver.openOutputStream(uri)?.use { stream ->
                    backupRepository.exportQuestionsJson(stream)
                } ?: false

                if (success) {
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            infoMessage = "Questions JSON exported successfully",
                        )
                    }
                } else {
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            errorMessage = "Failed to export questions JSON",
                        )
                    }
                }
            } catch (e: Exception) {
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        errorMessage = "Export failed: ${e.localizedMessage ?: "Unknown error"}",
                    )
                }
            }
        }
    }

    fun exportTestsJson(uri: Uri) {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, infoMessage = null, errorMessage = null) }
            try {
                val success = context.contentResolver.openOutputStream(uri)?.use { stream ->
                    backupRepository.exportTestsJson(stream)
                } ?: false

                if (success) {
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            infoMessage = "Tests JSON exported successfully",
                        )
                    }
                } else {
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            errorMessage = "Failed to export tests JSON",
                        )
                    }
                }
            } catch (e: Exception) {
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        errorMessage = "Export failed: ${e.localizedMessage ?: "Unknown error"}",
                    )
                }
            }
        }
    }

    fun onBackupFileSelected(uri: Uri) {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, infoMessage = null, errorMessage = null) }
            try {
                val preview = context.contentResolver.openInputStream(uri)?.use { stream ->
                    backupRepository.previewBackup(stream)
                }

                if (preview != null && preview.isValid) {
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            preview = preview,
                            pendingRestoreUri = uri,
                            restoreMode = RestoreMode.MERGE,
                        )
                    }
                } else {
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            errorMessage = preview?.errorMessage ?: "Invalid or corrupted .aptbackup archive",
                        )
                    }
                }
            } catch (e: Exception) {
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        errorMessage = "Failed to read backup: ${e.localizedMessage ?: "Unknown error"}",
                    )
                }
            }
        }
    }

    fun setRestoreMode(mode: RestoreMode) {
        _uiState.update { it.copy(restoreMode = mode) }
    }

    fun dismissRestorePreview() {
        _uiState.update { it.copy(preview = null, pendingRestoreUri = null) }
    }

    fun confirmRestore() {
        val uri = _uiState.value.pendingRestoreUri ?: return
        val mode = _uiState.value.restoreMode

        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, infoMessage = null, errorMessage = null) }
            try {
                val result = context.contentResolver.openInputStream(uri)?.use { stream ->
                    backupRepository.restoreBackup(stream, mode)
                }

                if (result != null && result.success) {
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            preview = null,
                            pendingRestoreUri = null,
                            infoMessage = "Restore completed! Restored: ${result.questionsRestored} questions, ${result.testsRestored} tests, ${result.attemptsRestored} attempts.",
                        )
                    }
                } else {
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            preview = null,
                            pendingRestoreUri = null,
                            errorMessage = result?.message ?: "Failed to restore backup archive",
                        )
                    }
                }
            } catch (e: Exception) {
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        preview = null,
                        pendingRestoreUri = null,
                        errorMessage = "Restore failed: ${e.localizedMessage ?: "Unknown error"}",
                    )
                }
            }
        }
    }

    fun clearMessages() {
        _uiState.update { it.copy(infoMessage = null, errorMessage = null) }
    }
}
