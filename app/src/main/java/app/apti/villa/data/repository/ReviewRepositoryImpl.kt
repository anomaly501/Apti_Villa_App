package app.apti.villa.data.repository

import androidx.room.withTransaction
import app.apti.villa.core.common.IoDispatcher
import app.apti.villa.data.local.dao.*
import app.apti.villa.data.local.db.AppDatabase
import app.apti.villa.data.local.entity.*
import app.apti.villa.domain.engine.revision.LeitnerScheduler
import app.apti.villa.domain.model.*
import app.apti.villa.domain.repository.ReviewRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import java.util.UUID
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ReviewRepositoryImpl @Inject constructor(
    private val db: AppDatabase,
    private val mistakeDao: MistakeDao,
    private val revisionDao: RevisionDao,
    private val questionDao: QuestionDao,
    private val questionOptionDao: QuestionOptionDao,
    private val hintDao: HintDao,
    private val trickDao: TrickDao,
    private val solutionStepDao: SolutionStepDao,
    private val questionTagDao: QuestionTagDao,
    private val savedTrickDao: SavedTrickDao,
    private val tagDao: TagDao,
    private val attemptDao: AttemptDao,
    private val answerDao: AnswerDao,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher,
) : ReviewRepository {

    override fun getActiveMistakes(): Flow<List<Mistake>> {
        return mistakeDao.getActiveMistakes().map { list ->
            list.mapNotNull { entity ->
                val question = buildFullQuestion(entity.questionId) ?: return@mapNotNull null
                Mistake(
                    question = question,
                    firstWrongAt = entity.firstWrongAt,
                    lastWrongAt = entity.lastWrongAt,
                    wrongCount = entity.wrongCount,
                    consecutiveCorrect = entity.consecutiveCorrect,
                    status = entity.status,
                )
            }
        }
    }

    override fun getResolvedMistakes(): Flow<List<Mistake>> {
        return mistakeDao.getResolvedMistakes().map { list ->
            list.mapNotNull { entity ->
                val question = buildFullQuestion(entity.questionId) ?: return@mapNotNull null
                Mistake(
                    question = question,
                    firstWrongAt = entity.firstWrongAt,
                    lastWrongAt = entity.lastWrongAt,
                    wrongCount = entity.wrongCount,
                    consecutiveCorrect = entity.consecutiveCorrect,
                    status = entity.status,
                )
            }
        }
    }

    override suspend fun recordMistake(questionId: Long) = withContext(ioDispatcher) {
        db.withTransaction {
            val now = System.currentTimeMillis()
            val existing = mistakeDao.getMistakeByQuestionId(questionId)
            if (existing == null) {
                mistakeDao.upsert(
                    MistakeEntity(
                        questionId = questionId,
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

            // Ensure revision_item exists for spaced repetition
            val revItem = revisionDao.getItemByQuestionId(questionId)
            if (revItem == null) {
                revisionDao.upsertItem(
                    RevisionItemEntity(
                        questionId = questionId,
                        box = 0,
                        dueAt = now,
                        lastReviewedAt = null,
                        reviewCount = 0,
                        suspended = false,
                    )
                )
            }
            revisionDao.insertReason(
                RevisionReasonEntity(
                    questionId = questionId,
                    reason = "WRONG",
                    createdAt = now,
                )
            )
        }
    }

    override suspend fun recordMistakeRetry(questionId: Long, isCorrect: Boolean) = withContext(ioDispatcher) {
        db.withTransaction {
            val existing = mistakeDao.getMistakeByQuestionId(questionId) ?: return@withTransaction
            val now = System.currentTimeMillis()
            if (isCorrect) {
                val newConsecutive = existing.consecutiveCorrect + 1
                val newStatus = if (newConsecutive >= 2) "RESOLVED" else existing.status
                mistakeDao.upsert(
                    existing.copy(
                        consecutiveCorrect = newConsecutive,
                        status = newStatus,
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
        }
    }

    override suspend fun updateMistakeStatus(questionId: Long, status: String) = withContext(ioDispatcher) {
        mistakeDao.updateStatus(questionId, status)
    }

    override suspend fun deleteMistake(questionId: Long) = withContext(ioDispatcher) {
        mistakeDao.delete(questionId)
    }

    override suspend fun startMistakePracticeSession(): Long = withContext(ioDispatcher) {
        db.withTransaction {
            val mistakes = mistakeDao.getActiveMistakesOnce()
            if (mistakes.isEmpty()) return@withTransaction 0L

            val now = System.currentTimeMillis()
            val attempt = AttemptEntity(
                uuid = UUID.randomUUID().toString(),
                testId = null,
                mode = AttemptMode.PRACTICE.name,
                scopeDesc = "Mistake Retry (${mistakes.size} Qs)",
                status = AttemptStatus.IN_PROGRESS.name,
                startedAt = now,
                updatedAt = now,
                timeLimitSec = null,
                isRetry = true,
                createdAt = now,
            )
            val attemptId = attemptDao.insert(attempt)

            val answers = mistakes.mapIndexed { index, mistake ->
                AnswerEntity(
                    attemptId = attemptId,
                    questionId = mistake.questionId,
                    position = index,
                    status = AnswerStatus.UNSEEN.name,
                )
            }
            answerDao.insertAll(answers)

            attemptId
        }
    }

    override fun getDueRevisionItems(): Flow<List<RevisionItem>> {
        val now = System.currentTimeMillis()
        return revisionDao.getDueItems(now).map { list ->
            list.mapNotNull { entity ->
                val question = buildFullQuestion(entity.questionId) ?: return@mapNotNull null
                val reasons = revisionDao.getReasonsForQuestion(entity.questionId).map { it.reason }
                RevisionItem(
                    question = question,
                    box = entity.box,
                    dueAt = entity.dueAt,
                    lastReviewedAt = entity.lastReviewedAt,
                    reviewCount = entity.reviewCount,
                    suspended = entity.suspended,
                    reasons = reasons,
                    isDue = LeitnerScheduler.isDue(entity.dueAt, now),
                )
            }
        }
    }

    override fun getRevisionBoxStats(): Flow<RevisionBoxStats> {
        val now = System.currentTimeMillis()
        return combine(
            listOf(
                revisionDao.countItemsInBox(0),
                revisionDao.countItemsInBox(1),
                revisionDao.countItemsInBox(2),
                revisionDao.countItemsInBox(3),
                revisionDao.countItemsInBox(4),
                revisionDao.countItemsInBox(5),
                revisionDao.countDueItems(now),
            )
        ) { counts ->
            RevisionBoxStats(
                box0Count = counts[0],
                box1Count = counts[1],
                box2Count = counts[2],
                box3Count = counts[3],
                box4Count = counts[4],
                box5Count = counts[5],
                totalDueToday = counts[6],
            )
        }
    }

    override suspend fun recordRevisionResult(questionId: Long, isCorrect: Boolean) = withContext(ioDispatcher) {
        db.withTransaction {
            val item = revisionDao.getItemByQuestionId(questionId) ?: return@withTransaction
            val now = System.currentTimeMillis()

            if (isCorrect) {
                val schedule = LeitnerScheduler.promote(item.box, now)
                revisionDao.updateBoxAndDue(questionId, schedule.newBox, schedule.dueAt, now)
            } else {
                val schedule = LeitnerScheduler.demote(now)
                revisionDao.updateBoxAndDue(questionId, schedule.newBox, schedule.dueAt, now)
            }
        }
    }

    override suspend fun setRevisionSuspended(questionId: Long, suspended: Boolean) = withContext(ioDispatcher) {
        revisionDao.setSuspended(questionId, suspended)
    }

    override suspend fun startRevisionPracticeSession(): Long = withContext(ioDispatcher) {
        db.withTransaction {
            val now = System.currentTimeMillis()
            val dueItems = revisionDao.getDueItemsOnce(now)
            if (dueItems.isEmpty()) return@withTransaction 0L

            val attempt = AttemptEntity(
                uuid = UUID.randomUUID().toString(),
                testId = null,
                mode = AttemptMode.PRACTICE.name,
                scopeDesc = "Daily Revision (${dueItems.size} Qs)",
                status = AttemptStatus.IN_PROGRESS.name,
                startedAt = now,
                updatedAt = now,
                timeLimitSec = null,
                isRetry = true,
                createdAt = now,
            )
            val attemptId = attemptDao.insert(attempt)

            val answers = dueItems.mapIndexed { index, item ->
                AnswerEntity(
                    attemptId = attemptId,
                    questionId = item.questionId,
                    position = index,
                    status = AnswerStatus.UNSEEN.name,
                )
            }
            answerDao.insertAll(answers)

            attemptId
        }
    }

    override fun getBookmarkedQuestions(): Flow<List<BookmarkedQuestion>> {
        return questionDao.getAllQuestions().map { questions ->
            questions.mapNotNull { qEntity ->
                val tags = questionTagDao.getTagsForQuestionOnce(qEntity.id)
                val importantTag = tags.firstOrNull { it.isSystem && it.name.equals("Important", ignoreCase = true) }
                    ?: return@mapNotNull null

                val question = buildFullQuestion(qEntity.id) ?: return@mapNotNull null
                BookmarkedQuestion(
                    question = question,
                    tag = Tag(
                        id = importantTag.id,
                        uuid = importantTag.uuid,
                        name = importantTag.name,
                        color = importantTag.color,
                        isSystem = importantTag.isSystem,
                        createdAt = importantTag.createdAt,
                        updatedAt = importantTag.updatedAt,
                    ),
                    bookmarkedAt = System.currentTimeMillis(),
                )
            }
        }
    }

    override fun getSavedTricksWithQuestions(): Flow<List<SavedTrickWithQuestion>> {
        return trickDao.getSavedTricks().map { tricks ->
            tricks.mapNotNull { trickEntity ->
                val question = buildFullQuestion(trickEntity.questionId) ?: return@mapNotNull null
                SavedTrickWithQuestion(
                    trick = Trick(
                        id = trickEntity.id,
                        questionId = trickEntity.questionId,
                        name = trickEntity.name,
                        description = trickEntity.description,
                        isSaved = true,
                    ),
                    question = question,
                )
            }
        }
    }


    override suspend fun deleteSavedTrick(trickId: Long) = withContext(ioDispatcher) {
        savedTrickDao.delete(trickId)
    }

    override suspend fun startBookmarkedPracticeSession(): Long = withContext(ioDispatcher) {
        db.withTransaction {
            val importantTag = tagDao.getTagByName("Important") ?: return@withTransaction 0L
            val allQuestions = questionDao.getAllQuestionsOnce()
            val bookmarkedIds = allQuestions.filter { q ->
                questionTagDao.hasTag(q.id, importantTag.id)
            }.map { it.id }

            if (bookmarkedIds.isEmpty()) return@withTransaction 0L

            val now = System.currentTimeMillis()
            val attempt = AttemptEntity(
                uuid = UUID.randomUUID().toString(),
                testId = null,
                mode = AttemptMode.PRACTICE.name,
                scopeDesc = "Bookmarked Questions (${bookmarkedIds.size} Qs)",
                status = AttemptStatus.IN_PROGRESS.name,
                startedAt = now,
                updatedAt = now,
                timeLimitSec = null,
                isRetry = true,
                createdAt = now,
            )
            val attemptId = attemptDao.insert(attempt)

            val answers = bookmarkedIds.mapIndexed { index, qId ->
                AnswerEntity(
                    attemptId = attemptId,
                    questionId = qId,
                    position = index,
                    status = AnswerStatus.UNSEEN.name,
                )
            }
            answerDao.insertAll(answers)

            attemptId
        }
    }

    private suspend fun buildFullQuestion(questionId: Long): Question? {
        val qEntity = questionDao.getQuestionById(questionId) ?: return null
        val options = questionOptionDao.getOptionsForQuestionOnce(questionId).map {
            QuestionOption(it.id, it.questionId, it.position, it.text, it.isCorrect)
        }
        val hints = hintDao.getHintsForQuestionOnce(questionId).map {
            Hint(it.id, it.questionId, it.number, it.title, it.description, isUnlocked = true)
        }
        val tricks = trickDao.getTricksForQuestionOnce(questionId).map {
            Trick(it.id, it.questionId, it.name, it.description, isSaved = savedTrickDao.isTrickSaved(it.id))
        }
        val steps = solutionStepDao.getStepsForQuestionOnce(questionId).map {
            SolutionStep(it.id, it.questionId, it.number, it.action)
        }
        val tags = questionTagDao.getTagsForQuestionOnce(questionId).map {
            Tag(it.id, it.uuid, it.name, it.color, it.isSystem, it.createdAt, it.updatedAt)
        }

        return Question(
            id = qEntity.id,
            uuid = qEntity.uuid,
            sourceId = qEntity.sourceId,
            externalId = qEntity.externalId,
            text = qEntity.text,
            format = qEntity.format,
            subjectId = qEntity.subjectId,
            topicId = qEntity.topicId,
            subTopicId = qEntity.subTopicId,
            difficulty = qEntity.difficulty,
            sourceTimestampSec = qEntity.sourceTimestampSec,
            sourceUrl = qEntity.sourceUrl,
            language = qEntity.language,
            hasSolution = qEntity.hasSolution,
            options = options,
            hints = hints,
            tricks = tricks,
            solutionSteps = steps,
            tags = tags,
            isDifficult = tags.any { it.isSystem && it.name.equals("Difficult", ignoreCase = true) },
            isBookmarked = tags.any { it.isSystem && it.name.equals("Important", ignoreCase = true) },
        )
    }
}
