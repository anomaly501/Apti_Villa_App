package app.apti.villa.ai.security

import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

class SecureKeyStoreTest {

    private val secureKeyStore = SecureKeyStore()

    @Test
    fun `maskKey handles empty and short keys safely`() {
        assertEquals("", secureKeyStore.maskKey(""))
        assertEquals("••••••••", secureKeyStore.maskKey("12345"))
        assertEquals("••••••••", secureKeyStore.maskKey("12345678"))
    }

    @Test
    fun `maskKey keeps first 4 and last 4 characters for standard API keys`() {
        val key = "sk-proj-1234567890abcdef"
        val masked = secureKeyStore.maskKey(key)

        assertTrue(masked.startsWith("sk-p"))
        assertTrue(masked.endsWith("cdef"))
        assertTrue(masked.contains("••••••••"))
        assertEquals("sk-p••••••••cdef", masked)
    }
}
