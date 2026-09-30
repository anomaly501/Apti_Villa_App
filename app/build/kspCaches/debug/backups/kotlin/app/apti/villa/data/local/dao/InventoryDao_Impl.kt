package app.apti.villa.`data`.local.dao

import androidx.room.RoomDatabase
import androidx.room.coroutines.createFlow
import androidx.sqlite.SQLiteStatement
import javax.`annotation`.processing.Generated
import kotlin.Boolean
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
public class InventoryDao_Impl(
  __db: RoomDatabase,
) : InventoryDao {
  private val __db: RoomDatabase
  init {
    this.__db = __db
  }

  public override fun countTotalQuestions(): Flow<Int> {
    val _sql: String = "SELECT COUNT(*) FROM question"
    return createFlow(__db, false, arrayOf("question")) { _connection ->
      val _stmt: SQLiteStatement = _connection.prepare(_sql)
      try {
        val _result: Int
        if (_stmt.step()) {
          val _tmp: Int
          _tmp = _stmt.getLong(0).toInt()
          _result = _tmp
        } else {
          _result = 0
        }
        _result
      } finally {
        _stmt.close()
      }
    }
  }

  public override fun countAttemptedQuestions(): Flow<Int> {
    val _sql: String =
        "SELECT COUNT(DISTINCT question_id) FROM answer WHERE status IN ('ANSWERED', 'ANSWERED_MARKED')"
    return createFlow(__db, false, arrayOf("answer")) { _connection ->
      val _stmt: SQLiteStatement = _connection.prepare(_sql)
      try {
        val _result: Int
        if (_stmt.step()) {
          val _tmp: Int
          _tmp = _stmt.getLong(0).toInt()
          _result = _tmp
        } else {
          _result = 0
        }
        _result
      } finally {
        _stmt.close()
      }
    }
  }

  public override fun countCorrectQuestions(): Flow<Int> {
    val _sql: String = "SELECT COUNT(DISTINCT question_id) FROM answer WHERE is_correct = 1"
    return createFlow(__db, false, arrayOf("answer")) { _connection ->
      val _stmt: SQLiteStatement = _connection.prepare(_sql)
      try {
        val _result: Int
        if (_stmt.step()) {
          val _tmp: Int
          _tmp = _stmt.getLong(0).toInt()
          _result = _tmp
        } else {
          _result = 0
        }
        _result
      } finally {
        _stmt.close()
      }
    }
  }

  public override fun countWrongQuestions(): Flow<Int> {
    val _sql: String = "SELECT COUNT(DISTINCT question_id) FROM answer WHERE is_correct = 0"
    return createFlow(__db, false, arrayOf("answer")) { _connection ->
      val _stmt: SQLiteStatement = _connection.prepare(_sql)
      try {
        val _result: Int
        if (_stmt.step()) {
          val _tmp: Int
          _tmp = _stmt.getLong(0).toInt()
          _result = _tmp
        } else {
          _result = 0
        }
        _result
      } finally {
        _stmt.close()
      }
    }
  }

  public override fun countBookmarkedQuestions(): Flow<Int> {
    val _sql: String = """
        |
        |        SELECT COUNT(DISTINCT qt.question_id)
        |        FROM question_tag qt
        |        INNER JOIN tag t ON t.id = qt.tag_id
        |        WHERE t.name = 'Important' OR t.name = 'Bookmark'
        |    
        """.trimMargin()
    return createFlow(__db, false, arrayOf("question_tag", "tag")) { _connection ->
      val _stmt: SQLiteStatement = _connection.prepare(_sql)
      try {
        val _result: Int
        if (_stmt.step()) {
          val _tmp: Int
          _tmp = _stmt.getLong(0).toInt()
          _result = _tmp
        } else {
          _result = 0
        }
        _result
      } finally {
        _stmt.close()
      }
    }
  }

  public override fun countDifficultQuestions(): Flow<Int> {
    val _sql: String = """
        |
        |        SELECT COUNT(DISTINCT qt.question_id)
        |        FROM question_tag qt
        |        INNER JOIN tag t ON t.id = qt.tag_id
        |        WHERE t.name = 'Difficult'
        |    
        """.trimMargin()
    return createFlow(__db, false, arrayOf("question_tag", "tag")) { _connection ->
      val _stmt: SQLiteStatement = _connection.prepare(_sql)
      try {
        val _result: Int
        if (_stmt.step()) {
          val _tmp: Int
          _tmp = _stmt.getLong(0).toInt()
          _result = _tmp
        } else {
          _result = 0
        }
        _result
      } finally {
        _stmt.close()
      }
    }
  }

  public override fun countRevisionQuestions(): Flow<Int> {
    val _sql: String = "SELECT COUNT(*) FROM revision_item WHERE suspended = 0"
    return createFlow(__db, false, arrayOf("revision_item")) { _connection ->
      val _stmt: SQLiteStatement = _connection.prepare(_sql)
      try {
        val _result: Int
        if (_stmt.step()) {
          val _tmp: Int
          _tmp = _stmt.getLong(0).toInt()
          _result = _tmp
        } else {
          _result = 0
        }
        _result
      } finally {
        _stmt.close()
      }
    }
  }

  public override fun countTopicsStudied(): Flow<Int> {
    val _sql: String = """
        |
        |        SELECT COUNT(DISTINCT q.topic_id)
        |        FROM question q
        |        INNER JOIN answer a ON a.question_id = q.id
        |        WHERE q.topic_id IS NOT NULL AND a.status IN ('ANSWERED', 'ANSWERED_MARKED')
        |    
        """.trimMargin()
    return createFlow(__db, false, arrayOf("question", "answer")) { _connection ->
      val _stmt: SQLiteStatement = _connection.prepare(_sql)
      try {
        val _result: Int
        if (_stmt.step()) {
          val _tmp: Int
          _tmp = _stmt.getLong(0).toInt()
          _result = _tmp
        } else {
          _result = 0
        }
        _result
      } finally {
        _stmt.close()
      }
    }
  }

  public override fun countTotalTopics(): Flow<Int> {
    val _sql: String = "SELECT COUNT(*) FROM topic"
    return createFlow(__db, false, arrayOf("topic")) { _connection ->
      val _stmt: SQLiteStatement = _connection.prepare(_sql)
      try {
        val _result: Int
        if (_stmt.step()) {
          val _tmp: Int
          _tmp = _stmt.getLong(0).toInt()
          _result = _tmp
        } else {
          _result = 0
        }
        _result
      } finally {
        _stmt.close()
      }
    }
  }

  public override fun countSavedTricks(): Flow<Int> {
    val _sql: String = "SELECT COUNT(*) FROM saved_trick"
    return createFlow(__db, false, arrayOf("saved_trick")) { _connection ->
      val _stmt: SQLiteStatement = _connection.prepare(_sql)
      try {
        val _result: Int
        if (_stmt.step()) {
          val _tmp: Int
          _tmp = _stmt.getLong(0).toInt()
          _result = _tmp
        } else {
          _result = 0
        }
        _result
      } finally {
        _stmt.close()
      }
    }
  }

  public override fun countActiveMistakes(): Flow<Int> {
    val _sql: String = "SELECT COUNT(*) FROM mistake WHERE status = 'ACTIVE'"
    return createFlow(__db, false, arrayOf("mistake")) { _connection ->
      val _stmt: SQLiteStatement = _connection.prepare(_sql)
      try {
        val _result: Int
        if (_stmt.step()) {
          val _tmp: Int
          _tmp = _stmt.getLong(0).toInt()
          _result = _tmp
        } else {
          _result = 0
        }
        _result
      } finally {
        _stmt.close()
      }
    }
  }

  public override fun getTopMistakeTopics(): Flow<List<TopMistakeTopicDbRow>> {
    val _sql: String = """
        |
        |        SELECT q.topic_id AS topicId, t.name AS topicName, COUNT(m.question_id) AS mistakeCount
        |        FROM mistake m
        |        INNER JOIN question q ON q.id = m.question_id
        |        INNER JOIN topic t ON t.id = q.topic_id
        |        WHERE m.status = 'ACTIVE'
        |        GROUP BY q.topic_id
        |        ORDER BY mistakeCount DESC
        |        LIMIT 5
        |    
        """.trimMargin()
    return createFlow(__db, false, arrayOf("mistake", "question", "topic")) { _connection ->
      val _stmt: SQLiteStatement = _connection.prepare(_sql)
      try {
        val _columnIndexOfTopicId: Int = 0
        val _columnIndexOfTopicName: Int = 1
        val _columnIndexOfMistakeCount: Int = 2
        val _result: MutableList<TopMistakeTopicDbRow> = mutableListOf()
        while (_stmt.step()) {
          val _item: TopMistakeTopicDbRow
          val _tmpTopicId: Long
          _tmpTopicId = _stmt.getLong(_columnIndexOfTopicId)
          val _tmpTopicName: String
          _tmpTopicName = _stmt.getText(_columnIndexOfTopicName)
          val _tmpMistakeCount: Int
          _tmpMistakeCount = _stmt.getLong(_columnIndexOfMistakeCount).toInt()
          _item = TopMistakeTopicDbRow(_tmpTopicId,_tmpTopicName,_tmpMistakeCount)
          _result.add(_item)
        }
        _result
      } finally {
        _stmt.close()
      }
    }
  }

  public override fun getQuestionsWithDetails(
    subjectId: Long?,
    topicId: Long?,
    difficulty: String?,
  ): Flow<List<InventoryQuestionDbRow>> {
    val _sql: String = """
        |
        |        SELECT 
        |            q.id AS questionId,
        |            q.text AS text,
        |            q.difficulty AS difficulty,
        |            q.subject_id AS subjectId,
        |            s.name AS subjectName,
        |            q.topic_id AS topicId,
        |            t.name AS topicName,
        |            EXISTS(SELECT 1 FROM answer a WHERE a.question_id = q.id AND a.status IN ('ANSWERED', 'ANSWERED_MARKED')) AS isAttempted,
        |            (SELECT a.is_correct FROM answer a WHERE a.question_id = q.id AND a.status IN ('ANSWERED', 'ANSWERED_MARKED') ORDER BY a.answered_at DESC LIMIT 1) AS isCorrect,
        |            EXISTS(SELECT 1 FROM question_tag qt INNER JOIN tag tg ON tg.id = qt.tag_id WHERE qt.question_id = q.id AND (tg.name = 'Important' OR tg.name = 'Bookmark')) AS isBookmarked,
        |            EXISTS(SELECT 1 FROM question_tag qt INNER JOIN tag tg ON tg.id = qt.tag_id WHERE qt.question_id = q.id AND tg.name = 'Difficult') AS isDifficult,
        |            EXISTS(SELECT 1 FROM mistake m WHERE m.question_id = q.id AND m.status = 'ACTIVE') AS isMistake
        |        FROM question q
        |        LEFT JOIN subject s ON s.id = q.subject_id
        |        LEFT JOIN topic t ON t.id = q.topic_id
        |        WHERE (? IS NULL OR q.subject_id = ?)
        |          AND (? IS NULL OR q.topic_id = ?)
        |          AND (? IS NULL OR q.difficulty = ? COLLATE NOCASE)
        |        ORDER BY q.id ASC
        |        LIMIT 200
        |    
        """.trimMargin()
    return createFlow(__db, false, arrayOf("answer", "question_tag", "tag", "mistake", "question",
        "subject", "topic")) { _connection ->
      val _stmt: SQLiteStatement = _connection.prepare(_sql)
      try {
        var _argIndex: Int = 1
        if (subjectId == null) {
          _stmt.bindNull(_argIndex)
        } else {
          _stmt.bindLong(_argIndex, subjectId)
        }
        _argIndex = 2
        if (subjectId == null) {
          _stmt.bindNull(_argIndex)
        } else {
          _stmt.bindLong(_argIndex, subjectId)
        }
        _argIndex = 3
        if (topicId == null) {
          _stmt.bindNull(_argIndex)
        } else {
          _stmt.bindLong(_argIndex, topicId)
        }
        _argIndex = 4
        if (topicId == null) {
          _stmt.bindNull(_argIndex)
        } else {
          _stmt.bindLong(_argIndex, topicId)
        }
        _argIndex = 5
        if (difficulty == null) {
          _stmt.bindNull(_argIndex)
        } else {
          _stmt.bindText(_argIndex, difficulty)
        }
        _argIndex = 6
        if (difficulty == null) {
          _stmt.bindNull(_argIndex)
        } else {
          _stmt.bindText(_argIndex, difficulty)
        }
        val _columnIndexOfQuestionId: Int = 0
        val _columnIndexOfText: Int = 1
        val _columnIndexOfDifficulty: Int = 2
        val _columnIndexOfSubjectId: Int = 3
        val _columnIndexOfSubjectName: Int = 4
        val _columnIndexOfTopicId: Int = 5
        val _columnIndexOfTopicName: Int = 6
        val _columnIndexOfIsAttempted: Int = 7
        val _columnIndexOfIsCorrect: Int = 8
        val _columnIndexOfIsBookmarked: Int = 9
        val _columnIndexOfIsDifficult: Int = 10
        val _columnIndexOfIsMistake: Int = 11
        val _result: MutableList<InventoryQuestionDbRow> = mutableListOf()
        while (_stmt.step()) {
          val _item: InventoryQuestionDbRow
          val _tmpQuestionId: Long
          _tmpQuestionId = _stmt.getLong(_columnIndexOfQuestionId)
          val _tmpText: String
          _tmpText = _stmt.getText(_columnIndexOfText)
          val _tmpDifficulty: String?
          if (_stmt.isNull(_columnIndexOfDifficulty)) {
            _tmpDifficulty = null
          } else {
            _tmpDifficulty = _stmt.getText(_columnIndexOfDifficulty)
          }
          val _tmpSubjectId: Long?
          if (_stmt.isNull(_columnIndexOfSubjectId)) {
            _tmpSubjectId = null
          } else {
            _tmpSubjectId = _stmt.getLong(_columnIndexOfSubjectId)
          }
          val _tmpSubjectName: String?
          if (_stmt.isNull(_columnIndexOfSubjectName)) {
            _tmpSubjectName = null
          } else {
            _tmpSubjectName = _stmt.getText(_columnIndexOfSubjectName)
          }
          val _tmpTopicId: Long?
          if (_stmt.isNull(_columnIndexOfTopicId)) {
            _tmpTopicId = null
          } else {
            _tmpTopicId = _stmt.getLong(_columnIndexOfTopicId)
          }
          val _tmpTopicName: String?
          if (_stmt.isNull(_columnIndexOfTopicName)) {
            _tmpTopicName = null
          } else {
            _tmpTopicName = _stmt.getText(_columnIndexOfTopicName)
          }
          val _tmpIsAttempted: Boolean
          val _tmp: Int
          _tmp = _stmt.getLong(_columnIndexOfIsAttempted).toInt()
          _tmpIsAttempted = _tmp != 0
          val _tmpIsCorrect: Boolean?
          val _tmp_1: Int?
          if (_stmt.isNull(_columnIndexOfIsCorrect)) {
            _tmp_1 = null
          } else {
            _tmp_1 = _stmt.getLong(_columnIndexOfIsCorrect).toInt()
          }
          _tmpIsCorrect = _tmp_1?.let { it != 0 }
          val _tmpIsBookmarked: Boolean
          val _tmp_2: Int
          _tmp_2 = _stmt.getLong(_columnIndexOfIsBookmarked).toInt()
          _tmpIsBookmarked = _tmp_2 != 0
          val _tmpIsDifficult: Boolean
          val _tmp_3: Int
          _tmp_3 = _stmt.getLong(_columnIndexOfIsDifficult).toInt()
          _tmpIsDifficult = _tmp_3 != 0
          val _tmpIsMistake: Boolean
          val _tmp_4: Int
          _tmp_4 = _stmt.getLong(_columnIndexOfIsMistake).toInt()
          _tmpIsMistake = _tmp_4 != 0
          _item =
              InventoryQuestionDbRow(_tmpQuestionId,_tmpText,_tmpDifficulty,_tmpSubjectId,_tmpSubjectName,_tmpTopicId,_tmpTopicName,_tmpIsAttempted,_tmpIsCorrect,_tmpIsBookmarked,_tmpIsDifficult,_tmpIsMistake)
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
