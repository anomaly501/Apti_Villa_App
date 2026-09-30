package app.apti.villa.feature.practice

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import app.apti.villa.domain.model.Attempt
import app.apti.villa.domain.model.PracticeFilter
import app.apti.villa.domain.model.TopicWithQuestionCount
import app.apti.villa.domain.repository.PracticeRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

data class PracticeHubUiState(
    val isLoading: Boolean = true,
    val resumeAttempt: Attempt? = null,
    val topics: List<TopicWithQuestionCount> = emptyList(),
    val selectedTopicId: Long? = null,
    val selectedDifficulty: String? = null,
    val selectedCount: Int = 10,
    val totalAvailableQuestions: Int = 0,
)

@HiltViewModel
class PracticeViewModel @Inject constructor(
    private val practiceRepository: PracticeRepository,
) : ViewModel() {

    private val _uiState = MutableStateFlow(PracticeHubUiState())
    val uiState: StateFlow<PracticeHubUiState> = _uiState.asStateFlow()

    init {
        loadData()
    }

    fun loadData() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }
            val inProgress = practiceRepository.getLatestInProgressAttempt()

            practiceRepository.getTopicsWithQuestionCounts().collect { topicCounts ->
                val totalQs = topicCounts.sumOf { it.questionCount }
                _uiState.update { current ->
                    current.copy(
                        isLoading = false,
                        resumeAttempt = inProgress,
                        topics = topicCounts,
                        totalAvailableQuestions = totalQs,
                    )
                }
            }
        }
    }

    fun selectTopic(topicId: Long?) {
        _uiState.update { it.copy(selectedTopicId = topicId) }
    }

    fun selectDifficulty(difficulty: String?) {
        _uiState.update { it.copy(selectedDifficulty = difficulty) }
    }

    fun selectCount(count: Int) {
        _uiState.update { it.copy(selectedCount = count) }
    }

    fun startPractice(onStarted: (Long) -> Unit) {
        viewModelScope.launch {
            val state = _uiState.value
            val filter = PracticeFilter(
                topicId = state.selectedTopicId,
                difficulty = state.selectedDifficulty,
                count = state.selectedCount,
            )
            val attemptId = practiceRepository.startPracticeSession(filter)
            onStarted(attemptId)
        }
    }
}
