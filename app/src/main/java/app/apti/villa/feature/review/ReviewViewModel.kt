package app.apti.villa.feature.review

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import app.apti.villa.domain.model.*
import app.apti.villa.domain.repository.ReviewRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

data class ReviewUiState(
    val selectedTab: Int = 0,
    val isLoading: Boolean = true,
    val activeMistakes: List<Mistake> = emptyList(),
    val resolvedMistakes: List<Mistake> = emptyList(),
    val mistakeFilter: String = "ACTIVE",
    val dueRevisionItems: List<RevisionItem> = emptyList(),
    val boxStats: RevisionBoxStats = RevisionBoxStats(),
    val bookmarkedQuestions: List<BookmarkedQuestion> = emptyList(),
    val savedTricks: List<SavedTrickWithQuestion> = emptyList(),
    val bookmarkSubTab: Int = 0, // 0: Questions, 1: Tricks
)

@HiltViewModel
class ReviewViewModel @Inject constructor(
    private val reviewRepository: ReviewRepository,
) : ViewModel() {

    private val _uiState = MutableStateFlow(ReviewUiState())
    val uiState: StateFlow<ReviewUiState> = _uiState.asStateFlow()

    init {
        loadData()
    }

    fun loadData() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }

            launch {
                reviewRepository.getActiveMistakes().collect { mistakes ->
                    _uiState.update { it.copy(activeMistakes = mistakes, isLoading = false) }
                }
            }

            launch {
                reviewRepository.getResolvedMistakes().collect { resolved ->
                    _uiState.update { it.copy(resolvedMistakes = resolved) }
                }
            }

            launch {
                reviewRepository.getDueRevisionItems().collect { due ->
                    _uiState.update { it.copy(dueRevisionItems = due) }
                }
            }

            launch {
                reviewRepository.getRevisionBoxStats().collect { stats ->
                    _uiState.update { it.copy(boxStats = stats) }
                }
            }

            launch {
                reviewRepository.getBookmarkedQuestions().collect { bookmarks ->
                    _uiState.update { it.copy(bookmarkedQuestions = bookmarks) }
                }
            }

            launch {
                reviewRepository.getSavedTricksWithQuestions().collect { tricks ->
                    _uiState.update { it.copy(savedTricks = tricks) }
                }
            }
        }
    }

    fun selectTab(tab: Int) {
        _uiState.update { it.copy(selectedTab = tab) }
    }

    fun setMistakeFilter(filter: String) {
        _uiState.update { it.copy(mistakeFilter = filter) }
    }

    fun setBookmarkSubTab(subTab: Int) {
        _uiState.update { it.copy(bookmarkSubTab = subTab) }
    }

    fun deleteMistake(questionId: Long) {
        viewModelScope.launch {
            reviewRepository.deleteMistake(questionId)
        }
    }

    fun deleteSavedTrick(trickId: Long) {
        viewModelScope.launch {
            reviewRepository.deleteSavedTrick(trickId)
        }
    }

    fun setRevisionSuspended(questionId: Long, suspended: Boolean) {
        viewModelScope.launch {
            reviewRepository.setRevisionSuspended(questionId, suspended)
        }
    }

    fun startMistakePractice(onStarted: (Long) -> Unit) {
        viewModelScope.launch {
            val attemptId = reviewRepository.startMistakePracticeSession()
            if (attemptId > 0) {
                onStarted(attemptId)
            }
        }
    }

    fun startRevisionPractice(onStarted: (Long) -> Unit) {
        viewModelScope.launch {
            val attemptId = reviewRepository.startRevisionPracticeSession()
            if (attemptId > 0) {
                onStarted(attemptId)
            }
        }
    }

    fun startBookmarkedPractice(onStarted: (Long) -> Unit) {
        viewModelScope.launch {
            val attemptId = reviewRepository.startBookmarkedPracticeSession()
            if (attemptId > 0) {
                onStarted(attemptId)
            }
        }
    }
}
