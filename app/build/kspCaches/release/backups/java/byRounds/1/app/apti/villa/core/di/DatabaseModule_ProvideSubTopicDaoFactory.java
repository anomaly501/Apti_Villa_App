package app.apti.villa.core.di;

import app.apti.villa.data.local.dao.SubTopicDao;
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
public final class DatabaseModule_ProvideSubTopicDaoFactory implements Factory<SubTopicDao> {
  private final Provider<AppDatabase> dbProvider;

  public DatabaseModule_ProvideSubTopicDaoFactory(Provider<AppDatabase> dbProvider) {
    this.dbProvider = dbProvider;
  }

  @Override
  public SubTopicDao get() {
    return provideSubTopicDao(dbProvider.get());
  }

  public static DatabaseModule_ProvideSubTopicDaoFactory create(
      javax.inject.Provider<AppDatabase> dbProvider) {
    return new DatabaseModule_ProvideSubTopicDaoFactory(Providers.asDaggerProvider(dbProvider));
  }

  public static DatabaseModule_ProvideSubTopicDaoFactory create(Provider<AppDatabase> dbProvider) {
    return new DatabaseModule_ProvideSubTopicDaoFactory(dbProvider);
  }

  public static SubTopicDao provideSubTopicDao(AppDatabase db) {
    return Preconditions.checkNotNullFromProvides(DatabaseModule.INSTANCE.provideSubTopicDao(db));
  }
}
