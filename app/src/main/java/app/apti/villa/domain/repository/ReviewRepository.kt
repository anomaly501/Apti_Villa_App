package app.apti.villa.domain.repository

import app.apti.villa.domain.model.*
import kotlinx.coroutines.flow.Flow

interface ReviewRepository {
    // Mistake Book
    fun getActiveMistakes(): Flow<List<Mistake>>
    fun getResolvedMistakes(): Flow<List<Mistake>>
    suspend fun recordMistake(questionId: Long)
    suspend fun recordMistakeRetry(questionId: Long, isCorrect: Boolean)
    suspend fun updateMistakeStatus(questionId: Long, status: String)
    suspend fun deleteMistake(questionId: Long)
    suspend fun startMistakePracticeSession(): Long

    // Leitner Revision
    fun getDueRevisionItems(): Flow<List<RevisionItem>>
    fun getRevisionBoxStats(): Flow<RevisionBoxStats>
    suspend fun recordRevisionResult(questionId: Long, isCorrect: Boolean)
    suspend fun setRevisionSuspended(questionId: Long, suspended: Boolean)
    suspend fun startRevisionPracticeSession(): Long

    // Bookmarks & Saved Tricks
    fun getBookmarkedQuestions(): Flow<List<BookmarkedQuestion>>
    fun getSavedTricksWithQuestions(): Flow<List<SavedTrickWithQuestion>>
    suspend fun deleteSavedTrick(trickId: Long)
    suspend fun startBookmarkedPracticeSession(): Long
}
