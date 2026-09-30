package app.apti.villa.data.local.db

import android.content.Context
import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import app.apti.villa.data.local.dao.*
import app.apti.villa.data.local.entity.*
import app.apti.villa.data.local.entity.fts.*
import java.util.UUID

@Database(
    entities = [
        ExamEntity::class,
        SubjectEntity::class,
        TopicEntity::class,
        SubTopicEntity::class,
        TagEntity::class,
        UserProfileEntity::class,
        SourceEntity::class,
        TestEntity::class,
        QuestionEntity::class,
        QuestionOptionEntity::class,
        TestQuestionEntity::class,
        HintEntity::class,
        TrickEntity::class,
        SolutionStepEntity::class,
        AttemptEntity::class,
        AnswerEntity::class,
        AnswerSubmissionEntity::class,
        HintUsageEntity::class,
        QuestionTagEntity::class,
        SavedTrickEntity::class,
        MistakeEntity::class,
        RevisionItemEntity::class,
        RevisionReasonEntity::class,
        StudyDayEntity::class,
        QuestionFtsEntity::class,
        QuestionOptionFtsEntity::class,
        TrickFtsEntity::class,
        TestFtsEntity::class,
        AiConversationEntity::class,
        AiMessageEntity::class,
    ],
    version = 6,
    exportSchema = true,
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun examDao(): ExamDao
    abstract fun subjectDao(): SubjectDao
    abstract fun topicDao(): TopicDao
    abstract fun subTopicDao(): SubTopicDao
    abstract fun tagDao(): TagDao
    abstract fun userProfileDao(): UserProfileDao
    abstract fun sourceDao(): SourceDao
    abstract fun testDao(): TestDao
    abstract fun questionDao(): QuestionDao
    abstract fun questionOptionDao(): QuestionOptionDao
    abstract fun testQuestionDao(): TestQuestionDao
    abstract fun hintDao(): HintDao
    abstract fun trickDao(): TrickDao
    abstract fun solutionStepDao(): SolutionStepDao
    abstract fun attemptDao(): AttemptDao
    abstract fun answerDao(): AnswerDao
    abstract fun answerSubmissionDao(): AnswerSubmissionDao
    abstract fun hintUsageDao(): HintUsageDao
    abstract fun questionTagDao(): QuestionTagDao
    abstract fun savedTrickDao(): SavedTrickDao
    abstract fun mistakeDao(): MistakeDao
    abstract fun revisionDao(): RevisionDao
    abstract fun studyDayDao(): StudyDayDao
    abstract fun analyticsDao(): AnalyticsDao
    abstract fun inventoryDao(): InventoryDao
    abstract fun searchDao(): SearchDao
    abstract fun aiConversationDao(): AiConversationDao



    companion object {
        const val DATABASE_NAME = "aptivilla.db"

        val MIGRATION_1_2 = object : Migration(1, 2) {
            override fun migrate(db: SupportSQLiteDatabase) {
                // 1. Create source table
                db.execSQL(
                    """CREATE TABLE IF NOT EXISTS `source` (
                        `id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
                        `uuid` TEXT NOT NULL,
                        `title` TEXT NOT NULL,
                        `type` TEXT NOT NULL,
                        `url` TEXT,
                        `channel` TEXT,
                        `schema_version` INTEGER NOT NULL,
                        `content_hash` TEXT NOT NULL,
                        `imported_at` INTEGER NOT NULL,
                        `import_report_json` TEXT,
                        `created_at` INTEGER NOT NULL,
                        `updated_at` INTEGER NOT NULL
                    )"""
                )
                db.execSQL("CREATE UNIQUE INDEX IF NOT EXISTS `index_source_uuid` ON `source` (`uuid`)")
                db.execSQL("CREATE INDEX IF NOT EXISTS `index_source_content_hash` ON `source` (`content_hash`)")

                // 2. Create test table
                db.execSQL(
                    """CREATE TABLE IF NOT EXISTS `test` (
                        `id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
                        `uuid` TEXT NOT NULL,
                        `source_id` INTEGER,
                        `exam_id` INTEGER,
                        `title` TEXT NOT NULL,
                        `kind` TEXT NOT NULL,
                        `duration_sec` INTEGER,
                        `marks_correct` REAL NOT NULL,
                        `negative_marks` REAL NOT NULL,
                        `archived` INTEGER NOT NULL,
                        `created_at` INTEGER NOT NULL,
                        `updated_at` INTEGER NOT NULL,
                        FOREIGN KEY(`source_id`) REFERENCES `source`(`id`) ON UPDATE NO ACTION ON DELETE SET NULL,
                        FOREIGN KEY(`exam_id`) REFERENCES `exam`(`id`) ON UPDATE NO ACTION ON DELETE SET NULL
                    )"""
                )
                db.execSQL("CREATE UNIQUE INDEX IF NOT EXISTS `index_test_uuid` ON `test` (`uuid`)")
                db.execSQL("CREATE INDEX IF NOT EXISTS `index_test_source_id` ON `test` (`source_id`)")
                db.execSQL("CREATE INDEX IF NOT EXISTS `index_test_exam_id` ON `test` (`exam_id`)")

                // 3. Create question table
                db.execSQL(
                    """CREATE TABLE IF NOT EXISTS `question` (
                        `id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
                        `uuid` TEXT NOT NULL,
                        `source_id` INTEGER NOT NULL,
                        `external_id` TEXT NOT NULL,
                        `content_hash` TEXT NOT NULL,
                        `text` TEXT NOT NULL,
                        `format` TEXT NOT NULL,
                        `subject_id` INTEGER,
                        `topic_id` INTEGER,
                        `sub_topic_id` INTEGER,
                        `difficulty` TEXT,
                        `source_timestamp_sec` INTEGER,
                        `source_url` TEXT,
                        `language` TEXT,
                        `has_solution` INTEGER NOT NULL,
                        `created_at` INTEGER NOT NULL,
                        `updated_at` INTEGER NOT NULL,
                        FOREIGN KEY(`source_id`) REFERENCES `source`(`id`) ON UPDATE NO ACTION ON DELETE CASCADE,
                        FOREIGN KEY(`subject_id`) REFERENCES `subject`(`id`) ON UPDATE NO ACTION ON DELETE SET NULL,
                        FOREIGN KEY(`topic_id`) REFERENCES `topic`(`id`) ON UPDATE NO ACTION ON DELETE SET NULL,
                        FOREIGN KEY(`sub_topic_id`) REFERENCES `sub_topic`(`id`) ON UPDATE NO ACTION ON DELETE SET NULL
                    )"""
                )
                db.execSQL("CREATE UNIQUE INDEX IF NOT EXISTS `index_question_uuid` ON `question` (`uuid`)")
                db.execSQL("CREATE UNIQUE INDEX IF NOT EXISTS `index_question_source_id_external_id` ON `question` (`source_id`, `external_id`)")
                db.execSQL("CREATE INDEX IF NOT EXISTS `index_question_content_hash` ON `question` (`content_hash`)")
                db.execSQL("CREATE INDEX IF NOT EXISTS `index_question_subject_id` ON `question` (`subject_id`)")
                db.execSQL("CREATE INDEX IF NOT EXISTS `index_question_topic_id` ON `question` (`topic_id`)")
                db.execSQL("CREATE INDEX IF NOT EXISTS `index_question_sub_topic_id` ON `question` (`sub_topic_id`)")

                // 4. Create question_option table
                db.execSQL(
                    """CREATE TABLE IF NOT EXISTS `question_option` (
                        `id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
                        `question_id` INTEGER NOT NULL,
                        `position` INTEGER NOT NULL,
                        `text` TEXT NOT NULL,
                        `is_correct` INTEGER NOT NULL,
                        FOREIGN KEY(`question_id`) REFERENCES `question`(`id`) ON UPDATE NO ACTION ON DELETE CASCADE
                    )"""
                )
                db.execSQL("CREATE INDEX IF NOT EXISTS `index_question_option_question_id` ON `question_option` (`question_id`)")

                // 5. Create test_question table
                db.execSQL(
                    """CREATE TABLE IF NOT EXISTS `test_question` (
                        `test_id` INTEGER NOT NULL,
                        `question_id` INTEGER NOT NULL,
                        `position` INTEGER NOT NULL,
                        PRIMARY KEY(`test_id`, `question_id`),
                        FOREIGN KEY(`test_id`) REFERENCES `test`(`id`) ON UPDATE NO ACTION ON DELETE CASCADE,
                        FOREIGN KEY(`question_id`) REFERENCES `question`(`id`) ON UPDATE NO ACTION ON DELETE CASCADE
                    )"""
                )
                db.execSQL("CREATE INDEX IF NOT EXISTS `index_test_question_test_id` ON `test_question` (`test_id`)")
                db.execSQL("CREATE INDEX IF NOT EXISTS `index_test_question_question_id` ON `test_question` (`question_id`)")

                // 6. Create hint table
                db.execSQL(
                    """CREATE TABLE IF NOT EXISTS `hint` (
                        `id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
                        `question_id` INTEGER NOT NULL,
                        `number` INTEGER NOT NULL,
                        `title` TEXT NOT NULL,
                        `description` TEXT NOT NULL,
                        FOREIGN KEY(`question_id`) REFERENCES `question`(`id`) ON UPDATE NO ACTION ON DELETE CASCADE
                    )"""
                )
                db.execSQL("CREATE INDEX IF NOT EXISTS `index_hint_question_id` ON `hint` (`question_id`)")

                // 7. Create trick table
                db.execSQL(
                    """CREATE TABLE IF NOT EXISTS `trick` (
                        `id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
                        `question_id` INTEGER NOT NULL,
                        `name` TEXT NOT NULL,
                        `description` TEXT NOT NULL,
                        FOREIGN KEY(`question_id`) REFERENCES `question`(`id`) ON UPDATE NO ACTION ON DELETE CASCADE
                    )"""
                )
                db.execSQL("CREATE INDEX IF NOT EXISTS `index_trick_question_id` ON `trick` (`question_id`)")

                // 8. Create solution_step table
                db.execSQL(
                    """CREATE TABLE IF NOT EXISTS `solution_step` (
                        `id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
                        `question_id` INTEGER NOT NULL,
                        `number` INTEGER NOT NULL,
                        `action` TEXT NOT NULL,
                        FOREIGN KEY(`question_id`) REFERENCES `question`(`id`) ON UPDATE NO ACTION ON DELETE CASCADE
                    )"""
                )
                db.execSQL("CREATE INDEX IF NOT EXISTS `index_solution_step_question_id` ON `solution_step` (`question_id`)")
            }
        }

        val MIGRATION_2_3 = object : Migration(2, 3) {
            override fun migrate(db: SupportSQLiteDatabase) {
                // 1. Create attempt table
                db.execSQL(
                    """CREATE TABLE IF NOT EXISTS `attempt` (
                        `id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
                        `uuid` TEXT NOT NULL,
                        `test_id` INTEGER,
                        `mode` TEXT NOT NULL,
                        `scope_desc` TEXT,
                        `status` TEXT NOT NULL,
                        `started_at` INTEGER NOT NULL,
                        `submitted_at` INTEGER,
                        `time_limit_sec` INTEGER,
                        `active_elapsed_ms` INTEGER NOT NULL DEFAULT 0,
                        `score` REAL,
                        `is_retry` INTEGER NOT NULL DEFAULT 0,
                        `created_at` INTEGER NOT NULL,
                        `updated_at` INTEGER NOT NULL,
                        FOREIGN KEY(`test_id`) REFERENCES `test`(`id`) ON UPDATE NO ACTION ON DELETE SET NULL
                    )"""
                )
                db.execSQL("CREATE UNIQUE INDEX IF NOT EXISTS `index_attempt_uuid` ON `attempt` (`uuid`)")
                db.execSQL("CREATE INDEX IF NOT EXISTS `index_attempt_status` ON `attempt` (`status`)")
                db.execSQL("CREATE INDEX IF NOT EXISTS `index_attempt_submitted_at` ON `attempt` (`submitted_at`)")
                db.execSQL("CREATE INDEX IF NOT EXISTS `index_attempt_test_id` ON `attempt` (`test_id`)")
                db.execSQL("CREATE INDEX IF NOT EXISTS `index_attempt_mode` ON `attempt` (`mode`)")

                // 2. Create answer table
                db.execSQL(
                    """CREATE TABLE IF NOT EXISTS `answer` (
                        `id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
                        `attempt_id` INTEGER NOT NULL,
                        `question_id` INTEGER NOT NULL,
                        `position` INTEGER NOT NULL,
                        `status` TEXT NOT NULL DEFAULT 'UNSEEN',
                        `selected_option_id` INTEGER,
                        `is_correct` INTEGER,
                        `time_spent_ms` INTEGER NOT NULL DEFAULT 0,
                        `first_seen_at` INTEGER,
                        `answered_at` INTEGER,
                        `first_submission_correct` INTEGER,
                        `hints_opened` INTEGER NOT NULL DEFAULT 0,
                        `solved_at_hint_level` INTEGER,
                        `revealed` INTEGER NOT NULL DEFAULT 0,
                        FOREIGN KEY(`attempt_id`) REFERENCES `attempt`(`id`) ON UPDATE NO ACTION ON DELETE CASCADE,
                        FOREIGN KEY(`question_id`) REFERENCES `question`(`id`) ON UPDATE NO ACTION ON DELETE CASCADE,
                        FOREIGN KEY(`selected_option_id`) REFERENCES `question_option`(`id`) ON UPDATE NO ACTION ON DELETE SET NULL
                    )"""
                )
                db.execSQL("CREATE UNIQUE INDEX IF NOT EXISTS `index_answer_attempt_id_question_id` ON `answer` (`attempt_id`, `question_id`)")
                db.execSQL("CREATE INDEX IF NOT EXISTS `index_answer_question_id` ON `answer` (`question_id`)")
                db.execSQL("CREATE INDEX IF NOT EXISTS `index_answer_is_correct` ON `answer` (`is_correct`)")
                db.execSQL("CREATE INDEX IF NOT EXISTS `index_answer_attempt_id` ON `answer` (`attempt_id`)")
                db.execSQL("CREATE INDEX IF NOT EXISTS `index_answer_selected_option_id` ON `answer` (`selected_option_id`)")

                // 3. Create answer_submission table
                db.execSQL(
                    """CREATE TABLE IF NOT EXISTS `answer_submission` (
                        `id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
                        `answer_id` INTEGER NOT NULL,
                        `option_id` INTEGER NOT NULL,
                        `submitted_at` INTEGER NOT NULL,
                        `hints_open_at_submit` INTEGER NOT NULL,
                        `ms_since_first_seen` INTEGER NOT NULL,
                        `is_correct` INTEGER NOT NULL,
                        FOREIGN KEY(`answer_id`) REFERENCES `answer`(`id`) ON UPDATE NO ACTION ON DELETE CASCADE,
                        FOREIGN KEY(`option_id`) REFERENCES `question_option`(`id`) ON UPDATE NO ACTION ON DELETE CASCADE
                    )"""
                )
                db.execSQL("CREATE INDEX IF NOT EXISTS `index_answer_submission_answer_id` ON `answer_submission` (`answer_id`)")
                db.execSQL("CREATE INDEX IF NOT EXISTS `index_answer_submission_option_id` ON `answer_submission` (`option_id`)")

                // 4. Create hint_usage table
                db.execSQL(
                    """CREATE TABLE IF NOT EXISTS `hint_usage` (
                        `id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
                        `answer_id` INTEGER NOT NULL,
                        `hint_id` INTEGER NOT NULL,
                        `opened_at` INTEGER NOT NULL,
                        `ms_since_first_seen` INTEGER NOT NULL,
                        FOREIGN KEY(`answer_id`) REFERENCES `answer`(`id`) ON UPDATE NO ACTION ON DELETE CASCADE,
                        FOREIGN KEY(`hint_id`) REFERENCES `hint`(`id`) ON UPDATE NO ACTION ON DELETE CASCADE
                    )"""
                )
                db.execSQL("CREATE UNIQUE INDEX IF NOT EXISTS `index_hint_usage_answer_id_hint_id` ON `hint_usage` (`answer_id`, `hint_id`)")
                db.execSQL("CREATE INDEX IF NOT EXISTS `index_hint_usage_hint_id` ON `hint_usage` (`hint_id`)")

                // 5. Create question_tag table
                db.execSQL(
                    """CREATE TABLE IF NOT EXISTS `question_tag` (
                        `question_id` INTEGER NOT NULL,
                        `tag_id` INTEGER NOT NULL,
                        `created_at` INTEGER NOT NULL,
                        PRIMARY KEY(`question_id`, `tag_id`),
                        FOREIGN KEY(`question_id`) REFERENCES `question`(`id`) ON UPDATE NO ACTION ON DELETE CASCADE,
                        FOREIGN KEY(`tag_id`) REFERENCES `tag`(`id`) ON UPDATE NO ACTION ON DELETE CASCADE
                    )"""
                )
                db.execSQL("CREATE INDEX IF NOT EXISTS `index_question_tag_question_id` ON `question_tag` (`question_id`)")
                db.execSQL("CREATE INDEX IF NOT EXISTS `index_question_tag_tag_id` ON `question_tag` (`tag_id`)")

                // 6. Create saved_trick table
                db.execSQL(
                    """CREATE TABLE IF NOT EXISTS `saved_trick` (
                        `trick_id` INTEGER NOT NULL,
                        `created_at` INTEGER NOT NULL,
                        PRIMARY KEY(`trick_id`),
                        FOREIGN KEY(`trick_id`) REFERENCES `trick`(`id`) ON UPDATE NO ACTION ON DELETE CASCADE
                    )"""
                )
            }
        }

        val MIGRATION_3_4 = object : Migration(3, 4) {
            override fun migrate(db: SupportSQLiteDatabase) {
                // 1. Create mistake table
                db.execSQL(
                    """CREATE TABLE IF NOT EXISTS `mistake` (
                        `question_id` INTEGER PRIMARY KEY NOT NULL,
                        `first_wrong_at` INTEGER NOT NULL,
                        `last_wrong_at` INTEGER NOT NULL,
                        `wrong_count` INTEGER NOT NULL DEFAULT 1,
                        `consecutive_correct` INTEGER NOT NULL DEFAULT 0,
                        `status` TEXT NOT NULL DEFAULT 'ACTIVE',
                        FOREIGN KEY(`question_id`) REFERENCES `question`(`id`) ON UPDATE NO ACTION ON DELETE CASCADE
                    )"""
                )

                // 2. Create revision_item table
                db.execSQL(
                    """CREATE TABLE IF NOT EXISTS `revision_item` (
                        `question_id` INTEGER PRIMARY KEY NOT NULL,
                        `box` INTEGER NOT NULL DEFAULT 0,
                        `due_at` INTEGER NOT NULL,
                        `last_reviewed_at` INTEGER,
                        `review_count` INTEGER NOT NULL DEFAULT 0,
                        `suspended` INTEGER NOT NULL DEFAULT 0,
                        FOREIGN KEY(`question_id`) REFERENCES `question`(`id`) ON UPDATE NO ACTION ON DELETE CASCADE
                    )"""
                )
                db.execSQL("CREATE INDEX IF NOT EXISTS `index_revision_item_due_at` ON `revision_item` (`due_at`)")
                db.execSQL("CREATE INDEX IF NOT EXISTS `index_revision_item_box` ON `revision_item` (`box`)")

                // 3. Create revision_reason table
                db.execSQL(
                    """CREATE TABLE IF NOT EXISTS `revision_reason` (
                        `question_id` INTEGER NOT NULL,
                        `reason` TEXT NOT NULL,
                        `created_at` INTEGER NOT NULL,
                        PRIMARY KEY(`question_id`, `reason`),
                        FOREIGN KEY(`question_id`) REFERENCES `question`(`id`) ON UPDATE NO ACTION ON DELETE CASCADE
                    )"""
                )

                // 4. Create study_day table
                db.execSQL(
                    """CREATE TABLE IF NOT EXISTS `study_day` (
                        `date` TEXT PRIMARY KEY NOT NULL,
                        `questions_attempted` INTEGER NOT NULL DEFAULT 0,
                        `correct` INTEGER NOT NULL DEFAULT 0,
                        `active_ms` INTEGER NOT NULL DEFAULT 0
                    )"""
                )
            }
        }

        val MIGRATION_4_5 = object : Migration(4, 5) {
            override fun migrate(db: SupportSQLiteDatabase) {
                // 1. Create FTS4 virtual tables
                db.execSQL(
                    "CREATE VIRTUAL TABLE IF NOT EXISTS `question_fts` USING FTS4(`text` TEXT NOT NULL, tokenize=unicode61, content=`question`)"
                )
                db.execSQL(
                    "CREATE VIRTUAL TABLE IF NOT EXISTS `question_option_fts` USING FTS4(`text` TEXT NOT NULL, tokenize=unicode61, content=`question_option`)"
                )
                db.execSQL(
                    "CREATE VIRTUAL TABLE IF NOT EXISTS `trick_fts` USING FTS4(`name` TEXT NOT NULL, `description` TEXT NOT NULL, tokenize=unicode61, content=`trick`)"
                )
                db.execSQL(
                    "CREATE VIRTUAL TABLE IF NOT EXISTS `test_fts` USING FTS4(`title` TEXT NOT NULL, tokenize=unicode61, content=`test`)"
                )

                // 2. Triggers for question_fts
                db.execSQL("CREATE TRIGGER IF NOT EXISTS room_fts_content_sync_question_fts_BEFORE_UPDATE BEFORE UPDATE ON `question` BEGIN DELETE FROM `question_fts` WHERE `docid`=OLD.`rowid`; END")
                db.execSQL("CREATE TRIGGER IF NOT EXISTS room_fts_content_sync_question_fts_BEFORE_DELETE BEFORE DELETE ON `question` BEGIN DELETE FROM `question_fts` WHERE `docid`=OLD.`rowid`; END")
                db.execSQL("CREATE TRIGGER IF NOT EXISTS room_fts_content_sync_question_fts_AFTER_UPDATE AFTER UPDATE ON `question` BEGIN INSERT INTO `question_fts`(`docid`, `text`) VALUES (NEW.`rowid`, NEW.`text`); END")
                db.execSQL("CREATE TRIGGER IF NOT EXISTS room_fts_content_sync_question_fts_AFTER_INSERT AFTER INSERT ON `question` BEGIN INSERT INTO `question_fts`(`docid`, `text`) VALUES (NEW.`rowid`, NEW.`text`); END")

                // 3. Triggers for question_option_fts
                db.execSQL("CREATE TRIGGER IF NOT EXISTS room_fts_content_sync_question_option_fts_BEFORE_UPDATE BEFORE UPDATE ON `question_option` BEGIN DELETE FROM `question_option_fts` WHERE `docid`=OLD.`rowid`; END")
                db.execSQL("CREATE TRIGGER IF NOT EXISTS room_fts_content_sync_question_option_fts_BEFORE_DELETE BEFORE DELETE ON `question_option` BEGIN DELETE FROM `question_option_fts` WHERE `docid`=OLD.`rowid`; END")
                db.execSQL("CREATE TRIGGER IF NOT EXISTS room_fts_content_sync_question_option_fts_AFTER_UPDATE AFTER UPDATE ON `question_option` BEGIN INSERT INTO `question_option_fts`(`docid`, `text`) VALUES (NEW.`rowid`, NEW.`text`); END")
                db.execSQL("CREATE TRIGGER IF NOT EXISTS room_fts_content_sync_question_option_fts_AFTER_INSERT AFTER INSERT ON `question_option` BEGIN INSERT INTO `question_option_fts`(`docid`, `text`) VALUES (NEW.`rowid`, NEW.`text`); END")

                // 4. Triggers for trick_fts
                db.execSQL("CREATE TRIGGER IF NOT EXISTS room_fts_content_sync_trick_fts_BEFORE_UPDATE BEFORE UPDATE ON `trick` BEGIN DELETE FROM `trick_fts` WHERE `docid`=OLD.`rowid`; END")
                db.execSQL("CREATE TRIGGER IF NOT EXISTS room_fts_content_sync_trick_fts_BEFORE_DELETE BEFORE DELETE ON `trick` BEGIN DELETE FROM `trick_fts` WHERE `docid`=OLD.`rowid`; END")
                db.execSQL("CREATE TRIGGER IF NOT EXISTS room_fts_content_sync_trick_fts_AFTER_UPDATE AFTER UPDATE ON `trick` BEGIN INSERT INTO `trick_fts`(`docid`, `name`, `description`) VALUES (NEW.`rowid`, NEW.`name`, NEW.`description`); END")
                db.execSQL("CREATE TRIGGER IF NOT EXISTS room_fts_content_sync_trick_fts_AFTER_INSERT AFTER INSERT ON `trick` BEGIN INSERT INTO `trick_fts`(`docid`, `name`, `description`) VALUES (NEW.`rowid`, NEW.`name`, NEW.`description`); END")

                // 5. Triggers for test_fts
                db.execSQL("CREATE TRIGGER IF NOT EXISTS room_fts_content_sync_test_fts_BEFORE_UPDATE BEFORE UPDATE ON `test` BEGIN DELETE FROM `test_fts` WHERE `docid`=OLD.`rowid`; END")
                db.execSQL("CREATE TRIGGER IF NOT EXISTS room_fts_content_sync_test_fts_BEFORE_DELETE BEFORE DELETE ON `test` BEGIN DELETE FROM `test_fts` WHERE `docid`=OLD.`rowid`; END")
                db.execSQL("CREATE TRIGGER IF NOT EXISTS room_fts_content_sync_test_fts_AFTER_UPDATE AFTER UPDATE ON `test` BEGIN INSERT INTO `test_fts`(`docid`, `title`) VALUES (NEW.`rowid`, NEW.`title`); END")
                db.execSQL("CREATE TRIGGER IF NOT EXISTS room_fts_content_sync_test_fts_AFTER_INSERT AFTER INSERT ON `test` BEGIN INSERT INTO `test_fts`(`docid`, `title`) VALUES (NEW.`rowid`, NEW.`title`); END")

                // 6. Rebuild initial indexes from existing content
                db.execSQL("INSERT INTO question_fts(question_fts) VALUES('rebuild')")
                db.execSQL("INSERT INTO question_option_fts(question_option_fts) VALUES('rebuild')")
                db.execSQL("INSERT INTO trick_fts(trick_fts) VALUES('rebuild')")
                db.execSQL("INSERT INTO test_fts(test_fts) VALUES('rebuild')")
            }
        }

        val MIGRATION_5_6 = object : Migration(5, 6) {
            override fun migrate(db: SupportSQLiteDatabase) {
                db.execSQL(
                    """
                    CREATE TABLE IF NOT EXISTS `ai_conversations` (
                        `id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
                        `uuid` TEXT NOT NULL,
                        `question_id` INTEGER NOT NULL,
                        `provider_id` TEXT NOT NULL,
                        `model` TEXT NOT NULL,
                        `title` TEXT NOT NULL,
                        `created_at` INTEGER NOT NULL,
                        `updated_at` INTEGER NOT NULL,
                        FOREIGN KEY(`question_id`) REFERENCES `question`(`id`) ON UPDATE NO ACTION ON DELETE CASCADE
                    )
                    """.trimIndent()
                )
                db.execSQL("CREATE INDEX IF NOT EXISTS `index_ai_conversations_question_id` ON `ai_conversations` (`question_id`)")
                db.execSQL("CREATE INDEX IF NOT EXISTS `index_ai_conversations_created_at` ON `ai_conversations` (`created_at`)")

                db.execSQL(
                    """
                    CREATE TABLE IF NOT EXISTS `ai_messages` (
                        `id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
                        `conversation_id` INTEGER NOT NULL,
                        `role` TEXT NOT NULL,
                        `content` TEXT NOT NULL,
                        `created_at` INTEGER NOT NULL,
                        FOREIGN KEY(`conversation_id`) REFERENCES `ai_conversations`(`id`) ON UPDATE NO ACTION ON DELETE CASCADE
                    )
                    """.trimIndent()
                )
                db.execSQL("CREATE INDEX IF NOT EXISTS `index_ai_messages_conversation_id` ON `ai_messages` (`conversation_id`)")
                db.execSQL("CREATE INDEX IF NOT EXISTS `index_ai_messages_created_at` ON `ai_messages` (`created_at`)")
            }
        }
    }



    /**
     * Seeds the database with default exams and system tags on first creation.
     */
    class SeedCallback(private val context: Context) : Callback() {
        override fun onCreate(db: SupportSQLiteDatabase) {
            super.onCreate(db)

            db.execSQL("PRAGMA foreign_keys = ON")
            val now = System.currentTimeMillis()

            val exams = listOf(
                "SSC CGL", "SSC CHSL", "SSC MTS", "SSC CPO", "SSC Steno",
                "IBPS PO", "IBPS Clerk", "IBPS SO",
                "SBI PO", "SBI Clerk",
                "RBI Grade B", "RBI Assistant",
                "NIACL AO", "LIC AAO", "LIC ADO",
                "RRB NTPC", "RRB Group D", "RRB ALP", "RRB JE",
                "NDA", "CDS", "AFCAT",
                "UPSC CSAT", "State PSC",
                "CAT", "MAT", "XAT", "GATE",
                "Placement Aptitude", "Custom",
            )
            exams.forEachIndexed { index, name ->
                db.execSQL(
                    """INSERT OR IGNORE INTO exam (uuid, name, is_builtin, sort_order, created_at, updated_at)
                       VALUES (?, ?, 1, ?, ?, ?)""",
                    arrayOf<Any>(UUID.randomUUID().toString(), name, index, now, now),
                )
            }

            val systemTags = listOf(
                "Important" to "#6366F1",
                "Revise Later" to "#F59E0B",
                "Useful Trick" to "#10B981",
                "Difficult" to "#EF4444",
                "Suspected Wrong Key" to "#F97316",
            )
            systemTags.forEach { (name, color) ->
                db.execSQL(
                    """INSERT OR IGNORE INTO tag (uuid, name, color, is_system, created_at, updated_at)
                       VALUES (?, ?, ?, 1, ?, ?)""",
                    arrayOf<Any>(UUID.randomUUID().toString(), name, color, now, now),
                )
            }

            db.execSQL(
                """INSERT OR IGNORE INTO user_profile (id, daily_goal_questions, daily_goal_minutes, created_at, updated_at)
                   VALUES (1, 20, 30, ?, ?)""",
                arrayOf<Any>(now, now),
            )
        }
    }
}
