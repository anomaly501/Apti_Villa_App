package app.apti.villa.ai.context;

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
public final class AiContextBuilder_Factory implements Factory<AiContextBuilder> {
  @Override
  public AiContextBuilder get() {
    return newInstance();
  }

  public static AiContextBuilder_Factory create() {
    return InstanceHolder.INSTANCE;
  }

  public static AiContextBuilder newInstance() {
    return new AiContextBuilder();
  }

  private static final class InstanceHolder {
    static final AiContextBuilder_Factory INSTANCE = new AiContextBuilder_Factory();
  }
}
