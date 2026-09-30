package app.apti.villa.feature.test

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import app.apti.villa.domain.model.Attempt
import app.apti.villa.domain.model.TestWithStats
import app.apti.villa.domain.repository.TestRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

data class TestListUiState(
    val isLoading: Boolean = true,
    val tests: List<TestWithStats> = emptyList(),
    val resumeAttempt: Attempt? = null,
)

@HiltViewModel
class TestListViewModel @Inject constructor(
    private val testRepository: TestRepository,
) : ViewModel() {

    private val _uiState = MutableStateFlow(TestListUiState())
    val uiState: StateFlow<TestListUiState> = _uiState.asStateFlow()

    init {
        loadData()
    }

    fun loadData() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }
            val inProgress = testRepository.getLatestInProgressExamAttempt()

            testRepository.getActiveTestsWithStats().collect { testList ->
                _uiState.update { current ->
                    current.copy(
                        isLoading = false,
                        tests = testList,
                        resumeAttempt = inProgress,
                    )
                }
            }
        }
    }

    fun startTest(testId: Long, onStarted: (Long) -> Unit) {
        viewModelScope.launch {
            val attemptId = testRepository.startExamSession(testId)
            onStarted(attemptId)
        }
    }
}
