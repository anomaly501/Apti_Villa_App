package app.apti.villa.importer

import app.apti.villa.domain.importer.AnswerResolutionResult
import app.apti.villa.domain.importer.AnswerResolver
import app.apti.villa.domain.importer.IssueCodes
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

class AnswerResolverTest {

    private val options = listOf("Option A", "Option B", "Option C", "Option D")

    @Test
    fun `step 1 exact match matches correctly`() {
        val result = AnswerResolver.resolve("Option B", options, 0, "1")
        assertTrue(result is AnswerResolutionResult.Success)
        assertEquals(1, (result as AnswerResolutionResult.Success).optionIndex)
    }

    @Test
    fun `step 2 trimmed match handles extra whitespace`() {
        val result = AnswerResolver.resolve("  Option   C  ", options, 0, "1")
        assertTrue(result is AnswerResolutionResult.Success)
        assertEquals(2, (result as AnswerResolutionResult.Success).optionIndex)
    }

    @Test
    fun `step 3 case-insensitive match matches correctly`() {
        val result = AnswerResolver.resolve("option a", options, 0, "1")
        assertTrue(result is AnswerResolutionResult.Success)
        assertEquals(0, (result as AnswerResolutionResult.Success).optionIndex)
    }

    @Test
    fun `step 4 letter mapping maps A-E to positions with warning`() {
        val result = AnswerResolver.resolve("C", options, 0, "1")
        assertTrue(result is AnswerResolutionResult.Success)
        val success = result as AnswerResolutionResult.Success
        assertEquals(2, success.optionIndex)
        assertEquals(IssueCodes.W_ANSWER_LETTER_MAPPED, success.warning?.code)
    }

    @Test
    fun `step 4 number mapping maps 1-4 to positions with warning`() {
        val result = AnswerResolver.resolve("4", options, 0, "1")
        assertTrue(result is AnswerResolutionResult.Success)
        val success = result as AnswerResolutionResult.Success
        assertEquals(3, success.optionIndex)
        assertEquals(IssueCodes.W_ANSWER_LETTER_MAPPED, success.warning?.code)
    }

    @Test
    fun `step 5 ambiguous answer emits E_ANSWER_AMBIGUOUS`() {
        val ambiguousOptions = listOf("Yes", "yes", "No")
        val result = AnswerResolver.resolve("YES", ambiguousOptions, 0, "1")
        assertTrue(result is AnswerResolutionResult.Ambiguous)
        assertEquals(IssueCodes.E_ANSWER_AMBIGUOUS, (result as AnswerResolutionResult.Ambiguous).issue.code)
    }

    @Test
    fun `step 6 no match emits E_ANSWER_NO_MATCH`() {
        val result = AnswerResolver.resolve("Totally Different Answer", options, 0, "1")
        assertTrue(result is AnswerResolutionResult.NoMatch)
        assertEquals(IssueCodes.E_ANSWER_NO_MATCH, (result as AnswerResolutionResult.NoMatch).issue.code)
    }
}
