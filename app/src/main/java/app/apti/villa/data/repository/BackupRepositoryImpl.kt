package app.apti.villa.data.repository

import androidx.room.withTransaction
import app.apti.villa.core.common.IoDispatcher
import app.apti.villa.data.backup.ZipBackupManager
import app.apti.villa.data.local.dao.*
import app.apti.villa.data.local.db.AppDatabase
import app.apti.villa.data.local.entity.*
import app.apti.villa.domain.engine.backup.BackupSerializer
import app.apti.villa.domain.model.BackupManifest
import app.apti.villa.domain.model.BackupPreview
import app.apti.villa.domain.model.RestoreMode
import app.apti.villa.domain.model.RestoreResult
import app.apti.villa.domain.repository.BackupRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import org.json.JSONArray
import org.json.JSONObject
import java.io.InputStream
import java.io.OutputStream
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class BackupRepositoryImpl @Inject constructor(
    private val db: AppDatabase,
    private val questionDao: QuestionDao,
    private val questionOptionDao: QuestionOptionDao,
    private val hintDao: HintDao,
    private val trickDao: TrickDao,
    private val solutionStepDao: SolutionStepDao,
    private val questionTagDao: QuestionTagDao,
    private val testDao: TestDao,
    private val testQuestionDao: TestQuestionDao,
    private val attemptDao: AttemptDao,
    private val answerDao: AnswerDao,
    private val answerSubmissionDao: AnswerSubmissionDao,
    private val hintUsageDao: HintUsageDao,
    private val mistakeDao: MistakeDao,
    private val revisionDao: RevisionDao,
    private val studyDayDao: StudyDayDao,
    private val savedTrickDao: SavedTrickDao,
    private val userProfileDao: UserProfileDao,
    private val subjectDao: SubjectDao,
    private val topicDao: TopicDao,
    private val examDao: ExamDao,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher,
) : BackupRepository {

    override suspend fun exportCompleteBackup(outputStream: OutputStream): Boolean = withContext(ioDispatcher) {
        val entries = mutableMapOf<String, String>()
        val counts = mutableMapOf<String, Int>()

        // 1. Questions
        val questions = questionDao.getAllQuestionsOnce()
        val qArray = JSONArray()
        for (q in questions) {
            val qObj = JSONObject().apply {
                put("id", q.id)
                put("uuid", q.uuid)
                put("text", q.text)
                put("format", q.format)
                put("difficulty", q.difficulty)
                put("subjectId", q.subjectId)
                put("topicId", q.topicId)
                put("externalId", q.externalId)
                put("sourceId", q.sourceId)

                // Options
                val optArray = JSONArray()
                for (opt in questionOptionDao.getOptionsForQuestionOnce(q.id)) {
                    optArray.put(JSONObject().apply {
                        put("id", opt.id)
                        put("position", opt.position)
                        put("text", opt.text)
                        put("isCorrect", opt.isCorrect)
                    })
                }
                put("options", optArray)
            }
            qArray.put(qObj)
        }
        val qContent = qArray.toString(2)
        entries["questions.json"] = qContent
        counts["questions.json"] = questions.size

        // 2. Tests
        val tests = testDao.getActiveTestsOnce()
        val tArray = JSONArray()
        for (t in tests) {
            tArray.put(JSONObject().apply {
                put("id", t.id)
                put("uuid", t.uuid)
                put("title", t.title)
                put("durationSec", t.durationSec)
                put("marksCorrect", t.marksCorrect)
                put("negativeMarks", t.negativeMarks)
            })
        }
        val tContent = tArray.toString(2)
        entries["tests.json"] = tContent
        counts["tests.json"] = tests.size

        // 3. Mistakes
        val mistakes = mistakeDao.getActiveMistakesOnce()
        val mArray = JSONArray()
        for (m in mistakes) {
            mArray.put(JSONObject().apply {
                put("questionId", m.questionId)
                put("firstWrongAt", m.firstWrongAt)
                put("lastWrongAt", m.lastWrongAt)
                put("wrongCount", m.wrongCount)
                put("consecutiveCorrect", m.consecutiveCorrect)
                put("status", m.status)
            })
        }
        val mContent = mArray.toString(2)
        entries["mistakes.json"] = mContent
        counts["mistakes.json"] = mistakes.size

        // 4. Study days
        val studyDays = studyDayDao.getAllStudyDaysOnce()
        val sArray = JSONArray()
        for (s in studyDays) {
            sArray.put(JSONObject().apply {
                put("date", s.date)
                put("questionsAttempted", s.questionsAttempted)
                put("correct", s.correct)
                put("activeMs", s.activeMs)
            })
        }
        val sContent = sArray.toString(2)
        entries["study_days.json"] = sContent
        counts["study_days.json"] = studyDays.size

        // 5. Manifest
        val manifest = BackupSerializer.createManifest(entries, counts)
        val manifestObj = JSONObject().apply {
            put("formatVersion", manifest.formatVersion)
            put("appVersion", manifest.appVersion)
            put("dbSchemaVersion", manifest.dbSchemaVersion)
            put("createdAt", manifest.createdAt)

            val countsObj = JSONObject()
            manifest.fileCounts.forEach { (k, v) -> countsObj.put(k, v) }
            put("fileCounts", countsObj)

            val checksumsObj = JSONObject()
            manifest.checksums.forEach { (k, v) -> checksumsObj.put(k, v) }
            put("checksums", checksumsObj)
        }
        entries["manifest.json"] = manifestObj.toString(2)

        ZipBackupManager.writeZip(entries, outputStream)
        true
    }

    override suspend fun exportQuestionsJson(outputStream: OutputStream): Boolean = withContext(ioDispatcher) {
        val questions = questionDao.getAllQuestionsOnce()
        val root = JSONObject().apply {
            put("schema_version", 1)
            put("title", "AptiVilla Question Export")
            put("created_at", System.currentTimeMillis())

            val qArray = JSONArray()
            for (q in questions) {
                val qObj = JSONObject().apply {
                    put("id", q.externalId.ifEmpty { "Q_${q.id}" })
                    put("question", q.text)
                    put("difficulty", q.difficulty)

                    val optArray = JSONArray()
                    val options = questionOptionDao.getOptionsForQuestionOnce(q.id)
                    var correctChar = "A"
                    options.forEachIndexed { idx, opt ->
                        optArray.put(opt.text)
                        if (opt.isCorrect) {
                            correctChar = ('A' + idx).toString()
                        }
                    }
                    put("options", optArray)
                    put("answer", correctChar)
                }
                qArray.put(qObj)
            }
            put("questions", qArray)
        }

        outputStream.bufferedWriter(Charsets.UTF_8).use {
            it.write(root.toString(2))
        }
        true
    }

    override suspend fun exportTestsJson(outputStream: OutputStream): Boolean = withContext(ioDispatcher) {
        val tests = testDao.getActiveTestsOnce()
        val root = JSONObject().apply {
            put("schema_version", 1)
            put("title", "AptiVilla Tests Export")
            put("created_at", System.currentTimeMillis())

            val tArray = JSONArray()
            for (t in tests) {
                tArray.put(JSONObject().apply {
                    put("id", t.uuid)
                    put("title", t.title)
                    put("duration_sec", t.durationSec)
                    put("marks_correct", t.marksCorrect)
                    put("negative_marks", t.negativeMarks)
                })
            }
            put("tests", tArray)
        }

        outputStream.bufferedWriter(Charsets.UTF_8).use {
            it.write(root.toString(2))
        }
        true
    }

    override suspend fun previewBackup(inputStream: InputStream): BackupPreview = withContext(ioDispatcher) {
        try {
            val entries = ZipBackupManager.readZip(inputStream)
            val manifestJson = entries["manifest.json"] ?: return@withContext BackupPreview(
                manifest = BackupManifest(),
                questionCount = 0,
                testCount = 0,
                attemptCount = 0,
                mistakeCount = 0,
                revisionCount = 0,
                isValid = false,
                errorMessage = "Invalid backup: manifest.json is missing.",
            )

            val mObj = JSONObject(manifestJson)
            val countsObj = mObj.optJSONObject("fileCounts")
            val checksumsObj = mObj.optJSONObject("checksums")

            val fileCounts = mutableMapOf<String, Int>()
            countsObj?.keys()?.forEach { k -> fileCounts[k] = countsObj.getInt(k) }

            val checksums = mutableMapOf<String, String>()
            checksumsObj?.keys()?.forEach { k -> checksums[k] = checksumsObj.getString(k) }

            val manifest = BackupManifest(
                formatVersion = mObj.optInt("formatVersion", 1),
                appVersion = mObj.optString("appVersion", "1.0.0"),
                dbSchemaVersion = mObj.optInt("dbSchemaVersion", 5),
                createdAt = mObj.optLong("createdAt", System.currentTimeMillis()),
                fileCounts = fileCounts,
                checksums = checksums,
            )

            val validation = BackupSerializer.validateBackup(manifest, entries)
            if (!validation.isValid) {
                return@withContext BackupPreview(
                    manifest = manifest,
                    questionCount = 0,
                    testCount = 0,
                    attemptCount = 0,
                    mistakeCount = 0,
                    revisionCount = 0,
                    isValid = false,
                    errorMessage = validation.errorMessage,
                )
            }

            BackupPreview(
                manifest = manifest,
                questionCount = fileCounts["questions.json"] ?: 0,
                testCount = fileCounts["tests.json"] ?: 0,
                attemptCount = fileCounts["attempts.json"] ?: 0,
                mistakeCount = fileCounts["mistakes.json"] ?: 0,
                revisionCount = fileCounts["revision.json"] ?: 0,
                isValid = true,
                errorMessage = null,
            )
        } catch (e: Exception) {
            BackupPreview(
                manifest = BackupManifest(),
                questionCount = 0,
                testCount = 0,
                attemptCount = 0,
                mistakeCount = 0,
                revisionCount = 0,
                isValid = false,
                errorMessage = "Failed to parse backup archive: ${e.localizedMessage}",
            )
        }
    }

    override suspend fun restoreBackup(inputStream: InputStream, mode: RestoreMode): RestoreResult = withContext(ioDispatcher) {
        try {
            val entries = ZipBackupManager.readZip(inputStream)
            val manifestJson = entries["manifest.json"] ?: return@withContext RestoreResult(false, "manifest.json missing")
            val mObj = JSONObject(manifestJson)
            val manifest = BackupManifest(formatVersion = mObj.optInt("formatVersion", 1))

            val validation = BackupSerializer.validateBackup(manifest, entries)
            if (!validation.isValid) {
                return@withContext RestoreResult(false, validation.errorMessage ?: "Validation failed")
            }

            var qCount = 0
            var tCount = 0

            db.withTransaction {
                if (mode == RestoreMode.REPLACE) {
                    attemptDao.deleteAllAttempts()
                    mistakeDao.deleteAll()
                    revisionDao.deleteAllItems()
                    revisionDao.deleteAllReasons()
                    studyDayDao.deleteAll()
                    savedTrickDao.deleteAll()
                }

                // Restore mistakes
                entries["mistakes.json"]?.let { mJson ->
                    val mArray = JSONArray(mJson)
                    for (i in 0 until mArray.length()) {
                        val obj = mArray.getJSONObject(i)
                        mistakeDao.upsert(
                            MistakeEntity(
                                questionId = obj.getLong("questionId"),
                                firstWrongAt = obj.getLong("firstWrongAt"),
                                lastWrongAt = obj.getLong("lastWrongAt"),
                                wrongCount = obj.getInt("wrongCount"),
                                consecutiveCorrect = obj.getInt("consecutiveCorrect"),
                                status = obj.optString("status", "ACTIVE"),
                            )
                        )
                    }
                }

                // Restore study days
                entries["study_days.json"]?.let { sJson ->
                    val sArray = JSONArray(sJson)
                    for (i in 0 until sArray.length()) {
                        val obj = sArray.getJSONObject(i)
                        studyDayDao.upsert(
                            StudyDayEntity(
                                date = obj.getString("date"),
                                questionsAttempted = obj.getInt("questionsAttempted"),
                                correct = obj.getInt("correct"),
                                activeMs = obj.getLong("activeMs"),
                            )
                        )
                    }
                }

                qCount = entries["questions.json"]?.let { JSONArray(it).length() } ?: 0
                tCount = entries["tests.json"]?.let { JSONArray(it).length() } ?: 0
            }

            RestoreResult(
                success = true,
                message = "Backup restored successfully (${mode.name} mode)",
                questionsRestored = qCount,
                testsRestored = tCount,
            )
        } catch (e: Exception) {
            RestoreResult(false, "Restore failed: ${e.localizedMessage}")
        }
    }

    override suspend fun clearAttemptHistory(): Boolean = withContext(ioDispatcher) {
        db.withTransaction {
            attemptDao.deleteAllAttempts()
        }
        true
    }

    override suspend fun clearMistakesAndRevision(): Boolean = withContext(ioDispatcher) {
        db.withTransaction {
            mistakeDao.deleteAll()
            revisionDao.deleteAllItems()
            revisionDao.deleteAllReasons()
        }
        true
    }

    override suspend fun wipeAllUserData(): Boolean = withContext(ioDispatcher) {
        db.withTransaction {
            attemptDao.deleteAllAttempts()
            mistakeDao.deleteAll()
            revisionDao.deleteAllItems()
            revisionDao.deleteAllReasons()
            studyDayDao.deleteAll()
            savedTrickDao.deleteAll()
            userProfileDao.upsert(
                UserProfileEntity(
                    id = 1,
                    dailyGoalQuestions = 20,
                    dailyGoalMinutes = 30,
                    createdAt = System.currentTimeMillis(),
                    updatedAt = System.currentTimeMillis(),
                )
            )
        }
        true
    }
}
