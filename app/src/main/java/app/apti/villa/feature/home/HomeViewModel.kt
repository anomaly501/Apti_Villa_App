package app.apti.villa.feature.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import app.apti.villa.domain.engine.analytics.PerformanceEngine
import app.apti.villa.domain.repository.AnalyticsRepository
import app.apti.villa.domain.repository.PracticeRepository
import app.apti.villa.domain.repository.ReviewRepository
import app.apti.villa.domain.repository.UserProfileRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.util.Calendar
import javax.inject.Inject

data class HomeUiState(
    val greeting: String = "Hello!",
    val userName: String = "",
    val streakDays: Int = 0,
    val questionsAttemptedToday: Int = 0,
    val dailyGoalQuestions: Int = 20,
    val minutesStudiedToday: Int = 0,
    val dailyGoalMinutes: Int = 30,
    val inProgressAttemptId: Long? = null,
    val inProgressAttemptMode: String? = null,
    val weakTopicRecommendation: PerformanceEngine.ClassifiedTopic? = null,
    val revisionDueTodayCount: Int = 0,
    val activeMistakesCount: Int = 0,
) {
    val progressFraction: Float
        get() = if (dailyGoalQuestions > 0) {
            (questionsAttemptedToday.toFloat() / dailyGoalQuestions).coerceIn(0f, 1f)
        } else 0f
}

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val userProfileRepository: UserProfileRepository,
    private val analyticsRepository: AnalyticsRepository,
    private val practiceRepository: PracticeRepository,
    private val reviewRepository: ReviewRepository,
) : ViewModel() {

    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState: StateFlow<HomeUiState> = _uiState.asStateFlow()

    init {
        loadHomeData()
    }

    private fun loadHomeData() {
        viewModelScope.launch {
            combine(
                userProfileRepository.getUserProfile(),
                analyticsRepository.getAnalyticsDashboard(),
                reviewRepository.getRevisionBoxStats(),
            ) { profile, analytics, revisionStats ->
                val hour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY)
                val timeGreeting = when {
                    hour < 12 -> "Good Morning"
                    hour < 17 -> "Good Afternoon"
                    else -> "Good Evening"
                }
                val name = profile?.name?.trim()
                val greeting = if (!name.isNullOrEmpty()) "$timeGreeting, $name" else timeGreeting

                val todayStr = LocalDate.now().toString()
                val todayStat = analytics.recentStudyDays.firstOrNull { it.date == todayStr }

                val attemptedToday = todayStat?.questionsAttempted ?: 0
                val minutesToday = ((todayStat?.activeMs ?: 0L) / 60000L).toInt()

                val inProgress = practiceRepository.getLatestInProgressAttempt()

                HomeUiState(
                    greeting = greeting,
                    userName = name ?: "",
                    streakDays = analytics.currentStreak,
                    questionsAttemptedToday = attemptedToday,
                    dailyGoalQuestions = profile?.dailyGoalQuestions ?: 20,
                    minutesStudiedToday = minutesToday,
                    dailyGoalMinutes = profile?.dailyGoalMinutes ?: 30,
                    inProgressAttemptId = inProgress?.id,
                    inProgressAttemptMode = inProgress?.mode?.name,
                    weakTopicRecommendation = analytics.weakTopics.firstOrNull(),
                    revisionDueTodayCount = revisionStats.totalDueToday,
                    activeMistakesCount = 0,
                )
            }.collect { newState ->
                _uiState.value = newState
            }
        }
    }
}
