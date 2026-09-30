package app.apti.villa.domain.repository

import app.apti.villa.domain.model.BackupPreview
import app.apti.villa.domain.model.RestoreMode
import app.apti.villa.domain.model.RestoreResult
import java.io.InputStream
import java.io.OutputStream

interface BackupRepository {
    suspend fun exportCompleteBackup(outputStream: OutputStream): Boolean
    suspend fun exportQuestionsJson(outputStream: OutputStream): Boolean
    suspend fun exportTestsJson(outputStream: OutputStream): Boolean
    suspend fun previewBackup(inputStream: InputStream): BackupPreview
    suspend fun restoreBackup(inputStream: InputStream, mode: RestoreMode): RestoreResult
    suspend fun clearAttemptHistory(): Boolean
    suspend fun clearMistakesAndRevision(): Boolean
    suspend fun wipeAllUserData(): Boolean
}
