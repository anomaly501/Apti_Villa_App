package app.apti.villa.feature.ai;

import app.apti.villa.ai.config.AiConfigRepository;
import app.apti.villa.ai.provider.AiProviderRegistry;
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
public final class AiSettingsViewModel_Factory implements Factory<AiSettingsViewModel> {
  private final Provider<AiConfigRepository> aiConfigRepositoryProvider;

  private final Provider<AiProviderRegistry> aiProviderRegistryProvider;

  private final Provider<AiConversationRepository> aiConversationRepositoryProvider;

  public AiSettingsViewModel_Factory(Provider<AiConfigRepository> aiConfigRepositoryProvider,
      Provider<AiProviderRegistry> aiProviderRegistryProvider,
      Provider<AiConversationRepository> aiConversationRepositoryProvider) {
    this.aiConfigRepositoryProvider = aiConfigRepositoryProvider;
    this.aiProviderRegistryProvider = aiProviderRegistryProvider;
    this.aiConversationRepositoryProvider = aiConversationRepositoryProvider;
  }

  @Override
  public AiSettingsViewModel get() {
    return newInstance(aiConfigRepositoryProvider.get(), aiProviderRegistryProvider.get(), aiConversationRepositoryProvider.get());
  }

  public static AiSettingsViewModel_Factory create(
      javax.inject.Provider<AiConfigRepository> aiConfigRepositoryProvider,
      javax.inject.Provider<AiProviderRegistry> aiProviderRegistryProvider,
      javax.inject.Provider<AiConversationRepository> aiConversationRepositoryProvider) {
    return new AiSettingsViewModel_Factory(Providers.asDaggerProvider(aiConfigRepositoryProvider), Providers.asDaggerProvider(aiProviderRegistryProvider), Providers.asDaggerProvider(aiConversationRepositoryProvider));
  }

  public static AiSettingsViewModel_Factory create(
      Provider<AiConfigRepository> aiConfigRepositoryProvider,
      Provider<AiProviderRegistry> aiProviderRegistryProvider,
      Provider<AiConversationRepository> aiConversationRepositoryProvider) {
    return new AiSettingsViewModel_Factory(aiConfigRepositoryProvider, aiProviderRegistryProvider, aiConversationRepositoryProvider);
  }

  public static AiSettingsViewModel newInstance(AiConfigRepository aiConfigRepository,
      AiProviderRegistry aiProviderRegistry, AiConversationRepository aiConversationRepository) {
    return new AiSettingsViewModel(aiConfigRepository, aiProviderRegistry, aiConversationRepository);
  }
}
