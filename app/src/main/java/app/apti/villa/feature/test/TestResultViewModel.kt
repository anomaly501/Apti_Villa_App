package app.apti.villa.feature.test

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import app.apti.villa.domain.model.TestScoreReport
import app.apti.villa.domain.repository.TestRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

data class TestResultUiState(
    val isLoading: Boolean = true,
    val report: TestScoreReport? = null,
)

@HiltViewModel
class TestResultViewModel @Inject constructor(
    private val testRepository: TestRepository,
    savedStateHandle: SavedStateHandle,
) : ViewModel() {

    private val _uiState = MutableStateFlow(TestResultUiState())
    val uiState: StateFlow<TestResultUiState> = _uiState.asStateFlow()

    init {
        val attemptId: Long? = savedStateHandle.get<Long>("attemptId")
        if (attemptId != null && attemptId > 0) {
            loadReport(attemptId)
        }
    }

    fun loadReport(attemptId: Long) {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }
            val report = testRepository.getTestScoreReport(attemptId)
            _uiState.update {
                it.copy(
                    isLoading = false,
                    report = report,
                )
            }
        }
    }
}
