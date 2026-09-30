package app.apti.villa.feature.inventory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import app.apti.villa.domain.model.InventoryFullData
import app.apti.villa.domain.model.InventoryQuestionItem
import app.apti.villa.domain.repository.InventoryRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

data class InventoryUiState(
    val isLoading: Boolean = true,
    val inventoryData: InventoryFullData? = null,
    val selectedTab: Int = 0, // 0: Questions, 1: Tests, 2: Learning
    val questionFilter: String = "ALL", // ALL, ATTEMPTED, UNATTEMPTED, BOOKMARKED, DIFFICULT, MISTAKES
    val filteredQuestions: List<InventoryQuestionItem> = emptyList(),
)

@HiltViewModel
class InventoryViewModel @Inject constructor(
    private val inventoryRepository: InventoryRepository,
) : ViewModel() {

    private val _uiState = MutableStateFlow(InventoryUiState())
    val uiState: StateFlow<InventoryUiState> = _uiState.asStateFlow()

    init {
        loadInventory()
        loadQuestions("ALL")
    }

    private fun loadInventory() {
        viewModelScope.launch {
            inventoryRepository.getInventoryFullData().collect { data ->
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        inventoryData = data,
                    )
                }
            }
        }
    }

    fun selectTab(tab: Int) {
        _uiState.update { it.copy(selectedTab = tab) }
    }

    fun setQuestionFilter(filter: String) {
        _uiState.update { it.copy(questionFilter = filter) }
        loadQuestions(filter)
    }

    private fun loadQuestions(filter: String) {
        viewModelScope.launch {
            inventoryRepository.getFilteredQuestions(
                subjectId = null,
                topicId = null,
                difficulty = null,
                filterMode = filter,
            ).collect { questions ->
                _uiState.update { it.copy(filteredQuestions = questions) }
            }
        }
    }
}
