package app.apti.villa;

import app.apti.villa.domain.repository.PreferencesRepository;
import dagger.MembersInjector;
import dagger.internal.DaggerGenerated;
import dagger.internal.InjectedFieldSignature;
import dagger.internal.Provider;
import dagger.internal.Providers;
import dagger.internal.QualifierMetadata;
import javax.annotation.processing.Generated;

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
public final class MainActivity_MembersInjector implements MembersInjector<MainActivity> {
  private final Provider<PreferencesRepository> preferencesRepositoryProvider;

  public MainActivity_MembersInjector(
      Provider<PreferencesRepository> preferencesRepositoryProvider) {
    this.preferencesRepositoryProvider = preferencesRepositoryProvider;
  }

  public static MembersInjector<MainActivity> create(
      Provider<PreferencesRepository> preferencesRepositoryProvider) {
    return new MainActivity_MembersInjector(preferencesRepositoryProvider);
  }

  public static MembersInjector<MainActivity> create(
      javax.inject.Provider<PreferencesRepository> preferencesRepositoryProvider) {
    return new MainActivity_MembersInjector(Providers.asDaggerProvider(preferencesRepositoryProvider));
  }

  @Override
  public void injectMembers(MainActivity instance) {
    injectPreferencesRepository(instance, preferencesRepositoryProvider.get());
  }

  @InjectedFieldSignature("app.apti.villa.MainActivity.preferencesRepository")
  public static void injectPreferencesRepository(MainActivity instance,
      PreferencesRepository preferencesRepository) {
    instance.preferencesRepository = preferencesRepository;
  }
}
