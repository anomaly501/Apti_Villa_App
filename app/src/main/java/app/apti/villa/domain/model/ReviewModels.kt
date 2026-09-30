package app.apti.villa.domain.model

data class Mistake(
    val question: Question,
    val firstWrongAt: Long,
    val lastWrongAt: Long,
    val wrongCount: Int = 1,
    val consecutiveCorrect: Int = 0,
    val status: String = "ACTIVE", // ACTIVE, RESOLVED, REMOVED
)

data class RevisionItem(
    val question: Question,
    val box: Int = 0,
    val dueAt: Long,
    val lastReviewedAt: Long? = null,
    val reviewCount: Int = 0,
    val suspended: Boolean = false,
    val reasons: List<String> = emptyList(),
    val isDue: Boolean = false,
)

data class RevisionBoxStats(
    val box0Count: Int = 0,
    val box1Count: Int = 0,
    val box2Count: Int = 0,
    val box3Count: Int = 0,
    val box4Count: Int = 0,
    val box5Count: Int = 0,
    val totalDueToday: Int = 0,
)

data class SavedTrickWithQuestion(
    val trick: Trick,
    val question: Question,
)

data class BookmarkedQuestion(
    val question: Question,
    val tag: Tag,
    val bookmarkedAt: Long,
)
