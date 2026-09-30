package app.apti.villa.data.repository

import androidx.room.withTransaction
import app.apti.villa.core.common.IoDispatcher
import app.apti.villa.data.local.dao.*
import app.apti.villa.data.local.db.AppDatabase
import app.apti.villa.data.local.entity.*
import app.apti.villa.domain.engine.hint.HintLadder
import app.apti.villa.domain.model.*
import app.apti.villa.domain.repository.PracticeRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import java.util.UUID
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PracticeRepositoryImpl @Inject constructor(
    private val db: AppDatabase,
    private val attemptDao: AttemptDao,
    private val answerDao: AnswerDao,
    private val answerSubmissionDao: AnswerSubmissionDao,
    private val hintUsageDao: HintUsageDao,
    private val questionDao: QuestionDao,
    private val questionOptionDao: QuestionOptionDao,
    private val hintDao: HintDao,
    private val trickDao: TrickDao,
    private val solutionStepDao: SolutionStepDao,
    private val questionTagDao: QuestionTagDao,
    private val savedTrickDao: SavedTrickDao,
    private val topicDao: TopicDao,
    private val tagDao: TagDao,
    private val mistakeDao: MistakeDao,
    private val revisionDao: RevisionDao,
    private val studyDayDao: StudyDayDao,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher,
) : PracticeRepository {


    override fun getTopicsWithQuestionCounts(): Flow<List<TopicWithQuestionCount>> {
        return topicDao.getAllTopics().map { topics ->
            topics.map { topicEntity ->
                val count = questionDao.getQuestionCountByTopicOnce(topicEntity.id)
                TopicWithQuestionCount(
                    topic = Topic(
                        id = topicEntity.id,
                        subjectId = topicEntity.subjectId,
                        name = topicEntity.name,
                        createdAt = topicEntity.createdAt,
                        updatedAt = topicEntity.updatedAt,
                    ),
                    questionCount = count,
                )
            }
        }
    }

    override suspend fun startPracticeSession(filter: PracticeFilter): Long = withContext(ioDispatcher) {
        db.withTransaction {
            val questions = questionDao.getQuestionsFiltered(
                topicId = filter.topicId,
                difficulty = filter.difficulty,
                limit = filter.count,
            ).ifEmpty {
                questionDao.getQuestionsFiltered(
                    topicId = null,
                    difficulty = null,
                    limit = filter.count,
                )
            }

            val now = System.currentTimeMillis()
            val topic = filter.topicId?.let { topicDao.getTopicById(it) }
            val desc = buildString {
                append(topic?.name ?: "All Topics")
                if (!filter.difficulty.isNullOrBlank()) {
                    append(" • ${filter.difficulty}")
                }
                append(" (${questions.size} Qs)")
            }

            val attempt = AttemptEntity(
                uuid = UUID.randomUUID().toString(),
                testId = null,
                mode = AttemptMode.PRACTICE.name,
                scopeDesc = desc,
                status = AttemptStatus.IN_PROGRESS.name,
                startedAt = now,
                updatedAt = now,
                timeLimitSec = null,
                createdAt = now,
            )

            val attemptId = attemptDao.insert(attempt)

            val answers = questions.mapIndexed { index, question ->
                AnswerEntity(
                    attemptId = attemptId,
                    questionId = question.id,
                    position = index,
                    status = AnswerStatus.UNSEEN.name,
                )
            }
            answerDao.insertAll(answers)

            attemptId
        }
    }

    override fun getAttempt(attemptId: Long): Flow<Attempt?> {
        return attemptDao.getAttemptByIdFlow(attemptId).map { it?.toDomain() }
    }

    override suspend fun getAttemptOnce(attemptId: Long): Attempt? = withContext(ioDispatcher) {
        attemptDao.getAttemptById(attemptId)?.toDomain()
    }

    override suspend fun getLatestInProgressAttempt(): Attempt? = withContext(ioDispatcher) {
        attemptDao.getLatestInProgressAttempt(AttemptMode.PRACTICE.name)?.toDomain()
    }

    override fun getPracticeItems(attemptId: Long): Flow<List<PracticeSessionItem>> {
        return answerDao.getAnswersForAttempt(attemptId).map { answers ->
            answers.map { answerEntity ->
                buildPracticeItem(answerEntity)
            }
        }
    }

    override suspend fun getPracticeItemsOnce(attemptId: Long): List<PracticeSessionItem> = withContext(ioDispatcher) {
        val answers = answerDao.getAnswersForAttemptOnce(attemptId)
        answers.map { buildPracticeItem(it) }
    }

    private suspend fun buildPracticeItem(answerEntity: AnswerEntity): PracticeSessionItem {
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

        val hints = hintDao.getHintsForQuestionOnce(questionEntity.id).map {
            Hint(
                id = it.id,
                questionId = it.questionId,
                number = it.number,
                title = it.title,
                description = it.description,
                isUnlocked = HintLadder.isHintUnlocked(answerEntity.hintsOpened, it.number),
            )
        }

        val tricks = trickDao.getTricksForQuestionOnce(questionEntity.id).map {
            val isSaved = savedTrickDao.isTrickSaved(it.id)
            Trick(
                id = it.id,
                questionId = it.questionId,
                name = it.name,
                description = it.description,
                isSaved = isSaved,
            )
        }

        val steps = solutionStepDao.getStepsForQuestionOnce(questionEntity.id).map {
            SolutionStep(
                id = it.id,
                questionId = it.questionId,
                number = it.number,
                action = it.action,
            )
        }

        val tagEntities = questionTagDao.getTagsForQuestionOnce(questionEntity.id)
        val tags = tagEntities.map {
            Tag(
                id = it.id,
                uuid = it.uuid,
                name = it.name,
                color = it.color,
                isSystem = it.isSystem,
                createdAt = it.createdAt,
                updatedAt = it.updatedAt,
            )
        }

        val isDifficult = tags.any { it.isSystem && it.name.equals("Difficult", ignoreCase = true) }
        val isBookmarked = tags.any { it.isSystem && it.name.equals("Important", ignoreCase = true) }

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
            hints = hints,
            tricks = tricks,
            solutionSteps = steps,
            tags = tags,
            isDifficult = isDifficult,
            isBookmarked = isBookmarked,
        )

        val submissions = answerSubmissionDao.getSubmissionsForAnswerOnce(answerEntity.id).map {
            AnswerSubmission(
                id = it.id,
                answerId = it.answerId,
                optionId = it.optionId,
                submittedAt = it.submittedAt,
                hintsOpenAtSubmit = it.hintsOpenAtSubmit,
                msSinceFirstSeen = it.msSinceFirstSeen,
                isCorrect = it.isCorrect,
            )
        }

        val hintUsages = hintUsageDao.getUsagesForAnswerOnce(answerEntity.id).map {
            HintUsage(
                id = it.id,
                answerId = it.answerId,
                hintId = it.hintId,
                openedAt = it.openedAt,
                msSinceFirstSeen = it.msSinceFirstSeen,
            )
        }

        return PracticeSessionItem(
            question = question,
            answer = answerEntity.toDomain(),
            submissions = submissions,
            hintUsages = hintUsages,
        )
    }

    override suspend fun recordSubmission(
        answerId: Long,
        optionId: Long,
        msSinceFirstSeen: Long,
        isCorrect: Boolean,
    ): Answer = withContext(ioDispatcher) {
        db.withTransaction {
            val answer = answerDao.getAnswerById(answerId)
                ?: throw IllegalArgumentException("Answer $answerId not found")

            val now = System.currentTimeMillis()
            val submission = AnswerSubmissionEntity(
                answerId = answerId,
                optionId = optionId,
                submittedAt = now,
                hintsOpenAtSubmit = answer.hintsOpened,
                msSinceFirstSeen = msSinceFirstSeen,
                isCorrect = isCorrect,
            )
            answerSubmissionDao.insert(submission)

            val isFirstSubmission = answer.firstSubmissionCorrect == null
            val updated = answer.copy(
                selectedOptionId = optionId,
                isCorrect = if (isCorrect) true else (answer.isCorrect ?: false),
                status = if (isCorrect) AnswerStatus.ANSWERED.name else answer.status,
                firstSubmissionCorrect = if (isFirstSubmission) isCorrect else answer.firstSubmissionCorrect,
                answeredAt = if (isCorrect) now else answer.answeredAt,
                solvedAtHintLevel = if (isCorrect && answer.solvedAtHintLevel == null) {
                    HintLadder.calculateSolvedAtHintLevel(answer.hintsOpened, answer.revealed)
                } else {
                    answer.solvedAtHintLevel
                },
            )
            answerDao.update(updated)

            // Auto-record or resolve mistakes for spaced repetition
            if (!isCorrect) {
                val existingMistake = mistakeDao.getMistakeByQuestionId(answer.questionId)
                if (existingMistake == null) {
                    mistakeDao.upsert(
                        MistakeEntity(
                            questionId = answer.questionId,
                            firstWrongAt = now,
                            lastWrongAt = now,
                            wrongCount = 1,
                            consecutiveCorrect = 0,
                            status = "ACTIVE",
                        )
                    )
                } else {
                    mistakeDao.upsert(
                        existingMistake.copy(
                            lastWrongAt = now,
                            wrongCount = existingMistake.wrongCount + 1,
                            consecutiveCorrect = 0,
                            status = "ACTIVE",
                        )
                    )
                }
                if (revisionDao.getItemByQuestionId(answer.questionId) == null) {
                    revisionDao.upsertItem(
                        RevisionItemEntity(
                            questionId = answer.questionId,
                            box = 0,
                            dueAt = now,
                            reviewCount = 0,
                            suspended = false,
                        )
                    )
                }
                revisionDao.insertReason(RevisionReasonEntity(answer.questionId, "WRONG", now))
            } else {
                val existingMistake = mistakeDao.getMistakeByQuestionId(answer.questionId)
                if (existingMistake != null) {
                    val newConsecutive = existingMistake.consecutiveCorrect + 1
                    val newStatus = if (newConsecutive >= 2) "RESOLVED" else existingMistake.status
                    mistakeDao.upsert(
                        existingMistake.copy(
                            consecutiveCorrect = newConsecutive,
                            status = newStatus,
                        )
                    )
                }
            }

            // Record progress in study_day
            val todayStr = java.time.LocalDate.now().toString()
            studyDayDao.recordProgress(
                date = todayStr,
                attempted = 1,
                correct = if (isCorrect) 1 else 0,
                activeMs = msSinceFirstSeen,
            )

            updated.toDomain()
        }
    }

    override suspend fun recordHintUsage(
        answerId: Long,
        hintId: Long,
        msSinceFirstSeen: Long,
    ): Answer = withContext(ioDispatcher) {
        db.withTransaction {
            val answer = answerDao.getAnswerById(answerId)
                ?: throw IllegalArgumentException("Answer $answerId not found")

            val now = System.currentTimeMillis()
            val usage = HintUsageEntity(
                answerId = answerId,
                hintId = hintId,
                openedAt = now,
                msSinceFirstSeen = msSinceFirstSeen,
            )
            hintUsageDao.insert(usage)

            val updated = answer.copy(
                hintsOpened = answer.hintsOpened + 1,
            )
            answerDao.update(updated)
            updated.toDomain()
        }
    }

    override suspend fun revealAnswer(
        answerId: Long,
        correctOptionId: Long?,
    ): Answer = withContext(ioDispatcher) {
        db.withTransaction {
            val answer = answerDao.getAnswerById(answerId)
                ?: throw IllegalArgumentException("Answer $answerId not found")

            val now = System.currentTimeMillis()
            val updated = answer.copy(
                selectedOptionId = correctOptionId ?: answer.selectedOptionId,
                revealed = true,
                status = AnswerStatus.ANSWERED.name,
                solvedAtHintLevel = null,
                answeredAt = answer.answeredAt ?: now,
            )
            answerDao.update(updated)
            updated.toDomain()
        }
    }

    override suspend fun updateAnswerTime(
        answerId: Long,
        timeSpentMs: Long,
    ) = withContext(ioDispatcher) {
        val answer = answerDao.getAnswerById(answerId) ?: return@withContext
        val updated = answer.copy(timeSpentMs = timeSpentMs)
        answerDao.update(updated)
    }

    override suspend fun updateAttemptActiveTime(
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

    override suspend fun completeAttempt(attemptId: Long) = withContext(ioDispatcher) {
        db.withTransaction {
            val attempt = attemptDao.getAttemptById(attemptId) ?: return@withTransaction
            val correctCount = answerDao.countCorrectAnswers(attemptId)
            val now = System.currentTimeMillis()
            val updated = attempt.copy(
                status = AttemptStatus.SUBMITTED.name,
                submittedAt = now,
                score = correctCount.toFloat(),
                updatedAt = now,
            )
            attemptDao.update(updated)
        }
    }

    override suspend fun toggleSaveTrick(trickId: Long): Boolean = withContext(ioDispatcher) {
        val isSaved = savedTrickDao.isTrickSaved(trickId)
        if (isSaved) {
            savedTrickDao.delete(trickId)
            false
        } else {
            savedTrickDao.insert(
                SavedTrickEntity(
                    trickId = trickId,
                    createdAt = System.currentTimeMillis(),
                )
            )
            true
        }
    }

    override suspend fun toggleQuestionTag(questionId: Long, tagId: Long): Boolean = withContext(ioDispatcher) {
        val has = questionTagDao.hasTag(questionId, tagId)
        if (has) {
            questionTagDao.delete(questionId, tagId)
            false
        } else {
            questionTagDao.insert(
                QuestionTagEntity(
                    questionId = questionId,
                    tagId = tagId,
                    createdAt = System.currentTimeMillis(),
                )
            )
            true
        }
    }

    override suspend fun getQuestionDetail(questionId: Long): Question? = withContext(ioDispatcher) {
        val questionEntity = questionDao.getQuestionById(questionId) ?: return@withContext null
        val options = questionOptionDao.getOptionsForQuestionOnce(questionEntity.id).map {
            QuestionOption(
                id = it.id,
                questionId = it.questionId,
                position = it.position,
                text = it.text,
                isCorrect = it.isCorrect,
            )
        }
        val hints = hintDao.getHintsForQuestionOnce(questionEntity.id).map {
            Hint(
                id = it.id,
                questionId = it.questionId,
                number = it.number,
                title = it.title,
                description = it.description,
                isUnlocked = true,
            )
        }
        val tricks = trickDao.getTricksForQuestionOnce(questionEntity.id).map {
            val isSaved = savedTrickDao.isTrickSaved(it.id)
            Trick(
                id = it.id,
                questionId = it.questionId,
                name = it.name,
                description = it.description,
                isSaved = isSaved,
            )
        }
        val steps = solutionStepDao.getStepsForQuestionOnce(questionEntity.id).map {
            SolutionStep(
                id = it.id,
                questionId = it.questionId,
                number = it.number,
                action = it.action,
            )
        }
        val tagEntities = questionTagDao.getTagsForQuestionOnce(questionEntity.id)
        val tags = tagEntities.map {
            Tag(
                id = it.id,
                uuid = it.uuid,
                name = it.name,
                color = it.color,
                isSystem = it.isSystem,
                createdAt = it.createdAt,
                updatedAt = it.updatedAt,
            )
        }
        val isDifficult = tags.any { it.isSystem && it.name.equals("Difficult", ignoreCase = true) }
        val isBookmarked = tags.any { it.isSystem && it.name.equals("Important", ignoreCase = true) }

        Question(
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
            hints = hints,
            tricks = tricks,
            solutionSteps = steps,
            tags = tags,
            isDifficult = isDifficult,
            isBookmarked = isBookmarked,
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
