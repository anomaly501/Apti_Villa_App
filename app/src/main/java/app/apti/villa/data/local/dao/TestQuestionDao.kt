package app.apti.villa.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import app.apti.villa.data.local.entity.TestQuestionEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface TestQuestionDao {
    @Query("SELECT * FROM test_question WHERE test_id = :testId ORDER BY position ASC")
    fun getQuestionsForTest(testId: Long): Flow<List<TestQuestionEntity>>

    @Query("SELECT * FROM test_question WHERE test_id = :testId ORDER BY position ASC")
    suspend fun getQuestionsForTestOnce(testId: Long): List<TestQuestionEntity>

    @Query("SELECT COUNT(*) FROM test_question WHERE test_id = :testId")
    suspend fun getQuestionCountForTest(testId: Long): Int

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(testQuestion: TestQuestionEntity)


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(testQuestions: List<TestQuestionEntity>)
}
