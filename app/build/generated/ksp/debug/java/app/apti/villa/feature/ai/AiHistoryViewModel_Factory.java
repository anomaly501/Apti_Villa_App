package app.apti.villa.feature.ai;

import app.apti.villa.domain.repository.AiConversationRepository;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.Provider;
import dagger.internal.Providers;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;

@ScopeMetadata
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
public final class AiHistoryViewModel_Factory implements Factory<AiHistoryViewModel> {
  private final Provider<AiConversationRepository> aiConversationRepositoryProvider;

  public AiHistoryViewModel_Factory(
      Provider<AiConversationRepository> aiConversationRepositoryProvider) {
    this.aiConversationRepositoryProvider = aiConversationRepositoryProvider;
  }

  @Override
  public AiHistoryViewModel get() {
    return newInstance(aiConversationRepositoryProvider.get());
  }

  public static AiHistoryViewModel_Factory create(
      javax.inject.Provider<AiConversationRepository> aiConversationRepositoryProvider) {
    return new AiHistoryViewModel_Factory(Providers.asDaggerProvider(aiConversationRepositoryProvider));
  }

  public static AiHistoryViewModel_Factory create(
      Provider<AiConversationRepository> aiConversationRepositoryProvider) {
    return new AiHistoryViewModel_Factory(aiConversationRepositoryProvider);
  }

  public static AiHistoryViewModel newInstance(AiConversationRepository aiConversationRepository) {
    return new AiHistoryViewModel(aiConversationRepository);
  }
}
