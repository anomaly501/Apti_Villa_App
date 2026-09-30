package app.apti.villa.feature.profile

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
import kotlinx.coroutines.launch
import javax.inject.Inject

data class ProfileUiState(
    val name: String = "",
    val preferredDifficulty: String = "MEDIUM",
    val dailyGoalQuestions: Int = 20,
    val dailyGoalMinutes: Int = 30,
    val prepStartDate: String? = null,
    val targetExamDate: String? = null,
    val availableExams: List<Exam> = emptyList(),
    val selectedExamUuids: Set<String> = emptySet(),
    val isSaving: Boolean = false,
)

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val userProfileRepository: UserProfileRepository,
    private val taxonomyRepository: TaxonomyRepository,
    private val preferencesRepository: PreferencesRepository,
) : ViewModel() {

    private val _editName = MutableStateFlow<String?>(null)
    private val _editDifficulty = MutableStateFlow<String?>(null)
    private val _editQuestions = MutableStateFlow<Int?>(null)
    private val _editMinutes = MutableStateFlow<Int?>(null)
    private val _editSelectedExams = MutableStateFlow<Set<String>?>(null)
    private val _isSaving = MutableStateFlow(false)

    val uiState: StateFlow<ProfileUiState> = combine(
        userProfileRepository.getUserProfile(),
        taxonomyRepository.getAllExams(),
        preferencesRepository.userPreferences,
        _isSaving,
    ) { profile, exams, prefs, saving ->
        ProfileUiState(
            name = _editName.value ?: profile?.name ?: "",
            preferredDifficulty = _editDifficulty.value ?: profile?.preferredDifficulty ?: "MEDIUM",
            dailyGoalQuestions = _editQuestions.value ?: profile?.dailyGoalQuestions ?: 20,
            dailyGoalMinutes = _editMinutes.value ?: profile?.dailyGoalMinutes ?: 30,
            prepStartDate = profile?.prepStartDate,
            targetExamDate = profile?.targetExamDate,
            availableExams = exams,
            selectedExamUuids = _editSelectedExams.value ?: prefs.selectedExamUuids,
            isSaving = saving,
        )
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = ProfileUiState(),
    )

    fun onNameChange(name: String) {
        _editName.value = name
    }

    fun onDifficultyChange(diff: String) {
        _editDifficulty.value = diff
    }

    fun onQuestionsGoalChange(questions: Int) {
        _editQuestions.value = questions.coerceIn(5, 200)
    }

    fun onMinutesGoalChange(minutes: Int) {
        _editMinutes.value = minutes.coerceIn(5, 240)
    }

    fun toggleExam(uuid: String) {
        val current = _editSelectedExams.value ?: uiState.value.selectedExamUuids
        _editSelectedExams.value = if (current.contains(uuid)) current - uuid else current + uuid
    }

    fun saveProfile(onSuccess: () -> Unit) {
        viewModelScope.launch {
            _isSaving.value = true
            val now = System.currentTimeMillis()
            val state = uiState.value
            userProfileRepository.upsertUserProfile(
                UserProfile(
                    id = 1,
                    name = state.name.trim().ifEmpty { null },
                    preferredDifficulty = state.preferredDifficulty,
                    dailyGoalQuestions = state.dailyGoalQuestions,
                    dailyGoalMinutes = state.dailyGoalMinutes,
                    prepStartDate = state.prepStartDate,
                    targetExamDate = state.targetExamDate,
                    updatedAt = now,
                )
            )
            preferencesRepository.setSelectedExams(state.selectedExamUuids)
            _isSaving.value = false
            _editName.value = null
            _editDifficulty.value = null
            _editQuestions.value = null
            _editMinutes.value = null
            _editSelectedExams.value = null
            onSuccess()
        }
    }
}
