package app.apti.villa.domain.importer

import java.security.MessageDigest

object ContentNormalizer {

    fun collapseWhitespace(text: String): String {
        return text.trim().replace(Regex("""\s+"""), " ")
    }

    /**
     * Parses timestamp string formatted as HH:MM:SS or MM:SS to seconds.
     * Returns null if input is null or blank.
     * Returns Outcome/Result with seconds, or throws IllegalArgumentException if invalid.
     */
    fun parseTimestamp(timestamp: String?): Int? {
        if (timestamp.isNullOrBlank()) return null
        val trimmed = timestamp.trim()
        val parts = trimmed.split(":")

        when (parts.size) {
            2 -> {
                val min = parts[0].toIntOrNull() ?: throw IllegalArgumentException("Invalid minutes in timestamp '$trimmed'")
                val sec = parts[1].toIntOrNull() ?: throw IllegalArgumentException("Invalid seconds in timestamp '$trimmed'")
                if (min < 0 || sec !in 0..59) {
                    throw IllegalArgumentException("Timestamp seconds or minutes out of range in '$trimmed'")
                }
                return min * 60 + sec
            }
            3 -> {
                val hr = parts[0].toIntOrNull() ?: throw IllegalArgumentException("Invalid hours in timestamp '$trimmed'")
                val min = parts[1].toIntOrNull() ?: throw IllegalArgumentException("Invalid minutes in timestamp '$trimmed'")
                val sec = parts[2].toIntOrNull() ?: throw IllegalArgumentException("Invalid seconds in timestamp '$trimmed'")
                if (hr < 0 || min !in 0..59 || sec !in 0..59) {
                    throw IllegalArgumentException("Timestamp values out of range in '$trimmed'")
                }
                return hr * 3600 + min * 60 + sec
            }
            else -> throw IllegalArgumentException("Timestamp must be MM:SS or HH:MM:SS, got '$trimmed'")
        }
    }

    fun normalizeDifficulty(diff: String?): String? {
        if (diff.isNullOrBlank()) return null
        return when (diff.trim().uppercase()) {
            "EASY" -> "EASY"
            "MED", "MEDIUM" -> "MEDIUM"
            "HARD" -> "HARD"
            else -> null
        }
    }

    /**
     * Computes SHA-256 hash of normalized question text + sorted normalized option texts.
     */
    fun computeContentHash(normalizedQuestionText: String, normalizedOptionTexts: List<String>): String {
        val sortedOptions = normalizedOptionTexts.sorted().joinToString("|")
        val input = "$normalizedQuestionText::$sortedOptions"
        val md = MessageDigest.getInstance("SHA-256")
        val digest = md.digest(input.toByteArray(Charsets.UTF_8))
        return digest.joinToString("") { "%02x".format(it) }
    }
}
