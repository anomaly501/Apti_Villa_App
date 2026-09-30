package app.apti.villa.core.di;

import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;
import kotlinx.coroutines.CoroutineDispatcher;

@ScopeMetadata
@QualifierMetadata("app.apti.villa.core.common.MainDispatcher")
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
public final class DispatcherModule_ProvideMainDispatcherFactory implements Factory<CoroutineDispatcher> {
  @Override
  public CoroutineDispatcher get() {
    return provideMainDispatcher();
  }

  public static DispatcherModule_ProvideMainDispatcherFactory create() {
    return InstanceHolder.INSTANCE;
  }

  public static CoroutineDispatcher provideMainDispatcher() {
    return Preconditions.checkNotNullFromProvides(DispatcherModule.INSTANCE.provideMainDispatcher());
  }

  private static final class InstanceHolder {
    static final DispatcherModule_ProvideMainDispatcherFactory INSTANCE = new DispatcherModule_ProvideMainDispatcherFactory();
  }
}
