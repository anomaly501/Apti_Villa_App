package app.apti.villa.domain.model

data class BackupManifest(
    val formatVersion: Int = 1,
    val appVersion: String = "1.0.0",
    val dbSchemaVersion: Int = 5,
    val createdAt: Long = System.currentTimeMillis(),
    val fileCounts: Map<String, Int> = emptyMap(),
    val checksums: Map<String, String> = emptyMap(),
)

data class BackupPreview(
    val manifest: BackupManifest,
    val questionCount: Int,
    val testCount: Int,
    val attemptCount: Int,
    val mistakeCount: Int,
    val revisionCount: Int,
    val isValid: Boolean,
    val errorMessage: String? = null,
)

enum class RestoreMode {
    MERGE,
    REPLACE,
}

data class RestoreResult(
    val success: Boolean,
    val message: String,
    val questionsRestored: Int = 0,
    val testsRestored: Int = 0,
    val attemptsRestored: Int = 0,
)
