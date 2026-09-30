package app.apti.villa.feature.question

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import app.apti.villa.domain.engine.hint.HintLadder
import app.apti.villa.domain.model.*
import app.apti.villa.domain.repository.PracticeRepository
import app.apti.villa.domain.repository.TagRepository
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

data class QuestionUiState(
    val isLoading: Boolean = true,
    val attempt: Attempt? = null,
    val items: List<PracticeSessionItem> = emptyList(),
    val currentIndex: Int = 0,
    val questionElapsedSec: Long = 0,
    val isFinished: Boolean = false,
    val allTags: List<Tag> = emptyList(),
    val showTagDialog: Boolean = false,
    val snackbarMessage: String? = null,
) {
    val currentItem: PracticeSessionItem?
        get() = items.getOrNull(currentIndex)

    val hasNext: Boolean
        get() = currentIndex < items.lastIndex

    val hasPrevious: Boolean
        get() = currentIndex > 0

    val totalQuestions: Int
        get() = items.size
}

@HiltViewModel
class QuestionViewModel @Inject constructor(
    private val practiceRepository: PracticeRepository,
    private val tagRepository: TagRepository,
    savedStateHandle: SavedStateHandle,
) : ViewModel() {

    private val _uiState = MutableStateFlow(QuestionUiState())
    val uiState: StateFlow<QuestionUiState> = _uiState.asStateFlow()

    private var timerJob: Job? = null
    private var currentAttemptId: Long = 0L

    init {
        val attemptId: Long? = savedStateHandle.get<Long>("attemptId")
        if (attemptId != null && attemptId > 0) {
            loadSession(attemptId)
        }
        loadTags()
    }

    private fun loadTags() {
        viewModelScope.launch {
            tagRepository.getAllTags().collect { tags ->
                _uiState.update { it.copy(allTags = tags) }
            }
        }
    }

    fun loadSession(attemptId: Long) {
        currentAttemptId = attemptId
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }
            val attempt = practiceRepository.getAttemptOnce(attemptId)

            practiceRepository.getPracticeItems(attemptId).collect { items ->
                val currIndex = _uiState.value.currentIndex.coerceIn(0, (items.size - 1).coerceAtLeast(0))
                val currItem = items.getOrNull(currIndex)

                _uiState.update { current ->
                    current.copy(
                        isLoading = false,
                        attempt = attempt,
                        items = items,
                        currentIndex = currIndex,
                        questionElapsedSec = (currItem?.answer?.timeSpentMs ?: 0L) / 1000L,
                    )
                }
                startTimer()
            }
        }
    }

    private fun startTimer() {
        timerJob?.cancel()
        timerJob = viewModelScope.launch {
            while (isActive) {
                delay(1000L)
                val currentItem = _uiState.value.currentItem ?: continue
                val newElapsedSec = _uiState.value.questionElapsedSec + 1
                _uiState.update { it.copy(questionElapsedSec = newElapsedSec) }

                // Periodic flush every 5 seconds to Room
                if (newElapsedSec % 5 == 0L) {
                    practiceRepository.updateAnswerTime(
                        answerId = currentItem.answer.id,
                        timeSpentMs = newElapsedSec * 1000L,
                    )
                    _uiState.value.attempt?.let { att ->
                        practiceRepository.updateAttemptActiveTime(
                            attemptId = att.id,
                            activeElapsedMs = att.activeElapsedMs + 5000L,
                        )
                    }
                }
            }
        }
    }

    private fun flushCurrentAnswerTime() {
        val currentItem = _uiState.value.currentItem ?: return
        val elapsedSec = _uiState.value.questionElapsedSec
        viewModelScope.launch {
            practiceRepository.updateAnswerTime(
                answerId = currentItem.answer.id,
                timeSpentMs = elapsedSec * 1000L,
            )
        }
    }

    fun selectOption(option: QuestionOption) {
        val currentItem = _uiState.value.currentItem ?: return
        // Don't re-submit if already solved or revealed
        if (currentItem.answer.isCorrect == true || currentItem.answer.revealed) return

        viewModelScope.launch {
            val now = System.currentTimeMillis()
            val firstSeen = currentItem.answer.firstSeenAt ?: now
            val msSinceFirstSeen = (now - firstSeen).coerceAtLeast(0)

            practiceRepository.recordSubmission(
                answerId = currentItem.answer.id,
                optionId = option.id,
                msSinceFirstSeen = msSinceFirstSeen,
                isCorrect = option.isCorrect,
            )

            if (option.isCorrect) {
                val level = HintLadder.calculateSolvedAtHintLevel(currentItem.answer.hintsOpened, currentItem.answer.revealed)
                val msg = if (level == 0) "Correct! Solved with no hints! 🎯" else "Correct! Solved at Hint level $level 💡"
                _uiState.update { it.copy(snackbarMessage = msg) }
            }
        }
    }

    fun unlockNextHint() {
        val currentItem = _uiState.value.currentItem ?: return
        val nextHintNumber = currentItem.answer.hintsOpened + 1
        val nextHint = currentItem.question.hints.firstOrNull { it.number == nextHintNumber } ?: return

        viewModelScope.launch {
            val now = System.currentTimeMillis()
            val firstSeen = currentItem.answer.firstSeenAt ?: now
            val msSinceFirstSeen = (now - firstSeen).coerceAtLeast(0)

            practiceRepository.recordHintUsage(
                answerId = currentItem.answer.id,
                hintId = nextHint.id,
                msSinceFirstSeen = msSinceFirstSeen,
            )
        }
    }

    fun revealAnswer() {
        val currentItem = _uiState.value.currentItem ?: return
        viewModelScope.launch {
            val correctOption = currentItem.question.options.firstOrNull { it.isCorrect }
            practiceRepository.revealAnswer(
                answerId = currentItem.answer.id,
                correctOptionId = correctOption?.id,
            )
            _uiState.update { it.copy(snackbarMessage = "Answer revealed") }
        }
    }

    fun toggleSaveTrick(trick: Trick) {
        viewModelScope.launch {
            val saved = practiceRepository.toggleSaveTrick(trick.id)
            _uiState.update {
                it.copy(snackbarMessage = if (saved) "Trick saved to your Notebook ⭐" else "Trick removed from saved")
            }
        }
    }

    fun toggleBookmark() {
        val currentItem = _uiState.value.currentItem ?: return
        val importantTag = _uiState.value.allTags.firstOrNull { it.name.equals("Important", ignoreCase = true) } ?: return
        viewModelScope.launch {
            val bookmarked = practiceRepository.toggleQuestionTag(currentItem.question.id, importantTag.id)
            _uiState.update {
                it.copy(snackbarMessage = if (bookmarked) "Question bookmarked 🔖" else "Bookmark removed")
            }
        }
    }

    fun toggleDifficult() {
        val currentItem = _uiState.value.currentItem ?: return
        val difficultTag = _uiState.value.allTags.firstOrNull { it.name.equals("Difficult", ignoreCase = true) } ?: return
        viewModelScope.launch {
            val marked = practiceRepository.toggleQuestionTag(currentItem.question.id, difficultTag.id)
            _uiState.update {
                it.copy(snackbarMessage = if (marked) "Marked as Difficult ⚠️" else "Removed from Difficult")
            }
        }
    }

    fun toggleTag(tagId: Long) {
        val currentItem = _uiState.value.currentItem ?: return
        viewModelScope.launch {
            practiceRepository.toggleQuestionTag(currentItem.question.id, tagId)
        }
    }

    fun showTagDialog(show: Boolean) {
        _uiState.update { it.copy(showTagDialog = show) }
    }

    fun dismissSnackbar() {
        _uiState.update { it.copy(snackbarMessage = null) }
    }

    fun nextQuestion() {
        flushCurrentAnswerTime()
        val nextIdx = _uiState.value.currentIndex + 1
        if (nextIdx < _uiState.value.items.size) {
            val nextItem = _uiState.value.items[nextIdx]
            _uiState.update {
                it.copy(
                    currentIndex = nextIdx,
                    questionElapsedSec = nextItem.answer.timeSpentMs / 1000L,
                )
            }
        }
    }

    fun previousQuestion() {
        flushCurrentAnswerTime()
        val prevIdx = _uiState.value.currentIndex - 1
        if (prevIdx >= 0) {
            val prevItem = _uiState.value.items[prevIdx]
            _uiState.update {
                it.copy(
                    currentIndex = prevIdx,
                    questionElapsedSec = prevItem.answer.timeSpentMs / 1000L,
                )
            }
        }
    }

    fun finishSession(onFinished: () -> Unit) {
        flushCurrentAnswerTime()
        timerJob?.cancel()
        viewModelScope.launch {
            practiceRepository.completeAttempt(currentAttemptId)
            _uiState.update { it.copy(isFinished = true) }
            onFinished()
        }
    }

    override fun onCleared() {
        super.onCleared()
        flushCurrentAnswerTime()
        timerJob?.cancel()
    }
}
