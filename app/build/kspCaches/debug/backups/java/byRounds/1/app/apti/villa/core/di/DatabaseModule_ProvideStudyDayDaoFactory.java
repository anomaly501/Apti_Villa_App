package app.apti.villa.core.di;

import app.apti.villa.data.local.dao.StudyDayDao;
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
public final class DatabaseModule_ProvideStudyDayDaoFactory implements Factory<StudyDayDao> {
  private final Provider<AppDatabase> dbProvider;

  public DatabaseModule_ProvideStudyDayDaoFactory(Provider<AppDatabase> dbProvider) {
    this.dbProvider = dbProvider;
  }

  @Override
  public StudyDayDao get() {
    return provideStudyDayDao(dbProvider.get());
  }

  public static DatabaseModule_ProvideStudyDayDaoFactory create(
      javax.inject.Provider<AppDatabase> dbProvider) {
    return new DatabaseModule_ProvideStudyDayDaoFactory(Providers.asDaggerProvider(dbProvider));
  }

  public static DatabaseModule_ProvideStudyDayDaoFactory create(Provider<AppDatabase> dbProvider) {
    return new DatabaseModule_ProvideStudyDayDaoFactory(dbProvider);
  }

  public static StudyDayDao provideStudyDayDao(AppDatabase db) {
    return Preconditions.checkNotNullFromProvides(DatabaseModule.INSTANCE.provideStudyDayDao(db));
  }
}
