package app.apti.villa

import androidx.room.testing.MigrationTestHelper
import androidx.sqlite.db.framework.FrameworkSQLiteOpenHelperFactory
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import app.apti.villa.data.local.db.AppDatabase
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class MigrationTest {

    @get:Rule
    val helper: MigrationTestHelper = MigrationTestHelper(
        InstrumentationRegistry.getInstrumentation(),
        AppDatabase::class.java,
        emptyList(),
        FrameworkSQLiteOpenHelperFactory(),
    )

    @Test
    fun createDatabase_version1_succeeds() {
        val db = helper.createDatabase(TEST_DB, 1)
        db.close()
    }

    @Test
    fun migrate_1_to_2_succeeds() {
        var db = helper.createDatabase(TEST_DB, 1)
        db.close()

        db = helper.runMigrationsAndValidate(TEST_DB, 2, true, AppDatabase.MIGRATION_1_2)
        db.close()
    }

    @Test
    fun migrate_2_to_3_succeeds() {
        var db = helper.createDatabase(TEST_DB, 2)
        db.close()

        db = helper.runMigrationsAndValidate(TEST_DB, 3, true, AppDatabase.MIGRATION_2_3)
        db.close()
    }

    @Test
    fun migrate_3_to_4_succeeds() {
        var db = helper.createDatabase(TEST_DB, 3)
        db.close()

        db = helper.runMigrationsAndValidate(TEST_DB, 4, true, AppDatabase.MIGRATION_3_4)
        db.close()
    }

    @Test
    fun migrate_4_to_5_succeeds() {
        var db = helper.createDatabase(TEST_DB, 4)
        db.close()

        db = helper.runMigrationsAndValidate(TEST_DB, 5, true, AppDatabase.MIGRATION_4_5)
        db.close()
    }

    @Test
    fun migrate_5_to_6_succeeds() {
        var db = helper.createDatabase(TEST_DB, 5)
        db.close()

        db = helper.runMigrationsAndValidate(TEST_DB, 6, true, AppDatabase.MIGRATION_5_6)
        db.close()
    }

    @Test
    fun migrate_all_1_to_6_succeeds() {
        var db = helper.createDatabase(TEST_DB, 1)
        db.close()

        db = helper.runMigrationsAndValidate(
            TEST_DB,
            6,
            true,
            AppDatabase.MIGRATION_1_2,
            AppDatabase.MIGRATION_2_3,
            AppDatabase.MIGRATION_3_4,
            AppDatabase.MIGRATION_4_5,
            AppDatabase.MIGRATION_5_6,
        )
        db.close()
    }

    companion object {
        private const val TEST_DB = "migration-test"
    }
}
