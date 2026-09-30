package app.apti.villa.data.backup

import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test
import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream

class ZipBackupManagerTest {

    @Test
    fun `writeZip and readZip correctly roundtrip text files`() {
        val files = mapOf(
            "manifest.json" to "{\"version\": 1}",
            "questions.json" to "[{\"id\": 1, \"text\": \"Find the sum\"}]",
            "tests.json" to "[{\"id\": 1, \"title\": \"SSC Mock 1\"}]",
        )

        val out = ByteArrayOutputStream()
        ZipBackupManager.writeZip(files, out)

        val zipBytes = out.toByteArray()
        assertTrue(zipBytes.isNotEmpty())

        val inStream = ByteArrayInputStream(zipBytes)
        val readFiles = ZipBackupManager.readZip(inStream)

        assertEquals(3, readFiles.size)
        assertEquals(files["manifest.json"], readFiles["manifest.json"])
        assertEquals(files["questions.json"], readFiles["questions.json"])
        assertEquals(files["tests.json"], readFiles["tests.json"])
    }
}
