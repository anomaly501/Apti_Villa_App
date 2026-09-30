package app.apti.villa.domain.engine.revision

import app.apti.villa.domain.model.Mistake
import app.apti.villa.domain.model.Question
import org.junit.Assert.*
import org.junit.Test

class MistakeManagerTest {

    private val dummyQuestion = Question(
        id = 1L,
        uuid = "q-uuid-1",
        sourceId = 1L,
        externalId = "ext-1",
        text = "What is 2+2?",
        format = "SINGLE_CHOICE",
        subjectId = null,
        topicId = null,
        subTopicId = null,
        difficulty = "EASY",
        sourceTimestampSec = null,
        sourceUrl = null,
        language = "en",
        hasSolution = true,
    )

    @Test
    fun `recordWrongAnswer creates active mistake with wrongCount 1 when no previous mistake exists`() {
        val now = 1_000_000L
        val mistake = MistakeManager.recordWrongAnswer(
            question = dummyQuestion,
            currentMistake = null,
            now = now,
        )

        assertEquals(dummyQuestion, mistake.question)
        assertEquals(now, mistake.firstWrongAt)
        assertEquals(now, mistake.lastWrongAt)
        assertEquals(1, mistake.wrongCount)
        assertEquals(0, mistake.consecutiveCorrect)
        assertEquals("ACTIVE", mistake.status)
    }

    @Test
    fun `recordWrongAnswer updates existing mistake, increments count, and resets consecutiveCorrect`() {
        val firstTime = 1_000_000L
        val secondTime = 2_000_000L
        val initial = Mistake(
            question = dummyQuestion,
            firstWrongAt = firstTime,
            lastWrongAt = firstTime,
            wrongCount = 1,
            consecutiveCorrect = 1,
            status = "RESOLVED",
        )

        val updated = MistakeManager.recordWrongAnswer(
            question = dummyQuestion,
            currentMistake = initial,
            now = secondTime,
        )

        assertEquals(firstTime, updated.firstWrongAt)
        assertEquals(secondTime, updated.lastWrongAt)
        assertEquals(2, updated.wrongCount)
        assertEquals(0, updated.consecutiveCorrect)
        assertEquals("ACTIVE", updated.status)
    }

    @Test
    fun `recordRetryResult with correct answer increments consecutive correct and resolves after 2 consecutive`() {
        val now = 1_000_000L
        val initial = Mistake(
            question = dummyQuestion,
            firstWrongAt = now,
            lastWrongAt = now,
            wrongCount = 2,
            consecutiveCorrect = 0,
            status = "ACTIVE",
        )

        // 1st correct retry
        val after1 = MistakeManager.recordRetryResult(initial, isCorrect = true, now = now + 100)
        assertEquals(1, after1.consecutiveCorrect)
        assertEquals("ACTIVE", after1.status)

        // 2nd correct retry -> RESOLVED
        val after2 = MistakeManager.recordRetryResult(after1, isCorrect = true, now = now + 200)
        assertEquals(2, after2.consecutiveCorrect)
        assertEquals("RESOLVED", after2.status)
    }

    @Test
    fun `recordRetryResult with wrong answer resets consecutive correct and increments count`() {
        val now = 1_000_000L
        val initial = Mistake(
            question = dummyQuestion,
            firstWrongAt = now,
            lastWrongAt = now,
            wrongCount = 1,
            consecutiveCorrect = 1,
            status = "ACTIVE",
        )

        val afterWrong = MistakeManager.recordRetryResult(initial, isCorrect = false, now = now + 500)
        assertEquals(0, afterWrong.consecutiveCorrect)
        assertEquals(2, afterWrong.wrongCount)
        assertEquals(now + 500, afterWrong.lastWrongAt)
        assertEquals("ACTIVE", afterWrong.status)
    }
}
