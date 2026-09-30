package app.apti.villa.domain.importer

import kotlinx.serialization.json.JsonArray
import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.JsonPrimitive
import kotlinx.serialization.json.booleanOrNull
import kotlinx.serialization.json.intOrNull

class ContentValidator {

    fun validate(root: JsonObject): ImportPlan {
        val issues = mutableListOf<ImportIssue>()

        // 1. Validate schema_version
        val schemaVersionElement = root["schema_version"]
        if (schemaVersionElement == null) {
            issues.add(
                ImportIssue(
                    path = "$.schema_version",
                    code = IssueCodes.E_MISSING_FIELD,
                    severity = IssueSeverity.ERROR,
                    message = "Missing required root field 'schema_version'.",
                )
            )
        } else {
            val version = (schemaVersionElement as? JsonPrimitive)?.intOrNull
            if (version == null) {
                issues.add(
                    ImportIssue(
                        path = "$.schema_version",
                        code = IssueCodes.E_WRONG_TYPE,
                        severity = IssueSeverity.ERROR,
                        message = "Field 'schema_version' must be an integer, got $schemaVersionElement.",
                    )
                )
            } else if (version != 1) {
                issues.add(
                    ImportIssue(
                        path = "$.schema_version",
                        code = IssueCodes.E_SCHEMA_VERSION,
                        severity = IssueSeverity.ERROR,
                        message = "schema_version $version is not supported. Only version 1 is supported.",
                    )
                )
            }
        }

        // Check unknown fields at root
        val knownRootFields = setOf("schema_version", "source_metadata", "questions")
        root.keys.filter { it !in knownRootFields }.forEach { extra ->
            issues.add(
                ImportIssue(
                    path = "$.$extra",
                    code = IssueCodes.W_UNKNOWN_FIELD,
                    severity = IssueSeverity.WARNING,
                    message = "Unknown field '$extra' at root will be ignored.",
                )
            )
        }

        // 2. Validate source_metadata
        val sourceMetaElement = root["source_metadata"]
        var rawSourceMetadata = RawSourceMetadata(title = "Untitled", topic = "General")
        if (sourceMetaElement == null) {
            issues.add(
                ImportIssue(
                    path = "$.source_metadata",
                    code = IssueCodes.E_MISSING_FIELD,
                    severity = IssueSeverity.ERROR,
                    message = "Missing required root field 'source_metadata'.",
                )
            )
        } else if (sourceMetaElement !is JsonObject) {
            issues.add(
                ImportIssue(
                    path = "$.source_metadata",
                    code = IssueCodes.E_WRONG_TYPE,
                    severity = IssueSeverity.ERROR,
                    message = "'source_metadata' must be an object.",
                )
            )
        } else {
            val knownSourceFields = setOf(
                "title", "topic", "sub_topic", "subject", "exam",
                "source_type", "source_url", "channel", "language"
            )
            sourceMetaElement.keys.filter { it !in knownSourceFields }.forEach { extra ->
                issues.add(
                    ImportIssue(
                        path = "$.source_metadata.$extra",
                        code = IssueCodes.W_UNKNOWN_FIELD,
                        severity = IssueSeverity.WARNING,
                        message = "source_metadata: Unknown field '$extra' will be ignored.",
                    )
                )
            }

            val title = sourceMetaElement["title"]?.asStringOrNull()?.trim()
            if (title.isNullOrEmpty()) {
                issues.add(
                    ImportIssue(
                        path = "$.source_metadata.title",
                        code = if (sourceMetaElement["title"] == null) IssueCodes.E_MISSING_FIELD else IssueCodes.E_EMPTY_QUESTION_TEXT,
                        severity = IssueSeverity.ERROR,
                        message = "source_metadata: 'title' is required and cannot be empty.",
                    )
                )
            }

            val topic = sourceMetaElement["topic"]?.asStringOrNull()?.trim()
            if (topic.isNullOrEmpty()) {
                issues.add(
                    ImportIssue(
                        path = "$.source_metadata.topic",
                        code = if (sourceMetaElement["topic"] == null) IssueCodes.E_MISSING_FIELD else IssueCodes.E_EMPTY_QUESTION_TEXT,
                        severity = IssueSeverity.ERROR,
                        message = "source_metadata: 'topic' is required and cannot be empty.",
                    )
                )
            }

            val sourceUrl = sourceMetaElement["source_url"]?.asStringOrNull()?.trim()
            if (sourceUrl != null && !isValidUrl(sourceUrl)) {
                issues.add(
                    ImportIssue(
                        path = "$.source_metadata.source_url",
                        code = IssueCodes.E_BAD_URL,
                        severity = IssueSeverity.ERROR,
                        message = "source_metadata.source_url: '$sourceUrl' is not a valid URL.",
                    )
                )
            }

            rawSourceMetadata = RawSourceMetadata(
                title = title ?: "Untitled",
                topic = topic ?: "General",
                subTopic = sourceMetaElement["sub_topic"]?.asStringOrNull()?.trim(),
                subject = sourceMetaElement["subject"]?.asStringOrNull()?.trim(),
                exam = sourceMetaElement["exam"]?.asStringOrNull()?.trim(),
                sourceType = sourceMetaElement["source_type"]?.asStringOrNull()?.trim() ?: "JSON",
                sourceUrl = sourceUrl,
                channel = sourceMetaElement["channel"]?.asStringOrNull()?.trim(),
                language = sourceMetaElement["language"]?.asStringOrNull()?.trim(),
            )
        }

        // 3. Validate questions array
        val questionsElement = root["questions"]
        val validQuestions = mutableListOf<NormalizedQuestion>()
        val skippedQuestions = mutableListOf<SkippedQuestion>()
        val seenQuestionIds = mutableMapOf<String, Int>()

        if (questionsElement == null) {
            issues.add(
                ImportIssue(
                    path = "$.questions",
                    code = IssueCodes.E_MISSING_FIELD,
                    severity = IssueSeverity.ERROR,
                    message = "Missing required root field 'questions'.",
                )
            )
        } else if (questionsElement !is JsonArray) {
            issues.add(
                ImportIssue(
                    path = "$.questions",
                    code = IssueCodes.E_WRONG_TYPE,
                    severity = IssueSeverity.ERROR,
                    message = "'questions' must be an array of question objects.",
                )
            )
        } else if (questionsElement.isEmpty()) {
            issues.add(
                ImportIssue(
                    path = "$.questions",
                    code = IssueCodes.E_OPTIONS_COUNT,
                    severity = IssueSeverity.ERROR,
                    message = "'questions' array cannot be empty.",
                )
            )
        } else {
            questionsElement.forEachIndexed { qIndex, qElem ->
                val qIssues = mutableListOf<ImportIssue>()
                if (qElem !is JsonObject) {
                    val err = ImportIssue(
                        path = "$.questions[$qIndex]",
                        code = IssueCodes.E_WRONG_TYPE,
                        severity = IssueSeverity.ERROR,
                        questionIndex = qIndex,
                        message = "Question #${qIndex + 1} must be an object.",
                    )
                    issues.add(err)
                    skippedQuestions.add(
                        SkippedQuestion(index = qIndex, id = null, reason = err.message, rawJson = qElem.toString())
                    )
                    return@forEachIndexed
                }

                val knownQFields = setOf(
                    "id", "question_text", "options", "correct_answer", "timestamp",
                    "category", "difficulty", "hint_count", "hints", "tricks",
                    "solution_steps", "subject", "exam"
                )
                qElem.keys.filter { it !in knownQFields }.forEach { extra ->
                    qIssues.add(
                        ImportIssue(
                            path = "$.questions[$qIndex].$extra",
                            code = IssueCodes.W_UNKNOWN_FIELD,
                            severity = IssueSeverity.WARNING,
                            questionIndex = qIndex,
                            message = "Question #${qIndex + 1}: Unknown field '$extra' will be ignored.",
                        )
                    )
                }

                // ID
                val rawIdElem = qElem["id"]
                val questionId = when {
                    rawIdElem is JsonPrimitive -> rawIdElem.content.trim()
                    else -> null
                }
                if (questionId.isNullOrEmpty()) {
                    qIssues.add(
                        ImportIssue(
                            path = "$.questions[$qIndex].id",
                            code = if (rawIdElem == null) IssueCodes.E_MISSING_FIELD else IssueCodes.E_EMPTY_QUESTION_TEXT,
                            severity = IssueSeverity.ERROR,
                            questionIndex = qIndex,
                            message = "Question #${qIndex + 1}: Missing or empty required field 'id'.",
                        )
                    )
                } else {
                    if (seenQuestionIds.containsKey(questionId)) {
                        val firstPos = seenQuestionIds[questionId]!! + 1
                        qIssues.add(
                            ImportIssue(
                                path = "$.questions[$qIndex].id",
                                code = IssueCodes.E_DUP_QUESTION_ID,
                                severity = IssueSeverity.ERROR,
                                questionIndex = qIndex,
                                questionId = questionId,
                                message = "Duplicate question id '$questionId' found at positions $firstPos and ${qIndex + 1}.",
                            )
                        )
                    } else {
                        seenQuestionIds[questionId] = qIndex
                    }
                }

                // Question text
                val questionText = qElem["question_text"]?.asStringOrNull()?.let { ContentNormalizer.collapseWhitespace(it) }
                if (questionText.isNullOrBlank()) {
                    qIssues.add(
                        ImportIssue(
                            path = "$.questions[$qIndex].question_text",
                            code = if (qElem["question_text"] == null) IssueCodes.E_MISSING_FIELD else IssueCodes.E_EMPTY_QUESTION_TEXT,
                            severity = IssueSeverity.ERROR,
                            questionIndex = qIndex,
                            questionId = questionId,
                            message = "Question #${qIndex + 1}: 'question_text' is empty or missing.",
                        )
                    )
                } else if (hasUnsupportedStructure(questionText)) {
                    qIssues.add(
                        ImportIssue(
                            path = "$.questions[$qIndex].question_text",
                            code = IssueCodes.E_UNSUPPORTED_STRUCTURE,
                            severity = IssueSeverity.ERROR,
                            questionIndex = qIndex,
                            questionId = questionId,
                            message = "Question #${qIndex + 1}: Contains LaTeX expressions or unsupported formatting.",
                        )
                    )
                }

                // Options
                val rawOptionsElem = qElem["options"]
                val rawOptions = mutableListOf<String>()
                if (rawOptionsElem == null) {
                    qIssues.add(
                        ImportIssue(
                            path = "$.questions[$qIndex].options",
                            code = IssueCodes.E_MISSING_FIELD,
                            severity = IssueSeverity.ERROR,
                            questionIndex = qIndex,
                            questionId = questionId,
                            message = "Question #${qIndex + 1}: Missing required field 'options'.",
                        )
                    )
                } else if (rawOptionsElem !is JsonArray) {
                    qIssues.add(
                        ImportIssue(
                            path = "$.questions[$qIndex].options",
                            code = IssueCodes.E_WRONG_TYPE,
                            severity = IssueSeverity.ERROR,
                            questionIndex = qIndex,
                            questionId = questionId,
                            message = "Question #${qIndex + 1}: 'options' must be an array.",
                        )
                    )
                } else {
                    if (rawOptionsElem.size !in 2..6) {
                        qIssues.add(
                            ImportIssue(
                                path = "$.questions[$qIndex].options",
                                code = IssueCodes.E_OPTIONS_COUNT,
                                severity = IssueSeverity.ERROR,
                                questionIndex = qIndex,
                                questionId = questionId,
                                message = "Question #${qIndex + 1}: Expected 2–6 options, got ${rawOptionsElem.size}.",
                            )
                        )
                    }

                    val normalizedOptionTexts = mutableListOf<String>()
                    val seenNormalizedOptions = mutableSetOf<String>()

                    rawOptionsElem.forEachIndexed { optIdx, optElem ->
                        val text = (optElem as? JsonPrimitive)?.content?.trim()
                        if (text.isNullOrEmpty()) {
                            qIssues.add(
                                ImportIssue(
                                    path = "$.questions[$qIndex].options[$optIdx]",
                                    code = IssueCodes.E_EMPTY_OPTION,
                                    severity = IssueSeverity.ERROR,
                                    questionIndex = qIndex,
                                    questionId = questionId,
                                    message = "Question #${qIndex + 1}: Option at position ${optIdx + 1} is empty.",
                                )
                            )
                        } else {
                            val collapsed = ContentNormalizer.collapseWhitespace(text)
                            if (seenNormalizedOptions.contains(collapsed)) {
                                qIssues.add(
                                    ImportIssue(
                                        path = "$.questions[$qIndex].options[$optIdx]",
                                        code = IssueCodes.E_DUP_OPTION,
                                        severity = IssueSeverity.ERROR,
                                        questionIndex = qIndex,
                                        questionId = questionId,
                                        message = "Question #${qIndex + 1}: Duplicate option '$collapsed' at position ${optIdx + 1}.",
                                    )
                                )
                            } else {
                                seenNormalizedOptions.add(collapsed)
                            }
                            normalizedOptionTexts.add(collapsed)
                            rawOptions.add(text)
                        }
                    }
                }

                // Correct answer
                val rawAnswer = qElem["correct_answer"]?.asStringOrNull()?.trim()
                var resolvedOptionIndex: Int? = null
                if (rawAnswer.isNullOrEmpty()) {
                    qIssues.add(
                        ImportIssue(
                            path = "$.questions[$qIndex].correct_answer",
                            code = if (qElem["correct_answer"] == null) IssueCodes.E_MISSING_FIELD else IssueCodes.E_EMPTY_QUESTION_TEXT,
                            severity = IssueSeverity.ERROR,
                            questionIndex = qIndex,
                            questionId = questionId,
                            message = "Question #${qIndex + 1}: 'correct_answer' is empty or missing.",
                        )
                    )
                } else if (rawOptions.size in 2..6) {
                    when (val res = AnswerResolver.resolve(rawAnswer, rawOptions, qIndex, questionId)) {
                        is AnswerResolutionResult.Success -> {
                            resolvedOptionIndex = res.optionIndex
                            if (res.warning != null) {
                                qIssues.add(res.warning)
                            }
                        }
                        is AnswerResolutionResult.Ambiguous -> qIssues.add(res.issue)
                        is AnswerResolutionResult.NoMatch -> qIssues.add(res.issue)
                    }
                }

                // Timestamp
                val rawTimestamp = qElem["timestamp"]?.asStringOrNull()?.trim()
                var parsedTimestampSec: Int? = null
                if (!rawTimestamp.isNullOrEmpty()) {
                    try {
                        parsedTimestampSec = ContentNormalizer.parseTimestamp(rawTimestamp)
                    } catch (e: IllegalArgumentException) {
                        qIssues.add(
                            ImportIssue(
                                path = "$.questions[$qIndex].timestamp",
                                code = IssueCodes.E_BAD_TIMESTAMP,
                                severity = IssueSeverity.ERROR,
                                questionIndex = qIndex,
                                questionId = questionId,
                                message = "Question #${qIndex + 1}: timestamp '$rawTimestamp' is invalid: ${e.message}",
                            )
                        )
                    }
                }

                // Hints
                val hintsElem = qElem["hints"]
                val normalizedHints = mutableListOf<NormalizedHint>()
                if (hintsElem == null || (hintsElem is JsonArray && hintsElem.isEmpty())) {
                    qIssues.add(
                        ImportIssue(
                            path = "$.questions[$qIndex].hints",
                            code = IssueCodes.W_NO_HINTS,
                            severity = IssueSeverity.WARNING,
                            questionIndex = qIndex,
                            questionId = questionId,
                            message = "Question #${qIndex + 1}: No hints provided.",
                        )
                    )
                } else if (hintsElem is JsonArray) {
                    val rawHintCount = (qElem["hint_count"] as? JsonPrimitive)?.intOrNull
                    if (rawHintCount != null && rawHintCount != hintsElem.size) {
                        qIssues.add(
                            ImportIssue(
                                path = "$.questions[$qIndex].hint_count",
                                code = IssueCodes.E_HINT_COUNT_MISMATCH,
                                severity = IssueSeverity.ERROR,
                                questionIndex = qIndex,
                                questionId = questionId,
                                message = "Question #${qIndex + 1}: hint_count is $rawHintCount but ${hintsElem.size} hints provided.",
                            )
                        )
                    }

                    val hintNumbers = mutableListOf<Int>()
                    hintsElem.forEachIndexed { hIdx, hElem ->
                        if (hElem is JsonObject) {
                            val hNum = (hElem["hint_number"] as? JsonPrimitive)?.intOrNull
                            val hTitle = hElem["title"]?.asStringOrNull()?.trim()
                            val hDesc = hElem["description"]?.asStringOrNull()?.trim()

                            if (hNum != null) hintNumbers.add(hNum)
                            if (hNum == null || hTitle.isNullOrEmpty() || hDesc.isNullOrEmpty()) {
                                qIssues.add(
                                    ImportIssue(
                                        path = "$.questions[$qIndex].hints[$hIdx]",
                                        code = IssueCodes.E_MISSING_FIELD,
                                        severity = IssueSeverity.ERROR,
                                        questionIndex = qIndex,
                                        questionId = questionId,
                                        message = "Question #${qIndex + 1}, Hint #${hIdx + 1}: Missing required hint_number, title, or description.",
                                    )
                                )
                            } else {
                                normalizedHints.add(
                                    NormalizedHint(
                                        number = hNum,
                                        title = ContentNormalizer.collapseWhitespace(hTitle),
                                        description = ContentNormalizer.collapseWhitespace(hDesc),
                                    )
                                )
                            }
                        }
                    }

                    val expectedHintNumbers = (1..hintsElem.size).toList()
                    if (hintNumbers.sorted() != expectedHintNumbers) {
                        qIssues.add(
                            ImportIssue(
                                path = "$.questions[$qIndex].hints",
                                code = IssueCodes.E_HINT_NUMBERING,
                                severity = IssueSeverity.ERROR,
                                questionIndex = qIndex,
                                questionId = questionId,
                                message = "Question #${qIndex + 1}: Hint numbers are $hintNumbers but expected $expectedHintNumbers.",
                            )
                        )
                    }
                }

                // Tricks
                val tricksElem = qElem["tricks"]
                val normalizedTricks = mutableListOf<NormalizedTrick>()
                if (tricksElem is JsonArray) {
                    tricksElem.forEachIndexed { tIdx, tElem ->
                        if (tElem is JsonObject) {
                            val tName = tElem["trick_name"]?.asStringOrNull()?.trim()
                            val tDesc = tElem["description"]?.asStringOrNull()?.trim()
                            if (tName.isNullOrEmpty() || tDesc.isNullOrEmpty()) {
                                qIssues.add(
                                    ImportIssue(
                                        path = "$.questions[$qIndex].tricks[$tIdx]",
                                        code = IssueCodes.E_MISSING_FIELD,
                                        severity = IssueSeverity.ERROR,
                                        questionIndex = qIndex,
                                        questionId = questionId,
                                        message = "Question #${qIndex + 1}, Trick #${tIdx + 1}: Missing trick_name or description.",
                                    )
                                )
                            } else {
                                normalizedTricks.add(
                                    NormalizedTrick(
                                        name = ContentNormalizer.collapseWhitespace(tName),
                                        description = ContentNormalizer.collapseWhitespace(tDesc),
                                    )
                                )
                            }
                        }
                    }
                }

                // Solution steps
                val stepsElem = qElem["solution_steps"]
                val normalizedSteps = mutableListOf<NormalizedSolutionStep>()
                if (stepsElem == null || (stepsElem is JsonArray && stepsElem.isEmpty())) {
                    qIssues.add(
                        ImportIssue(
                            path = "$.questions[$qIndex].solution_steps",
                            code = IssueCodes.W_NO_SOLUTION,
                            severity = IssueSeverity.WARNING,
                            questionIndex = qIndex,
                            questionId = questionId,
                            message = "Question #${qIndex + 1}: No solution steps provided.",
                        )
                    )
                } else if (stepsElem is JsonArray) {
                    val stepNumbers = mutableListOf<Int>()
                    stepsElem.forEachIndexed { sIdx, sElem ->
                        if (sElem is JsonObject) {
                            val sNum = (sElem["step_number"] as? JsonPrimitive)?.intOrNull
                            val sAction = sElem["action"]?.asStringOrNull()?.trim()
                            if (sNum != null) stepNumbers.add(sNum)
                            if (sNum == null || sAction.isNullOrEmpty()) {
                                qIssues.add(
                                    ImportIssue(
                                        path = "$.questions[$qIndex].solution_steps[$sIdx]",
                                        code = IssueCodes.E_MISSING_FIELD,
                                        severity = IssueSeverity.ERROR,
                                        questionIndex = qIndex,
                                        questionId = questionId,
                                        message = "Question #${qIndex + 1}, Step #${sIdx + 1}: Missing step_number or action.",
                                    )
                                )
                            } else {
                                normalizedSteps.add(
                                    NormalizedSolutionStep(
                                        number = sNum,
                                        action = ContentNormalizer.collapseWhitespace(sAction),
                                    )
                                )
                            }
                        }
                    }

                    val expectedStepNumbers = (1..stepsElem.size).toList()
                    if (stepNumbers.sorted() != expectedStepNumbers) {
                        qIssues.add(
                            ImportIssue(
                                path = "$.questions[$qIndex].solution_steps",
                                code = IssueCodes.E_STEP_NUMBERING,
                                severity = IssueSeverity.ERROR,
                                questionIndex = qIndex,
                                questionId = questionId,
                                message = "Question #${qIndex + 1}: Solution step numbers are $stepNumbers but expected $expectedStepNumbers.",
                            )
                        )
                    }
                }

                // Check question error status
                val hasQuestionErrors = qIssues.any { it.severity == IssueSeverity.ERROR }
                issues.addAll(qIssues)

                if (hasQuestionErrors || questionId == null || questionText == null || resolvedOptionIndex == null) {
                    val primaryError = qIssues.firstOrNull { it.severity == IssueSeverity.ERROR }?.message ?: "Validation error"
                    skippedQuestions.add(
                        SkippedQuestion(
                            index = qIndex,
                            id = questionId,
                            reason = primaryError,
                            rawJson = qElem.toString(),
                        )
                    )
                } else {
                    val normalizedOptions = rawOptions.mapIndexed { idx, optText ->
                        NormalizedOption(
                            position = idx,
                            text = ContentNormalizer.collapseWhitespace(optText),
                            isCorrect = (idx == resolvedOptionIndex),
                        )
                    }
                    val contentHash = ContentNormalizer.computeContentHash(
                        questionText,
                        normalizedOptions.map { it.text },
                    )

                    validQuestions.add(
                        NormalizedQuestion(
                            externalId = questionId,
                            text = questionText,
                            options = normalizedOptions,
                            contentHash = contentHash,
                            difficulty = ContentNormalizer.normalizeDifficulty(qElem["difficulty"]?.asStringOrNull()),
                            sourceTimestampSec = parsedTimestampSec,
                            sourceUrl = rawSourceMetadata.sourceUrl,
                            language = rawSourceMetadata.language,
                            subTopic = qElem["category"]?.asStringOrNull()?.trim() ?: rawSourceMetadata.subTopic,
                            subject = qElem["subject"]?.asStringOrNull()?.trim() ?: rawSourceMetadata.subject,
                            exam = qElem["exam"]?.asStringOrNull()?.trim() ?: rawSourceMetadata.exam,
                            hints = normalizedHints,
                            tricks = normalizedTricks,
                            solutionSteps = normalizedSteps,
                            hasSolution = normalizedSteps.isNotEmpty(),
                        )
                    )
                }
            }
        }

        val errorCount = issues.count { it.severity == IssueSeverity.ERROR }
        val warningCount = issues.count { it.severity == IssueSeverity.WARNING }
        val totalQuestions = (questionsElement as? JsonArray)?.size ?: 0

        return ImportPlan(
            sourceMetadata = rawSourceMetadata,
            validQuestions = validQuestions,
            skippedQuestions = skippedQuestions,
            issues = issues,
            stats = ImportStats(
                totalQuestionsInFile = totalQuestions,
                validQuestionsCount = validQuestions.size,
                skippedQuestionsCount = skippedQuestions.size,
                errorCount = errorCount,
                warningCount = warningCount,
            ),
        )
    }

    private fun JsonElement.asStringOrNull(): String? {
        return (this as? JsonPrimitive)?.content
    }

    private fun isValidUrl(url: String): Boolean {
        return url.startsWith("http://", ignoreCase = true) || url.startsWith("https://", ignoreCase = true)
    }

    private fun hasUnsupportedStructure(text: String): Boolean {
        return text.contains("\\frac") || text.contains("\\sqrt") || text.contains("$$")
    }
}
