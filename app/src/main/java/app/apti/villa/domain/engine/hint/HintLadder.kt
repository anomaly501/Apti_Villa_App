package app.apti.villa.domain.engine.hint

object HintLadder {

    /**
     * Progressive unlock rule: A student can only unlock hint [hintNumber] if all previous
     * hints (1..hintNumber - 1) have already been opened.
     */
    fun canUnlockHint(currentHintsOpened: Int, hintNumber: Int): Boolean {
        return hintNumber == currentHintsOpened + 1
    }

    /**
     * Checks if a particular hint number is already unlocked.
     */
    fun isHintUnlocked(currentHintsOpened: Int, hintNumber: Int): Boolean {
        return hintNumber <= currentHintsOpened
    }

    /**
     * Computes solved_at_hint_level:
     * - Returns null if the solution was revealed via "Show Answer"
     * - Otherwise returns the count of hints that were opened when the correct answer was submitted (0..N)
     */
    fun calculateSolvedAtHintLevel(hintsOpened: Int, isRevealed: Boolean): Int? {
        if (isRevealed) return null
        return hintsOpened.coerceAtLeast(0)
    }
}
