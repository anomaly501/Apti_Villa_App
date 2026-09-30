package app.apti.villa.feature.settings;

import app.apti.villa.domain.repository.PreferencesRepository;
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
public final class SettingsViewModel_Factory implements Factory<SettingsViewModel> {
  private final Provider<PreferencesRepository> preferencesRepositoryProvider;

  public SettingsViewModel_Factory(Provider<PreferencesRepository> preferencesRepositoryProvider) {
    this.preferencesRepositoryProvider = preferencesRepositoryProvider;
  }

  @Override
  public SettingsViewModel get() {
    return newInstance(preferencesRepositoryProvider.get());
  }

  public static SettingsViewModel_Factory create(
      javax.inject.Provider<PreferencesRepository> preferencesRepositoryProvider) {
    return new SettingsViewModel_Factory(Providers.asDaggerProvider(preferencesRepositoryProvider));
  }

  public static SettingsViewModel_Factory create(
      Provider<PreferencesRepository> preferencesRepositoryProvider) {
    return new SettingsViewModel_Factory(preferencesRepositoryProvider);
  }

  public static SettingsViewModel newInstance(PreferencesRepository preferencesRepository) {
    return new SettingsViewModel(preferencesRepository);
  }
}
