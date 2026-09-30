package app.apti.villa.feature.test;

import androidx.lifecycle.SavedStateHandle;
import app.apti.villa.domain.repository.TestRepository;
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
public final class TestReviewViewModel_Factory implements Factory<TestReviewViewModel> {
  private final Provider<TestRepository> testRepositoryProvider;

  private final Provider<SavedStateHandle> savedStateHandleProvider;

  public TestReviewViewModel_Factory(Provider<TestRepository> testRepositoryProvider,
      Provider<SavedStateHandle> savedStateHandleProvider) {
    this.testRepositoryProvider = testRepositoryProvider;
    this.savedStateHandleProvider = savedStateHandleProvider;
  }

  @Override
  public TestReviewViewModel get() {
    return newInstance(testRepositoryProvider.get(), savedStateHandleProvider.get());
  }

  public static TestReviewViewModel_Factory create(
      javax.inject.Provider<TestRepository> testRepositoryProvider,
      javax.inject.Provider<SavedStateHandle> savedStateHandleProvider) {
    return new TestReviewViewModel_Factory(Providers.asDaggerProvider(testRepositoryProvider), Providers.asDaggerProvider(savedStateHandleProvider));
  }

  public static TestReviewViewModel_Factory create(Provider<TestRepository> testRepositoryProvider,
      Provider<SavedStateHandle> savedStateHandleProvider) {
    return new TestReviewViewModel_Factory(testRepositoryProvider, savedStateHandleProvider);
  }

  public static TestReviewViewModel newInstance(TestRepository testRepository,
      SavedStateHandle savedStateHandle) {
    return new TestReviewViewModel(testRepository, savedStateHandle);
  }
}
