package app.apti.villa.feature.question;

import androidx.lifecycle.SavedStateHandle;
import app.apti.villa.domain.repository.PracticeRepository;
import app.apti.villa.domain.repository.TagRepository;
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
public final class QuestionViewModel_Factory implements Factory<QuestionViewModel> {
  private final Provider<PracticeRepository> practiceRepositoryProvider;

  private final Provider<TagRepository> tagRepositoryProvider;

  private final Provider<SavedStateHandle> savedStateHandleProvider;

  public QuestionViewModel_Factory(Provider<PracticeRepository> practiceRepositoryProvider,
      Provider<TagRepository> tagRepositoryProvider,
      Provider<SavedStateHandle> savedStateHandleProvider) {
    this.practiceRepositoryProvider = practiceRepositoryProvider;
    this.tagRepositoryProvider = tagRepositoryProvider;
    this.savedStateHandleProvider = savedStateHandleProvider;
  }

  @Override
  public QuestionViewModel get() {
    return newInstance(practiceRepositoryProvider.get(), tagRepositoryProvider.get(), savedStateHandleProvider.get());
  }

  public static QuestionViewModel_Factory create(
      javax.inject.Provider<PracticeRepository> practiceRepositoryProvider,
      javax.inject.Provider<TagRepository> tagRepositoryProvider,
      javax.inject.Provider<SavedStateHandle> savedStateHandleProvider) {
    return new QuestionViewModel_Factory(Providers.asDaggerProvider(practiceRepositoryProvider), Providers.asDaggerProvider(tagRepositoryProvider), Providers.asDaggerProvider(savedStateHandleProvider));
  }

  public static QuestionViewModel_Factory create(
      Provider<PracticeRepository> practiceRepositoryProvider,
      Provider<TagRepository> tagRepositoryProvider,
      Provider<SavedStateHandle> savedStateHandleProvider) {
    return new QuestionViewModel_Factory(practiceRepositoryProvider, tagRepositoryProvider, savedStateHandleProvider);
  }

  public static QuestionViewModel newInstance(PracticeRepository practiceRepository,
      TagRepository tagRepository, SavedStateHandle savedStateHandle) {
    return new QuestionViewModel(practiceRepository, tagRepository, savedStateHandle);
  }
}
