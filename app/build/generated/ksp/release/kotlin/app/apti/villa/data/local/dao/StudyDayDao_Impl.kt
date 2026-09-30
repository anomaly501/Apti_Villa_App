package app.apti.villa.`data`.local.dao

import androidx.room.EntityInsertAdapter
import androidx.room.RoomDatabase
import androidx.room.coroutines.createFlow
import androidx.room.util.getColumnIndexOrThrow
import androidx.room.util.performSuspending
import androidx.sqlite.SQLiteStatement
import app.apti.villa.`data`.local.entity.StudyDayEntity
import javax.`annotation`.processing.Generated
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
public class StudyDayDao_Impl(
  __db: RoomDatabase,
) : StudyDayDao {
  private val __db: RoomDatabase

  private val __insertAdapterOfStudyDayEntity: EntityInsertAdapter<StudyDayEntity>
  init {
    this.__db = __db
    this.__insertAdapterOfStudyDayEntity = object : EntityInsertAdapter<StudyDayEntity>() {
      protected override fun createQuery(): String =
          "INSERT OR REPLACE INTO `study_day` (`date`,`questions_attempted`,`correct`,`active_ms`) VALUES (?,?,?,?)"

      protected override fun bind(statement: SQLiteStatement, entity: StudyDayEntity) {
        statement.bindText(1, entity.date)
        statement.bindLong(2, entity.questionsAttempted.toLong())
        statement.bindLong(3, entity.correct.toLong())
        statement.bindLong(4, entity.activeMs)
      }
    }
  }

  public override suspend fun upsert(studyDay: StudyDayEntity): Unit = performSuspending(__db,
      false, true) { _connection ->
    __insertAdapterOfStudyDayEntity.insert(_connection, studyDay)
  }

  public override suspend fun getStudyDay(date: String): StudyDayEntity? {
    val _sql: String = "SELECT * FROM study_day WHERE date = ? LIMIT 1"
    return performSuspending(__db, true, false) { _connection ->
      val _stmt: SQLiteStatement = _connection.prepare(_sql)
      try {
        var _argIndex: Int = 1
        _stmt.bindText(_argIndex, date)
        val _columnIndexOfDate: Int = getColumnIndexOrThrow(_stmt, "date")
        val _columnIndexOfQuestionsAttempted: Int = getColumnIndexOrThrow(_stmt,
            "questions_attempted")
        val _columnIndexOfCorrect: Int = getColumnIndexOrThrow(_stmt, "correct")
        val _columnIndexOfActiveMs: Int = getColumnIndexOrThrow(_stmt, "active_ms")
        val _result: StudyDayEntity?
        if (_stmt.step()) {
          val _tmpDate: String
          _tmpDate = _stmt.getText(_columnIndexOfDate)
          val _tmpQuestionsAttempted: Int
          _tmpQuestionsAttempted = _stmt.getLong(_columnIndexOfQuestionsAttempted).toInt()
          val _tmpCorrect: Int
          _tmpCorrect = _stmt.getLong(_columnIndexOfCorrect).toInt()
          val _tmpActiveMs: Long
          _tmpActiveMs = _stmt.getLong(_columnIndexOfActiveMs)
          _result = StudyDayEntity(_tmpDate,_tmpQuestionsAttempted,_tmpCorrect,_tmpActiveMs)
        } else {
          _result = null
        }
        _result
      } finally {
        _stmt.close()
      }
    }
  }

  public override fun getAllStudyDays(): Flow<List<StudyDayEntity>> {
    val _sql: String = "SELECT * FROM study_day ORDER BY date DESC"
    return createFlow(__db, false, arrayOf("study_day")) { _connection ->
      val _stmt: SQLiteStatement = _connection.prepare(_sql)
      try {
        val _columnIndexOfDate: Int = getColumnIndexOrThrow(_stmt, "date")
        val _columnIndexOfQuestionsAttempted: Int = getColumnIndexOrThrow(_stmt,
            "questions_attempted")
        val _columnIndexOfCorrect: Int = getColumnIndexOrThrow(_stmt, "correct")
        val _columnIndexOfActiveMs: Int = getColumnIndexOrThrow(_stmt, "active_ms")
        val _result: MutableList<StudyDayEntity> = mutableListOf()
        while (_stmt.step()) {
          val _item: StudyDayEntity
          val _tmpDate: String
          _tmpDate = _stmt.getText(_columnIndexOfDate)
          val _tmpQuestionsAttempted: Int
          _tmpQuestionsAttempted = _stmt.getLong(_columnIndexOfQuestionsAttempted).toInt()
          val _tmpCorrect: Int
          _tmpCorrect = _stmt.getLong(_columnIndexOfCorrect).toInt()
          val _tmpActiveMs: Long
          _tmpActiveMs = _stmt.getLong(_columnIndexOfActiveMs)
          _item = StudyDayEntity(_tmpDate,_tmpQuestionsAttempted,_tmpCorrect,_tmpActiveMs)
          _result.add(_item)
        }
        _result
      } finally {
        _stmt.close()
      }
    }
  }

  public override suspend fun countActiveDays(): Int {
    val _sql: String = "SELECT COUNT(*) FROM study_day WHERE questions_attempted > 0"
    return performSuspending(__db, true, false) { _connection ->
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

  public override suspend fun getAllStudyDaysOnce(): List<StudyDayEntity> {
    val _sql: String = "SELECT * FROM study_day ORDER BY date DESC"
    return performSuspending(__db, true, false) { _connection ->
      val _stmt: SQLiteStatement = _connection.prepare(_sql)
      try {
        val _columnIndexOfDate: Int = getColumnIndexOrThrow(_stmt, "date")
        val _columnIndexOfQuestionsAttempted: Int = getColumnIndexOrThrow(_stmt,
            "questions_attempted")
        val _columnIndexOfCorrect: Int = getColumnIndexOrThrow(_stmt, "correct")
        val _columnIndexOfActiveMs: Int = getColumnIndexOrThrow(_stmt, "active_ms")
        val _result: MutableList<StudyDayEntity> = mutableListOf()
        while (_stmt.step()) {
          val _item: StudyDayEntity
          val _tmpDate: String
          _tmpDate = _stmt.getText(_columnIndexOfDate)
          val _tmpQuestionsAttempted: Int
          _tmpQuestionsAttempted = _stmt.getLong(_columnIndexOfQuestionsAttempted).toInt()
          val _tmpCorrect: Int
          _tmpCorrect = _stmt.getLong(_columnIndexOfCorrect).toInt()
          val _tmpActiveMs: Long
          _tmpActiveMs = _stmt.getLong(_columnIndexOfActiveMs)
          _item = StudyDayEntity(_tmpDate,_tmpQuestionsAttempted,_tmpCorrect,_tmpActiveMs)
          _result.add(_item)
        }
        _result
      } finally {
        _stmt.close()
      }
    }
  }

  public override suspend fun recordProgress(
    date: String,
    attempted: Int,
    correct: Int,
    activeMs: Long,
  ) {
    val _sql: String = """
        |
        |        INSERT INTO study_day (date, questions_attempted, correct, active_ms)
        |        VALUES (?, ?, ?, ?)
        |        ON CONFLICT(date) DO UPDATE SET
        |            questions_attempted = questions_attempted + ?,
        |            correct = correct + ?,
        |            active_ms = active_ms + ?
        |    
        """.trimMargin()
    return performSuspending(__db, false, true) { _connection ->
      val _stmt: SQLiteStatement = _connection.prepare(_sql)
      try {
        var _argIndex: Int = 1
        _stmt.bindText(_argIndex, date)
        _argIndex = 2
        _stmt.bindLong(_argIndex, attempted.toLong())
        _argIndex = 3
        _stmt.bindLong(_argIndex, correct.toLong())
        _argIndex = 4
        _stmt.bindLong(_argIndex, activeMs)
        _argIndex = 5
        _stmt.bindLong(_argIndex, attempted.toLong())
        _argIndex = 6
        _stmt.bindLong(_argIndex, correct.toLong())
        _argIndex = 7
        _stmt.bindLong(_argIndex, activeMs)
        _stmt.step()
      } finally {
        _stmt.close()
      }
    }
  }

  public override suspend fun deleteAll() {
    val _sql: String = "DELETE FROM study_day"
    return performSuspending(__db, false, true) { _connection ->
      val _stmt: SQLiteStatement = _connection.prepare(_sql)
      try {
        _stmt.step()
      } finally {
        _stmt.close()
      }
    }
  }

  public companion object {
    public fun getRequiredConverters(): List<KClass<*>> = emptyList()
  }
}
