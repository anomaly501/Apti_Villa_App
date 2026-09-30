package app.apti.villa.domain.engine.test

import app.apti.villa.domain.model.*

object Scorer {

    data class ScoreSummary(
        val totalQuestions: Int,
        val attemptedCount: Int,
        val correctCount: Int,
        val wrongCount: Int,
        val unattemptedCount: Int,
        val markedCount: Int,
        val score: Float,
        val maxScore: Float,
        val negativeMarksDeducted: Float,
        val accuracyPercentage: Float,
        val timeSpentSec: Long,
    )

    fun calculateScore(
        answers: List<Answer>,
        marksCorrect: Float,
        negativeMarks: Float,
    ): ScoreSummary {
        val totalQuestions = answers.size
        var correctCount = 0
        var wrongCount = 0
        var unattemptedCount = 0
        var markedCount = 0
        var totalTimeSpentMs = 0L

        for (answer in answers) {
            totalTimeSpentMs += answer.timeSpentMs
            if (answer.status == AnswerStatus.MARKED || answer.status == AnswerStatus.ANSWERED_MARKED) {
                markedCount++
            }

            if (answer.selectedOptionId != null) {
                if (answer.isCorrect == true) {
                    correctCount++
                } else {
                    wrongCount++
                }
            } else {
                unattemptedCount++
            }
        }

        val attemptedCount = correctCount + wrongCount
        val negativeMarksDeducted = wrongCount * negativeMarks
        val score = (correctCount * marksCorrect) - negativeMarksDeducted
        val maxScore = totalQuestions * marksCorrect
        val accuracy = if (attemptedCount > 0) {
            (correctCount.toFloat() / attemptedCount) * 100f
        } else {
            0f
        }

        return ScoreSummary(
            totalQuestions = totalQuestions,
            attemptedCount = attemptedCount,
            correctCount = correctCount,
            wrongCount = wrongCount,
            unattemptedCount = unattemptedCount,
            markedCount = markedCount,
            score = score,
            maxScore = maxScore,
            negativeMarksDeducted = negativeMarksDeducted,
            accuracyPercentage = accuracy,
            timeSpentSec = totalTimeSpentMs / 1000L,
        )
    }
}
