package app.apti.villa.data.repository;

import app.apti.villa.data.local.dao.SearchDao;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.Provider;
import dagger.internal.Providers;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;
import kotlinx.coroutines.CoroutineDispatcher;

@ScopeMetadata("javax.inject.Singleton")
@QualifierMetadata("app.apti.villa.core.common.IoDispatcher")
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
public final class SearchRepositoryImpl_Factory implements Factory<SearchRepositoryImpl> {
  private final Provider<SearchDao> searchDaoProvider;

  private final Provider<CoroutineDispatcher> ioDispatcherProvider;

  public SearchRepositoryImpl_Factory(Provider<SearchDao> searchDaoProvider,
      Provider<CoroutineDispatcher> ioDispatcherProvider) {
    this.searchDaoProvider = searchDaoProvider;
    this.ioDispatcherProvider = ioDispatcherProvider;
  }

  @Override
  public SearchRepositoryImpl get() {
    return newInstance(searchDaoProvider.get(), ioDispatcherProvider.get());
  }

  public static SearchRepositoryImpl_Factory create(
      javax.inject.Provider<SearchDao> searchDaoProvider,
      javax.inject.Provider<CoroutineDispatcher> ioDispatcherProvider) {
    return new SearchRepositoryImpl_Factory(Providers.asDaggerProvider(searchDaoProvider), Providers.asDaggerProvider(ioDispatcherProvider));
  }

  public static SearchRepositoryImpl_Factory create(Provider<SearchDao> searchDaoProvider,
      Provider<CoroutineDispatcher> ioDispatcherProvider) {
    return new SearchRepositoryImpl_Factory(searchDaoProvider, ioDispatcherProvider);
  }

  public static SearchRepositoryImpl newInstance(SearchDao searchDao,
      CoroutineDispatcher ioDispatcher) {
    return new SearchRepositoryImpl(searchDao, ioDispatcher);
  }
}
