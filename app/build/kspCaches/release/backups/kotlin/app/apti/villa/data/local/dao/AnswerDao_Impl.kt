package app.apti.villa.`data`.local.dao

import androidx.room.EntityDeleteOrUpdateAdapter
import androidx.room.EntityInsertAdapter
import androidx.room.RoomDatabase
import androidx.room.coroutines.createFlow
import androidx.room.util.getColumnIndexOrThrow
import androidx.room.util.performSuspending
import androidx.sqlite.SQLiteStatement
import app.apti.villa.`data`.local.entity.AnswerEntity
import javax.`annotation`.processing.Generated
import kotlin.Boolean
import kotlin.Int
import kotlin.Long
import kotlin.String
import kotlin.Suppress
import kotlin.Unit
import kotlin.collections.List
import kotlin.collections.MutableList
import kotlin.collections.mutableListOf
import kotlin.reflect.KClass
import kotlinx.coroutines.flow.Flow

@Generated(value = ["androidx.room.RoomProcessor"])
@Suppress(names = ["UNCHECKED_CAST", "DEPRECATION", "REDUNDANT_PROJECTION", "REMOVAL"])
public class AnswerDao_Impl(
  __db: RoomDatabase,
) : AnswerDao {
  private val __db: RoomDatabase

  private val __insertAdapterOfAnswerEntity: EntityInsertAdapter<AnswerEntity>

  private val __updateAdapterOfAnswerEntity: EntityDeleteOrUpdateAdapter<AnswerEntity>
  init {
    this.__db = __db
    this.__insertAdapterOfAnswerEntity = object : EntityInsertAdapter<AnswerEntity>() {
      protected override fun createQuery(): String =
          "INSERT OR REPLACE INTO `answer` (`id`,`attempt_id`,`question_id`,`position`,`status`,`selected_option_id`,`is_correct`,`time_spent_ms`,`first_seen_at`,`answered_at`,`first_submission_correct`,`hints_opened`,`solved_at_hint_level`,`revealed`) VALUES (nullif(?, 0),?,?,?,?,?,?,?,?,?,?,?,?,?)"

      protected override fun bind(statement: SQLiteStatement, entity: AnswerEntity) {
        statement.bindLong(1, entity.id)
        statement.bindLong(2, entity.attemptId)
        statement.bindLong(3, entity.questionId)
        statement.bindLong(4, entity.position.toLong())
        statement.bindText(5, entity.status)
        val _tmpSelectedOptionId: Long? = entity.selectedOptionId
        if (_tmpSelectedOptionId == null) {
          statement.bindNull(6)
        } else {
          statement.bindLong(6, _tmpSelectedOptionId)
        }
        val _tmpIsCorrect: Boolean? = entity.isCorrect
        val _tmp: Int? = _tmpIsCorrect?.let { if (it) 1 else 0 }
        if (_tmp == null) {
          statement.bindNull(7)
        } else {
          statement.bindLong(7, _tmp.toLong())
        }
        statement.bindLong(8, entity.timeSpentMs)
        val _tmpFirstSeenAt: Long? = entity.firstSeenAt
        if (_tmpFirstSeenAt == null) {
          statement.bindNull(9)
        } else {
          statement.bindLong(9, _tmpFirstSeenAt)
        }
        val _tmpAnsweredAt: Long? = entity.answeredAt
        if (_tmpAnsweredAt == null) {
          statement.bindNull(10)
        } else {
          statement.bindLong(10, _tmpAnsweredAt)
        }
        val _tmpFirstSubmissionCorrect: Boolean? = entity.firstSubmissionCorrect
        val _tmp_1: Int? = _tmpFirstSubmissionCorrect?.let { if (it) 1 else 0 }
        if (_tmp_1 == null) {
          statement.bindNull(11)
        } else {
          statement.bindLong(11, _tmp_1.toLong())
        }
        statement.bindLong(12, entity.hintsOpened.toLong())
        val _tmpSolvedAtHintLevel: Int? = entity.solvedAtHintLevel
        if (_tmpSolvedAtHintLevel == null) {
          statement.bindNull(13)
        } else {
          statement.bindLong(13, _tmpSolvedAtHintLevel.toLong())
        }
        val _tmp_2: Int = if (entity.revealed) 1 else 0
        statement.bindLong(14, _tmp_2.toLong())
      }
    }
    this.__updateAdapterOfAnswerEntity = object : EntityDeleteOrUpdateAdapter<AnswerEntity>() {
      protected override fun createQuery(): String =
          "UPDATE OR ABORT `answer` SET `id` = ?,`attempt_id` = ?,`question_id` = ?,`position` = ?,`status` = ?,`selected_option_id` = ?,`is_correct` = ?,`time_spent_ms` = ?,`first_seen_at` = ?,`answered_at` = ?,`first_submission_correct` = ?,`hints_opened` = ?,`solved_at_hint_level` = ?,`revealed` = ? WHERE `id` = ?"

      protected override fun bind(statement: SQLiteStatement, entity: AnswerEntity) {
        statement.bindLong(1, entity.id)
        statement.bindLong(2, entity.attemptId)
        statement.bindLong(3, entity.questionId)
        statement.bindLong(4, entity.position.toLong())
        statement.bindText(5, entity.status)
        val _tmpSelectedOptionId: Long? = entity.selectedOptionId
        if (_tmpSelectedOptionId == null) {
          statement.bindNull(6)
        } else {
          statement.bindLong(6, _tmpSelectedOptionId)
        }
        val _tmpIsCorrect: Boolean? = entity.isCorrect
        val _tmp: Int? = _tmpIsCorrect?.let { if (it) 1 else 0 }
        if (_tmp == null) {
          statement.bindNull(7)
        } else {
          statement.bindLong(7, _tmp.toLong())
        }
        statement.bindLong(8, entity.timeSpentMs)
        val _tmpFirstSeenAt: Long? = entity.firstSeenAt
        if (_tmpFirstSeenAt == null) {
          statement.bindNull(9)
        } else {
          statement.bindLong(9, _tmpFirstSeenAt)
        }
        val _tmpAnsweredAt: Long? = entity.answeredAt
        if (_tmpAnsweredAt == null) {
          statement.bindNull(10)
        } else {
          statement.bindLong(10, _tmpAnsweredAt)
        }
        val _tmpFirstSubmissionCorrect: Boolean? = entity.firstSubmissionCorrect
        val _tmp_1: Int? = _tmpFirstSubmissionCorrect?.let { if (it) 1 else 0 }
        if (_tmp_1 == null) {
          statement.bindNull(11)
        } else {
          statement.bindLong(11, _tmp_1.toLong())
        }
        statement.bindLong(12, entity.hintsOpened.toLong())
        val _tmpSolvedAtHintLevel: Int? = entity.solvedAtHintLevel
        if (_tmpSolvedAtHintLevel == null) {
          statement.bindNull(13)
        } else {
          statement.bindLong(13, _tmpSolvedAtHintLevel.toLong())
        }
        val _tmp_2: Int = if (entity.revealed) 1 else 0
        statement.bindLong(14, _tmp_2.toLong())
        statement.bindLong(15, entity.id)
      }
    }
  }

  public override suspend fun insert(answer: AnswerEntity): Long = performSuspending(__db, false,
      true) { _connection ->
    val _result: Long = __insertAdapterOfAnswerEntity.insertAndReturnId(_connection, answer)
    _result
  }

  public override suspend fun insertAll(answers: List<AnswerEntity>): Unit = performSuspending(__db,
      false, true) { _connection ->
    __insertAdapterOfAnswerEntity.insert(_connection, answers)
  }

  public override suspend fun update(answer: AnswerEntity): Unit = performSuspending(__db, false,
      true) { _connection ->
    __updateAdapterOfAnswerEntity.handle(_connection, answer)
  }

  public override suspend fun getAnswerById(id: Long): AnswerEntity? {
    val _sql: String = "SELECT * FROM answer WHERE id = ?"
    return performSuspending(__db, true, false) { _connection ->
      val _stmt: SQLiteStatement = _connection.prepare(_sql)
      try {
        var _argIndex: Int = 1
        _stmt.bindLong(_argIndex, id)
        val _columnIndexOfId: Int = getColumnIndexOrThrow(_stmt, "id")
        val _columnIndexOfAttemptId: Int = getColumnIndexOrThrow(_stmt, "attempt_id")
        val _columnIndexOfQuestionId: Int = getColumnIndexOrThrow(_stmt, "question_id")
        val _columnIndexOfPosition: Int = getColumnIndexOrThrow(_stmt, "position")
        val _columnIndexOfStatus: Int = getColumnIndexOrThrow(_stmt, "status")
        val _columnIndexOfSelectedOptionId: Int = getColumnIndexOrThrow(_stmt, "selected_option_id")
        val _columnIndexOfIsCorrect: Int = getColumnIndexOrThrow(_stmt, "is_correct")
        val _columnIndexOfTimeSpentMs: Int = getColumnIndexOrThrow(_stmt, "time_spent_ms")
        val _columnIndexOfFirstSeenAt: Int = getColumnIndexOrThrow(_stmt, "first_seen_at")
        val _columnIndexOfAnsweredAt: Int = getColumnIndexOrThrow(_stmt, "answered_at")
        val _columnIndexOfFirstSubmissionCorrect: Int = getColumnIndexOrThrow(_stmt,
            "first_submission_correct")
        val _columnIndexOfHintsOpened: Int = getColumnIndexOrThrow(_stmt, "hints_opened")
        val _columnIndexOfSolvedAtHintLevel: Int = getColumnIndexOrThrow(_stmt,
            "solved_at_hint_level")
        val _columnIndexOfRevealed: Int = getColumnIndexOrThrow(_stmt, "revealed")
        val _result: AnswerEntity?
        if (_stmt.step()) {
          val _tmpId: Long
          _tmpId = _stmt.getLong(_columnIndexOfId)
          val _tmpAttemptId: Long
          _tmpAttemptId = _stmt.getLong(_columnIndexOfAttemptId)
          val _tmpQuestionId: Long
          _tmpQuestionId = _stmt.getLong(_columnIndexOfQuestionId)
          val _tmpPosition: Int
          _tmpPosition = _stmt.getLong(_columnIndexOfPosition).toInt()
          val _tmpStatus: String
          _tmpStatus = _stmt.getText(_columnIndexOfStatus)
          val _tmpSelectedOptionId: Long?
          if (_stmt.isNull(_columnIndexOfSelectedOptionId)) {
            _tmpSelectedOptionId = null
          } else {
            _tmpSelectedOptionId = _stmt.getLong(_columnIndexOfSelectedOptionId)
          }
          val _tmpIsCorrect: Boolean?
          val _tmp: Int?
          if (_stmt.isNull(_columnIndexOfIsCorrect)) {
            _tmp = null
          } else {
            _tmp = _stmt.getLong(_columnIndexOfIsCorrect).toInt()
          }
          _tmpIsCorrect = _tmp?.let { it != 0 }
          val _tmpTimeSpentMs: Long
          _tmpTimeSpentMs = _stmt.getLong(_columnIndexOfTimeSpentMs)
          val _tmpFirstSeenAt: Long?
          if (_stmt.isNull(_columnIndexOfFirstSeenAt)) {
            _tmpFirstSeenAt = null
          } else {
            _tmpFirstSeenAt = _stmt.getLong(_columnIndexOfFirstSeenAt)
          }
          val _tmpAnsweredAt: Long?
          if (_stmt.isNull(_columnIndexOfAnsweredAt)) {
            _tmpAnsweredAt = null
          } else {
            _tmpAnsweredAt = _stmt.getLong(_columnIndexOfAnsweredAt)
          }
          val _tmpFirstSubmissionCorrect: Boolean?
          val _tmp_1: Int?
          if (_stmt.isNull(_columnIndexOfFirstSubmissionCorrect)) {
            _tmp_1 = null
          } else {
            _tmp_1 = _stmt.getLong(_columnIndexOfFirstSubmissionCorrect).toInt()
          }
          _tmpFirstSubmissionCorrect = _tmp_1?.let { it != 0 }
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
          _result =
              AnswerEntity(_tmpId,_tmpAttemptId,_tmpQuestionId,_tmpPosition,_tmpStatus,_tmpSelectedOptionId,_tmpIsCorrect,_tmpTimeSpentMs,_tmpFirstSeenAt,_tmpAnsweredAt,_tmpFirstSubmissionCorrect,_tmpHintsOpened,_tmpSolvedAtHintLevel,_tmpRevealed)
        } else {
          _result = null
        }
        _result
      } finally {
        _stmt.close()
      }
    }
  }

  public override fun getAnswersForAttempt(attemptId: Long): Flow<List<AnswerEntity>> {
    val _sql: String = "SELECT * FROM answer WHERE attempt_id = ? ORDER BY position ASC"
    return createFlow(__db, false, arrayOf("answer")) { _connection ->
      val _stmt: SQLiteStatement = _connection.prepare(_sql)
      try {
        var _argIndex: Int = 1
        _stmt.bindLong(_argIndex, attemptId)
        val _columnIndexOfId: Int = getColumnIndexOrThrow(_stmt, "id")
        val _columnIndexOfAttemptId: Int = getColumnIndexOrThrow(_stmt, "attempt_id")
        val _columnIndexOfQuestionId: Int = getColumnIndexOrThrow(_stmt, "question_id")
        val _columnIndexOfPosition: Int = getColumnIndexOrThrow(_stmt, "position")
        val _columnIndexOfStatus: Int = getColumnIndexOrThrow(_stmt, "status")
        val _columnIndexOfSelectedOptionId: Int = getColumnIndexOrThrow(_stmt, "selected_option_id")
        val _columnIndexOfIsCorrect: Int = getColumnIndexOrThrow(_stmt, "is_correct")
        val _columnIndexOfTimeSpentMs: Int = getColumnIndexOrThrow(_stmt, "time_spent_ms")
        val _columnIndexOfFirstSeenAt: Int = getColumnIndexOrThrow(_stmt, "first_seen_at")
        val _columnIndexOfAnsweredAt: Int = getColumnIndexOrThrow(_stmt, "answered_at")
        val _columnIndexOfFirstSubmissionCorrect: Int = getColumnIndexOrThrow(_stmt,
            "first_submission_correct")
        val _columnIndexOfHintsOpened: Int = getColumnIndexOrThrow(_stmt, "hints_opened")
        val _columnIndexOfSolvedAtHintLevel: Int = getColumnIndexOrThrow(_stmt,
            "solved_at_hint_level")
        val _columnIndexOfRevealed: Int = getColumnIndexOrThrow(_stmt, "revealed")
        val _result: MutableList<AnswerEntity> = mutableListOf()
        while (_stmt.step()) {
          val _item: AnswerEntity
          val _tmpId: Long
          _tmpId = _stmt.getLong(_columnIndexOfId)
          val _tmpAttemptId: Long
          _tmpAttemptId = _stmt.getLong(_columnIndexOfAttemptId)
          val _tmpQuestionId: Long
          _tmpQuestionId = _stmt.getLong(_columnIndexOfQuestionId)
          val _tmpPosition: Int
          _tmpPosition = _stmt.getLong(_columnIndexOfPosition).toInt()
          val _tmpStatus: String
          _tmpStatus = _stmt.getText(_columnIndexOfStatus)
          val _tmpSelectedOptionId: Long?
          if (_stmt.isNull(_columnIndexOfSelectedOptionId)) {
            _tmpSelectedOptionId = null
          } else {
            _tmpSelectedOptionId = _stmt.getLong(_columnIndexOfSelectedOptionId)
          }
          val _tmpIsCorrect: Boolean?
          val _tmp: Int?
          if (_stmt.isNull(_columnIndexOfIsCorrect)) {
            _tmp = null
          } else {
            _tmp = _stmt.getLong(_columnIndexOfIsCorrect).toInt()
          }
          _tmpIsCorrect = _tmp?.let { it != 0 }
          val _tmpTimeSpentMs: Long
          _tmpTimeSpentMs = _stmt.getLong(_columnIndexOfTimeSpentMs)
          val _tmpFirstSeenAt: Long?
          if (_stmt.isNull(_columnIndexOfFirstSeenAt)) {
            _tmpFirstSeenAt = null
          } else {
            _tmpFirstSeenAt = _stmt.getLong(_columnIndexOfFirstSeenAt)
          }
          val _tmpAnsweredAt: Long?
          if (_stmt.isNull(_columnIndexOfAnsweredAt)) {
            _tmpAnsweredAt = null
          } else {
            _tmpAnsweredAt = _stmt.getLong(_columnIndexOfAnsweredAt)
          }
          val _tmpFirstSubmissionCorrect: Boolean?
          val _tmp_1: Int?
          if (_stmt.isNull(_columnIndexOfFirstSubmissionCorrect)) {
            _tmp_1 = null
          } else {
            _tmp_1 = _stmt.getLong(_columnIndexOfFirstSubmissionCorrect).toInt()
          }
          _tmpFirstSubmissionCorrect = _tmp_1?.let { it != 0 }
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
          _item =
              AnswerEntity(_tmpId,_tmpAttemptId,_tmpQuestionId,_tmpPosition,_tmpStatus,_tmpSelectedOptionId,_tmpIsCorrect,_tmpTimeSpentMs,_tmpFirstSeenAt,_tmpAnsweredAt,_tmpFirstSubmissionCorrect,_tmpHintsOpened,_tmpSolvedAtHintLevel,_tmpRevealed)
          _result.add(_item)
        }
        _result
      } finally {
        _stmt.close()
      }
    }
  }

  public override suspend fun getAnswersForAttemptOnce(attemptId: Long): List<AnswerEntity> {
    val _sql: String = "SELECT * FROM answer WHERE attempt_id = ? ORDER BY position ASC"
    return performSuspending(__db, true, false) { _connection ->
      val _stmt: SQLiteStatement = _connection.prepare(_sql)
      try {
        var _argIndex: Int = 1
        _stmt.bindLong(_argIndex, attemptId)
        val _columnIndexOfId: Int = getColumnIndexOrThrow(_stmt, "id")
        val _columnIndexOfAttemptId: Int = getColumnIndexOrThrow(_stmt, "attempt_id")
        val _columnIndexOfQuestionId: Int = getColumnIndexOrThrow(_stmt, "question_id")
        val _columnIndexOfPosition: Int = getColumnIndexOrThrow(_stmt, "position")
        val _columnIndexOfStatus: Int = getColumnIndexOrThrow(_stmt, "status")
        val _columnIndexOfSelectedOptionId: Int = getColumnIndexOrThrow(_stmt, "selected_option_id")
        val _columnIndexOfIsCorrect: Int = getColumnIndexOrThrow(_stmt, "is_correct")
        val _columnIndexOfTimeSpentMs: Int = getColumnIndexOrThrow(_stmt, "time_spent_ms")
        val _columnIndexOfFirstSeenAt: Int = getColumnIndexOrThrow(_stmt, "first_seen_at")
        val _columnIndexOfAnsweredAt: Int = getColumnIndexOrThrow(_stmt, "answered_at")
        val _columnIndexOfFirstSubmissionCorrect: Int = getColumnIndexOrThrow(_stmt,
            "first_submission_correct")
        val _columnIndexOfHintsOpened: Int = getColumnIndexOrThrow(_stmt, "hints_opened")
        val _columnIndexOfSolvedAtHintLevel: Int = getColumnIndexOrThrow(_stmt,
            "solved_at_hint_level")
        val _columnIndexOfRevealed: Int = getColumnIndexOrThrow(_stmt, "revealed")
        val _result: MutableList<AnswerEntity> = mutableListOf()
        while (_stmt.step()) {
          val _item: AnswerEntity
          val _tmpId: Long
          _tmpId = _stmt.getLong(_columnIndexOfId)
          val _tmpAttemptId: Long
          _tmpAttemptId = _stmt.getLong(_columnIndexOfAttemptId)
          val _tmpQuestionId: Long
          _tmpQuestionId = _stmt.getLong(_columnIndexOfQuestionId)
          val _tmpPosition: Int
          _tmpPosition = _stmt.getLong(_columnIndexOfPosition).toInt()
          val _tmpStatus: String
          _tmpStatus = _stmt.getText(_columnIndexOfStatus)
          val _tmpSelectedOptionId: Long?
          if (_stmt.isNull(_columnIndexOfSelectedOptionId)) {
            _tmpSelectedOptionId = null
          } else {
            _tmpSelectedOptionId = _stmt.getLong(_columnIndexOfSelectedOptionId)
          }
          val _tmpIsCorrect: Boolean?
          val _tmp: Int?
          if (_stmt.isNull(_columnIndexOfIsCorrect)) {
            _tmp = null
          } else {
            _tmp = _stmt.getLong(_columnIndexOfIsCorrect).toInt()
          }
          _tmpIsCorrect = _tmp?.let { it != 0 }
          val _tmpTimeSpentMs: Long
          _tmpTimeSpentMs = _stmt.getLong(_columnIndexOfTimeSpentMs)
          val _tmpFirstSeenAt: Long?
          if (_stmt.isNull(_columnIndexOfFirstSeenAt)) {
            _tmpFirstSeenAt = null
          } else {
            _tmpFirstSeenAt = _stmt.getLong(_columnIndexOfFirstSeenAt)
          }
          val _tmpAnsweredAt: Long?
          if (_stmt.isNull(_columnIndexOfAnsweredAt)) {
            _tmpAnsweredAt = null
          } else {
            _tmpAnsweredAt = _stmt.getLong(_columnIndexOfAnsweredAt)
          }
          val _tmpFirstSubmissionCorrect: Boolean?
          val _tmp_1: Int?
          if (_stmt.isNull(_columnIndexOfFirstSubmissionCorrect)) {
            _tmp_1 = null
          } else {
            _tmp_1 = _stmt.getLong(_columnIndexOfFirstSubmissionCorrect).toInt()
          }
          _tmpFirstSubmissionCorrect = _tmp_1?.let { it != 0 }
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
          _item =
              AnswerEntity(_tmpId,_tmpAttemptId,_tmpQuestionId,_tmpPosition,_tmpStatus,_tmpSelectedOptionId,_tmpIsCorrect,_tmpTimeSpentMs,_tmpFirstSeenAt,_tmpAnsweredAt,_tmpFirstSubmissionCorrect,_tmpHintsOpened,_tmpSolvedAtHintLevel,_tmpRevealed)
          _result.add(_item)
        }
        _result
      } finally {
        _stmt.close()
      }
    }
  }

  public override suspend fun getAnswer(attemptId: Long, questionId: Long): AnswerEntity? {
    val _sql: String = "SELECT * FROM answer WHERE attempt_id = ? AND question_id = ? LIMIT 1"
    return performSuspending(__db, true, false) { _connection ->
      val _stmt: SQLiteStatement = _connection.prepare(_sql)
      try {
        var _argIndex: Int = 1
        _stmt.bindLong(_argIndex, attemptId)
        _argIndex = 2
        _stmt.bindLong(_argIndex, questionId)
        val _columnIndexOfId: Int = getColumnIndexOrThrow(_stmt, "id")
        val _columnIndexOfAttemptId: Int = getColumnIndexOrThrow(_stmt, "attempt_id")
        val _columnIndexOfQuestionId: Int = getColumnIndexOrThrow(_stmt, "question_id")
        val _columnIndexOfPosition: Int = getColumnIndexOrThrow(_stmt, "position")
        val _columnIndexOfStatus: Int = getColumnIndexOrThrow(_stmt, "status")
        val _columnIndexOfSelectedOptionId: Int = getColumnIndexOrThrow(_stmt, "selected_option_id")
        val _columnIndexOfIsCorrect: Int = getColumnIndexOrThrow(_stmt, "is_correct")
        val _columnIndexOfTimeSpentMs: Int = getColumnIndexOrThrow(_stmt, "time_spent_ms")
        val _columnIndexOfFirstSeenAt: Int = getColumnIndexOrThrow(_stmt, "first_seen_at")
        val _columnIndexOfAnsweredAt: Int = getColumnIndexOrThrow(_stmt, "answered_at")
        val _columnIndexOfFirstSubmissionCorrect: Int = getColumnIndexOrThrow(_stmt,
            "first_submission_correct")
        val _columnIndexOfHintsOpened: Int = getColumnIndexOrThrow(_stmt, "hints_opened")
        val _columnIndexOfSolvedAtHintLevel: Int = getColumnIndexOrThrow(_stmt,
            "solved_at_hint_level")
        val _columnIndexOfRevealed: Int = getColumnIndexOrThrow(_stmt, "revealed")
        val _result: AnswerEntity?
        if (_stmt.step()) {
          val _tmpId: Long
          _tmpId = _stmt.getLong(_columnIndexOfId)
          val _tmpAttemptId: Long
          _tmpAttemptId = _stmt.getLong(_columnIndexOfAttemptId)
          val _tmpQuestionId: Long
          _tmpQuestionId = _stmt.getLong(_columnIndexOfQuestionId)
          val _tmpPosition: Int
          _tmpPosition = _stmt.getLong(_columnIndexOfPosition).toInt()
          val _tmpStatus: String
          _tmpStatus = _stmt.getText(_columnIndexOfStatus)
          val _tmpSelectedOptionId: Long?
          if (_stmt.isNull(_columnIndexOfSelectedOptionId)) {
            _tmpSelectedOptionId = null
          } else {
            _tmpSelectedOptionId = _stmt.getLong(_columnIndexOfSelectedOptionId)
          }
          val _tmpIsCorrect: Boolean?
          val _tmp: Int?
          if (_stmt.isNull(_columnIndexOfIsCorrect)) {
            _tmp = null
          } else {
            _tmp = _stmt.getLong(_columnIndexOfIsCorrect).toInt()
          }
          _tmpIsCorrect = _tmp?.let { it != 0 }
          val _tmpTimeSpentMs: Long
          _tmpTimeSpentMs = _stmt.getLong(_columnIndexOfTimeSpentMs)
          val _tmpFirstSeenAt: Long?
          if (_stmt.isNull(_columnIndexOfFirstSeenAt)) {
            _tmpFirstSeenAt = null
          } else {
            _tmpFirstSeenAt = _stmt.getLong(_columnIndexOfFirstSeenAt)
          }
          val _tmpAnsweredAt: Long?
          if (_stmt.isNull(_columnIndexOfAnsweredAt)) {
            _tmpAnsweredAt = null
          } else {
            _tmpAnsweredAt = _stmt.getLong(_columnIndexOfAnsweredAt)
          }
          val _tmpFirstSubmissionCorrect: Boolean?
          val _tmp_1: Int?
          if (_stmt.isNull(_columnIndexOfFirstSubmissionCorrect)) {
            _tmp_1 = null
          } else {
            _tmp_1 = _stmt.getLong(_columnIndexOfFirstSubmissionCorrect).toInt()
          }
          _tmpFirstSubmissionCorrect = _tmp_1?.let { it != 0 }
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
          _result =
              AnswerEntity(_tmpId,_tmpAttemptId,_tmpQuestionId,_tmpPosition,_tmpStatus,_tmpSelectedOptionId,_tmpIsCorrect,_tmpTimeSpentMs,_tmpFirstSeenAt,_tmpAnsweredAt,_tmpFirstSubmissionCorrect,_tmpHintsOpened,_tmpSolvedAtHintLevel,_tmpRevealed)
        } else {
          _result = null
        }
        _result
      } finally {
        _stmt.close()
      }
    }
  }

  public override fun getAnswerFlow(attemptId: Long, questionId: Long): Flow<AnswerEntity?> {
    val _sql: String = "SELECT * FROM answer WHERE attempt_id = ? AND question_id = ? LIMIT 1"
    return createFlow(__db, false, arrayOf("answer")) { _connection ->
      val _stmt: SQLiteStatement = _connection.prepare(_sql)
      try {
        var _argIndex: Int = 1
        _stmt.bindLong(_argIndex, attemptId)
        _argIndex = 2
        _stmt.bindLong(_argIndex, questionId)
        val _columnIndexOfId: Int = getColumnIndexOrThrow(_stmt, "id")
        val _columnIndexOfAttemptId: Int = getColumnIndexOrThrow(_stmt, "attempt_id")
        val _columnIndexOfQuestionId: Int = getColumnIndexOrThrow(_stmt, "question_id")
        val _columnIndexOfPosition: Int = getColumnIndexOrThrow(_stmt, "position")
        val _columnIndexOfStatus: Int = getColumnIndexOrThrow(_stmt, "status")
        val _columnIndexOfSelectedOptionId: Int = getColumnIndexOrThrow(_stmt, "selected_option_id")
        val _columnIndexOfIsCorrect: Int = getColumnIndexOrThrow(_stmt, "is_correct")
        val _columnIndexOfTimeSpentMs: Int = getColumnIndexOrThrow(_stmt, "time_spent_ms")
        val _columnIndexOfFirstSeenAt: Int = getColumnIndexOrThrow(_stmt, "first_seen_at")
        val _columnIndexOfAnsweredAt: Int = getColumnIndexOrThrow(_stmt, "answered_at")
        val _columnIndexOfFirstSubmissionCorrect: Int = getColumnIndexOrThrow(_stmt,
            "first_submission_correct")
        val _columnIndexOfHintsOpened: Int = getColumnIndexOrThrow(_stmt, "hints_opened")
        val _columnIndexOfSolvedAtHintLevel: Int = getColumnIndexOrThrow(_stmt,
            "solved_at_hint_level")
        val _columnIndexOfRevealed: Int = getColumnIndexOrThrow(_stmt, "revealed")
        val _result: AnswerEntity?
        if (_stmt.step()) {
          val _tmpId: Long
          _tmpId = _stmt.getLong(_columnIndexOfId)
          val _tmpAttemptId: Long
          _tmpAttemptId = _stmt.getLong(_columnIndexOfAttemptId)
          val _tmpQuestionId: Long
          _tmpQuestionId = _stmt.getLong(_columnIndexOfQuestionId)
          val _tmpPosition: Int
          _tmpPosition = _stmt.getLong(_columnIndexOfPosition).toInt()
          val _tmpStatus: String
          _tmpStatus = _stmt.getText(_columnIndexOfStatus)
          val _tmpSelectedOptionId: Long?
          if (_stmt.isNull(_columnIndexOfSelectedOptionId)) {
            _tmpSelectedOptionId = null
          } else {
            _tmpSelectedOptionId = _stmt.getLong(_columnIndexOfSelectedOptionId)
          }
          val _tmpIsCorrect: Boolean?
          val _tmp: Int?
          if (_stmt.isNull(_columnIndexOfIsCorrect)) {
            _tmp = null
          } else {
            _tmp = _stmt.getLong(_columnIndexOfIsCorrect).toInt()
          }
          _tmpIsCorrect = _tmp?.let { it != 0 }
          val _tmpTimeSpentMs: Long
          _tmpTimeSpentMs = _stmt.getLong(_columnIndexOfTimeSpentMs)
          val _tmpFirstSeenAt: Long?
          if (_stmt.isNull(_columnIndexOfFirstSeenAt)) {
            _tmpFirstSeenAt = null
          } else {
            _tmpFirstSeenAt = _stmt.getLong(_columnIndexOfFirstSeenAt)
          }
          val _tmpAnsweredAt: Long?
          if (_stmt.isNull(_columnIndexOfAnsweredAt)) {
            _tmpAnsweredAt = null
          } else {
            _tmpAnsweredAt = _stmt.getLong(_columnIndexOfAnsweredAt)
          }
          val _tmpFirstSubmissionCorrect: Boolean?
          val _tmp_1: Int?
          if (_stmt.isNull(_columnIndexOfFirstSubmissionCorrect)) {
            _tmp_1 = null
          } else {
            _tmp_1 = _stmt.getLong(_columnIndexOfFirstSubmissionCorrect).toInt()
          }
          _tmpFirstSubmissionCorrect = _tmp_1?.let { it != 0 }
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
          _result =
              AnswerEntity(_tmpId,_tmpAttemptId,_tmpQuestionId,_tmpPosition,_tmpStatus,_tmpSelectedOptionId,_tmpIsCorrect,_tmpTimeSpentMs,_tmpFirstSeenAt,_tmpAnsweredAt,_tmpFirstSubmissionCorrect,_tmpHintsOpened,_tmpSolvedAtHintLevel,_tmpRevealed)
        } else {
          _result = null
        }
        _result
      } finally {
        _stmt.close()
      }
    }
  }

  public override suspend fun getAnswerAtPosition(attemptId: Long, position: Int): AnswerEntity? {
    val _sql: String = "SELECT * FROM answer WHERE attempt_id = ? AND position = ? LIMIT 1"
    return performSuspending(__db, true, false) { _connection ->
      val _stmt: SQLiteStatement = _connection.prepare(_sql)
      try {
        var _argIndex: Int = 1
        _stmt.bindLong(_argIndex, attemptId)
        _argIndex = 2
        _stmt.bindLong(_argIndex, position.toLong())
        val _columnIndexOfId: Int = getColumnIndexOrThrow(_stmt, "id")
        val _columnIndexOfAttemptId: Int = getColumnIndexOrThrow(_stmt, "attempt_id")
        val _columnIndexOfQuestionId: Int = getColumnIndexOrThrow(_stmt, "question_id")
        val _columnIndexOfPosition: Int = getColumnIndexOrThrow(_stmt, "position")
        val _columnIndexOfStatus: Int = getColumnIndexOrThrow(_stmt, "status")
        val _columnIndexOfSelectedOptionId: Int = getColumnIndexOrThrow(_stmt, "selected_option_id")
        val _columnIndexOfIsCorrect: Int = getColumnIndexOrThrow(_stmt, "is_correct")
        val _columnIndexOfTimeSpentMs: Int = getColumnIndexOrThrow(_stmt, "time_spent_ms")
        val _columnIndexOfFirstSeenAt: Int = getColumnIndexOrThrow(_stmt, "first_seen_at")
        val _columnIndexOfAnsweredAt: Int = getColumnIndexOrThrow(_stmt, "answered_at")
        val _columnIndexOfFirstSubmissionCorrect: Int = getColumnIndexOrThrow(_stmt,
            "first_submission_correct")
        val _columnIndexOfHintsOpened: Int = getColumnIndexOrThrow(_stmt, "hints_opened")
        val _columnIndexOfSolvedAtHintLevel: Int = getColumnIndexOrThrow(_stmt,
            "solved_at_hint_level")
        val _columnIndexOfRevealed: Int = getColumnIndexOrThrow(_stmt, "revealed")
        val _result: AnswerEntity?
        if (_stmt.step()) {
          val _tmpId: Long
          _tmpId = _stmt.getLong(_columnIndexOfId)
          val _tmpAttemptId: Long
          _tmpAttemptId = _stmt.getLong(_columnIndexOfAttemptId)
          val _tmpQuestionId: Long
          _tmpQuestionId = _stmt.getLong(_columnIndexOfQuestionId)
          val _tmpPosition: Int
          _tmpPosition = _stmt.getLong(_columnIndexOfPosition).toInt()
          val _tmpStatus: String
          _tmpStatus = _stmt.getText(_columnIndexOfStatus)
          val _tmpSelectedOptionId: Long?
          if (_stmt.isNull(_columnIndexOfSelectedOptionId)) {
            _tmpSelectedOptionId = null
          } else {
            _tmpSelectedOptionId = _stmt.getLong(_columnIndexOfSelectedOptionId)
          }
          val _tmpIsCorrect: Boolean?
          val _tmp: Int?
          if (_stmt.isNull(_columnIndexOfIsCorrect)) {
            _tmp = null
          } else {
            _tmp = _stmt.getLong(_columnIndexOfIsCorrect).toInt()
          }
          _tmpIsCorrect = _tmp?.let { it != 0 }
          val _tmpTimeSpentMs: Long
          _tmpTimeSpentMs = _stmt.getLong(_columnIndexOfTimeSpentMs)
          val _tmpFirstSeenAt: Long?
          if (_stmt.isNull(_columnIndexOfFirstSeenAt)) {
            _tmpFirstSeenAt = null
          } else {
            _tmpFirstSeenAt = _stmt.getLong(_columnIndexOfFirstSeenAt)
          }
          val _tmpAnsweredAt: Long?
          if (_stmt.isNull(_columnIndexOfAnsweredAt)) {
            _tmpAnsweredAt = null
          } else {
            _tmpAnsweredAt = _stmt.getLong(_columnIndexOfAnsweredAt)
          }
          val _tmpFirstSubmissionCorrect: Boolean?
          val _tmp_1: Int?
          if (_stmt.isNull(_columnIndexOfFirstSubmissionCorrect)) {
            _tmp_1 = null
          } else {
            _tmp_1 = _stmt.getLong(_columnIndexOfFirstSubmissionCorrect).toInt()
          }
          _tmpFirstSubmissionCorrect = _tmp_1?.let { it != 0 }
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
          _result =
              AnswerEntity(_tmpId,_tmpAttemptId,_tmpQuestionId,_tmpPosition,_tmpStatus,_tmpSelectedOptionId,_tmpIsCorrect,_tmpTimeSpentMs,_tmpFirstSeenAt,_tmpAnsweredAt,_tmpFirstSubmissionCorrect,_tmpHintsOpened,_tmpSolvedAtHintLevel,_tmpRevealed)
        } else {
          _result = null
        }
        _result
      } finally {
        _stmt.close()
      }
    }
  }

  public override suspend fun countCorrectAnswers(attemptId: Long): Int {
    val _sql: String = "SELECT COUNT(*) FROM answer WHERE attempt_id = ? AND is_correct = 1"
    return performSuspending(__db, true, false) { _connection ->
      val _stmt: SQLiteStatement = _connection.prepare(_sql)
      try {
        var _argIndex: Int = 1
        _stmt.bindLong(_argIndex, attemptId)
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

  public override suspend fun countAnswered(attemptId: Long): Int {
    val _sql: String = "SELECT COUNT(*) FROM answer WHERE attempt_id = ? AND status = 'ANSWERED'"
    return performSuspending(__db, true, false) { _connection ->
      val _stmt: SQLiteStatement = _connection.prepare(_sql)
      try {
        var _argIndex: Int = 1
        _stmt.bindLong(_argIndex, attemptId)
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

  public companion object {
    public fun getRequiredConverters(): List<KClass<*>> = emptyList()
  }
}
