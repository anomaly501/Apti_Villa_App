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
class AnthropicProvider @Inject constructor(
    private val okHttpClient: OkHttpClient,
) : AIProvider {

    override val providerType: AiProviderType = AiProviderType.ANTHROPIC

    private val jsonMediaType = "application/json; charset=utf-8".toMediaType()

    private fun buildUrl(endpoint: String): String {
        val clean = endpoint.trim().removeSuffix("/")
        return if (clean.endsWith("/messages") || clean.endsWith("/v1/messages")) {
            clean
        } else if (clean.endsWith("/v1")) {
            "$clean/messages"
        } else {
            "$clean/v1/messages"
        }
    }

    override suspend fun validateKey(config: ProviderConfig): Result<Unit> = runCatching {
        val url = buildUrl(config.endpointUrl)
        val payload = JSONObject().apply {
            put("model", config.modelName)
            put("max_tokens", 1)
            put("messages", JSONArray().apply {
                put(JSONObject().apply {
                    put("role", "user")
                    put("content", "ping")
                })
            })
            put("stream", false)
        }

        val request = Request.Builder()
            .url(url)
            .header("x-api-key", config.apiKey.trim())
            .header("anthropic-version", "2023-06-01")
            .post(payload.toString().toRequestBody(jsonMediaType))
            .build()

        okHttpClient.newCall(request).execute().use { response ->
            when (response.code) {
                in 200..299 -> Unit
                401 -> throw IllegalStateException("Invalid Anthropic API key")
                429 -> throw IllegalStateException("Anthropic quota or rate limit exceeded (HTTP 429)")
                else -> throw IllegalStateException("Anthropic provider error: HTTP ${response.code} ${response.message}")
            }
        }
    }

    override fun streamChat(request: AIChatRequest): Flow<AIChunk> = flow {
        val url = buildUrl(request.config.endpointUrl)
        var systemPrompt = ""
        val messagesArray = JSONArray()

        for (msg in request.messages) {
            when (msg.role) {
                AIChatRole.SYSTEM -> {
                    systemPrompt = msg.content
                }
                AIChatRole.USER -> {
                    messagesArray.put(JSONObject().apply {
                        put("role", "user")
                        put("content", msg.content)
                    })
                }
                AIChatRole.ASSISTANT -> {
                    messagesArray.put(JSONObject().apply {
                        put("role", "assistant")
                        put("content", msg.content)
                    })
                }
            }
        }

        val payload = JSONObject().apply {
            put("model", request.config.modelName)
            put("max_tokens", 2048)
            if (systemPrompt.isNotBlank()) {
                put("system", systemPrompt)
            }
            put("messages", messagesArray)
            put("temperature", request.temperature)
            put("stream", true)
        }

        val httpRequest = Request.Builder()
            .url(url)
            .header("x-api-key", request.config.apiKey.trim())
            .header("anthropic-version", "2023-06-01")
            .header("Accept", "text/event-stream")
            .post(payload.toString().toRequestBody(jsonMediaType))
            .build()

        try {
            val response = okHttpClient.newCall(httpRequest).execute()
            if (!response.isSuccessful) {
                val errorBody = response.body?.string() ?: ""
                val errorMsg = when (response.code) {
                    401 -> "Invalid Anthropic API key"
                    429 -> "Anthropic rate limit or quota exceeded (HTTP 429)"
                    else -> "Anthropic error HTTP ${response.code}: $errorBody"
                }
                emit(AIChunk.Error(errorMsg, isRetryable = response.code in listOf(429, 500, 529)))
                response.close()
                return@flow
            }

            val body = response.body ?: run {
                emit(AIChunk.Error("Empty response body from Anthropic"))
                return@flow
            }

            BufferedReader(InputStreamReader(body.byteStream(), Charsets.UTF_8)).use { reader ->
                var currentEvent = ""
                var line: String? = reader.readLine()
                while (line != null) {
                    val trimmed = line.trim()
                    if (trimmed.startsWith("event:")) {
                        currentEvent = trimmed.substring(6).trim()
                    } else if (trimmed.startsWith("data:")) {
                        val dataContent = trimmed.substring(5).trim()
                        if (currentEvent == "message_stop") {
                            emit(AIChunk.Done)
                            break
                        } else if (dataContent.isNotEmpty()) {
                            try {
                                val json = JSONObject(dataContent)
                                val delta = json.optJSONObject("delta")
                                val text = delta?.optString("text")
                                if (!text.isNullOrEmpty()) {
                                    emit(AIChunk.Delta(text))
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
