package app.apti.villa.ai.provider

import app.apti.villa.domain.model.AIChatMessage
import app.apti.villa.domain.model.AIChatRequest
import app.apti.villa.domain.model.AIChatRole
import app.apti.villa.domain.model.AIChunk
import app.apti.villa.domain.model.AiProviderType
import app.apti.villa.domain.model.ProviderConfig
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONArray
import org.json.JSONObject
import java.io.BufferedReader
import java.io.InputStreamReader
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GeminiProvider @Inject constructor(
    private val okHttpClient: OkHttpClient,
) : AIProvider {

    override val providerType: AiProviderType = AiProviderType.GEMINI

    private val jsonMediaType = "application/json; charset=utf-8".toMediaType()

    private fun cleanBaseUrl(endpoint: String): String = endpoint.trim().removeSuffix("/")

    override suspend fun validateKey(config: ProviderConfig): Result<Unit> = runCatching {
        val base = cleanBaseUrl(config.endpointUrl)
        val url = "$base/v1beta/models/${config.modelName}:generateContent?key=${config.apiKey.trim()}"

        val payload = JSONObject().apply {
            put("contents", JSONArray().apply {
                put(JSONObject().apply {
                    put("role", "user")
                    put("parts", JSONArray().apply {
                        put(JSONObject().apply { put("text", "ping") })
                    })
                })
            })
            put("generationConfig", JSONObject().apply {
                put("maxOutputTokens", 1)
            })
        }

        val request = Request.Builder()
            .url(url)
            .post(payload.toString().toRequestBody(jsonMediaType))
            .build()

        okHttpClient.newCall(request).execute().use { response ->
            when (response.code) {
                in 200..299 -> Unit
                400 -> {
                    val body = response.body?.string() ?: ""
                    if (body.contains("API_KEY_INVALID", ignoreCase = true)) {
                        throw IllegalStateException("Invalid Gemini API key")
                    } else {
                        throw IllegalStateException("Gemini request error: $body")
                    }
                }
                401, 403 -> throw IllegalStateException("Invalid Gemini API key or unauthorized (HTTP ${response.code})")
                429 -> throw IllegalStateException("Gemini quota exceeded or rate limited (HTTP 429)")
                else -> throw IllegalStateException("Gemini provider error: HTTP ${response.code} ${response.message}")
            }
        }
    }

    override fun streamChat(request: AIChatRequest): Flow<AIChunk> = flow {
        val base = cleanBaseUrl(request.config.endpointUrl)
        val url = "$base/v1beta/models/${request.config.modelName}:streamGenerateContent?key=${request.config.apiKey.trim()}&alt=sse"

        var systemPromptText = ""
        val contentsArray = JSONArray()

        for (msg in request.messages) {
            when (msg.role) {
                AIChatRole.SYSTEM -> {
                    systemPromptText = msg.content
                }
                AIChatRole.USER -> {
                    contentsArray.put(JSONObject().apply {
                        put("role", "user")
                        put("parts", JSONArray().apply {
                            put(JSONObject().apply { put("text", msg.content) })
                        })
                    })
                }
                AIChatRole.ASSISTANT -> {
                    contentsArray.put(JSONObject().apply {
                        put("role", "model")
                        put("parts", JSONArray().apply {
                            put(JSONObject().apply { put("text", msg.content) })
                        })
                    })
                }
            }
        }

        val payload = JSONObject().apply {
            if (systemPromptText.isNotBlank()) {
                put("systemInstruction", JSONObject().apply {
                    put("parts", JSONArray().apply {
                        put(JSONObject().apply { put("text", systemPromptText) })
                    })
                })
            }
            put("contents", contentsArray)
            put("generationConfig", JSONObject().apply {
                put("temperature", request.temperature)
            })
        }

        val httpRequest = Request.Builder()
            .url(url)
            .header("Accept", "text/event-stream")
            .post(payload.toString().toRequestBody(jsonMediaType))
            .build()

        try {
            val response = okHttpClient.newCall(httpRequest).execute()
            if (!response.isSuccessful) {
                val errorBody = response.body?.string() ?: ""
                val errorMsg = when (response.code) {
                    400 -> if (errorBody.contains("API_KEY_INVALID", ignoreCase = true)) "Invalid Gemini API key" else "Gemini error: $errorBody"
                    401, 403 -> "Invalid Gemini API key or unauthorized (HTTP ${response.code})"
                    429 -> "Gemini rate limit or quota exceeded (HTTP 429)"
                    else -> "Gemini error HTTP ${response.code}: $errorBody"
                }
                emit(AIChunk.Error(errorMsg, isRetryable = response.code in listOf(429, 500, 503)))
                response.close()
                return@flow
            }

            val body = response.body ?: run {
                emit(AIChunk.Error("Empty response body from Gemini"))
                return@flow
            }

            BufferedReader(InputStreamReader(body.byteStream(), Charsets.UTF_8)).use { reader ->
                var line: String? = reader.readLine()
                while (line != null) {
                    val trimmed = line.trim()
                    if (trimmed.startsWith("data:")) {
                        val dataContent = trimmed.substring(5).trim()
                        if (dataContent.isNotEmpty()) {
                            try {
                                val json = JSONObject(dataContent)
                                val candidates = json.optJSONArray("candidates")
                                if (candidates != null && candidates.length() > 0) {
                                    val candidate = candidates.getJSONObject(0)
                                    val content = candidate.optJSONObject("content")
                                    val parts = content?.optJSONArray("parts")
                                    if (parts != null && parts.length() > 0) {
                                        val text = parts.getJSONObject(0).optString("text")
                                        if (!text.isNullOrEmpty()) {
                                            emit(AIChunk.Delta(text))
                                        }
                                    }
                                }
                            } catch (_: Exception) {
                                // Ignore non-json or partial lines
                            }
                        }
                    }
                    line = reader.readLine()
                }
            }
            emit(AIChunk.Done)
        } catch (e: CancellationException) {
            throw e
        } catch (e: Exception) {
            emit(AIChunk.Error("Connection error: ${e.localizedMessage ?: "Network failure"}", isRetryable = true))
        }
    }.flowOn(Dispatchers.IO)
}
