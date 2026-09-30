package app.apti.villa.domain.repository

import app.apti.villa.domain.model.*
import kotlinx.coroutines.flow.Flow

interface TestRepository {
    fun getActiveTestsWithStats(): Flow<List<TestWithStats>>

    suspend fun getTestById(testId: Long): Test?

    suspend fun startExamSession(testId: Long): Long

    suspend fun getAttempt(attemptId: Long): Attempt?

    fun getAttemptFlow(attemptId: Long): Flow<Attempt?>

    suspend fun getLatestInProgressExamAttempt(): Attempt?

    fun getExamQuestionItems(attemptId: Long): Flow<List<ExamQuestionItem>>

    suspend fun getExamQuestionItemsOnce(attemptId: Long): List<ExamQuestionItem>

    suspend fun saveOptionResponse(
        answerId: Long,
        optionId: Long?,
        status: AnswerStatus,
        timeSpentMs: Long,
    )

    suspend fun updateExamActiveTime(
        attemptId: Long,
        activeElapsedMs: Long,
    )

    suspend fun submitExam(attemptId: Long): TestScoreReport

    suspend fun getTestScoreReport(attemptId: Long): TestScoreReport?
}
