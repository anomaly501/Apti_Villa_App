package app.apti.villa.core.di;

import app.apti.villa.data.local.dao.HintUsageDao;
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
public final class DatabaseModule_ProvideHintUsageDaoFactory implements Factory<HintUsageDao> {
  private final Provider<AppDatabase> dbProvider;

  public DatabaseModule_ProvideHintUsageDaoFactory(Provider<AppDatabase> dbProvider) {
    this.dbProvider = dbProvider;
  }

  @Override
  public HintUsageDao get() {
    return provideHintUsageDao(dbProvider.get());
  }

  public static DatabaseModule_ProvideHintUsageDaoFactory create(
      javax.inject.Provider<AppDatabase> dbProvider) {
    return new DatabaseModule_ProvideHintUsageDaoFactory(Providers.asDaggerProvider(dbProvider));
  }

  public static DatabaseModule_ProvideHintUsageDaoFactory create(Provider<AppDatabase> dbProvider) {
    return new DatabaseModule_ProvideHintUsageDaoFactory(dbProvider);
  }

  public static HintUsageDao provideHintUsageDao(AppDatabase db) {
    return Preconditions.checkNotNullFromProvides(DatabaseModule.INSTANCE.provideHintUsageDao(db));
  }
}
