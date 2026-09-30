package app.apti.villa.`data`.local.dao

import androidx.room.EntityInsertAdapter
import androidx.room.RoomDatabase
import androidx.room.coroutines.createFlow
import androidx.room.util.getColumnIndexOrThrow
import androidx.room.util.performSuspending
import androidx.sqlite.SQLiteStatement
import app.apti.villa.`data`.local.entity.AnswerSubmissionEntity
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
public class AnswerSubmissionDao_Impl(
  __db: RoomDatabase,
) : AnswerSubmissionDao {
  private val __db: RoomDatabase

  private val __insertAdapterOfAnswerSubmissionEntity: EntityInsertAdapter<AnswerSubmissionEntity>
  init {
    this.__db = __db
    this.__insertAdapterOfAnswerSubmissionEntity = object :
        EntityInsertAdapter<AnswerSubmissionEntity>() {
      protected override fun createQuery(): String =
          "INSERT OR REPLACE INTO `answer_submission` (`id`,`answer_id`,`option_id`,`submitted_at`,`hints_open_at_submit`,`ms_since_first_seen`,`is_correct`) VALUES (nullif(?, 0),?,?,?,?,?,?)"

      protected override fun bind(statement: SQLiteStatement, entity: AnswerSubmissionEntity) {
        statement.bindLong(1, entity.id)
        statement.bindLong(2, entity.answerId)
        statement.bindLong(3, entity.optionId)
        statement.bindLong(4, entity.submittedAt)
        statement.bindLong(5, entity.hintsOpenAtSubmit.toLong())
        statement.bindLong(6, entity.msSinceFirstSeen)
        val _tmp: Int = if (entity.isCorrect) 1 else 0
        statement.bindLong(7, _tmp.toLong())
      }
    }
  }

  public override suspend fun insert(submission: AnswerSubmissionEntity): Long =
      performSuspending(__db, false, true) { _connection ->
    val _result: Long = __insertAdapterOfAnswerSubmissionEntity.insertAndReturnId(_connection,
        submission)
    _result
  }

  public override fun getSubmissionsForAnswer(answerId: Long): Flow<List<AnswerSubmissionEntity>> {
    val _sql: String =
        "SELECT * FROM answer_submission WHERE answer_id = ? ORDER BY submitted_at ASC"
    return createFlow(__db, false, arrayOf("answer_submission")) { _connection ->
      val _stmt: SQLiteStatement = _connection.prepare(_sql)
      try {
        var _argIndex: Int = 1
        _stmt.bindLong(_argIndex, answerId)
        val _columnIndexOfId: Int = getColumnIndexOrThrow(_stmt, "id")
        val _columnIndexOfAnswerId: Int = getColumnIndexOrThrow(_stmt, "answer_id")
        val _columnIndexOfOptionId: Int = getColumnIndexOrThrow(_stmt, "option_id")
        val _columnIndexOfSubmittedAt: Int = getColumnIndexOrThrow(_stmt, "submitted_at")
        val _columnIndexOfHintsOpenAtSubmit: Int = getColumnIndexOrThrow(_stmt,
            "hints_open_at_submit")
        val _columnIndexOfMsSinceFirstSeen: Int = getColumnIndexOrThrow(_stmt,
            "ms_since_first_seen")
        val _columnIndexOfIsCorrect: Int = getColumnIndexOrThrow(_stmt, "is_correct")
        val _result: MutableList<AnswerSubmissionEntity> = mutableListOf()
        while (_stmt.step()) {
          val _item: AnswerSubmissionEntity
          val _tmpId: Long
          _tmpId = _stmt.getLong(_columnIndexOfId)
          val _tmpAnswerId: Long
          _tmpAnswerId = _stmt.getLong(_columnIndexOfAnswerId)
          val _tmpOptionId: Long
          _tmpOptionId = _stmt.getLong(_columnIndexOfOptionId)
          val _tmpSubmittedAt: Long
          _tmpSubmittedAt = _stmt.getLong(_columnIndexOfSubmittedAt)
          val _tmpHintsOpenAtSubmit: Int
          _tmpHintsOpenAtSubmit = _stmt.getLong(_columnIndexOfHintsOpenAtSubmit).toInt()
          val _tmpMsSinceFirstSeen: Long
          _tmpMsSinceFirstSeen = _stmt.getLong(_columnIndexOfMsSinceFirstSeen)
          val _tmpIsCorrect: Boolean
          val _tmp: Int
          _tmp = _stmt.getLong(_columnIndexOfIsCorrect).toInt()
          _tmpIsCorrect = _tmp != 0
          _item =
              AnswerSubmissionEntity(_tmpId,_tmpAnswerId,_tmpOptionId,_tmpSubmittedAt,_tmpHintsOpenAtSubmit,_tmpMsSinceFirstSeen,_tmpIsCorrect)
          _result.add(_item)
        }
        _result
      } finally {
        _stmt.close()
      }
    }
  }

  public override suspend fun getSubmissionsForAnswerOnce(answerId: Long):
      List<AnswerSubmissionEntity> {
    val _sql: String =
        "SELECT * FROM answer_submission WHERE answer_id = ? ORDER BY submitted_at ASC"
    return performSuspending(__db, true, false) { _connection ->
      val _stmt: SQLiteStatement = _connection.prepare(_sql)
      try {
        var _argIndex: Int = 1
        _stmt.bindLong(_argIndex, answerId)
        val _columnIndexOfId: Int = getColumnIndexOrThrow(_stmt, "id")
        val _columnIndexOfAnswerId: Int = getColumnIndexOrThrow(_stmt, "answer_id")
        val _columnIndexOfOptionId: Int = getColumnIndexOrThrow(_stmt, "option_id")
        val _columnIndexOfSubmittedAt: Int = getColumnIndexOrThrow(_stmt, "submitted_at")
        val _columnIndexOfHintsOpenAtSubmit: Int = getColumnIndexOrThrow(_stmt,
            "hints_open_at_submit")
        val _columnIndexOfMsSinceFirstSeen: Int = getColumnIndexOrThrow(_stmt,
            "ms_since_first_seen")
        val _columnIndexOfIsCorrect: Int = getColumnIndexOrThrow(_stmt, "is_correct")
        val _result: MutableList<AnswerSubmissionEntity> = mutableListOf()
        while (_stmt.step()) {
          val _item: AnswerSubmissionEntity
          val _tmpId: Long
          _tmpId = _stmt.getLong(_columnIndexOfId)
          val _tmpAnswerId: Long
          _tmpAnswerId = _stmt.getLong(_columnIndexOfAnswerId)
          val _tmpOptionId: Long
          _tmpOptionId = _stmt.getLong(_columnIndexOfOptionId)
          val _tmpSubmittedAt: Long
          _tmpSubmittedAt = _stmt.getLong(_columnIndexOfSubmittedAt)
          val _tmpHintsOpenAtSubmit: Int
          _tmpHintsOpenAtSubmit = _stmt.getLong(_columnIndexOfHintsOpenAtSubmit).toInt()
          val _tmpMsSinceFirstSeen: Long
          _tmpMsSinceFirstSeen = _stmt.getLong(_columnIndexOfMsSinceFirstSeen)
          val _tmpIsCorrect: Boolean
          val _tmp: Int
          _tmp = _stmt.getLong(_columnIndexOfIsCorrect).toInt()
          _tmpIsCorrect = _tmp != 0
          _item =
              AnswerSubmissionEntity(_tmpId,_tmpAnswerId,_tmpOptionId,_tmpSubmittedAt,_tmpHintsOpenAtSubmit,_tmpMsSinceFirstSeen,_tmpIsCorrect)
          _result.add(_item)
        }
        _result
      } finally {
        _stmt.close()
      }
    }
  }

  public override suspend fun countSubmissionsForAnswer(answerId: Long): Int {
    val _sql: String = "SELECT COUNT(*) FROM answer_submission WHERE answer_id = ?"
    return performSuspending(__db, true, false) { _connection ->
      val _stmt: SQLiteStatement = _connection.prepare(_sql)
      try {
        var _argIndex: Int = 1
        _stmt.bindLong(_argIndex, answerId)
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
