package app.apti.villa.data.repository;

import app.apti.villa.data.local.dao.AnswerDao;
import app.apti.villa.data.local.dao.AnswerSubmissionDao;
import app.apti.villa.data.local.dao.AttemptDao;
import app.apti.villa.data.local.dao.ExamDao;
import app.apti.villa.data.local.dao.HintDao;
import app.apti.villa.data.local.dao.MistakeDao;
import app.apti.villa.data.local.dao.QuestionDao;
import app.apti.villa.data.local.dao.QuestionOptionDao;
import app.apti.villa.data.local.dao.RevisionDao;
import app.apti.villa.data.local.dao.SolutionStepDao;
import app.apti.villa.data.local.dao.StudyDayDao;
import app.apti.villa.data.local.dao.TestDao;
import app.apti.villa.data.local.dao.TestQuestionDao;
import app.apti.villa.data.local.dao.TrickDao;
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
public final class TestRepositoryImpl_Factory implements Factory<TestRepositoryImpl> {
  private final Provider<AppDatabase> dbProvider;

  private final Provider<TestDao> testDaoProvider;

  private final Provider<TestQuestionDao> testQuestionDaoProvider;

  private final Provider<AttemptDao> attemptDaoProvider;

  private final Provider<AnswerDao> answerDaoProvider;

  private final Provider<AnswerSubmissionDao> answerSubmissionDaoProvider;

  private final Provider<QuestionDao> questionDaoProvider;

  private final Provider<QuestionOptionDao> questionOptionDaoProvider;

  private final Provider<HintDao> hintDaoProvider;

  private final Provider<TrickDao> trickDaoProvider;

  private final Provider<SolutionStepDao> solutionStepDaoProvider;

  private final Provider<ExamDao> examDaoProvider;

  private final Provider<MistakeDao> mistakeDaoProvider;

  private final Provider<RevisionDao> revisionDaoProvider;

  private final Provider<StudyDayDao> studyDayDaoProvider;

  private final Provider<CoroutineDispatcher> ioDispatcherProvider;

  public TestRepositoryImpl_Factory(Provider<AppDatabase> dbProvider,
      Provider<TestDao> testDaoProvider, Provider<TestQuestionDao> testQuestionDaoProvider,
      Provider<AttemptDao> attemptDaoProvider, Provider<AnswerDao> answerDaoProvider,
      Provider<AnswerSubmissionDao> answerSubmissionDaoProvider,
      Provider<QuestionDao> questionDaoProvider,
      Provider<QuestionOptionDao> questionOptionDaoProvider, Provider<HintDao> hintDaoProvider,
      Provider<TrickDao> trickDaoProvider, Provider<SolutionStepDao> solutionStepDaoProvider,
      Provider<ExamDao> examDaoProvider, Provider<MistakeDao> mistakeDaoProvider,
      Provider<RevisionDao> revisionDaoProvider, Provider<StudyDayDao> studyDayDaoProvider,
      Provider<CoroutineDispatcher> ioDispatcherProvider) {
    this.dbProvider = dbProvider;
    this.testDaoProvider = testDaoProvider;
    this.testQuestionDaoProvider = testQuestionDaoProvider;
    this.attemptDaoProvider = attemptDaoProvider;
    this.answerDaoProvider = answerDaoProvider;
    this.answerSubmissionDaoProvider = answerSubmissionDaoProvider;
    this.questionDaoProvider = questionDaoProvider;
    this.questionOptionDaoProvider = questionOptionDaoProvider;
    this.hintDaoProvider = hintDaoProvider;
    this.trickDaoProvider = trickDaoProvider;
    this.solutionStepDaoProvider = solutionStepDaoProvider;
    this.examDaoProvider = examDaoProvider;
    this.mistakeDaoProvider = mistakeDaoProvider;
    this.revisionDaoProvider = revisionDaoProvider;
    this.studyDayDaoProvider = studyDayDaoProvider;
    this.ioDispatcherProvider = ioDispatcherProvider;
  }

  @Override
  public TestRepositoryImpl get() {
    return newInstance(dbProvider.get(), testDaoProvider.get(), testQuestionDaoProvider.get(), attemptDaoProvider.get(), answerDaoProvider.get(), answerSubmissionDaoProvider.get(), questionDaoProvider.get(), questionOptionDaoProvider.get(), hintDaoProvider.get(), trickDaoProvider.get(), solutionStepDaoProvider.get(), examDaoProvider.get(), mistakeDaoProvider.get(), revisionDaoProvider.get(), studyDayDaoProvider.get(), ioDispatcherProvider.get());
  }

  public static TestRepositoryImpl_Factory create(javax.inject.Provider<AppDatabase> dbProvider,
      javax.inject.Provider<TestDao> testDaoProvider,
      javax.inject.Provider<TestQuestionDao> testQuestionDaoProvider,
      javax.inject.Provider<AttemptDao> attemptDaoProvider,
      javax.inject.Provider<AnswerDao> answerDaoProvider,
      javax.inject.Provider<AnswerSubmissionDao> answerSubmissionDaoProvider,
      javax.inject.Provider<QuestionDao> questionDaoProvider,
      javax.inject.Provider<QuestionOptionDao> questionOptionDaoProvider,
      javax.inject.Provider<HintDao> hintDaoProvider,
      javax.inject.Provider<TrickDao> trickDaoProvider,
      javax.inject.Provider<SolutionStepDao> solutionStepDaoProvider,
      javax.inject.Provider<ExamDao> examDaoProvider,
      javax.inject.Provider<MistakeDao> mistakeDaoProvider,
      javax.inject.Provider<RevisionDao> revisionDaoProvider,
      javax.inject.Provider<StudyDayDao> studyDayDaoProvider,
      javax.inject.Provider<CoroutineDispatcher> ioDispatcherProvider) {
    return new TestRepositoryImpl_Factory(Providers.asDaggerProvider(dbProvider), Providers.asDaggerProvider(testDaoProvider), Providers.asDaggerProvider(testQuestionDaoProvider), Providers.asDaggerProvider(attemptDaoProvider), Providers.asDaggerProvider(answerDaoProvider), Providers.asDaggerProvider(answerSubmissionDaoProvider), Providers.asDaggerProvider(questionDaoProvider), Providers.asDaggerProvider(questionOptionDaoProvider), Providers.asDaggerProvider(hintDaoProvider), Providers.asDaggerProvider(trickDaoProvider), Providers.asDaggerProvider(solutionStepDaoProvider), Providers.asDaggerProvider(examDaoProvider), Providers.asDaggerProvider(mistakeDaoProvider), Providers.asDaggerProvider(revisionDaoProvider), Providers.asDaggerProvider(studyDayDaoProvider), Providers.asDaggerProvider(ioDispatcherProvider));
  }

  public static TestRepositoryImpl_Factory create(Provider<AppDatabase> dbProvider,
      Provider<TestDao> testDaoProvider, Provider<TestQuestionDao> testQuestionDaoProvider,
      Provider<AttemptDao> attemptDaoProvider, Provider<AnswerDao> answerDaoProvider,
      Provider<AnswerSubmissionDao> answerSubmissionDaoProvider,
      Provider<QuestionDao> questionDaoProvider,
      Provider<QuestionOptionDao> questionOptionDaoProvider, Provider<HintDao> hintDaoProvider,
      Provider<TrickDao> trickDaoProvider, Provider<SolutionStepDao> solutionStepDaoProvider,
      Provider<ExamDao> examDaoProvider, Provider<MistakeDao> mistakeDaoProvider,
      Provider<RevisionDao> revisionDaoProvider, Provider<StudyDayDao> studyDayDaoProvider,
      Provider<CoroutineDispatcher> ioDispatcherProvider) {
    return new TestRepositoryImpl_Factory(dbProvider, testDaoProvider, testQuestionDaoProvider, attemptDaoProvider, answerDaoProvider, answerSubmissionDaoProvider, questionDaoProvider, questionOptionDaoProvider, hintDaoProvider, trickDaoProvider, solutionStepDaoProvider, examDaoProvider, mistakeDaoProvider, revisionDaoProvider, studyDayDaoProvider, ioDispatcherProvider);
  }

  public static TestRepositoryImpl newInstance(AppDatabase db, TestDao testDao,
      TestQuestionDao testQuestionDao, AttemptDao attemptDao, AnswerDao answerDao,
      AnswerSubmissionDao answerSubmissionDao, QuestionDao questionDao,
      QuestionOptionDao questionOptionDao, HintDao hintDao, TrickDao trickDao,
      SolutionStepDao solutionStepDao, ExamDao examDao, MistakeDao mistakeDao,
      RevisionDao revisionDao, StudyDayDao studyDayDao, CoroutineDispatcher ioDispatcher) {
    return new TestRepositoryImpl(db, testDao, testQuestionDao, attemptDao, answerDao, answerSubmissionDao, questionDao, questionOptionDao, hintDao, trickDao, solutionStepDao, examDao, mistakeDao, revisionDao, studyDayDao, ioDispatcher);
  }
}
