package app.apti.villa.domain.engine.revision

object LeitnerScheduler {

    const val ONE_DAY_MS = 24L * 60L * 60L * 1000L

    data class ScheduleResult(
        val newBox: Int,
        val dueAt: Long,
    )

    /**
     * Intervals in days for Leitner boxes 0 through 5:
     * Box 0: 0 days (immediate)
     * Box 1: 1 day
     * Box 2: 3 days
     * Box 3: 7 days
     * Box 4: 14 days
     * Box 5: 30 days
     */
    fun getIntervalDays(box: Int): Int = when (box.coerceIn(0, 5)) {
        0 -> 0
        1 -> 1
        2 -> 3
        3 -> 7
        4 -> 14
        5 -> 30
        else -> 0
    }

    /**
     * Promotes question to the next box upon correct answer (max Box 5).
     */
    fun promote(currentBox: Int, now: Long): ScheduleResult {
        val nextBox = (currentBox + 1).coerceAtMost(5)
        val intervalDays = getIntervalDays(nextBox)
        val dueAt = now + (intervalDays * ONE_DAY_MS)
        return ScheduleResult(newBox = nextBox, dueAt = dueAt)
    }

    /**
     * Demotes question back to Box 0 upon wrong answer, due immediately.
     */
    fun demote(now: Long): ScheduleResult {
        return ScheduleResult(newBox = 0, dueAt = now)
    }

    /**
     * Determines whether an item is due for revision at timestamp [now].
     */
    fun isDue(dueAt: Long, now: Long): Boolean {
        return dueAt <= now
    }
}
