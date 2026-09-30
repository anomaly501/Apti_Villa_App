package app.apti.villa.domain.repository

import app.apti.villa.domain.model.*
import kotlinx.coroutines.flow.Flow

interface PracticeRepository {
    fun getTopicsWithQuestionCounts(): Flow<List<TopicWithQuestionCount>>

    suspend fun startPracticeSession(filter: PracticeFilter): Long

    fun getAttempt(attemptId: Long): Flow<Attempt?>

    suspend fun getAttemptOnce(attemptId: Long): Attempt?

    suspend fun getLatestInProgressAttempt(): Attempt?

    fun getPracticeItems(attemptId: Long): Flow<List<PracticeSessionItem>>

    suspend fun getPracticeItemsOnce(attemptId: Long): List<PracticeSessionItem>

    suspend fun recordSubmission(
        answerId: Long,
        optionId: Long,
        msSinceFirstSeen: Long,
        isCorrect: Boolean,
    ): Answer

    suspend fun recordHintUsage(
        answerId: Long,
        hintId: Long,
        msSinceFirstSeen: Long,
    ): Answer

    suspend fun revealAnswer(
        answerId: Long,
        correctOptionId: Long?,
    ): Answer

    suspend fun updateAnswerTime(
        answerId: Long,
        timeSpentMs: Long,
    )

    suspend fun updateAttemptActiveTime(
        attemptId: Long,
        activeElapsedMs: Long,
    )

    suspend fun completeAttempt(attemptId: Long)

    suspend fun toggleSaveTrick(trickId: Long): Boolean

    suspend fun toggleQuestionTag(questionId: Long, tagId: Long): Boolean

    suspend fun getQuestionDetail(questionId: Long): Question?
}
