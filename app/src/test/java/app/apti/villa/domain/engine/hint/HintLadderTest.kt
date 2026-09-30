package app.apti.villa.domain.engine.hint

import org.junit.Assert.*
import org.junit.Test

class HintLadderTest {

    @Test
    fun `progressive unlock requires unlocking hints strictly in sequence`() {
        // Initially 0 hints unlocked
        assertTrue(HintLadder.canUnlockHint(currentHintsOpened = 0, hintNumber = 1))
        assertFalse(HintLadder.canUnlockHint(currentHintsOpened = 0, hintNumber = 2))
        assertFalse(HintLadder.canUnlockHint(currentHintsOpened = 0, hintNumber = 3))

        // When Hint 1 is opened
        assertFalse(HintLadder.canUnlockHint(currentHintsOpened = 1, hintNumber = 1))
        assertTrue(HintLadder.canUnlockHint(currentHintsOpened = 1, hintNumber = 2))
        assertFalse(HintLadder.canUnlockHint(currentHintsOpened = 1, hintNumber = 3))

        // When Hint 2 is opened
        assertFalse(HintLadder.canUnlockHint(currentHintsOpened = 2, hintNumber = 2))
        assertTrue(HintLadder.canUnlockHint(currentHintsOpened = 2, hintNumber = 3))
    }

    @Test
    fun `isHintUnlocked returns true for unlocked hints and false for locked`() {
        assertFalse(HintLadder.isHintUnlocked(currentHintsOpened = 0, hintNumber = 1))
        assertFalse(HintLadder.isHintUnlocked(currentHintsOpened = 0, hintNumber = 2))

        assertTrue(HintLadder.isHintUnlocked(currentHintsOpened = 1, hintNumber = 1))
        assertFalse(HintLadder.isHintUnlocked(currentHintsOpened = 1, hintNumber = 2))

        assertTrue(HintLadder.isHintUnlocked(currentHintsOpened = 2, hintNumber = 1))
        assertTrue(HintLadder.isHintUnlocked(currentHintsOpened = 2, hintNumber = 2))
        assertFalse(HintLadder.isHintUnlocked(currentHintsOpened = 2, hintNumber = 3))
    }

    @Test
    fun `calculateSolvedAtHintLevel returns opened count when not revealed`() {
        assertEquals(0, HintLadder.calculateSolvedAtHintLevel(hintsOpened = 0, isRevealed = false))
        assertEquals(1, HintLadder.calculateSolvedAtHintLevel(hintsOpened = 1, isRevealed = false))
        assertEquals(3, HintLadder.calculateSolvedAtHintLevel(hintsOpened = 3, isRevealed = false))
    }

    @Test
    fun `calculateSolvedAtHintLevel returns null when answer is revealed`() {
        assertNull(HintLadder.calculateSolvedAtHintLevel(hintsOpened = 0, isRevealed = true))
        assertNull(HintLadder.calculateSolvedAtHintLevel(hintsOpened = 2, isRevealed = true))
    }
}
