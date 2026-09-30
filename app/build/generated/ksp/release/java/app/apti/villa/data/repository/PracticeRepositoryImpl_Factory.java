package app.apti.villa.data.repository;

import app.apti.villa.data.local.dao.AnswerDao;
import app.apti.villa.data.local.dao.AnswerSubmissionDao;
import app.apti.villa.data.local.dao.AttemptDao;
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
import app.apti.villa.data.local.dao.TagDao;
import app.apti.villa.data.local.dao.TopicDao;
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
public final class PracticeRepositoryImpl_Factory implements Factory<PracticeRepositoryImpl> {
  private final Provider<AppDatabase> dbProvider;

  private final Provider<AttemptDao> attemptDaoProvider;

  private final Provider<AnswerDao> answerDaoProvider;

  private final Provider<AnswerSubmissionDao> answerSubmissionDaoProvider;

  private final Provider<HintUsageDao> hintUsageDaoProvider;

  private final Provider<QuestionDao> questionDaoProvider;

  private final Provider<QuestionOptionDao> questionOptionDaoProvider;

  private final Provider<HintDao> hintDaoProvider;

  private final Provider<TrickDao> trickDaoProvider;

  private final Provider<SolutionStepDao> solutionStepDaoProvider;

  private final Provider<QuestionTagDao> questionTagDaoProvider;

  private final Provider<SavedTrickDao> savedTrickDaoProvider;

  private final Provider<TopicDao> topicDaoProvider;

  private final Provider<TagDao> tagDaoProvider;

  private final Provider<MistakeDao> mistakeDaoProvider;

  private final Provider<RevisionDao> revisionDaoProvider;

  private final Provider<StudyDayDao> studyDayDaoProvider;

  private final Provider<CoroutineDispatcher> ioDispatcherProvider;

  public PracticeRepositoryImpl_Factory(Provider<AppDatabase> dbProvider,
      Provider<AttemptDao> attemptDaoProvider, Provider<AnswerDao> answerDaoProvider,
      Provider<AnswerSubmissionDao> answerSubmissionDaoProvider,
      Provider<HintUsageDao> hintUsageDaoProvider, Provider<QuestionDao> questionDaoProvider,
      Provider<QuestionOptionDao> questionOptionDaoProvider, Provider<HintDao> hintDaoProvider,
      Provider<TrickDao> trickDaoProvider, Provider<SolutionStepDao> solutionStepDaoProvider,
      Provider<QuestionTagDao> questionTagDaoProvider,
      Provider<SavedTrickDao> savedTrickDaoProvider, Provider<TopicDao> topicDaoProvider,
      Provider<TagDao> tagDaoProvider, Provider<MistakeDao> mistakeDaoProvider,
      Provider<RevisionDao> revisionDaoProvider, Provider<StudyDayDao> studyDayDaoProvider,
      Provider<CoroutineDispatcher> ioDispatcherProvider) {
    this.dbProvider = dbProvider;
    this.attemptDaoProvider = attemptDaoProvider;
    this.answerDaoProvider = answerDaoProvider;
    this.answerSubmissionDaoProvider = answerSubmissionDaoProvider;
    this.hintUsageDaoProvider = hintUsageDaoProvider;
    this.questionDaoProvider = questionDaoProvider;
    this.questionOptionDaoProvider = questionOptionDaoProvider;
    this.hintDaoProvider = hintDaoProvider;
    this.trickDaoProvider = trickDaoProvider;
    this.solutionStepDaoProvider = solutionStepDaoProvider;
    this.questionTagDaoProvider = questionTagDaoProvider;
    this.savedTrickDaoProvider = savedTrickDaoProvider;
    this.topicDaoProvider = topicDaoProvider;
    this.tagDaoProvider = tagDaoProvider;
    this.mistakeDaoProvider = mistakeDaoProvider;
    this.revisionDaoProvider = revisionDaoProvider;
    this.studyDayDaoProvider = studyDayDaoProvider;
    this.ioDispatcherProvider = ioDispatcherProvider;
  }

  @Override
  public PracticeRepositoryImpl get() {
    return newInstance(dbProvider.get(), attemptDaoProvider.get(), answerDaoProvider.get(), answerSubmissionDaoProvider.get(), hintUsageDaoProvider.get(), questionDaoProvider.get(), questionOptionDaoProvider.get(), hintDaoProvider.get(), trickDaoProvider.get(), solutionStepDaoProvider.get(), questionTagDaoProvider.get(), savedTrickDaoProvider.get(), topicDaoProvider.get(), tagDaoProvider.get(), mistakeDaoProvider.get(), revisionDaoProvider.get(), studyDayDaoProvider.get(), ioDispatcherProvider.get());
  }

  public static PracticeRepositoryImpl_Factory create(javax.inject.Provider<AppDatabase> dbProvider,
      javax.inject.Provider<AttemptDao> attemptDaoProvider,
      javax.inject.Provider<AnswerDao> answerDaoProvider,
      javax.inject.Provider<AnswerSubmissionDao> answerSubmissionDaoProvider,
      javax.inject.Provider<HintUsageDao> hintUsageDaoProvider,
      javax.inject.Provider<QuestionDao> questionDaoProvider,
      javax.inject.Provider<QuestionOptionDao> questionOptionDaoProvider,
      javax.inject.Provider<HintDao> hintDaoProvider,
      javax.inject.Provider<TrickDao> trickDaoProvider,
      javax.inject.Provider<SolutionStepDao> solutionStepDaoProvider,
      javax.inject.Provider<QuestionTagDao> questionTagDaoProvider,
      javax.inject.Provider<SavedTrickDao> savedTrickDaoProvider,
      javax.inject.Provider<TopicDao> topicDaoProvider,
      javax.inject.Provider<TagDao> tagDaoProvider,
      javax.inject.Provider<MistakeDao> mistakeDaoProvider,
      javax.inject.Provider<RevisionDao> revisionDaoProvider,
      javax.inject.Provider<StudyDayDao> studyDayDaoProvider,
      javax.inject.Provider<CoroutineDispatcher> ioDispatcherProvider) {
    return new PracticeRepositoryImpl_Factory(Providers.asDaggerProvider(dbProvider), Providers.asDaggerProvider(attemptDaoProvider), Providers.asDaggerProvider(answerDaoProvider), Providers.asDaggerProvider(answerSubmissionDaoProvider), Providers.asDaggerProvider(hintUsageDaoProvider), Providers.asDaggerProvider(questionDaoProvider), Providers.asDaggerProvider(questionOptionDaoProvider), Providers.asDaggerProvider(hintDaoProvider), Providers.asDaggerProvider(trickDaoProvider), Providers.asDaggerProvider(solutionStepDaoProvider), Providers.asDaggerProvider(questionTagDaoProvider), Providers.asDaggerProvider(savedTrickDaoProvider), Providers.asDaggerProvider(topicDaoProvider), Providers.asDaggerProvider(tagDaoProvider), Providers.asDaggerProvider(mistakeDaoProvider), Providers.asDaggerProvider(revisionDaoProvider), Providers.asDaggerProvider(studyDayDaoProvider), Providers.asDaggerProvider(ioDispatcherProvider));
  }

  public static PracticeRepositoryImpl_Factory create(Provider<AppDatabase> dbProvider,
      Provider<AttemptDao> attemptDaoProvider, Provider<AnswerDao> answerDaoProvider,
      Provider<AnswerSubmissionDao> answerSubmissionDaoProvider,
      Provider<HintUsageDao> hintUsageDaoProvider, Provider<QuestionDao> questionDaoProvider,
      Provider<QuestionOptionDao> questionOptionDaoProvider, Provider<HintDao> hintDaoProvider,
      Provider<TrickDao> trickDaoProvider, Provider<SolutionStepDao> solutionStepDaoProvider,
      Provider<QuestionTagDao> questionTagDaoProvider,
      Provider<SavedTrickDao> savedTrickDaoProvider, Provider<TopicDao> topicDaoProvider,
      Provider<TagDao> tagDaoProvider, Provider<MistakeDao> mistakeDaoProvider,
      Provider<RevisionDao> revisionDaoProvider, Provider<StudyDayDao> studyDayDaoProvider,
      Provider<CoroutineDispatcher> ioDispatcherProvider) {
    return new PracticeRepositoryImpl_Factory(dbProvider, attemptDaoProvider, answerDaoProvider, answerSubmissionDaoProvider, hintUsageDaoProvider, questionDaoProvider, questionOptionDaoProvider, hintDaoProvider, trickDaoProvider, solutionStepDaoProvider, questionTagDaoProvider, savedTrickDaoProvider, topicDaoProvider, tagDaoProvider, mistakeDaoProvider, revisionDaoProvider, studyDayDaoProvider, ioDispatcherProvider);
  }

  public static PracticeRepositoryImpl newInstance(AppDatabase db, AttemptDao attemptDao,
      AnswerDao answerDao, AnswerSubmissionDao answerSubmissionDao, HintUsageDao hintUsageDao,
      QuestionDao questionDao, QuestionOptionDao questionOptionDao, HintDao hintDao,
      TrickDao trickDao, SolutionStepDao solutionStepDao, QuestionTagDao questionTagDao,
      SavedTrickDao savedTrickDao, TopicDao topicDao, TagDao tagDao, MistakeDao mistakeDao,
      RevisionDao revisionDao, StudyDayDao studyDayDao, CoroutineDispatcher ioDispatcher) {
    return new PracticeRepositoryImpl(db, attemptDao, answerDao, answerSubmissionDao, hintUsageDao, questionDao, questionOptionDao, hintDao, trickDao, solutionStepDao, questionTagDao, savedTrickDao, topicDao, tagDao, mistakeDao, revisionDao, studyDayDao, ioDispatcher);
  }
}
