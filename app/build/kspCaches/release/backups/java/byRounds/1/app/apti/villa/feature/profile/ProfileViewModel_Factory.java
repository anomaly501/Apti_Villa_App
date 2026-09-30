package app.apti.villa.feature.profile;

import app.apti.villa.domain.repository.PreferencesRepository;
import app.apti.villa.domain.repository.TaxonomyRepository;
import app.apti.villa.domain.repository.UserProfileRepository;
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
public final class ProfileViewModel_Factory implements Factory<ProfileViewModel> {
  private final Provider<UserProfileRepository> userProfileRepositoryProvider;

  private final Provider<TaxonomyRepository> taxonomyRepositoryProvider;

  private final Provider<PreferencesRepository> preferencesRepositoryProvider;

  public ProfileViewModel_Factory(Provider<UserProfileRepository> userProfileRepositoryProvider,
      Provider<TaxonomyRepository> taxonomyRepositoryProvider,
      Provider<PreferencesRepository> preferencesRepositoryProvider) {
    this.userProfileRepositoryProvider = userProfileRepositoryProvider;
    this.taxonomyRepositoryProvider = taxonomyRepositoryProvider;
    this.preferencesRepositoryProvider = preferencesRepositoryProvider;
  }

  @Override
  public ProfileViewModel get() {
    return newInstance(userProfileRepositoryProvider.get(), taxonomyRepositoryProvider.get(), preferencesRepositoryProvider.get());
  }

  public static ProfileViewModel_Factory create(
      javax.inject.Provider<UserProfileRepository> userProfileRepositoryProvider,
      javax.inject.Provider<TaxonomyRepository> taxonomyRepositoryProvider,
      javax.inject.Provider<PreferencesRepository> preferencesRepositoryProvider) {
    return new ProfileViewModel_Factory(Providers.asDaggerProvider(userProfileRepositoryProvider), Providers.asDaggerProvider(taxonomyRepositoryProvider), Providers.asDaggerProvider(preferencesRepositoryProvider));
  }

  public static ProfileViewModel_Factory create(
      Provider<UserProfileRepository> userProfileRepositoryProvider,
      Provider<TaxonomyRepository> taxonomyRepositoryProvider,
      Provider<PreferencesRepository> preferencesRepositoryProvider) {
    return new ProfileViewModel_Factory(userProfileRepositoryProvider, taxonomyRepositoryProvider, preferencesRepositoryProvider);
  }

  public static ProfileViewModel newInstance(UserProfileRepository userProfileRepository,
      TaxonomyRepository taxonomyRepository, PreferencesRepository preferencesRepository) {
    return new ProfileViewModel(userProfileRepository, taxonomyRepository, preferencesRepository);
  }
}
