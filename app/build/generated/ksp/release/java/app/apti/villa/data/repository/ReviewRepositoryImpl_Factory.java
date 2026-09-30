package app.apti.villa.data.repository;

import app.apti.villa.data.local.dao.AnswerDao;
import app.apti.villa.data.local.dao.AttemptDao;
import app.apti.villa.data.local.dao.HintDao;
import app.apti.villa.data.local.dao.MistakeDao;
import app.apti.villa.data.local.dao.QuestionDao;
import app.apti.villa.data.local.dao.QuestionOptionDao;
import app.apti.villa.data.local.dao.QuestionTagDao;
import app.apti.villa.data.local.dao.RevisionDao;
import app.apti.villa.data.local.dao.SavedTrickDao;
import app.apti.villa.data.local.dao.SolutionStepDao;
import app.apti.villa.data.local.dao.TagDao;
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
public final class ReviewRepositoryImpl_Factory implements Factory<ReviewRepositoryImpl> {
  private final Provider<AppDatabase> dbProvider;

  private final Provider<MistakeDao> mistakeDaoProvider;

  private final Provider<RevisionDao> revisionDaoProvider;

  private final Provider<QuestionDao> questionDaoProvider;

  private final Provider<QuestionOptionDao> questionOptionDaoProvider;

  private final Provider<HintDao> hintDaoProvider;

  private final Provider<TrickDao> trickDaoProvider;

  private final Provider<SolutionStepDao> solutionStepDaoProvider;

  private final Provider<QuestionTagDao> questionTagDaoProvider;

  private final Provider<SavedTrickDao> savedTrickDaoProvider;

  private final Provider<TagDao> tagDaoProvider;

  private final Provider<AttemptDao> attemptDaoProvider;

  private final Provider<AnswerDao> answerDaoProvider;

  private final Provider<CoroutineDispatcher> ioDispatcherProvider;

  public ReviewRepositoryImpl_Factory(Provider<AppDatabase> dbProvider,
      Provider<MistakeDao> mistakeDaoProvider, Provider<RevisionDao> revisionDaoProvider,
      Provider<QuestionDao> questionDaoProvider,
      Provider<QuestionOptionDao> questionOptionDaoProvider, Provider<HintDao> hintDaoProvider,
      Provider<TrickDao> trickDaoProvider, Provider<SolutionStepDao> solutionStepDaoProvider,
      Provider<QuestionTagDao> questionTagDaoProvider,
      Provider<SavedTrickDao> savedTrickDaoProvider, Provider<TagDao> tagDaoProvider,
      Provider<AttemptDao> attemptDaoProvider, Provider<AnswerDao> answerDaoProvider,
      Provider<CoroutineDispatcher> ioDispatcherProvider) {
    this.dbProvider = dbProvider;
    this.mistakeDaoProvider = mistakeDaoProvider;
    this.revisionDaoProvider = revisionDaoProvider;
    this.questionDaoProvider = questionDaoProvider;
    this.questionOptionDaoProvider = questionOptionDaoProvider;
    this.hintDaoProvider = hintDaoProvider;
    this.trickDaoProvider = trickDaoProvider;
    this.solutionStepDaoProvider = solutionStepDaoProvider;
    this.questionTagDaoProvider = questionTagDaoProvider;
    this.savedTrickDaoProvider = savedTrickDaoProvider;
    this.tagDaoProvider = tagDaoProvider;
    this.attemptDaoProvider = attemptDaoProvider;
    this.answerDaoProvider = answerDaoProvider;
    this.ioDispatcherProvider = ioDispatcherProvider;
  }

  @Override
  public ReviewRepositoryImpl get() {
    return newInstance(dbProvider.get(), mistakeDaoProvider.get(), revisionDaoProvider.get(), questionDaoProvider.get(), questionOptionDaoProvider.get(), hintDaoProvider.get(), trickDaoProvider.get(), solutionStepDaoProvider.get(), questionTagDaoProvider.get(), savedTrickDaoProvider.get(), tagDaoProvider.get(), attemptDaoProvider.get(), answerDaoProvider.get(), ioDispatcherProvider.get());
  }

  public static ReviewRepositoryImpl_Factory create(javax.inject.Provider<AppDatabase> dbProvider,
      javax.inject.Provider<MistakeDao> mistakeDaoProvider,
      javax.inject.Provider<RevisionDao> revisionDaoProvider,
      javax.inject.Provider<QuestionDao> questionDaoProvider,
      javax.inject.Provider<QuestionOptionDao> questionOptionDaoProvider,
      javax.inject.Provider<HintDao> hintDaoProvider,
      javax.inject.Provider<TrickDao> trickDaoProvider,
      javax.inject.Provider<SolutionStepDao> solutionStepDaoProvider,
      javax.inject.Provider<QuestionTagDao> questionTagDaoProvider,
      javax.inject.Provider<SavedTrickDao> savedTrickDaoProvider,
      javax.inject.Provider<TagDao> tagDaoProvider,
      javax.inject.Provider<AttemptDao> attemptDaoProvider,
      javax.inject.Provider<AnswerDao> answerDaoProvider,
      javax.inject.Provider<CoroutineDispatcher> ioDispatcherProvider) {
    return new ReviewRepositoryImpl_Factory(Providers.asDaggerProvider(dbProvider), Providers.asDaggerProvider(mistakeDaoProvider), Providers.asDaggerProvider(revisionDaoProvider), Providers.asDaggerProvider(questionDaoProvider), Providers.asDaggerProvider(questionOptionDaoProvider), Providers.asDaggerProvider(hintDaoProvider), Providers.asDaggerProvider(trickDaoProvider), Providers.asDaggerProvider(solutionStepDaoProvider), Providers.asDaggerProvider(questionTagDaoProvider), Providers.asDaggerProvider(savedTrickDaoProvider), Providers.asDaggerProvider(tagDaoProvider), Providers.asDaggerProvider(attemptDaoProvider), Providers.asDaggerProvider(answerDaoProvider), Providers.asDaggerProvider(ioDispatcherProvider));
  }

  public static ReviewRepositoryImpl_Factory create(Provider<AppDatabase> dbProvider,
      Provider<MistakeDao> mistakeDaoProvider, Provider<RevisionDao> revisionDaoProvider,
      Provider<QuestionDao> questionDaoProvider,
      Provider<QuestionOptionDao> questionOptionDaoProvider, Provider<HintDao> hintDaoProvider,
      Provider<TrickDao> trickDaoProvider, Provider<SolutionStepDao> solutionStepDaoProvider,
      Provider<QuestionTagDao> questionTagDaoProvider,
      Provider<SavedTrickDao> savedTrickDaoProvider, Provider<TagDao> tagDaoProvider,
      Provider<AttemptDao> attemptDaoProvider, Provider<AnswerDao> answerDaoProvider,
      Provider<CoroutineDispatcher> ioDispatcherProvider) {
    return new ReviewRepositoryImpl_Factory(dbProvider, mistakeDaoProvider, revisionDaoProvider, questionDaoProvider, questionOptionDaoProvider, hintDaoProvider, trickDaoProvider, solutionStepDaoProvider, questionTagDaoProvider, savedTrickDaoProvider, tagDaoProvider, attemptDaoProvider, answerDaoProvider, ioDispatcherProvider);
  }

  public static ReviewRepositoryImpl newInstance(AppDatabase db, MistakeDao mistakeDao,
      RevisionDao revisionDao, QuestionDao questionDao, QuestionOptionDao questionOptionDao,
      HintDao hintDao, TrickDao trickDao, SolutionStepDao solutionStepDao,
      QuestionTagDao questionTagDao, SavedTrickDao savedTrickDao, TagDao tagDao,
      AttemptDao attemptDao, AnswerDao answerDao, CoroutineDispatcher ioDispatcher) {
    return new ReviewRepositoryImpl(db, mistakeDao, revisionDao, questionDao, questionOptionDao, hintDao, trickDao, solutionStepDao, questionTagDao, savedTrickDao, tagDao, attemptDao, answerDao, ioDispatcher);
  }
}
