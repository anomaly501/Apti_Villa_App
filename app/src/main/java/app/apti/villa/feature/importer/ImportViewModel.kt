package app.apti.villa.feature.importer

import android.content.Context
import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import app.apti.villa.core.common.AppError
import app.apti.villa.core.common.Outcome
import app.apti.villa.domain.importer.*
import app.apti.villa.domain.model.Exam
import app.apti.villa.domain.model.Subject
import app.apti.villa.domain.repository.TaxonomyRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.io.BufferedReader
import java.io.InputStreamReader
import javax.inject.Inject

data class ImportUiState(
    val plan: ImportPlan? = null,
    val isValidating: Boolean = false,
    val isCommitting: Boolean = false,
    val errorMessage: String? = null,
    val inspectQuestion: SkippedQuestion? = null,
    val availableExams: List<Exam> = emptyList(),
    val availableSubjects: List<Subject> = emptyList(),
    val selectedExamId: Long? = null,
    val selectedSubjectId: Long? = null,
    val customTitle: String = "",
    val durationMinutes: Int? = null,
    val negativeMarks: Double = 0.25,
    val importResult: ImportResult? = null,
)

@HiltViewModel
class ImportViewModel @Inject constructor(
    private val importRepository: ImportRepository,
    private val taxonomyRepository: TaxonomyRepository,
) : ViewModel() {

    private val _plan = MutableStateFlow<ImportPlan?>(null)
    private val _isValidating = MutableStateFlow(false)
    private val _isCommitting = MutableStateFlow(false)
    private val _errorMessage = MutableStateFlow<String?>(null)
    private val _inspectQuestion = MutableStateFlow<SkippedQuestion?>(null)
    private val _selectedExamId = MutableStateFlow<Long?>(null)
    private val _selectedSubjectId = MutableStateFlow<Long?>(null)
    private val _customTitle = MutableStateFlow("")
    private val _durationMinutes = MutableStateFlow<Int?>(null)
    private val _negativeMarks = MutableStateFlow(0.25)
    private val _importResult = MutableStateFlow<ImportResult?>(null)

    val uiState: StateFlow<ImportUiState> = combine(
        combine(
            _plan,
            _isValidating,
            _isCommitting,
            _errorMessage,
            _inspectQuestion,
        ) { plan, validating, committing, error, inspect ->
            Tuple5(plan, validating, committing, error, inspect)
        },
        combine(
            taxonomyRepository.getAllExams(),
            taxonomyRepository.getAllSubjects(),
            _selectedExamId,
            _selectedSubjectId,
        ) { exams, subjects, examId, subjectId ->
            Tuple4(exams, subjects, examId, subjectId)
        },
        combine(
            _customTitle,
            _durationMinutes,
            _negativeMarks,
            _importResult,
        ) { title, duration, neg, result ->
            Tuple4(title, duration, neg, result)
        }
    ) { (plan, validating, committing, error, inspect), (exams, subjects, examId, subjectId), (title, duration, neg, result) ->
        ImportUiState(
            plan = plan,
            isValidating = validating,
            isCommitting = committing,
            errorMessage = error,
            inspectQuestion = inspect,
            availableExams = exams,
            availableSubjects = subjects,
            selectedExamId = examId,
            selectedSubjectId = subjectId,
            customTitle = title,
            durationMinutes = duration,
            negativeMarks = neg,
            importResult = result,
        )
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = ImportUiState(),
    )

    fun processJson(
        jsonString: String,
        onNavigateToPreview: () -> Unit,
        onNavigateToError: () -> Unit,
    ) {
        viewModelScope.launch {
            _isValidating.value = true
            _errorMessage.value = null

            // 20 MB string size check
            if (jsonString.length > 20 * 1024 * 1024) {
                _isValidating.value = false
                _errorMessage.value = "Input exceeds 20 MB limit."
                return@launch
            }

            when (val result = importRepository.parseAndValidate(jsonString)) {
                is Outcome.Success -> {
                    val plan = result.data
                    _plan.value = plan
                    _customTitle.value = plan.sourceMetadata.title
                    _isValidating.value = false

                    // Auto match exam and subject if present in metadata
                    if (!plan.sourceMetadata.exam.isNullOrBlank()) {
                        val matched = taxonomyRepository.getExamByName(plan.sourceMetadata.exam)
                        if (matched != null) {
                            _selectedExamId.value = matched.id
                        }
                    }
                    if (!plan.sourceMetadata.subject.isNullOrBlank()) {
                        val matched = taxonomyRepository.getSubjectByName(plan.sourceMetadata.subject)
                        if (matched != null) {
                            _selectedSubjectId.value = matched.id
                        }
                    }

                    if (plan.hasErrors) {
                        onNavigateToError()
                    } else {
                        onNavigateToPreview()
                    }
                }
                is Outcome.Error -> {
                    _isValidating.value = false
                    val err = result.error
                    _errorMessage.value = when (err) {
                        is AppError.Import -> err.issues.joinToString("\n") { it.message }
                        is AppError.Database -> "Database error: ${err.cause?.message ?: err.message}"
                        else -> "Unexpected error during validation."
                    }
                }
            }
        }
    }

    fun processFileUri(
        context: Context,
        uri: Uri,
        onNavigateToPreview: () -> Unit,
        onNavigateToError: () -> Unit,
    ) {
        viewModelScope.launch {
            _isValidating.value = true
            try {
                // Size check
                val pfd = context.contentResolver.openFileDescriptor(uri, "r")
                val sizeBytes = pfd?.statSize ?: 0L
                pfd?.close()

                if (sizeBytes > 20 * 1024 * 1024) {
                    _isValidating.value = false
                    _errorMessage.value = "File is too large (${sizeBytes / (1024 * 1024)} MB). Maximum size is 20 MB."
                    return@launch
                }

                val inputStream = context.contentResolver.openInputStream(uri)
                val reader = BufferedReader(InputStreamReader(inputStream))
                val content = reader.readText()
                reader.close()

                processJson(content, onNavigateToPreview, onNavigateToError)
            } catch (e: Exception) {
                _isValidating.value = false
                _errorMessage.value = "Failed to read file: ${e.message}"
            }
        }
    }

    fun acceptValidOnlyAndProceed(onNavigateToPreview: () -> Unit) {
        val currentPlan = _plan.value ?: return
        val filteredPlan = currentPlan.copy(
            issues = currentPlan.issues.filter { it.severity == IssueSeverity.WARNING }
        )
        _plan.value = filteredPlan
        onNavigateToPreview()
    }

    fun setInspectQuestion(question: SkippedQuestion?) {
        _inspectQuestion.value = question
    }

    fun onExamSelected(examId: Long?) {
        _selectedExamId.value = examId
    }

    fun onSubjectSelected(subjectId: Long?) {
        _selectedSubjectId.value = subjectId
    }

    fun onCustomTitleChange(title: String) {
        _customTitle.value = title
    }

    fun onDurationMinutesChange(minutes: Int?) {
        _durationMinutes.value = minutes
    }

    fun onNegativeMarksChange(marks: Double) {
        _negativeMarks.value = marks
    }

    fun commitImport(onSuccess: (ImportResult) -> Unit) {
        val plan = _plan.value ?: return
        viewModelScope.launch {
            _isCommitting.value = true
            when (val result = importRepository.commit(
                plan = plan,
                examId = _selectedExamId.value,
                subjectId = _selectedSubjectId.value,
                customTestTitle = _customTitle.value,
                durationMinutes = _durationMinutes.value,
                negativeMarks = _negativeMarks.value,
                positiveMarks = 1.0,
            )) {
                is Outcome.Success -> {
                    _isCommitting.value = false
                    _importResult.value = result.data
                    onSuccess(result.data)
                }
                is Outcome.Error -> {
                    _isCommitting.value = false
                    _errorMessage.value = "Failed to commit import: ${(result.error as? AppError.Database)?.cause?.message ?: "Unknown error"}"
                }
            }
        }
    }

    fun reset() {
        _plan.value = null
        _isValidating.value = false
        _isCommitting.value = false
        _errorMessage.value = null
        _inspectQuestion.value = null
        _selectedExamId.value = null
        _selectedSubjectId.value = null
        _customTitle.value = ""
        _durationMinutes.value = null
        _negativeMarks.value = 0.25
        _importResult.value = null
    }

    private data class Tuple4<A, B, C, D>(val a: A, val b: B, val c: C, val d: D)
    private data class Tuple5<A, B, C, D, E>(val a: A, val b: B, val c: C, val d: D, val e: E)
}
