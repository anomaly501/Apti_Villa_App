package app.apti.villa.domain.engine.analytics

import kotlin.math.sqrt

/**
 * Pure Kotlin performance calculation engine.
 * Implements deterministic formulas specified in Section 8.3 & Section 16 of the build spec.
 * ZERO Android or Room dependencies.
 */
object PerformanceEngine {

    const val MIN_SAMPLE_SIZE = 10
    const val WEAK_ACCURACY_THRESHOLD = 60.0f
    const val STRONG_ACCURACY_THRESHOLD = 80.0f
    const val RECENT_OVERALL_SAMPLE_SIZE = 50
    const val RECENT_TOPIC_SAMPLE_SIZE = 20
    const val OUTLIER_TIME_THRESHOLD_MS = 600_000L // 10 minutes

    data class AnswerMetricInput(
        val isCorrect: Boolean?,
        val firstSubmissionCorrect: Boolean?,
        val timeSpentMs: Long,
        val isRetry: Boolean,
        val hintsOpened: Int = 0,
        val solvedAtHintLevel: Int? = null,
        val revealed: Boolean = false,
        val answeredAt: Long? = null,
        val topicId: Long? = null,
        val difficulty: String? = null,
    )

    data class CoreMetrics(
        val totalAttempted: Int,
        val totalCorrect: Int,
        val totalWrong: Int,
        val accuracy: Float,
        val firstAttemptAccuracy: Float,
        val avgTimeSpentMs: Long,
        val medianTimeSpentMs: Long,
        val recentAccuracy: Float,
    )

    data class HintDependency(
        val solvedWithoutHint: Int,
        val solvedAfterHint1: Int,
        val solvedAfterHint2: Int,
        val solvedAfterHint3Plus: Int,
        val couldNotSolve: Int,
    )

    data class DifficultyStat(
        val difficulty: String,
        val attempted: Int,
        val correct: Int,
        val accuracy: Float,
    )

    enum class TopicMasteryStatus {
        NOT_ENOUGH_DATA,
        WEAK,
        DEVELOPING,
        STRONG,
    }

    data class ClassifiedTopic(
        val topicId: Long,
        val topicName: String,
        val subjectName: String?,
        val attempted: Int,
        val correct: Int,
        val accuracy: Float,
        val recentAccuracy: Float,
        val avgTimeSpentMs: Long,
        val status: TopicMasteryStatus,
        val statusLabel: String,
        val weakRankScore: Float,
        val lastPracticedAt: Long?,
    )

    /**
     * Computes all core performance metrics.
     */
    fun calculateCoreMetrics(answers: List<AnswerMetricInput>): CoreMetrics {
        if (answers.isEmpty()) {
            return CoreMetrics(
                totalAttempted = 0,
                totalCorrect = 0,
                totalWrong = 0,
                accuracy = 0f,
                firstAttemptAccuracy = 0f,
                avgTimeSpentMs = 0L,
                medianTimeSpentMs = 0L,
                recentAccuracy = 0f,
            )
        }

        val totalAttempted = answers.size
        val totalCorrect = answers.count { it.isCorrect == true }
        val totalWrong = totalAttempted - totalCorrect
        val accuracy = (totalCorrect.toFloat() / totalAttempted.toFloat()) * 100f

        // First-attempt accuracy excludes retries
        val firstAttemptEligible = answers.filter { !it.isRetry && it.firstSubmissionCorrect != null }
        val firstAttemptAccuracy = if (firstAttemptEligible.isNotEmpty()) {
            val firstCorrect = firstAttemptEligible.count { it.firstSubmissionCorrect == true }
            (firstCorrect.toFloat() / firstAttemptEligible.size.toFloat()) * 100f
        } else {
            0f
        }

        // Filter outliers (> 10 mins) for time metrics
        val validTimes = answers
            .map { it.timeSpentMs }
            .filter { it in 1..OUTLIER_TIME_THRESHOLD_MS }

        val avgTimeSpentMs = if (validTimes.isNotEmpty()) {
            validTimes.sum() / validTimes.size
        } else {
            0L
        }

        val medianTimeSpentMs = if (validTimes.isNotEmpty()) {
            val sorted = validTimes.sorted()
            val mid = sorted.size / 2
            if (sorted.size % 2 == 0) {
                (sorted[mid - 1] + sorted[mid]) / 2
            } else {
                sorted[mid]
            }
        } else {
            0L
        }

        // Recent accuracy: last 50 answers (answers assumed ordered by answeredAt desc or asc)
        val sortedByDate = answers.sortedByDescending { it.answeredAt ?: 0L }
        val recentSample = sortedByDate.take(RECENT_OVERALL_SAMPLE_SIZE)
        val recentAccuracy = if (recentSample.isNotEmpty()) {
            val recentCorrect = recentSample.count { it.isCorrect == true }
            (recentCorrect.toFloat() / recentSample.size.toFloat()) * 100f
        } else {
            0f
        }

        return CoreMetrics(
            totalAttempted = totalAttempted,
            totalCorrect = totalCorrect,
            totalWrong = totalWrong,
            accuracy = accuracy,
            firstAttemptAccuracy = firstAttemptAccuracy,
            avgTimeSpentMs = avgTimeSpentMs,
            medianTimeSpentMs = medianTimeSpentMs,
            recentAccuracy = recentAccuracy,
        )
    }

    /**
     * Computes hint dependency distribution.
     */
    fun calculateHintDependency(answers: List<AnswerMetricInput>): HintDependency {
        var solvedWithoutHint = 0
        var solvedAfterHint1 = 0
        var solvedAfterHint2 = 0
        var solvedAfterHint3Plus = 0
        var couldNotSolve = 0

        for (ans in answers) {
            when {
                ans.revealed -> couldNotSolve++
                ans.isCorrect == true && ans.solvedAtHintLevel == 0 -> solvedWithoutHint++
                ans.isCorrect == true && ans.solvedAtHintLevel == 1 -> solvedAfterHint1++
                ans.isCorrect == true && ans.solvedAtHintLevel == 2 -> solvedAfterHint2++
                ans.isCorrect == true && (ans.solvedAtHintLevel ?: 0) >= 3 -> solvedAfterHint3Plus++
                ans.isCorrect == false -> couldNotSolve++
                else -> solvedWithoutHint++
            }
        }

        return HintDependency(
            solvedWithoutHint = solvedWithoutHint,
            solvedAfterHint1 = solvedAfterHint1,
            solvedAfterHint2 = solvedAfterHint2,
            solvedAfterHint3Plus = solvedAfterHint3Plus,
            couldNotSolve = couldNotSolve,
        )
    }

    /**
     * Computes accuracy by difficulty level.
     */
    fun calculateDifficultyStats(answers: List<AnswerMetricInput>): List<DifficultyStat> {
        val groups = answers.groupBy { it.difficulty?.uppercase() ?: "UNLABELED" }
        val order = listOf("EASY", "MEDIUM", "HARD", "UNLABELED")

        return order.mapNotNull { diff ->
            val list = groups[diff] ?: if (diff == "UNLABELED") null else emptyList()
            if (list == null || list.isEmpty()) {
                DifficultyStat(diff, 0, 0, 0f)
            } else {
                val correct = list.count { it.isCorrect == true }
                val acc = (correct.toFloat() / list.size.toFloat()) * 100f
                DifficultyStat(diff, list.size, correct, acc)
            }
        }
    }

    /**
     * Classifies a topic into WEAK, STRONG, DEVELOPING, or NOT_ENOUGH_DATA based on attempts & recent accuracy.
     */
    fun classifyTopic(
        topicId: Long,
        topicName: String,
        subjectName: String?,
        answers: List<AnswerMetricInput>,
    ): ClassifiedTopic {
        val attempted = answers.size
        if (attempted == 0) {
            return ClassifiedTopic(
                topicId = topicId,
                topicName = topicName,
                subjectName = subjectName,
                attempted = 0,
                correct = 0,
                accuracy = 0f,
                recentAccuracy = 0f,
                avgTimeSpentMs = 0L,
                status = TopicMasteryStatus.NOT_ENOUGH_DATA,
                statusLabel = "Not enough data (0/$MIN_SAMPLE_SIZE)",
                weakRankScore = 0f,
                lastPracticedAt = null,
            )
        }

        val correct = answers.count { it.isCorrect == true }
        val allTimeAcc = (correct.toFloat() / attempted.toFloat()) * 100f

        val sortedRecent = answers.sortedByDescending { it.answeredAt ?: 0L }.take(RECENT_TOPIC_SAMPLE_SIZE)
        val recentCorrect = sortedRecent.count { it.isCorrect == true }
        val recentAcc = (recentCorrect.toFloat() / sortedRecent.size.toFloat()) * 100f

        val lastPracticedAt = answers.mapNotNull { it.answeredAt }.maxOrNull()

        val validTimes = answers.map { it.timeSpentMs }.filter { it in 1..OUTLIER_TIME_THRESHOLD_MS }
        val avgTime = if (validTimes.isNotEmpty()) validTimes.sum() / validTimes.size else 0L

        val status: TopicMasteryStatus
        val statusLabel: String
        var weakRankScore = 0f

        if (attempted < MIN_SAMPLE_SIZE) {
            status = TopicMasteryStatus.NOT_ENOUGH_DATA
            statusLabel = "Not enough data ($attempted/$MIN_SAMPLE_SIZE)"
        } else if (recentAcc < WEAK_ACCURACY_THRESHOLD) {
            status = TopicMasteryStatus.WEAK
            statusLabel = "Weak (${recentAcc.toInt()}%)"
            // (60 - accuracy) * sqrt(attempted)
            weakRankScore = (WEAK_ACCURACY_THRESHOLD - recentAcc) * sqrt(attempted.toFloat())
        } else if (recentAcc >= STRONG_ACCURACY_THRESHOLD) {
            status = TopicMasteryStatus.STRONG
            statusLabel = "Strong (${recentAcc.toInt()}%)"
        } else {
            status = TopicMasteryStatus.DEVELOPING
            statusLabel = "Developing (${recentAcc.toInt()}%)"
        }

        return ClassifiedTopic(
            topicId = topicId,
            topicName = topicName,
            subjectName = subjectName,
            attempted = attempted,
            correct = correct,
            accuracy = allTimeAcc,
            recentAccuracy = recentAcc,
            avgTimeSpentMs = avgTime,
            status = status,
            statusLabel = statusLabel,
            weakRankScore = weakRankScore,
            lastPracticedAt = lastPracticedAt,
        )
    }

    /**
     * Calculates consecutive active study day streak.
     * @param activeDateStrings list of dates (e.g. ["2026-09-30", "2026-09-29"]) with >= 1 question attempted
     * @param todayDate e.g. "2026-09-30"
     * @param yesterdayDate e.g. "2026-09-29"
     */
    fun calculateStreak(
        activeDateStrings: Set<String>,
        todayDate: String,
        yesterdayDate: String,
    ): Int {
        if (activeDateStrings.isEmpty()) return 0

        // If today has activity, count consecutive backwards starting today.
        // If today has 0 activity, count consecutive backwards starting yesterday.
        var startDate = if (activeDateStrings.contains(todayDate)) {
            todayDate
        } else if (activeDateStrings.contains(yesterdayDate)) {
            yesterdayDate
        } else {
            return 0
        }

        var streak = 0
        var curDate = java.time.LocalDate.parse(startDate)

        while (activeDateStrings.contains(curDate.toString())) {
            streak++
            curDate = curDate.minusDays(1)
        }

        return streak
    }
}
