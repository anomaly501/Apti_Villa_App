package app.apti.villa.`data`.local.dao

import androidx.room.EntityInsertAdapter
import androidx.room.RoomDatabase
import androidx.room.coroutines.createFlow
import androidx.room.util.performSuspending
import androidx.sqlite.SQLiteStatement
import app.apti.villa.`data`.local.entity.SavedTrickEntity
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
public class SavedTrickDao_Impl(
  __db: RoomDatabase,
) : SavedTrickDao {
  private val __db: RoomDatabase

  private val __insertAdapterOfSavedTrickEntity: EntityInsertAdapter<SavedTrickEntity>
  init {
    this.__db = __db
    this.__insertAdapterOfSavedTrickEntity = object : EntityInsertAdapter<SavedTrickEntity>() {
      protected override fun createQuery(): String =
          "INSERT OR REPLACE INTO `saved_trick` (`trick_id`,`created_at`) VALUES (?,?)"

      protected override fun bind(statement: SQLiteStatement, entity: SavedTrickEntity) {
        statement.bindLong(1, entity.trickId)
        statement.bindLong(2, entity.createdAt)
      }
    }
  }

  public override suspend fun insert(savedTrick: SavedTrickEntity): Unit = performSuspending(__db,
      false, true) { _connection ->
    __insertAdapterOfSavedTrickEntity.insert(_connection, savedTrick)
  }

  public override suspend fun isTrickSaved(trickId: Long): Boolean {
    val _sql: String = "SELECT COUNT(*) > 0 FROM saved_trick WHERE trick_id = ?"
    return performSuspending(__db, true, false) { _connection ->
      val _stmt: SQLiteStatement = _connection.prepare(_sql)
      try {
        var _argIndex: Int = 1
        _stmt.bindLong(_argIndex, trickId)
        val _result: Boolean
        if (_stmt.step()) {
          val _tmp: Int
          _tmp = _stmt.getLong(0).toInt()
          _result = _tmp != 0
        } else {
          _result = false
        }
        _result
      } finally {
        _stmt.close()
      }
    }
  }

  public override fun isTrickSavedFlow(trickId: Long): Flow<Boolean> {
    val _sql: String = "SELECT COUNT(*) > 0 FROM saved_trick WHERE trick_id = ?"
    return createFlow(__db, false, arrayOf("saved_trick")) { _connection ->
      val _stmt: SQLiteStatement = _connection.prepare(_sql)
      try {
        var _argIndex: Int = 1
        _stmt.bindLong(_argIndex, trickId)
        val _result: Boolean
        if (_stmt.step()) {
          val _tmp: Int
          _tmp = _stmt.getLong(0).toInt()
          _result = _tmp != 0
        } else {
          _result = false
        }
        _result
      } finally {
        _stmt.close()
      }
    }
  }

  public override fun getAllSavedTrickIds(): Flow<List<Long>> {
    val _sql: String = "SELECT trick_id FROM saved_trick"
    return createFlow(__db, false, arrayOf("saved_trick")) { _connection ->
      val _stmt: SQLiteStatement = _connection.prepare(_sql)
      try {
        val _result: MutableList<Long> = mutableListOf()
        while (_stmt.step()) {
          val _item: Long
          _item = _stmt.getLong(0)
          _result.add(_item)
        }
        _result
      } finally {
        _stmt.close()
      }
    }
  }

  public override suspend fun getAllSavedTrickIdsOnce(): List<Long> {
    val _sql: String = "SELECT trick_id FROM saved_trick"
    return performSuspending(__db, true, false) { _connection ->
      val _stmt: SQLiteStatement = _connection.prepare(_sql)
      try {
        val _result: MutableList<Long> = mutableListOf()
        while (_stmt.step()) {
          val _item: Long
          _item = _stmt.getLong(0)
          _result.add(_item)
        }
        _result
      } finally {
        _stmt.close()
      }
    }
  }

  public override suspend fun delete(trickId: Long) {
    val _sql: String = "DELETE FROM saved_trick WHERE trick_id = ?"
    return performSuspending(__db, false, true) { _connection ->
      val _stmt: SQLiteStatement = _connection.prepare(_sql)
      try {
        var _argIndex: Int = 1
        _stmt.bindLong(_argIndex, trickId)
        _stmt.step()
      } finally {
        _stmt.close()
      }
    }
  }

  public override suspend fun deleteAll() {
    val _sql: String = "DELETE FROM saved_trick"
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
