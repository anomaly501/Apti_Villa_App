package app.apti.villa.core.di;

import app.apti.villa.data.local.dao.AnswerDao;
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
public final class DatabaseModule_ProvideAnswerDaoFactory implements Factory<AnswerDao> {
  private final Provider<AppDatabase> dbProvider;

  public DatabaseModule_ProvideAnswerDaoFactory(Provider<AppDatabase> dbProvider) {
    this.dbProvider = dbProvider;
  }

  @Override
  public AnswerDao get() {
    return provideAnswerDao(dbProvider.get());
  }

  public static DatabaseModule_ProvideAnswerDaoFactory create(
      javax.inject.Provider<AppDatabase> dbProvider) {
    return new DatabaseModule_ProvideAnswerDaoFactory(Providers.asDaggerProvider(dbProvider));
  }

  public static DatabaseModule_ProvideAnswerDaoFactory create(Provider<AppDatabase> dbProvider) {
    return new DatabaseModule_ProvideAnswerDaoFactory(dbProvider);
  }

  public static AnswerDao provideAnswerDao(AppDatabase db) {
    return Preconditions.checkNotNullFromProvides(DatabaseModule.INSTANCE.provideAnswerDao(db));
  }
}
