package app.apti.villa.data.repository

import androidx.room.withTransaction
import app.apti.villa.core.common.IoDispatcher
import app.apti.villa.data.local.dao.*
import app.apti.villa.data.local.db.AppDatabase
import app.apti.villa.data.local.entity.*
import app.apti.villa.domain.engine.test.Scorer
import app.apti.villa.domain.model.*
import app.apti.villa.domain.repository.TestRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import java.util.UUID
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TestRepositoryImpl @Inject constructor(
    private val db: AppDatabase,
    private val testDao: TestDao,
    private val testQuestionDao: TestQuestionDao,
    private val attemptDao: AttemptDao,
    private val answerDao: AnswerDao,
    private val answerSubmissionDao: AnswerSubmissionDao,
    private val questionDao: QuestionDao,
    private val questionOptionDao: QuestionOptionDao,
    private val hintDao: HintDao,
    private val trickDao: TrickDao,
    private val solutionStepDao: SolutionStepDao,
    private val examDao: ExamDao,
    private val mistakeDao: MistakeDao,
    private val revisionDao: RevisionDao,
    private val studyDayDao: StudyDayDao,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher,
) : TestRepository {


    override fun getActiveTestsWithStats(): Flow<List<TestWithStats>> {
        return testDao.getActiveTests().map { tests ->
            tests.map { testEntity ->
                val qCount = testQuestionDao.getQuestionCountForTest(testEntity.id)
                val exam = testEntity.examId?.let { examDao.getExamById(it) }

                TestWithStats(
                    test = Test(
                        id = testEntity.id,
                        uuid = testEntity.uuid,
                        sourceId = testEntity.sourceId,
                        examId = testEntity.examId,
                        title = testEntity.title,
                        kind = testEntity.kind,
                        durationSec = testEntity.durationSec,
                        marksCorrect = testEntity.marksCorrect.toFloat(),
                        negativeMarks = testEntity.negativeMarks.toFloat(),
                        archived = testEntity.archived,
                        createdAt = testEntity.createdAt,
                        updatedAt = testEntity.updatedAt,
                    ),
                    questionCount = qCount,
                    examName = exam?.name,
                )
            }
        }
    }

    override suspend fun getTestById(testId: Long): Test? = withContext(ioDispatcher) {
        testDao.getTestById(testId)?.let {
            Test(
                id = it.id,
                uuid = it.uuid,
                sourceId = it.sourceId,
                examId = it.examId,
                title = it.title,
                kind = it.kind,
                durationSec = it.durationSec,
                marksCorrect = it.marksCorrect.toFloat(),
                negativeMarks = it.negativeMarks.toFloat(),
                archived = it.archived,
                createdAt = it.createdAt,
                updatedAt = it.updatedAt,
            )
        }
    }


    override suspend fun startExamSession(testId: Long): Long = withContext(ioDispatcher) {
        db.withTransaction {
            val test = testDao.getTestById(testId)
                ?: throw IllegalArgumentException("Test $testId not found")

            val testQuestions = testQuestionDao.getQuestionsForTestOnce(testId)
            val now = System.currentTimeMillis()

            val attempt = AttemptEntity(
                uuid = UUID.randomUUID().toString(),
                testId = testId,
                mode = AttemptMode.EXAM.name,
                scopeDesc = test.title,
                status = AttemptStatus.IN_PROGRESS.name,
                startedAt = now,
                updatedAt = now,
                timeLimitSec = test.durationSec,
                activeElapsedMs = 0L,
                createdAt = now,
            )
            val attemptId = attemptDao.insert(attempt)

            val answers = testQuestions.mapIndexed { index, tq ->
                AnswerEntity(
                    attemptId = attemptId,
                    questionId = tq.questionId,
                    position = index,
                    status = AnswerStatus.UNSEEN.name,
                )
            }
            answerDao.insertAll(answers)

            attemptId
        }
    }

    override suspend fun getAttempt(attemptId: Long): Attempt? = withContext(ioDispatcher) {
        attemptDao.getAttemptById(attemptId)?.toDomain()
    }

    override fun getAttemptFlow(attemptId: Long): Flow<Attempt?> {
        return attemptDao.getAttemptByIdFlow(attemptId).map { it?.toDomain() }
    }

    override suspend fun getLatestInProgressExamAttempt(): Attempt? = withContext(ioDispatcher) {
        attemptDao.getLatestInProgressAttempt(AttemptMode.EXAM.name)?.toDomain()
    }

    override fun getExamQuestionItems(attemptId: Long): Flow<List<ExamQuestionItem>> {
        return answerDao.getAnswersForAttempt(attemptId).map { answers ->
            answers.map { answerEntity ->
                buildExamQuestionItem(answerEntity)
            }
        }
    }

    override suspend fun getExamQuestionItemsOnce(attemptId: Long): List<ExamQuestionItem> = withContext(ioDispatcher) {
        val answers = answerDao.getAnswersForAttemptOnce(attemptId)
        answers.map { buildExamQuestionItem(it) }
    }

    private suspend fun buildExamQuestionItem(answerEntity: AnswerEntity): ExamQuestionItem {
        val questionEntity = questionDao.getQuestionById(answerEntity.questionId)
            ?: throw IllegalStateException("Question not found for answer ${answerEntity.id}")

        val options = questionOptionDao.getOptionsForQuestionOnce(questionEntity.id).map {
            QuestionOption(
                id = it.id,
                questionId = it.questionId,
                position = it.position,
                text = it.text,
                isCorrect = it.isCorrect,
            )
        }

        val question = Question(
            id = questionEntity.id,
            uuid = questionEntity.uuid,
            sourceId = questionEntity.sourceId,
            externalId = questionEntity.externalId,
            text = questionEntity.text,
            format = questionEntity.format,
            subjectId = questionEntity.subjectId,
            topicId = questionEntity.topicId,
            subTopicId = questionEntity.subTopicId,
            difficulty = questionEntity.difficulty,
            sourceTimestampSec = questionEntity.sourceTimestampSec,
            sourceUrl = questionEntity.sourceUrl,
            language = questionEntity.language,
            hasSolution = questionEntity.hasSolution,
            options = options,
        )

        return ExamQuestionItem(
            question = question,
            answer = answerEntity.toDomain(),
        )
    }

    override suspend fun saveOptionResponse(
        answerId: Long,
        optionId: Long?,
        status: AnswerStatus,
        timeSpentMs: Long,
    ) = withContext(ioDispatcher) {
        db.withTransaction {
            val answer = answerDao.getAnswerById(answerId) ?: return@withTransaction
            val now = System.currentTimeMillis()

            val selectedOption = if (optionId != null) {
                questionOptionDao.getOptionsForQuestionOnce(answer.questionId).firstOrNull { it.id == optionId }
            } else {
                null
            }

            val isCorrect = selectedOption?.isCorrect
            val isFirstSubmission = answer.firstSubmissionCorrect == null

            val updated = answer.copy(
                selectedOptionId = optionId,
                isCorrect = isCorrect,
                status = status.name,
                timeSpentMs = timeSpentMs,
                answeredAt = if (optionId != null) now else null,
                firstSubmissionCorrect = if (optionId != null && isFirstSubmission) isCorrect else answer.firstSubmissionCorrect,
            )
            answerDao.update(updated)

            if (optionId != null && selectedOption != null) {
                answerSubmissionDao.insert(
                    AnswerSubmissionEntity(
                        answerId = answerId,
                        optionId = optionId,
                        submittedAt = now,
                        hintsOpenAtSubmit = 0,
                        msSinceFirstSeen = timeSpentMs,
                        isCorrect = selectedOption.isCorrect,
                    )
                )
            }
        }
    }

    override suspend fun updateExamActiveTime(
        attemptId: Long,
        activeElapsedMs: Long,
    ) = withContext(ioDispatcher) {
        val attempt = attemptDao.getAttemptById(attemptId) ?: return@withContext
        val updated = attempt.copy(
            activeElapsedMs = activeElapsedMs,
            updatedAt = System.currentTimeMillis(),
        )
        attemptDao.update(updated)
    }

    override suspend fun submitExam(attemptId: Long): TestScoreReport = withContext(ioDispatcher) {
        db.withTransaction {
            val attempt = attemptDao.getAttemptById(attemptId)
                ?: throw IllegalArgumentException("Attempt $attemptId not found")

            val test = attempt.testId?.let { testDao.getTestById(it) }
            val marksCorrect = test?.marksCorrect?.toFloat() ?: 1.0f
            val negativeMarks = test?.negativeMarks?.toFloat() ?: 0.25f


            val answers = answerDao.getAnswersForAttemptOnce(attemptId)
            val domainAnswers = answers.map { it.toDomain() }

            val summary = Scorer.calculateScore(
                answers = domainAnswers,
                marksCorrect = marksCorrect,
                negativeMarks = negativeMarks,
            )

            val now = System.currentTimeMillis()
            val updatedAttempt = attempt.copy(
                status = AttemptStatus.SUBMITTED.name,
                submittedAt = now,
                score = summary.score,
                updatedAt = now,
            )
            attemptDao.update(updatedAttempt)

            // Auto-record wrong answers into mistake book & revision items
            for (ans in answers) {
                if (ans.selectedOptionId != null && ans.isCorrect == false) {
                    val existing = mistakeDao.getMistakeByQuestionId(ans.questionId)
                    if (existing == null) {
                        mistakeDao.upsert(
                            MistakeEntity(
                                questionId = ans.questionId,
                                firstWrongAt = now,
                                lastWrongAt = now,
                                wrongCount = 1,
                                consecutiveCorrect = 0,
                                status = "ACTIVE",
                            )
                        )
                    } else {
                        mistakeDao.upsert(
                            existing.copy(
                                lastWrongAt = now,
                                wrongCount = existing.wrongCount + 1,
                                consecutiveCorrect = 0,
                                status = "ACTIVE",
                            )
                        )
                    }
                    if (revisionDao.getItemByQuestionId(ans.questionId) == null) {
                        revisionDao.upsertItem(
                            RevisionItemEntity(
                                questionId = ans.questionId,
                                box = 0,
                                dueAt = now,
                                reviewCount = 0,
                                suspended = false,
                            )
                        )
                    }
                    revisionDao.insertReason(RevisionReasonEntity(ans.questionId, "WRONG", now))
                } else if (ans.isCorrect == true) {
                    val existing = mistakeDao.getMistakeByQuestionId(ans.questionId)
                    if (existing != null) {
                        val newConsecutive = existing.consecutiveCorrect + 1
                        val newStatus = if (newConsecutive >= 2) "RESOLVED" else existing.status
                        mistakeDao.upsert(
                            existing.copy(
                                consecutiveCorrect = newConsecutive,
                                status = newStatus,
                            )
                        )
                    }
                }
            }

            // Record progress in study_day
            val todayStr = java.time.LocalDate.now().toString()
            val totalAttempted = answers.count { it.status == AnswerStatus.ANSWERED.name || it.status == AnswerStatus.ANSWERED_MARKED.name }
            val totalCorrect = answers.count { it.isCorrect == true }
            studyDayDao.recordProgress(
                date = todayStr,
                attempted = totalAttempted,
                correct = totalCorrect,
                activeMs = updatedAttempt.activeElapsedMs,
            )

            val questionReviews = answers.mapIndexed { index, ansEntity ->
                val qEntity = questionDao.getQuestionById(ansEntity.questionId)
                val options = questionOptionDao.getOptionsForQuestionOnce(ansEntity.questionId).map {
                    QuestionOption(
                        id = it.id,
                        questionId = it.questionId,
                        position = it.position,
                        text = it.text,
                        isCorrect = it.isCorrect,
                    )
                }
                val hints = hintDao.getHintsForQuestionOnce(ansEntity.questionId).map {
                    Hint(
                        id = it.id,
                        questionId = it.questionId,
                        number = it.number,
                        title = it.title,
                        description = it.description,
                        isUnlocked = true, // in review mode after submission, all hints are unlocked
                    )
                }
                val tricks = trickDao.getTricksForQuestionOnce(ansEntity.questionId).map {
                    Trick(
                        id = it.id,
                        questionId = it.questionId,
                        name = it.name,
                        description = it.description,
                    )
                }
                val steps = solutionStepDao.getStepsForQuestionOnce(ansEntity.questionId).map {
                    SolutionStep(
                        id = it.id,
                        questionId = it.questionId,
                        number = it.number,
                        action = it.action,
                    )
                }

                val question = Question(
                    id = ansEntity.questionId,
                    uuid = qEntity?.uuid ?: "",
                    sourceId = qEntity?.sourceId ?: 0L,
                    externalId = qEntity?.externalId ?: "",
                    text = qEntity?.text ?: "",
                    format = qEntity?.format ?: "MCQ",
                    subjectId = qEntity?.subjectId,
                    topicId = qEntity?.topicId,
                    subTopicId = qEntity?.subTopicId,
                    difficulty = qEntity?.difficulty,
                    sourceTimestampSec = qEntity?.sourceTimestampSec,
                    sourceUrl = qEntity?.sourceUrl,
                    language = qEntity?.language,
                    hasSolution = qEntity?.hasSolution ?: false,
                    options = options,
                    hints = hints,
                    tricks = tricks,
                    solutionSteps = steps,
                )

                val selectedOpt = options.firstOrNull { it.id == ansEntity.selectedOptionId }
                val correctOpt = options.firstOrNull { it.isCorrect }

                QuestionReviewItem(
                    position = index,
                    question = question,
                    answer = ansEntity.toDomain(),
                    selectedOption = selectedOpt,
                    correctOption = correctOpt,
                    isCorrect = ansEntity.isCorrect == true,
                    timeSpentSec = ansEntity.timeSpentMs / 1000L,
                )
            }

            TestScoreReport(
                attemptId = attemptId,
                testTitle = test?.title ?: "Mock Test",
                totalQuestions = summary.totalQuestions,
                attemptedCount = summary.attemptedCount,
                correctCount = summary.correctCount,
                wrongCount = summary.wrongCount,
                unattemptedCount = summary.unattemptedCount,
                markedCount = summary.markedCount,
                score = summary.score,
                maxScore = summary.maxScore,
                negativeMarksDeducted = summary.negativeMarksDeducted,
                accuracyPercentage = summary.accuracyPercentage,
                timeSpentSec = summary.timeSpentSec,
                questionReviews = questionReviews,
            )
        }
    }

    override suspend fun getTestScoreReport(attemptId: Long): TestScoreReport? = withContext(ioDispatcher) {
        val attempt = attemptDao.getAttemptById(attemptId) ?: return@withContext null
        val test = attempt.testId?.let { testDao.getTestById(it) }
        val marksCorrect = test?.marksCorrect?.toFloat() ?: 1.0f
        val negativeMarks = test?.negativeMarks?.toFloat() ?: 0.25f


        val answers = answerDao.getAnswersForAttemptOnce(attemptId)
        val domainAnswers = answers.map { it.toDomain() }

        val summary = Scorer.calculateScore(
            answers = domainAnswers,
            marksCorrect = marksCorrect,
            negativeMarks = negativeMarks,
        )

        val questionReviews = answers.mapIndexed { index, ansEntity ->
            val qEntity = questionDao.getQuestionById(ansEntity.questionId)
            val options = questionOptionDao.getOptionsForQuestionOnce(ansEntity.questionId).map {
                QuestionOption(
                    id = it.id,
                    questionId = it.questionId,
                    position = it.position,
                    text = it.text,
                    isCorrect = it.isCorrect,
                )
            }
            val hints = hintDao.getHintsForQuestionOnce(ansEntity.questionId).map {
                Hint(
                    id = it.id,
                    questionId = it.questionId,
                    number = it.number,
                    title = it.title,
                    description = it.description,
                    isUnlocked = true,
                )
            }
            val tricks = trickDao.getTricksForQuestionOnce(ansEntity.questionId).map {
                Trick(
                    id = it.id,
                    questionId = it.questionId,
                    name = it.name,
                    description = it.description,
                )
            }
            val steps = solutionStepDao.getStepsForQuestionOnce(ansEntity.questionId).map {
                SolutionStep(
                    id = it.id,
                    questionId = it.questionId,
                    number = it.number,
                    action = it.action,
                )
            }

            val question = Question(
                id = ansEntity.questionId,
                uuid = qEntity?.uuid ?: "",
                sourceId = qEntity?.sourceId ?: 0L,
                externalId = qEntity?.externalId ?: "",
                text = qEntity?.text ?: "",
                format = qEntity?.format ?: "MCQ",
                subjectId = qEntity?.subjectId,
                topicId = qEntity?.topicId,
                subTopicId = qEntity?.subTopicId,
                difficulty = qEntity?.difficulty,
                sourceTimestampSec = qEntity?.sourceTimestampSec,
                sourceUrl = qEntity?.sourceUrl,
                language = qEntity?.language,
                hasSolution = qEntity?.hasSolution ?: false,
                options = options,
                hints = hints,
                tricks = tricks,
                solutionSteps = steps,
            )

            val selectedOpt = options.firstOrNull { it.id == ansEntity.selectedOptionId }
            val correctOpt = options.firstOrNull { it.isCorrect }

            QuestionReviewItem(
                position = index,
                question = question,
                answer = ansEntity.toDomain(),
                selectedOption = selectedOpt,
                correctOption = correctOpt,
                isCorrect = ansEntity.isCorrect == true,
                timeSpentSec = ansEntity.timeSpentMs / 1000L,
            )
        }

        TestScoreReport(
            attemptId = attemptId,
            testTitle = test?.title ?: "Mock Test",
            totalQuestions = summary.totalQuestions,
            attemptedCount = summary.attemptedCount,
            correctCount = summary.correctCount,
            wrongCount = summary.wrongCount,
            unattemptedCount = summary.unattemptedCount,
            markedCount = summary.markedCount,
            score = summary.score,
            maxScore = summary.maxScore,
            negativeMarksDeducted = summary.negativeMarksDeducted,
            accuracyPercentage = summary.accuracyPercentage,
            timeSpentSec = summary.timeSpentSec,
            questionReviews = questionReviews,
        )
    }

    private fun AttemptEntity.toDomain() = Attempt(
        id = id,
        uuid = uuid,
        testId = testId,
        mode = AttemptMode.valueOf(mode),
        scopeDesc = scopeDesc,
        status = AttemptStatus.valueOf(status),
        startedAt = startedAt,
        submittedAt = submittedAt,
        timeLimitSec = timeLimitSec,
        activeElapsedMs = activeElapsedMs,
        score = score,
        isRetry = isRetry,
        createdAt = createdAt,
        updatedAt = updatedAt,
    )

    private fun AnswerEntity.toDomain() = Answer(
        id = id,
        attemptId = attemptId,
        questionId = questionId,
        position = position,
        status = AnswerStatus.valueOf(status),
        selectedOptionId = selectedOptionId,
        isCorrect = isCorrect,
        timeSpentMs = timeSpentMs,
        firstSeenAt = firstSeenAt,
        answeredAt = answeredAt,
        firstSubmissionCorrect = firstSubmissionCorrect,
        hintsOpened = hintsOpened,
        solvedAtHintLevel = solvedAtHintLevel,
        revealed = revealed,
    )
}
