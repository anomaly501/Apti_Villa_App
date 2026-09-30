package app.apti.villa.feature.test

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import app.apti.villa.domain.engine.test.TestTimerPolicy
import app.apti.villa.domain.model.*
import app.apti.villa.domain.repository.TestRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import javax.inject.Inject

data class ExamUiState(
    val isLoading: Boolean = true,
    val attempt: Attempt? = null,
    val test: Test? = null,
    val items: List<ExamQuestionItem> = emptyList(),
    val currentIndex: Int = 0,
    val remainingTimeSec: Int? = null,
    val selectedOptionId: Long? = null,
    val showPalette: Boolean = false,
    val showSubmitDialog: Boolean = false,
    val isSubmitted: Boolean = false,
) {
    val currentItem: ExamQuestionItem?
        get() = items.getOrNull(currentIndex)

    val totalQuestions: Int
        get() = items.size

    val answeredCount: Int
        get() = items.count { it.answer.status == AnswerStatus.ANSWERED || it.answer.status == AnswerStatus.ANSWERED_MARKED }

    val unansweredCount: Int
        get() = items.count { it.answer.selectedOptionId == null }

    val markedCount: Int
        get() = items.count { it.answer.status == AnswerStatus.MARKED || it.answer.status == AnswerStatus.ANSWERED_MARKED }

    val notVisitedCount: Int
        get() = items.count { it.answer.status == AnswerStatus.UNSEEN }
}

@HiltViewModel
class ExamViewModel @Inject constructor(
    private val testRepository: TestRepository,
    savedStateHandle: SavedStateHandle,
) : ViewModel() {

    private val _uiState = MutableStateFlow(ExamUiState())
    val uiState: StateFlow<ExamUiState> = _uiState.asStateFlow()

    private var timerJob: Job? = null
    private var currentAttemptId: Long = 0L
    private var activeElapsedMs: Long = 0L

    init {
        val attemptId: Long? = savedStateHandle.get<Long>("attemptId")
        if (attemptId != null && attemptId > 0) {
            loadExam(attemptId)
        }
    }

    fun loadExam(attemptId: Long) {
        currentAttemptId = attemptId
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }
            val attempt = testRepository.getAttempt(attemptId)
                ?: return@launch
            val test = attempt.testId?.let { testRepository.getTestById(it) }

            activeElapsedMs = attempt.activeElapsedMs

            testRepository.getExamQuestionItems(attemptId).collect { items ->
                val currIndex = _uiState.value.currentIndex.coerceIn(0, (items.size - 1).coerceAtLeast(0))
                val currItem = items.getOrNull(currIndex)

                // Mark current question as SEEN if it was UNSEEN
                if (currItem != null && currItem.answer.status == AnswerStatus.UNSEEN) {
                    testRepository.saveOptionResponse(
                        answerId = currItem.answer.id,
                        optionId = null,
                        status = AnswerStatus.SEEN,
                        timeSpentMs = currItem.answer.timeSpentMs,
                    )
                }

                _uiState.update { current ->
                    current.copy(
                        isLoading = false,
                        attempt = attempt,
                        test = test,
                        items = items,
                        currentIndex = currIndex,
                        selectedOptionId = currItem?.answer?.selectedOptionId,
                        remainingTimeSec = TestTimerPolicy.calculateRemainingSeconds(
                            timeLimitSec = test?.durationSec,
                            activeElapsedMs = activeElapsedMs,
                        ),
                    )
                }

                startTimer(test?.durationSec)
            }
        }
    }

    private fun startTimer(durationSec: Int?) {
        timerJob?.cancel()
        timerJob = viewModelScope.launch {
            while (isActive) {
                delay(1000L)
                activeElapsedMs += 1000L
                val remaining = TestTimerPolicy.calculateRemainingSeconds(durationSec, activeElapsedMs)

                _uiState.update { it.copy(remainingTimeSec = remaining) }

                // Periodic flush active time to Room
                if ((activeElapsedMs / 1000L) % 5 == 0L) {
                    testRepository.updateExamActiveTime(currentAttemptId, activeElapsedMs)
                }

                // Check auto-submit on time expiry
                if (durationSec != null && remaining != null && remaining <= 0) {
                    submitExam {}
                    break
                }
            }
        }
    }

    fun selectOption(optionId: Long) {
        _uiState.update { it.copy(selectedOptionId = optionId) }
    }

    fun clearResponse() {
        val currItem = _uiState.value.currentItem ?: return
        _uiState.update { it.copy(selectedOptionId = null) }
        viewModelScope.launch {
            testRepository.saveOptionResponse(
                answerId = currItem.answer.id,
                optionId = null,
                status = AnswerStatus.SEEN,
                timeSpentMs = currItem.answer.timeSpentMs,
            )
        }
    }

    fun saveAndNext() {
        val currItem = _uiState.value.currentItem ?: return
        val selected = _uiState.value.selectedOptionId
        val status = if (selected != null) AnswerStatus.ANSWERED else AnswerStatus.SEEN

        viewModelScope.launch {
            testRepository.saveOptionResponse(
                answerId = currItem.answer.id,
                optionId = selected,
                status = status,
                timeSpentMs = currItem.answer.timeSpentMs + 1000L,
            )
            moveToNext()
        }
    }

    fun markForReviewAndNext() {
        val currItem = _uiState.value.currentItem ?: return
        val selected = _uiState.value.selectedOptionId
        val status = if (selected != null) AnswerStatus.ANSWERED_MARKED else AnswerStatus.MARKED

        viewModelScope.launch {
            testRepository.saveOptionResponse(
                answerId = currItem.answer.id,
                optionId = selected,
                status = status,
                timeSpentMs = currItem.answer.timeSpentMs + 1000L,
            )
            moveToNext()
        }
    }

    fun previousQuestion() {
        val prevIdx = _uiState.value.currentIndex - 1
        if (prevIdx >= 0) {
            val prevItem = _uiState.value.items.getOrNull(prevIdx)
            _uiState.update {
                it.copy(
                    currentIndex = prevIdx,
                    selectedOptionId = prevItem?.answer?.selectedOptionId,
                )
            }
        }
    }

    private fun moveToNext() {
        val nextIdx = _uiState.value.currentIndex + 1
        if (nextIdx < _uiState.value.items.size) {
            val nextItem = _uiState.value.items.getOrNull(nextIdx)
            _uiState.update {
                it.copy(
                    currentIndex = nextIdx,
                    selectedOptionId = nextItem?.answer?.selectedOptionId,
                )
            }
        }
    }

    fun jumpToQuestion(index: Int) {
        if (index in _uiState.value.items.indices) {
            val targetItem = _uiState.value.items[index]
            _uiState.update {
                it.copy(
                    currentIndex = index,
                    selectedOptionId = targetItem.answer.selectedOptionId,
                    showPalette = false,
                )
            }
        }
    }

    fun togglePalette(show: Boolean) {
        _uiState.update { it.copy(showPalette = show) }
    }

    fun toggleSubmitDialog(show: Boolean) {
        _uiState.update { it.copy(showSubmitDialog = show) }
    }

    fun submitExam(onSubmitted: (Long) -> Unit) {
        timerJob?.cancel()
        val currItem = _uiState.value.currentItem
        val selected = _uiState.value.selectedOptionId

        viewModelScope.launch {
            if (currItem != null && selected != null) {
                testRepository.saveOptionResponse(
                    answerId = currItem.answer.id,
                    optionId = selected,
                    status = AnswerStatus.ANSWERED,
                    timeSpentMs = currItem.answer.timeSpentMs,
                )
            }
            testRepository.submitExam(currentAttemptId)
            _uiState.update { it.copy(isSubmitted = true, showSubmitDialog = false) }
            onSubmitted(currentAttemptId)
        }
    }

    override fun onCleared() {
        super.onCleared()
        timerJob?.cancel()
        viewModelScope.launch {
            testRepository.updateExamActiveTime(currentAttemptId, activeElapsedMs)
        }
    }
}
