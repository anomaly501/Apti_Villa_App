package app.apti.villa.feature.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import app.apti.villa.domain.model.SearchCategory
import app.apti.villa.domain.model.SearchResultItem
import app.apti.villa.domain.repository.SearchRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

data class SearchUiState(
    val query: String = "",
    val category: SearchCategory = SearchCategory.ALL,
    val results: List<SearchResultItem> = emptyList(),
    val isSearching: Boolean = false,
    val hasSearched: Boolean = false,
)

@OptIn(FlowPreview::class)
@HiltViewModel
class SearchViewModel @Inject constructor(
    private val searchRepository: SearchRepository,
) : ViewModel() {

    private val _uiState = MutableStateFlow(SearchUiState())
    val uiState: StateFlow<SearchUiState> = _uiState.asStateFlow()

    private val _queryFlow = MutableStateFlow("")
    private var searchJob: Job? = null

    init {
        viewModelScope.launch {
            _queryFlow
                .debounce(200)
                .distinctUntilChanged()
                .collect { q ->
                    performSearch(q, _uiState.value.category)
                }
        }
    }

    fun onQueryChanged(newQuery: String) {
        _uiState.update { it.copy(query = newQuery) }
        _queryFlow.value = newQuery
    }

    fun onCategorySelected(newCategory: SearchCategory) {
        _uiState.update { it.copy(category = newCategory) }
        performSearch(_uiState.value.query, newCategory)
    }

    fun clearQuery() {
        _uiState.update { it.copy(query = "", results = emptyList(), hasSearched = false) }
        _queryFlow.value = ""
    }

    private fun performSearch(query: String, category: SearchCategory) {
        searchJob?.cancel()
        if (query.isBlank()) {
            _uiState.update { it.copy(results = emptyList(), isSearching = false, hasSearched = false) }
            return
        }

        _uiState.update { it.copy(isSearching = true) }
        searchJob = viewModelScope.launch {
            val results = searchRepository.search(query, category)
            _uiState.update {
                it.copy(
                    results = results,
                    isSearching = false,
                    hasSearched = true,
                )
            }
        }
    }
}
