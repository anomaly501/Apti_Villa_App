package app.apti.villa.core.di;

import app.apti.villa.data.local.dao.RevisionDao;
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
public final class DatabaseModule_ProvideRevisionDaoFactory implements Factory<RevisionDao> {
  private final Provider<AppDatabase> dbProvider;

  public DatabaseModule_ProvideRevisionDaoFactory(Provider<AppDatabase> dbProvider) {
    this.dbProvider = dbProvider;
  }

  @Override
  public RevisionDao get() {
    return provideRevisionDao(dbProvider.get());
  }

  public static DatabaseModule_ProvideRevisionDaoFactory create(
      javax.inject.Provider<AppDatabase> dbProvider) {
    return new DatabaseModule_ProvideRevisionDaoFactory(Providers.asDaggerProvider(dbProvider));
  }

  public static DatabaseModule_ProvideRevisionDaoFactory create(Provider<AppDatabase> dbProvider) {
    return new DatabaseModule_ProvideRevisionDaoFactory(dbProvider);
  }

  public static RevisionDao provideRevisionDao(AppDatabase db) {
    return Preconditions.checkNotNullFromProvides(DatabaseModule.INSTANCE.provideRevisionDao(db));
  }
}
