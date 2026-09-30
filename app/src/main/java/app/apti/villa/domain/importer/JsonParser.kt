package app.apti.villa.domain.importer

import app.apti.villa.core.common.AppError
import app.apti.villa.core.common.Outcome
import kotlinx.serialization.SerializationException
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.JsonObject

class JsonParser(
    private val json: Json = Json {
        ignoreUnknownKeys = false
        isLenient = false
        allowSpecialFloatingPointValues = false
    },
) {
    fun parse(jsonString: String): Outcome<JsonObject> {
        return try {
            val element: JsonElement = json.parseToJsonElement(jsonString)
            if (element is JsonObject) {
                Outcome.Success(element)
            } else {
                Outcome.Error(
                    AppError.Import(
                        listOf(
                            ImportIssue(
                                path = "$",
                                code = IssueCodes.E_WRONG_TYPE,
                                severity = IssueSeverity.ERROR,
                                message = "Root element must be a JSON object, got ${element::class.simpleName}.",
                            )
                        )
                    )
                )
            }
        } catch (e: SerializationException) {
            val (line, col) = extractLineCol(e.message ?: "", jsonString)
            Outcome.Error(
                AppError.Import(
                    listOf(
                        ImportIssue(
                            path = "$",
                            code = IssueCodes.E_JSON_SYNTAX,
                            severity = IssueSeverity.ERROR,
                            message = "JSON syntax error at line $line, column $col: ${e.message ?: "Invalid JSON"}",
                        )
                    )
                )
            )
        } catch (e: Exception) {
            Outcome.Error(
                AppError.Import(
                    listOf(
                        ImportIssue(
                            path = "$",
                            code = IssueCodes.E_JSON_SYNTAX,
                            severity = IssueSeverity.ERROR,
                            message = "Failed to parse JSON: ${e.message ?: "Unknown error"}",
                        )
                    )
                )
            )
        }
    }

    private fun extractLineCol(errorMessage: String, fullText: String): Pair<Int, Int> {
        val offsetRegex = Regex("""(?:at path|offset)\s+(\d+)""", RegexOption.IGNORE_CASE)
        val match = offsetRegex.find(errorMessage)
        if (match != null) {
            val offset = match.groupValues[1].toIntOrNull()
            if (offset != null && offset in fullText.indices) {
                val lines = fullText.substring(0, offset).split('\n')
                val line = lines.size
                val col = lines.last().length + 1
                return Pair(line, col)
            }
        }
        return Pair(1, 1)
    }
}
