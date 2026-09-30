package app.apti.villa.`data`.local.db

import androidx.room.InvalidationTracker
import androidx.room.RoomOpenDelegate
import androidx.room.migration.AutoMigrationSpec
import androidx.room.migration.Migration
import androidx.room.util.FtsTableInfo
import androidx.room.util.TableInfo
import androidx.room.util.dropFtsSyncTriggers
import androidx.sqlite.SQLiteConnection
import androidx.sqlite.execSQL
import app.apti.villa.`data`.local.dao.AiConversationDao
import app.apti.villa.`data`.local.dao.AiConversationDao_Impl
import app.apti.villa.`data`.local.dao.AnalyticsDao
import app.apti.villa.`data`.local.dao.AnalyticsDao_Impl
import app.apti.villa.`data`.local.dao.AnswerDao
import app.apti.villa.`data`.local.dao.AnswerDao_Impl
import app.apti.villa.`data`.local.dao.AnswerSubmissionDao
import app.apti.villa.`data`.local.dao.AnswerSubmissionDao_Impl
import app.apti.villa.`data`.local.dao.AttemptDao
import app.apti.villa.`data`.local.dao.AttemptDao_Impl
import app.apti.villa.`data`.local.dao.ExamDao
import app.apti.villa.`data`.local.dao.ExamDao_Impl
import app.apti.villa.`data`.local.dao.HintDao
import app.apti.villa.`data`.local.dao.HintDao_Impl
import app.apti.villa.`data`.local.dao.HintUsageDao
import app.apti.villa.`data`.local.dao.HintUsageDao_Impl
import app.apti.villa.`data`.local.dao.InventoryDao
import app.apti.villa.`data`.local.dao.InventoryDao_Impl
import app.apti.villa.`data`.local.dao.MistakeDao
import app.apti.villa.`data`.local.dao.MistakeDao_Impl
import app.apti.villa.`data`.local.dao.QuestionDao
import app.apti.villa.`data`.local.dao.QuestionDao_Impl
import app.apti.villa.`data`.local.dao.QuestionOptionDao
import app.apti.villa.`data`.local.dao.QuestionOptionDao_Impl
import app.apti.villa.`data`.local.dao.QuestionTagDao
import app.apti.villa.`data`.local.dao.QuestionTagDao_Impl
import app.apti.villa.`data`.local.dao.RevisionDao
import app.apti.villa.`data`.local.dao.RevisionDao_Impl
import app.apti.villa.`data`.local.dao.SavedTrickDao
import app.apti.villa.`data`.local.dao.SavedTrickDao_Impl
import app.apti.villa.`data`.local.dao.SearchDao
import app.apti.villa.`data`.local.dao.SearchDao_Impl
import app.apti.villa.`data`.local.dao.SolutionStepDao
import app.apti.villa.`data`.local.dao.SolutionStepDao_Impl
import app.apti.villa.`data`.local.dao.SourceDao
import app.apti.villa.`data`.local.dao.SourceDao_Impl
import app.apti.villa.`data`.local.dao.StudyDayDao
import app.apti.villa.`data`.local.dao.StudyDayDao_Impl
import app.apti.villa.`data`.local.dao.SubTopicDao
import app.apti.villa.`data`.local.dao.SubTopicDao_Impl
import app.apti.villa.`data`.local.dao.SubjectDao
import app.apti.villa.`data`.local.dao.SubjectDao_Impl
import app.apti.villa.`data`.local.dao.TagDao
import app.apti.villa.`data`.local.dao.TagDao_Impl
import app.apti.villa.`data`.local.dao.TestDao
import app.apti.villa.`data`.local.dao.TestDao_Impl
import app.apti.villa.`data`.local.dao.TestQuestionDao
import app.apti.villa.`data`.local.dao.TestQuestionDao_Impl
import app.apti.villa.`data`.local.dao.TopicDao
import app.apti.villa.`data`.local.dao.TopicDao_Impl
import app.apti.villa.`data`.local.dao.TrickDao
import app.apti.villa.`data`.local.dao.TrickDao_Impl
import app.apti.villa.`data`.local.dao.UserProfileDao
import app.apti.villa.`data`.local.dao.UserProfileDao_Impl
import javax.`annotation`.processing.Generated
import kotlin.Lazy
import kotlin.String
import kotlin.Suppress
import kotlin.collections.List
import kotlin.collections.Map
import kotlin.collections.MutableList
import kotlin.collections.MutableMap
import kotlin.collections.MutableSet
import kotlin.collections.Set
import kotlin.collections.mutableListOf
import kotlin.collections.mutableMapOf
import kotlin.collections.mutableSetOf
import kotlin.reflect.KClass
import androidx.room.util.FtsTableInfo.Companion.read as ftsTableInfoRead
import androidx.room.util.TableInfo.Companion.read as tableInfoRead

@Generated(value = ["androidx.room.RoomProcessor"])
@Suppress(names = ["UNCHECKED_CAST", "DEPRECATION", "REDUNDANT_PROJECTION", "REMOVAL"])
public class AppDatabase_Impl : AppDatabase() {
  private val _examDao: Lazy<ExamDao> = lazy {
    ExamDao_Impl(this)
  }

  private val _subjectDao: Lazy<SubjectDao> = lazy {
    SubjectDao_Impl(this)
  }

  private val _topicDao: Lazy<TopicDao> = lazy {
    TopicDao_Impl(this)
  }

  private val _subTopicDao: Lazy<SubTopicDao> = lazy {
    SubTopicDao_Impl(this)
  }

  private val _tagDao: Lazy<TagDao> = lazy {
    TagDao_Impl(this)
  }

  private val _userProfileDao: Lazy<UserProfileDao> = lazy {
    UserProfileDao_Impl(this)
  }

  private val _sourceDao: Lazy<SourceDao> = lazy {
    SourceDao_Impl(this)
  }

  private val _testDao: Lazy<TestDao> = lazy {
    TestDao_Impl(this)
  }

  private val _questionDao: Lazy<QuestionDao> = lazy {
    QuestionDao_Impl(this)
  }

  private val _questionOptionDao: Lazy<QuestionOptionDao> = lazy {
    QuestionOptionDao_Impl(this)
  }

  private val _testQuestionDao: Lazy<TestQuestionDao> = lazy {
    TestQuestionDao_Impl(this)
  }

  private val _hintDao: Lazy<HintDao> = lazy {
    HintDao_Impl(this)
  }

  private val _trickDao: Lazy<TrickDao> = lazy {
    TrickDao_Impl(this)
  }

  private val _solutionStepDao: Lazy<SolutionStepDao> = lazy {
    SolutionStepDao_Impl(this)
  }

  private val _attemptDao: Lazy<AttemptDao> = lazy {
    AttemptDao_Impl(this)
  }

  private val _answerDao: Lazy<AnswerDao> = lazy {
    AnswerDao_Impl(this)
  }

  private val _answerSubmissionDao: Lazy<AnswerSubmissionDao> = lazy {
    AnswerSubmissionDao_Impl(this)
  }

  private val _hintUsageDao: Lazy<HintUsageDao> = lazy {
    HintUsageDao_Impl(this)
  }

  private val _questionTagDao: Lazy<QuestionTagDao> = lazy {
    QuestionTagDao_Impl(this)
  }

  private val _savedTrickDao: Lazy<SavedTrickDao> = lazy {
    SavedTrickDao_Impl(this)
  }

  private val _mistakeDao: Lazy<MistakeDao> = lazy {
    MistakeDao_Impl(this)
  }

  private val _revisionDao: Lazy<RevisionDao> = lazy {
    RevisionDao_Impl(this)
  }

  private val _studyDayDao: Lazy<StudyDayDao> = lazy {
    StudyDayDao_Impl(this)
  }

  private val _analyticsDao: Lazy<AnalyticsDao> = lazy {
    AnalyticsDao_Impl(this)
  }

  private val _inventoryDao: Lazy<InventoryDao> = lazy {
    InventoryDao_Impl(this)
  }

  private val _searchDao: Lazy<SearchDao> = lazy {
    SearchDao_Impl(this)
  }

  private val _aiConversationDao: Lazy<AiConversationDao> = lazy {
    AiConversationDao_Impl(this)
  }

  protected override fun createOpenDelegate(): RoomOpenDelegate {
    val _openDelegate: RoomOpenDelegate = object : RoomOpenDelegate(6,
        "55d29c52ae2f1fe927a2b74a8ccb9056", "067a8e8dcb1eeff6c568c23a99a61f80") {
      public override fun createAllTables(connection: SQLiteConnection) {
        connection.execSQL("CREATE TABLE IF NOT EXISTS `exam` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `uuid` TEXT NOT NULL, `name` TEXT NOT NULL, `is_builtin` INTEGER NOT NULL, `sort_order` INTEGER NOT NULL, `created_at` INTEGER NOT NULL, `updated_at` INTEGER NOT NULL)")
        connection.execSQL("CREATE UNIQUE INDEX IF NOT EXISTS `index_exam_uuid` ON `exam` (`uuid`)")
        connection.execSQL("CREATE TABLE IF NOT EXISTS `subject` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `name` TEXT NOT NULL, `created_at` INTEGER NOT NULL, `updated_at` INTEGER NOT NULL)")
        connection.execSQL("CREATE TABLE IF NOT EXISTS `topic` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `subject_id` INTEGER, `name` TEXT NOT NULL, `created_at` INTEGER NOT NULL, `updated_at` INTEGER NOT NULL, FOREIGN KEY(`subject_id`) REFERENCES `subject`(`id`) ON UPDATE NO ACTION ON DELETE SET NULL )")
        connection.execSQL("CREATE UNIQUE INDEX IF NOT EXISTS `index_topic_subject_id_name` ON `topic` (`subject_id`, `name`)")
        connection.execSQL("CREATE INDEX IF NOT EXISTS `index_topic_subject_id` ON `topic` (`subject_id`)")
        connection.execSQL("CREATE TABLE IF NOT EXISTS `sub_topic` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `topic_id` INTEGER NOT NULL, `name` TEXT NOT NULL, `created_at` INTEGER NOT NULL, `updated_at` INTEGER NOT NULL, FOREIGN KEY(`topic_id`) REFERENCES `topic`(`id`) ON UPDATE NO ACTION ON DELETE CASCADE )")
        connection.execSQL("CREATE UNIQUE INDEX IF NOT EXISTS `index_sub_topic_topic_id_name` ON `sub_topic` (`topic_id`, `name`)")
        connection.execSQL("CREATE INDEX IF NOT EXISTS `index_sub_topic_topic_id` ON `sub_topic` (`topic_id`)")
        connection.execSQL("CREATE TABLE IF NOT EXISTS `tag` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `uuid` TEXT NOT NULL, `name` TEXT NOT NULL, `color` TEXT, `is_system` INTEGER NOT NULL, `created_at` INTEGER NOT NULL, `updated_at` INTEGER NOT NULL)")
        connection.execSQL("CREATE UNIQUE INDEX IF NOT EXISTS `index_tag_uuid` ON `tag` (`uuid`)")
        connection.execSQL("CREATE TABLE IF NOT EXISTS `user_profile` (`id` INTEGER NOT NULL, `name` TEXT, `preferred_difficulty` TEXT, `daily_goal_questions` INTEGER NOT NULL, `daily_goal_minutes` INTEGER NOT NULL, `prep_start_date` TEXT, `target_exam_date` TEXT, `created_at` INTEGER NOT NULL, `updated_at` INTEGER NOT NULL, PRIMARY KEY(`id`))")
        connection.execSQL("CREATE TABLE IF NOT EXISTS `source` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `uuid` TEXT NOT NULL, `title` TEXT NOT NULL, `type` TEXT NOT NULL, `url` TEXT, `channel` TEXT, `schema_version` INTEGER NOT NULL, `content_hash` TEXT NOT NULL, `imported_at` INTEGER NOT NULL, `import_report_json` TEXT, `created_at` INTEGER NOT NULL, `updated_at` INTEGER NOT NULL)")
        connection.execSQL("CREATE UNIQUE INDEX IF NOT EXISTS `index_source_uuid` ON `source` (`uuid`)")
        connection.execSQL("CREATE INDEX IF NOT EXISTS `index_source_content_hash` ON `source` (`content_hash`)")
        connection.execSQL("CREATE TABLE IF NOT EXISTS `test` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `uuid` TEXT NOT NULL, `source_id` INTEGER, `exam_id` INTEGER, `title` TEXT NOT NULL, `kind` TEXT NOT NULL, `duration_sec` INTEGER, `marks_correct` REAL NOT NULL, `negative_marks` REAL NOT NULL, `archived` INTEGER NOT NULL, `created_at` INTEGER NOT NULL, `updated_at` INTEGER NOT NULL, FOREIGN KEY(`source_id`) REFERENCES `source`(`id`) ON UPDATE NO ACTION ON DELETE SET NULL , FOREIGN KEY(`exam_id`) REFERENCES `exam`(`id`) ON UPDATE NO ACTION ON DELETE SET NULL )")
        connection.execSQL("CREATE UNIQUE INDEX IF NOT EXISTS `index_test_uuid` ON `test` (`uuid`)")
        connection.execSQL("CREATE INDEX IF NOT EXISTS `index_test_source_id` ON `test` (`source_id`)")
        connection.execSQL("CREATE INDEX IF NOT EXISTS `index_test_exam_id` ON `test` (`exam_id`)")
        connection.execSQL("CREATE TABLE IF NOT EXISTS `question` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `uuid` TEXT NOT NULL, `source_id` INTEGER NOT NULL, `external_id` TEXT NOT NULL, `content_hash` TEXT NOT NULL, `text` TEXT NOT NULL, `format` TEXT NOT NULL, `subject_id` INTEGER, `topic_id` INTEGER, `sub_topic_id` INTEGER, `difficulty` TEXT, `source_timestamp_sec` INTEGER, `source_url` TEXT, `language` TEXT, `has_solution` INTEGER NOT NULL, `created_at` INTEGER NOT NULL, `updated_at` INTEGER NOT NULL, FOREIGN KEY(`source_id`) REFERENCES `source`(`id`) ON UPDATE NO ACTION ON DELETE CASCADE , FOREIGN KEY(`subject_id`) REFERENCES `subject`(`id`) ON UPDATE NO ACTION ON DELETE SET NULL , FOREIGN KEY(`topic_id`) REFERENCES `topic`(`id`) ON UPDATE NO ACTION ON DELETE SET NULL , FOREIGN KEY(`sub_topic_id`) REFERENCES `sub_topic`(`id`) ON UPDATE NO ACTION ON DELETE SET NULL )")
        connection.execSQL("CREATE UNIQUE INDEX IF NOT EXISTS `index_question_uuid` ON `question` (`uuid`)")
        connection.execSQL("CREATE UNIQUE INDEX IF NOT EXISTS `index_question_source_id_external_id` ON `question` (`source_id`, `external_id`)")
        connection.execSQL("CREATE INDEX IF NOT EXISTS `index_question_content_hash` ON `question` (`content_hash`)")
        connection.execSQL("CREATE INDEX IF NOT EXISTS `index_question_subject_id` ON `question` (`subject_id`)")
        connection.execSQL("CREATE INDEX IF NOT EXISTS `index_question_topic_id` ON `question` (`topic_id`)")
        connection.execSQL("CREATE INDEX IF NOT EXISTS `index_question_sub_topic_id` ON `question` (`sub_topic_id`)")
        connection.execSQL("CREATE TABLE IF NOT EXISTS `question_option` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `question_id` INTEGER NOT NULL, `position` INTEGER NOT NULL, `text` TEXT NOT NULL, `is_correct` INTEGER NOT NULL, FOREIGN KEY(`question_id`) REFERENCES `question`(`id`) ON UPDATE NO ACTION ON DELETE CASCADE )")
        connection.execSQL("CREATE INDEX IF NOT EXISTS `index_question_option_question_id` ON `question_option` (`question_id`)")
        connection.execSQL("CREATE TABLE IF NOT EXISTS `test_question` (`test_id` INTEGER NOT NULL, `question_id` INTEGER NOT NULL, `position` INTEGER NOT NULL, PRIMARY KEY(`test_id`, `question_id`), FOREIGN KEY(`test_id`) REFERENCES `test`(`id`) ON UPDATE NO ACTION ON DELETE CASCADE , FOREIGN KEY(`question_id`) REFERENCES `question`(`id`) ON UPDATE NO ACTION ON DELETE CASCADE )")
        connection.execSQL("CREATE INDEX IF NOT EXISTS `index_test_question_test_id` ON `test_question` (`test_id`)")
        connection.execSQL("CREATE INDEX IF NOT EXISTS `index_test_question_question_id` ON `test_question` (`question_id`)")
        connection.execSQL("CREATE TABLE IF NOT EXISTS `hint` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `question_id` INTEGER NOT NULL, `number` INTEGER NOT NULL, `title` TEXT NOT NULL, `description` TEXT NOT NULL, FOREIGN KEY(`question_id`) REFERENCES `question`(`id`) ON UPDATE NO ACTION ON DELETE CASCADE )")
        connection.execSQL("CREATE INDEX IF NOT EXISTS `index_hint_question_id` ON `hint` (`question_id`)")
        connection.execSQL("CREATE TABLE IF NOT EXISTS `trick` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `question_id` INTEGER NOT NULL, `name` TEXT NOT NULL, `description` TEXT NOT NULL, FOREIGN KEY(`question_id`) REFERENCES `question`(`id`) ON UPDATE NO ACTION ON DELETE CASCADE )")
        connection.execSQL("CREATE INDEX IF NOT EXISTS `index_trick_question_id` ON `trick` (`question_id`)")
        connection.execSQL("CREATE TABLE IF NOT EXISTS `solution_step` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `question_id` INTEGER NOT NULL, `number` INTEGER NOT NULL, `action` TEXT NOT NULL, FOREIGN KEY(`question_id`) REFERENCES `question`(`id`) ON UPDATE NO ACTION ON DELETE CASCADE )")
        connection.execSQL("CREATE INDEX IF NOT EXISTS `index_solution_step_question_id` ON `solution_step` (`question_id`)")
        connection.execSQL("CREATE TABLE IF NOT EXISTS `attempt` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `uuid` TEXT NOT NULL, `test_id` INTEGER, `mode` TEXT NOT NULL, `scope_desc` TEXT, `status` TEXT NOT NULL, `started_at` INTEGER NOT NULL, `submitted_at` INTEGER, `time_limit_sec` INTEGER, `active_elapsed_ms` INTEGER NOT NULL DEFAULT 0, `score` REAL, `is_retry` INTEGER NOT NULL DEFAULT 0, `created_at` INTEGER NOT NULL, `updated_at` INTEGER NOT NULL, FOREIGN KEY(`test_id`) REFERENCES `test`(`id`) ON UPDATE NO ACTION ON DELETE SET NULL )")
        connection.execSQL("CREATE UNIQUE INDEX IF NOT EXISTS `index_attempt_uuid` ON `attempt` (`uuid`)")
        connection.execSQL("CREATE INDEX IF NOT EXISTS `index_attempt_status` ON `attempt` (`status`)")
        connection.execSQL("CREATE INDEX IF NOT EXISTS `index_attempt_submitted_at` ON `attempt` (`submitted_at`)")
        connection.execSQL("CREATE INDEX IF NOT EXISTS `index_attempt_test_id` ON `attempt` (`test_id`)")
        connection.execSQL("CREATE INDEX IF NOT EXISTS `index_attempt_mode` ON `attempt` (`mode`)")
        connection.execSQL("CREATE TABLE IF NOT EXISTS `answer` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `attempt_id` INTEGER NOT NULL, `question_id` INTEGER NOT NULL, `position` INTEGER NOT NULL, `status` TEXT NOT NULL DEFAULT 'UNSEEN', `selected_option_id` INTEGER, `is_correct` INTEGER, `time_spent_ms` INTEGER NOT NULL DEFAULT 0, `first_seen_at` INTEGER, `answered_at` INTEGER, `first_submission_correct` INTEGER, `hints_opened` INTEGER NOT NULL DEFAULT 0, `solved_at_hint_level` INTEGER, `revealed` INTEGER NOT NULL DEFAULT 0, FOREIGN KEY(`attempt_id`) REFERENCES `attempt`(`id`) ON UPDATE NO ACTION ON DELETE CASCADE , FOREIGN KEY(`question_id`) REFERENCES `question`(`id`) ON UPDATE NO ACTION ON DELETE CASCADE , FOREIGN KEY(`selected_option_id`) REFERENCES `question_option`(`id`) ON UPDATE NO ACTION ON DELETE SET NULL )")
        connection.execSQL("CREATE UNIQUE INDEX IF NOT EXISTS `index_answer_attempt_id_question_id` ON `answer` (`attempt_id`, `question_id`)")
        connection.execSQL("CREATE INDEX IF NOT EXISTS `index_answer_question_id` ON `answer` (`question_id`)")
        connection.execSQL("CREATE INDEX IF NOT EXISTS `index_answer_is_correct` ON `answer` (`is_correct`)")
        connection.execSQL("CREATE INDEX IF NOT EXISTS `index_answer_attempt_id` ON `answer` (`attempt_id`)")
        connection.execSQL("CREATE INDEX IF NOT EXISTS `index_answer_selected_option_id` ON `answer` (`selected_option_id`)")
        connection.execSQL("CREATE TABLE IF NOT EXISTS `answer_submission` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `answer_id` INTEGER NOT NULL, `option_id` INTEGER NOT NULL, `submitted_at` INTEGER NOT NULL, `hints_open_at_submit` INTEGER NOT NULL, `ms_since_first_seen` INTEGER NOT NULL, `is_correct` INTEGER NOT NULL, FOREIGN KEY(`answer_id`) REFERENCES `answer`(`id`) ON UPDATE NO ACTION ON DELETE CASCADE , FOREIGN KEY(`option_id`) REFERENCES `question_option`(`id`) ON UPDATE NO ACTION ON DELETE CASCADE )")
        connection.execSQL("CREATE INDEX IF NOT EXISTS `index_answer_submission_answer_id` ON `answer_submission` (`answer_id`)")
        connection.execSQL("CREATE INDEX IF NOT EXISTS `index_answer_submission_option_id` ON `answer_submission` (`option_id`)")
        connection.execSQL("CREATE TABLE IF NOT EXISTS `hint_usage` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `answer_id` INTEGER NOT NULL, `hint_id` INTEGER NOT NULL, `opened_at` INTEGER NOT NULL, `ms_since_first_seen` INTEGER NOT NULL, FOREIGN KEY(`answer_id`) REFERENCES `answer`(`id`) ON UPDATE NO ACTION ON DELETE CASCADE , FOREIGN KEY(`hint_id`) REFERENCES `hint`(`id`) ON UPDATE NO ACTION ON DELETE CASCADE )")
        connection.execSQL("CREATE UNIQUE INDEX IF NOT EXISTS `index_hint_usage_answer_id_hint_id` ON `hint_usage` (`answer_id`, `hint_id`)")
        connection.execSQL("CREATE INDEX IF NOT EXISTS `index_hint_usage_hint_id` ON `hint_usage` (`hint_id`)")
        connection.execSQL("CREATE TABLE IF NOT EXISTS `question_tag` (`question_id` INTEGER NOT NULL, `tag_id` INTEGER NOT NULL, `created_at` INTEGER NOT NULL, PRIMARY KEY(`question_id`, `tag_id`), FOREIGN KEY(`question_id`) REFERENCES `question`(`id`) ON UPDATE NO ACTION ON DELETE CASCADE , FOREIGN KEY(`tag_id`) REFERENCES `tag`(`id`) ON UPDATE NO ACTION ON DELETE CASCADE )")
        connection.execSQL("CREATE INDEX IF NOT EXISTS `index_question_tag_question_id` ON `question_tag` (`question_id`)")
        connection.execSQL("CREATE INDEX IF NOT EXISTS `index_question_tag_tag_id` ON `question_tag` (`tag_id`)")
        connection.execSQL("CREATE TABLE IF NOT EXISTS `saved_trick` (`trick_id` INTEGER NOT NULL, `created_at` INTEGER NOT NULL, PRIMARY KEY(`trick_id`), FOREIGN KEY(`trick_id`) REFERENCES `trick`(`id`) ON UPDATE NO ACTION ON DELETE CASCADE )")
        connection.execSQL("CREATE TABLE IF NOT EXISTS `mistake` (`question_id` INTEGER NOT NULL, `first_wrong_at` INTEGER NOT NULL, `last_wrong_at` INTEGER NOT NULL, `wrong_count` INTEGER NOT NULL DEFAULT 1, `consecutive_correct` INTEGER NOT NULL DEFAULT 0, `status` TEXT NOT NULL DEFAULT 'ACTIVE', PRIMARY KEY(`question_id`), FOREIGN KEY(`question_id`) REFERENCES `question`(`id`) ON UPDATE NO ACTION ON DELETE CASCADE )")
        connection.execSQL("CREATE TABLE IF NOT EXISTS `revision_item` (`question_id` INTEGER NOT NULL, `box` INTEGER NOT NULL DEFAULT 0, `due_at` INTEGER NOT NULL, `last_reviewed_at` INTEGER, `review_count` INTEGER NOT NULL DEFAULT 0, `suspended` INTEGER NOT NULL DEFAULT 0, PRIMARY KEY(`question_id`), FOREIGN KEY(`question_id`) REFERENCES `question`(`id`) ON UPDATE NO ACTION ON DELETE CASCADE )")
        connection.execSQL("CREATE INDEX IF NOT EXISTS `index_revision_item_due_at` ON `revision_item` (`due_at`)")
        connection.execSQL("CREATE INDEX IF NOT EXISTS `index_revision_item_box` ON `revision_item` (`box`)")
        connection.execSQL("CREATE TABLE IF NOT EXISTS `revision_reason` (`question_id` INTEGER NOT NULL, `reason` TEXT NOT NULL, `created_at` INTEGER NOT NULL, PRIMARY KEY(`question_id`, `reason`), FOREIGN KEY(`question_id`) REFERENCES `question`(`id`) ON UPDATE NO ACTION ON DELETE CASCADE )")
        connection.execSQL("CREATE TABLE IF NOT EXISTS `study_day` (`date` TEXT NOT NULL, `questions_attempted` INTEGER NOT NULL DEFAULT 0, `correct` INTEGER NOT NULL DEFAULT 0, `active_ms` INTEGER NOT NULL DEFAULT 0, PRIMARY KEY(`date`))")
        connection.execSQL("CREATE VIRTUAL TABLE IF NOT EXISTS `question_fts` USING FTS4(`text` TEXT NOT NULL, tokenize=unicode61, content=`question`)")
        connection.execSQL("CREATE TRIGGER IF NOT EXISTS room_fts_content_sync_question_fts_BEFORE_UPDATE BEFORE UPDATE ON `question` BEGIN DELETE FROM `question_fts` WHERE `docid`=OLD.`rowid`; END")
        connection.execSQL("CREATE TRIGGER IF NOT EXISTS room_fts_content_sync_question_fts_BEFORE_DELETE BEFORE DELETE ON `question` BEGIN DELETE FROM `question_fts` WHERE `docid`=OLD.`rowid`; END")
        connection.execSQL("CREATE TRIGGER IF NOT EXISTS room_fts_content_sync_question_fts_AFTER_UPDATE AFTER UPDATE ON `question` BEGIN INSERT INTO `question_fts`(`docid`, `text`) VALUES (NEW.`rowid`, NEW.`text`); END")
        connection.execSQL("CREATE TRIGGER IF NOT EXISTS room_fts_content_sync_question_fts_AFTER_INSERT AFTER INSERT ON `question` BEGIN INSERT INTO `question_fts`(`docid`, `text`) VALUES (NEW.`rowid`, NEW.`text`); END")
        connection.execSQL("CREATE VIRTUAL TABLE IF NOT EXISTS `question_option_fts` USING FTS4(`text` TEXT NOT NULL, tokenize=unicode61, content=`question_option`)")
        connection.execSQL("CREATE TRIGGER IF NOT EXISTS room_fts_content_sync_question_option_fts_BEFORE_UPDATE BEFORE UPDATE ON `question_option` BEGIN DELETE FROM `question_option_fts` WHERE `docid`=OLD.`rowid`; END")
        connection.execSQL("CREATE TRIGGER IF NOT EXISTS room_fts_content_sync_question_option_fts_BEFORE_DELETE BEFORE DELETE ON `question_option` BEGIN DELETE FROM `question_option_fts` WHERE `docid`=OLD.`rowid`; END")
        connection.execSQL("CREATE TRIGGER IF NOT EXISTS room_fts_content_sync_question_option_fts_AFTER_UPDATE AFTER UPDATE ON `question_option` BEGIN INSERT INTO `question_option_fts`(`docid`, `text`) VALUES (NEW.`rowid`, NEW.`text`); END")
        connection.execSQL("CREATE TRIGGER IF NOT EXISTS room_fts_content_sync_question_option_fts_AFTER_INSERT AFTER INSERT ON `question_option` BEGIN INSERT INTO `question_option_fts`(`docid`, `text`) VALUES (NEW.`rowid`, NEW.`text`); END")
        connection.execSQL("CREATE VIRTUAL TABLE IF NOT EXISTS `trick_fts` USING FTS4(`name` TEXT NOT NULL, `description` TEXT NOT NULL, tokenize=unicode61, content=`trick`)")
        connection.execSQL("CREATE TRIGGER IF NOT EXISTS room_fts_content_sync_trick_fts_BEFORE_UPDATE BEFORE UPDATE ON `trick` BEGIN DELETE FROM `trick_fts` WHERE `docid`=OLD.`rowid`; END")
        connection.execSQL("CREATE TRIGGER IF NOT EXISTS room_fts_content_sync_trick_fts_BEFORE_DELETE BEFORE DELETE ON `trick` BEGIN DELETE FROM `trick_fts` WHERE `docid`=OLD.`rowid`; END")
        connection.execSQL("CREATE TRIGGER IF NOT EXISTS room_fts_content_sync_trick_fts_AFTER_UPDATE AFTER UPDATE ON `trick` BEGIN INSERT INTO `trick_fts`(`docid`, `name`, `description`) VALUES (NEW.`rowid`, NEW.`name`, NEW.`description`); END")
        connection.execSQL("CREATE TRIGGER IF NOT EXISTS room_fts_content_sync_trick_fts_AFTER_INSERT AFTER INSERT ON `trick` BEGIN INSERT INTO `trick_fts`(`docid`, `name`, `description`) VALUES (NEW.`rowid`, NEW.`name`, NEW.`description`); END")
        connection.execSQL("CREATE VIRTUAL TABLE IF NOT EXISTS `test_fts` USING FTS4(`title` TEXT NOT NULL, tokenize=unicode61, content=`test`)")
        connection.execSQL("CREATE TRIGGER IF NOT EXISTS room_fts_content_sync_test_fts_BEFORE_UPDATE BEFORE UPDATE ON `test` BEGIN DELETE FROM `test_fts` WHERE `docid`=OLD.`rowid`; END")
        connection.execSQL("CREATE TRIGGER IF NOT EXISTS room_fts_content_sync_test_fts_BEFORE_DELETE BEFORE DELETE ON `test` BEGIN DELETE FROM `test_fts` WHERE `docid`=OLD.`rowid`; END")
        connection.execSQL("CREATE TRIGGER IF NOT EXISTS room_fts_content_sync_test_fts_AFTER_UPDATE AFTER UPDATE ON `test` BEGIN INSERT INTO `test_fts`(`docid`, `title`) VALUES (NEW.`rowid`, NEW.`title`); END")
        connection.execSQL("CREATE TRIGGER IF NOT EXISTS room_fts_content_sync_test_fts_AFTER_INSERT AFTER INSERT ON `test` BEGIN INSERT INTO `test_fts`(`docid`, `title`) VALUES (NEW.`rowid`, NEW.`title`); END")
        connection.execSQL("CREATE TABLE IF NOT EXISTS `ai_conversations` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `uuid` TEXT NOT NULL, `question_id` INTEGER NOT NULL, `provider_id` TEXT NOT NULL, `model` TEXT NOT NULL, `title` TEXT NOT NULL, `created_at` INTEGER NOT NULL, `updated_at` INTEGER NOT NULL, FOREIGN KEY(`question_id`) REFERENCES `question`(`id`) ON UPDATE NO ACTION ON DELETE CASCADE )")
        connection.execSQL("CREATE INDEX IF NOT EXISTS `index_ai_conversations_question_id` ON `ai_conversations` (`question_id`)")
        connection.execSQL("CREATE INDEX IF NOT EXISTS `index_ai_conversations_created_at` ON `ai_conversations` (`created_at`)")
        connection.execSQL("CREATE TABLE IF NOT EXISTS `ai_messages` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `conversation_id` INTEGER NOT NULL, `role` TEXT NOT NULL, `content` TEXT NOT NULL, `created_at` INTEGER NOT NULL, FOREIGN KEY(`conversation_id`) REFERENCES `ai_conversations`(`id`) ON UPDATE NO ACTION ON DELETE CASCADE )")
        connection.execSQL("CREATE INDEX IF NOT EXISTS `index_ai_messages_conversation_id` ON `ai_messages` (`conversation_id`)")
        connection.execSQL("CREATE INDEX IF NOT EXISTS `index_ai_messages_created_at` ON `ai_messages` (`created_at`)")
        connection.execSQL("CREATE TABLE IF NOT EXISTS room_master_table (id INTEGER PRIMARY KEY,identity_hash TEXT)")
        connection.execSQL("INSERT OR REPLACE INTO room_master_table (id,identity_hash) VALUES(42, '55d29c52ae2f1fe927a2b74a8ccb9056')")
      }

      public override fun dropAllTables(connection: SQLiteConnection) {
        connection.execSQL("DROP TABLE IF EXISTS `exam`")
        connection.execSQL("DROP TABLE IF EXISTS `subject`")
        connection.execSQL("DROP TABLE IF EXISTS `topic`")
        connection.execSQL("DROP TABLE IF EXISTS `sub_topic`")
        connection.execSQL("DROP TABLE IF EXISTS `tag`")
        connection.execSQL("DROP TABLE IF EXISTS `user_profile`")
        connection.execSQL("DROP TABLE IF EXISTS `source`")
        connection.execSQL("DROP TABLE IF EXISTS `test`")
        connection.execSQL("DROP TABLE IF EXISTS `question`")
        connection.execSQL("DROP TABLE IF EXISTS `question_option`")
        connection.execSQL("DROP TABLE IF EXISTS `test_question`")
        connection.execSQL("DROP TABLE IF EXISTS `hint`")
        connection.execSQL("DROP TABLE IF EXISTS `trick`")
        connection.execSQL("DROP TABLE IF EXISTS `solution_step`")
        connection.execSQL("DROP TABLE IF EXISTS `attempt`")
        connection.execSQL("DROP TABLE IF EXISTS `answer`")
        connection.execSQL("DROP TABLE IF EXISTS `answer_submission`")
        connection.execSQL("DROP TABLE IF EXISTS `hint_usage`")
        connection.execSQL("DROP TABLE IF EXISTS `question_tag`")
        connection.execSQL("DROP TABLE IF EXISTS `saved_trick`")
        connection.execSQL("DROP TABLE IF EXISTS `mistake`")
        connection.execSQL("DROP TABLE IF EXISTS `revision_item`")
        connection.execSQL("DROP TABLE IF EXISTS `revision_reason`")
        connection.execSQL("DROP TABLE IF EXISTS `study_day`")
        connection.execSQL("DROP TABLE IF EXISTS `question_fts`")
        connection.execSQL("DROP TABLE IF EXISTS `question_option_fts`")
        connection.execSQL("DROP TABLE IF EXISTS `trick_fts`")
        connection.execSQL("DROP TABLE IF EXISTS `test_fts`")
        connection.execSQL("DROP TABLE IF EXISTS `ai_conversations`")
        connection.execSQL("DROP TABLE IF EXISTS `ai_messages`")
      }

      public override fun onCreate(connection: SQLiteConnection) {
      }

      public override fun onOpen(connection: SQLiteConnection) {
        connection.execSQL("PRAGMA foreign_keys = ON")
        internalInitInvalidationTracker(connection)
      }

      public override fun onPreMigrate(connection: SQLiteConnection) {
        dropFtsSyncTriggers(connection)
      }

      public override fun onPostMigrate(connection: SQLiteConnection) {
        connection.execSQL("CREATE TRIGGER IF NOT EXISTS room_fts_content_sync_question_fts_BEFORE_UPDATE BEFORE UPDATE ON `question` BEGIN DELETE FROM `question_fts` WHERE `docid`=OLD.`rowid`; END")
        connection.execSQL("CREATE TRIGGER IF NOT EXISTS room_fts_content_sync_question_fts_BEFORE_DELETE BEFORE DELETE ON `question` BEGIN DELETE FROM `question_fts` WHERE `docid`=OLD.`rowid`; END")
        connection.execSQL("CREATE TRIGGER IF NOT EXISTS room_fts_content_sync_question_fts_AFTER_UPDATE AFTER UPDATE ON `question` BEGIN INSERT INTO `question_fts`(`docid`, `text`) VALUES (NEW.`rowid`, NEW.`text`); END")
        connection.execSQL("CREATE TRIGGER IF NOT EXISTS room_fts_content_sync_question_fts_AFTER_INSERT AFTER INSERT ON `question` BEGIN INSERT INTO `question_fts`(`docid`, `text`) VALUES (NEW.`rowid`, NEW.`text`); END")
        connection.execSQL("CREATE TRIGGER IF NOT EXISTS room_fts_content_sync_question_option_fts_BEFORE_UPDATE BEFORE UPDATE ON `question_option` BEGIN DELETE FROM `question_option_fts` WHERE `docid`=OLD.`rowid`; END")
        connection.execSQL("CREATE TRIGGER IF NOT EXISTS room_fts_content_sync_question_option_fts_BEFORE_DELETE BEFORE DELETE ON `question_option` BEGIN DELETE FROM `question_option_fts` WHERE `docid`=OLD.`rowid`; END")
        connection.execSQL("CREATE TRIGGER IF NOT EXISTS room_fts_content_sync_question_option_fts_AFTER_UPDATE AFTER UPDATE ON `question_option` BEGIN INSERT INTO `question_option_fts`(`docid`, `text`) VALUES (NEW.`rowid`, NEW.`text`); END")
        connection.execSQL("CREATE TRIGGER IF NOT EXISTS room_fts_content_sync_question_option_fts_AFTER_INSERT AFTER INSERT ON `question_option` BEGIN INSERT INTO `question_option_fts`(`docid`, `text`) VALUES (NEW.`rowid`, NEW.`text`); END")
        connection.execSQL("CREATE TRIGGER IF NOT EXISTS room_fts_content_sync_trick_fts_BEFORE_UPDATE BEFORE UPDATE ON `trick` BEGIN DELETE FROM `trick_fts` WHERE `docid`=OLD.`rowid`; END")
        connection.execSQL("CREATE TRIGGER IF NOT EXISTS room_fts_content_sync_trick_fts_BEFORE_DELETE BEFORE DELETE ON `trick` BEGIN DELETE FROM `trick_fts` WHERE `docid`=OLD.`rowid`; END")
        connection.execSQL("CREATE TRIGGER IF NOT EXISTS room_fts_content_sync_trick_fts_AFTER_UPDATE AFTER UPDATE ON `trick` BEGIN INSERT INTO `trick_fts`(`docid`, `name`, `description`) VALUES (NEW.`rowid`, NEW.`name`, NEW.`description`); END")
        connection.execSQL("CREATE TRIGGER IF NOT EXISTS room_fts_content_sync_trick_fts_AFTER_INSERT AFTER INSERT ON `trick` BEGIN INSERT INTO `trick_fts`(`docid`, `name`, `description`) VALUES (NEW.`rowid`, NEW.`name`, NEW.`description`); END")
        connection.execSQL("CREATE TRIGGER IF NOT EXISTS room_fts_content_sync_test_fts_BEFORE_UPDATE BEFORE UPDATE ON `test` BEGIN DELETE FROM `test_fts` WHERE `docid`=OLD.`rowid`; END")
        connection.execSQL("CREATE TRIGGER IF NOT EXISTS room_fts_content_sync_test_fts_BEFORE_DELETE BEFORE DELETE ON `test` BEGIN DELETE FROM `test_fts` WHERE `docid`=OLD.`rowid`; END")
        connection.execSQL("CREATE TRIGGER IF NOT EXISTS room_fts_content_sync_test_fts_AFTER_UPDATE AFTER UPDATE ON `test` BEGIN INSERT INTO `test_fts`(`docid`, `title`) VALUES (NEW.`rowid`, NEW.`title`); END")
        connection.execSQL("CREATE TRIGGER IF NOT EXISTS room_fts_content_sync_test_fts_AFTER_INSERT AFTER INSERT ON `test` BEGIN INSERT INTO `test_fts`(`docid`, `title`) VALUES (NEW.`rowid`, NEW.`title`); END")
      }

      public override fun onValidateSchema(connection: SQLiteConnection):
          RoomOpenDelegate.ValidationResult {
        val _columnsExam: MutableMap<String, TableInfo.Column> = mutableMapOf()
        _columnsExam.put("id", TableInfo.Column("id", "INTEGER", true, 1, null,
            TableInfo.CREATED_FROM_ENTITY))
        _columnsExam.put("uuid", TableInfo.Column("uuid", "TEXT", true, 0, null,
            TableInfo.CREATED_FROM_ENTITY))
        _columnsExam.put("name", TableInfo.Column("name", "TEXT", true, 0, null,
            TableInfo.CREATED_FROM_ENTITY))
        _columnsExam.put("is_builtin", TableInfo.Column("is_builtin", "INTEGER", true, 0, null,
            TableInfo.CREATED_FROM_ENTITY))
        _columnsExam.put("sort_order", TableInfo.Column("sort_order", "INTEGER", true, 0, null,
            TableInfo.CREATED_FROM_ENTITY))
        _columnsExam.put("created_at", TableInfo.Column("created_at", "INTEGER", true, 0, null,
            TableInfo.CREATED_FROM_ENTITY))
        _columnsExam.put("updated_at", TableInfo.Column("updated_at", "INTEGER", true, 0, null,
            TableInfo.CREATED_FROM_ENTITY))
        val _foreignKeysExam: MutableSet<TableInfo.ForeignKey> = mutableSetOf()
        val _indicesExam: MutableSet<TableInfo.Index> = mutableSetOf()
        _indicesExam.add(TableInfo.Index("index_exam_uuid", true, listOf("uuid"), listOf("ASC")))
        val _infoExam: TableInfo = TableInfo("exam", _columnsExam, _foreignKeysExam, _indicesExam)
        val _existingExam: TableInfo = tableInfoRead(connection, "exam")
        if (!_infoExam.equals(_existingExam)) {
          return RoomOpenDelegate.ValidationResult(false, """
              |exam(app.apti.villa.data.local.entity.ExamEntity).
              | Expected:
              |""".trimMargin() + _infoExam + """
              |
              | Found:
              |""".trimMargin() + _existingExam)
        }
        val _columnsSubject: MutableMap<String, TableInfo.Column> = mutableMapOf()
        _columnsSubject.put("id", TableInfo.Column("id", "INTEGER", true, 1, null,
            TableInfo.CREATED_FROM_ENTITY))
        _columnsSubject.put("name", TableInfo.Column("name", "TEXT", true, 0, null,
            TableInfo.CREATED_FROM_ENTITY))
        _columnsSubject.put("created_at", TableInfo.Column("created_at", "INTEGER", true, 0, null,
            TableInfo.CREATED_FROM_ENTITY))
        _columnsSubject.put("updated_at", TableInfo.Column("updated_at", "INTEGER", true, 0, null,
            TableInfo.CREATED_FROM_ENTITY))
        val _foreignKeysSubject: MutableSet<TableInfo.ForeignKey> = mutableSetOf()
        val _indicesSubject: MutableSet<TableInfo.Index> = mutableSetOf()
        val _infoSubject: TableInfo = TableInfo("subject", _columnsSubject, _foreignKeysSubject,
            _indicesSubject)
        val _existingSubject: TableInfo = tableInfoRead(connection, "subject")
        if (!_infoSubject.equals(_existingSubject)) {
          return RoomOpenDelegate.ValidationResult(false, """
              |subject(app.apti.villa.data.local.entity.SubjectEntity).
              | Expected:
              |""".trimMargin() + _infoSubject + """
              |
              | Found:
              |""".trimMargin() + _existingSubject)
        }
        val _columnsTopic: MutableMap<String, TableInfo.Column> = mutableMapOf()
        _columnsTopic.put("id", TableInfo.Column("id", "INTEGER", true, 1, null,
            TableInfo.CREATED_FROM_ENTITY))
        _columnsTopic.put("subject_id", TableInfo.Column("subject_id", "INTEGER", false, 0, null,
            TableInfo.CREATED_FROM_ENTITY))
        _columnsTopic.put("name", TableInfo.Column("name", "TEXT", true, 0, null,
            TableInfo.CREATED_FROM_ENTITY))
        _columnsTopic.put("created_at", TableInfo.Column("created_at", "INTEGER", true, 0, null,
            TableInfo.CREATED_FROM_ENTITY))
        _columnsTopic.put("updated_at", TableInfo.Column("updated_at", "INTEGER", true, 0, null,
            TableInfo.CREATED_FROM_ENTITY))
        val _foreignKeysTopic: MutableSet<TableInfo.ForeignKey> = mutableSetOf()
        _foreignKeysTopic.add(TableInfo.ForeignKey("subject", "SET NULL", "NO ACTION",
            listOf("subject_id"), listOf("id")))
        val _indicesTopic: MutableSet<TableInfo.Index> = mutableSetOf()
        _indicesTopic.add(TableInfo.Index("index_topic_subject_id_name", true, listOf("subject_id",
            "name"), listOf("ASC", "ASC")))
        _indicesTopic.add(TableInfo.Index("index_topic_subject_id", false, listOf("subject_id"),
            listOf("ASC")))
        val _infoTopic: TableInfo = TableInfo("topic", _columnsTopic, _foreignKeysTopic,
            _indicesTopic)
        val _existingTopic: TableInfo = tableInfoRead(connection, "topic")
        if (!_infoTopic.equals(_existingTopic)) {
          return RoomOpenDelegate.ValidationResult(false, """
              |topic(app.apti.villa.data.local.entity.TopicEntity).
              | Expected:
              |""".trimMargin() + _infoTopic + """
              |
              | Found:
              |""".trimMargin() + _existingTopic)
        }
        val _columnsSubTopic: MutableMap<String, TableInfo.Column> = mutableMapOf()
        _columnsSubTopic.put("id", TableInfo.Column("id", "INTEGER", true, 1, null,
            TableInfo.CREATED_FROM_ENTITY))
        _columnsSubTopic.put("topic_id", TableInfo.Column("topic_id", "INTEGER", true, 0, null,
            TableInfo.CREATED_FROM_ENTITY))
        _columnsSubTopic.put("name", TableInfo.Column("name", "TEXT", true, 0, null,
            TableInfo.CREATED_FROM_ENTITY))
        _columnsSubTopic.put("created_at", TableInfo.Column("created_at", "INTEGER", true, 0, null,
            TableInfo.CREATED_FROM_ENTITY))
        _columnsSubTopic.put("updated_at", TableInfo.Column("updated_at", "INTEGER", true, 0, null,
            TableInfo.CREATED_FROM_ENTITY))
        val _foreignKeysSubTopic: MutableSet<TableInfo.ForeignKey> = mutableSetOf()
        _foreignKeysSubTopic.add(TableInfo.ForeignKey("topic", "CASCADE", "NO ACTION",
            listOf("topic_id"), listOf("id")))
        val _indicesSubTopic: MutableSet<TableInfo.Index> = mutableSetOf()
        _indicesSubTopic.add(TableInfo.Index("index_sub_topic_topic_id_name", true,
            listOf("topic_id", "name"), listOf("ASC", "ASC")))
        _indicesSubTopic.add(TableInfo.Index("index_sub_topic_topic_id", false, listOf("topic_id"),
            listOf("ASC")))
        val _infoSubTopic: TableInfo = TableInfo("sub_topic", _columnsSubTopic,
            _foreignKeysSubTopic, _indicesSubTopic)
        val _existingSubTopic: TableInfo = tableInfoRead(connection, "sub_topic")
        if (!_infoSubTopic.equals(_existingSubTopic)) {
          return RoomOpenDelegate.ValidationResult(false, """
              |sub_topic(app.apti.villa.data.local.entity.SubTopicEntity).
              | Expected:
              |""".trimMargin() + _infoSubTopic + """
              |
              | Found:
              |""".trimMargin() + _existingSubTopic)
        }
        val _columnsTag: MutableMap<String, TableInfo.Column> = mutableMapOf()
        _columnsTag.put("id", TableInfo.Column("id", "INTEGER", true, 1, null,
            TableInfo.CREATED_FROM_ENTITY))
        _columnsTag.put("uuid", TableInfo.Column("uuid", "TEXT", true, 0, null,
            TableInfo.CREATED_FROM_ENTITY))
        _columnsTag.put("name", TableInfo.Column("name", "TEXT", true, 0, null,
            TableInfo.CREATED_FROM_ENTITY))
        _columnsTag.put("color", TableInfo.Column("color", "TEXT", false, 0, null,
            TableInfo.CREATED_FROM_ENTITY))
        _columnsTag.put("is_system", TableInfo.Column("is_system", "INTEGER", true, 0, null,
            TableInfo.CREATED_FROM_ENTITY))
        _columnsTag.put("created_at", TableInfo.Column("created_at", "INTEGER", true, 0, null,
            TableInfo.CREATED_FROM_ENTITY))
        _columnsTag.put("updated_at", TableInfo.Column("updated_at", "INTEGER", true, 0, null,
            TableInfo.CREATED_FROM_ENTITY))
        val _foreignKeysTag: MutableSet<TableInfo.ForeignKey> = mutableSetOf()
        val _indicesTag: MutableSet<TableInfo.Index> = mutableSetOf()
        _indicesTag.add(TableInfo.Index("index_tag_uuid", true, listOf("uuid"), listOf("ASC")))
        val _infoTag: TableInfo = TableInfo("tag", _columnsTag, _foreignKeysTag, _indicesTag)
        val _existingTag: TableInfo = tableInfoRead(connection, "tag")
        if (!_infoTag.equals(_existingTag)) {
          return RoomOpenDelegate.ValidationResult(false, """
              |tag(app.apti.villa.data.local.entity.TagEntity).
              | Expected:
              |""".trimMargin() + _infoTag + """
              |
              | Found:
              |""".trimMargin() + _existingTag)
        }
        val _columnsUserProfile: MutableMap<String, TableInfo.Column> = mutableMapOf()
        _columnsUserProfile.put("id", TableInfo.Column("id", "INTEGER", true, 1, null,
            TableInfo.CREATED_FROM_ENTITY))
        _columnsUserProfile.put("name", TableInfo.Column("name", "TEXT", false, 0, null,
            TableInfo.CREATED_FROM_ENTITY))
        _columnsUserProfile.put("preferred_difficulty", TableInfo.Column("preferred_difficulty",
            "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsUserProfile.put("daily_goal_questions", TableInfo.Column("daily_goal_questions",
            "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsUserProfile.put("daily_goal_minutes", TableInfo.Column("daily_goal_minutes",
            "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsUserProfile.put("prep_start_date", TableInfo.Column("prep_start_date", "TEXT",
            false, 0, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsUserProfile.put("target_exam_date", TableInfo.Column("target_exam_date", "TEXT",
            false, 0, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsUserProfile.put("created_at", TableInfo.Column("created_at", "INTEGER", true, 0,
            null, TableInfo.CREATED_FROM_ENTITY))
        _columnsUserProfile.put("updated_at", TableInfo.Column("updated_at", "INTEGER", true, 0,
            null, TableInfo.CREATED_FROM_ENTITY))
        val _foreignKeysUserProfile: MutableSet<TableInfo.ForeignKey> = mutableSetOf()
        val _indicesUserProfile: MutableSet<TableInfo.Index> = mutableSetOf()
        val _infoUserProfile: TableInfo = TableInfo("user_profile", _columnsUserProfile,
            _foreignKeysUserProfile, _indicesUserProfile)
        val _existingUserProfile: TableInfo = tableInfoRead(connection, "user_profile")
        if (!_infoUserProfile.equals(_existingUserProfile)) {
          return RoomOpenDelegate.ValidationResult(false, """
              |user_profile(app.apti.villa.data.local.entity.UserProfileEntity).
              | Expected:
              |""".trimMargin() + _infoUserProfile + """
              |
              | Found:
              |""".trimMargin() + _existingUserProfile)
        }
        val _columnsSource: MutableMap<String, TableInfo.Column> = mutableMapOf()
        _columnsSource.put("id", TableInfo.Column("id", "INTEGER", true, 1, null,
            TableInfo.CREATED_FROM_ENTITY))
        _columnsSource.put("uuid", TableInfo.Column("uuid", "TEXT", true, 0, null,
            TableInfo.CREATED_FROM_ENTITY))
        _columnsSource.put("title", TableInfo.Column("title", "TEXT", true, 0, null,
            TableInfo.CREATED_FROM_ENTITY))
        _columnsSource.put("type", TableInfo.Column("type", "TEXT", true, 0, null,
            TableInfo.CREATED_FROM_ENTITY))
        _columnsSource.put("url", TableInfo.Column("url", "TEXT", false, 0, null,
            TableInfo.CREATED_FROM_ENTITY))
        _columnsSource.put("channel", TableInfo.Column("channel", "TEXT", false, 0, null,
            TableInfo.CREATED_FROM_ENTITY))
        _columnsSource.put("schema_version", TableInfo.Column("schema_version", "INTEGER", true, 0,
            null, TableInfo.CREATED_FROM_ENTITY))
        _columnsSource.put("content_hash", TableInfo.Column("content_hash", "TEXT", true, 0, null,
            TableInfo.CREATED_FROM_ENTITY))
        _columnsSource.put("imported_at", TableInfo.Column("imported_at", "INTEGER", true, 0, null,
            TableInfo.CREATED_FROM_ENTITY))
        _columnsSource.put("import_report_json", TableInfo.Column("import_report_json", "TEXT",
            false, 0, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsSource.put("created_at", TableInfo.Column("created_at", "INTEGER", true, 0, null,
            TableInfo.CREATED_FROM_ENTITY))
        _columnsSource.put("updated_at", TableInfo.Column("updated_at", "INTEGER", true, 0, null,
            TableInfo.CREATED_FROM_ENTITY))
        val _foreignKeysSource: MutableSet<TableInfo.ForeignKey> = mutableSetOf()
        val _indicesSource: MutableSet<TableInfo.Index> = mutableSetOf()
        _indicesSource.add(TableInfo.Index("index_source_uuid", true, listOf("uuid"),
            listOf("ASC")))
        _indicesSource.add(TableInfo.Index("index_source_content_hash", false,
            listOf("content_hash"), listOf("ASC")))
        val _infoSource: TableInfo = TableInfo("source", _columnsSource, _foreignKeysSource,
            _indicesSource)
        val _existingSource: TableInfo = tableInfoRead(connection, "source")
        if (!_infoSource.equals(_existingSource)) {
          return RoomOpenDelegate.ValidationResult(false, """
              |source(app.apti.villa.data.local.entity.SourceEntity).
              | Expected:
              |""".trimMargin() + _infoSource + """
              |
              | Found:
              |""".trimMargin() + _existingSource)
        }
        val _columnsTest: MutableMap<String, TableInfo.Column> = mutableMapOf()
        _columnsTest.put("id", TableInfo.Column("id", "INTEGER", true, 1, null,
            TableInfo.CREATED_FROM_ENTITY))
        _columnsTest.put("uuid", TableInfo.Column("uuid", "TEXT", true, 0, null,
            TableInfo.CREATED_FROM_ENTITY))
        _columnsTest.put("source_id", TableInfo.Column("source_id", "INTEGER", false, 0, null,
            TableInfo.CREATED_FROM_ENTITY))
        _columnsTest.put("exam_id", TableInfo.Column("exam_id", "INTEGER", false, 0, null,
            TableInfo.CREATED_FROM_ENTITY))
        _columnsTest.put("title", TableInfo.Column("title", "TEXT", true, 0, null,
            TableInfo.CREATED_FROM_ENTITY))
        _columnsTest.put("kind", TableInfo.Column("kind", "TEXT", true, 0, null,
            TableInfo.CREATED_FROM_ENTITY))
        _columnsTest.put("duration_sec", TableInfo.Column("duration_sec", "INTEGER", false, 0, null,
            TableInfo.CREATED_FROM_ENTITY))
        _columnsTest.put("marks_correct", TableInfo.Column("marks_correct", "REAL", true, 0, null,
            TableInfo.CREATED_FROM_ENTITY))
        _columnsTest.put("negative_marks", TableInfo.Column("negative_marks", "REAL", true, 0, null,
            TableInfo.CREATED_FROM_ENTITY))
        _columnsTest.put("archived", TableInfo.Column("archived", "INTEGER", true, 0, null,
            TableInfo.CREATED_FROM_ENTITY))
        _columnsTest.put("created_at", TableInfo.Column("created_at", "INTEGER", true, 0, null,
            TableInfo.CREATED_FROM_ENTITY))
        _columnsTest.put("updated_at", TableInfo.Column("updated_at", "INTEGER", true, 0, null,
            TableInfo.CREATED_FROM_ENTITY))
        val _foreignKeysTest: MutableSet<TableInfo.ForeignKey> = mutableSetOf()
        _foreignKeysTest.add(TableInfo.ForeignKey("source", "SET NULL", "NO ACTION",
            listOf("source_id"), listOf("id")))
        _foreignKeysTest.add(TableInfo.ForeignKey("exam", "SET NULL", "NO ACTION",
            listOf("exam_id"), listOf("id")))
        val _indicesTest: MutableSet<TableInfo.Index> = mutableSetOf()
        _indicesTest.add(TableInfo.Index("index_test_uuid", true, listOf("uuid"), listOf("ASC")))
        _indicesTest.add(TableInfo.Index("index_test_source_id", false, listOf("source_id"),
            listOf("ASC")))
        _indicesTest.add(TableInfo.Index("index_test_exam_id", false, listOf("exam_id"),
            listOf("ASC")))
        val _infoTest: TableInfo = TableInfo("test", _columnsTest, _foreignKeysTest, _indicesTest)
        val _existingTest: TableInfo = tableInfoRead(connection, "test")
        if (!_infoTest.equals(_existingTest)) {
          return RoomOpenDelegate.ValidationResult(false, """
              |test(app.apti.villa.data.local.entity.TestEntity).
              | Expected:
              |""".trimMargin() + _infoTest + """
              |
              | Found:
              |""".trimMargin() + _existingTest)
        }
        val _columnsQuestion: MutableMap<String, TableInfo.Column> = mutableMapOf()
        _columnsQuestion.put("id", TableInfo.Column("id", "INTEGER", true, 1, null,
            TableInfo.CREATED_FROM_ENTITY))
        _columnsQuestion.put("uuid", TableInfo.Column("uuid", "TEXT", true, 0, null,
            TableInfo.CREATED_FROM_ENTITY))
        _columnsQuestion.put("source_id", TableInfo.Column("source_id", "INTEGER", true, 0, null,
            TableInfo.CREATED_FROM_ENTITY))
        _columnsQuestion.put("external_id", TableInfo.Column("external_id", "TEXT", true, 0, null,
            TableInfo.CREATED_FROM_ENTITY))
        _columnsQuestion.put("content_hash", TableInfo.Column("content_hash", "TEXT", true, 0, null,
            TableInfo.CREATED_FROM_ENTITY))
        _columnsQuestion.put("text", TableInfo.Column("text", "TEXT", true, 0, null,
            TableInfo.CREATED_FROM_ENTITY))
        _columnsQuestion.put("format", TableInfo.Column("format", "TEXT", true, 0, null,
            TableInfo.CREATED_FROM_ENTITY))
        _columnsQuestion.put("subject_id", TableInfo.Column("subject_id", "INTEGER", false, 0, null,
            TableInfo.CREATED_FROM_ENTITY))
        _columnsQuestion.put("topic_id", TableInfo.Column("topic_id", "INTEGER", false, 0, null,
            TableInfo.CREATED_FROM_ENTITY))
        _columnsQuestion.put("sub_topic_id", TableInfo.Column("sub_topic_id", "INTEGER", false, 0,
            null, TableInfo.CREATED_FROM_ENTITY))
        _columnsQuestion.put("difficulty", TableInfo.Column("difficulty", "TEXT", false, 0, null,
            TableInfo.CREATED_FROM_ENTITY))
        _columnsQuestion.put("source_timestamp_sec", TableInfo.Column("source_timestamp_sec",
            "INTEGER", false, 0, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsQuestion.put("source_url", TableInfo.Column("source_url", "TEXT", false, 0, null,
            TableInfo.CREATED_FROM_ENTITY))
        _columnsQuestion.put("language", TableInfo.Column("language", "TEXT", false, 0, null,
            TableInfo.CREATED_FROM_ENTITY))
        _columnsQuestion.put("has_solution", TableInfo.Column("has_solution", "INTEGER", true, 0,
            null, TableInfo.CREATED_FROM_ENTITY))
        _columnsQuestion.put("created_at", TableInfo.Column("created_at", "INTEGER", true, 0, null,
            TableInfo.CREATED_FROM_ENTITY))
        _columnsQuestion.put("updated_at", TableInfo.Column("updated_at", "INTEGER", true, 0, null,
            TableInfo.CREATED_FROM_ENTITY))
        val _foreignKeysQuestion: MutableSet<TableInfo.ForeignKey> = mutableSetOf()
        _foreignKeysQuestion.add(TableInfo.ForeignKey("source", "CASCADE", "NO ACTION",
            listOf("source_id"), listOf("id")))
        _foreignKeysQuestion.add(TableInfo.ForeignKey("subject", "SET NULL", "NO ACTION",
            listOf("subject_id"), listOf("id")))
        _foreignKeysQuestion.add(TableInfo.ForeignKey("topic", "SET NULL", "NO ACTION",
            listOf("topic_id"), listOf("id")))
        _foreignKeysQuestion.add(TableInfo.ForeignKey("sub_topic", "SET NULL", "NO ACTION",
            listOf("sub_topic_id"), listOf("id")))
        val _indicesQuestion: MutableSet<TableInfo.Index> = mutableSetOf()
        _indicesQuestion.add(TableInfo.Index("index_question_uuid", true, listOf("uuid"),
            listOf("ASC")))
        _indicesQuestion.add(TableInfo.Index("index_question_source_id_external_id", true,
            listOf("source_id", "external_id"), listOf("ASC", "ASC")))
        _indicesQuestion.add(TableInfo.Index("index_question_content_hash", false,
            listOf("content_hash"), listOf("ASC")))
        _indicesQuestion.add(TableInfo.Index("index_question_subject_id", false,
            listOf("subject_id"), listOf("ASC")))
        _indicesQuestion.add(TableInfo.Index("index_question_topic_id", false, listOf("topic_id"),
            listOf("ASC")))
        _indicesQuestion.add(TableInfo.Index("index_question_sub_topic_id", false,
            listOf("sub_topic_id"), listOf("ASC")))
        val _infoQuestion: TableInfo = TableInfo("question", _columnsQuestion, _foreignKeysQuestion,
            _indicesQuestion)
        val _existingQuestion: TableInfo = tableInfoRead(connection, "question")
        if (!_infoQuestion.equals(_existingQuestion)) {
          return RoomOpenDelegate.ValidationResult(false, """
              |question(app.apti.villa.data.local.entity.QuestionEntity).
              | Expected:
              |""".trimMargin() + _infoQuestion + """
              |
              | Found:
              |""".trimMargin() + _existingQuestion)
        }
        val _columnsQuestionOption: MutableMap<String, TableInfo.Column> = mutableMapOf()
        _columnsQuestionOption.put("id", TableInfo.Column("id", "INTEGER", true, 1, null,
            TableInfo.CREATED_FROM_ENTITY))
        _columnsQuestionOption.put("question_id", TableInfo.Column("question_id", "INTEGER", true,
            0, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsQuestionOption.put("position", TableInfo.Column("position", "INTEGER", true, 0,
            null, TableInfo.CREATED_FROM_ENTITY))
        _columnsQuestionOption.put("text", TableInfo.Column("text", "TEXT", true, 0, null,
            TableInfo.CREATED_FROM_ENTITY))
        _columnsQuestionOption.put("is_correct", TableInfo.Column("is_correct", "INTEGER", true, 0,
            null, TableInfo.CREATED_FROM_ENTITY))
        val _foreignKeysQuestionOption: MutableSet<TableInfo.ForeignKey> = mutableSetOf()
        _foreignKeysQuestionOption.add(TableInfo.ForeignKey("question", "CASCADE", "NO ACTION",
            listOf("question_id"), listOf("id")))
        val _indicesQuestionOption: MutableSet<TableInfo.Index> = mutableSetOf()
        _indicesQuestionOption.add(TableInfo.Index("index_question_option_question_id", false,
            listOf("question_id"), listOf("ASC")))
        val _infoQuestionOption: TableInfo = TableInfo("question_option", _columnsQuestionOption,
            _foreignKeysQuestionOption, _indicesQuestionOption)
        val _existingQuestionOption: TableInfo = tableInfoRead(connection, "question_option")
        if (!_infoQuestionOption.equals(_existingQuestionOption)) {
          return RoomOpenDelegate.ValidationResult(false, """
              |question_option(app.apti.villa.data.local.entity.QuestionOptionEntity).
              | Expected:
              |""".trimMargin() + _infoQuestionOption + """
              |
              | Found:
              |""".trimMargin() + _existingQuestionOption)
        }
        val _columnsTestQuestion: MutableMap<String, TableInfo.Column> = mutableMapOf()
        _columnsTestQuestion.put("test_id", TableInfo.Column("test_id", "INTEGER", true, 1, null,
            TableInfo.CREATED_FROM_ENTITY))
        _columnsTestQuestion.put("question_id", TableInfo.Column("question_id", "INTEGER", true, 2,
            null, TableInfo.CREATED_FROM_ENTITY))
        _columnsTestQuestion.put("position", TableInfo.Column("position", "INTEGER", true, 0, null,
            TableInfo.CREATED_FROM_ENTITY))
        val _foreignKeysTestQuestion: MutableSet<TableInfo.ForeignKey> = mutableSetOf()
        _foreignKeysTestQuestion.add(TableInfo.ForeignKey("test", "CASCADE", "NO ACTION",
            listOf("test_id"), listOf("id")))
        _foreignKeysTestQuestion.add(TableInfo.ForeignKey("question", "CASCADE", "NO ACTION",
            listOf("question_id"), listOf("id")))
        val _indicesTestQuestion: MutableSet<TableInfo.Index> = mutableSetOf()
        _indicesTestQuestion.add(TableInfo.Index("index_test_question_test_id", false,
            listOf("test_id"), listOf("ASC")))
        _indicesTestQuestion.add(TableInfo.Index("index_test_question_question_id", false,
            listOf("question_id"), listOf("ASC")))
        val _infoTestQuestion: TableInfo = TableInfo("test_question", _columnsTestQuestion,
            _foreignKeysTestQuestion, _indicesTestQuestion)
        val _existingTestQuestion: TableInfo = tableInfoRead(connection, "test_question")
        if (!_infoTestQuestion.equals(_existingTestQuestion)) {
          return RoomOpenDelegate.ValidationResult(false, """
              |test_question(app.apti.villa.data.local.entity.TestQuestionEntity).
              | Expected:
              |""".trimMargin() + _infoTestQuestion + """
              |
              | Found:
              |""".trimMargin() + _existingTestQuestion)
        }
        val _columnsHint: MutableMap<String, TableInfo.Column> = mutableMapOf()
        _columnsHint.put("id", TableInfo.Column("id", "INTEGER", true, 1, null,
            TableInfo.CREATED_FROM_ENTITY))
        _columnsHint.put("question_id", TableInfo.Column("question_id", "INTEGER", true, 0, null,
            TableInfo.CREATED_FROM_ENTITY))
        _columnsHint.put("number", TableInfo.Column("number", "INTEGER", true, 0, null,
            TableInfo.CREATED_FROM_ENTITY))
        _columnsHint.put("title", TableInfo.Column("title", "TEXT", true, 0, null,
            TableInfo.CREATED_FROM_ENTITY))
        _columnsHint.put("description", TableInfo.Column("description", "TEXT", true, 0, null,
            TableInfo.CREATED_FROM_ENTITY))
        val _foreignKeysHint: MutableSet<TableInfo.ForeignKey> = mutableSetOf()
        _foreignKeysHint.add(TableInfo.ForeignKey("question", "CASCADE", "NO ACTION",
            listOf("question_id"), listOf("id")))
        val _indicesHint: MutableSet<TableInfo.Index> = mutableSetOf()
        _indicesHint.add(TableInfo.Index("index_hint_question_id", false, listOf("question_id"),
            listOf("ASC")))
        val _infoHint: TableInfo = TableInfo("hint", _columnsHint, _foreignKeysHint, _indicesHint)
        val _existingHint: TableInfo = tableInfoRead(connection, "hint")
        if (!_infoHint.equals(_existingHint)) {
          return RoomOpenDelegate.ValidationResult(false, """
              |hint(app.apti.villa.data.local.entity.HintEntity).
              | Expected:
              |""".trimMargin() + _infoHint + """
              |
              | Found:
              |""".trimMargin() + _existingHint)
        }
        val _columnsTrick: MutableMap<String, TableInfo.Column> = mutableMapOf()
        _columnsTrick.put("id", TableInfo.Column("id", "INTEGER", true, 1, null,
            TableInfo.CREATED_FROM_ENTITY))
        _columnsTrick.put("question_id", TableInfo.Column("question_id", "INTEGER", true, 0, null,
            TableInfo.CREATED_FROM_ENTITY))
        _columnsTrick.put("name", TableInfo.Column("name", "TEXT", true, 0, null,
            TableInfo.CREATED_FROM_ENTITY))
        _columnsTrick.put("description", TableInfo.Column("description", "TEXT", true, 0, null,
            TableInfo.CREATED_FROM_ENTITY))
        val _foreignKeysTrick: MutableSet<TableInfo.ForeignKey> = mutableSetOf()
        _foreignKeysTrick.add(TableInfo.ForeignKey("question", "CASCADE", "NO ACTION",
            listOf("question_id"), listOf("id")))
        val _indicesTrick: MutableSet<TableInfo.Index> = mutableSetOf()
        _indicesTrick.add(TableInfo.Index("index_trick_question_id", false, listOf("question_id"),
            listOf("ASC")))
        val _infoTrick: TableInfo = TableInfo("trick", _columnsTrick, _foreignKeysTrick,
            _indicesTrick)
        val _existingTrick: TableInfo = tableInfoRead(connection, "trick")
        if (!_infoTrick.equals(_existingTrick)) {
          return RoomOpenDelegate.ValidationResult(false, """
              |trick(app.apti.villa.data.local.entity.TrickEntity).
              | Expected:
              |""".trimMargin() + _infoTrick + """
              |
              | Found:
              |""".trimMargin() + _existingTrick)
        }
        val _columnsSolutionStep: MutableMap<String, TableInfo.Column> = mutableMapOf()
        _columnsSolutionStep.put("id", TableInfo.Column("id", "INTEGER", true, 1, null,
            TableInfo.CREATED_FROM_ENTITY))
        _columnsSolutionStep.put("question_id", TableInfo.Column("question_id", "INTEGER", true, 0,
            null, TableInfo.CREATED_FROM_ENTITY))
        _columnsSolutionStep.put("number", TableInfo.Column("number", "INTEGER", true, 0, null,
            TableInfo.CREATED_FROM_ENTITY))
        _columnsSolutionStep.put("action", TableInfo.Column("action", "TEXT", true, 0, null,
            TableInfo.CREATED_FROM_ENTITY))
        val _foreignKeysSolutionStep: MutableSet<TableInfo.ForeignKey> = mutableSetOf()
        _foreignKeysSolutionStep.add(TableInfo.ForeignKey("question", "CASCADE", "NO ACTION",
            listOf("question_id"), listOf("id")))
        val _indicesSolutionStep: MutableSet<TableInfo.Index> = mutableSetOf()
        _indicesSolutionStep.add(TableInfo.Index("index_solution_step_question_id", false,
            listOf("question_id"), listOf("ASC")))
        val _infoSolutionStep: TableInfo = TableInfo("solution_step", _columnsSolutionStep,
            _foreignKeysSolutionStep, _indicesSolutionStep)
        val _existingSolutionStep: TableInfo = tableInfoRead(connection, "solution_step")
        if (!_infoSolutionStep.equals(_existingSolutionStep)) {
          return RoomOpenDelegate.ValidationResult(false, """
              |solution_step(app.apti.villa.data.local.entity.SolutionStepEntity).
              | Expected:
              |""".trimMargin() + _infoSolutionStep + """
              |
              | Found:
              |""".trimMargin() + _existingSolutionStep)
        }
        val _columnsAttempt: MutableMap<String, TableInfo.Column> = mutableMapOf()
        _columnsAttempt.put("id", TableInfo.Column("id", "INTEGER", true, 1, null,
            TableInfo.CREATED_FROM_ENTITY))
        _columnsAttempt.put("uuid", TableInfo.Column("uuid", "TEXT", true, 0, null,
            TableInfo.CREATED_FROM_ENTITY))
        _columnsAttempt.put("test_id", TableInfo.Column("test_id", "INTEGER", false, 0, null,
            TableInfo.CREATED_FROM_ENTITY))
        _columnsAttempt.put("mode", TableInfo.Column("mode", "TEXT", true, 0, null,
            TableInfo.CREATED_FROM_ENTITY))
        _columnsAttempt.put("scope_desc", TableInfo.Column("scope_desc", "TEXT", false, 0, null,
            TableInfo.CREATED_FROM_ENTITY))
        _columnsAttempt.put("status", TableInfo.Column("status", "TEXT", true, 0, null,
            TableInfo.CREATED_FROM_ENTITY))
        _columnsAttempt.put("started_at", TableInfo.Column("started_at", "INTEGER", true, 0, null,
            TableInfo.CREATED_FROM_ENTITY))
        _columnsAttempt.put("submitted_at", TableInfo.Column("submitted_at", "INTEGER", false, 0,
            null, TableInfo.CREATED_FROM_ENTITY))
        _columnsAttempt.put("time_limit_sec", TableInfo.Column("time_limit_sec", "INTEGER", false,
            0, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsAttempt.put("active_elapsed_ms", TableInfo.Column("active_elapsed_ms", "INTEGER",
            true, 0, "0", TableInfo.CREATED_FROM_ENTITY))
        _columnsAttempt.put("score", TableInfo.Column("score", "REAL", false, 0, null,
            TableInfo.CREATED_FROM_ENTITY))
        _columnsAttempt.put("is_retry", TableInfo.Column("is_retry", "INTEGER", true, 0, "0",
            TableInfo.CREATED_FROM_ENTITY))
        _columnsAttempt.put("created_at", TableInfo.Column("created_at", "INTEGER", true, 0, null,
            TableInfo.CREATED_FROM_ENTITY))
        _columnsAttempt.put("updated_at", TableInfo.Column("updated_at", "INTEGER", true, 0, null,
            TableInfo.CREATED_FROM_ENTITY))
        val _foreignKeysAttempt: MutableSet<TableInfo.ForeignKey> = mutableSetOf()
        _foreignKeysAttempt.add(TableInfo.ForeignKey("test", "SET NULL", "NO ACTION",
            listOf("test_id"), listOf("id")))
        val _indicesAttempt: MutableSet<TableInfo.Index> = mutableSetOf()
        _indicesAttempt.add(TableInfo.Index("index_attempt_uuid", true, listOf("uuid"),
            listOf("ASC")))
        _indicesAttempt.add(TableInfo.Index("index_attempt_status", false, listOf("status"),
            listOf("ASC")))
        _indicesAttempt.add(TableInfo.Index("index_attempt_submitted_at", false,
            listOf("submitted_at"), listOf("ASC")))
        _indicesAttempt.add(TableInfo.Index("index_attempt_test_id", false, listOf("test_id"),
            listOf("ASC")))
        _indicesAttempt.add(TableInfo.Index("index_attempt_mode", false, listOf("mode"),
            listOf("ASC")))
        val _infoAttempt: TableInfo = TableInfo("attempt", _columnsAttempt, _foreignKeysAttempt,
            _indicesAttempt)
        val _existingAttempt: TableInfo = tableInfoRead(connection, "attempt")
        if (!_infoAttempt.equals(_existingAttempt)) {
          return RoomOpenDelegate.ValidationResult(false, """
              |attempt(app.apti.villa.data.local.entity.AttemptEntity).
              | Expected:
              |""".trimMargin() + _infoAttempt + """
              |
              | Found:
              |""".trimMargin() + _existingAttempt)
        }
        val _columnsAnswer: MutableMap<String, TableInfo.Column> = mutableMapOf()
        _columnsAnswer.put("id", TableInfo.Column("id", "INTEGER", true, 1, null,
            TableInfo.CREATED_FROM_ENTITY))
        _columnsAnswer.put("attempt_id", TableInfo.Column("attempt_id", "INTEGER", true, 0, null,
            TableInfo.CREATED_FROM_ENTITY))
        _columnsAnswer.put("question_id", TableInfo.Column("question_id", "INTEGER", true, 0, null,
            TableInfo.CREATED_FROM_ENTITY))
        _columnsAnswer.put("position", TableInfo.Column("position", "INTEGER", true, 0, null,
            TableInfo.CREATED_FROM_ENTITY))
        _columnsAnswer.put("status", TableInfo.Column("status", "TEXT", true, 0, "'UNSEEN'",
            TableInfo.CREATED_FROM_ENTITY))
        _columnsAnswer.put("selected_option_id", TableInfo.Column("selected_option_id", "INTEGER",
            false, 0, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsAnswer.put("is_correct", TableInfo.Column("is_correct", "INTEGER", false, 0, null,
            TableInfo.CREATED_FROM_ENTITY))
        _columnsAnswer.put("time_spent_ms", TableInfo.Column("time_spent_ms", "INTEGER", true, 0,
            "0", TableInfo.CREATED_FROM_ENTITY))
        _columnsAnswer.put("first_seen_at", TableInfo.Column("first_seen_at", "INTEGER", false, 0,
            null, TableInfo.CREATED_FROM_ENTITY))
        _columnsAnswer.put("answered_at", TableInfo.Column("answered_at", "INTEGER", false, 0, null,
            TableInfo.CREATED_FROM_ENTITY))
        _columnsAnswer.put("first_submission_correct", TableInfo.Column("first_submission_correct",
            "INTEGER", false, 0, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsAnswer.put("hints_opened", TableInfo.Column("hints_opened", "INTEGER", true, 0, "0",
            TableInfo.CREATED_FROM_ENTITY))
        _columnsAnswer.put("solved_at_hint_level", TableInfo.Column("solved_at_hint_level",
            "INTEGER", false, 0, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsAnswer.put("revealed", TableInfo.Column("revealed", "INTEGER", true, 0, "0",
            TableInfo.CREATED_FROM_ENTITY))
        val _foreignKeysAnswer: MutableSet<TableInfo.ForeignKey> = mutableSetOf()
        _foreignKeysAnswer.add(TableInfo.ForeignKey("attempt", "CASCADE", "NO ACTION",
            listOf("attempt_id"), listOf("id")))
        _foreignKeysAnswer.add(TableInfo.ForeignKey("question", "CASCADE", "NO ACTION",
            listOf("question_id"), listOf("id")))
        _foreignKeysAnswer.add(TableInfo.ForeignKey("question_option", "SET NULL", "NO ACTION",
            listOf("selected_option_id"), listOf("id")))
        val _indicesAnswer: MutableSet<TableInfo.Index> = mutableSetOf()
        _indicesAnswer.add(TableInfo.Index("index_answer_attempt_id_question_id", true,
            listOf("attempt_id", "question_id"), listOf("ASC", "ASC")))
        _indicesAnswer.add(TableInfo.Index("index_answer_question_id", false, listOf("question_id"),
            listOf("ASC")))
        _indicesAnswer.add(TableInfo.Index("index_answer_is_correct", false, listOf("is_correct"),
            listOf("ASC")))
        _indicesAnswer.add(TableInfo.Index("index_answer_attempt_id", false, listOf("attempt_id"),
            listOf("ASC")))
        _indicesAnswer.add(TableInfo.Index("index_answer_selected_option_id", false,
            listOf("selected_option_id"), listOf("ASC")))
        val _infoAnswer: TableInfo = TableInfo("answer", _columnsAnswer, _foreignKeysAnswer,
            _indicesAnswer)
        val _existingAnswer: TableInfo = tableInfoRead(connection, "answer")
        if (!_infoAnswer.equals(_existingAnswer)) {
          return RoomOpenDelegate.ValidationResult(false, """
              |answer(app.apti.villa.data.local.entity.AnswerEntity).
              | Expected:
              |""".trimMargin() + _infoAnswer + """
              |
              | Found:
              |""".trimMargin() + _existingAnswer)
        }
        val _columnsAnswerSubmission: MutableMap<String, TableInfo.Column> = mutableMapOf()
        _columnsAnswerSubmission.put("id", TableInfo.Column("id", "INTEGER", true, 1, null,
            TableInfo.CREATED_FROM_ENTITY))
        _columnsAnswerSubmission.put("answer_id", TableInfo.Column("answer_id", "INTEGER", true, 0,
            null, TableInfo.CREATED_FROM_ENTITY))
        _columnsAnswerSubmission.put("option_id", TableInfo.Column("option_id", "INTEGER", true, 0,
            null, TableInfo.CREATED_FROM_ENTITY))
        _columnsAnswerSubmission.put("submitted_at", TableInfo.Column("submitted_at", "INTEGER",
            true, 0, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsAnswerSubmission.put("hints_open_at_submit",
            TableInfo.Column("hints_open_at_submit", "INTEGER", true, 0, null,
            TableInfo.CREATED_FROM_ENTITY))
        _columnsAnswerSubmission.put("ms_since_first_seen", TableInfo.Column("ms_since_first_seen",
            "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsAnswerSubmission.put("is_correct", TableInfo.Column("is_correct", "INTEGER", true,
            0, null, TableInfo.CREATED_FROM_ENTITY))
        val _foreignKeysAnswerSubmission: MutableSet<TableInfo.ForeignKey> = mutableSetOf()
        _foreignKeysAnswerSubmission.add(TableInfo.ForeignKey("answer", "CASCADE", "NO ACTION",
            listOf("answer_id"), listOf("id")))
        _foreignKeysAnswerSubmission.add(TableInfo.ForeignKey("question_option", "CASCADE",
            "NO ACTION", listOf("option_id"), listOf("id")))
        val _indicesAnswerSubmission: MutableSet<TableInfo.Index> = mutableSetOf()
        _indicesAnswerSubmission.add(TableInfo.Index("index_answer_submission_answer_id", false,
            listOf("answer_id"), listOf("ASC")))
        _indicesAnswerSubmission.add(TableInfo.Index("index_answer_submission_option_id", false,
            listOf("option_id"), listOf("ASC")))
        val _infoAnswerSubmission: TableInfo = TableInfo("answer_submission",
            _columnsAnswerSubmission, _foreignKeysAnswerSubmission, _indicesAnswerSubmission)
        val _existingAnswerSubmission: TableInfo = tableInfoRead(connection, "answer_submission")
        if (!_infoAnswerSubmission.equals(_existingAnswerSubmission)) {
          return RoomOpenDelegate.ValidationResult(false, """
              |answer_submission(app.apti.villa.data.local.entity.AnswerSubmissionEntity).
              | Expected:
              |""".trimMargin() + _infoAnswerSubmission + """
              |
              | Found:
              |""".trimMargin() + _existingAnswerSubmission)
        }
        val _columnsHintUsage: MutableMap<String, TableInfo.Column> = mutableMapOf()
        _columnsHintUsage.put("id", TableInfo.Column("id", "INTEGER", true, 1, null,
            TableInfo.CREATED_FROM_ENTITY))
        _columnsHintUsage.put("answer_id", TableInfo.Column("answer_id", "INTEGER", true, 0, null,
            TableInfo.CREATED_FROM_ENTITY))
        _columnsHintUsage.put("hint_id", TableInfo.Column("hint_id", "INTEGER", true, 0, null,
            TableInfo.CREATED_FROM_ENTITY))
        _columnsHintUsage.put("opened_at", TableInfo.Column("opened_at", "INTEGER", true, 0, null,
            TableInfo.CREATED_FROM_ENTITY))
        _columnsHintUsage.put("ms_since_first_seen", TableInfo.Column("ms_since_first_seen",
            "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY))
        val _foreignKeysHintUsage: MutableSet<TableInfo.ForeignKey> = mutableSetOf()
        _foreignKeysHintUsage.add(TableInfo.ForeignKey("answer", "CASCADE", "NO ACTION",
            listOf("answer_id"), listOf("id")))
        _foreignKeysHintUsage.add(TableInfo.ForeignKey("hint", "CASCADE", "NO ACTION",
            listOf("hint_id"), listOf("id")))
        val _indicesHintUsage: MutableSet<TableInfo.Index> = mutableSetOf()
        _indicesHintUsage.add(TableInfo.Index("index_hint_usage_answer_id_hint_id", true,
            listOf("answer_id", "hint_id"), listOf("ASC", "ASC")))
        _indicesHintUsage.add(TableInfo.Index("index_hint_usage_hint_id", false, listOf("hint_id"),
            listOf("ASC")))
        val _infoHintUsage: TableInfo = TableInfo("hint_usage", _columnsHintUsage,
            _foreignKeysHintUsage, _indicesHintUsage)
        val _existingHintUsage: TableInfo = tableInfoRead(connection, "hint_usage")
        if (!_infoHintUsage.equals(_existingHintUsage)) {
          return RoomOpenDelegate.ValidationResult(false, """
              |hint_usage(app.apti.villa.data.local.entity.HintUsageEntity).
              | Expected:
              |""".trimMargin() + _infoHintUsage + """
              |
              | Found:
              |""".trimMargin() + _existingHintUsage)
        }
        val _columnsQuestionTag: MutableMap<String, TableInfo.Column> = mutableMapOf()
        _columnsQuestionTag.put("question_id", TableInfo.Column("question_id", "INTEGER", true, 1,
            null, TableInfo.CREATED_FROM_ENTITY))
        _columnsQuestionTag.put("tag_id", TableInfo.Column("tag_id", "INTEGER", true, 2, null,
            TableInfo.CREATED_FROM_ENTITY))
        _columnsQuestionTag.put("created_at", TableInfo.Column("created_at", "INTEGER", true, 0,
            null, TableInfo.CREATED_FROM_ENTITY))
        val _foreignKeysQuestionTag: MutableSet<TableInfo.ForeignKey> = mutableSetOf()
        _foreignKeysQuestionTag.add(TableInfo.ForeignKey("question", "CASCADE", "NO ACTION",
            listOf("question_id"), listOf("id")))
        _foreignKeysQuestionTag.add(TableInfo.ForeignKey("tag", "CASCADE", "NO ACTION",
            listOf("tag_id"), listOf("id")))
        val _indicesQuestionTag: MutableSet<TableInfo.Index> = mutableSetOf()
        _indicesQuestionTag.add(TableInfo.Index("index_question_tag_question_id", false,
            listOf("question_id"), listOf("ASC")))
        _indicesQuestionTag.add(TableInfo.Index("index_question_tag_tag_id", false,
            listOf("tag_id"), listOf("ASC")))
        val _infoQuestionTag: TableInfo = TableInfo("question_tag", _columnsQuestionTag,
            _foreignKeysQuestionTag, _indicesQuestionTag)
        val _existingQuestionTag: TableInfo = tableInfoRead(connection, "question_tag")
        if (!_infoQuestionTag.equals(_existingQuestionTag)) {
          return RoomOpenDelegate.ValidationResult(false, """
              |question_tag(app.apti.villa.data.local.entity.QuestionTagEntity).
              | Expected:
              |""".trimMargin() + _infoQuestionTag + """
              |
              | Found:
              |""".trimMargin() + _existingQuestionTag)
        }
        val _columnsSavedTrick: MutableMap<String, TableInfo.Column> = mutableMapOf()
        _columnsSavedTrick.put("trick_id", TableInfo.Column("trick_id", "INTEGER", true, 1, null,
            TableInfo.CREATED_FROM_ENTITY))
        _columnsSavedTrick.put("created_at", TableInfo.Column("created_at", "INTEGER", true, 0,
            null, TableInfo.CREATED_FROM_ENTITY))
        val _foreignKeysSavedTrick: MutableSet<TableInfo.ForeignKey> = mutableSetOf()
        _foreignKeysSavedTrick.add(TableInfo.ForeignKey("trick", "CASCADE", "NO ACTION",
            listOf("trick_id"), listOf("id")))
        val _indicesSavedTrick: MutableSet<TableInfo.Index> = mutableSetOf()
        val _infoSavedTrick: TableInfo = TableInfo("saved_trick", _columnsSavedTrick,
            _foreignKeysSavedTrick, _indicesSavedTrick)
        val _existingSavedTrick: TableInfo = tableInfoRead(connection, "saved_trick")
        if (!_infoSavedTrick.equals(_existingSavedTrick)) {
          return RoomOpenDelegate.ValidationResult(false, """
              |saved_trick(app.apti.villa.data.local.entity.SavedTrickEntity).
              | Expected:
              |""".trimMargin() + _infoSavedTrick + """
              |
              | Found:
              |""".trimMargin() + _existingSavedTrick)
        }
        val _columnsMistake: MutableMap<String, TableInfo.Column> = mutableMapOf()
        _columnsMistake.put("question_id", TableInfo.Column("question_id", "INTEGER", true, 1, null,
            TableInfo.CREATED_FROM_ENTITY))
        _columnsMistake.put("first_wrong_at", TableInfo.Column("first_wrong_at", "INTEGER", true, 0,
            null, TableInfo.CREATED_FROM_ENTITY))
        _columnsMistake.put("last_wrong_at", TableInfo.Column("last_wrong_at", "INTEGER", true, 0,
            null, TableInfo.CREATED_FROM_ENTITY))
        _columnsMistake.put("wrong_count", TableInfo.Column("wrong_count", "INTEGER", true, 0, "1",
            TableInfo.CREATED_FROM_ENTITY))
        _columnsMistake.put("consecutive_correct", TableInfo.Column("consecutive_correct",
            "INTEGER", true, 0, "0", TableInfo.CREATED_FROM_ENTITY))
        _columnsMistake.put("status", TableInfo.Column("status", "TEXT", true, 0, "'ACTIVE'",
            TableInfo.CREATED_FROM_ENTITY))
        val _foreignKeysMistake: MutableSet<TableInfo.ForeignKey> = mutableSetOf()
        _foreignKeysMistake.add(TableInfo.ForeignKey("question", "CASCADE", "NO ACTION",
            listOf("question_id"), listOf("id")))
        val _indicesMistake: MutableSet<TableInfo.Index> = mutableSetOf()
        val _infoMistake: TableInfo = TableInfo("mistake", _columnsMistake, _foreignKeysMistake,
            _indicesMistake)
        val _existingMistake: TableInfo = tableInfoRead(connection, "mistake")
        if (!_infoMistake.equals(_existingMistake)) {
          return RoomOpenDelegate.ValidationResult(false, """
              |mistake(app.apti.villa.data.local.entity.MistakeEntity).
              | Expected:
              |""".trimMargin() + _infoMistake + """
              |
              | Found:
              |""".trimMargin() + _existingMistake)
        }
        val _columnsRevisionItem: MutableMap<String, TableInfo.Column> = mutableMapOf()
        _columnsRevisionItem.put("question_id", TableInfo.Column("question_id", "INTEGER", true, 1,
            null, TableInfo.CREATED_FROM_ENTITY))
        _columnsRevisionItem.put("box", TableInfo.Column("box", "INTEGER", true, 0, "0",
            TableInfo.CREATED_FROM_ENTITY))
        _columnsRevisionItem.put("due_at", TableInfo.Column("due_at", "INTEGER", true, 0, null,
            TableInfo.CREATED_FROM_ENTITY))
        _columnsRevisionItem.put("last_reviewed_at", TableInfo.Column("last_reviewed_at", "INTEGER",
            false, 0, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsRevisionItem.put("review_count", TableInfo.Column("review_count", "INTEGER", true,
            0, "0", TableInfo.CREATED_FROM_ENTITY))
        _columnsRevisionItem.put("suspended", TableInfo.Column("suspended", "INTEGER", true, 0, "0",
            TableInfo.CREATED_FROM_ENTITY))
        val _foreignKeysRevisionItem: MutableSet<TableInfo.ForeignKey> = mutableSetOf()
        _foreignKeysRevisionItem.add(TableInfo.ForeignKey("question", "CASCADE", "NO ACTION",
            listOf("question_id"), listOf("id")))
        val _indicesRevisionItem: MutableSet<TableInfo.Index> = mutableSetOf()
        _indicesRevisionItem.add(TableInfo.Index("index_revision_item_due_at", false,
            listOf("due_at"), listOf("ASC")))
        _indicesRevisionItem.add(TableInfo.Index("index_revision_item_box", false, listOf("box"),
            listOf("ASC")))
        val _infoRevisionItem: TableInfo = TableInfo("revision_item", _columnsRevisionItem,
            _foreignKeysRevisionItem, _indicesRevisionItem)
        val _existingRevisionItem: TableInfo = tableInfoRead(connection, "revision_item")
        if (!_infoRevisionItem.equals(_existingRevisionItem)) {
          return RoomOpenDelegate.ValidationResult(false, """
              |revision_item(app.apti.villa.data.local.entity.RevisionItemEntity).
              | Expected:
              |""".trimMargin() + _infoRevisionItem + """
              |
              | Found:
              |""".trimMargin() + _existingRevisionItem)
        }
        val _columnsRevisionReason: MutableMap<String, TableInfo.Column> = mutableMapOf()
        _columnsRevisionReason.put("question_id", TableInfo.Column("question_id", "INTEGER", true,
            1, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsRevisionReason.put("reason", TableInfo.Column("reason", "TEXT", true, 2, null,
            TableInfo.CREATED_FROM_ENTITY))
        _columnsRevisionReason.put("created_at", TableInfo.Column("created_at", "INTEGER", true, 0,
            null, TableInfo.CREATED_FROM_ENTITY))
        val _foreignKeysRevisionReason: MutableSet<TableInfo.ForeignKey> = mutableSetOf()
        _foreignKeysRevisionReason.add(TableInfo.ForeignKey("question", "CASCADE", "NO ACTION",
            listOf("question_id"), listOf("id")))
        val _indicesRevisionReason: MutableSet<TableInfo.Index> = mutableSetOf()
        val _infoRevisionReason: TableInfo = TableInfo("revision_reason", _columnsRevisionReason,
            _foreignKeysRevisionReason, _indicesRevisionReason)
        val _existingRevisionReason: TableInfo = tableInfoRead(connection, "revision_reason")
        if (!_infoRevisionReason.equals(_existingRevisionReason)) {
          return RoomOpenDelegate.ValidationResult(false, """
              |revision_reason(app.apti.villa.data.local.entity.RevisionReasonEntity).
              | Expected:
              |""".trimMargin() + _infoRevisionReason + """
              |
              | Found:
              |""".trimMargin() + _existingRevisionReason)
        }
        val _columnsStudyDay: MutableMap<String, TableInfo.Column> = mutableMapOf()
        _columnsStudyDay.put("date", TableInfo.Column("date", "TEXT", true, 1, null,
            TableInfo.CREATED_FROM_ENTITY))
        _columnsStudyDay.put("questions_attempted", TableInfo.Column("questions_attempted",
            "INTEGER", true, 0, "0", TableInfo.CREATED_FROM_ENTITY))
        _columnsStudyDay.put("correct", TableInfo.Column("correct", "INTEGER", true, 0, "0",
            TableInfo.CREATED_FROM_ENTITY))
        _columnsStudyDay.put("active_ms", TableInfo.Column("active_ms", "INTEGER", true, 0, "0",
            TableInfo.CREATED_FROM_ENTITY))
        val _foreignKeysStudyDay: MutableSet<TableInfo.ForeignKey> = mutableSetOf()
        val _indicesStudyDay: MutableSet<TableInfo.Index> = mutableSetOf()
        val _infoStudyDay: TableInfo = TableInfo("study_day", _columnsStudyDay,
            _foreignKeysStudyDay, _indicesStudyDay)
        val _existingStudyDay: TableInfo = tableInfoRead(connection, "study_day")
        if (!_infoStudyDay.equals(_existingStudyDay)) {
          return RoomOpenDelegate.ValidationResult(false, """
              |study_day(app.apti.villa.data.local.entity.StudyDayEntity).
              | Expected:
              |""".trimMargin() + _infoStudyDay + """
              |
              | Found:
              |""".trimMargin() + _existingStudyDay)
        }
        val _columnsQuestionFts: MutableSet<String> = mutableSetOf()
        _columnsQuestionFts.add("text")
        val _infoQuestionFts: FtsTableInfo = FtsTableInfo("question_fts", _columnsQuestionFts,
            "CREATE VIRTUAL TABLE IF NOT EXISTS `question_fts` USING FTS4(`text` TEXT NOT NULL, tokenize=unicode61, content=`question`)")
        val _existingQuestionFts: FtsTableInfo = ftsTableInfoRead(connection, "question_fts")
        if (!_infoQuestionFts.equals(_existingQuestionFts)) {
          return RoomOpenDelegate.ValidationResult(false, """
              |question_fts(app.apti.villa.data.local.entity.fts.QuestionFtsEntity).
              | Expected:
              |""".trimMargin() + _infoQuestionFts + """
              |
              | Found:
              |""".trimMargin() + _existingQuestionFts)
        }
        val _columnsQuestionOptionFts: MutableSet<String> = mutableSetOf()
        _columnsQuestionOptionFts.add("text")
        val _infoQuestionOptionFts: FtsTableInfo = FtsTableInfo("question_option_fts",
            _columnsQuestionOptionFts,
            "CREATE VIRTUAL TABLE IF NOT EXISTS `question_option_fts` USING FTS4(`text` TEXT NOT NULL, tokenize=unicode61, content=`question_option`)")
        val _existingQuestionOptionFts: FtsTableInfo = ftsTableInfoRead(connection,
            "question_option_fts")
        if (!_infoQuestionOptionFts.equals(_existingQuestionOptionFts)) {
          return RoomOpenDelegate.ValidationResult(false, """
              |question_option_fts(app.apti.villa.data.local.entity.fts.QuestionOptionFtsEntity).
              | Expected:
              |""".trimMargin() + _infoQuestionOptionFts + """
              |
              | Found:
              |""".trimMargin() + _existingQuestionOptionFts)
        }
        val _columnsTrickFts: MutableSet<String> = mutableSetOf()
        _columnsTrickFts.add("name")
        _columnsTrickFts.add("description")
        val _infoTrickFts: FtsTableInfo = FtsTableInfo("trick_fts", _columnsTrickFts,
            "CREATE VIRTUAL TABLE IF NOT EXISTS `trick_fts` USING FTS4(`name` TEXT NOT NULL, `description` TEXT NOT NULL, tokenize=unicode61, content=`trick`)")
        val _existingTrickFts: FtsTableInfo = ftsTableInfoRead(connection, "trick_fts")
        if (!_infoTrickFts.equals(_existingTrickFts)) {
          return RoomOpenDelegate.ValidationResult(false, """
              |trick_fts(app.apti.villa.data.local.entity.fts.TrickFtsEntity).
              | Expected:
              |""".trimMargin() + _infoTrickFts + """
              |
              | Found:
              |""".trimMargin() + _existingTrickFts)
        }
        val _columnsTestFts: MutableSet<String> = mutableSetOf()
        _columnsTestFts.add("title")
        val _infoTestFts: FtsTableInfo = FtsTableInfo("test_fts", _columnsTestFts,
            "CREATE VIRTUAL TABLE IF NOT EXISTS `test_fts` USING FTS4(`title` TEXT NOT NULL, tokenize=unicode61, content=`test`)")
        val _existingTestFts: FtsTableInfo = ftsTableInfoRead(connection, "test_fts")
        if (!_infoTestFts.equals(_existingTestFts)) {
          return RoomOpenDelegate.ValidationResult(false, """
              |test_fts(app.apti.villa.data.local.entity.fts.TestFtsEntity).
              | Expected:
              |""".trimMargin() + _infoTestFts + """
              |
              | Found:
              |""".trimMargin() + _existingTestFts)
        }
        val _columnsAiConversations: MutableMap<String, TableInfo.Column> = mutableMapOf()
        _columnsAiConversations.put("id", TableInfo.Column("id", "INTEGER", true, 1, null,
            TableInfo.CREATED_FROM_ENTITY))
        _columnsAiConversations.put("uuid", TableInfo.Column("uuid", "TEXT", true, 0, null,
            TableInfo.CREATED_FROM_ENTITY))
        _columnsAiConversations.put("question_id", TableInfo.Column("question_id", "INTEGER", true,
            0, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsAiConversations.put("provider_id", TableInfo.Column("provider_id", "TEXT", true, 0,
            null, TableInfo.CREATED_FROM_ENTITY))
        _columnsAiConversations.put("model", TableInfo.Column("model", "TEXT", true, 0, null,
            TableInfo.CREATED_FROM_ENTITY))
        _columnsAiConversations.put("title", TableInfo.Column("title", "TEXT", true, 0, null,
            TableInfo.CREATED_FROM_ENTITY))
        _columnsAiConversations.put("created_at", TableInfo.Column("created_at", "INTEGER", true, 0,
            null, TableInfo.CREATED_FROM_ENTITY))
        _columnsAiConversations.put("updated_at", TableInfo.Column("updated_at", "INTEGER", true, 0,
            null, TableInfo.CREATED_FROM_ENTITY))
        val _foreignKeysAiConversations: MutableSet<TableInfo.ForeignKey> = mutableSetOf()
        _foreignKeysAiConversations.add(TableInfo.ForeignKey("question", "CASCADE", "NO ACTION",
            listOf("question_id"), listOf("id")))
        val _indicesAiConversations: MutableSet<TableInfo.Index> = mutableSetOf()
        _indicesAiConversations.add(TableInfo.Index("index_ai_conversations_question_id", false,
            listOf("question_id"), listOf("ASC")))
        _indicesAiConversations.add(TableInfo.Index("index_ai_conversations_created_at", false,
            listOf("created_at"), listOf("ASC")))
        val _infoAiConversations: TableInfo = TableInfo("ai_conversations", _columnsAiConversations,
            _foreignKeysAiConversations, _indicesAiConversations)
        val _existingAiConversations: TableInfo = tableInfoRead(connection, "ai_conversations")
        if (!_infoAiConversations.equals(_existingAiConversations)) {
          return RoomOpenDelegate.ValidationResult(false, """
              |ai_conversations(app.apti.villa.data.local.entity.AiConversationEntity).
              | Expected:
              |""".trimMargin() + _infoAiConversations + """
              |
              | Found:
              |""".trimMargin() + _existingAiConversations)
        }
        val _columnsAiMessages: MutableMap<String, TableInfo.Column> = mutableMapOf()
        _columnsAiMessages.put("id", TableInfo.Column("id", "INTEGER", true, 1, null,
            TableInfo.CREATED_FROM_ENTITY))
        _columnsAiMessages.put("conversation_id", TableInfo.Column("conversation_id", "INTEGER",
            true, 0, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsAiMessages.put("role", TableInfo.Column("role", "TEXT", true, 0, null,
            TableInfo.CREATED_FROM_ENTITY))
        _columnsAiMessages.put("content", TableInfo.Column("content", "TEXT", true, 0, null,
            TableInfo.CREATED_FROM_ENTITY))
        _columnsAiMessages.put("created_at", TableInfo.Column("created_at", "INTEGER", true, 0,
            null, TableInfo.CREATED_FROM_ENTITY))
        val _foreignKeysAiMessages: MutableSet<TableInfo.ForeignKey> = mutableSetOf()
        _foreignKeysAiMessages.add(TableInfo.ForeignKey("ai_conversations", "CASCADE", "NO ACTION",
            listOf("conversation_id"), listOf("id")))
        val _indicesAiMessages: MutableSet<TableInfo.Index> = mutableSetOf()
        _indicesAiMessages.add(TableInfo.Index("index_ai_messages_conversation_id", false,
            listOf("conversation_id"), listOf("ASC")))
        _indicesAiMessages.add(TableInfo.Index("index_ai_messages_created_at", false,
            listOf("created_at"), listOf("ASC")))
        val _infoAiMessages: TableInfo = TableInfo("ai_messages", _columnsAiMessages,
            _foreignKeysAiMessages, _indicesAiMessages)
        val _existingAiMessages: TableInfo = tableInfoRead(connection, "ai_messages")
        if (!_infoAiMessages.equals(_existingAiMessages)) {
          return RoomOpenDelegate.ValidationResult(false, """
              |ai_messages(app.apti.villa.data.local.entity.AiMessageEntity).
              | Expected:
              |""".trimMargin() + _infoAiMessages + """
              |
              | Found:
              |""".trimMargin() + _existingAiMessages)
        }
        return RoomOpenDelegate.ValidationResult(true, null)
      }
    }
    return _openDelegate
  }

  protected override fun createInvalidationTracker(): InvalidationTracker {
    val _shadowTablesMap: MutableMap<String, String> = mutableMapOf()
    _shadowTablesMap.put("question_fts", "question")
    _shadowTablesMap.put("question_option_fts", "question_option")
    _shadowTablesMap.put("trick_fts", "trick")
    _shadowTablesMap.put("test_fts", "test")
    val _viewTables: MutableMap<String, Set<String>> = mutableMapOf()
    return InvalidationTracker(this, _shadowTablesMap, _viewTables, "exam", "subject", "topic",
        "sub_topic", "tag", "user_profile", "source", "test", "question", "question_option",
        "test_question", "hint", "trick", "solution_step", "attempt", "answer", "answer_submission",
        "hint_usage", "question_tag", "saved_trick", "mistake", "revision_item", "revision_reason",
        "study_day", "question_fts", "question_option_fts", "trick_fts", "test_fts",
        "ai_conversations", "ai_messages")
  }

  public override fun clearAllTables() {
    super.performClear(true, "exam", "subject", "topic", "sub_topic", "tag", "user_profile",
        "source", "test", "question", "question_option", "test_question", "hint", "trick",
        "solution_step", "attempt", "answer", "answer_submission", "hint_usage", "question_tag",
        "saved_trick", "mistake", "revision_item", "revision_reason", "study_day", "question_fts",
        "question_option_fts", "trick_fts", "test_fts", "ai_conversations", "ai_messages")
  }

  protected override fun getRequiredTypeConverterClasses(): Map<KClass<*>, List<KClass<*>>> {
    val _typeConvertersMap: MutableMap<KClass<*>, List<KClass<*>>> = mutableMapOf()
    _typeConvertersMap.put(ExamDao::class, ExamDao_Impl.getRequiredConverters())
    _typeConvertersMap.put(SubjectDao::class, SubjectDao_Impl.getRequiredConverters())
    _typeConvertersMap.put(TopicDao::class, TopicDao_Impl.getRequiredConverters())
    _typeConvertersMap.put(SubTopicDao::class, SubTopicDao_Impl.getRequiredConverters())
    _typeConvertersMap.put(TagDao::class, TagDao_Impl.getRequiredConverters())
    _typeConvertersMap.put(UserProfileDao::class, UserProfileDao_Impl.getRequiredConverters())
    _typeConvertersMap.put(SourceDao::class, SourceDao_Impl.getRequiredConverters())
    _typeConvertersMap.put(TestDao::class, TestDao_Impl.getRequiredConverters())
    _typeConvertersMap.put(QuestionDao::class, QuestionDao_Impl.getRequiredConverters())
    _typeConvertersMap.put(QuestionOptionDao::class, QuestionOptionDao_Impl.getRequiredConverters())
    _typeConvertersMap.put(TestQuestionDao::class, TestQuestionDao_Impl.getRequiredConverters())
    _typeConvertersMap.put(HintDao::class, HintDao_Impl.getRequiredConverters())
    _typeConvertersMap.put(TrickDao::class, TrickDao_Impl.getRequiredConverters())
    _typeConvertersMap.put(SolutionStepDao::class, SolutionStepDao_Impl.getRequiredConverters())
    _typeConvertersMap.put(AttemptDao::class, AttemptDao_Impl.getRequiredConverters())
    _typeConvertersMap.put(AnswerDao::class, AnswerDao_Impl.getRequiredConverters())
    _typeConvertersMap.put(AnswerSubmissionDao::class,
        AnswerSubmissionDao_Impl.getRequiredConverters())
    _typeConvertersMap.put(HintUsageDao::class, HintUsageDao_Impl.getRequiredConverters())
    _typeConvertersMap.put(QuestionTagDao::class, QuestionTagDao_Impl.getRequiredConverters())
    _typeConvertersMap.put(SavedTrickDao::class, SavedTrickDao_Impl.getRequiredConverters())
    _typeConvertersMap.put(MistakeDao::class, MistakeDao_Impl.getRequiredConverters())
    _typeConvertersMap.put(RevisionDao::class, RevisionDao_Impl.getRequiredConverters())
    _typeConvertersMap.put(StudyDayDao::class, StudyDayDao_Impl.getRequiredConverters())
    _typeConvertersMap.put(AnalyticsDao::class, AnalyticsDao_Impl.getRequiredConverters())
    _typeConvertersMap.put(InventoryDao::class, InventoryDao_Impl.getRequiredConverters())
    _typeConvertersMap.put(SearchDao::class, SearchDao_Impl.getRequiredConverters())
    _typeConvertersMap.put(AiConversationDao::class, AiConversationDao_Impl.getRequiredConverters())
    return _typeConvertersMap
  }

  public override fun getRequiredAutoMigrationSpecClasses(): Set<KClass<out AutoMigrationSpec>> {
    val _autoMigrationSpecsSet: MutableSet<KClass<out AutoMigrationSpec>> = mutableSetOf()
    return _autoMigrationSpecsSet
  }

  public override
      fun createAutoMigrations(autoMigrationSpecs: Map<KClass<out AutoMigrationSpec>, AutoMigrationSpec>):
      List<Migration> {
    val _autoMigrations: MutableList<Migration> = mutableListOf()
    return _autoMigrations
  }

  public override fun examDao(): ExamDao = _examDao.value

  public override fun subjectDao(): SubjectDao = _subjectDao.value

  public override fun topicDao(): TopicDao = _topicDao.value

  public override fun subTopicDao(): SubTopicDao = _subTopicDao.value

  public override fun tagDao(): TagDao = _tagDao.value

  public override fun userProfileDao(): UserProfileDao = _userProfileDao.value

  public override fun sourceDao(): SourceDao = _sourceDao.value

  public override fun testDao(): TestDao = _testDao.value

  public override fun questionDao(): QuestionDao = _questionDao.value

  public override fun questionOptionDao(): QuestionOptionDao = _questionOptionDao.value

  public override fun testQuestionDao(): TestQuestionDao = _testQuestionDao.value

  public override fun hintDao(): HintDao = _hintDao.value

  public override fun trickDao(): TrickDao = _trickDao.value

  public override fun solutionStepDao(): SolutionStepDao = _solutionStepDao.value

  public override fun attemptDao(): AttemptDao = _attemptDao.value

  public override fun answerDao(): AnswerDao = _answerDao.value

  public override fun answerSubmissionDao(): AnswerSubmissionDao = _answerSubmissionDao.value

  public override fun hintUsageDao(): HintUsageDao = _hintUsageDao.value

  public override fun questionTagDao(): QuestionTagDao = _questionTagDao.value

  public override fun savedTrickDao(): SavedTrickDao = _savedTrickDao.value

  public override fun mistakeDao(): MistakeDao = _mistakeDao.value

  public override fun revisionDao(): RevisionDao = _revisionDao.value

  public override fun studyDayDao(): StudyDayDao = _studyDayDao.value

  public override fun analyticsDao(): AnalyticsDao = _analyticsDao.value

  public override fun inventoryDao(): InventoryDao = _inventoryDao.value

  public override fun searchDao(): SearchDao = _searchDao.value

  public override fun aiConversationDao(): AiConversationDao = _aiConversationDao.value
}
