package app.apti.villa.core.di;

import app.apti.villa.data.local.dao.TrickDao;
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
public final class DatabaseModule_ProvideTrickDaoFactory implements Factory<TrickDao> {
  private final Provider<AppDatabase> dbProvider;

  public DatabaseModule_ProvideTrickDaoFactory(Provider<AppDatabase> dbProvider) {
    this.dbProvider = dbProvider;
  }

  @Override
  public TrickDao get() {
    return provideTrickDao(dbProvider.get());
  }

  public static DatabaseModule_ProvideTrickDaoFactory create(
      javax.inject.Provider<AppDatabase> dbProvider) {
    return new DatabaseModule_ProvideTrickDaoFactory(Providers.asDaggerProvider(dbProvider));
  }

  public static DatabaseModule_ProvideTrickDaoFactory create(Provider<AppDatabase> dbProvider) {
    return new DatabaseModule_ProvideTrickDaoFactory(dbProvider);
  }

  public static TrickDao provideTrickDao(AppDatabase db) {
    return Preconditions.checkNotNullFromProvides(DatabaseModule.INSTANCE.provideTrickDao(db));
  }
}
