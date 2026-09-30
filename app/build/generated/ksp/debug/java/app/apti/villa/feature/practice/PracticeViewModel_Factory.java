package app.apti.villa.feature.practice;

import app.apti.villa.domain.repository.PracticeRepository;
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
public final class PracticeViewModel_Factory implements Factory<PracticeViewModel> {
  private final Provider<PracticeRepository> practiceRepositoryProvider;

  public PracticeViewModel_Factory(Provider<PracticeRepository> practiceRepositoryProvider) {
    this.practiceRepositoryProvider = practiceRepositoryProvider;
  }

  @Override
  public PracticeViewModel get() {
    return newInstance(practiceRepositoryProvider.get());
  }

  public static PracticeViewModel_Factory create(
      javax.inject.Provider<PracticeRepository> practiceRepositoryProvider) {
    return new PracticeViewModel_Factory(Providers.asDaggerProvider(practiceRepositoryProvider));
  }

  public static PracticeViewModel_Factory create(
      Provider<PracticeRepository> practiceRepositoryProvider) {
    return new PracticeViewModel_Factory(practiceRepositoryProvider);
  }

  public static PracticeViewModel newInstance(PracticeRepository practiceRepository) {
    return new PracticeViewModel(practiceRepository);
  }
}
