package app.apti.villa.feature.importer;

import app.apti.villa.domain.importer.ImportRepository;
import app.apti.villa.domain.repository.TaxonomyRepository;
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
public final class ImportViewModel_Factory implements Factory<ImportViewModel> {
  private final Provider<ImportRepository> importRepositoryProvider;

  private final Provider<TaxonomyRepository> taxonomyRepositoryProvider;

  public ImportViewModel_Factory(Provider<ImportRepository> importRepositoryProvider,
      Provider<TaxonomyRepository> taxonomyRepositoryProvider) {
    this.importRepositoryProvider = importRepositoryProvider;
    this.taxonomyRepositoryProvider = taxonomyRepositoryProvider;
  }

  @Override
  public ImportViewModel get() {
    return newInstance(importRepositoryProvider.get(), taxonomyRepositoryProvider.get());
  }

  public static ImportViewModel_Factory create(
      javax.inject.Provider<ImportRepository> importRepositoryProvider,
      javax.inject.Provider<TaxonomyRepository> taxonomyRepositoryProvider) {
    return new ImportViewModel_Factory(Providers.asDaggerProvider(importRepositoryProvider), Providers.asDaggerProvider(taxonomyRepositoryProvider));
  }

  public static ImportViewModel_Factory create(Provider<ImportRepository> importRepositoryProvider,
      Provider<TaxonomyRepository> taxonomyRepositoryProvider) {
    return new ImportViewModel_Factory(importRepositoryProvider, taxonomyRepositoryProvider);
  }

  public static ImportViewModel newInstance(ImportRepository importRepository,
      TaxonomyRepository taxonomyRepository) {
    return new ImportViewModel(importRepository, taxonomyRepository);
  }
}
