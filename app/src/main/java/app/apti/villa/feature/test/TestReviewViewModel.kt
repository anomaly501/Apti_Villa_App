package app.apti.villa.feature.test

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import app.apti.villa.domain.model.AnswerStatus
import app.apti.villa.domain.model.QuestionReviewItem
import app.apti.villa.domain.model.TestScoreReport
import app.apti.villa.domain.repository.TestRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

enum class ReviewFilter {
    ALL, CORRECT, WRONG, UNATTEMPTED, MARKED
}

data class TestReviewUiState(
    val isLoading: Boolean = true,
    val report: TestScoreReport? = null,
    val selectedFilter: ReviewFilter = ReviewFilter.ALL,
    val filteredReviews: List<QuestionReviewItem> = emptyList(),
)

@HiltViewModel
class TestReviewViewModel @Inject constructor(
    private val testRepository: TestRepository,
    savedStateHandle: SavedStateHandle,
) : ViewModel() {

    private val _uiState = MutableStateFlow(TestReviewUiState())
    val uiState: StateFlow<TestReviewUiState> = _uiState.asStateFlow()

    init {
        val attemptId: Long? = savedStateHandle.get<Long>("attemptId")
        if (attemptId != null && attemptId > 0) {
            loadReview(attemptId)
        }
    }

    fun loadReview(attemptId: Long) {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }
            val report = testRepository.getTestScoreReport(attemptId)
            _uiState.update { current ->
                current.copy(
                    isLoading = false,
                    report = report,
                    filteredReviews = applyFilter(report?.questionReviews ?: emptyList(), current.selectedFilter),
                )
            }
        }
    }

    fun setFilter(filter: ReviewFilter) {
        _uiState.update { current ->
            current.copy(
                selectedFilter = filter,
                filteredReviews = applyFilter(current.report?.questionReviews ?: emptyList(), filter),
            )
        }
    }

    private fun applyFilter(items: List<QuestionReviewItem>, filter: ReviewFilter): List<QuestionReviewItem> {
        return when (filter) {
            ReviewFilter.ALL -> items
            ReviewFilter.CORRECT -> items.filter { it.isCorrect }
            ReviewFilter.WRONG -> items.filter { it.selectedOption != null && !it.isCorrect }
            ReviewFilter.UNATTEMPTED -> items.filter { it.selectedOption == null }
            ReviewFilter.MARKED -> items.filter {
                it.answer.status == AnswerStatus.MARKED || it.answer.status == AnswerStatus.ANSWERED_MARKED
            }
        }
    }
}
