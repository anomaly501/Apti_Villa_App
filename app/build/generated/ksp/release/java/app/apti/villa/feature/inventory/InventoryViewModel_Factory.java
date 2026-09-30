package app.apti.villa.feature.inventory;

import app.apti.villa.domain.repository.InventoryRepository;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
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
public final class InventoryViewModel_Factory implements Factory<InventoryViewModel> {
  private final Provider<InventoryRepository> inventoryRepositoryProvider;

  public InventoryViewModel_Factory(Provider<InventoryRepository> inventoryRepositoryProvider) {
    this.inventoryRepositoryProvider = inventoryRepositoryProvider;
  }

  @Override
  public InventoryViewModel get() {
    return newInstance(inventoryRepositoryProvider.get());
  }

  public static InventoryViewModel_Factory create(
      javax.inject.Provider<InventoryRepository> inventoryRepositoryProvider) {
    return new InventoryViewModel_Factory(Providers.asDaggerProvider(inventoryRepositoryProvider));
  }

  public static InventoryViewModel_Factory create(
      Provider<InventoryRepository> inventoryRepositoryProvider) {
    return new InventoryViewModel_Factory(inventoryRepositoryProvider);
  }

  public static InventoryViewModel newInstance(InventoryRepository inventoryRepository) {
    return new InventoryViewModel(inventoryRepository);
  }
}
