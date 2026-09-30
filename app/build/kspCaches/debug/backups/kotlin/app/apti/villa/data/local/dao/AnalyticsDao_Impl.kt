package app.apti.villa.`data`.local.dao

import androidx.room.RoomDatabase
import androidx.room.coroutines.createFlow
import androidx.room.util.performSuspending
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
public class AnalyticsDao_Impl(
  __db: RoomDatabase,
) : AnalyticsDao {
  private val __db: RoomDatabase
  init {
    this.__db = __db
  }

  public override fun getAnswerPerformanceRecords(): Flow<List<AnswerPerformanceRecord>> {
    val _sql: String = """
        |
        |        SELECT 
        |            a.id AS answerId,
        |            a.attempt_id AS attemptId,
        |            a.question_id AS questionId,
        |            a.status AS status,
        |            a.is_correct AS isCorrect,
        |            a.first_submission_correct AS firstSubmissionCorrect,
        |            a.time_spent_ms AS timeSpentMs,
        |            a.hints_opened AS hintsOpened,
        |            a.solved_at_hint_level AS solvedAtHintLevel,
        |            a.revealed AS revealed,
        |            a.answered_at AS answeredAt,
        |            att.is_retry AS isRetry,
        |            att.mode AS mode,
        |            q.subject_id AS subjectId,
        |            q.topic_id AS topicId,
        |            q.difficulty AS difficulty
        |        FROM answer a
        |        INNER JOIN attempt att ON att.id = a.attempt_id
        |        INNER JOIN question q ON q.id = a.question_id
        |        WHERE a.status IN ('ANSWERED', 'ANSWERED_MARKED')
        |        ORDER BY a.answered_at DESC
        |    
        """.trimMargin()
    return createFlow(__db, false, arrayOf("answer", "attempt", "question")) { _connection ->
      val _stmt: SQLiteStatement = _connection.prepare(_sql)
      try {
        val _columnIndexOfAnswerId: Int = 0
        val _columnIndexOfAttemptId: Int = 1
        val _columnIndexOfQuestionId: Int = 2
        val _columnIndexOfStatus: Int = 3
        val _columnIndexOfIsCorrect: Int = 4
        val _columnIndexOfFirstSubmissionCorrect: Int = 5
        val _columnIndexOfTimeSpentMs: Int = 6
        val _columnIndexOfHintsOpened: Int = 7
        val _columnIndexOfSolvedAtHintLevel: Int = 8
        val _columnIndexOfRevealed: Int = 9
        val _columnIndexOfAnsweredAt: Int = 10
        val _columnIndexOfIsRetry: Int = 11
        val _columnIndexOfMode: Int = 12
        val _columnIndexOfSubjectId: Int = 13
        val _columnIndexOfTopicId: Int = 14
        val _columnIndexOfDifficulty: Int = 15
        val _result: MutableList<AnswerPerformanceRecord> = mutableListOf()
        while (_stmt.step()) {
          val _item: AnswerPerformanceRecord
          val _tmpAnswerId: Long
          _tmpAnswerId = _stmt.getLong(_columnIndexOfAnswerId)
          val _tmpAttemptId: Long
          _tmpAttemptId = _stmt.getLong(_columnIndexOfAttemptId)
          val _tmpQuestionId: Long
          _tmpQuestionId = _stmt.getLong(_columnIndexOfQuestionId)
          val _tmpStatus: String
          _tmpStatus = _stmt.getText(_columnIndexOfStatus)
          val _tmpIsCorrect: Boolean?
          val _tmp: Int?
          if (_stmt.isNull(_columnIndexOfIsCorrect)) {
            _tmp = null
          } else {
            _tmp = _stmt.getLong(_columnIndexOfIsCorrect).toInt()
          }
          _tmpIsCorrect = _tmp?.let { it != 0 }
          val _tmpFirstSubmissionCorrect: Boolean?
          val _tmp_1: Int?
          if (_stmt.isNull(_columnIndexOfFirstSubmissionCorrect)) {
            _tmp_1 = null
          } else {
            _tmp_1 = _stmt.getLong(_columnIndexOfFirstSubmissionCorrect).toInt()
          }
          _tmpFirstSubmissionCorrect = _tmp_1?.let { it != 0 }
          val _tmpTimeSpentMs: Long
          _tmpTimeSpentMs = _stmt.getLong(_columnIndexOfTimeSpentMs)
          val _tmpHintsOpened: Int
          _tmpHintsOpened = _stmt.getLong(_columnIndexOfHintsOpened).toInt()
          val _tmpSolvedAtHintLevel: Int?
          if (_stmt.isNull(_columnIndexOfSolvedAtHintLevel)) {
            _tmpSolvedAtHintLevel = null
          } else {
            _tmpSolvedAtHintLevel = _stmt.getLong(_columnIndexOfSolvedAtHintLevel).toInt()
          }
          val _tmpRevealed: Boolean
          val _tmp_2: Int
          _tmp_2 = _stmt.getLong(_columnIndexOfRevealed).toInt()
          _tmpRevealed = _tmp_2 != 0
          val _tmpAnsweredAt: Long?
          if (_stmt.isNull(_columnIndexOfAnsweredAt)) {
            _tmpAnsweredAt = null
          } else {
            _tmpAnsweredAt = _stmt.getLong(_columnIndexOfAnsweredAt)
          }
          val _tmpIsRetry: Boolean
          val _tmp_3: Int
          _tmp_3 = _stmt.getLong(_columnIndexOfIsRetry).toInt()
          _tmpIsRetry = _tmp_3 != 0
          val _tmpMode: String
          _tmpMode = _stmt.getText(_columnIndexOfMode)
          val _tmpSubjectId: Long?
          if (_stmt.isNull(_columnIndexOfSubjectId)) {
            _tmpSubjectId = null
          } else {
            _tmpSubjectId = _stmt.getLong(_columnIndexOfSubjectId)
          }
          val _tmpTopicId: Long?
          if (_stmt.isNull(_columnIndexOfTopicId)) {
            _tmpTopicId = null
          } else {
            _tmpTopicId = _stmt.getLong(_columnIndexOfTopicId)
          }
          val _tmpDifficulty: String?
          if (_stmt.isNull(_columnIndexOfDifficulty)) {
            _tmpDifficulty = null
          } else {
            _tmpDifficulty = _stmt.getText(_columnIndexOfDifficulty)
          }
          _item =
              AnswerPerformanceRecord(_tmpAnswerId,_tmpAttemptId,_tmpQuestionId,_tmpStatus,_tmpIsCorrect,_tmpFirstSubmissionCorrect,_tmpTimeSpentMs,_tmpHintsOpened,_tmpSolvedAtHintLevel,_tmpRevealed,_tmpAnsweredAt,_tmpIsRetry,_tmpMode,_tmpSubjectId,_tmpTopicId,_tmpDifficulty)
          _result.add(_item)
        }
        _result
      } finally {
        _stmt.close()
      }
    }
  }

  public override suspend fun getAnswerPerformanceRecordsOnce(): List<AnswerPerformanceRecord> {
    val _sql: String = """
        |
        |        SELECT 
        |            a.id AS answerId,
        |            a.attempt_id AS attemptId,
        |            a.question_id AS questionId,
        |            a.status AS status,
        |            a.is_correct AS isCorrect,
        |            a.first_submission_correct AS firstSubmissionCorrect,
        |            a.time_spent_ms AS timeSpentMs,
        |            a.hints_opened AS hintsOpened,
        |            a.solved_at_hint_level AS solvedAtHintLevel,
        |            a.revealed AS revealed,
        |            a.answered_at AS answeredAt,
        |            att.is_retry AS isRetry,
        |            att.mode AS mode,
        |            q.subject_id AS subjectId,
        |            q.topic_id AS topicId,
        |            q.difficulty AS difficulty
        |        FROM answer a
        |        INNER JOIN attempt att ON att.id = a.attempt_id
        |        INNER JOIN question q ON q.id = a.question_id
        |        WHERE a.status IN ('ANSWERED', 'ANSWERED_MARKED')
        |        ORDER BY a.answered_at DESC
        |    
        """.trimMargin()
    return performSuspending(__db, true, false) { _connection ->
      val _stmt: SQLiteStatement = _connection.prepare(_sql)
      try {
        val _columnIndexOfAnswerId: Int = 0
        val _columnIndexOfAttemptId: Int = 1
        val _columnIndexOfQuestionId: Int = 2
        val _columnIndexOfStatus: Int = 3
        val _columnIndexOfIsCorrect: Int = 4
        val _columnIndexOfFirstSubmissionCorrect: Int = 5
        val _columnIndexOfTimeSpentMs: Int = 6
        val _columnIndexOfHintsOpened: Int = 7
        val _columnIndexOfSolvedAtHintLevel: Int = 8
        val _columnIndexOfRevealed: Int = 9
        val _columnIndexOfAnsweredAt: Int = 10
        val _columnIndexOfIsRetry: Int = 11
        val _columnIndexOfMode: Int = 12
        val _columnIndexOfSubjectId: Int = 13
        val _columnIndexOfTopicId: Int = 14
        val _columnIndexOfDifficulty: Int = 15
        val _result: MutableList<AnswerPerformanceRecord> = mutableListOf()
        while (_stmt.step()) {
          val _item: AnswerPerformanceRecord
          val _tmpAnswerId: Long
          _tmpAnswerId = _stmt.getLong(_columnIndexOfAnswerId)
          val _tmpAttemptId: Long
          _tmpAttemptId = _stmt.getLong(_columnIndexOfAttemptId)
          val _tmpQuestionId: Long
          _tmpQuestionId = _stmt.getLong(_columnIndexOfQuestionId)
          val _tmpStatus: String
          _tmpStatus = _stmt.getText(_columnIndexOfStatus)
          val _tmpIsCorrect: Boolean?
          val _tmp: Int?
          if (_stmt.isNull(_columnIndexOfIsCorrect)) {
            _tmp = null
          } else {
            _tmp = _stmt.getLong(_columnIndexOfIsCorrect).toInt()
          }
          _tmpIsCorrect = _tmp?.let { it != 0 }
          val _tmpFirstSubmissionCorrect: Boolean?
          val _tmp_1: Int?
          if (_stmt.isNull(_columnIndexOfFirstSubmissionCorrect)) {
            _tmp_1 = null
          } else {
            _tmp_1 = _stmt.getLong(_columnIndexOfFirstSubmissionCorrect).toInt()
          }
          _tmpFirstSubmissionCorrect = _tmp_1?.let { it != 0 }
          val _tmpTimeSpentMs: Long
          _tmpTimeSpentMs = _stmt.getLong(_columnIndexOfTimeSpentMs)
          val _tmpHintsOpened: Int
          _tmpHintsOpened = _stmt.getLong(_columnIndexOfHintsOpened).toInt()
          val _tmpSolvedAtHintLevel: Int?
          if (_stmt.isNull(_columnIndexOfSolvedAtHintLevel)) {
            _tmpSolvedAtHintLevel = null
          } else {
            _tmpSolvedAtHintLevel = _stmt.getLong(_columnIndexOfSolvedAtHintLevel).toInt()
          }
          val _tmpRevealed: Boolean
          val _tmp_2: Int
          _tmp_2 = _stmt.getLong(_columnIndexOfRevealed).toInt()
          _tmpRevealed = _tmp_2 != 0
          val _tmpAnsweredAt: Long?
          if (_stmt.isNull(_columnIndexOfAnsweredAt)) {
            _tmpAnsweredAt = null
          } else {
            _tmpAnsweredAt = _stmt.getLong(_columnIndexOfAnsweredAt)
          }
          val _tmpIsRetry: Boolean
          val _tmp_3: Int
          _tmp_3 = _stmt.getLong(_columnIndexOfIsRetry).toInt()
          _tmpIsRetry = _tmp_3 != 0
          val _tmpMode: String
          _tmpMode = _stmt.getText(_columnIndexOfMode)
          val _tmpSubjectId: Long?
          if (_stmt.isNull(_columnIndexOfSubjectId)) {
            _tmpSubjectId = null
          } else {
            _tmpSubjectId = _stmt.getLong(_columnIndexOfSubjectId)
          }
          val _tmpTopicId: Long?
          if (_stmt.isNull(_columnIndexOfTopicId)) {
            _tmpTopicId = null
          } else {
            _tmpTopicId = _stmt.getLong(_columnIndexOfTopicId)
          }
          val _tmpDifficulty: String?
          if (_stmt.isNull(_columnIndexOfDifficulty)) {
            _tmpDifficulty = null
          } else {
            _tmpDifficulty = _stmt.getText(_columnIndexOfDifficulty)
          }
          _item =
              AnswerPerformanceRecord(_tmpAnswerId,_tmpAttemptId,_tmpQuestionId,_tmpStatus,_tmpIsCorrect,_tmpFirstSubmissionCorrect,_tmpTimeSpentMs,_tmpHintsOpened,_tmpSolvedAtHintLevel,_tmpRevealed,_tmpAnsweredAt,_tmpIsRetry,_tmpMode,_tmpSubjectId,_tmpTopicId,_tmpDifficulty)
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
