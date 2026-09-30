package app.apti.villa.domain.engine.test

import org.junit.Assert.*
import org.junit.Test

class TestTimerPolicyTest {

    @Test
    fun `calculateRemainingSeconds computes remaining time and stops at zero`() {
        val limitSec = 3600 // 1 hour

        // Halfway
        assertEquals(1800, TestTimerPolicy.calculateRemainingSeconds(limitSec, 1800000L))

        // 10 seconds remaining
        assertEquals(10, TestTimerPolicy.calculateRemainingSeconds(limitSec, 3590000L))

        // Exactly finished
        assertEquals(0, TestTimerPolicy.calculateRemainingSeconds(limitSec, 3600000L))

        // Over time
        assertEquals(0, TestTimerPolicy.calculateRemainingSeconds(limitSec, 3700000L))
    }

    @Test
    fun `calculateRemainingSeconds returns null for untimed tests`() {
        assertNull(TestTimerPolicy.calculateRemainingSeconds(null, 50000L))
        assertNull(TestTimerPolicy.calculateRemainingSeconds(0, 50000L))
    }

    @Test
    fun `isExpired accurately triggers on time expiry`() {
        val limitSec = 600

        assertFalse(TestTimerPolicy.isExpired(limitSec, 599000L))
        assertTrue(TestTimerPolicy.isExpired(limitSec, 600000L))
        assertTrue(TestTimerPolicy.isExpired(limitSec, 650000L))
        assertFalse(TestTimerPolicy.isExpired(null, 999999L))
    }
}
