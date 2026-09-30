package app.apti.villa.ai.context

import app.apti.villa.domain.model.AIChatMessage
import app.apti.villa.domain.model.AIChatRequest
import app.apti.villa.domain.model.AIChatRole
import app.apti.villa.domain.model.AiContextPreview
import app.apti.villa.domain.model.ProviderConfig
import app.apti.villa.domain.model.Question
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AiContextBuilder @Inject constructor() {

    companion object {
        const val MAX_CONVERSATION_MESSAGES = 8
        const val MAX_PAYLOAD_CHARS = 12000 // roughly 3,000 tokens

        val SYSTEM_PROMPT = """
            You are an expert, encouraging aptitude and reasoning tutor for Indian competitive examinations (SSC CGL/CHSL, Banking IBPS/SBI, Railway RRB, Defence, UPSC CSAT, State PSCs).

            Core Guidelines:
            1. Pedagogical approach: Prefer Socratic questioning, gentle nudges, and conceptual hints that guide the student to discover the solution themselves.
            2. Answer revelation: Never reveal the correct answer option directly unless the student explicitly asks "what is the correct answer?" or the answer gate is open and they ask for the full solution.
            3. Error analysis: When analyzing a student's incorrect attempt, identify their conceptual mistake, arithmetic slip, or misinterpretation of the question.
            4. Shortcut methods: Where appropriate for competitive exams, highlight high-speed techniques (e.g., unit digit, digital sum, ratio method, elimination of implausible options).
            5. Conciseness: Keep responses crisp and easy to read on mobile screens. Avoid conversational fluff.
            6. Truthfulness: Never hallucinate or invent facts. If the question statement is ambiguous or lacks necessary data, say so clearly.
            7. Answer key integrity: If the question's official answer key appears inconsistent or mathematically incorrect, politely point out the discrepancy.
        """.trimIndent()
    }

    fun buildUserPayload(
        question: Question,
        selectedOptionId: Long? = null,
        openedHintIds: Set<Long> = emptySet(),
        isAnswerGated: Boolean = false,
        attemptSummary: String? = null,
        userQuery: String,
    ): String {
        val sb = StringBuilder()

        sb.appendLine("### QUESTION")
        sb.appendLine(question.text)
        question.difficulty?.let { sb.appendLine("(Difficulty: $it)") }
        sb.appendLine()

        sb.appendLine("### OPTIONS")
        question.options.sortedBy { it.position }.forEachIndexed { index, option ->
            val label = ('A' + index).toString()
            sb.appendLine("$label) ${option.text}")
        }
        sb.appendLine()

        selectedOptionId?.let { selId ->
            val selectedOption = question.options.find { it.id == selId }
            val index = question.options.indexOf(selectedOption)
            val label = if (index >= 0) ('A' + index).toString() else "?"
            sb.appendLine("### STUDENT'S SELECTED OPTION")
            sb.appendLine("Selected: $label) ${selectedOption?.text ?: "Unknown"}")
            sb.appendLine()
        }

        attemptSummary?.let { summary ->
            sb.appendLine("### ATTEMPT CONTEXT")
            sb.appendLine(summary)
            sb.appendLine()
        }

        // Answer Gate (D13)
        if (isAnswerGated) {
            sb.appendLine("### OFFICIAL ANSWER & SOLUTION (Gate: OPEN)")
            val correctOption = question.options.find { it.isCorrect }
            val correctIndex = question.options.indexOf(correctOption)
            val correctLabel = if (correctIndex >= 0) ('A' + correctIndex).toString() else "?"
            sb.appendLine("Correct Option: $correctLabel) ${correctOption?.text ?: "Not specified"}")

            if (question.solutionSteps.isNotEmpty()) {
                sb.appendLine("Solution Steps:")
                question.solutionSteps.sortedBy { it.number }.forEach { step ->
                    sb.appendLine("${step.number}. ${step.action}")
                }
            }

            if (question.tricks.isNotEmpty()) {
                sb.appendLine("Competitive Shortcuts / Tricks:")
                question.tricks.forEach { trick ->
                    sb.appendLine("• ${trick.name}: ${trick.description}")
                }
            }
            sb.appendLine()
        } else {
            sb.appendLine("### ANSWER GATE STATUS: CLOSED")
            sb.appendLine("NOTE: The student has NOT submitted or finalized their answer yet. DO NOT give away the final correct option or write out the full solution directly. Provide conceptual guidance and nudges only.")
            sb.appendLine()
        }

        // Opened hints only
        val openedHints = question.hints.filter { it.id in openedHintIds || it.isUnlocked }
        if (openedHints.isNotEmpty()) {
            sb.appendLine("### HINTS ALREADY UNLOCKED BY STUDENT")
            openedHints.sortedBy { it.number }.forEach { hint ->
                sb.appendLine("Hint ${hint.number} (${hint.title}): ${hint.description}")
            }
            sb.appendLine()
        }

        sb.appendLine("### STUDENT QUESTION / REQUEST")
        sb.appendLine(userQuery.ifBlank { "Please explain how to approach this question." })

        return sb.toString().trim()
    }

    fun buildChatRequest(
        config: ProviderConfig,
        question: Question,
        selectedOptionId: Long? = null,
        openedHintIds: Set<Long> = emptySet(),
        isAnswerGated: Boolean = false,
        attemptSummary: String? = null,
        userQuery: String,
        previousMessages: List<AIChatMessage> = emptyList(),
    ): AIChatRequest {
        val messages = mutableListOf<AIChatMessage>()

        // 1. System Prompt
        messages.add(AIChatMessage(AIChatRole.SYSTEM, SYSTEM_PROMPT))

        // 2. Add truncated conversation history (up to MAX_CONVERSATION_MESSAGES)
        val historyWindow = previousMessages
            .filter { it.role != AIChatRole.SYSTEM }
            .takeLast(MAX_CONVERSATION_MESSAGES)
        messages.addAll(historyWindow)

        // 3. User Payload for this turn
        val payload = buildUserPayload(
            question = question,
            selectedOptionId = selectedOptionId,
            openedHintIds = openedHintIds,
            isAnswerGated = isAnswerGated,
            attemptSummary = attemptSummary,
            userQuery = userQuery,
        )
        messages.add(AIChatMessage(AIChatRole.USER, payload))

        return AIChatRequest(
            config = config,
            messages = messages,
            temperature = 0.7f,
        )
    }

    fun buildPreview(
        question: Question,
        selectedOptionId: Long? = null,
        openedHintIds: Set<Long> = emptySet(),
        isAnswerGated: Boolean = false,
        attemptSummary: String? = null,
        userQuery: String,
    ): AiContextPreview {
        val userPayload = buildUserPayload(
            question = question,
            selectedOptionId = selectedOptionId,
            openedHintIds = openedHintIds,
            isAnswerGated = isAnswerGated,
            attemptSummary = attemptSummary,
            userQuery = userQuery,
        )
        val totalChars = SYSTEM_PROMPT.length + userPayload.length
        val approximateTokens = (totalChars / 4) + 1

        return AiContextPreview(
            systemPrompt = SYSTEM_PROMPT,
            userPayload = userPayload,
            isAnswerGated = isAnswerGated,
            approximateTokens = approximateTokens,
        )
    }
}
