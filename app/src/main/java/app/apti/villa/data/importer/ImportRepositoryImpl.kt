package app.apti.villa.data.importer

import androidx.room.withTransaction
import app.apti.villa.core.common.AppError
import app.apti.villa.core.common.Outcome
import app.apti.villa.data.local.dao.*
import app.apti.villa.data.local.db.AppDatabase
import app.apti.villa.data.local.entity.*
import app.apti.villa.domain.importer.*
import java.util.UUID
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ImportRepositoryImpl @Inject constructor(
    private val db: AppDatabase,
    private val sourceDao: SourceDao,
    private val testDao: TestDao,
    private val questionDao: QuestionDao,
    private val questionOptionDao: QuestionOptionDao,
    private val testQuestionDao: TestQuestionDao,
    private val hintDao: HintDao,
    private val trickDao: TrickDao,
    private val solutionStepDao: SolutionStepDao,
    private val subjectDao: SubjectDao,
    private val topicDao: TopicDao,
    private val subTopicDao: SubTopicDao,
) : ImportRepository {

    private val jsonParser = JsonParser()
    private val validator = ContentValidator()

    override suspend fun parseAndValidate(jsonString: String): Outcome<ImportPlan> {
        val parseOutcome = jsonParser.parse(jsonString)
        if (parseOutcome is Outcome.Error) {
            return parseOutcome
        }
        val root = (parseOutcome as Outcome.Success).data
        val plan = validator.validate(root)
        val deduplicatedPlan = deduplicate(plan)
        return Outcome.Success(deduplicatedPlan)
    }

    override suspend fun deduplicate(plan: ImportPlan): ImportPlan {
        val updatedIssues = plan.issues.toMutableList()
        var dupCount = 0

        plan.validQuestions.forEachIndexed { index, q ->
            val existing = questionDao.getQuestionByHash(q.contentHash)
            if (existing != null) {
                dupCount++
                updatedIssues.add(
                    ImportIssue(
                        path = "$.questions[$index]",
                        code = IssueCodes.W_DUPLICATE_OF_EXISTING,
                        severity = IssueSeverity.WARNING,
                        questionIndex = index,
                        questionId = q.externalId,
                        message = "Question #${index + 1} (id: ${q.externalId}): Exact duplicate of existing question (DB id: ${existing.id}).",
                    )
                )
            }
        }

        return plan.copy(
            issues = updatedIssues,
            stats = plan.stats.copy(
                warningCount = updatedIssues.count { it.severity == IssueSeverity.WARNING },
            ),
        )
    }

    override suspend fun commit(
        plan: ImportPlan,
        examId: Long?,
        subjectId: Long?,
        customTestTitle: String?,
        durationMinutes: Int?,
        negativeMarks: Double?,
        positiveMarks: Double?,
    ): Outcome<ImportResult> {
        return try {
            val result = db.withTransaction {
                val now = System.currentTimeMillis()

                // 1. Resolve or create Subject
                var finalSubjectId = subjectId
                if (finalSubjectId == null && !plan.sourceMetadata.subject.isNullOrBlank()) {
                    val subjectName = plan.sourceMetadata.subject.trim()
                    val existing = subjectDao.getSubjectByName(subjectName)
                    finalSubjectId = existing?.id ?: subjectDao.insert(
                        SubjectEntity(name = subjectName, createdAt = now, updatedAt = now)
                    )
                }

                // 2. Resolve or create Topic
                val topicName = plan.sourceMetadata.topic.trim()
                val existingTopic = topicDao.getTopicBySubjectAndName(finalSubjectId, topicName)
                val finalTopicId = existingTopic?.id ?: topicDao.insert(
                    TopicEntity(subjectId = finalSubjectId, name = topicName, createdAt = now, updatedAt = now)
                )

                // 3. Resolve or create SubTopic if specified
                var finalSubTopicId: Long? = null
                val subTopicName = plan.sourceMetadata.subTopic?.trim()
                if (!subTopicName.isNullOrBlank()) {
                    val existingSub = subTopicDao.getSubTopicByTopicAndName(finalTopicId, subTopicName)
                    finalSubTopicId = existingSub?.id ?: subTopicDao.insert(
                        SubTopicEntity(topicId = finalTopicId, name = subTopicName, createdAt = now, updatedAt = now)
                    )
                }

                // 4. Insert Source
                val sourceUuid = UUID.randomUUID().toString()
                val sourceHash = ContentNormalizer.computeContentHash(
                    plan.sourceMetadata.title,
                    plan.validQuestions.map { it.contentHash },
                )
                val sourceEntity = SourceEntity(
                    uuid = sourceUuid,
                    title = plan.sourceMetadata.title,
                    type = plan.sourceMetadata.sourceType,
                    url = plan.sourceMetadata.sourceUrl,
                    channel = plan.sourceMetadata.channel,
                    schemaVersion = 1,
                    contentHash = sourceHash,
                    importedAt = now,
                    importReportJson = null,
                    createdAt = now,
                    updatedAt = now,
                )
                val sourceId = sourceDao.insert(sourceEntity)

                // 5. Insert Test
                val testUuid = UUID.randomUUID().toString()
                val testEntity = TestEntity(
                    uuid = testUuid,
                    sourceId = sourceId,
                    examId = examId,
                    title = customTestTitle?.trim()?.ifEmpty { null } ?: plan.sourceMetadata.title,
                    kind = "IMPORTED",
                    durationSec = durationMinutes?.let { it * 60 },
                    marksCorrect = positiveMarks ?: 1.0,
                    negativeMarks = negativeMarks ?: 0.0,
                    archived = false,
                    createdAt = now,
                    updatedAt = now,
                )
                val testId = testDao.insert(testEntity)

                // 6. Insert Questions & related tables
                plan.validQuestions.forEachIndexed { position, q ->
                    val qUuid = UUID.randomUUID().toString()
                    val questionEntity = QuestionEntity(
                        uuid = qUuid,
                        sourceId = sourceId,
                        externalId = q.externalId,
                        contentHash = q.contentHash,
                        text = q.text,
                        format = "MCQ_SINGLE",
                        subjectId = finalSubjectId,
                        topicId = finalTopicId,
                        subTopicId = finalSubTopicId,
                        difficulty = q.difficulty,
                        sourceTimestampSec = q.sourceTimestampSec,
                        sourceUrl = q.sourceUrl,
                        language = q.language,
                        hasSolution = q.hasSolution,
                        createdAt = now,
                        updatedAt = now,
                    )
                    val questionId = questionDao.insert(questionEntity)

                    // Options
                    val optionEntities = q.options.map { opt ->
                        QuestionOptionEntity(
                            questionId = questionId,
                            position = opt.position,
                            text = opt.text,
                            isCorrect = opt.isCorrect,
                        )
                    }
                    questionOptionDao.insertAll(optionEntities)

                    // Hints
                    if (q.hints.isNotEmpty()) {
                        val hintEntities = q.hints.map { h ->
                            HintEntity(
                                questionId = questionId,
                                number = h.number,
                                title = h.title,
                                description = h.description,
                            )
                        }
                        hintDao.insertAll(hintEntities)
                    }

                    // Tricks
                    if (q.tricks.isNotEmpty()) {
                        val trickEntities = q.tricks.map { t ->
                            TrickEntity(
                                questionId = questionId,
                                name = t.name,
                                description = t.description,
                            )
                        }
                        trickDao.insertAll(trickEntities)
                    }

                    // Solution Steps
                    if (q.solutionSteps.isNotEmpty()) {
                        val stepEntities = q.solutionSteps.map { s ->
                            SolutionStepEntity(
                                questionId = questionId,
                                number = s.number,
                                action = s.action,
                            )
                        }
                        solutionStepDao.insertAll(stepEntities)
                    }

                    // TestQuestion junction
                    testQuestionDao.insert(
                        TestQuestionEntity(
                            testId = testId,
                            questionId = questionId,
                            position = position,
                        )
                    )
                }

                ImportResult(
                    sourceId = sourceId,
                    testId = testId,
                    questionsImported = plan.validQuestions.size,
                )
            }
            Outcome.Success(result)
        } catch (e: Exception) {
            Outcome.Error(AppError.Database(cause = e))
        }
    }
}
