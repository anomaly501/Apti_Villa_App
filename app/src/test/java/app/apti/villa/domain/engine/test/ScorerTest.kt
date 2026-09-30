package app.apti.villa.domain.engine.test

import app.apti.villa.domain.model.Answer
import app.apti.villa.domain.model.AnswerStatus
import org.junit.Assert.*
import org.junit.Test

class ScorerTest {

    @Test
    fun `calculateScore correctly computes positive and negative marks with accuracy`() {
        // 5 questions: 3 correct, 1 wrong, 1 unattempted
        val answers = listOf(
            Answer(attemptId = 1, questionId = 1, position = 0, selectedOptionId = 101, isCorrect = true, timeSpentMs = 30000L),
            Answer(attemptId = 1, questionId = 2, position = 1, selectedOptionId = 102, isCorrect = true, timeSpentMs = 45000L),
            Answer(attemptId = 1, questionId = 3, position = 2, selectedOptionId = 103, isCorrect = true, timeSpentMs = 25000L),
            Answer(attemptId = 1, questionId = 4, position = 3, selectedOptionId = 104, isCorrect = false, timeSpentMs = 50000L),
            Answer(attemptId = 1, questionId = 5, position = 4, selectedOptionId = null, isCorrect = null, timeSpentMs = 10000L),
        )

        val summary = Scorer.calculateScore(
            answers = answers,
            marksCorrect = 2.0f,
            negativeMarks = 0.5f,
        )

        assertEquals(5, summary.totalQuestions)
        assertEquals(4, summary.attemptedCount)
        assertEquals(3, summary.correctCount)
        assertEquals(1, summary.wrongCount)
        assertEquals(1, summary.unattemptedCount)

        // Score: (3 * 2.0) - (1 * 0.5) = 6.0 - 0.5 = 5.5
        assertEquals(5.5f, summary.score, 0.001f)
        assertEquals(10.0f, summary.maxScore, 0.001f)
        assertEquals(0.5f, summary.negativeMarksDeducted, 0.001f)

        // Accuracy: 3 / 4 = 75.0%
        assertEquals(75.0f, summary.accuracyPercentage, 0.001f)
        assertEquals(160L, summary.timeSpentSec) // (30+45+25+50+10) = 160s
    }

    @Test
    fun `calculateScore with zero attempts yields zero score and zero accuracy without division by zero`() {
        val answers = listOf(
            Answer(attemptId = 1, questionId = 1, position = 0, selectedOptionId = null, isCorrect = null),
            Answer(attemptId = 1, questionId = 2, position = 1, selectedOptionId = null, isCorrect = null),
        )

        val summary = Scorer.calculateScore(
            answers = answers,
            marksCorrect = 1.0f,
            negativeMarks = 0.25f,
        )

        assertEquals(2, summary.totalQuestions)
        assertEquals(0, summary.attemptedCount)
        assertEquals(0, summary.correctCount)
        assertEquals(0, summary.wrongCount)
        assertEquals(2, summary.unattemptedCount)
        assertEquals(0f, summary.score, 0.001f)
        assertEquals(0f, summary.accuracyPercentage, 0.001f)
    }

    @Test
    fun `calculateScore counts marked for review questions`() {
        val answers = listOf(
            Answer(attemptId = 1, questionId = 1, position = 0, status = AnswerStatus.MARKED),
            Answer(attemptId = 1, questionId = 2, position = 1, status = AnswerStatus.ANSWERED_MARKED, selectedOptionId = 10, isCorrect = true),
            Answer(attemptId = 1, questionId = 3, position = 2, status = AnswerStatus.ANSWERED, selectedOptionId = 11, isCorrect = true),
        )

        val summary = Scorer.calculateScore(answers, 1.0f, 0.25f)
        assertEquals(2, summary.markedCount)
    }
}
