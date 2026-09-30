package app.apti.villa.data.importer;

import app.apti.villa.data.local.dao.HintDao;
import app.apti.villa.data.local.dao.QuestionDao;
import app.apti.villa.data.local.dao.QuestionOptionDao;
import app.apti.villa.data.local.dao.SolutionStepDao;
import app.apti.villa.data.local.dao.SourceDao;
import app.apti.villa.data.local.dao.SubTopicDao;
import app.apti.villa.data.local.dao.SubjectDao;
import app.apti.villa.data.local.dao.TestDao;
import app.apti.villa.data.local.dao.TestQuestionDao;
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

@ScopeMetadata("javax.inject.Singleton")
@QualifierMetadata
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
public final class ImportRepositoryImpl_Factory implements Factory<ImportRepositoryImpl> {
  private final Provider<AppDatabase> dbProvider;

  private final Provider<SourceDao> sourceDaoProvider;

  private final Provider<TestDao> testDaoProvider;

  private final Provider<QuestionDao> questionDaoProvider;

  private final Provider<QuestionOptionDao> questionOptionDaoProvider;

  private final Provider<TestQuestionDao> testQuestionDaoProvider;

  private final Provider<HintDao> hintDaoProvider;

  private final Provider<TrickDao> trickDaoProvider;

  private final Provider<SolutionStepDao> solutionStepDaoProvider;

  private final Provider<SubjectDao> subjectDaoProvider;

  private final Provider<TopicDao> topicDaoProvider;

  private final Provider<SubTopicDao> subTopicDaoProvider;

  public ImportRepositoryImpl_Factory(Provider<AppDatabase> dbProvider,
      Provider<SourceDao> sourceDaoProvider, Provider<TestDao> testDaoProvider,
      Provider<QuestionDao> questionDaoProvider,
      Provider<QuestionOptionDao> questionOptionDaoProvider,
      Provider<TestQuestionDao> testQuestionDaoProvider, Provider<HintDao> hintDaoProvider,
      Provider<TrickDao> trickDaoProvider, Provider<SolutionStepDao> solutionStepDaoProvider,
      Provider<SubjectDao> subjectDaoProvider, Provider<TopicDao> topicDaoProvider,
      Provider<SubTopicDao> subTopicDaoProvider) {
    this.dbProvider = dbProvider;
    this.sourceDaoProvider = sourceDaoProvider;
    this.testDaoProvider = testDaoProvider;
    this.questionDaoProvider = questionDaoProvider;
    this.questionOptionDaoProvider = questionOptionDaoProvider;
    this.testQuestionDaoProvider = testQuestionDaoProvider;
    this.hintDaoProvider = hintDaoProvider;
    this.trickDaoProvider = trickDaoProvider;
    this.solutionStepDaoProvider = solutionStepDaoProvider;
    this.subjectDaoProvider = subjectDaoProvider;
    this.topicDaoProvider = topicDaoProvider;
    this.subTopicDaoProvider = subTopicDaoProvider;
  }

  @Override
  public ImportRepositoryImpl get() {
    return newInstance(dbProvider.get(), sourceDaoProvider.get(), testDaoProvider.get(), questionDaoProvider.get(), questionOptionDaoProvider.get(), testQuestionDaoProvider.get(), hintDaoProvider.get(), trickDaoProvider.get(), solutionStepDaoProvider.get(), subjectDaoProvider.get(), topicDaoProvider.get(), subTopicDaoProvider.get());
  }

  public static ImportRepositoryImpl_Factory create(javax.inject.Provider<AppDatabase> dbProvider,
      javax.inject.Provider<SourceDao> sourceDaoProvider,
      javax.inject.Provider<TestDao> testDaoProvider,
      javax.inject.Provider<QuestionDao> questionDaoProvider,
      javax.inject.Provider<QuestionOptionDao> questionOptionDaoProvider,
      javax.inject.Provider<TestQuestionDao> testQuestionDaoProvider,
      javax.inject.Provider<HintDao> hintDaoProvider,
      javax.inject.Provider<TrickDao> trickDaoProvider,
      javax.inject.Provider<SolutionStepDao> solutionStepDaoProvider,
      javax.inject.Provider<SubjectDao> subjectDaoProvider,
      javax.inject.Provider<TopicDao> topicDaoProvider,
      javax.inject.Provider<SubTopicDao> subTopicDaoProvider) {
    return new ImportRepositoryImpl_Factory(Providers.asDaggerProvider(dbProvider), Providers.asDaggerProvider(sourceDaoProvider), Providers.asDaggerProvider(testDaoProvider), Providers.asDaggerProvider(questionDaoProvider), Providers.asDaggerProvider(questionOptionDaoProvider), Providers.asDaggerProvider(testQuestionDaoProvider), Providers.asDaggerProvider(hintDaoProvider), Providers.asDaggerProvider(trickDaoProvider), Providers.asDaggerProvider(solutionStepDaoProvider), Providers.asDaggerProvider(subjectDaoProvider), Providers.asDaggerProvider(topicDaoProvider), Providers.asDaggerProvider(subTopicDaoProvider));
  }

  public static ImportRepositoryImpl_Factory create(Provider<AppDatabase> dbProvider,
      Provider<SourceDao> sourceDaoProvider, Provider<TestDao> testDaoProvider,
      Provider<QuestionDao> questionDaoProvider,
      Provider<QuestionOptionDao> questionOptionDaoProvider,
      Provider<TestQuestionDao> testQuestionDaoProvider, Provider<HintDao> hintDaoProvider,
      Provider<TrickDao> trickDaoProvider, Provider<SolutionStepDao> solutionStepDaoProvider,
      Provider<SubjectDao> subjectDaoProvider, Provider<TopicDao> topicDaoProvider,
      Provider<SubTopicDao> subTopicDaoProvider) {
    return new ImportRepositoryImpl_Factory(dbProvider, sourceDaoProvider, testDaoProvider, questionDaoProvider, questionOptionDaoProvider, testQuestionDaoProvider, hintDaoProvider, trickDaoProvider, solutionStepDaoProvider, subjectDaoProvider, topicDaoProvider, subTopicDaoProvider);
  }

  public static ImportRepositoryImpl newInstance(AppDatabase db, SourceDao sourceDao,
      TestDao testDao, QuestionDao questionDao, QuestionOptionDao questionOptionDao,
      TestQuestionDao testQuestionDao, HintDao hintDao, TrickDao trickDao,
      SolutionStepDao solutionStepDao, SubjectDao subjectDao, TopicDao topicDao,
      SubTopicDao subTopicDao) {
    return new ImportRepositoryImpl(db, sourceDao, testDao, questionDao, questionOptionDao, testQuestionDao, hintDao, trickDao, solutionStepDao, subjectDao, topicDao, subTopicDao);
  }
}
