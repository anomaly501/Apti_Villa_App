package app.apti.villa.ai.context

import app.apti.villa.domain.model.AIChatMessage
import app.apti.villa.domain.model.AIChatRole
import app.apti.villa.domain.model.AiProviderType
import app.apti.villa.domain.model.Hint
import app.apti.villa.domain.model.ProviderConfig
import app.apti.villa.domain.model.Question
import app.apti.villa.domain.model.QuestionOption
import app.apti.villa.domain.model.SolutionStep
import app.apti.villa.domain.model.Trick
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

class AiContextBuilderTest {

    private lateinit var builder: AiContextBuilder

    private val sampleQuestion = Question(
        id = 101L,
        uuid = "q-101",
        sourceId = 1L,
        externalId = "ext-101",
        text = "Find the time taken by A and B together to complete the work.",
        format = "MCQ",
        subjectId = 1L,
        topicId = 2L,
        subTopicId = 3L,
        difficulty = "MEDIUM",
        sourceTimestampSec = null,
        sourceUrl = null,
        language = "en",
        hasSolution = true,
        options = listOf(
            QuestionOption(id = 1L, questionId = 101L, position = 0, text = "10 days", isCorrect = false),
            QuestionOption(id = 2L, questionId = 101L, position = 1, text = "12 days", isCorrect = true),
            QuestionOption(id = 3L, questionId = 101L, position = 2, text = "15 days", isCorrect = false),
            QuestionOption(id = 4L, questionId = 101L, position = 3, text = "20 days", isCorrect = false),
        ),
        hints = listOf(
            Hint(id = 1L, questionId = 101L, number = 1, title = "Formulas", description = "Use 1/A + 1/B = 1/T", isUnlocked = false),
            Hint(id = 2L, questionId = 101L, number = 2, title = "LCM Method", description = "Take total work as LCM of days", isUnlocked = false),
        ),
        tricks = listOf(
            Trick(id = 1L, questionId = 101L, name = "Product by Sum", description = "(A*B)/(A+B)", isSaved = false),
        ),
        solutionSteps = listOf(
            SolutionStep(id = 1L, questionId = 101L, number = 1, action = "Calculate daily rate of A and B"),
            SolutionStep(id = 2L, questionId = 101L, number = 2, action = "Sum the rates: 1/20 + 1/30 = 5/60 = 1/12"),
            SolutionStep(id = 3L, questionId = 101L, number = 3, action = "Total time is reciprocal = 12 days"),
        ),
    )

    @Before
    fun setUp() {
        builder = AiContextBuilder()
    }

    @Test
    fun `when answer gate is closed, correct answer and solutions are strictly omitted`() {
        val payload = builder.buildUserPayload(
            question = sampleQuestion,
            selectedOptionId = 1L,
            openedHintIds = emptySet(),
            isAnswerGated = false,
            attemptSummary = "Attempt #1",
            userQuery = "Why is 10 days incorrect?",
        )

        // Gate must be closed
        assertTrue(payload.contains("ANSWER GATE STATUS: CLOSED"))
        assertFalse(payload.contains("OFFICIAL ANSWER & SOLUTION (Gate: OPEN)"))

        // Correct answer and solution steps must NOT be leaked
        assertFalse(payload.contains("Correct Option:"))
        assertFalse(payload.contains("Total time is reciprocal"))
        assertFalse(payload.contains("Product by Sum"))
        assertFalse(payload.contains("Calculate daily rate"))
    }

    @Test
    fun `when answer gate is open, correct answer, solution steps, and tricks are included`() {
        val payload = builder.buildUserPayload(
            question = sampleQuestion,
            selectedOptionId = 1L,
            openedHintIds = emptySet(),
            isAnswerGated = true,
            attemptSummary = "Attempt #1: Incorrect",
            userQuery = "Explain the right way to solve this.",
        )

        assertTrue(payload.contains("OFFICIAL ANSWER & SOLUTION (Gate: OPEN)"))
        assertTrue(payload.contains("Correct Option: B) 12 days"))
        assertTrue(payload.contains("Product by Sum"))
        assertTrue(payload.contains("Calculate daily rate"))
    }

    @Test
    fun `only unlocked hints are included in payload`() {
        // Unlock only hint 1
        val payloadWithHint1 = builder.buildUserPayload(
            question = sampleQuestion,
            openedHintIds = setOf(1L),
            isAnswerGated = false,
            userQuery = "Give me help",
        )

        assertTrue(payloadWithHint1.contains("Hint 1 (Formulas): Use 1/A + 1/B = 1/T"))
        assertFalse(payloadWithHint1.contains("Hint 2 (LCM Method)"))
    }

    @Test
    fun `buildChatRequest enforces system prompt and message truncation`() {
        val config = ProviderConfig(
            providerType = AiProviderType.GEMINI,
            endpointUrl = "https://example.com",
            modelName = "test-model",
            apiKey = "test-key",
        )

        // 12 dummy messages in history
        val longHistory = (1..12).map { i ->
            AIChatMessage(
                role = if (i % 2 == 1) AIChatRole.USER else AIChatRole.ASSISTANT,
                content = "Message $i",
            )
        }

        val request = builder.buildChatRequest(
            config = config,
            question = sampleQuestion,
            userQuery = "Final query",
            previousMessages = longHistory,
        )

        // 1 System + max 8 History + 1 User turn = 10 messages max
        assertEquals(AIChatRole.SYSTEM, request.messages.first().role)
        assertEquals(10, request.messages.size)
        assertEquals(AIChatRole.USER, request.messages.last().role)
        assertTrue(request.messages.last().content.contains("Final query"))
    }

    @Test
    fun `buildPreview returns accurate token estimate and payload`() {
        val preview = builder.buildPreview(
            question = sampleQuestion,
            isAnswerGated = false,
            userQuery = "How do I start?",
        )

        assertFalse(preview.isAnswerGated)
        assertTrue(preview.userPayload.contains("How do I start?"))
        assertTrue(preview.approximateTokens > 50)
    }
}
