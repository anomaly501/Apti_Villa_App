package app.apti.villa.data.local.dao

import androidx.room.Dao
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

data class TopMistakeTopicDbRow(
    val topicId: Long,
    val topicName: String,
    val mistakeCount: Int,
)

data class InventoryQuestionDbRow(
    val questionId: Long,
    val text: String,
    val difficulty: String?,
    val subjectId: Long?,
    val subjectName: String?,
    val topicId: Long?,
    val topicName: String?,
    val isAttempted: Boolean,
    val isCorrect: Boolean?,
    val isBookmarked: Boolean,
    val isDifficult: Boolean,
    val isMistake: Boolean,
)

@Dao
interface InventoryDao {

    @Query("SELECT COUNT(*) FROM question")
    fun countTotalQuestions(): Flow<Int>

    @Query("SELECT COUNT(DISTINCT question_id) FROM answer WHERE status IN ('ANSWERED', 'ANSWERED_MARKED')")
    fun countAttemptedQuestions(): Flow<Int>

    @Query("SELECT COUNT(DISTINCT question_id) FROM answer WHERE is_correct = 1")
    fun countCorrectQuestions(): Flow<Int>

    @Query("SELECT COUNT(DISTINCT question_id) FROM answer WHERE is_correct = 0")
    fun countWrongQuestions(): Flow<Int>

    @Query("""
        SELECT COUNT(DISTINCT qt.question_id)
        FROM question_tag qt
        INNER JOIN tag t ON t.id = qt.tag_id
        WHERE t.name = 'Important' OR t.name = 'Bookmark'
    """)
    fun countBookmarkedQuestions(): Flow<Int>

    @Query("""
        SELECT COUNT(DISTINCT qt.question_id)
        FROM question_tag qt
        INNER JOIN tag t ON t.id = qt.tag_id
        WHERE t.name = 'Difficult'
    """)
    fun countDifficultQuestions(): Flow<Int>

    @Query("SELECT COUNT(*) FROM revision_item WHERE suspended = 0")
    fun countRevisionQuestions(): Flow<Int>

    @Query("""
        SELECT COUNT(DISTINCT q.topic_id)
        FROM question q
        INNER JOIN answer a ON a.question_id = q.id
        WHERE q.topic_id IS NOT NULL AND a.status IN ('ANSWERED', 'ANSWERED_MARKED')
    """)
    fun countTopicsStudied(): Flow<Int>

    @Query("SELECT COUNT(*) FROM topic")
    fun countTotalTopics(): Flow<Int>

    @Query("SELECT COUNT(*) FROM saved_trick")
    fun countSavedTricks(): Flow<Int>

    @Query("SELECT COUNT(*) FROM mistake WHERE status = 'ACTIVE'")
    fun countActiveMistakes(): Flow<Int>

    @Query("""
        SELECT q.topic_id AS topicId, t.name AS topicName, COUNT(m.question_id) AS mistakeCount
        FROM mistake m
        INNER JOIN question q ON q.id = m.question_id
        INNER JOIN topic t ON t.id = q.topic_id
        WHERE m.status = 'ACTIVE'
        GROUP BY q.topic_id
        ORDER BY mistakeCount DESC
        LIMIT 5
    """)
    fun getTopMistakeTopics(): Flow<List<TopMistakeTopicDbRow>>

    @Query("""
        SELECT 
            q.id AS questionId,
            q.text AS text,
            q.difficulty AS difficulty,
            q.subject_id AS subjectId,
            s.name AS subjectName,
            q.topic_id AS topicId,
            t.name AS topicName,
            EXISTS(SELECT 1 FROM answer a WHERE a.question_id = q.id AND a.status IN ('ANSWERED', 'ANSWERED_MARKED')) AS isAttempted,
            (SELECT a.is_correct FROM answer a WHERE a.question_id = q.id AND a.status IN ('ANSWERED', 'ANSWERED_MARKED') ORDER BY a.answered_at DESC LIMIT 1) AS isCorrect,
            EXISTS(SELECT 1 FROM question_tag qt INNER JOIN tag tg ON tg.id = qt.tag_id WHERE qt.question_id = q.id AND (tg.name = 'Important' OR tg.name = 'Bookmark')) AS isBookmarked,
            EXISTS(SELECT 1 FROM question_tag qt INNER JOIN tag tg ON tg.id = qt.tag_id WHERE qt.question_id = q.id AND tg.name = 'Difficult') AS isDifficult,
            EXISTS(SELECT 1 FROM mistake m WHERE m.question_id = q.id AND m.status = 'ACTIVE') AS isMistake
        FROM question q
        LEFT JOIN subject s ON s.id = q.subject_id
        LEFT JOIN topic t ON t.id = q.topic_id
        WHERE (:subjectId IS NULL OR q.subject_id = :subjectId)
          AND (:topicId IS NULL OR q.topic_id = :topicId)
          AND (:difficulty IS NULL OR q.difficulty = :difficulty COLLATE NOCASE)
        ORDER BY q.id ASC
        LIMIT 200
    """)
    fun getQuestionsWithDetails(
        subjectId: Long?,
        topicId: Long?,
        difficulty: String?,
    ): Flow<List<InventoryQuestionDbRow>>
}
