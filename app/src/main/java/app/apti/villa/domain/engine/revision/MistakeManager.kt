package app.apti.villa.domain.engine.revision

import app.apti.villa.domain.model.Mistake
import app.apti.villa.domain.model.Question

object MistakeManager {

    /**
     * Records a wrong answer occurrence for a question.
     */
    fun recordWrongAnswer(
        question: Question,
        currentMistake: Mistake?,
        now: Long,
    ): Mistake {
        return if (currentMistake == null) {
            Mistake(
                question = question,
                firstWrongAt = now,
                lastWrongAt = now,
                wrongCount = 1,
                consecutiveCorrect = 0,
                status = "ACTIVE",
            )
        } else {
            currentMistake.copy(
                lastWrongAt = now,
                wrongCount = currentMistake.wrongCount + 1,
                consecutiveCorrect = 0,
                status = "ACTIVE",
            )
        }
    }

    /**
     * Evaluates a retry attempt on a mistake:
     * - 2 consecutive correct answers marks the mistake as RESOLVED.
     * - Wrong answer resets consecutive correct to 0 and increments wrong count.
     */
    fun recordRetryResult(
        mistake: Mistake,
        isCorrect: Boolean,
        now: Long,
    ): Mistake {
        return if (isCorrect) {
            val newConsecutive = mistake.consecutiveCorrect + 1
            val newStatus = if (newConsecutive >= 2) "RESOLVED" else mistake.status
            mistake.copy(
                consecutiveCorrect = newConsecutive,
                status = newStatus,
            )
        } else {
            mistake.copy(
                lastWrongAt = now,
                wrongCount = mistake.wrongCount + 1,
                consecutiveCorrect = 0,
                status = "ACTIVE",
            )
        }
    }
}
