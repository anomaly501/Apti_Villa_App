package app.apti.villa.domain.engine.test

object TestTimerPolicy {

    /**
     * Calculates remaining seconds in the test.
     * Returns null if test is untimed (timeLimitSec is null).
     */
    fun calculateRemainingSeconds(timeLimitSec: Int?, activeElapsedMs: Long): Int? {
        if (timeLimitSec == null || timeLimitSec <= 0) return null
        val elapsedSec = (activeElapsedMs / 1000L).toInt()
        return (timeLimitSec - elapsedSec).coerceAtLeast(0)
    }

    /**
     * Checks if the test time limit has expired.
     */
    fun isExpired(timeLimitSec: Int?, activeElapsedMs: Long): Boolean {
        if (timeLimitSec == null || timeLimitSec <= 0) return false
        val remaining = calculateRemainingSeconds(timeLimitSec, activeElapsedMs) ?: return false
        return remaining <= 0
    }
}
