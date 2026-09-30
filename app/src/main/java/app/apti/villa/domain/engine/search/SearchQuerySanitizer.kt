package app.apti.villa.domain.engine.search

/**
 * Pure Kotlin search query sanitizer for SQLite FTS4/FTS5 MATCH expressions.
 * Ensures queries with special characters, punctuation, and Unicode/Devanagari
 * do not cause syntax errors and properly support prefix search.
 * ZERO Android or Room dependencies.
 */
object SearchQuerySanitizer {

    // Characters that have special meaning in FTS syntax
    private val FTS_SPECIAL_CHARS = Regex("[*\"'():^+\\-_\\[\\]{}!~?><=;,/\\\\]")

    /**
     * Sanitizes a raw user input query into a safe FTS MATCH string.
     * E.g.: "percentage 2024" -> "percentage* 2024*"
     * E.g.: "प्रतिशत" -> "प्रतिशत*"
     * E.g.: "test: hello (world)" -> "test* hello* world*"
     */
    fun sanitizeForFts(rawQuery: String): String {
        if (rawQuery.isBlank()) return ""

        // Replace special characters with spaces
        val cleaned = rawQuery.replace(FTS_SPECIAL_CHARS, " ")

        // Split by whitespace
        val tokens = cleaned
            .trim()
            .split(Regex("\\s+"))
            .filter { it.isNotBlank() }

        if (tokens.isEmpty()) return ""

        // Append * wildcard to each token for prefix matching
        return tokens.joinToString(" ") { "$it*" }
    }

    /**
     * Determines whether a query contains any searchable alphanumeric or unicode letters.
     */
    fun isValidSearchQuery(rawQuery: String): Boolean {
        return sanitizeForFts(rawQuery).isNotBlank()
    }
}
