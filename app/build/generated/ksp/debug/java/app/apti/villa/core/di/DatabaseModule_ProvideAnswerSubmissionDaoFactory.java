package app.apti.villa.core.di;

import app.apti.villa.data.local.dao.AnswerSubmissionDao;
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
public final class DatabaseModule_ProvideAnswerSubmissionDaoFactory implements Factory<AnswerSubmissionDao> {
  private final Provider<AppDatabase> dbProvider;

  public DatabaseModule_ProvideAnswerSubmissionDaoFactory(Provider<AppDatabase> dbProvider) {
    this.dbProvider = dbProvider;
  }

  @Override
  public AnswerSubmissionDao get() {
    return provideAnswerSubmissionDao(dbProvider.get());
  }

  public static DatabaseModule_ProvideAnswerSubmissionDaoFactory create(
      javax.inject.Provider<AppDatabase> dbProvider) {
    return new DatabaseModule_ProvideAnswerSubmissionDaoFactory(Providers.asDaggerProvider(dbProvider));
  }

  public static DatabaseModule_ProvideAnswerSubmissionDaoFactory create(
      Provider<AppDatabase> dbProvider) {
    return new DatabaseModule_ProvideAnswerSubmissionDaoFactory(dbProvider);
  }

  public static AnswerSubmissionDao provideAnswerSubmissionDao(AppDatabase db) {
    return Preconditions.checkNotNullFromProvides(DatabaseModule.INSTANCE.provideAnswerSubmissionDao(db));
  }
}
