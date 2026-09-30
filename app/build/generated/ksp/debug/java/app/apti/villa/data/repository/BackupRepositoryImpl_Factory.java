package app.apti.villa.data.repository;

import app.apti.villa.data.local.dao.AnswerDao;
import app.apti.villa.data.local.dao.AnswerSubmissionDao;
import app.apti.villa.data.local.dao.AttemptDao;
import app.apti.villa.data.local.dao.ExamDao;
import app.apti.villa.data.local.dao.HintDao;
import app.apti.villa.data.local.dao.HintUsageDao;
import app.apti.villa.data.local.dao.MistakeDao;
import app.apti.villa.data.local.dao.QuestionDao;
import app.apti.villa.data.local.dao.QuestionOptionDao;
import app.apti.villa.data.local.dao.QuestionTagDao;
import app.apti.villa.data.local.dao.RevisionDao;
import app.apti.villa.data.local.dao.SavedTrickDao;
import app.apti.villa.data.local.dao.SolutionStepDao;
import app.apti.villa.data.local.dao.StudyDayDao;
import app.apti.villa.data.local.dao.SubjectDao;
import app.apti.villa.data.local.dao.TestDao;
import app.apti.villa.data.local.dao.TestQuestionDao;
import app.apti.villa.data.local.dao.TopicDao;
import app.apti.villa.data.local.dao.TrickDao;
import app.apti.villa.data.local.dao.UserProfileDao;
import app.apti.villa.data.local.db.AppDatabase;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.Provider;
import dagger.internal.Providers;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;
import kotlinx.coroutines.CoroutineDispatcher;

@ScopeMetadata("javax.inject.Singleton")
@QualifierMetadata("app.apti.villa.core.common.IoDispatcher")
@DaggerGenerated
@Generated(
    value = "dagger.internal.codegen.ComponentProcessor",
    comments = "https://dagger.dev"
)
@SuppressWarnings({
    "unchecked",
    "rawtypes",
    "KotlinInternal",
    "KotlinInternalInJava",
    "cast",
    "deprecation",
    "nullness:initialization.field.uninitialized"
})
public final class BackupRepositoryImpl_Factory implements Factory<BackupRepositoryImpl> {
  private final Provider<AppDatabase> dbProvider;

  private final Provider<QuestionDao> questionDaoProvider;

  private final Provider<QuestionOptionDao> questionOptionDaoProvider;

  private final Provider<HintDao> hintDaoProvider;

  private final Provider<TrickDao> trickDaoProvider;

  private final Provider<SolutionStepDao> solutionStepDaoProvider;

  private final Provider<QuestionTagDao> questionTagDaoProvider;

  private final Provider<TestDao> testDaoProvider;

  private final Provider<TestQuestionDao> testQuestionDaoProvider;

  private final Provider<AttemptDao> attemptDaoProvider;

  private final Provider<AnswerDao> answerDaoProvider;

  private final Provider<AnswerSubmissionDao> answerSubmissionDaoProvider;

  private final Provider<HintUsageDao> hintUsageDaoProvider;

  private final Provider<MistakeDao> mistakeDaoProvider;

  private final Provider<RevisionDao> revisionDaoProvider;

  private final Provider<StudyDayDao> studyDayDaoProvider;

  private final Provider<SavedTrickDao> savedTrickDaoProvider;

  private final Provider<UserProfileDao> userProfileDaoProvider;

  private final Provider<SubjectDao> subjectDaoProvider;

  private final Provider<TopicDao> topicDaoProvider;

  private final Provider<ExamDao> examDaoProvider;

  private final Provider<CoroutineDispatcher> ioDispatcherProvider;

  public BackupRepositoryImpl_Factory(Provider<AppDatabase> dbProvider,
      Provider<QuestionDao> questionDaoProvider,
      Provider<QuestionOptionDao> questionOptionDaoProvider, Provider<HintDao> hintDaoProvider,
      Provider<TrickDao> trickDaoProvider, Provider<SolutionStepDao> solutionStepDaoProvider,
      Provider<QuestionTagDao> questionTagDaoProvider, Provider<TestDao> testDaoProvider,
      Provider<TestQuestionDao> testQuestionDaoProvider, Provider<AttemptDao> attemptDaoProvider,
      Provider<AnswerDao> answerDaoProvider,
      Provider<AnswerSubmissionDao> answerSubmissionDaoProvider,
      Provider<HintUsageDao> hintUsageDaoProvider, Provider<MistakeDao> mistakeDaoProvider,
      Provider<RevisionDao> revisionDaoProvider, Provider<StudyDayDao> studyDayDaoProvider,
      Provider<SavedTrickDao> savedTrickDaoProvider,
      Provider<UserProfileDao> userProfileDaoProvider, Provider<SubjectDao> subjectDaoProvider,
      Provider<TopicDao> topicDaoProvider, Provider<ExamDao> examDaoProvider,
      Provider<CoroutineDispatcher> ioDispatcherProvider) {
    this.dbProvider = dbProvider;
    this.questionDaoProvider = questionDaoProvider;
    this.questionOptionDaoProvider = questionOptionDaoProvider;
    this.hintDaoProvider = hintDaoProvider;
    this.trickDaoProvider = trickDaoProvider;
    this.solutionStepDaoProvider = solutionStepDaoProvider;
    this.questionTagDaoProvider = questionTagDaoProvider;
    this.testDaoProvider = testDaoProvider;
    this.testQuestionDaoProvider = testQuestionDaoProvider;
    this.attemptDaoProvider = attemptDaoProvider;
    this.answerDaoProvider = answerDaoProvider;
    this.answerSubmissionDaoProvider = answerSubmissionDaoProvider;
    this.hintUsageDaoProvider = hintUsageDaoProvider;
    this.mistakeDaoProvider = mistakeDaoProvider;
    this.revisionDaoProvider = revisionDaoProvider;
    this.studyDayDaoProvider = studyDayDaoProvider;
    this.savedTrickDaoProvider = savedTrickDaoProvider;
    this.userProfileDaoProvider = userProfileDaoProvider;
    this.subjectDaoProvider = subjectDaoProvider;
    this.topicDaoProvider = topicDaoProvider;
    this.examDaoProvider = examDaoProvider;
    this.ioDispatcherProvider = ioDispatcherProvider;
  }

  @Override
  public BackupRepositoryImpl get() {
    return newInstance(dbProvider.get(), questionDaoProvider.get(), questionOptionDaoProvider.get(), hintDaoProvider.get(), trickDaoProvider.get(), solutionStepDaoProvider.get(), questionTagDaoProvider.get(), testDaoProvider.get(), testQuestionDaoProvider.get(), attemptDaoProvider.get(), answerDaoProvider.get(), answerSubmissionDaoProvider.get(), hintUsageDaoProvider.get(), mistakeDaoProvider.get(), revisionDaoProvider.get(), studyDayDaoProvider.get(), savedTrickDaoProvider.get(), userProfileDaoProvider.get(), subjectDaoProvider.get(), topicDaoProvider.get(), examDaoProvider.get(), ioDispatcherProvider.get());
  }

  public static BackupRepositoryImpl_Factory create(javax.inject.Provider<AppDatabase> dbProvider,
      javax.inject.Provider<QuestionDao> questionDaoProvider,
      javax.inject.Provider<QuestionOptionDao> questionOptionDaoProvider,
      javax.inject.Provider<HintDao> hintDaoProvider,
      javax.inject.Provider<TrickDao> trickDaoProvider,
      javax.inject.Provider<SolutionStepDao> solutionStepDaoProvider,
      javax.inject.Provider<QuestionTagDao> questionTagDaoProvider,
      javax.inject.Provider<TestDao> testDaoProvider,
      javax.inject.Provider<TestQuestionDao> testQuestionDaoProvider,
      javax.inject.Provider<AttemptDao> attemptDaoProvider,
      javax.inject.Provider<AnswerDao> answerDaoProvider,
      javax.inject.Provider<AnswerSubmissionDao> answerSubmissionDaoProvider,
      javax.inject.Provider<HintUsageDao> hintUsageDaoProvider,
      javax.inject.Provider<MistakeDao> mistakeDaoProvider,
      javax.inject.Provider<RevisionDao> revisionDaoProvider,
      javax.inject.Provider<StudyDayDao> studyDayDaoProvider,
      javax.inject.Provider<SavedTrickDao> savedTrickDaoProvider,
      javax.inject.Provider<UserProfileDao> userProfileDaoProvider,
      javax.inject.Provider<SubjectDao> subjectDaoProvider,
      javax.inject.Provider<TopicDao> topicDaoProvider,
      javax.inject.Provider<ExamDao> examDaoProvider,
      javax.inject.Provider<CoroutineDispatcher> ioDispatcherProvider) {
    return new BackupRepositoryImpl_Factory(Providers.asDaggerProvider(dbProvider), Providers.asDaggerProvider(questionDaoProvider), Providers.asDaggerProvider(questionOptionDaoProvider), Providers.asDaggerProvider(hintDaoProvider), Providers.asDaggerProvider(trickDaoProvider), Providers.asDaggerProvider(solutionStepDaoProvider), Providers.asDaggerProvider(questionTagDaoProvider), Providers.asDaggerProvider(testDaoProvider), Providers.asDaggerProvider(testQuestionDaoProvider), Providers.asDaggerProvider(attemptDaoProvider), Providers.asDaggerProvider(answerDaoProvider), Providers.asDaggerProvider(answerSubmissionDaoProvider), Providers.asDaggerProvider(hintUsageDaoProvider), Providers.asDaggerProvider(mistakeDaoProvider), Providers.asDaggerProvider(revisionDaoProvider), Providers.asDaggerProvider(studyDayDaoProvider), Providers.asDaggerProvider(savedTrickDaoProvider), Providers.asDaggerProvider(userProfileDaoProvider), Providers.asDaggerProvider(subjectDaoProvider), Providers.asDaggerProvider(topicDaoProvider), Providers.asDaggerProvider(examDaoProvider), Providers.asDaggerProvider(ioDispatcherProvider));
  }

  public static BackupRepositoryImpl_Factory create(Provider<AppDatabase> dbProvider,
      Provider<QuestionDao> questionDaoProvider,
      Provider<QuestionOptionDao> questionOptionDaoProvider, Provider<HintDao> hintDaoProvider,
      Provider<TrickDao> trickDaoProvider, Provider<SolutionStepDao> solutionStepDaoProvider,
      Provider<QuestionTagDao> questionTagDaoProvider, Provider<TestDao> testDaoProvider,
      Provider<TestQuestionDao> testQuestionDaoProvider, Provider<AttemptDao> attemptDaoProvider,
      Provider<AnswerDao> answerDaoProvider,
      Provider<AnswerSubmissionDao> answerSubmissionDaoProvider,
      Provider<HintUsageDao> hintUsageDaoProvider, Provider<MistakeDao> mistakeDaoProvider,
      Provider<RevisionDao> revisionDaoProvider, Provider<StudyDayDao> studyDayDaoProvider,
      Provider<SavedTrickDao> savedTrickDaoProvider,
      Provider<UserProfileDao> userProfileDaoProvider, Provider<SubjectDao> subjectDaoProvider,
      Provider<TopicDao> topicDaoProvider, Provider<ExamDao> examDaoProvider,
      Provider<CoroutineDispatcher> ioDispatcherProvider) {
    return new BackupRepositoryImpl_Factory(dbProvider, questionDaoProvider, questionOptionDaoProvider, hintDaoProvider, trickDaoProvider, solutionStepDaoProvider, questionTagDaoProvider, testDaoProvider, testQuestionDaoProvider, attemptDaoProvider, answerDaoProvider, answerSubmissionDaoProvider, hintUsageDaoProvider, mistakeDaoProvider, revisionDaoProvider, studyDayDaoProvider, savedTrickDaoProvider, userProfileDaoProvider, subjectDaoProvider, topicDaoProvider, examDaoProvider, ioDispatcherProvider);
  }

  public static BackupRepositoryImpl newInstance(AppDatabase db, QuestionDao questionDao,
      QuestionOptionDao questionOptionDao, HintDao hintDao, TrickDao trickDao,
      SolutionStepDao solutionStepDao, QuestionTagDao questionTagDao, TestDao testDao,
      TestQuestionDao testQuestionDao, AttemptDao attemptDao, AnswerDao answerDao,
      AnswerSubmissionDao answerSubmissionDao, HintUsageDao hintUsageDao, MistakeDao mistakeDao,
      RevisionDao revisionDao, StudyDayDao studyDayDao, SavedTrickDao savedTrickDao,
      UserProfileDao userProfileDao, SubjectDao subjectDao, TopicDao topicDao, ExamDao examDao,
      CoroutineDispatcher ioDispatcher) {
    return new BackupRepositoryImpl(db, questionDao, questionOptionDao, hintDao, trickDao, solutionStepDao, questionTagDao, testDao, testQuestionDao, attemptDao, answerDao, answerSubmissionDao, hintUsageDao, mistakeDao, revisionDao, studyDayDao, savedTrickDao, userProfileDao, subjectDao, topicDao, examDao, ioDispatcher);
  }
}
