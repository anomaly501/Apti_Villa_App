package app.apti.villa.feature.settings;

import app.apti.villa.domain.repository.BackupRepository;
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
public final class DataManagementViewModel_Factory implements Factory<DataManagementViewModel> {
  private final Provider<BackupRepository> backupRepositoryProvider;

  public DataManagementViewModel_Factory(Provider<BackupRepository> backupRepositoryProvider) {
    this.backupRepositoryProvider = backupRepositoryProvider;
  }

  @Override
  public DataManagementViewModel get() {
    return newInstance(backupRepositoryProvider.get());
  }

  public static DataManagementViewModel_Factory create(
      javax.inject.Provider<BackupRepository> backupRepositoryProvider) {
    return new DataManagementViewModel_Factory(Providers.asDaggerProvider(backupRepositoryProvider));
  }

  public static DataManagementViewModel_Factory create(
      Provider<BackupRepository> backupRepositoryProvider) {
    return new DataManagementViewModel_Factory(backupRepositoryProvider);
  }

  public static DataManagementViewModel newInstance(BackupRepository backupRepository) {
    return new DataManagementViewModel(backupRepository);
  }
}
