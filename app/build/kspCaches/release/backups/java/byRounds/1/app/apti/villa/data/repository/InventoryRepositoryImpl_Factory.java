package app.apti.villa.data.repository;

import app.apti.villa.data.local.dao.AttemptDao;
import app.apti.villa.data.local.dao.ExamDao;
import app.apti.villa.data.local.dao.InventoryDao;
import app.apti.villa.data.local.dao.TestDao;
import app.apti.villa.data.local.dao.TestQuestionDao;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.Provider;
import dagger.internal.Providers;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;
import kotlinx.coroutines.CoroutineDispatcher;

@ScopeMetadata("javax.inject.Singleton")
@QualifierMetadata("app.apti.villa.core.common.IoDispatcher")
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
public final class InventoryRepositoryImpl_Factory implements Factory<InventoryRepositoryImpl> {
  private final Provider<InventoryDao> inventoryDaoProvider;

  private final Provider<TestDao> testDaoProvider;

  private final Provider<TestQuestionDao> testQuestionDaoProvider;

  private final Provider<ExamDao> examDaoProvider;

  private final Provider<AttemptDao> attemptDaoProvider;

  private final Provider<CoroutineDispatcher> ioDispatcherProvider;

  public InventoryRepositoryImpl_Factory(Provider<InventoryDao> inventoryDaoProvider,
      Provider<TestDao> testDaoProvider, Provider<TestQuestionDao> testQuestionDaoProvider,
      Provider<ExamDao> examDaoProvider, Provider<AttemptDao> attemptDaoProvider,
      Provider<CoroutineDispatcher> ioDispatcherProvider) {
    this.inventoryDaoProvider = inventoryDaoProvider;
    this.testDaoProvider = testDaoProvider;
    this.testQuestionDaoProvider = testQuestionDaoProvider;
    this.examDaoProvider = examDaoProvider;
    this.attemptDaoProvider = attemptDaoProvider;
    this.ioDispatcherProvider = ioDispatcherProvider;
  }

  @Override
  public InventoryRepositoryImpl get() {
    return newInstance(inventoryDaoProvider.get(), testDaoProvider.get(), testQuestionDaoProvider.get(), examDaoProvider.get(), attemptDaoProvider.get(), ioDispatcherProvider.get());
  }

  public static InventoryRepositoryImpl_Factory create(
      javax.inject.Provider<InventoryDao> inventoryDaoProvider,
      javax.inject.Provider<TestDao> testDaoProvider,
      javax.inject.Provider<TestQuestionDao> testQuestionDaoProvider,
      javax.inject.Provider<ExamDao> examDaoProvider,
      javax.inject.Provider<AttemptDao> attemptDaoProvider,
      javax.inject.Provider<CoroutineDispatcher> ioDispatcherProvider) {
    return new InventoryRepositoryImpl_Factory(Providers.asDaggerProvider(inventoryDaoProvider), Providers.asDaggerProvider(testDaoProvider), Providers.asDaggerProvider(testQuestionDaoProvider), Providers.asDaggerProvider(examDaoProvider), Providers.asDaggerProvider(attemptDaoProvider), Providers.asDaggerProvider(ioDispatcherProvider));
  }

  public static InventoryRepositoryImpl_Factory create(Provider<InventoryDao> inventoryDaoProvider,
      Provider<TestDao> testDaoProvider, Provider<TestQuestionDao> testQuestionDaoProvider,
      Provider<ExamDao> examDaoProvider, Provider<AttemptDao> attemptDaoProvider,
      Provider<CoroutineDispatcher> ioDispatcherProvider) {
    return new InventoryRepositoryImpl_Factory(inventoryDaoProvider, testDaoProvider, testQuestionDaoProvider, examDaoProvider, attemptDaoProvider, ioDispatcherProvider);
  }

  public static InventoryRepositoryImpl newInstance(InventoryDao inventoryDao, TestDao testDao,
      TestQuestionDao testQuestionDao, ExamDao examDao, AttemptDao attemptDao,
      CoroutineDispatcher ioDispatcher) {
    return new InventoryRepositoryImpl(inventoryDao, testDao, testQuestionDao, examDao, attemptDao, ioDispatcher);
  }
}
