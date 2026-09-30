package app.apti.villa.domain.engine.search

import org.junit.Assert.*
import org.junit.Test

class SearchQuerySanitizerTest {

    @Test
    fun `sanitizeForFts with empty or blank string returns empty string`() {
        assertEquals("", SearchQuerySanitizer.sanitizeForFts(""))
        assertEquals("", SearchQuerySanitizer.sanitizeForFts("   "))
        assertFalse(SearchQuerySanitizer.isValidSearchQuery("   "))
    }

    @Test
    fun `sanitizeForFts with single word adds prefix wildcard`() {
        assertEquals("percentage*", SearchQuerySanitizer.sanitizeForFts("percentage"))
        assertTrue(SearchQuerySanitizer.isValidSearchQuery("percentage"))
    }

    @Test
    fun `sanitizeForFts with multiple words adds wildcard to each token`() {
        assertEquals("time* and* work*", SearchQuerySanitizer.sanitizeForFts("time and work"))
    }

    @Test
    fun `sanitizeForFts removes FTS special characters and colons`() {
        val result = SearchQuerySanitizer.sanitizeForFts("test: (hello+world)*")
        assertEquals("test* hello* world*", result)
    }

    @Test
    fun `sanitizeForFts correctly preserves Devanagari Hindi unicode characters`() {
        // "प्रतिशत" (percentage in Hindi)
        val hindiResult = SearchQuerySanitizer.sanitizeForFts("प्रतिशत")
        assertEquals("प्रतिशत*", hindiResult)
        assertTrue(SearchQuerySanitizer.isValidSearchQuery("प्रतिशत"))

        // Multi-word Hindi
        val multiHindi = SearchQuerySanitizer.sanitizeForFts("संख्या पद्धति")
        assertEquals("संख्या* पद्धति*", multiHindi)
    }
}
