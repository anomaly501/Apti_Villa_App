package app.apti.villa.core.di;

import app.apti.villa.data.local.dao.InventoryDao;
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
public final class DatabaseModule_ProvideInventoryDaoFactory implements Factory<InventoryDao> {
  private final Provider<AppDatabase> dbProvider;

  public DatabaseModule_ProvideInventoryDaoFactory(Provider<AppDatabase> dbProvider) {
    this.dbProvider = dbProvider;
  }

  @Override
  public InventoryDao get() {
    return provideInventoryDao(dbProvider.get());
  }

  public static DatabaseModule_ProvideInventoryDaoFactory create(
      javax.inject.Provider<AppDatabase> dbProvider) {
    return new DatabaseModule_ProvideInventoryDaoFactory(Providers.asDaggerProvider(dbProvider));
  }

  public static DatabaseModule_ProvideInventoryDaoFactory create(Provider<AppDatabase> dbProvider) {
    return new DatabaseModule_ProvideInventoryDaoFactory(dbProvider);
  }

  public static InventoryDao provideInventoryDao(AppDatabase db) {
    return Preconditions.checkNotNullFromProvides(DatabaseModule.INSTANCE.provideInventoryDao(db));
  }
}
