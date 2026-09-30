package app.apti.villa.feature.home;

import app.apti.villa.domain.repository.AnalyticsRepository;
import app.apti.villa.domain.repository.PracticeRepository;
import app.apti.villa.domain.repository.ReviewRepository;
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
public final class HomeViewModel_Factory implements Factory<HomeViewModel> {
  private final Provider<UserProfileRepository> userProfileRepositoryProvider;

  private final Provider<AnalyticsRepository> analyticsRepositoryProvider;

  private final Provider<PracticeRepository> practiceRepositoryProvider;

  private final Provider<ReviewRepository> reviewRepositoryProvider;

  public HomeViewModel_Factory(Provider<UserProfileRepository> userProfileRepositoryProvider,
      Provider<AnalyticsRepository> analyticsRepositoryProvider,
      Provider<PracticeRepository> practiceRepositoryProvider,
      Provider<ReviewRepository> reviewRepositoryProvider) {
    this.userProfileRepositoryProvider = userProfileRepositoryProvider;
    this.analyticsRepositoryProvider = analyticsRepositoryProvider;
    this.practiceRepositoryProvider = practiceRepositoryProvider;
    this.reviewRepositoryProvider = reviewRepositoryProvider;
  }

  @Override
  public HomeViewModel get() {
    return newInstance(userProfileRepositoryProvider.get(), analyticsRepositoryProvider.get(), practiceRepositoryProvider.get(), reviewRepositoryProvider.get());
  }

  public static HomeViewModel_Factory create(
      javax.inject.Provider<UserProfileRepository> userProfileRepositoryProvider,
      javax.inject.Provider<AnalyticsRepository> analyticsRepositoryProvider,
      javax.inject.Provider<PracticeRepository> practiceRepositoryProvider,
      javax.inject.Provider<ReviewRepository> reviewRepositoryProvider) {
    return new HomeViewModel_Factory(Providers.asDaggerProvider(userProfileRepositoryProvider), Providers.asDaggerProvider(analyticsRepositoryProvider), Providers.asDaggerProvider(practiceRepositoryProvider), Providers.asDaggerProvider(reviewRepositoryProvider));
  }

  public static HomeViewModel_Factory create(
      Provider<UserProfileRepository> userProfileRepositoryProvider,
      Provider<AnalyticsRepository> analyticsRepositoryProvider,
      Provider<PracticeRepository> practiceRepositoryProvider,
      Provider<ReviewRepository> reviewRepositoryProvider) {
    return new HomeViewModel_Factory(userProfileRepositoryProvider, analyticsRepositoryProvider, practiceRepositoryProvider, reviewRepositoryProvider);
  }

  public static HomeViewModel newInstance(UserProfileRepository userProfileRepository,
      AnalyticsRepository analyticsRepository, PracticeRepository practiceRepository,
      ReviewRepository reviewRepository) {
    return new HomeViewModel(userProfileRepository, analyticsRepository, practiceRepository, reviewRepository);
  }
}
