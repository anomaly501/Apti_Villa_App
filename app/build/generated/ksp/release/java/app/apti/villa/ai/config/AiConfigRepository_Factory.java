package app.apti.villa.ai.config;

import android.content.Context;
import app.apti.villa.ai.security.SecureKeyStore;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.Provider;
import dagger.internal.Providers;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;

@ScopeMetadata("javax.inject.Singleton")
@QualifierMetadata("dagger.hilt.android.qualifiers.ApplicationContext")
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
public final class AiConfigRepository_Factory implements Factory<AiConfigRepository> {
  private final Provider<Context> contextProvider;

  private final Provider<SecureKeyStore> secureKeyStoreProvider;

  public AiConfigRepository_Factory(Provider<Context> contextProvider,
      Provider<SecureKeyStore> secureKeyStoreProvider) {
    this.contextProvider = contextProvider;
    this.secureKeyStoreProvider = secureKeyStoreProvider;
  }

  @Override
  public AiConfigRepository get() {
    return newInstance(contextProvider.get(), secureKeyStoreProvider.get());
  }

  public static AiConfigRepository_Factory create(javax.inject.Provider<Context> contextProvider,
      javax.inject.Provider<SecureKeyStore> secureKeyStoreProvider) {
    return new AiConfigRepository_Factory(Providers.asDaggerProvider(contextProvider), Providers.asDaggerProvider(secureKeyStoreProvider));
  }

  public static AiConfigRepository_Factory create(Provider<Context> contextProvider,
      Provider<SecureKeyStore> secureKeyStoreProvider) {
    return new AiConfigRepository_Factory(contextProvider, secureKeyStoreProvider);
  }

  public static AiConfigRepository newInstance(Context context, SecureKeyStore secureKeyStore) {
    return new AiConfigRepository(context, secureKeyStore);
  }
}
