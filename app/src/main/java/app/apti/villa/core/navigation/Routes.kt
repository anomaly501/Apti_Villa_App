package app.apti.villa.core.navigation

import kotlinx.serialization.Serializable

/**
 * Type-safe navigation routes using kotlinx.serialization.
 * Each route is a data object or data class (for routes with arguments).
 */
object Routes {
    // Bottom tab destinations
    @Serializable data object Home
    @Serializable data object Practice
    @Serializable data object Tests
    @Serializable data object Review
    @Serializable data object Analytics

    // Top-bar destinations (reached from Home)
    @Serializable data object Profile
    @Serializable data object ProfileEdit
    @Serializable data object Search
    @Serializable data object Settings
    @Serializable data object Inventory

    // Onboarding
    @Serializable data object Onboarding

    // Import
    @Serializable data object ImportSource
    @Serializable data object ImportPreview
    @Serializable data object ImportError
    @Serializable data object ImportReport

    // Practice & Questions
    @Serializable
    data class PracticeSession(
        val attemptId: Long,
    )

    @Serializable
    data class QuestionDetail(
        val questionId: Long,
    )

    // Exam & Tests
    @Serializable
    data class ExamScreen(
        val attemptId: Long,
    )

    @Serializable
    data class TestResults(
        val attemptId: Long,
    )

    @Serializable
    data class TestReview(
        val attemptId: Long,
    )

    // Phase 8: Backup, Data Ownership & Privacy
    @Serializable data object Backup
    @Serializable data object DataManagement
    @Serializable data object Privacy

    // Phase 9: Optional AI Assistant (BYOK)
    @Serializable data object AiSettings
    @Serializable data object AiHistory
    @Serializable
    data class AiChat(
        val questionId: Long,
    )
}
