package app.apti.villa.data.repository;

import app.apti.villa.data.prefs.UserPreferencesDataStore;
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
public final class PreferencesRepositoryImpl_Factory implements Factory<PreferencesRepositoryImpl> {
  private final Provider<UserPreferencesDataStore> dataStoreProvider;

  public PreferencesRepositoryImpl_Factory(Provider<UserPreferencesDataStore> dataStoreProvider) {
    this.dataStoreProvider = dataStoreProvider;
  }

  @Override
  public PreferencesRepositoryImpl get() {
    return newInstance(dataStoreProvider.get());
  }

  public static PreferencesRepositoryImpl_Factory create(
      javax.inject.Provider<UserPreferencesDataStore> dataStoreProvider) {
    return new PreferencesRepositoryImpl_Factory(Providers.asDaggerProvider(dataStoreProvider));
  }

  public static PreferencesRepositoryImpl_Factory create(
      Provider<UserPreferencesDataStore> dataStoreProvider) {
    return new PreferencesRepositoryImpl_Factory(dataStoreProvider);
  }

  public static PreferencesRepositoryImpl newInstance(UserPreferencesDataStore dataStore) {
    return new PreferencesRepositoryImpl(dataStore);
  }
}
