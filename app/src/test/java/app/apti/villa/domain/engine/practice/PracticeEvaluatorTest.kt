package app.apti.villa.domain.engine.practice

import app.apti.villa.domain.model.Answer
import app.apti.villa.domain.model.AnswerStatus
import app.apti.villa.domain.model.QuestionOption
import org.junit.Assert.*
import org.junit.Test

class PracticeEvaluatorTest {

    private val optionA = QuestionOption(id = 1, questionId = 10, position = 0, text = "Option A", isCorrect = false)
    private val optionB = QuestionOption(id = 2, questionId = 10, position = 1, text = "Option B", isCorrect = true)

    private val initialAnswer = Answer(
        id = 100,
        attemptId = 1,
        questionId = 10,
        position = 0,
        status = AnswerStatus.UNSEEN,
    )

    @Test
    fun `evaluating wrong option records failure and preserves attempt for retry`() {
        val now = 10000L
        val firstSeen = 5000L

        val result = PracticeEvaluator.evaluateSubmission(
            currentAnswer = initialAnswer,
            option = optionA,
            now = now,
            firstSeenAt = firstSeen,
        )

        assertFalse(result.isCorrect)
        assertFalse(result.submission.isCorrect)
        assertEquals(5000L, result.submission.msSinceFirstSeen)
        assertEquals(false, result.updatedAnswer.firstSubmissionCorrect)
        assertEquals(false, result.updatedAnswer.isCorrect)
        assertNull(result.updatedAnswer.solvedAtHintLevel)
    }

    @Test
    fun `evaluating correct option marks answer answered and computes hint level`() {
        val now = 12000L
        val firstSeen = 6000L
        val answerWithHint = initialAnswer.copy(hintsOpened = 1)

        val result = PracticeEvaluator.evaluateSubmission(
            currentAnswer = answerWithHint,
            option = optionB,
            now = now,
            firstSeenAt = firstSeen,
        )

        assertTrue(result.isCorrect)
        assertTrue(result.submission.isCorrect)
        assertEquals(6000L, result.submission.msSinceFirstSeen)
        assertEquals(true, result.updatedAnswer.firstSubmissionCorrect)
        assertEquals(true, result.updatedAnswer.isCorrect)
        assertEquals(AnswerStatus.ANSWERED, result.updatedAnswer.status)
        assertEquals(1, result.updatedAnswer.solvedAtHintLevel)
        assertEquals(now, result.updatedAnswer.answeredAt)
    }

    @Test
    fun `revealAnswer marks revealed true and sets solvedAtHintLevel to null`() {
        val now = 15000L
        val answerWithHints = initialAnswer.copy(hintsOpened = 2)

        val revealed = PracticeEvaluator.revealAnswer(
            currentAnswer = answerWithHints,
            correctOptionId = optionB.id,
            now = now,
        )

        assertTrue(revealed.revealed)
        assertEquals(AnswerStatus.ANSWERED, revealed.status)
        assertEquals(optionB.id, revealed.selectedOptionId)
        assertNull(revealed.solvedAtHintLevel)
        assertEquals(now, revealed.answeredAt)
    }
}
