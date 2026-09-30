package app.apti.villa.domain.model

data class UserProfile(
    val id: Long = 1,
    val name: String? = null,
    val preferredDifficulty: String? = null,
    val dailyGoalQuestions: Int = 20,
    val dailyGoalMinutes: Int = 30,
    val prepStartDate: String? = null,
    val targetExamDate: String? = null,
    val createdAt: Long = System.currentTimeMillis(),
    val updatedAt: Long = System.currentTimeMillis(),
)
