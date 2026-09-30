package app.apti.villa.core.di;

import app.apti.villa.data.local.dao.QuestionOptionDao;
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
public final class DatabaseModule_ProvideQuestionOptionDaoFactory implements Factory<QuestionOptionDao> {
  private final Provider<AppDatabase> dbProvider;

  public DatabaseModule_ProvideQuestionOptionDaoFactory(Provider<AppDatabase> dbProvider) {
    this.dbProvider = dbProvider;
  }

  @Override
  public QuestionOptionDao get() {
    return provideQuestionOptionDao(dbProvider.get());
  }

  public static DatabaseModule_ProvideQuestionOptionDaoFactory create(
      javax.inject.Provider<AppDatabase> dbProvider) {
    return new DatabaseModule_ProvideQuestionOptionDaoFactory(Providers.asDaggerProvider(dbProvider));
  }

  public static DatabaseModule_ProvideQuestionOptionDaoFactory create(
      Provider<AppDatabase> dbProvider) {
    return new DatabaseModule_ProvideQuestionOptionDaoFactory(dbProvider);
  }

  public static QuestionOptionDao provideQuestionOptionDao(AppDatabase db) {
    return Preconditions.checkNotNullFromProvides(DatabaseModule.INSTANCE.provideQuestionOptionDao(db));
  }
}
