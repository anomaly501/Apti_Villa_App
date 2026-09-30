package app.apti.villa.domain.importer

sealed interface AnswerResolutionResult {
    data class Success(val optionIndex: Int, val warning: ImportIssue? = null) : AnswerResolutionResult
    data class Ambiguous(val matchingIndices: List<Int>, val issue: ImportIssue) : AnswerResolutionResult
    data class NoMatch(val issue: ImportIssue) : AnswerResolutionResult
}

object AnswerResolver {

    /**
     * Resolves the correct_answer string against the list of raw options.
     * Follows the 6-step fallback specified in IMPORT_SPEC.md §3.3.
     */
    fun resolve(
        correctAnswer: String,
        options: List<String>,
        questionIndex: Int,
        questionId: String?,
    ): AnswerResolutionResult {
        // Step 1: Exact match
        val exactMatches = options.indices.filter { options[it] == correctAnswer }
        if (exactMatches.size == 1) {
            return AnswerResolutionResult.Success(exactMatches.first())
        }
        if (exactMatches.size > 1) {
            return AnswerResolutionResult.Ambiguous(
                exactMatches,
                ImportIssue(
                    path = "$.questions[$questionIndex].correct_answer",
                    code = IssueCodes.E_ANSWER_AMBIGUOUS,
                    severity = IssueSeverity.ERROR,
                    questionIndex = questionIndex,
                    questionId = questionId,
                    message = "Question #${questionIndex + 1}: correct_answer '$correctAnswer' matches options at positions ${exactMatches.map { it + 1 }}.",
                )
            )
        }

        // Step 2: Trimmed and collapsed whitespace match
        val normalizedAnswer = collapseWhitespace(correctAnswer.trim())
        val trimmedOptions = options.map { collapseWhitespace(it.trim()) }
        val trimmedMatches = trimmedOptions.indices.filter { trimmedOptions[it] == normalizedAnswer }
        if (trimmedMatches.size == 1) {
            return AnswerResolutionResult.Success(trimmedMatches.first())
        }
        if (trimmedMatches.size > 1) {
            return AnswerResolutionResult.Ambiguous(
                trimmedMatches,
                ImportIssue(
                    path = "$.questions[$questionIndex].correct_answer",
                    code = IssueCodes.E_ANSWER_AMBIGUOUS,
                    severity = IssueSeverity.ERROR,
                    questionIndex = questionIndex,
                    questionId = questionId,
                    message = "Question #${questionIndex + 1}: correct_answer '$correctAnswer' matches options at positions ${trimmedMatches.map { it + 1 }} after whitespace trimming.",
                )
            )
        }

        // Step 3: Case-insensitive match
        val lowerAnswer = normalizedAnswer.lowercase()
        val lowerMatches = trimmedOptions.indices.filter { trimmedOptions[it].lowercase() == lowerAnswer }
        if (lowerMatches.size == 1) {
            return AnswerResolutionResult.Success(lowerMatches.first())
        }
        if (lowerMatches.size > 1) {
            return AnswerResolutionResult.Ambiguous(
                lowerMatches,
                ImportIssue(
                    path = "$.questions[$questionIndex].correct_answer",
                    code = IssueCodes.E_ANSWER_AMBIGUOUS,
                    severity = IssueSeverity.ERROR,
                    questionIndex = questionIndex,
                    questionId = questionId,
                    message = "Question #${questionIndex + 1}: correct_answer '$correctAnswer' matches options at positions ${lowerMatches.map { it + 1 }} case-insensitively.",
                )
            )
        }

        // Step 4: Letter / number mapping (A-E or 1-5)
        val letterMappedIndex = when (normalizedAnswer.uppercase()) {
            "A", "1" -> 0
            "B", "2" -> 1
            "C", "3" -> 2
            "D", "4" -> 3
            "E", "5" -> 4
            "F", "6" -> 5
            else -> null
        }

        if (letterMappedIndex != null && letterMappedIndex < options.size) {
            val matchedOptionText = options[letterMappedIndex]
            val warning = ImportIssue(
                path = "$.questions[$questionIndex].correct_answer",
                code = IssueCodes.W_ANSWER_LETTER_MAPPED,
                severity = IssueSeverity.WARNING,
                questionIndex = questionIndex,
                questionId = questionId,
                message = "Question #${questionIndex + 1}: correct_answer '$correctAnswer' mapped to option at position ${letterMappedIndex + 1} ('$matchedOptionText').",
            )
            return AnswerResolutionResult.Success(letterMappedIndex, warning)
        }

        // Step 6: No match
        return AnswerResolutionResult.NoMatch(
            ImportIssue(
                path = "$.questions[$questionIndex].correct_answer",
                code = IssueCodes.E_ANSWER_NO_MATCH,
                severity = IssueSeverity.ERROR,
                questionIndex = questionIndex,
                questionId = questionId,
                message = "Question #${questionIndex + 1}: correct_answer '$correctAnswer' does not match any option.",
            )
        )
    }

    fun collapseWhitespace(text: String): String {
        return text.replace(Regex("""\s+"""), " ")
    }
}
