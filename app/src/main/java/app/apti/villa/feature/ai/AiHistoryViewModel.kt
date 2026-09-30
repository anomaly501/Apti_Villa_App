package app.apti.villa.feature.ai

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import app.apti.villa.domain.model.AiConversation
import app.apti.villa.domain.repository.AiConversationRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AiHistoryViewModel @Inject constructor(
    private val aiConversationRepository: AiConversationRepository,
) : ViewModel() {

    val conversations: StateFlow<List<AiConversation>> = aiConversationRepository.getAllConversations()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList(),
        )

    fun deleteConversation(id: Long) {
        viewModelScope.launch {
            aiConversationRepository.deleteConversation(id)
        }
    }

    fun deleteAllConversations() {
        viewModelScope.launch {
            aiConversationRepository.deleteAllConversations()
        }
    }
}
