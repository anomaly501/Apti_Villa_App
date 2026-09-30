package app.apti.villa.data.local.dao

import androidx.room.Dao
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

data class SearchDbResult(
    val id: Long,
    val title: String,
    val difficulty: String?,
    val subjectName: String?,
    val topicName: String?,
    val resultType: String,
    val snippet: String?,
)

@Dao
interface SearchDao {

    @Query("""
        SELECT 
            q.id AS id,
            q.text AS title,
            q.difficulty AS difficulty,
            s.name AS subjectName,
            t.name AS topicName,
            'QUESTION' AS resultType,
            snippet(question_fts, '<b>', '</b>', '...', 15) AS snippet
        FROM question_fts fts
        INNER JOIN question q ON q.id = fts.rowid
        LEFT JOIN subject s ON s.id = q.subject_id
        LEFT JOIN topic t ON t.id = q.topic_id
        WHERE question_fts MATCH :query
        LIMIT 50
    """)
    fun searchQuestions(query: String): Flow<List<SearchDbResult>>

    @Query("""
        SELECT 
            q.id AS id,
            q.text AS title,
            q.difficulty AS difficulty,
            s.name AS subjectName,
            t.name AS topicName,
            'QUESTION' AS resultType,
            snippet(question_fts, '<b>', '</b>', '...', 15) AS snippet
        FROM question_fts fts
        INNER JOIN question q ON q.id = fts.rowid
        LEFT JOIN subject s ON s.id = q.subject_id
        LEFT JOIN topic t ON t.id = q.topic_id
        WHERE question_fts MATCH :query
        LIMIT 50
    """)
    suspend fun searchQuestionsOnce(query: String): List<SearchDbResult>

    @Query("""
        SELECT 
            q.id AS id,
            q.text AS title,
            q.difficulty AS difficulty,
            s.name AS subjectName,
            t.name AS topicName,
            'QUESTION' AS resultType,
            snippet(question_option_fts, '<b>', '</b>', '...', 15) AS snippet
        FROM question_option_fts fts
        INNER JOIN question_option qo ON qo.id = fts.rowid
        INNER JOIN question q ON q.id = qo.question_id
        LEFT JOIN subject s ON s.id = q.subject_id
        LEFT JOIN topic t ON t.id = q.topic_id
        WHERE question_option_fts MATCH :query
        LIMIT 50
    """)
    suspend fun searchQuestionsByOptionsOnce(query: String): List<SearchDbResult>

    @Query("""
        SELECT 
            t.id AS id,
            t.title AS title,
            NULL AS difficulty,
            e.name AS subjectName,
            NULL AS topicName,
            'TEST' AS resultType,
            snippet(test_fts, '<b>', '</b>', '...', 15) AS snippet
        FROM test_fts fts
        INNER JOIN test t ON t.id = fts.rowid
        LEFT JOIN exam e ON e.id = t.exam_id
        WHERE test_fts MATCH :query
        LIMIT 50
    """)
    suspend fun searchTestsOnce(query: String): List<SearchDbResult>

    @Query("""
        SELECT 
            tr.id AS id,
            tr.name AS title,
            NULL AS difficulty,
            NULL AS subjectName,
            NULL AS topicName,
            'TRICK' AS resultType,
            snippet(trick_fts, '<b>', '</b>', '...', 15) AS snippet
        FROM trick_fts fts
        INNER JOIN trick tr ON tr.id = fts.rowid
        WHERE trick_fts MATCH :query
        LIMIT 50
    """)
    suspend fun searchTricksOnce(query: String): List<SearchDbResult>

    @Query("""
        SELECT 
            t.id AS id,
            t.name AS title,
            NULL AS difficulty,
            s.name AS subjectName,
            NULL AS topicName,
            'TOPIC' AS resultType,
            t.name AS snippet
        FROM topic t
        LEFT JOIN subject s ON s.id = t.subject_id
        WHERE t.name LIKE :pattern
        LIMIT 30
    """)
    suspend fun searchTopicsOnce(pattern: String): List<SearchDbResult>
}
