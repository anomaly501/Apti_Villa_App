package app.apti.villa.ai.security;

import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
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
public final class SecureKeyStore_Factory implements Factory<SecureKeyStore> {
  @Override
  public SecureKeyStore get() {
    return newInstance();
  }

  public static SecureKeyStore_Factory create() {
    return InstanceHolder.INSTANCE;
  }

  public static SecureKeyStore newInstance() {
    return new SecureKeyStore();
  }

  private static final class InstanceHolder {
    static final SecureKeyStore_Factory INSTANCE = new SecureKeyStore_Factory();
  }
}
