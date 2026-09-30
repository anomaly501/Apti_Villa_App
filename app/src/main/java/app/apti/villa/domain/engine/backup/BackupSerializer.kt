package app.apti.villa.domain.engine.backup

import app.apti.villa.domain.model.BackupManifest
import java.security.MessageDigest

/**
 * Pure Kotlin backup serializer and validator.
 * Validates manifest, checksums, and ensures secrets/keys are never included.
 * ZERO Android or Room dependencies.
 */
object BackupSerializer {

    const val CURRENT_FORMAT_VERSION = 1
    const val CURRENT_APP_VERSION = "1.0.0"
    const val CURRENT_SCHEMA_VERSION = 5

    private val FORBIDDEN_SECRET_KEYS = listOf(
        "api_key",
        "apikey",
        "secret",
        "token",
        "authorization",
        "password",
    )

    /**
     * Computes SHA-256 hex string for a given text.
     */
    fun sha256(content: String): String {
        val digest = MessageDigest.getInstance("SHA-256")
        val bytes = digest.digest(content.toByteArray(Charsets.UTF_8))
        return bytes.joinToString("") { "%02x".format(it) }
    }

    /**
     * Creates a BackupManifest for the given entries.
     */
    fun createManifest(
        filesContent: Map<String, String>,
        itemCounts: Map<String, Int>,
        timestamp: Long = System.currentTimeMillis(),
    ): BackupManifest {
        val checksums = filesContent.mapValues { (_, content) -> sha256(content) }
        return BackupManifest(
            formatVersion = CURRENT_FORMAT_VERSION,
            appVersion = CURRENT_APP_VERSION,
            dbSchemaVersion = CURRENT_SCHEMA_VERSION,
            createdAt = timestamp,
            fileCounts = itemCounts,
            checksums = checksums,
        )
    }

    /**
     * Validates that a backup contains no API keys or secret tokens.
     */
    fun containsSecrets(content: String): Boolean {
        val lower = content.lowercase()
        return FORBIDDEN_SECRET_KEYS.any { lower.contains("\"$it\"") || lower.contains("'$it'") }
    }

    /**
     * Validates a manifest and its file checksums.
     */
    fun validateBackup(
        manifest: BackupManifest,
        filesContent: Map<String, String>,
    ): ValidationResult {
        if (manifest.formatVersion > CURRENT_FORMAT_VERSION) {
            return ValidationResult(
                isValid = false,
                errorMessage = "Backup format version ${manifest.formatVersion} is newer than supported version ($CURRENT_FORMAT_VERSION). Please update the app.",
            )
        }

        // Verify checksums
        for ((fileName, expectedChecksum) in manifest.checksums) {
            val content = filesContent[fileName] ?: return ValidationResult(
                isValid = false,
                errorMessage = "Missing file '$fileName' specified in backup manifest.",
            )
            val actualChecksum = sha256(content)
            if (actualChecksum != expectedChecksum) {
                return ValidationResult(
                    isValid = false,
                    errorMessage = "Checksum mismatch for file '$fileName'. Backup may be corrupted.",
                )
            }

            // Ensure no secret keys exist
            if (containsSecrets(content)) {
                return ValidationResult(
                    isValid = false,
                    errorMessage = "Security violation: File '$fileName' contains forbidden credential keys.",
                )
            }
        }

        return ValidationResult(isValid = true)
    }

    data class ValidationResult(
        val isValid: Boolean,
        val errorMessage: String? = null,
    )
}
