package app.apti.villa.feature.onboarding;

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
public final class OnboardingViewModel_Factory implements Factory<OnboardingViewModel> {
  private final Provider<TaxonomyRepository> taxonomyRepositoryProvider;

  private final Provider<UserProfileRepository> userProfileRepositoryProvider;

  private final Provider<PreferencesRepository> preferencesRepositoryProvider;

  public OnboardingViewModel_Factory(Provider<TaxonomyRepository> taxonomyRepositoryProvider,
      Provider<UserProfileRepository> userProfileRepositoryProvider,
      Provider<PreferencesRepository> preferencesRepositoryProvider) {
    this.taxonomyRepositoryProvider = taxonomyRepositoryProvider;
    this.userProfileRepositoryProvider = userProfileRepositoryProvider;
    this.preferencesRepositoryProvider = preferencesRepositoryProvider;
  }

  @Override
  public OnboardingViewModel get() {
    return newInstance(taxonomyRepositoryProvider.get(), userProfileRepositoryProvider.get(), preferencesRepositoryProvider.get());
  }

  public static OnboardingViewModel_Factory create(
      javax.inject.Provider<TaxonomyRepository> taxonomyRepositoryProvider,
      javax.inject.Provider<UserProfileRepository> userProfileRepositoryProvider,
      javax.inject.Provider<PreferencesRepository> preferencesRepositoryProvider) {
    return new OnboardingViewModel_Factory(Providers.asDaggerProvider(taxonomyRepositoryProvider), Providers.asDaggerProvider(userProfileRepositoryProvider), Providers.asDaggerProvider(preferencesRepositoryProvider));
  }

  public static OnboardingViewModel_Factory create(
      Provider<TaxonomyRepository> taxonomyRepositoryProvider,
      Provider<UserProfileRepository> userProfileRepositoryProvider,
      Provider<PreferencesRepository> preferencesRepositoryProvider) {
    return new OnboardingViewModel_Factory(taxonomyRepositoryProvider, userProfileRepositoryProvider, preferencesRepositoryProvider);
  }

  public static OnboardingViewModel newInstance(TaxonomyRepository taxonomyRepository,
      UserProfileRepository userProfileRepository, PreferencesRepository preferencesRepository) {
    return new OnboardingViewModel(taxonomyRepository, userProfileRepository, preferencesRepository);
  }
}
