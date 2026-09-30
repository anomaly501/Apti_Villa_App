package app.apti.villa.domain.engine.practice

import app.apti.villa.domain.engine.hint.HintLadder
import app.apti.villa.domain.model.Answer
import app.apti.villa.domain.model.AnswerStatus
import app.apti.villa.domain.model.AnswerSubmission
import app.apti.villa.domain.model.QuestionOption

object PracticeEvaluator {

    data class EvaluationResult(
        val updatedAnswer: Answer,
        val submission: AnswerSubmission,
        val isCorrect: Boolean,
    )

    /**
     * Evaluates an option submission for an answer in practice mode.
     */
    fun evaluateSubmission(
        currentAnswer: Answer,
        option: QuestionOption,
        now: Long,
        firstSeenAt: Long,
    ): EvaluationResult {
        val isCorrect = option.isCorrect
        val msSinceFirstSeen = (now - firstSeenAt).coerceAtLeast(0)

        val submission = AnswerSubmission(
            answerId = currentAnswer.id,
            optionId = option.id,
            submittedAt = now,
            hintsOpenAtSubmit = currentAnswer.hintsOpened,
            msSinceFirstSeen = msSinceFirstSeen,
            isCorrect = isCorrect,
        )

        val isFirstSubmission = currentAnswer.firstSubmissionCorrect == null

        val updatedAnswer = currentAnswer.copy(
            selectedOptionId = option.id,
            isCorrect = if (isCorrect) true else (currentAnswer.isCorrect ?: false),
            status = if (isCorrect) AnswerStatus.ANSWERED else currentAnswer.status,
            firstSubmissionCorrect = if (isFirstSubmission) isCorrect else currentAnswer.firstSubmissionCorrect,
            answeredAt = if (isCorrect) now else currentAnswer.answeredAt,
            solvedAtHintLevel = if (isCorrect && currentAnswer.solvedAtHintLevel == null) {
                HintLadder.calculateSolvedAtHintLevel(currentAnswer.hintsOpened, currentAnswer.revealed)
            } else {
                currentAnswer.solvedAtHintLevel
            },
        )

        return EvaluationResult(
            updatedAnswer = updatedAnswer,
            submission = submission,
            isCorrect = isCorrect,
        )
    }

    /**
     * Marks the answer as revealed when student clicks "Show Answer".
     */
    fun revealAnswer(
        currentAnswer: Answer,
        correctOptionId: Long?,
        now: Long,
    ): Answer {
        return currentAnswer.copy(
            selectedOptionId = correctOptionId ?: currentAnswer.selectedOptionId,
            revealed = true,
            status = AnswerStatus.ANSWERED,
            solvedAtHintLevel = null,
            answeredAt = currentAnswer.answeredAt ?: now,
        )
    }
}
