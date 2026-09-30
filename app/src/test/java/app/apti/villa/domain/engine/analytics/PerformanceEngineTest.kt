package app.apti.villa.domain.engine.analytics

import org.junit.Assert.*
import org.junit.Test

class PerformanceEngineTest {

    @Test
    fun `calculateCoreMetrics with empty list returns zeroed metrics without crashing`() {
        val result = PerformanceEngine.calculateCoreMetrics(emptyList())
        assertEquals(0, result.totalAttempted)
        assertEquals(0, result.totalCorrect)
        assertEquals(0, result.totalWrong)
        assertEquals(0f, result.accuracy, 0.001f)
        assertEquals(0f, result.firstAttemptAccuracy, 0.001f)
        assertEquals(0L, result.avgTimeSpentMs)
        assertEquals(0L, result.medianTimeSpentMs)
    }

    @Test
    fun `calculateCoreMetrics computes accuracy, first-attempt accuracy, and median time`() {
        val answers = listOf(
            PerformanceEngine.AnswerMetricInput(
                isCorrect = true,
                firstSubmissionCorrect = true,
                timeSpentMs = 20_000L,
                isRetry = false,
                answeredAt = 1000L,
            ),
            PerformanceEngine.AnswerMetricInput(
                isCorrect = true,
                firstSubmissionCorrect = false, // solved on 2nd try in practice
                timeSpentMs = 40_000L,
                isRetry = false,
                answeredAt = 2000L,
            ),
            PerformanceEngine.AnswerMetricInput(
                isCorrect = false,
                firstSubmissionCorrect = false,
                timeSpentMs = 60_000L,
                isRetry = false,
                answeredAt = 3000L,
            ),
            PerformanceEngine.AnswerMetricInput(
                isCorrect = true,
                firstSubmissionCorrect = true,
                timeSpentMs = 30_000L,
                isRetry = true, // retry attempt: excluded from firstAttemptAccuracy
                answeredAt = 4000L,
            ),
        )

        val result = PerformanceEngine.calculateCoreMetrics(answers)

        assertEquals(4, result.totalAttempted)
        assertEquals(3, result.totalCorrect)
        assertEquals(1, result.totalWrong)
        // 3 / 4 = 75.0%
        assertEquals(75.0f, result.accuracy, 0.001f)
        // first attempt eligible: first 3 items (item 4 isRetry=true). Out of 3, 1 was firstSubmissionCorrect=true -> 1/3 = 33.333%
        assertEquals(33.333f, result.firstAttemptAccuracy, 0.01f)
        // Times: 20s, 30s, 40s, 60s -> avg = 150 / 4 = 37500ms
        assertEquals(37500L, result.avgTimeSpentMs)
        // Median of 20s, 30s, 40s, 60s = (30s + 40s) / 2 = 35000ms
        assertEquals(35000L, result.medianTimeSpentMs)
    }

    @Test
    fun `calculateCoreMetrics ignores time outliers above 10 minutes`() {
        val answers = listOf(
            PerformanceEngine.AnswerMetricInput(
                isCorrect = true,
                firstSubmissionCorrect = true,
                timeSpentMs = 30_000L,
                isRetry = false,
            ),
            PerformanceEngine.AnswerMetricInput(
                isCorrect = true,
                firstSubmissionCorrect = true,
                timeSpentMs = 700_000L, // Outlier: > 10 min
                isRetry = false,
            ),
        )

        val result = PerformanceEngine.calculateCoreMetrics(answers)
        // Only 30s should be used for average time
        assertEquals(30_000L, result.avgTimeSpentMs)
        assertEquals(30_000L, result.medianTimeSpentMs)
    }

    @Test
    fun `calculateHintDependency counts categories correctly`() {
        val answers = listOf(
            PerformanceEngine.AnswerMetricInput(isCorrect = true, firstSubmissionCorrect = true, timeSpentMs = 1000L, isRetry = false, solvedAtHintLevel = 0),
            PerformanceEngine.AnswerMetricInput(isCorrect = true, firstSubmissionCorrect = true, timeSpentMs = 1000L, isRetry = false, solvedAtHintLevel = 1),
            PerformanceEngine.AnswerMetricInput(isCorrect = true, firstSubmissionCorrect = true, timeSpentMs = 1000L, isRetry = false, solvedAtHintLevel = 2),
            PerformanceEngine.AnswerMetricInput(isCorrect = true, firstSubmissionCorrect = true, timeSpentMs = 1000L, isRetry = false, solvedAtHintLevel = 3),
            PerformanceEngine.AnswerMetricInput(isCorrect = false, firstSubmissionCorrect = false, timeSpentMs = 1000L, isRetry = false, revealed = true),
        )

        val hints = PerformanceEngine.calculateHintDependency(answers)
        assertEquals(1, hints.solvedWithoutHint)
        assertEquals(1, hints.solvedAfterHint1)
        assertEquals(1, hints.solvedAfterHint2)
        assertEquals(1, hints.solvedAfterHint3Plus)
        assertEquals(1, hints.couldNotSolve)
    }

    @Test
    fun `classifyTopic correctly categorizes NOT_ENOUGH_DATA, WEAK, STRONG, and DEVELOPING`() {
        // Less than 10 attempts -> NOT_ENOUGH_DATA
        val fewAnswers = List(5) {
            PerformanceEngine.AnswerMetricInput(isCorrect = true, firstSubmissionCorrect = true, timeSpentMs = 1000L, isRetry = false)
        }
        val notEnough = PerformanceEngine.classifyTopic(1L, "Percentages", "Arithmetic", fewAnswers)
        assertEquals(PerformanceEngine.TopicMasteryStatus.NOT_ENOUGH_DATA, notEnough.status)

        // 10 attempts, 4 correct (40% < 60%) -> WEAK
        val weakAnswers = List(10) { idx ->
            PerformanceEngine.AnswerMetricInput(isCorrect = idx < 4, firstSubmissionCorrect = idx < 4, timeSpentMs = 1000L, isRetry = false)
        }
        val weak = PerformanceEngine.classifyTopic(2L, "Probability", "Algebra", weakAnswers)
        assertEquals(PerformanceEngine.TopicMasteryStatus.WEAK, weak.status)
        assertTrue(weak.weakRankScore > 0f)

        // 10 attempts, 9 correct (90% >= 80%) -> STRONG
        val strongAnswers = List(10) { idx ->
            PerformanceEngine.AnswerMetricInput(isCorrect = idx < 9, firstSubmissionCorrect = idx < 9, timeSpentMs = 1000L, isRetry = false)
        }
        val strong = PerformanceEngine.classifyTopic(3L, "Time & Work", "Arithmetic", strongAnswers)
        assertEquals(PerformanceEngine.TopicMasteryStatus.STRONG, strong.status)

        // 10 attempts, 7 correct (70% in [60, 80)) -> DEVELOPING
        val devAnswers = List(10) { idx ->
            PerformanceEngine.AnswerMetricInput(isCorrect = idx < 7, firstSubmissionCorrect = idx < 7, timeSpentMs = 1000L, isRetry = false)
        }
        val dev = PerformanceEngine.classifyTopic(4L, "Number Series", "Reasoning", devAnswers)
        assertEquals(PerformanceEngine.TopicMasteryStatus.DEVELOPING, dev.status)
    }

    @Test
    fun `calculateStreak counts consecutive days backwards from today or yesterday`() {
        val today = "2026-09-30"
        val yesterday = "2026-09-29"

        // Streak with today active (3 days: 30, 29, 28)
        val activeDates1 = setOf("2026-09-30", "2026-09-29", "2026-09-28", "2026-09-25")
        val streak1 = PerformanceEngine.calculateStreak(activeDates1, today, yesterday)
        assertEquals(3, streak1)

        // Streak when today has no activity yet (counts from yesterday: 29, 28 = 2 days)
        val activeDates2 = setOf("2026-09-29", "2026-09-28", "2026-09-20")
        val streak2 = PerformanceEngine.calculateStreak(activeDates2, today, yesterday)
        assertEquals(2, streak2)

        // Streak broken if neither today nor yesterday has activity
        val activeDates3 = setOf("2026-09-28", "2026-09-27")
        val streak3 = PerformanceEngine.calculateStreak(activeDates3, today, yesterday)
        assertEquals(0, streak3)
    }
}
