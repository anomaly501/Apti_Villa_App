package app.apti.villa.core.di

import android.content.Context
import androidx.room.Room
import app.apti.villa.data.local.dao.*
import app.apti.villa.data.local.db.AppDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(
        @ApplicationContext context: Context,
    ): AppDatabase {
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            AppDatabase.DATABASE_NAME,
        )
            .addCallback(AppDatabase.SeedCallback(context))
            .addMigrations(
                AppDatabase.MIGRATION_1_2,
                AppDatabase.MIGRATION_2_3,
                AppDatabase.MIGRATION_3_4,
                AppDatabase.MIGRATION_4_5,
                AppDatabase.MIGRATION_5_6,
            )
            .build()
    }

    @Provides
    fun provideExamDao(db: AppDatabase): ExamDao = db.examDao()

    @Provides
    fun provideSubjectDao(db: AppDatabase): SubjectDao = db.subjectDao()

    @Provides
    fun provideTopicDao(db: AppDatabase): TopicDao = db.topicDao()

    @Provides
    fun provideSubTopicDao(db: AppDatabase): SubTopicDao = db.subTopicDao()

    @Provides
    fun provideTagDao(db: AppDatabase): TagDao = db.tagDao()

    @Provides
    fun provideUserProfileDao(db: AppDatabase): UserProfileDao = db.userProfileDao()

    @Provides
    fun provideSourceDao(db: AppDatabase): SourceDao = db.sourceDao()

    @Provides
    fun provideTestDao(db: AppDatabase): TestDao = db.testDao()

    @Provides
    fun provideQuestionDao(db: AppDatabase): QuestionDao = db.questionDao()

    @Provides
    fun provideQuestionOptionDao(db: AppDatabase): QuestionOptionDao = db.questionOptionDao()

    @Provides
    fun provideTestQuestionDao(db: AppDatabase): TestQuestionDao = db.testQuestionDao()

    @Provides
    fun provideHintDao(db: AppDatabase): HintDao = db.hintDao()

    @Provides
    fun provideTrickDao(db: AppDatabase): TrickDao = db.trickDao()

    @Provides
    fun provideSolutionStepDao(db: AppDatabase): SolutionStepDao = db.solutionStepDao()

    @Provides
    fun provideAttemptDao(db: AppDatabase): AttemptDao = db.attemptDao()

    @Provides
    fun provideAnswerDao(db: AppDatabase): AnswerDao = db.answerDao()

    @Provides
    fun provideAnswerSubmissionDao(db: AppDatabase): AnswerSubmissionDao = db.answerSubmissionDao()

    @Provides
    fun provideHintUsageDao(db: AppDatabase): HintUsageDao = db.hintUsageDao()

    @Provides
    fun provideQuestionTagDao(db: AppDatabase): QuestionTagDao = db.questionTagDao()

    @Provides
    fun provideSavedTrickDao(db: AppDatabase): SavedTrickDao = db.savedTrickDao()

    @Provides
    fun provideMistakeDao(db: AppDatabase): MistakeDao = db.mistakeDao()

    @Provides
    fun provideRevisionDao(db: AppDatabase): RevisionDao = db.revisionDao()

    @Provides
    fun provideStudyDayDao(db: AppDatabase): StudyDayDao = db.studyDayDao()

    @Provides
    fun provideAnalyticsDao(db: AppDatabase): AnalyticsDao = db.analyticsDao()

    @Provides
    fun provideInventoryDao(db: AppDatabase): InventoryDao = db.inventoryDao()

    @Provides
    fun provideSearchDao(db: AppDatabase): SearchDao = db.searchDao()

    @Provides
    fun provideAiConversationDao(db: AppDatabase): AiConversationDao = db.aiConversationDao()
}


