package app.apti.villa.data.repository;

import app.apti.villa.data.local.dao.AiConversationDao;
import app.apti.villa.data.local.dao.QuestionDao;
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
public final class AiConversationRepositoryImpl_Factory implements Factory<AiConversationRepositoryImpl> {
  private final Provider<AiConversationDao> aiConversationDaoProvider;

  private final Provider<QuestionDao> questionDaoProvider;

  private final Provider<TopicDao> topicDaoProvider;

  private final Provider<CoroutineDispatcher> ioDispatcherProvider;

  public AiConversationRepositoryImpl_Factory(Provider<AiConversationDao> aiConversationDaoProvider,
      Provider<QuestionDao> questionDaoProvider, Provider<TopicDao> topicDaoProvider,
      Provider<CoroutineDispatcher> ioDispatcherProvider) {
    this.aiConversationDaoProvider = aiConversationDaoProvider;
    this.questionDaoProvider = questionDaoProvider;
    this.topicDaoProvider = topicDaoProvider;
    this.ioDispatcherProvider = ioDispatcherProvider;
  }

  @Override
  public AiConversationRepositoryImpl get() {
    return newInstance(aiConversationDaoProvider.get(), questionDaoProvider.get(), topicDaoProvider.get(), ioDispatcherProvider.get());
  }

  public static AiConversationRepositoryImpl_Factory create(
      javax.inject.Provider<AiConversationDao> aiConversationDaoProvider,
      javax.inject.Provider<QuestionDao> questionDaoProvider,
      javax.inject.Provider<TopicDao> topicDaoProvider,
      javax.inject.Provider<CoroutineDispatcher> ioDispatcherProvider) {
    return new AiConversationRepositoryImpl_Factory(Providers.asDaggerProvider(aiConversationDaoProvider), Providers.asDaggerProvider(questionDaoProvider), Providers.asDaggerProvider(topicDaoProvider), Providers.asDaggerProvider(ioDispatcherProvider));
  }

  public static AiConversationRepositoryImpl_Factory create(
      Provider<AiConversationDao> aiConversationDaoProvider,
      Provider<QuestionDao> questionDaoProvider, Provider<TopicDao> topicDaoProvider,
      Provider<CoroutineDispatcher> ioDispatcherProvider) {
    return new AiConversationRepositoryImpl_Factory(aiConversationDaoProvider, questionDaoProvider, topicDaoProvider, ioDispatcherProvider);
  }

  public static AiConversationRepositoryImpl newInstance(AiConversationDao aiConversationDao,
      QuestionDao questionDao, TopicDao topicDao, CoroutineDispatcher ioDispatcher) {
    return new AiConversationRepositoryImpl(aiConversationDao, questionDao, topicDao, ioDispatcher);
  }
}
