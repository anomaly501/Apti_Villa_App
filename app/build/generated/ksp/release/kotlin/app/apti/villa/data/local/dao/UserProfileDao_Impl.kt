package app.apti.villa.`data`.local.dao

import androidx.room.EntityDeleteOrUpdateAdapter
import androidx.room.EntityInsertAdapter
import androidx.room.RoomDatabase
import androidx.room.coroutines.createFlow
import androidx.room.util.getColumnIndexOrThrow
import androidx.room.util.performSuspending
import androidx.sqlite.SQLiteStatement
import app.apti.villa.`data`.local.entity.UserProfileEntity
import javax.`annotation`.processing.Generated
import kotlin.Int
import kotlin.Long
import kotlin.String
import kotlin.Suppress
import kotlin.Unit
import kotlin.collections.List
import kotlin.reflect.KClass
import kotlinx.coroutines.flow.Flow

@Generated(value = ["androidx.room.RoomProcessor"])
@Suppress(names = ["UNCHECKED_CAST", "DEPRECATION", "REDUNDANT_PROJECTION", "REMOVAL"])
public class UserProfileDao_Impl(
  __db: RoomDatabase,
) : UserProfileDao {
  private val __db: RoomDatabase

  private val __insertAdapterOfUserProfileEntity: EntityInsertAdapter<UserProfileEntity>

  private val __updateAdapterOfUserProfileEntity: EntityDeleteOrUpdateAdapter<UserProfileEntity>
  init {
    this.__db = __db
    this.__insertAdapterOfUserProfileEntity = object : EntityInsertAdapter<UserProfileEntity>() {
      protected override fun createQuery(): String =
          "INSERT OR REPLACE INTO `user_profile` (`id`,`name`,`preferred_difficulty`,`daily_goal_questions`,`daily_goal_minutes`,`prep_start_date`,`target_exam_date`,`created_at`,`updated_at`) VALUES (?,?,?,?,?,?,?,?,?)"

      protected override fun bind(statement: SQLiteStatement, entity: UserProfileEntity) {
        statement.bindLong(1, entity.id)
        val _tmpName: String? = entity.name
        if (_tmpName == null) {
          statement.bindNull(2)
        } else {
          statement.bindText(2, _tmpName)
        }
        val _tmpPreferredDifficulty: String? = entity.preferredDifficulty
        if (_tmpPreferredDifficulty == null) {
          statement.bindNull(3)
        } else {
          statement.bindText(3, _tmpPreferredDifficulty)
        }
        statement.bindLong(4, entity.dailyGoalQuestions.toLong())
        statement.bindLong(5, entity.dailyGoalMinutes.toLong())
        val _tmpPrepStartDate: String? = entity.prepStartDate
        if (_tmpPrepStartDate == null) {
          statement.bindNull(6)
        } else {
          statement.bindText(6, _tmpPrepStartDate)
        }
        val _tmpTargetExamDate: String? = entity.targetExamDate
        if (_tmpTargetExamDate == null) {
          statement.bindNull(7)
        } else {
          statement.bindText(7, _tmpTargetExamDate)
        }
        statement.bindLong(8, entity.createdAt)
        statement.bindLong(9, entity.updatedAt)
      }
    }
    this.__updateAdapterOfUserProfileEntity = object :
        EntityDeleteOrUpdateAdapter<UserProfileEntity>() {
      protected override fun createQuery(): String =
          "UPDATE OR ABORT `user_profile` SET `id` = ?,`name` = ?,`preferred_difficulty` = ?,`daily_goal_questions` = ?,`daily_goal_minutes` = ?,`prep_start_date` = ?,`target_exam_date` = ?,`created_at` = ?,`updated_at` = ? WHERE `id` = ?"

      protected override fun bind(statement: SQLiteStatement, entity: UserProfileEntity) {
        statement.bindLong(1, entity.id)
        val _tmpName: String? = entity.name
        if (_tmpName == null) {
          statement.bindNull(2)
        } else {
          statement.bindText(2, _tmpName)
        }
        val _tmpPreferredDifficulty: String? = entity.preferredDifficulty
        if (_tmpPreferredDifficulty == null) {
          statement.bindNull(3)
        } else {
          statement.bindText(3, _tmpPreferredDifficulty)
        }
        statement.bindLong(4, entity.dailyGoalQuestions.toLong())
        statement.bindLong(5, entity.dailyGoalMinutes.toLong())
        val _tmpPrepStartDate: String? = entity.prepStartDate
        if (_tmpPrepStartDate == null) {
          statement.bindNull(6)
        } else {
          statement.bindText(6, _tmpPrepStartDate)
        }
        val _tmpTargetExamDate: String? = entity.targetExamDate
        if (_tmpTargetExamDate == null) {
          statement.bindNull(7)
        } else {
          statement.bindText(7, _tmpTargetExamDate)
        }
        statement.bindLong(8, entity.createdAt)
        statement.bindLong(9, entity.updatedAt)
        statement.bindLong(10, entity.id)
      }
    }
  }

  public override suspend fun upsert(profile: UserProfileEntity): Unit = performSuspending(__db,
      false, true) { _connection ->
    __insertAdapterOfUserProfileEntity.insert(_connection, profile)
  }

  public override suspend fun update(profile: UserProfileEntity): Unit = performSuspending(__db,
      false, true) { _connection ->
    __updateAdapterOfUserProfileEntity.handle(_connection, profile)
  }

  public override fun getProfile(): Flow<UserProfileEntity?> {
    val _sql: String = "SELECT * FROM user_profile WHERE id = 1"
    return createFlow(__db, false, arrayOf("user_profile")) { _connection ->
      val _stmt: SQLiteStatement = _connection.prepare(_sql)
      try {
        val _columnIndexOfId: Int = getColumnIndexOrThrow(_stmt, "id")
        val _columnIndexOfName: Int = getColumnIndexOrThrow(_stmt, "name")
        val _columnIndexOfPreferredDifficulty: Int = getColumnIndexOrThrow(_stmt,
            "preferred_difficulty")
        val _columnIndexOfDailyGoalQuestions: Int = getColumnIndexOrThrow(_stmt,
            "daily_goal_questions")
        val _columnIndexOfDailyGoalMinutes: Int = getColumnIndexOrThrow(_stmt, "daily_goal_minutes")
        val _columnIndexOfPrepStartDate: Int = getColumnIndexOrThrow(_stmt, "prep_start_date")
        val _columnIndexOfTargetExamDate: Int = getColumnIndexOrThrow(_stmt, "target_exam_date")
        val _columnIndexOfCreatedAt: Int = getColumnIndexOrThrow(_stmt, "created_at")
        val _columnIndexOfUpdatedAt: Int = getColumnIndexOrThrow(_stmt, "updated_at")
        val _result: UserProfileEntity?
        if (_stmt.step()) {
          val _tmpId: Long
          _tmpId = _stmt.getLong(_columnIndexOfId)
          val _tmpName: String?
          if (_stmt.isNull(_columnIndexOfName)) {
            _tmpName = null
          } else {
            _tmpName = _stmt.getText(_columnIndexOfName)
          }
          val _tmpPreferredDifficulty: String?
          if (_stmt.isNull(_columnIndexOfPreferredDifficulty)) {
            _tmpPreferredDifficulty = null
          } else {
            _tmpPreferredDifficulty = _stmt.getText(_columnIndexOfPreferredDifficulty)
          }
          val _tmpDailyGoalQuestions: Int
          _tmpDailyGoalQuestions = _stmt.getLong(_columnIndexOfDailyGoalQuestions).toInt()
          val _tmpDailyGoalMinutes: Int
          _tmpDailyGoalMinutes = _stmt.getLong(_columnIndexOfDailyGoalMinutes).toInt()
          val _tmpPrepStartDate: String?
          if (_stmt.isNull(_columnIndexOfPrepStartDate)) {
            _tmpPrepStartDate = null
          } else {
            _tmpPrepStartDate = _stmt.getText(_columnIndexOfPrepStartDate)
          }
          val _tmpTargetExamDate: String?
          if (_stmt.isNull(_columnIndexOfTargetExamDate)) {
            _tmpTargetExamDate = null
          } else {
            _tmpTargetExamDate = _stmt.getText(_columnIndexOfTargetExamDate)
          }
          val _tmpCreatedAt: Long
          _tmpCreatedAt = _stmt.getLong(_columnIndexOfCreatedAt)
          val _tmpUpdatedAt: Long
          _tmpUpdatedAt = _stmt.getLong(_columnIndexOfUpdatedAt)
          _result =
              UserProfileEntity(_tmpId,_tmpName,_tmpPreferredDifficulty,_tmpDailyGoalQuestions,_tmpDailyGoalMinutes,_tmpPrepStartDate,_tmpTargetExamDate,_tmpCreatedAt,_tmpUpdatedAt)
        } else {
          _result = null
        }
        _result
      } finally {
        _stmt.close()
      }
    }
  }

  public override suspend fun getProfileOnce(): UserProfileEntity? {
    val _sql: String = "SELECT * FROM user_profile WHERE id = 1"
    return performSuspending(__db, true, false) { _connection ->
      val _stmt: SQLiteStatement = _connection.prepare(_sql)
      try {
        val _columnIndexOfId: Int = getColumnIndexOrThrow(_stmt, "id")
        val _columnIndexOfName: Int = getColumnIndexOrThrow(_stmt, "name")
        val _columnIndexOfPreferredDifficulty: Int = getColumnIndexOrThrow(_stmt,
            "preferred_difficulty")
        val _columnIndexOfDailyGoalQuestions: Int = getColumnIndexOrThrow(_stmt,
            "daily_goal_questions")
        val _columnIndexOfDailyGoalMinutes: Int = getColumnIndexOrThrow(_stmt, "daily_goal_minutes")
        val _columnIndexOfPrepStartDate: Int = getColumnIndexOrThrow(_stmt, "prep_start_date")
        val _columnIndexOfTargetExamDate: Int = getColumnIndexOrThrow(_stmt, "target_exam_date")
        val _columnIndexOfCreatedAt: Int = getColumnIndexOrThrow(_stmt, "created_at")
        val _columnIndexOfUpdatedAt: Int = getColumnIndexOrThrow(_stmt, "updated_at")
        val _result: UserProfileEntity?
        if (_stmt.step()) {
          val _tmpId: Long
          _tmpId = _stmt.getLong(_columnIndexOfId)
          val _tmpName: String?
          if (_stmt.isNull(_columnIndexOfName)) {
            _tmpName = null
          } else {
            _tmpName = _stmt.getText(_columnIndexOfName)
          }
          val _tmpPreferredDifficulty: String?
          if (_stmt.isNull(_columnIndexOfPreferredDifficulty)) {
            _tmpPreferredDifficulty = null
          } else {
            _tmpPreferredDifficulty = _stmt.getText(_columnIndexOfPreferredDifficulty)
          }
          val _tmpDailyGoalQuestions: Int
          _tmpDailyGoalQuestions = _stmt.getLong(_columnIndexOfDailyGoalQuestions).toInt()
          val _tmpDailyGoalMinutes: Int
          _tmpDailyGoalMinutes = _stmt.getLong(_columnIndexOfDailyGoalMinutes).toInt()
          val _tmpPrepStartDate: String?
          if (_stmt.isNull(_columnIndexOfPrepStartDate)) {
            _tmpPrepStartDate = null
          } else {
            _tmpPrepStartDate = _stmt.getText(_columnIndexOfPrepStartDate)
          }
          val _tmpTargetExamDate: String?
          if (_stmt.isNull(_columnIndexOfTargetExamDate)) {
            _tmpTargetExamDate = null
          } else {
            _tmpTargetExamDate = _stmt.getText(_columnIndexOfTargetExamDate)
          }
          val _tmpCreatedAt: Long
          _tmpCreatedAt = _stmt.getLong(_columnIndexOfCreatedAt)
          val _tmpUpdatedAt: Long
          _tmpUpdatedAt = _stmt.getLong(_columnIndexOfUpdatedAt)
          _result =
              UserProfileEntity(_tmpId,_tmpName,_tmpPreferredDifficulty,_tmpDailyGoalQuestions,_tmpDailyGoalMinutes,_tmpPrepStartDate,_tmpTargetExamDate,_tmpCreatedAt,_tmpUpdatedAt)
        } else {
          _result = null
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
