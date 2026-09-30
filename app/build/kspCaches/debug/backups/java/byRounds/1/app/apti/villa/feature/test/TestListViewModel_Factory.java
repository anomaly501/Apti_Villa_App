package app.apti.villa.feature.test;

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
public final class TestListViewModel_Factory implements Factory<TestListViewModel> {
  private final Provider<TestRepository> testRepositoryProvider;

  public TestListViewModel_Factory(Provider<TestRepository> testRepositoryProvider) {
    this.testRepositoryProvider = testRepositoryProvider;
  }

  @Override
  public TestListViewModel get() {
    return newInstance(testRepositoryProvider.get());
  }

  public static TestListViewModel_Factory create(
      javax.inject.Provider<TestRepository> testRepositoryProvider) {
    return new TestListViewModel_Factory(Providers.asDaggerProvider(testRepositoryProvider));
  }

  public static TestListViewModel_Factory create(Provider<TestRepository> testRepositoryProvider) {
    return new TestListViewModel_Factory(testRepositoryProvider);
  }

  public static TestListViewModel newInstance(TestRepository testRepository) {
    return new TestListViewModel(testRepository);
  }
}
