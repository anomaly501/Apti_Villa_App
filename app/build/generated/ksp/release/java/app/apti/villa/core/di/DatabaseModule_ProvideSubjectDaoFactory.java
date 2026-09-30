package app.apti.villa.core.di;

import app.apti.villa.data.local.dao.SubjectDao;
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
public final class DatabaseModule_ProvideSubjectDaoFactory implements Factory<SubjectDao> {
  private final Provider<AppDatabase> dbProvider;

  public DatabaseModule_ProvideSubjectDaoFactory(Provider<AppDatabase> dbProvider) {
    this.dbProvider = dbProvider;
  }

  @Override
  public SubjectDao get() {
    return provideSubjectDao(dbProvider.get());
  }

  public static DatabaseModule_ProvideSubjectDaoFactory create(
      javax.inject.Provider<AppDatabase> dbProvider) {
    return new DatabaseModule_ProvideSubjectDaoFactory(Providers.asDaggerProvider(dbProvider));
  }

  public static DatabaseModule_ProvideSubjectDaoFactory create(Provider<AppDatabase> dbProvider) {
    return new DatabaseModule_ProvideSubjectDaoFactory(dbProvider);
  }

  public static SubjectDao provideSubjectDao(AppDatabase db) {
    return Preconditions.checkNotNullFromProvides(DatabaseModule.INSTANCE.provideSubjectDao(db));
  }
}
