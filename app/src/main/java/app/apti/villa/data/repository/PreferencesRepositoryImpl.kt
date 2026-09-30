package app.apti.villa.data.repository

import app.apti.villa.data.prefs.UserPreferencesDataStore
import app.apti.villa.domain.model.AppThemeMode
import app.apti.villa.domain.model.UserPreferences
import app.apti.villa.domain.repository.PreferencesRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PreferencesRepositoryImpl @Inject constructor(
    private val dataStore: UserPreferencesDataStore,
) : PreferencesRepository {

    override val userPreferences: Flow<UserPreferences> = dataStore.userPreferences

    override suspend fun setThemeMode(mode: AppThemeMode) {
        dataStore.setThemeMode(mode)
    }

    override suspend fun setDynamicColor(enabled: Boolean) {
        dataStore.setDynamicColor(enabled)
    }

    override suspend fun setOnboardingCompleted(completed: Boolean) {
        dataStore.setOnboardingCompleted(completed)
    }

    override suspend fun setSelectedExams(examUuids: Set<String>) {
        dataStore.setSelectedExams(examUuids)
    }

    override suspend fun setSoundEffectsEnabled(enabled: Boolean) {
        dataStore.setSoundEffectsEnabled(enabled)
    }

    override suspend fun setHapticFeedbackEnabled(enabled: Boolean) {
        dataStore.setHapticFeedbackEnabled(enabled)
    }
}
