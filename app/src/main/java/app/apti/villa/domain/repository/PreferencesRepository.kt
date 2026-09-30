package app.apti.villa.domain.repository

import app.apti.villa.domain.model.AppThemeMode
import app.apti.villa.domain.model.UserPreferences
import kotlinx.coroutines.flow.Flow

interface PreferencesRepository {
    val userPreferences: Flow<UserPreferences>
    suspend fun setThemeMode(mode: AppThemeMode)
    suspend fun setDynamicColor(enabled: Boolean)
    suspend fun setOnboardingCompleted(completed: Boolean)
    suspend fun setSelectedExams(examUuids: Set<String>)
    suspend fun setSoundEffectsEnabled(enabled: Boolean)
    suspend fun setHapticFeedbackEnabled(enabled: Boolean)
}
