package app.apti.villa.`data`.local.dao

import androidx.room.EntityInsertAdapter
import androidx.room.RoomDatabase
import androidx.room.coroutines.createFlow
import androidx.room.util.getColumnIndexOrThrow
import androidx.room.util.performSuspending
import androidx.sqlite.SQLiteStatement
import app.apti.villa.`data`.local.entity.TestQuestionEntity
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
public class TestQuestionDao_Impl(
  __db: RoomDatabase,
) : TestQuestionDao {
  private val __db: RoomDatabase

  private val __insertAdapterOfTestQuestionEntity: EntityInsertAdapter<TestQuestionEntity>
  init {
    this.__db = __db
    this.__insertAdapterOfTestQuestionEntity = object : EntityInsertAdapter<TestQuestionEntity>() {
      protected override fun createQuery(): String =
          "INSERT OR REPLACE INTO `test_question` (`test_id`,`question_id`,`position`) VALUES (?,?,?)"

      protected override fun bind(statement: SQLiteStatement, entity: TestQuestionEntity) {
        statement.bindLong(1, entity.testId)
        statement.bindLong(2, entity.questionId)
        statement.bindLong(3, entity.position.toLong())
      }
    }
  }

  public override suspend fun insert(testQuestion: TestQuestionEntity): Unit =
      performSuspending(__db, false, true) { _connection ->
    __insertAdapterOfTestQuestionEntity.insert(_connection, testQuestion)
  }

  public override suspend fun insertAll(testQuestions: List<TestQuestionEntity>): Unit =
      performSuspending(__db, false, true) { _connection ->
    __insertAdapterOfTestQuestionEntity.insert(_connection, testQuestions)
  }

  public override fun getQuestionsForTest(testId: Long): Flow<List<TestQuestionEntity>> {
    val _sql: String = "SELECT * FROM test_question WHERE test_id = ? ORDER BY position ASC"
    return createFlow(__db, false, arrayOf("test_question")) { _connection ->
      val _stmt: SQLiteStatement = _connection.prepare(_sql)
      try {
        var _argIndex: Int = 1
        _stmt.bindLong(_argIndex, testId)
        val _columnIndexOfTestId: Int = getColumnIndexOrThrow(_stmt, "test_id")
        val _columnIndexOfQuestionId: Int = getColumnIndexOrThrow(_stmt, "question_id")
        val _columnIndexOfPosition: Int = getColumnIndexOrThrow(_stmt, "position")
        val _result: MutableList<TestQuestionEntity> = mutableListOf()
        while (_stmt.step()) {
          val _item: TestQuestionEntity
          val _tmpTestId: Long
          _tmpTestId = _stmt.getLong(_columnIndexOfTestId)
          val _tmpQuestionId: Long
          _tmpQuestionId = _stmt.getLong(_columnIndexOfQuestionId)
          val _tmpPosition: Int
          _tmpPosition = _stmt.getLong(_columnIndexOfPosition).toInt()
          _item = TestQuestionEntity(_tmpTestId,_tmpQuestionId,_tmpPosition)
          _result.add(_item)
        }
        _result
      } finally {
        _stmt.close()
      }
    }
  }

  public override suspend fun getQuestionsForTestOnce(testId: Long): List<TestQuestionEntity> {
    val _sql: String = "SELECT * FROM test_question WHERE test_id = ? ORDER BY position ASC"
    return performSuspending(__db, true, false) { _connection ->
      val _stmt: SQLiteStatement = _connection.prepare(_sql)
      try {
        var _argIndex: Int = 1
        _stmt.bindLong(_argIndex, testId)
        val _columnIndexOfTestId: Int = getColumnIndexOrThrow(_stmt, "test_id")
        val _columnIndexOfQuestionId: Int = getColumnIndexOrThrow(_stmt, "question_id")
        val _columnIndexOfPosition: Int = getColumnIndexOrThrow(_stmt, "position")
        val _result: MutableList<TestQuestionEntity> = mutableListOf()
        while (_stmt.step()) {
          val _item: TestQuestionEntity
          val _tmpTestId: Long
          _tmpTestId = _stmt.getLong(_columnIndexOfTestId)
          val _tmpQuestionId: Long
          _tmpQuestionId = _stmt.getLong(_columnIndexOfQuestionId)
          val _tmpPosition: Int
          _tmpPosition = _stmt.getLong(_columnIndexOfPosition).toInt()
          _item = TestQuestionEntity(_tmpTestId,_tmpQuestionId,_tmpPosition)
          _result.add(_item)
        }
        _result
      } finally {
        _stmt.close()
      }
    }
  }

  public override suspend fun getQuestionCountForTest(testId: Long): Int {
    val _sql: String = "SELECT COUNT(*) FROM test_question WHERE test_id = ?"
    return performSuspending(__db, true, false) { _connection ->
      val _stmt: SQLiteStatement = _connection.prepare(_sql)
      try {
        var _argIndex: Int = 1
        _stmt.bindLong(_argIndex, testId)
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
