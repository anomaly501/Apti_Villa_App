package app.apti.villa.core.di;

import app.apti.villa.data.local.dao.TopicDao;
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
public final class DatabaseModule_ProvideTopicDaoFactory implements Factory<TopicDao> {
  private final Provider<AppDatabase> dbProvider;

  public DatabaseModule_ProvideTopicDaoFactory(Provider<AppDatabase> dbProvider) {
    this.dbProvider = dbProvider;
  }

  @Override
  public TopicDao get() {
    return provideTopicDao(dbProvider.get());
  }

  public static DatabaseModule_ProvideTopicDaoFactory create(
      javax.inject.Provider<AppDatabase> dbProvider) {
    return new DatabaseModule_ProvideTopicDaoFactory(Providers.asDaggerProvider(dbProvider));
  }

  public static DatabaseModule_ProvideTopicDaoFactory create(Provider<AppDatabase> dbProvider) {
    return new DatabaseModule_ProvideTopicDaoFactory(dbProvider);
  }

  public static TopicDao provideTopicDao(AppDatabase db) {
    return Preconditions.checkNotNullFromProvides(DatabaseModule.INSTANCE.provideTopicDao(db));
  }
}
