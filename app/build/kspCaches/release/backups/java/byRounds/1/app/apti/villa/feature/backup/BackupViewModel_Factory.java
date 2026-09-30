package app.apti.villa.feature.backup;

import android.content.Context;
import app.apti.villa.domain.repository.BackupRepository;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.Provider;
import dagger.internal.Providers;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;

@ScopeMetadata
@QualifierMetadata("dagger.hilt.android.qualifiers.ApplicationContext")
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
public final class BackupViewModel_Factory implements Factory<BackupViewModel> {
  private final Provider<BackupRepository> backupRepositoryProvider;

  private final Provider<Context> contextProvider;

  public BackupViewModel_Factory(Provider<BackupRepository> backupRepositoryProvider,
      Provider<Context> contextProvider) {
    this.backupRepositoryProvider = backupRepositoryProvider;
    this.contextProvider = contextProvider;
  }

  @Override
  public BackupViewModel get() {
    return newInstance(backupRepositoryProvider.get(), contextProvider.get());
  }

  public static BackupViewModel_Factory create(
      javax.inject.Provider<BackupRepository> backupRepositoryProvider,
      javax.inject.Provider<Context> contextProvider) {
    return new BackupViewModel_Factory(Providers.asDaggerProvider(backupRepositoryProvider), Providers.asDaggerProvider(contextProvider));
  }

  public static BackupViewModel_Factory create(Provider<BackupRepository> backupRepositoryProvider,
      Provider<Context> contextProvider) {
    return new BackupViewModel_Factory(backupRepositoryProvider, contextProvider);
  }

  public static BackupViewModel newInstance(BackupRepository backupRepository, Context context) {
    return new BackupViewModel(backupRepository, context);
  }
}
