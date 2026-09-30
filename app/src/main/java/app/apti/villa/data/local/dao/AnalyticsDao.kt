package app.apti.villa.data.local.dao

import androidx.room.Dao
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

data class AnswerPerformanceRecord(
    val answerId: Long,
    val attemptId: Long,
    val questionId: Long,
    val status: String,
    val isCorrect: Boolean?,
    val firstSubmissionCorrect: Boolean?,
    val timeSpentMs: Long,
    val hintsOpened: Int,
    val solvedAtHintLevel: Int?,
    val revealed: Boolean,
    val answeredAt: Long?,
    val isRetry: Boolean,
    val mode: String,
    val subjectId: Long?,
    val topicId: Long?,
    val difficulty: String?,
)

@Dao
interface AnalyticsDao {
    @Query("""
        SELECT 
            a.id AS answerId,
            a.attempt_id AS attemptId,
            a.question_id AS questionId,
            a.status AS status,
            a.is_correct AS isCorrect,
            a.first_submission_correct AS firstSubmissionCorrect,
            a.time_spent_ms AS timeSpentMs,
            a.hints_opened AS hintsOpened,
            a.solved_at_hint_level AS solvedAtHintLevel,
            a.revealed AS revealed,
            a.answered_at AS answeredAt,
            att.is_retry AS isRetry,
            att.mode AS mode,
            q.subject_id AS subjectId,
            q.topic_id AS topicId,
            q.difficulty AS difficulty
        FROM answer a
        INNER JOIN attempt att ON att.id = a.attempt_id
        INNER JOIN question q ON q.id = a.question_id
        WHERE a.status IN ('ANSWERED', 'ANSWERED_MARKED')
        ORDER BY a.answered_at DESC
    """)
    fun getAnswerPerformanceRecords(): Flow<List<AnswerPerformanceRecord>>

    @Query("""
        SELECT 
            a.id AS answerId,
            a.attempt_id AS attemptId,
            a.question_id AS questionId,
            a.status AS status,
            a.is_correct AS isCorrect,
            a.first_submission_correct AS firstSubmissionCorrect,
            a.time_spent_ms AS timeSpentMs,
            a.hints_opened AS hintsOpened,
            a.solved_at_hint_level AS solvedAtHintLevel,
            a.revealed AS revealed,
            a.answered_at AS answeredAt,
            att.is_retry AS isRetry,
            att.mode AS mode,
            q.subject_id AS subjectId,
            q.topic_id AS topicId,
            q.difficulty AS difficulty
        FROM answer a
        INNER JOIN attempt att ON att.id = a.attempt_id
        INNER JOIN question q ON q.id = a.question_id
        WHERE a.status IN ('ANSWERED', 'ANSWERED_MARKED')
        ORDER BY a.answered_at DESC
    """)
    suspend fun getAnswerPerformanceRecordsOnce(): List<AnswerPerformanceRecord>
}
