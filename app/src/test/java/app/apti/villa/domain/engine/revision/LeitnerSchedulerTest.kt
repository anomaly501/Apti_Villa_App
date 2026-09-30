package app.apti.villa.domain.engine.revision

import org.junit.Assert.*
import org.junit.Test

class LeitnerSchedulerTest {

    @Test
    fun `interval days match standard Leitner schedule`() {
        assertEquals(0, LeitnerScheduler.getIntervalDays(0))
        assertEquals(1, LeitnerScheduler.getIntervalDays(1))
        assertEquals(3, LeitnerScheduler.getIntervalDays(2))
        assertEquals(7, LeitnerScheduler.getIntervalDays(3))
        assertEquals(14, LeitnerScheduler.getIntervalDays(4))
        assertEquals(30, LeitnerScheduler.getIntervalDays(5))
    }

    @Test
    fun `interval days for out of range box clamps to 0 or 5`() {
        assertEquals(0, LeitnerScheduler.getIntervalDays(-1))
        assertEquals(30, LeitnerScheduler.getIntervalDays(6))
        assertEquals(30, LeitnerScheduler.getIntervalDays(100))
    }

    @Test
    fun `promote increases box up to 5 and sets due date based on interval`() {
        val now = 1_000_000_000L

        // From 0 -> 1 (interval 1 day)
        val res1 = LeitnerScheduler.promote(0, now)
        assertEquals(1, res1.newBox)
        assertEquals(now + 1 * LeitnerScheduler.ONE_DAY_MS, res1.dueAt)

        // From 1 -> 2 (interval 3 days)
        val res2 = LeitnerScheduler.promote(1, now)
        assertEquals(2, res2.newBox)
        assertEquals(now + 3 * LeitnerScheduler.ONE_DAY_MS, res2.dueAt)

        // From 4 -> 5 (interval 30 days)
        val res5 = LeitnerScheduler.promote(4, now)
        assertEquals(5, res5.newBox)
        assertEquals(now + 30 * LeitnerScheduler.ONE_DAY_MS, res5.dueAt)

        // From 5 -> 5 (clamped at box 5, interval 30 days)
        val resMax = LeitnerScheduler.promote(5, now)
        assertEquals(5, resMax.newBox)
        assertEquals(now + 30 * LeitnerScheduler.ONE_DAY_MS, resMax.dueAt)
    }

    @Test
    fun `demote resets item to box 0 and due immediately`() {
        val now = 1_700_000_000L
        val res = LeitnerScheduler.demote(now)
        assertEquals(0, res.newBox)
        assertEquals(now, res.dueAt)
    }

    @Test
    fun `isDue returns true when now is equal to or past dueAt`() {
        val dueAt = 5000L
        assertTrue(LeitnerScheduler.isDue(dueAt = dueAt, now = 5000L))
        assertTrue(LeitnerScheduler.isDue(dueAt = dueAt, now = 6000L))
        assertFalse(LeitnerScheduler.isDue(dueAt = dueAt, now = 4999L))
    }
}
