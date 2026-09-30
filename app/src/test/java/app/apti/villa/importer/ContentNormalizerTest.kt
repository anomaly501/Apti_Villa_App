package app.apti.villa.importer

import app.apti.villa.domain.importer.ContentNormalizer
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Test

class ContentNormalizerTest {

    @Test
    fun `collapseWhitespace removes leading trailing and internal extra whitespace`() {
        val input = "  Hello    World \n  from   AptiVilla  "
        assertEquals("Hello World from AptiVilla", ContentNormalizer.collapseWhitespace(input))
    }

    @Test
    fun `parseTimestamp handles MM SS and HH MM SS`() {
        assertEquals(330, ContentNormalizer.parseTimestamp("05:30"))
        assertEquals(3665, ContentNormalizer.parseTimestamp("01:01:05"))
        assertNull(ContentNormalizer.parseTimestamp(null))
        assertNull(ContentNormalizer.parseTimestamp("   "))
    }

    @Test(expected = IllegalArgumentException::class)
    fun `parseTimestamp throws on invalid seconds`() {
        ContentNormalizer.parseTimestamp("05:65")
    }

    @Test
    fun `normalizeDifficulty standardizes easy medium hard`() {
        assertEquals("EASY", ContentNormalizer.normalizeDifficulty("easy"))
        assertEquals("MEDIUM", ContentNormalizer.normalizeDifficulty("med"))
        assertEquals("HARD", ContentNormalizer.normalizeDifficulty("HARD"))
        assertNull(ContentNormalizer.normalizeDifficulty(null))
    }

    @Test
    fun `computeContentHash is deterministic and order-independent for options`() {
        val hash1 = ContentNormalizer.computeContentHash("What is 2+2?", listOf("4", "5", "6"))
        val hash2 = ContentNormalizer.computeContentHash("What is 2+2?", listOf("6", "4", "5"))
        assertEquals(hash1, hash2)
    }
}
