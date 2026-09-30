package app.apti.villa.domain.model

enum class AppThemeMode {
    SYSTEM,
    LIGHT,
    DARK
}

data class UserPreferences(
    val themeMode: AppThemeMode = AppThemeMode.SYSTEM,
    val dynamicColor: Boolean = true,
    val isOnboardingCompleted: Boolean = false,
    val selectedExamUuids: Set<String> = emptySet(),
    val soundEffectsEnabled: Boolean = true,
    val hapticFeedbackEnabled: Boolean = true,
)
