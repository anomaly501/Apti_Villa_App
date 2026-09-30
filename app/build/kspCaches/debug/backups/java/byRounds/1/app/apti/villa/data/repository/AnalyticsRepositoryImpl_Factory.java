package app.apti.villa.data.repository;

import app.apti.villa.data.local.dao.AnalyticsDao;
import app.apti.villa.data.local.dao.StudyDayDao;
import app.apti.villa.data.local.dao.SubjectDao;
import app.apti.villa.data.local.dao.TopicDao;
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
public final class AnalyticsRepositoryImpl_Factory implements Factory<AnalyticsRepositoryImpl> {
  private final Provider<AnalyticsDao> analyticsDaoProvider;

  private final Provider<StudyDayDao> studyDayDaoProvider;

  private final Provider<TopicDao> topicDaoProvider;

  private final Provider<SubjectDao> subjectDaoProvider;

  private final Provider<CoroutineDispatcher> ioDispatcherProvider;

  public AnalyticsRepositoryImpl_Factory(Provider<AnalyticsDao> analyticsDaoProvider,
      Provider<StudyDayDao> studyDayDaoProvider, Provider<TopicDao> topicDaoProvider,
      Provider<SubjectDao> subjectDaoProvider, Provider<CoroutineDispatcher> ioDispatcherProvider) {
    this.analyticsDaoProvider = analyticsDaoProvider;
    this.studyDayDaoProvider = studyDayDaoProvider;
    this.topicDaoProvider = topicDaoProvider;
    this.subjectDaoProvider = subjectDaoProvider;
    this.ioDispatcherProvider = ioDispatcherProvider;
  }

  @Override
  public AnalyticsRepositoryImpl get() {
    return newInstance(analyticsDaoProvider.get(), studyDayDaoProvider.get(), topicDaoProvider.get(), subjectDaoProvider.get(), ioDispatcherProvider.get());
  }

  public static AnalyticsRepositoryImpl_Factory create(
      javax.inject.Provider<AnalyticsDao> analyticsDaoProvider,
      javax.inject.Provider<StudyDayDao> studyDayDaoProvider,
      javax.inject.Provider<TopicDao> topicDaoProvider,
      javax.inject.Provider<SubjectDao> subjectDaoProvider,
      javax.inject.Provider<CoroutineDispatcher> ioDispatcherProvider) {
    return new AnalyticsRepositoryImpl_Factory(Providers.asDaggerProvider(analyticsDaoProvider), Providers.asDaggerProvider(studyDayDaoProvider), Providers.asDaggerProvider(topicDaoProvider), Providers.asDaggerProvider(subjectDaoProvider), Providers.asDaggerProvider(ioDispatcherProvider));
  }

  public static AnalyticsRepositoryImpl_Factory create(Provider<AnalyticsDao> analyticsDaoProvider,
      Provider<StudyDayDao> studyDayDaoProvider, Provider<TopicDao> topicDaoProvider,
      Provider<SubjectDao> subjectDaoProvider, Provider<CoroutineDispatcher> ioDispatcherProvider) {
    return new AnalyticsRepositoryImpl_Factory(analyticsDaoProvider, studyDayDaoProvider, topicDaoProvider, subjectDaoProvider, ioDispatcherProvider);
  }

  public static AnalyticsRepositoryImpl newInstance(AnalyticsDao analyticsDao,
      StudyDayDao studyDayDao, TopicDao topicDao, SubjectDao subjectDao,
      CoroutineDispatcher ioDispatcher) {
    return new AnalyticsRepositoryImpl(analyticsDao, studyDayDao, topicDao, subjectDao, ioDispatcher);
  }
}
