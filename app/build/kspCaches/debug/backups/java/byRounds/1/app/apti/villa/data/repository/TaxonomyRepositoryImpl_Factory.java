package app.apti.villa.data.repository;

import app.apti.villa.data.local.dao.ExamDao;
import app.apti.villa.data.local.dao.SubTopicDao;
import app.apti.villa.data.local.dao.SubjectDao;
import app.apti.villa.data.local.dao.TopicDao;
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
public final class TaxonomyRepositoryImpl_Factory implements Factory<TaxonomyRepositoryImpl> {
  private final Provider<ExamDao> examDaoProvider;

  private final Provider<SubjectDao> subjectDaoProvider;

  private final Provider<TopicDao> topicDaoProvider;

  private final Provider<SubTopicDao> subTopicDaoProvider;

  public TaxonomyRepositoryImpl_Factory(Provider<ExamDao> examDaoProvider,
      Provider<SubjectDao> subjectDaoProvider, Provider<TopicDao> topicDaoProvider,
      Provider<SubTopicDao> subTopicDaoProvider) {
    this.examDaoProvider = examDaoProvider;
    this.subjectDaoProvider = subjectDaoProvider;
    this.topicDaoProvider = topicDaoProvider;
    this.subTopicDaoProvider = subTopicDaoProvider;
  }

  @Override
  public TaxonomyRepositoryImpl get() {
    return newInstance(examDaoProvider.get(), subjectDaoProvider.get(), topicDaoProvider.get(), subTopicDaoProvider.get());
  }

  public static TaxonomyRepositoryImpl_Factory create(
      javax.inject.Provider<ExamDao> examDaoProvider,
      javax.inject.Provider<SubjectDao> subjectDaoProvider,
      javax.inject.Provider<TopicDao> topicDaoProvider,
      javax.inject.Provider<SubTopicDao> subTopicDaoProvider) {
    return new TaxonomyRepositoryImpl_Factory(Providers.asDaggerProvider(examDaoProvider), Providers.asDaggerProvider(subjectDaoProvider), Providers.asDaggerProvider(topicDaoProvider), Providers.asDaggerProvider(subTopicDaoProvider));
  }

  public static TaxonomyRepositoryImpl_Factory create(Provider<ExamDao> examDaoProvider,
      Provider<SubjectDao> subjectDaoProvider, Provider<TopicDao> topicDaoProvider,
      Provider<SubTopicDao> subTopicDaoProvider) {
    return new TaxonomyRepositoryImpl_Factory(examDaoProvider, subjectDaoProvider, topicDaoProvider, subTopicDaoProvider);
  }

  public static TaxonomyRepositoryImpl newInstance(ExamDao examDao, SubjectDao subjectDao,
      TopicDao topicDao, SubTopicDao subTopicDao) {
    return new TaxonomyRepositoryImpl(examDao, subjectDao, topicDao, subTopicDao);
  }
}
