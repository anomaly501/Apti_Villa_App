package app.apti.villa.ai.config

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import app.apti.villa.ai.security.SecureKeyStore
import app.apti.villa.domain.model.AiProviderType
import app.apti.villa.domain.model.ProviderConfig
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

private val Context.aiDataStore: DataStore<Preferences> by preferencesDataStore(name = "aptivilla_ai_prefs")

@Singleton
class AiConfigRepository @Inject constructor(
    @ApplicationContext private val context: Context,
    private val secureKeyStore: SecureKeyStore,
) {
    private object Keys {
        val SELECTED_PROVIDER = stringPreferencesKey("selected_provider")
        val SAVE_CONVERSATIONS = booleanPreferencesKey("save_conversations")

        fun endpointKey(type: AiProviderType) = stringPreferencesKey("endpoint_${type.name}")
        fun modelKey(type: AiProviderType) = stringPreferencesKey("model_${type.name}")
        fun cipherKey(type: AiProviderType) = stringPreferencesKey("cipher_${type.name}")
        fun ivKey(type: AiProviderType) = stringPreferencesKey("iv_${type.name}")
    }

    val selectedProvider: Flow<AiProviderType> = context.aiDataStore.data.map { prefs ->
        val raw = prefs[Keys.SELECTED_PROVIDER] ?: AiProviderType.GEMINI.name
        try {
            AiProviderType.valueOf(raw)
        } catch (_: Exception) {
            AiProviderType.GEMINI
        }
    }

    val isSaveHistoryEnabled: Flow<Boolean> = context.aiDataStore.data.map { prefs ->
        prefs[Keys.SAVE_CONVERSATIONS] ?: true
    }

    fun getEndpoint(type: AiProviderType): Flow<String> = context.aiDataStore.data.map { prefs ->
        prefs[Keys.endpointKey(type)] ?: AiPresets.defaultEndpoint(type)
    }

    fun getModel(type: AiProviderType): Flow<String> = context.aiDataStore.data.map { prefs ->
        prefs[Keys.modelKey(type)] ?: AiPresets.defaultModel(type)
    }

    fun getMaskedApiKey(type: AiProviderType): Flow<String?> = context.aiDataStore.data.map { prefs ->
        val cipher = prefs[Keys.cipherKey(type)]
        val iv = prefs[Keys.ivKey(type)]
        if (!cipher.isNullOrBlank() && !iv.isNullOrBlank()) {
            try {
                val key = secureKeyStore.decrypt(cipher, iv)
                secureKeyStore.maskKey(key)
            } catch (_: Exception) {
                null
            }
        } else {
            null
        }
    }

    fun hasConfiguredKey(type: AiProviderType): Flow<Boolean> = context.aiDataStore.data.map { prefs ->
        val cipher = prefs[Keys.cipherKey(type)]
        val iv = prefs[Keys.ivKey(type)]
        !cipher.isNullOrBlank() && !iv.isNullOrBlank()
    }

    suspend fun setSelectedProvider(type: AiProviderType) {
        context.aiDataStore.edit { prefs ->
            prefs[Keys.SELECTED_PROVIDER] = type.name
        }
    }

    suspend fun setEndpoint(type: AiProviderType, endpoint: String) {
        context.aiDataStore.edit { prefs ->
            prefs[Keys.endpointKey(type)] = endpoint.trim()
        }
    }

    suspend fun setModel(type: AiProviderType, model: String) {
        context.aiDataStore.edit { prefs ->
            prefs[Keys.modelKey(type)] = model.trim()
        }
    }

    suspend fun setSaveHistoryEnabled(enabled: Boolean) {
        context.aiDataStore.edit { prefs ->
            prefs[Keys.SAVE_CONVERSATIONS] = enabled
        }
    }

    suspend fun saveApiKey(type: AiProviderType, apiKey: String) {
        val payload = secureKeyStore.encrypt(apiKey.trim())
        context.aiDataStore.edit { prefs ->
            prefs[Keys.cipherKey(type)] = payload.ciphertextHex
            prefs[Keys.ivKey(type)] = payload.ivHex
        }
    }

    suspend fun deleteApiKey(type: AiProviderType) {
        context.aiDataStore.edit { prefs ->
            prefs.remove(Keys.cipherKey(type))
            prefs.remove(Keys.ivKey(type))
        }
    }

    suspend fun getApiKey(type: AiProviderType): String? {
        val prefs = context.aiDataStore.data.first()
        val cipher = prefs[Keys.cipherKey(type)] ?: return null
        val iv = prefs[Keys.ivKey(type)] ?: return null
        return try {
            secureKeyStore.decrypt(cipher, iv)
        } catch (_: Exception) {
            null
        }
    }

    suspend fun getActiveConfig(): ProviderConfig? {
        val type = selectedProvider.first()
        val key = getApiKey(type) ?: return null
        val endpoint = getEndpoint(type).first()
        val model = getModel(type).first()
        return ProviderConfig(
            providerType = type,
            endpointUrl = endpoint,
            modelName = model,
            apiKey = key,
        )
    }
}
