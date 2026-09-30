package app.apti.villa.core.di;

import app.apti.villa.data.local.dao.TestDao;
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
public final class DatabaseModule_ProvideTestDaoFactory implements Factory<TestDao> {
  private final Provider<AppDatabase> dbProvider;

  public DatabaseModule_ProvideTestDaoFactory(Provider<AppDatabase> dbProvider) {
    this.dbProvider = dbProvider;
  }

  @Override
  public TestDao get() {
    return provideTestDao(dbProvider.get());
  }

  public static DatabaseModule_ProvideTestDaoFactory create(
      javax.inject.Provider<AppDatabase> dbProvider) {
    return new DatabaseModule_ProvideTestDaoFactory(Providers.asDaggerProvider(dbProvider));
  }

  public static DatabaseModule_ProvideTestDaoFactory create(Provider<AppDatabase> dbProvider) {
    return new DatabaseModule_ProvideTestDaoFactory(dbProvider);
  }

  public static TestDao provideTestDao(AppDatabase db) {
    return Preconditions.checkNotNullFromProvides(DatabaseModule.INSTANCE.provideTestDao(db));
  }
}
