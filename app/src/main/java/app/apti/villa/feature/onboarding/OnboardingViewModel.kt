package app.apti.villa.feature.onboarding

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import app.apti.villa.domain.model.Exam
import app.apti.villa.domain.model.UserProfile
import app.apti.villa.domain.repository.PreferencesRepository
import app.apti.villa.domain.repository.TaxonomyRepository
import app.apti.villa.domain.repository.UserProfileRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

data class OnboardingUiState(
    val availableExams: List<Exam> = emptyList(),
    val selectedExamUuids: Set<String> = emptySet(),
    val name: String = "",
    val dailyGoalQuestions: Int = 20,
    val dailyGoalMinutes: Int = 30,
    val isSaving: Boolean = false,
)

private data class FormState(
    val name: String = "",
    val selectedExamUuids: Set<String> = emptySet(),
    val dailyGoalQuestions: Int = 20,
    val dailyGoalMinutes: Int = 30,
    val isSaving: Boolean = false,
)

@HiltViewModel
class OnboardingViewModel @Inject constructor(
    private val taxonomyRepository: TaxonomyRepository,
    private val userProfileRepository: UserProfileRepository,
    private val preferencesRepository: PreferencesRepository,
) : ViewModel() {

    private val _formState = MutableStateFlow(FormState())

    val uiState: StateFlow<OnboardingUiState> = combine(
        taxonomyRepository.getAllExams(),
        _formState,
    ) { exams, form ->
        OnboardingUiState(
            availableExams = exams,
            selectedExamUuids = form.selectedExamUuids,
            name = form.name,
            dailyGoalQuestions = form.dailyGoalQuestions,
            dailyGoalMinutes = form.dailyGoalMinutes,
            isSaving = form.isSaving,
        )
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = OnboardingUiState(),
    )

    fun onNameChange(name: String) {
        _formState.update { it.copy(name = name) }
    }

    fun toggleExamSelection(examUuid: String) {
        _formState.update { current ->
            val updated = if (current.selectedExamUuids.contains(examUuid)) {
                current.selectedExamUuids - examUuid
            } else {
                current.selectedExamUuids + examUuid
            }
            current.copy(selectedExamUuids = updated)
        }
    }

    fun onDailyGoalQuestionsChange(questions: Int) {
        _formState.update { it.copy(dailyGoalQuestions = questions.coerceIn(5, 200)) }
    }

    fun onDailyGoalMinutesChange(minutes: Int) {
        _formState.update { it.copy(dailyGoalMinutes = minutes.coerceIn(5, 240)) }
    }

    fun completeOnboarding(onSuccess: () -> Unit) {
        viewModelScope.launch {
            _formState.update { it.copy(isSaving = true) }
            val current = _formState.value
            val now = System.currentTimeMillis()
            userProfileRepository.upsertUserProfile(
                UserProfile(
                    id = 1,
                    name = current.name.trim().ifEmpty { null },
                    dailyGoalQuestions = current.dailyGoalQuestions,
                    dailyGoalMinutes = current.dailyGoalMinutes,
                    createdAt = now,
                    updatedAt = now,
                )
            )
            preferencesRepository.setSelectedExams(current.selectedExamUuids)
            preferencesRepository.setOnboardingCompleted(true)
            _formState.update { it.copy(isSaving = false) }
            onSuccess()
        }
    }

    fun skipOnboarding(onSuccess: () -> Unit) {
        viewModelScope.launch {
            preferencesRepository.setOnboardingCompleted(true)
            onSuccess()
        }
    }
}
