package app.apti.villa.feature.ai;

import androidx.lifecycle.SavedStateHandle;
import app.apti.villa.ai.config.AiConfigRepository;
import app.apti.villa.ai.context.AiContextBuilder;
import app.apti.villa.ai.provider.AiProviderRegistry;
import app.apti.villa.domain.repository.AiConversationRepository;
import app.apti.villa.domain.repository.PracticeRepository;
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
public final class AiChatViewModel_Factory implements Factory<AiChatViewModel> {
  private final Provider<SavedStateHandle> savedStateHandleProvider;

  private final Provider<PracticeRepository> practiceRepositoryProvider;

  private final Provider<AiConfigRepository> aiConfigRepositoryProvider;

  private final Provider<AiProviderRegistry> aiProviderRegistryProvider;

  private final Provider<AiContextBuilder> aiContextBuilderProvider;

  private final Provider<AiConversationRepository> aiConversationRepositoryProvider;

  public AiChatViewModel_Factory(Provider<SavedStateHandle> savedStateHandleProvider,
      Provider<PracticeRepository> practiceRepositoryProvider,
      Provider<AiConfigRepository> aiConfigRepositoryProvider,
      Provider<AiProviderRegistry> aiProviderRegistryProvider,
      Provider<AiContextBuilder> aiContextBuilderProvider,
      Provider<AiConversationRepository> aiConversationRepositoryProvider) {
    this.savedStateHandleProvider = savedStateHandleProvider;
    this.practiceRepositoryProvider = practiceRepositoryProvider;
    this.aiConfigRepositoryProvider = aiConfigRepositoryProvider;
    this.aiProviderRegistryProvider = aiProviderRegistryProvider;
    this.aiContextBuilderProvider = aiContextBuilderProvider;
    this.aiConversationRepositoryProvider = aiConversationRepositoryProvider;
  }

  @Override
  public AiChatViewModel get() {
    return newInstance(savedStateHandleProvider.get(), practiceRepositoryProvider.get(), aiConfigRepositoryProvider.get(), aiProviderRegistryProvider.get(), aiContextBuilderProvider.get(), aiConversationRepositoryProvider.get());
  }

  public static AiChatViewModel_Factory create(
      javax.inject.Provider<SavedStateHandle> savedStateHandleProvider,
      javax.inject.Provider<PracticeRepository> practiceRepositoryProvider,
      javax.inject.Provider<AiConfigRepository> aiConfigRepositoryProvider,
      javax.inject.Provider<AiProviderRegistry> aiProviderRegistryProvider,
      javax.inject.Provider<AiContextBuilder> aiContextBuilderProvider,
      javax.inject.Provider<AiConversationRepository> aiConversationRepositoryProvider) {
    return new AiChatViewModel_Factory(Providers.asDaggerProvider(savedStateHandleProvider), Providers.asDaggerProvider(practiceRepositoryProvider), Providers.asDaggerProvider(aiConfigRepositoryProvider), Providers.asDaggerProvider(aiProviderRegistryProvider), Providers.asDaggerProvider(aiContextBuilderProvider), Providers.asDaggerProvider(aiConversationRepositoryProvider));
  }

  public static AiChatViewModel_Factory create(Provider<SavedStateHandle> savedStateHandleProvider,
      Provider<PracticeRepository> practiceRepositoryProvider,
      Provider<AiConfigRepository> aiConfigRepositoryProvider,
      Provider<AiProviderRegistry> aiProviderRegistryProvider,
      Provider<AiContextBuilder> aiContextBuilderProvider,
      Provider<AiConversationRepository> aiConversationRepositoryProvider) {
    return new AiChatViewModel_Factory(savedStateHandleProvider, practiceRepositoryProvider, aiConfigRepositoryProvider, aiProviderRegistryProvider, aiContextBuilderProvider, aiConversationRepositoryProvider);
  }

  public static AiChatViewModel newInstance(SavedStateHandle savedStateHandle,
      PracticeRepository practiceRepository, AiConfigRepository aiConfigRepository,
      AiProviderRegistry aiProviderRegistry, AiContextBuilder aiContextBuilder,
      AiConversationRepository aiConversationRepository) {
    return new AiChatViewModel(savedStateHandle, practiceRepository, aiConfigRepository, aiProviderRegistry, aiContextBuilder, aiConversationRepository);
  }
}
