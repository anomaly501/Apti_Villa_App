package app.apti.villa.data.repository;

import app.apti.villa.data.local.dao.UserProfileDao;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.Provider;
import dagger.internal.Providers;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;

@ScopeMetadata("javax.inject.Singleton")
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
public final class UserProfileRepositoryImpl_Factory implements Factory<UserProfileRepositoryImpl> {
  private final Provider<UserProfileDao> userProfileDaoProvider;

  public UserProfileRepositoryImpl_Factory(Provider<UserProfileDao> userProfileDaoProvider) {
    this.userProfileDaoProvider = userProfileDaoProvider;
  }

  @Override
  public UserProfileRepositoryImpl get() {
    return newInstance(userProfileDaoProvider.get());
  }

  public static UserProfileRepositoryImpl_Factory create(
      javax.inject.Provider<UserProfileDao> userProfileDaoProvider) {
    return new UserProfileRepositoryImpl_Factory(Providers.asDaggerProvider(userProfileDaoProvider));
  }

  public static UserProfileRepositoryImpl_Factory create(
      Provider<UserProfileDao> userProfileDaoProvider) {
    return new UserProfileRepositoryImpl_Factory(userProfileDaoProvider);
  }

  public static UserProfileRepositoryImpl newInstance(UserProfileDao userProfileDao) {
    return new UserProfileRepositoryImpl(userProfileDao);
  }
}
