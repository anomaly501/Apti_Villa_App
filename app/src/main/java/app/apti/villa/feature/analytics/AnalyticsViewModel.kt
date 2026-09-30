package app.apti.villa.feature.analytics

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import app.apti.villa.domain.engine.analytics.PerformanceEngine
import app.apti.villa.domain.model.AnalyticsDashboardData
import app.apti.villa.domain.repository.AnalyticsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

data class AnalyticsUiState(
    val isLoading: Boolean = true,
    val dashboard: AnalyticsDashboardData? = null,
    val selectedTab: Int = 0,
    val topicFilter: String = "ALL", // ALL, WEAK, STRONG, DEVELOPING
    val selectedTopicDetail: PerformanceEngine.ClassifiedTopic? = null,
)

@HiltViewModel
class AnalyticsViewModel @Inject constructor(
    private val analyticsRepository: AnalyticsRepository,
) : ViewModel() {

    private val _uiState = MutableStateFlow(AnalyticsUiState())
    val uiState: StateFlow<AnalyticsUiState> = _uiState.asStateFlow()

    init {
        loadAnalytics()
    }

    private fun loadAnalytics() {
        viewModelScope.launch {
            analyticsRepository.getAnalyticsDashboard().collect { data ->
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        dashboard = data,
                    )
                }
            }
        }
    }

    fun selectTab(index: Int) {
        _uiState.update { it.copy(selectedTab = index) }
    }

    fun setTopicFilter(filter: String) {
        _uiState.update { it.copy(topicFilter = filter) }
    }

    fun selectTopicDetail(topic: PerformanceEngine.ClassifiedTopic?) {
        _uiState.update { it.copy(selectedTopicDetail = topic) }
    }
}
