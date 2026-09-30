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
public final class ExamViewModel_Factory implements Factory<ExamViewModel> {
  private final Provider<TestRepository> testRepositoryProvider;

  private final Provider<SavedStateHandle> savedStateHandleProvider;

  public ExamViewModel_Factory(Provider<TestRepository> testRepositoryProvider,
      Provider<SavedStateHandle> savedStateHandleProvider) {
    this.testRepositoryProvider = testRepositoryProvider;
    this.savedStateHandleProvider = savedStateHandleProvider;
  }

  @Override
  public ExamViewModel get() {
    return newInstance(testRepositoryProvider.get(), savedStateHandleProvider.get());
  }

  public static ExamViewModel_Factory create(
      javax.inject.Provider<TestRepository> testRepositoryProvider,
      javax.inject.Provider<SavedStateHandle> savedStateHandleProvider) {
    return new ExamViewModel_Factory(Providers.asDaggerProvider(testRepositoryProvider), Providers.asDaggerProvider(savedStateHandleProvider));
  }

  public static ExamViewModel_Factory create(Provider<TestRepository> testRepositoryProvider,
      Provider<SavedStateHandle> savedStateHandleProvider) {
    return new ExamViewModel_Factory(testRepositoryProvider, savedStateHandleProvider);
  }

  public static ExamViewModel newInstance(TestRepository testRepository,
      SavedStateHandle savedStateHandle) {
    return new ExamViewModel(testRepository, savedStateHandle);
  }
}
