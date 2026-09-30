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
public final class OpenAiCompatibleProvider_Factory implements Factory<OpenAiCompatibleProvider> {
  private final Provider<OkHttpClient> okHttpClientProvider;

  public OpenAiCompatibleProvider_Factory(Provider<OkHttpClient> okHttpClientProvider) {
    this.okHttpClientProvider = okHttpClientProvider;
  }

  @Override
  public OpenAiCompatibleProvider get() {
    return newInstance(okHttpClientProvider.get());
  }

  public static OpenAiCompatibleProvider_Factory create(
      javax.inject.Provider<OkHttpClient> okHttpClientProvider) {
    return new OpenAiCompatibleProvider_Factory(Providers.asDaggerProvider(okHttpClientProvider));
  }

  public static OpenAiCompatibleProvider_Factory create(
      Provider<OkHttpClient> okHttpClientProvider) {
    return new OpenAiCompatibleProvider_Factory(okHttpClientProvider);
  }

  public static OpenAiCompatibleProvider newInstance(OkHttpClient okHttpClient) {
    return new OpenAiCompatibleProvider(okHttpClient);
  }
}
