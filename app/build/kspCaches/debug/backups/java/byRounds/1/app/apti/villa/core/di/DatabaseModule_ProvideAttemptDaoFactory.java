package app.apti.villa.core.di;

import app.apti.villa.data.local.dao.AttemptDao;
import app.apti.villa.data.local.db.AppDatabase;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
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
public final class DatabaseModule_ProvideAttemptDaoFactory implements Factory<AttemptDao> {
  private final Provider<AppDatabase> dbProvider;

  public DatabaseModule_ProvideAttemptDaoFactory(Provider<AppDatabase> dbProvider) {
    this.dbProvider = dbProvider;
  }

  @Override
  public AttemptDao get() {
    return provideAttemptDao(dbProvider.get());
  }

  public static DatabaseModule_ProvideAttemptDaoFactory create(
      javax.inject.Provider<AppDatabase> dbProvider) {
    return new DatabaseModule_ProvideAttemptDaoFactory(Providers.asDaggerProvider(dbProvider));
  }

  public static DatabaseModule_ProvideAttemptDaoFactory create(Provider<AppDatabase> dbProvider) {
    return new DatabaseModule_ProvideAttemptDaoFactory(dbProvider);
  }

  public static AttemptDao provideAttemptDao(AppDatabase db) {
    return Preconditions.checkNotNullFromProvides(DatabaseModule.INSTANCE.provideAttemptDao(db));
  }
}
