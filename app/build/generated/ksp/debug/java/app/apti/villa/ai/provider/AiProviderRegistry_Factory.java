package app.apti.villa.ai.provider;

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
public final class AiProviderRegistry_Factory implements Factory<AiProviderRegistry> {
  private final Provider<OpenAiCompatibleProvider> openAiProvider;

  private final Provider<GeminiProvider> geminiProvider;

  private final Provider<AnthropicProvider> anthropicProvider;

  public AiProviderRegistry_Factory(Provider<OpenAiCompatibleProvider> openAiProvider,
      Provider<GeminiProvider> geminiProvider, Provider<AnthropicProvider> anthropicProvider) {
    this.openAiProvider = openAiProvider;
    this.geminiProvider = geminiProvider;
    this.anthropicProvider = anthropicProvider;
  }

  @Override
  public AiProviderRegistry get() {
    return newInstance(openAiProvider.get(), geminiProvider.get(), anthropicProvider.get());
  }

  public static AiProviderRegistry_Factory create(
      javax.inject.Provider<OpenAiCompatibleProvider> openAiProvider,
      javax.inject.Provider<GeminiProvider> geminiProvider,
      javax.inject.Provider<AnthropicProvider> anthropicProvider) {
    return new AiProviderRegistry_Factory(Providers.asDaggerProvider(openAiProvider), Providers.asDaggerProvider(geminiProvider), Providers.asDaggerProvider(anthropicProvider));
  }

  public static AiProviderRegistry_Factory create(Provider<OpenAiCompatibleProvider> openAiProvider,
      Provider<GeminiProvider> geminiProvider, Provider<AnthropicProvider> anthropicProvider) {
    return new AiProviderRegistry_Factory(openAiProvider, geminiProvider, anthropicProvider);
  }

  public static AiProviderRegistry newInstance(OpenAiCompatibleProvider openAiProvider,
      GeminiProvider geminiProvider, AnthropicProvider anthropicProvider) {
    return new AiProviderRegistry(openAiProvider, geminiProvider, anthropicProvider);
  }
}
