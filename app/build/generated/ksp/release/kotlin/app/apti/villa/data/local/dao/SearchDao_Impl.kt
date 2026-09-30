package app.apti.villa.`data`.local.dao

import androidx.room.RoomDatabase
import androidx.room.coroutines.createFlow
import androidx.room.util.performSuspending
import androidx.sqlite.SQLiteStatement
import javax.`annotation`.processing.Generated
import kotlin.Int
import kotlin.Long
import kotlin.String
import kotlin.Suppress
import kotlin.collections.List
import kotlin.collections.MutableList
import kotlin.collections.mutableListOf
import kotlin.reflect.KClass
import kotlinx.coroutines.flow.Flow

@Generated(value = ["androidx.room.RoomProcessor"])
@Suppress(names = ["UNCHECKED_CAST", "DEPRECATION", "REDUNDANT_PROJECTION", "REMOVAL"])
public class SearchDao_Impl(
  __db: RoomDatabase,
) : SearchDao {
  private val __db: RoomDatabase
  init {
    this.__db = __db
  }

  public override fun searchQuestions(query: String): Flow<List<SearchDbResult>> {
    val _sql: String = """
        |
        |        SELECT 
        |            q.id AS id,
        |            q.text AS title,
        |            q.difficulty AS difficulty,
        |            s.name AS subjectName,
        |            t.name AS topicName,
        |            'QUESTION' AS resultType,
        |            snippet(question_fts, '<b>', '</b>', '...', 15) AS snippet
        |        FROM question_fts fts
        |        INNER JOIN question q ON q.id = fts.rowid
        |        LEFT JOIN subject s ON s.id = q.subject_id
        |        LEFT JOIN topic t ON t.id = q.topic_id
        |        WHERE question_fts MATCH ?
        |        LIMIT 50
        |    
        """.trimMargin()
    return createFlow(__db, false, arrayOf("question_fts", "question", "subject", "topic")) {
        _connection ->
      val _stmt: SQLiteStatement = _connection.prepare(_sql)
      try {
        var _argIndex: Int = 1
        _stmt.bindText(_argIndex, query)
        val _columnIndexOfId: Int = 0
        val _columnIndexOfTitle: Int = 1
        val _columnIndexOfDifficulty: Int = 2
        val _columnIndexOfSubjectName: Int = 3
        val _columnIndexOfTopicName: Int = 4
        val _columnIndexOfResultType: Int = 5
        val _columnIndexOfSnippet: Int = 6
        val _result: MutableList<SearchDbResult> = mutableListOf()
        while (_stmt.step()) {
          val _item: SearchDbResult
          val _tmpId: Long
          _tmpId = _stmt.getLong(_columnIndexOfId)
          val _tmpTitle: String
          _tmpTitle = _stmt.getText(_columnIndexOfTitle)
          val _tmpDifficulty: String?
          if (_stmt.isNull(_columnIndexOfDifficulty)) {
            _tmpDifficulty = null
          } else {
            _tmpDifficulty = _stmt.getText(_columnIndexOfDifficulty)
          }
          val _tmpSubjectName: String?
          if (_stmt.isNull(_columnIndexOfSubjectName)) {
            _tmpSubjectName = null
          } else {
            _tmpSubjectName = _stmt.getText(_columnIndexOfSubjectName)
          }
          val _tmpTopicName: String?
          if (_stmt.isNull(_columnIndexOfTopicName)) {
            _tmpTopicName = null
          } else {
            _tmpTopicName = _stmt.getText(_columnIndexOfTopicName)
          }
          val _tmpResultType: String
          _tmpResultType = _stmt.getText(_columnIndexOfResultType)
          val _tmpSnippet: String?
          if (_stmt.isNull(_columnIndexOfSnippet)) {
            _tmpSnippet = null
          } else {
            _tmpSnippet = _stmt.getText(_columnIndexOfSnippet)
          }
          _item =
              SearchDbResult(_tmpId,_tmpTitle,_tmpDifficulty,_tmpSubjectName,_tmpTopicName,_tmpResultType,_tmpSnippet)
          _result.add(_item)
        }
        _result
      } finally {
        _stmt.close()
      }
    }
  }

  public override suspend fun searchQuestionsOnce(query: String): List<SearchDbResult> {
    val _sql: String = """
        |
        |        SELECT 
        |            q.id AS id,
        |            q.text AS title,
        |            q.difficulty AS difficulty,
        |            s.name AS subjectName,
        |            t.name AS topicName,
        |            'QUESTION' AS resultType,
        |            snippet(question_fts, '<b>', '</b>', '...', 15) AS snippet
        |        FROM question_fts fts
        |        INNER JOIN question q ON q.id = fts.rowid
        |        LEFT JOIN subject s ON s.id = q.subject_id
        |        LEFT JOIN topic t ON t.id = q.topic_id
        |        WHERE question_fts MATCH ?
        |        LIMIT 50
        |    
        """.trimMargin()
    return performSuspending(__db, true, false) { _connection ->
      val _stmt: SQLiteStatement = _connection.prepare(_sql)
      try {
        var _argIndex: Int = 1
        _stmt.bindText(_argIndex, query)
        val _columnIndexOfId: Int = 0
        val _columnIndexOfTitle: Int = 1
        val _columnIndexOfDifficulty: Int = 2
        val _columnIndexOfSubjectName: Int = 3
        val _columnIndexOfTopicName: Int = 4
        val _columnIndexOfResultType: Int = 5
        val _columnIndexOfSnippet: Int = 6
        val _result: MutableList<SearchDbResult> = mutableListOf()
        while (_stmt.step()) {
          val _item: SearchDbResult
          val _tmpId: Long
          _tmpId = _stmt.getLong(_columnIndexOfId)
          val _tmpTitle: String
          _tmpTitle = _stmt.getText(_columnIndexOfTitle)
          val _tmpDifficulty: String?
          if (_stmt.isNull(_columnIndexOfDifficulty)) {
            _tmpDifficulty = null
          } else {
            _tmpDifficulty = _stmt.getText(_columnIndexOfDifficulty)
          }
          val _tmpSubjectName: String?
          if (_stmt.isNull(_columnIndexOfSubjectName)) {
            _tmpSubjectName = null
          } else {
            _tmpSubjectName = _stmt.getText(_columnIndexOfSubjectName)
          }
          val _tmpTopicName: String?
          if (_stmt.isNull(_columnIndexOfTopicName)) {
            _tmpTopicName = null
          } else {
            _tmpTopicName = _stmt.getText(_columnIndexOfTopicName)
          }
          val _tmpResultType: String
          _tmpResultType = _stmt.getText(_columnIndexOfResultType)
          val _tmpSnippet: String?
          if (_stmt.isNull(_columnIndexOfSnippet)) {
            _tmpSnippet = null
          } else {
            _tmpSnippet = _stmt.getText(_columnIndexOfSnippet)
          }
          _item =
              SearchDbResult(_tmpId,_tmpTitle,_tmpDifficulty,_tmpSubjectName,_tmpTopicName,_tmpResultType,_tmpSnippet)
          _result.add(_item)
        }
        _result
      } finally {
        _stmt.close()
      }
    }
  }

  public override suspend fun searchQuestionsByOptionsOnce(query: String): List<SearchDbResult> {
    val _sql: String = """
        |
        |        SELECT 
        |            q.id AS id,
        |            q.text AS title,
        |            q.difficulty AS difficulty,
        |            s.name AS subjectName,
        |            t.name AS topicName,
        |            'QUESTION' AS resultType,
        |            snippet(question_option_fts, '<b>', '</b>', '...', 15) AS snippet
        |        FROM question_option_fts fts
        |        INNER JOIN question_option qo ON qo.id = fts.rowid
        |        INNER JOIN question q ON q.id = qo.question_id
        |        LEFT JOIN subject s ON s.id = q.subject_id
        |        LEFT JOIN topic t ON t.id = q.topic_id
        |        WHERE question_option_fts MATCH ?
        |        LIMIT 50
        |    
        """.trimMargin()
    return performSuspending(__db, true, false) { _connection ->
      val _stmt: SQLiteStatement = _connection.prepare(_sql)
      try {
        var _argIndex: Int = 1
        _stmt.bindText(_argIndex, query)
        val _columnIndexOfId: Int = 0
        val _columnIndexOfTitle: Int = 1
        val _columnIndexOfDifficulty: Int = 2
        val _columnIndexOfSubjectName: Int = 3
        val _columnIndexOfTopicName: Int = 4
        val _columnIndexOfResultType: Int = 5
        val _columnIndexOfSnippet: Int = 6
        val _result: MutableList<SearchDbResult> = mutableListOf()
        while (_stmt.step()) {
          val _item: SearchDbResult
          val _tmpId: Long
          _tmpId = _stmt.getLong(_columnIndexOfId)
          val _tmpTitle: String
          _tmpTitle = _stmt.getText(_columnIndexOfTitle)
          val _tmpDifficulty: String?
          if (_stmt.isNull(_columnIndexOfDifficulty)) {
            _tmpDifficulty = null
          } else {
            _tmpDifficulty = _stmt.getText(_columnIndexOfDifficulty)
          }
          val _tmpSubjectName: String?
          if (_stmt.isNull(_columnIndexOfSubjectName)) {
            _tmpSubjectName = null
          } else {
            _tmpSubjectName = _stmt.getText(_columnIndexOfSubjectName)
          }
          val _tmpTopicName: String?
          if (_stmt.isNull(_columnIndexOfTopicName)) {
            _tmpTopicName = null
          } else {
            _tmpTopicName = _stmt.getText(_columnIndexOfTopicName)
          }
          val _tmpResultType: String
          _tmpResultType = _stmt.getText(_columnIndexOfResultType)
          val _tmpSnippet: String?
          if (_stmt.isNull(_columnIndexOfSnippet)) {
            _tmpSnippet = null
          } else {
            _tmpSnippet = _stmt.getText(_columnIndexOfSnippet)
          }
          _item =
              SearchDbResult(_tmpId,_tmpTitle,_tmpDifficulty,_tmpSubjectName,_tmpTopicName,_tmpResultType,_tmpSnippet)
          _result.add(_item)
        }
        _result
      } finally {
        _stmt.close()
      }
    }
  }

  public override suspend fun searchTestsOnce(query: String): List<SearchDbResult> {
    val _sql: String = """
        |
        |        SELECT 
        |            t.id AS id,
        |            t.title AS title,
        |            NULL AS difficulty,
        |            e.name AS subjectName,
        |            NULL AS topicName,
        |            'TEST' AS resultType,
        |            snippet(test_fts, '<b>', '</b>', '...', 15) AS snippet
        |        FROM test_fts fts
        |        INNER JOIN test t ON t.id = fts.rowid
        |        LEFT JOIN exam e ON e.id = t.exam_id
        |        WHERE test_fts MATCH ?
        |        LIMIT 50
        |    
        """.trimMargin()
    return performSuspending(__db, true, false) { _connection ->
      val _stmt: SQLiteStatement = _connection.prepare(_sql)
      try {
        var _argIndex: Int = 1
        _stmt.bindText(_argIndex, query)
        val _columnIndexOfId: Int = 0
        val _columnIndexOfTitle: Int = 1
        val _columnIndexOfDifficulty: Int = 2
        val _columnIndexOfSubjectName: Int = 3
        val _columnIndexOfTopicName: Int = 4
        val _columnIndexOfResultType: Int = 5
        val _columnIndexOfSnippet: Int = 6
        val _result: MutableList<SearchDbResult> = mutableListOf()
        while (_stmt.step()) {
          val _item: SearchDbResult
          val _tmpId: Long
          _tmpId = _stmt.getLong(_columnIndexOfId)
          val _tmpTitle: String
          _tmpTitle = _stmt.getText(_columnIndexOfTitle)
          val _tmpDifficulty: String?
          if (_stmt.isNull(_columnIndexOfDifficulty)) {
            _tmpDifficulty = null
          } else {
            _tmpDifficulty = _stmt.getText(_columnIndexOfDifficulty)
          }
          val _tmpSubjectName: String?
          if (_stmt.isNull(_columnIndexOfSubjectName)) {
            _tmpSubjectName = null
          } else {
            _tmpSubjectName = _stmt.getText(_columnIndexOfSubjectName)
          }
          val _tmpTopicName: String?
          if (_stmt.isNull(_columnIndexOfTopicName)) {
            _tmpTopicName = null
          } else {
            _tmpTopicName = _stmt.getText(_columnIndexOfTopicName)
          }
          val _tmpResultType: String
          _tmpResultType = _stmt.getText(_columnIndexOfResultType)
          val _tmpSnippet: String?
          if (_stmt.isNull(_columnIndexOfSnippet)) {
            _tmpSnippet = null
          } else {
            _tmpSnippet = _stmt.getText(_columnIndexOfSnippet)
          }
          _item =
              SearchDbResult(_tmpId,_tmpTitle,_tmpDifficulty,_tmpSubjectName,_tmpTopicName,_tmpResultType,_tmpSnippet)
          _result.add(_item)
        }
        _result
      } finally {
        _stmt.close()
      }
    }
  }

  public override suspend fun searchTricksOnce(query: String): List<SearchDbResult> {
    val _sql: String = """
        |
        |        SELECT 
        |            tr.id AS id,
        |            tr.name AS title,
        |            NULL AS difficulty,
        |            NULL AS subjectName,
        |            NULL AS topicName,
        |            'TRICK' AS resultType,
        |            snippet(trick_fts, '<b>', '</b>', '...', 15) AS snippet
        |        FROM trick_fts fts
        |        INNER JOIN trick tr ON tr.id = fts.rowid
        |        WHERE trick_fts MATCH ?
        |        LIMIT 50
        |    
        """.trimMargin()
    return performSuspending(__db, true, false) { _connection ->
      val _stmt: SQLiteStatement = _connection.prepare(_sql)
      try {
        var _argIndex: Int = 1
        _stmt.bindText(_argIndex, query)
        val _columnIndexOfId: Int = 0
        val _columnIndexOfTitle: Int = 1
        val _columnIndexOfDifficulty: Int = 2
        val _columnIndexOfSubjectName: Int = 3
        val _columnIndexOfTopicName: Int = 4
        val _columnIndexOfResultType: Int = 5
        val _columnIndexOfSnippet: Int = 6
        val _result: MutableList<SearchDbResult> = mutableListOf()
        while (_stmt.step()) {
          val _item: SearchDbResult
          val _tmpId: Long
          _tmpId = _stmt.getLong(_columnIndexOfId)
          val _tmpTitle: String
          _tmpTitle = _stmt.getText(_columnIndexOfTitle)
          val _tmpDifficulty: String?
          if (_stmt.isNull(_columnIndexOfDifficulty)) {
            _tmpDifficulty = null
          } else {
            _tmpDifficulty = _stmt.getText(_columnIndexOfDifficulty)
          }
          val _tmpSubjectName: String?
          if (_stmt.isNull(_columnIndexOfSubjectName)) {
            _tmpSubjectName = null
          } else {
            _tmpSubjectName = _stmt.getText(_columnIndexOfSubjectName)
          }
          val _tmpTopicName: String?
          if (_stmt.isNull(_columnIndexOfTopicName)) {
            _tmpTopicName = null
          } else {
            _tmpTopicName = _stmt.getText(_columnIndexOfTopicName)
          }
          val _tmpResultType: String
          _tmpResultType = _stmt.getText(_columnIndexOfResultType)
          val _tmpSnippet: String?
          if (_stmt.isNull(_columnIndexOfSnippet)) {
            _tmpSnippet = null
          } else {
            _tmpSnippet = _stmt.getText(_columnIndexOfSnippet)
          }
          _item =
              SearchDbResult(_tmpId,_tmpTitle,_tmpDifficulty,_tmpSubjectName,_tmpTopicName,_tmpResultType,_tmpSnippet)
          _result.add(_item)
        }
        _result
      } finally {
        _stmt.close()
      }
    }
  }

  public override suspend fun searchTopicsOnce(pattern: String): List<SearchDbResult> {
    val _sql: String = """
        |
        |        SELECT 
        |            t.id AS id,
        |            t.name AS title,
        |            NULL AS difficulty,
        |            s.name AS subjectName,
        |            NULL AS topicName,
        |            'TOPIC' AS resultType,
        |            t.name AS snippet
        |        FROM topic t
        |        LEFT JOIN subject s ON s.id = t.subject_id
        |        WHERE t.name LIKE ?
        |        LIMIT 30
        |    
        """.trimMargin()
    return performSuspending(__db, true, false) { _connection ->
      val _stmt: SQLiteStatement = _connection.prepare(_sql)
      try {
        var _argIndex: Int = 1
        _stmt.bindText(_argIndex, pattern)
        val _columnIndexOfId: Int = 0
        val _columnIndexOfTitle: Int = 1
        val _columnIndexOfDifficulty: Int = 2
        val _columnIndexOfSubjectName: Int = 3
        val _columnIndexOfTopicName: Int = 4
        val _columnIndexOfResultType: Int = 5
        val _columnIndexOfSnippet: Int = 6
        val _result: MutableList<SearchDbResult> = mutableListOf()
        while (_stmt.step()) {
          val _item: SearchDbResult
          val _tmpId: Long
          _tmpId = _stmt.getLong(_columnIndexOfId)
          val _tmpTitle: String
          _tmpTitle = _stmt.getText(_columnIndexOfTitle)
          val _tmpDifficulty: String?
          if (_stmt.isNull(_columnIndexOfDifficulty)) {
            _tmpDifficulty = null
          } else {
            _tmpDifficulty = _stmt.getText(_columnIndexOfDifficulty)
          }
          val _tmpSubjectName: String?
          if (_stmt.isNull(_columnIndexOfSubjectName)) {
            _tmpSubjectName = null
          } else {
            _tmpSubjectName = _stmt.getText(_columnIndexOfSubjectName)
          }
          val _tmpTopicName: String?
          if (_stmt.isNull(_columnIndexOfTopicName)) {
            _tmpTopicName = null
          } else {
            _tmpTopicName = _stmt.getText(_columnIndexOfTopicName)
          }
          val _tmpResultType: String
          _tmpResultType = _stmt.getText(_columnIndexOfResultType)
          val _tmpSnippet: String?
          if (_stmt.isNull(_columnIndexOfSnippet)) {
            _tmpSnippet = null
          } else {
            _tmpSnippet = _stmt.getText(_columnIndexOfSnippet)
          }
          _item =
              SearchDbResult(_tmpId,_tmpTitle,_tmpDifficulty,_tmpSubjectName,_tmpTopicName,_tmpResultType,_tmpSnippet)
          _result.add(_item)
        }
        _result
      } finally {
        _stmt.close()
      }
    }
  }

  public companion object {
    public fun getRequiredConverters(): List<KClass<*>> = emptyList()
  }
}
