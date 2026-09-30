package app.apti.villa.domain.engine.backup

import app.apti.villa.domain.model.BackupManifest
import org.junit.Assert.*
import org.junit.Test

class BackupSerializerTest {

    @Test
    fun `sha256 computes consistent hex digest`() {
        val hash1 = BackupSerializer.sha256("test content")
        val hash2 = BackupSerializer.sha256("test content")
        assertEquals(hash1, hash2)
        assertEquals(64, hash1.length) // 256 bits = 64 hex characters
    }

    @Test
    fun `createManifest calculates checksums for all entries`() {
        val files = mapOf(
            "questions.json" to "[{\"id\": 1, \"text\": \"sample\"}]",
            "tests.json" to "[{\"id\": 1, \"title\": \"Mock Test\"}]",
        )
        val counts = mapOf(
            "questions.json" to 1,
            "tests.json" to 1,
        )

        val manifest = BackupSerializer.createManifest(files, counts)

        assertEquals(1, manifest.formatVersion)
        assertEquals(5, manifest.dbSchemaVersion)
        assertEquals(2, manifest.checksums.size)
        assertEquals(BackupSerializer.sha256(files["questions.json"]!!), manifest.checksums["questions.json"])
    }

    @Test
    fun `validateBackup accepts valid payload with matching checksums`() {
        val files = mapOf("questions.json" to "sample text")
        val manifest = BackupSerializer.createManifest(files, mapOf("questions.json" to 1))

        val validation = BackupSerializer.validateBackup(manifest, files)
        assertTrue(validation.isValid)
        assertNull(validation.errorMessage)
    }

    @Test
    fun `validateBackup rejects corrupted file with checksum mismatch`() {
        val files = mapOf("questions.json" to "sample text")
        val manifest = BackupSerializer.createManifest(files, mapOf("questions.json" to 1))

        // Modify file after manifest was generated
        val tamperedFiles = mapOf("questions.json" to "tampered text")
        val validation = BackupSerializer.validateBackup(manifest, tamperedFiles)

        assertFalse(validation.isValid)
        assertTrue(validation.errorMessage!!.contains("Checksum mismatch"))
    }

    @Test
    fun `validateBackup rejects future format version`() {
        val manifest = BackupManifest(formatVersion = 999)
        val validation = BackupSerializer.validateBackup(manifest, emptyMap())

        assertFalse(validation.isValid)
        assertTrue(validation.errorMessage!!.contains("newer than supported"))
    }

    @Test
    fun `validateBackup rejects payload containing secret credentials or API keys`() {
        val filesWithSecret = mapOf("settings.json" to "{\"api_key\": \"sk-123456\"}")
        val manifest = BackupSerializer.createManifest(filesWithSecret, mapOf("settings.json" to 1))

        val validation = BackupSerializer.validateBackup(manifest, filesWithSecret)
        assertFalse(validation.isValid)
        assertTrue(validation.errorMessage!!.contains("Security violation"))
    }
}
