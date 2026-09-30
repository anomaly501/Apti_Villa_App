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
import java.util.concurrent.TimeUnit
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class OpenAiCompatibleProvider @Inject constructor(
    private val okHttpClient: OkHttpClient,
) : AIProvider {

    override val providerType: AiProviderType = AiProviderType.OPENAI_COMPATIBLE

    private val jsonMediaType = "application/json; charset=utf-8".toMediaType()

    private fun buildUrl(endpoint: String): String {
        val clean = endpoint.trim().removeSuffix("/")
        return if (clean.endsWith("/chat/completions")) {
            clean
        } else {
            "$clean/chat/completions"
        }
    }

    override suspend fun validateKey(config: ProviderConfig): Result<Unit> = runCatching {
        val url = buildUrl(config.endpointUrl)
        val payload = JSONObject().apply {
            put("model", config.modelName)
            put("messages", JSONArray().apply {
                put(JSONObject().apply {
                    put("role", "user")
                    put("content", "ping")
                })
            })
            put("max_tokens", 1)
            put("stream", false)
        }

        val request = Request.Builder()
            .url(url)
            .header("Authorization", "Bearer ${config.apiKey.trim()}")
            .post(payload.toString().toRequestBody(jsonMediaType))
            .build()

        okHttpClient.newCall(request).execute().use { response ->
            when (response.code) {
                in 200..299 -> Unit
                401, 403 -> throw IllegalStateException("Invalid API key or unauthorized (HTTP ${response.code})")
                429 -> throw IllegalStateException("Quota exceeded or rate limited (HTTP 429)")
                else -> throw IllegalStateException("Provider error: HTTP ${response.code} ${response.message}")
            }
        }
    }

    override fun streamChat(request: AIChatRequest): Flow<AIChunk> = flow {
        val url = buildUrl(request.config.endpointUrl)
        val messagesArray = JSONArray()
        for (msg in request.messages) {
            val roleStr = when (msg.role) {
                AIChatRole.SYSTEM -> "system"
                AIChatRole.USER -> "user"
                AIChatRole.ASSISTANT -> "assistant"
            }
            messagesArray.put(JSONObject().apply {
                put("role", roleStr)
                put("content", msg.content)
            })
        }

        val payload = JSONObject().apply {
            put("model", request.config.modelName)
            put("messages", messagesArray)
            put("temperature", request.temperature)
            put("stream", true)
        }

        val httpRequest = Request.Builder()
            .url(url)
            .header("Authorization", "Bearer ${request.config.apiKey.trim()}")
            .header("Accept", "text/event-stream")
            .post(payload.toString().toRequestBody(jsonMediaType))
            .build()

        try {
            val response = okHttpClient.newCall(httpRequest).execute()
            if (!response.isSuccessful) {
                val errorBody = response.body?.string() ?: ""
                val errorMsg = when (response.code) {
                    401, 403 -> "Invalid API key or unauthorized (HTTP ${response.code})"
                    429 -> "Rate limit or quota exceeded (HTTP 429)"
                    else -> "Provider error HTTP ${response.code}: $errorBody"
                }
                emit(AIChunk.Error(errorMsg, isRetryable = response.code in listOf(429, 500, 502, 503)))
                response.close()
                return@flow
            }

            val body = response.body ?: run {
                emit(AIChunk.Error("Empty response body from provider"))
                return@flow
            }

            BufferedReader(InputStreamReader(body.byteStream(), Charsets.UTF_8)).use { reader ->
                var line: String? = reader.readLine()
                while (line != null) {
                    val trimmed = line.trim()
                    if (trimmed.startsWith("data:")) {
                        val dataContent = trimmed.substring(5).trim()
                        if (dataContent == "[DONE]") {
                            emit(AIChunk.Done)
                            break
                        } else if (dataContent.isNotEmpty()) {
                            try {
                                val json = JSONObject(dataContent)
                                val choices = json.optJSONArray("choices")
                                if (choices != null && choices.length() > 0) {
                                    val delta = choices.getJSONObject(0).optJSONObject("delta")
                                    val text = delta?.optString("content")
                                    if (!text.isNullOrEmpty()) {
                                        emit(AIChunk.Delta(text))
                                    }
                                }
                            } catch (_: Exception) {
                                // Ignore unparseable keep-alives
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
