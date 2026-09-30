package app.apti.villa.domain.importer

enum class IssueSeverity {
    ERROR,
    WARNING,
}

object IssueCodes {
    // Errors (block import unless user chooses "Import valid only")
    const val E_FILE_TOO_LARGE = "E_FILE_TOO_LARGE"
    const val E_JSON_SYNTAX = "E_JSON_SYNTAX"
    const val E_MISSING_FIELD = "E_MISSING_FIELD"
    const val E_WRONG_TYPE = "E_WRONG_TYPE"
    const val E_DUP_QUESTION_ID = "E_DUP_QUESTION_ID"
    const val E_EMPTY_QUESTION_TEXT = "E_EMPTY_QUESTION_TEXT"
    const val E_OPTIONS_COUNT = "E_OPTIONS_COUNT"
    const val E_DUP_OPTION = "E_DUP_OPTION"
    const val E_EMPTY_OPTION = "E_EMPTY_OPTION"
    const val E_ANSWER_NO_MATCH = "E_ANSWER_NO_MATCH"
    const val E_ANSWER_AMBIGUOUS = "E_ANSWER_AMBIGUOUS"
    const val E_HINT_NUMBERING = "E_HINT_NUMBERING"
    const val E_HINT_COUNT_MISMATCH = "E_HINT_COUNT_MISMATCH"
    const val E_STEP_NUMBERING = "E_STEP_NUMBERING"
    const val E_BAD_URL = "E_BAD_URL"
    const val E_BAD_TIMESTAMP = "E_BAD_TIMESTAMP"
    const val E_UNSUPPORTED_STRUCTURE = "E_UNSUPPORTED_STRUCTURE"
    const val E_SCHEMA_VERSION = "E_SCHEMA_VERSION"

    // Warnings (import proceeds, user informed)
    const val W_UNKNOWN_FIELD = "W_UNKNOWN_FIELD"
    const val W_ANSWER_LETTER_MAPPED = "W_ANSWER_LETTER_MAPPED"
    const val W_NO_HINTS = "W_NO_HINTS"
    const val W_NO_SOLUTION = "W_NO_SOLUTION"
    const val W_DUPLICATE_OF_EXISTING = "W_DUPLICATE_OF_EXISTING"
}
