package app.apti.villa.data.prefs

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.core.stringSetPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import app.apti.villa.domain.model.AppThemeMode
import app.apti.villa.domain.model.UserPreferences
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

val Context.userDataStore: DataStore<Preferences> by preferencesDataStore(name = "aptivilla_prefs")

@Singleton
class UserPreferencesDataStore @Inject constructor(
    @ApplicationContext private val context: Context,
) {
    private object Keys {
        val THEME_MODE = stringPreferencesKey("theme_mode")
        val DYNAMIC_COLOR = booleanPreferencesKey("dynamic_color")
        val ONBOARDING_COMPLETED = booleanPreferencesKey("onboarding_completed")
        val SELECTED_EXAMS = stringSetPreferencesKey("selected_exams")
        val SOUND_EFFECTS = booleanPreferencesKey("sound_effects")
        val HAPTIC_FEEDBACK = booleanPreferencesKey("haptic_feedback")
    }

    val userPreferences: Flow<UserPreferences> = context.userDataStore.data.map { prefs ->
        val themeModeStr = prefs[Keys.THEME_MODE] ?: AppThemeMode.SYSTEM.name
        val themeMode = try {
            AppThemeMode.valueOf(themeModeStr)
        } catch (_: Exception) {
            AppThemeMode.SYSTEM
        }

        UserPreferences(
            themeMode = themeMode,
            dynamicColor = prefs[Keys.DYNAMIC_COLOR] ?: true,
            isOnboardingCompleted = prefs[Keys.ONBOARDING_COMPLETED] ?: false,
            selectedExamUuids = prefs[Keys.SELECTED_EXAMS] ?: emptySet(),
            soundEffectsEnabled = prefs[Keys.SOUND_EFFECTS] ?: true,
            hapticFeedbackEnabled = prefs[Keys.HAPTIC_FEEDBACK] ?: true,
        )
    }

    suspend fun setThemeMode(mode: AppThemeMode) {
        context.userDataStore.edit { prefs ->
            prefs[Keys.THEME_MODE] = mode.name
        }
    }

    suspend fun setDynamicColor(enabled: Boolean) {
        context.userDataStore.edit { prefs ->
            prefs[Keys.DYNAMIC_COLOR] = enabled
        }
    }

    suspend fun setOnboardingCompleted(completed: Boolean) {
        context.userDataStore.edit { prefs ->
            prefs[Keys.ONBOARDING_COMPLETED] = completed
        }
    }

    suspend fun setSelectedExams(examUuids: Set<String>) {
        context.userDataStore.edit { prefs ->
            prefs[Keys.SELECTED_EXAMS] = examUuids
        }
    }

    suspend fun setSoundEffectsEnabled(enabled: Boolean) {
        context.userDataStore.edit { prefs ->
            prefs[Keys.SOUND_EFFECTS] = enabled
        }
    }

    suspend fun setHapticFeedbackEnabled(enabled: Boolean) {
        context.userDataStore.edit { prefs ->
            prefs[Keys.HAPTIC_FEEDBACK] = enabled
        }
    }
}
