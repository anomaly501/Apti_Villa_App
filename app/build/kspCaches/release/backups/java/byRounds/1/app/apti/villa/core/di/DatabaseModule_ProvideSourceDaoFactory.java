package app.apti.villa.core.di;

import app.apti.villa.data.local.dao.SourceDao;
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
public final class DatabaseModule_ProvideSourceDaoFactory implements Factory<SourceDao> {
  private final Provider<AppDatabase> dbProvider;

  public DatabaseModule_ProvideSourceDaoFactory(Provider<AppDatabase> dbProvider) {
    this.dbProvider = dbProvider;
  }

  @Override
  public SourceDao get() {
    return provideSourceDao(dbProvider.get());
  }

  public static DatabaseModule_ProvideSourceDaoFactory create(
      javax.inject.Provider<AppDatabase> dbProvider) {
    return new DatabaseModule_ProvideSourceDaoFactory(Providers.asDaggerProvider(dbProvider));
  }

  public static DatabaseModule_ProvideSourceDaoFactory create(Provider<AppDatabase> dbProvider) {
    return new DatabaseModule_ProvideSourceDaoFactory(dbProvider);
  }

  public static SourceDao provideSourceDao(AppDatabase db) {
    return Preconditions.checkNotNullFromProvides(DatabaseModule.INSTANCE.provideSourceDao(db));
  }
}
