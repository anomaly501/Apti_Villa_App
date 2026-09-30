package app.apti.villa.ai.provider;

import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.Provider;
import dagger.internal.Providers;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;
import okhttp3.OkHttpClient;

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
public final class AnthropicProvider_Factory implements Factory<AnthropicProvider> {
  private final Provider<OkHttpClient> okHttpClientProvider;

  public AnthropicProvider_Factory(Provider<OkHttpClient> okHttpClientProvider) {
    this.okHttpClientProvider = okHttpClientProvider;
  }

  @Override
  public AnthropicProvider get() {
    return newInstance(okHttpClientProvider.get());
  }

  public static AnthropicProvider_Factory create(
      javax.inject.Provider<OkHttpClient> okHttpClientProvider) {
    return new AnthropicProvider_Factory(Providers.asDaggerProvider(okHttpClientProvider));
  }

  public static AnthropicProvider_Factory create(Provider<OkHttpClient> okHttpClientProvider) {
    return new AnthropicProvider_Factory(okHttpClientProvider);
  }

  public static AnthropicProvider newInstance(OkHttpClient okHttpClient) {
    return new AnthropicProvider(okHttpClient);
  }
}
