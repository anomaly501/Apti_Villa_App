package app.apti.villa.`data`.local.dao

import androidx.room.EntityInsertAdapter
import androidx.room.RoomDatabase
import androidx.room.coroutines.createFlow
import androidx.room.util.getColumnIndexOrThrow
import androidx.room.util.performSuspending
import androidx.sqlite.SQLiteStatement
import app.apti.villa.`data`.local.entity.SolutionStepEntity
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
public class SolutionStepDao_Impl(
  __db: RoomDatabase,
) : SolutionStepDao {
  private val __db: RoomDatabase

  private val __insertAdapterOfSolutionStepEntity: EntityInsertAdapter<SolutionStepEntity>
  init {
    this.__db = __db
    this.__insertAdapterOfSolutionStepEntity = object : EntityInsertAdapter<SolutionStepEntity>() {
      protected override fun createQuery(): String =
          "INSERT OR REPLACE INTO `solution_step` (`id`,`question_id`,`number`,`action`) VALUES (nullif(?, 0),?,?,?)"

      protected override fun bind(statement: SQLiteStatement, entity: SolutionStepEntity) {
        statement.bindLong(1, entity.id)
        statement.bindLong(2, entity.questionId)
        statement.bindLong(3, entity.number.toLong())
        statement.bindText(4, entity.action)
      }
    }
  }

  public override suspend fun insertAll(steps: List<SolutionStepEntity>): Unit =
      performSuspending(__db, false, true) { _connection ->
    __insertAdapterOfSolutionStepEntity.insert(_connection, steps)
  }

  public override fun getStepsForQuestion(questionId: Long): Flow<List<SolutionStepEntity>> {
    val _sql: String = "SELECT * FROM solution_step WHERE question_id = ? ORDER BY number ASC"
    return createFlow(__db, false, arrayOf("solution_step")) { _connection ->
      val _stmt: SQLiteStatement = _connection.prepare(_sql)
      try {
        var _argIndex: Int = 1
        _stmt.bindLong(_argIndex, questionId)
        val _columnIndexOfId: Int = getColumnIndexOrThrow(_stmt, "id")
        val _columnIndexOfQuestionId: Int = getColumnIndexOrThrow(_stmt, "question_id")
        val _columnIndexOfNumber: Int = getColumnIndexOrThrow(_stmt, "number")
        val _columnIndexOfAction: Int = getColumnIndexOrThrow(_stmt, "action")
        val _result: MutableList<SolutionStepEntity> = mutableListOf()
        while (_stmt.step()) {
          val _item: SolutionStepEntity
          val _tmpId: Long
          _tmpId = _stmt.getLong(_columnIndexOfId)
          val _tmpQuestionId: Long
          _tmpQuestionId = _stmt.getLong(_columnIndexOfQuestionId)
          val _tmpNumber: Int
          _tmpNumber = _stmt.getLong(_columnIndexOfNumber).toInt()
          val _tmpAction: String
          _tmpAction = _stmt.getText(_columnIndexOfAction)
          _item = SolutionStepEntity(_tmpId,_tmpQuestionId,_tmpNumber,_tmpAction)
          _result.add(_item)
        }
        _result
      } finally {
        _stmt.close()
      }
    }
  }

  public override suspend fun getStepsForQuestionOnce(questionId: Long): List<SolutionStepEntity> {
    val _sql: String = "SELECT * FROM solution_step WHERE question_id = ? ORDER BY number ASC"
    return performSuspending(__db, true, false) { _connection ->
      val _stmt: SQLiteStatement = _connection.prepare(_sql)
      try {
        var _argIndex: Int = 1
        _stmt.bindLong(_argIndex, questionId)
        val _columnIndexOfId: Int = getColumnIndexOrThrow(_stmt, "id")
        val _columnIndexOfQuestionId: Int = getColumnIndexOrThrow(_stmt, "question_id")
        val _columnIndexOfNumber: Int = getColumnIndexOrThrow(_stmt, "number")
        val _columnIndexOfAction: Int = getColumnIndexOrThrow(_stmt, "action")
        val _result: MutableList<SolutionStepEntity> = mutableListOf()
        while (_stmt.step()) {
          val _item: SolutionStepEntity
          val _tmpId: Long
          _tmpId = _stmt.getLong(_columnIndexOfId)
          val _tmpQuestionId: Long
          _tmpQuestionId = _stmt.getLong(_columnIndexOfQuestionId)
          val _tmpNumber: Int
          _tmpNumber = _stmt.getLong(_columnIndexOfNumber).toInt()
          val _tmpAction: String
          _tmpAction = _stmt.getText(_columnIndexOfAction)
          _item = SolutionStepEntity(_tmpId,_tmpQuestionId,_tmpNumber,_tmpAction)
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
