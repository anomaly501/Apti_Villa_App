package app.apti.villa;

import android.app.Activity;
import android.app.Service;
import android.view.View;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.SavedStateHandle;
import androidx.lifecycle.ViewModel;
import app.apti.villa.ai.config.AiConfigRepository;
import app.apti.villa.ai.context.AiContextBuilder;
import app.apti.villa.ai.di.AiNetworkModule_ProvideOkHttpClientFactory;
import app.apti.villa.ai.provider.AiProviderRegistry;
import app.apti.villa.ai.provider.AnthropicProvider;
import app.apti.villa.ai.provider.GeminiProvider;
import app.apti.villa.ai.provider.OpenAiCompatibleProvider;
import app.apti.villa.ai.security.SecureKeyStore;
import app.apti.villa.core.di.DatabaseModule_ProvideAiConversationDaoFactory;
import app.apti.villa.core.di.DatabaseModule_ProvideAnalyticsDaoFactory;
import app.apti.villa.core.di.DatabaseModule_ProvideAnswerDaoFactory;
import app.apti.villa.core.di.DatabaseModule_ProvideAnswerSubmissionDaoFactory;
import app.apti.villa.core.di.DatabaseModule_ProvideAttemptDaoFactory;
import app.apti.villa.core.di.DatabaseModule_ProvideDatabaseFactory;
import app.apti.villa.core.di.DatabaseModule_ProvideExamDaoFactory;
import app.apti.villa.core.di.DatabaseModule_ProvideHintDaoFactory;
import app.apti.villa.core.di.DatabaseModule_ProvideHintUsageDaoFactory;
import app.apti.villa.core.di.DatabaseModule_ProvideInventoryDaoFactory;
import app.apti.villa.core.di.DatabaseModule_ProvideMistakeDaoFactory;
import app.apti.villa.core.di.DatabaseModule_ProvideQuestionDaoFactory;
import app.apti.villa.core.di.DatabaseModule_ProvideQuestionOptionDaoFactory;
import app.apti.villa.core.di.DatabaseModule_ProvideQuestionTagDaoFactory;
import app.apti.villa.core.di.DatabaseModule_ProvideRevisionDaoFactory;
import app.apti.villa.core.di.DatabaseModule_ProvideSavedTrickDaoFactory;
import app.apti.villa.core.di.DatabaseModule_ProvideSearchDaoFactory;
import app.apti.villa.core.di.DatabaseModule_ProvideSolutionStepDaoFactory;
import app.apti.villa.core.di.DatabaseModule_ProvideSourceDaoFactory;
import app.apti.villa.core.di.DatabaseModule_ProvideStudyDayDaoFactory;
import app.apti.villa.core.di.DatabaseModule_ProvideSubTopicDaoFactory;
import app.apti.villa.core.di.DatabaseModule_ProvideSubjectDaoFactory;
import app.apti.villa.core.di.DatabaseModule_ProvideTagDaoFactory;
import app.apti.villa.core.di.DatabaseModule_ProvideTestDaoFactory;
import app.apti.villa.core.di.DatabaseModule_ProvideTestQuestionDaoFactory;
import app.apti.villa.core.di.DatabaseModule_ProvideTopicDaoFactory;
import app.apti.villa.core.di.DatabaseModule_ProvideTrickDaoFactory;
import app.apti.villa.core.di.DatabaseModule_ProvideUserProfileDaoFactory;
import app.apti.villa.core.di.DispatcherModule_ProvideIoDispatcherFactory;
import app.apti.villa.data.importer.ImportRepositoryImpl;
import app.apti.villa.data.local.dao.AiConversationDao;
import app.apti.villa.data.local.dao.AnalyticsDao;
import app.apti.villa.data.local.dao.AnswerDao;
import app.apti.villa.data.local.dao.AnswerSubmissionDao;
import app.apti.villa.data.local.dao.AttemptDao;
import app.apti.villa.data.local.dao.ExamDao;
import app.apti.villa.data.local.dao.HintDao;
import app.apti.villa.data.local.dao.HintUsageDao;
import app.apti.villa.data.local.dao.InventoryDao;
import app.apti.villa.data.local.dao.MistakeDao;
import app.apti.villa.data.local.dao.QuestionDao;
import app.apti.villa.data.local.dao.QuestionOptionDao;
import app.apti.villa.data.local.dao.QuestionTagDao;
import app.apti.villa.data.local.dao.RevisionDao;
import app.apti.villa.data.local.dao.SavedTrickDao;
import app.apti.villa.data.local.dao.SearchDao;
import app.apti.villa.data.local.dao.SolutionStepDao;
import app.apti.villa.data.local.dao.SourceDao;
import app.apti.villa.data.local.dao.StudyDayDao;
import app.apti.villa.data.local.dao.SubTopicDao;
import app.apti.villa.data.local.dao.SubjectDao;
import app.apti.villa.data.local.dao.TagDao;
import app.apti.villa.data.local.dao.TestDao;
import app.apti.villa.data.local.dao.TestQuestionDao;
import app.apti.villa.data.local.dao.TopicDao;
import app.apti.villa.data.local.dao.TrickDao;
import app.apti.villa.data.local.dao.UserProfileDao;
import app.apti.villa.data.local.db.AppDatabase;
import app.apti.villa.data.prefs.UserPreferencesDataStore;
import app.apti.villa.data.repository.AiConversationRepositoryImpl;
import app.apti.villa.data.repository.AnalyticsRepositoryImpl;
import app.apti.villa.data.repository.BackupRepositoryImpl;
import app.apti.villa.data.repository.InventoryRepositoryImpl;
import app.apti.villa.data.repository.PracticeRepositoryImpl;
import app.apti.villa.data.repository.PreferencesRepositoryImpl;
import app.apti.villa.data.repository.ReviewRepositoryImpl;
import app.apti.villa.data.repository.SearchRepositoryImpl;
import app.apti.villa.data.repository.TagRepositoryImpl;
import app.apti.villa.data.repository.TaxonomyRepositoryImpl;
import app.apti.villa.data.repository.TestRepositoryImpl;
import app.apti.villa.data.repository.UserProfileRepositoryImpl;
import app.apti.villa.feature.ai.AiChatViewModel;
import app.apti.villa.feature.ai.AiChatViewModel_HiltModules;
import app.apti.villa.feature.ai.AiChatViewModel_HiltModules_BindsModule_Binds_LazyMapKey;
import app.apti.villa.feature.ai.AiChatViewModel_HiltModules_KeyModule_Provide_LazyMapKey;
import app.apti.villa.feature.ai.AiHistoryViewModel;
import app.apti.villa.feature.ai.AiHistoryViewModel_HiltModules;
import app.apti.villa.feature.ai.AiHistoryViewModel_HiltModules_BindsModule_Binds_LazyMapKey;
import app.apti.villa.feature.ai.AiHistoryViewModel_HiltModules_KeyModule_Provide_LazyMapKey;
import app.apti.villa.feature.ai.AiSettingsViewModel;
import app.apti.villa.feature.ai.AiSettingsViewModel_HiltModules;
import app.apti.villa.feature.ai.AiSettingsViewModel_HiltModules_BindsModule_Binds_LazyMapKey;
import app.apti.villa.feature.ai.AiSettingsViewModel_HiltModules_KeyModule_Provide_LazyMapKey;
import app.apti.villa.feature.analytics.AnalyticsViewModel;
import app.apti.villa.feature.analytics.AnalyticsViewModel_HiltModules;
import app.apti.villa.feature.analytics.AnalyticsViewModel_HiltModules_BindsModule_Binds_LazyMapKey;
import app.apti.villa.feature.analytics.AnalyticsViewModel_HiltModules_KeyModule_Provide_LazyMapKey;
import app.apti.villa.feature.backup.BackupViewModel;
import app.apti.villa.feature.backup.BackupViewModel_HiltModules;
import app.apti.villa.feature.backup.BackupViewModel_HiltModules_BindsModule_Binds_LazyMapKey;
import app.apti.villa.feature.backup.BackupViewModel_HiltModules_KeyModule_Provide_LazyMapKey;
import app.apti.villa.feature.home.HomeViewModel;
import app.apti.villa.feature.home.HomeViewModel_HiltModules;
import app.apti.villa.feature.home.HomeViewModel_HiltModules_BindsModule_Binds_LazyMapKey;
import app.apti.villa.feature.home.HomeViewModel_HiltModules_KeyModule_Provide_LazyMapKey;
import app.apti.villa.feature.importer.ImportViewModel;
import app.apti.villa.feature.importer.ImportViewModel_HiltModules;
import app.apti.villa.feature.importer.ImportViewModel_HiltModules_BindsModule_Binds_LazyMapKey;
import app.apti.villa.feature.importer.ImportViewModel_HiltModules_KeyModule_Provide_LazyMapKey;
import app.apti.villa.feature.inventory.InventoryViewModel;
import app.apti.villa.feature.inventory.InventoryViewModel_HiltModules;
import app.apti.villa.feature.inventory.InventoryViewModel_HiltModules_BindsModule_Binds_LazyMapKey;
import app.apti.villa.feature.inventory.InventoryViewModel_HiltModules_KeyModule_Provide_LazyMapKey;
import app.apti.villa.feature.onboarding.OnboardingViewModel;
import app.apti.villa.feature.onboarding.OnboardingViewModel_HiltModules;
import app.apti.villa.feature.onboarding.OnboardingViewModel_HiltModules_BindsModule_Binds_LazyMapKey;
import app.apti.villa.feature.onboarding.OnboardingViewModel_HiltModules_KeyModule_Provide_LazyMapKey;
import app.apti.villa.feature.practice.PracticeViewModel;
import app.apti.villa.feature.practice.PracticeViewModel_HiltModules;
import app.apti.villa.feature.practice.PracticeViewModel_HiltModules_BindsModule_Binds_LazyMapKey;
import app.apti.villa.feature.practice.PracticeViewModel_HiltModules_KeyModule_Provide_LazyMapKey;
import app.apti.villa.feature.profile.ProfileViewModel;
import app.apti.villa.feature.profile.ProfileViewModel_HiltModules;
import app.apti.villa.feature.profile.ProfileViewModel_HiltModules_BindsModule_Binds_LazyMapKey;
import app.apti.villa.feature.profile.ProfileViewModel_HiltModules_KeyModule_Provide_LazyMapKey;
import app.apti.villa.feature.question.QuestionViewModel;
import app.apti.villa.feature.question.QuestionViewModel_HiltModules;
import app.apti.villa.feature.question.QuestionViewModel_HiltModules_BindsModule_Binds_LazyMapKey;
import app.apti.villa.feature.question.QuestionViewModel_HiltModules_KeyModule_Provide_LazyMapKey;
import app.apti.villa.feature.review.ReviewViewModel;
import app.apti.villa.feature.review.ReviewViewModel_HiltModules;
import app.apti.villa.feature.review.ReviewViewModel_HiltModules_BindsModule_Binds_LazyMapKey;
import app.apti.villa.feature.review.ReviewViewModel_HiltModules_KeyModule_Provide_LazyMapKey;
import app.apti.villa.feature.search.SearchViewModel;
import app.apti.villa.feature.search.SearchViewModel_HiltModules;
import app.apti.villa.feature.search.SearchViewModel_HiltModules_BindsModule_Binds_LazyMapKey;
import app.apti.villa.feature.search.SearchViewModel_HiltModules_KeyModule_Provide_LazyMapKey;
import app.apti.villa.feature.settings.DataManagementViewModel;
import app.apti.villa.feature.settings.DataManagementViewModel_HiltModules;
import app.apti.villa.feature.settings.DataManagementViewModel_HiltModules_BindsModule_Binds_LazyMapKey;
import app.apti.villa.feature.settings.DataManagementViewModel_HiltModules_KeyModule_Provide_LazyMapKey;
import app.apti.villa.feature.settings.SettingsViewModel;
import app.apti.villa.feature.settings.SettingsViewModel_HiltModules;
import app.apti.villa.feature.settings.SettingsViewModel_HiltModules_BindsModule_Binds_LazyMapKey;
import app.apti.villa.feature.settings.SettingsViewModel_HiltModules_KeyModule_Provide_LazyMapKey;
import app.apti.villa.feature.test.ExamViewModel;
import app.apti.villa.feature.test.ExamViewModel_HiltModules;
import app.apti.villa.feature.test.ExamViewModel_HiltModules_BindsModule_Binds_LazyMapKey;
import app.apti.villa.feature.test.ExamViewModel_HiltModules_KeyModule_Provide_LazyMapKey;
import app.apti.villa.feature.test.TestListViewModel;
import app.apti.villa.feature.test.TestListViewModel_HiltModules;
import app.apti.villa.feature.test.TestListViewModel_HiltModules_BindsModule_Binds_LazyMapKey;
import app.apti.villa.feature.test.TestListViewModel_HiltModules_KeyModule_Provide_LazyMapKey;
import app.apti.villa.feature.test.TestResultViewModel;
import app.apti.villa.feature.test.TestResultViewModel_HiltModules;
import app.apti.villa.feature.test.TestResultViewModel_HiltModules_BindsModule_Binds_LazyMapKey;
import app.apti.villa.feature.test.TestResultViewModel_HiltModules_KeyModule_Provide_LazyMapKey;
import app.apti.villa.feature.test.TestReviewViewModel;
import app.apti.villa.feature.test.TestReviewViewModel_HiltModules;
import app.apti.villa.feature.test.TestReviewViewModel_HiltModules_BindsModule_Binds_LazyMapKey;
import app.apti.villa.feature.test.TestReviewViewModel_HiltModules_KeyModule_Provide_LazyMapKey;
import com.google.errorprone.annotations.CanIgnoreReturnValue;
import dagger.hilt.android.ActivityRetainedLifecycle;
import dagger.hilt.android.ViewModelLifecycle;
import dagger.hilt.android.internal.builders.ActivityComponentBuilder;
import dagger.hilt.android.internal.builders.ActivityRetainedComponentBuilder;
import dagger.hilt.android.internal.builders.FragmentComponentBuilder;
import dagger.hilt.android.internal.builders.ServiceComponentBuilder;
import dagger.hilt.android.internal.builders.ViewComponentBuilder;
import dagger.hilt.android.internal.builders.ViewModelComponentBuilder;
import dagger.hilt.android.internal.builders.ViewWithFragmentComponentBuilder;
import dagger.hilt.android.internal.lifecycle.DefaultViewModelFactories;
import dagger.hilt.android.internal.lifecycle.DefaultViewModelFactories_InternalFactoryFactory_Factory;
import dagger.hilt.android.internal.managers.ActivityRetainedComponentManager_LifecycleModule_ProvideActivityRetainedLifecycleFactory;
import dagger.hilt.android.internal.managers.SavedStateHandleHolder;
import dagger.hilt.android.internal.modules.ApplicationContextModule;
import dagger.hilt.android.internal.modules.ApplicationContextModule_ProvideContextFactory;
import dagger.internal.DaggerGenerated;
import dagger.internal.DoubleCheck;
import dagger.internal.LazyClassKeyMap;
import dagger.internal.MapBuilder;
import dagger.internal.Preconditions;
import dagger.internal.Provider;
import java.util.Collections;
import java.util.Map;
import java.util.Set;
import javax.annotation.processing.Generated;
import okhttp3.OkHttpClient;

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
public final class DaggerAptiVillaApp_HiltComponents_SingletonC {
  private DaggerAptiVillaApp_HiltComponents_SingletonC() {
  }

  public static Builder builder() {
    return new Builder();
  }

  public static final class Builder {
    private ApplicationContextModule applicationContextModule;

    private Builder() {
    }

    public Builder applicationContextModule(ApplicationContextModule applicationContextModule) {
      this.applicationContextModule = Preconditions.checkNotNull(applicationContextModule);
      return this;
    }

    public AptiVillaApp_HiltComponents.SingletonC build() {
      Preconditions.checkBuilderRequirement(applicationContextModule, ApplicationContextModule.class);
      return new SingletonCImpl(applicationContextModule);
    }
  }

  private static final class ActivityRetainedCBuilder implements AptiVillaApp_HiltComponents.ActivityRetainedC.Builder {
    private final SingletonCImpl singletonCImpl;

    private SavedStateHandleHolder savedStateHandleHolder;

    private ActivityRetainedCBuilder(SingletonCImpl singletonCImpl) {
      this.singletonCImpl = singletonCImpl;
    }

    @Override
    public ActivityRetainedCBuilder savedStateHandleHolder(
        SavedStateHandleHolder savedStateHandleHolder) {
      this.savedStateHandleHolder = Preconditions.checkNotNull(savedStateHandleHolder);
      return this;
    }

    @Override
    public AptiVillaApp_HiltComponents.ActivityRetainedC build() {
      Preconditions.checkBuilderRequirement(savedStateHandleHolder, SavedStateHandleHolder.class);
      return new ActivityRetainedCImpl(singletonCImpl, savedStateHandleHolder);
    }
  }

  private static final class ActivityCBuilder implements AptiVillaApp_HiltComponents.ActivityC.Builder {
    private final SingletonCImpl singletonCImpl;

    private final ActivityRetainedCImpl activityRetainedCImpl;

    private Activity activity;

    private ActivityCBuilder(SingletonCImpl singletonCImpl,
        ActivityRetainedCImpl activityRetainedCImpl) {
      this.singletonCImpl = singletonCImpl;
      this.activityRetainedCImpl = activityRetainedCImpl;
    }

    @Override
    public ActivityCBuilder activity(Activity activity) {
      this.activity = Preconditions.checkNotNull(activity);
      return this;
    }

    @Override
    public AptiVillaApp_HiltComponents.ActivityC build() {
      Preconditions.checkBuilderRequirement(activity, Activity.class);
      return new ActivityCImpl(singletonCImpl, activityRetainedCImpl, activity);
    }
  }

  private static final class FragmentCBuilder implements AptiVillaApp_HiltComponents.FragmentC.Builder {
    private final SingletonCImpl singletonCImpl;

    private final ActivityRetainedCImpl activityRetainedCImpl;

    private final ActivityCImpl activityCImpl;

    private Fragment fragment;

    private FragmentCBuilder(SingletonCImpl singletonCImpl,
        ActivityRetainedCImpl activityRetainedCImpl, ActivityCImpl activityCImpl) {
      this.singletonCImpl = singletonCImpl;
      this.activityRetainedCImpl = activityRetainedCImpl;
      this.activityCImpl = activityCImpl;
    }

    @Override
    public FragmentCBuilder fragment(Fragment fragment) {
      this.fragment = Preconditions.checkNotNull(fragment);
      return this;
    }

    @Override
    public AptiVillaApp_HiltComponents.FragmentC build() {
      Preconditions.checkBuilderRequirement(fragment, Fragment.class);
      return new FragmentCImpl(singletonCImpl, activityRetainedCImpl, activityCImpl, fragment);
    }
  }

  private static final class ViewWithFragmentCBuilder implements AptiVillaApp_HiltComponents.ViewWithFragmentC.Builder {
    private final SingletonCImpl singletonCImpl;

    private final ActivityRetainedCImpl activityRetainedCImpl;

    private final ActivityCImpl activityCImpl;

    private final FragmentCImpl fragmentCImpl;

    private View view;

    private ViewWithFragmentCBuilder(SingletonCImpl singletonCImpl,
        ActivityRetainedCImpl activityRetainedCImpl, ActivityCImpl activityCImpl,
        FragmentCImpl fragmentCImpl) {
      this.singletonCImpl = singletonCImpl;
      this.activityRetainedCImpl = activityRetainedCImpl;
      this.activityCImpl = activityCImpl;
      this.fragmentCImpl = fragmentCImpl;
    }

    @Override
    public ViewWithFragmentCBuilder view(View view) {
      this.view = Preconditions.checkNotNull(view);
      return this;
    }

    @Override
    public AptiVillaApp_HiltComponents.ViewWithFragmentC build() {
      Preconditions.checkBuilderRequirement(view, View.class);
      return new ViewWithFragmentCImpl(singletonCImpl, activityRetainedCImpl, activityCImpl, fragmentCImpl, view);
    }
  }

  private static final class ViewCBuilder implements AptiVillaApp_HiltComponents.ViewC.Builder {
    private final SingletonCImpl singletonCImpl;

    private final ActivityRetainedCImpl activityRetainedCImpl;

    private final ActivityCImpl activityCImpl;

    private View view;

    private ViewCBuilder(SingletonCImpl singletonCImpl, ActivityRetainedCImpl activityRetainedCImpl,
        ActivityCImpl activityCImpl) {
      this.singletonCImpl = singletonCImpl;
      this.activityRetainedCImpl = activityRetainedCImpl;
      this.activityCImpl = activityCImpl;
    }

    @Override
    public ViewCBuilder view(View view) {
      this.view = Preconditions.checkNotNull(view);
      return this;
    }

    @Override
    public AptiVillaApp_HiltComponents.ViewC build() {
      Preconditions.checkBuilderRequirement(view, View.class);
      return new ViewCImpl(singletonCImpl, activityRetainedCImpl, activityCImpl, view);
    }
  }

  private static final class ViewModelCBuilder implements AptiVillaApp_HiltComponents.ViewModelC.Builder {
    private final SingletonCImpl singletonCImpl;

    private final ActivityRetainedCImpl activityRetainedCImpl;

    private SavedStateHandle savedStateHandle;

    private ViewModelLifecycle viewModelLifecycle;

    private ViewModelCBuilder(SingletonCImpl singletonCImpl,
        ActivityRetainedCImpl activityRetainedCImpl) {
      this.singletonCImpl = singletonCImpl;
      this.activityRetainedCImpl = activityRetainedCImpl;
    }

    @Override
    public ViewModelCBuilder savedStateHandle(SavedStateHandle handle) {
      this.savedStateHandle = Preconditions.checkNotNull(handle);
      return this;
    }

    @Override
    public ViewModelCBuilder viewModelLifecycle(ViewModelLifecycle viewModelLifecycle) {
      this.viewModelLifecycle = Preconditions.checkNotNull(viewModelLifecycle);
      return this;
    }

    @Override
    public AptiVillaApp_HiltComponents.ViewModelC build() {
      Preconditions.checkBuilderRequirement(savedStateHandle, SavedStateHandle.class);
      Preconditions.checkBuilderRequirement(viewModelLifecycle, ViewModelLifecycle.class);
      return new ViewModelCImpl(singletonCImpl, activityRetainedCImpl, savedStateHandle, viewModelLifecycle);
    }
  }

  private static final class ServiceCBuilder implements AptiVillaApp_HiltComponents.ServiceC.Builder {
    private final SingletonCImpl singletonCImpl;

    private Service service;

    private ServiceCBuilder(SingletonCImpl singletonCImpl) {
      this.singletonCImpl = singletonCImpl;
    }

    @Override
    public ServiceCBuilder service(Service service) {
      this.service = Preconditions.checkNotNull(service);
      return this;
    }

    @Override
    public AptiVillaApp_HiltComponents.ServiceC build() {
      Preconditions.checkBuilderRequirement(service, Service.class);
      return new ServiceCImpl(singletonCImpl, service);
    }
  }

  private static final class ViewWithFragmentCImpl extends AptiVillaApp_HiltComponents.ViewWithFragmentC {
    private final SingletonCImpl singletonCImpl;

    private final ActivityRetainedCImpl activityRetainedCImpl;

    private final ActivityCImpl activityCImpl;

    private final FragmentCImpl fragmentCImpl;

    private final ViewWithFragmentCImpl viewWithFragmentCImpl = this;

    private ViewWithFragmentCImpl(SingletonCImpl singletonCImpl,
        ActivityRetainedCImpl activityRetainedCImpl, ActivityCImpl activityCImpl,
        FragmentCImpl fragmentCImpl, View viewParam) {
      this.singletonCImpl = singletonCImpl;
      this.activityRetainedCImpl = activityRetainedCImpl;
      this.activityCImpl = activityCImpl;
      this.fragmentCImpl = fragmentCImpl;


    }
  }

  private static final class FragmentCImpl extends AptiVillaApp_HiltComponents.FragmentC {
    private final SingletonCImpl singletonCImpl;

    private final ActivityRetainedCImpl activityRetainedCImpl;

    private final ActivityCImpl activityCImpl;

    private final FragmentCImpl fragmentCImpl = this;

    private FragmentCImpl(SingletonCImpl singletonCImpl,
        ActivityRetainedCImpl activityRetainedCImpl, ActivityCImpl activityCImpl,
        Fragment fragmentParam) {
      this.singletonCImpl = singletonCImpl;
      this.activityRetainedCImpl = activityRetainedCImpl;
      this.activityCImpl = activityCImpl;


    }

    @Override
    public DefaultViewModelFactories.InternalFactoryFactory getHiltInternalFactoryFactory() {
      return activityCImpl.getHiltInternalFactoryFactory();
    }

    @Override
    public ViewWithFragmentComponentBuilder viewWithFragmentComponentBuilder() {
      return new ViewWithFragmentCBuilder(singletonCImpl, activityRetainedCImpl, activityCImpl, fragmentCImpl);
    }
  }

  private static final class ViewCImpl extends AptiVillaApp_HiltComponents.ViewC {
    private final SingletonCImpl singletonCImpl;

    private final ActivityRetainedCImpl activityRetainedCImpl;

    private final ActivityCImpl activityCImpl;

    private final ViewCImpl viewCImpl = this;

    private ViewCImpl(SingletonCImpl singletonCImpl, ActivityRetainedCImpl activityRetainedCImpl,
        ActivityCImpl activityCImpl, View viewParam) {
      this.singletonCImpl = singletonCImpl;
      this.activityRetainedCImpl = activityRetainedCImpl;
      this.activityCImpl = activityCImpl;


    }
  }

  private static final class ActivityCImpl extends AptiVillaApp_HiltComponents.ActivityC {
    private final SingletonCImpl singletonCImpl;

    private final ActivityRetainedCImpl activityRetainedCImpl;

    private final ActivityCImpl activityCImpl = this;

    private ActivityCImpl(SingletonCImpl singletonCImpl,
        ActivityRetainedCImpl activityRetainedCImpl, Activity activityParam) {
      this.singletonCImpl = singletonCImpl;
      this.activityRetainedCImpl = activityRetainedCImpl;


    }

    @Override
    public void injectMainActivity(MainActivity mainActivity) {
      injectMainActivity2(mainActivity);
    }

    @Override
    public DefaultViewModelFactories.InternalFactoryFactory getHiltInternalFactoryFactory() {
      return DefaultViewModelFactories_InternalFactoryFactory_Factory.newInstance(getViewModelKeys(), new ViewModelCBuilder(singletonCImpl, activityRetainedCImpl));
    }

    @Override
    public Map<Class<?>, Boolean> getViewModelKeys() {
      return LazyClassKeyMap.<Boolean>of(MapBuilder.<String, Boolean>newMapBuilder(20).put(AiChatViewModel_HiltModules_KeyModule_Provide_LazyMapKey.lazyClassKeyName, AiChatViewModel_HiltModules.KeyModule.provide()).put(AiHistoryViewModel_HiltModules_KeyModule_Provide_LazyMapKey.lazyClassKeyName, AiHistoryViewModel_HiltModules.KeyModule.provide()).put(AiSettingsViewModel_HiltModules_KeyModule_Provide_LazyMapKey.lazyClassKeyName, AiSettingsViewModel_HiltModules.KeyModule.provide()).put(AnalyticsViewModel_HiltModules_KeyModule_Provide_LazyMapKey.lazyClassKeyName, AnalyticsViewModel_HiltModules.KeyModule.provide()).put(BackupViewModel_HiltModules_KeyModule_Provide_LazyMapKey.lazyClassKeyName, BackupViewModel_HiltModules.KeyModule.provide()).put(DataManagementViewModel_HiltModules_KeyModule_Provide_LazyMapKey.lazyClassKeyName, DataManagementViewModel_HiltModules.KeyModule.provide()).put(ExamViewModel_HiltModules_KeyModule_Provide_LazyMapKey.lazyClassKeyName, ExamViewModel_HiltModules.KeyModule.provide()).put(HomeViewModel_HiltModules_KeyModule_Provide_LazyMapKey.lazyClassKeyName, HomeViewModel_HiltModules.KeyModule.provide()).put(ImportViewModel_HiltModules_KeyModule_Provide_LazyMapKey.lazyClassKeyName, ImportViewModel_HiltModules.KeyModule.provide()).put(InventoryViewModel_HiltModules_KeyModule_Provide_LazyMapKey.lazyClassKeyName, InventoryViewModel_HiltModules.KeyModule.provide()).put(OnboardingViewModel_HiltModules_KeyModule_Provide_LazyMapKey.lazyClassKeyName, OnboardingViewModel_HiltModules.KeyModule.provide()).put(PracticeViewModel_HiltModules_KeyModule_Provide_LazyMapKey.lazyClassKeyName, PracticeViewModel_HiltModules.KeyModule.provide()).put(ProfileViewModel_HiltModules_KeyModule_Provide_LazyMapKey.lazyClassKeyName, ProfileViewModel_HiltModules.KeyModule.provide()).put(QuestionViewModel_HiltModules_KeyModule_Provide_LazyMapKey.lazyClassKeyName, QuestionViewModel_HiltModules.KeyModule.provide()).put(ReviewViewModel_HiltModules_KeyModule_Provide_LazyMapKey.lazyClassKeyName, ReviewViewModel_HiltModules.KeyModule.provide()).put(SearchViewModel_HiltModules_KeyModule_Provide_LazyMapKey.lazyClassKeyName, SearchViewModel_HiltModules.KeyModule.provide()).put(SettingsViewModel_HiltModules_KeyModule_Provide_LazyMapKey.lazyClassKeyName, SettingsViewModel_HiltModules.KeyModule.provide()).put(TestListViewModel_HiltModules_KeyModule_Provide_LazyMapKey.lazyClassKeyName, TestListViewModel_HiltModules.KeyModule.provide()).put(TestResultViewModel_HiltModules_KeyModule_Provide_LazyMapKey.lazyClassKeyName, TestResultViewModel_HiltModules.KeyModule.provide()).put(TestReviewViewModel_HiltModules_KeyModule_Provide_LazyMapKey.lazyClassKeyName, TestReviewViewModel_HiltModules.KeyModule.provide()).build());
    }

    @Override
    public ViewModelComponentBuilder getViewModelComponentBuilder() {
      return new ViewModelCBuilder(singletonCImpl, activityRetainedCImpl);
    }

    @Override
    public FragmentComponentBuilder fragmentComponentBuilder() {
      return new FragmentCBuilder(singletonCImpl, activityRetainedCImpl, activityCImpl);
    }

    @Override
    public ViewComponentBuilder viewComponentBuilder() {
      return new ViewCBuilder(singletonCImpl, activityRetainedCImpl, activityCImpl);
    }

    @CanIgnoreReturnValue
    private MainActivity injectMainActivity2(MainActivity instance) {
      MainActivity_MembersInjector.injectPreferencesRepository(instance, singletonCImpl.preferencesRepositoryImplProvider.get());
      return instance;
    }
  }

  private static final class ViewModelCImpl extends AptiVillaApp_HiltComponents.ViewModelC {
    private final SavedStateHandle savedStateHandle;

    private final SingletonCImpl singletonCImpl;

    private final ActivityRetainedCImpl activityRetainedCImpl;

    private final ViewModelCImpl viewModelCImpl = this;

    private Provider<AiChatViewModel> aiChatViewModelProvider;

    private Provider<AiHistoryViewModel> aiHistoryViewModelProvider;

    private Provider<AiSettingsViewModel> aiSettingsViewModelProvider;

    private Provider<AnalyticsViewModel> analyticsViewModelProvider;

    private Provider<BackupViewModel> backupViewModelProvider;

    private Provider<DataManagementViewModel> dataManagementViewModelProvider;

    private Provider<ExamViewModel> examViewModelProvider;

    private Provider<HomeViewModel> homeViewModelProvider;

    private Provider<ImportViewModel> importViewModelProvider;

    private Provider<InventoryViewModel> inventoryViewModelProvider;

    private Provider<OnboardingViewModel> onboardingViewModelProvider;

    private Provider<PracticeViewModel> practiceViewModelProvider;

    private Provider<ProfileViewModel> profileViewModelProvider;

    private Provider<QuestionViewModel> questionViewModelProvider;

    private Provider<ReviewViewModel> reviewViewModelProvider;

    private Provider<SearchViewModel> searchViewModelProvider;

    private Provider<SettingsViewModel> settingsViewModelProvider;

    private Provider<TestListViewModel> testListViewModelProvider;

    private Provider<TestResultViewModel> testResultViewModelProvider;

    private Provider<TestReviewViewModel> testReviewViewModelProvider;

    private ViewModelCImpl(SingletonCImpl singletonCImpl,
        ActivityRetainedCImpl activityRetainedCImpl, SavedStateHandle savedStateHandleParam,
        ViewModelLifecycle viewModelLifecycleParam) {
      this.singletonCImpl = singletonCImpl;
      this.activityRetainedCImpl = activityRetainedCImpl;
      this.savedStateHandle = savedStateHandleParam;
      initialize(savedStateHandleParam, viewModelLifecycleParam);

    }

    @SuppressWarnings("unchecked")
    private void initialize(final SavedStateHandle savedStateHandleParam,
        final ViewModelLifecycle viewModelLifecycleParam) {
      this.aiChatViewModelProvider = new SwitchingProvider<>(singletonCImpl, activityRetainedCImpl, viewModelCImpl, 0);
      this.aiHistoryViewModelProvider = new SwitchingProvider<>(singletonCImpl, activityRetainedCImpl, viewModelCImpl, 1);
      this.aiSettingsViewModelProvider = new SwitchingProvider<>(singletonCImpl, activityRetainedCImpl, viewModelCImpl, 2);
      this.analyticsViewModelProvider = new SwitchingProvider<>(singletonCImpl, activityRetainedCImpl, viewModelCImpl, 3);
      this.backupViewModelProvider = new SwitchingProvider<>(singletonCImpl, activityRetainedCImpl, viewModelCImpl, 4);
      this.dataManagementViewModelProvider = new SwitchingProvider<>(singletonCImpl, activityRetainedCImpl, viewModelCImpl, 5);
      this.examViewModelProvider = new SwitchingProvider<>(singletonCImpl, activityRetainedCImpl, viewModelCImpl, 6);
      this.homeViewModelProvider = new SwitchingProvider<>(singletonCImpl, activityRetainedCImpl, viewModelCImpl, 7);
      this.importViewModelProvider = new SwitchingProvider<>(singletonCImpl, activityRetainedCImpl, viewModelCImpl, 8);
      this.inventoryViewModelProvider = new SwitchingProvider<>(singletonCImpl, activityRetainedCImpl, viewModelCImpl, 9);
      this.onboardingViewModelProvider = new SwitchingProvider<>(singletonCImpl, activityRetainedCImpl, viewModelCImpl, 10);
      this.practiceViewModelProvider = new SwitchingProvider<>(singletonCImpl, activityRetainedCImpl, viewModelCImpl, 11);
      this.profileViewModelProvider = new SwitchingProvider<>(singletonCImpl, activityRetainedCImpl, viewModelCImpl, 12);
      this.questionViewModelProvider = new SwitchingProvider<>(singletonCImpl, activityRetainedCImpl, viewModelCImpl, 13);
      this.reviewViewModelProvider = new SwitchingProvider<>(singletonCImpl, activityRetainedCImpl, viewModelCImpl, 14);
      this.searchViewModelProvider = new SwitchingProvider<>(singletonCImpl, activityRetainedCImpl, viewModelCImpl, 15);
      this.settingsViewModelProvider = new SwitchingProvider<>(singletonCImpl, activityRetainedCImpl, viewModelCImpl, 16);
      this.testListViewModelProvider = new SwitchingProvider<>(singletonCImpl, activityRetainedCImpl, viewModelCImpl, 17);
      this.testResultViewModelProvider = new SwitchingProvider<>(singletonCImpl, activityRetainedCImpl, viewModelCImpl, 18);
      this.testReviewViewModelProvider = new SwitchingProvider<>(singletonCImpl, activityRetainedCImpl, viewModelCImpl, 19);
    }

    @Override
    public Map<Class<?>, javax.inject.Provider<ViewModel>> getHiltViewModelMap() {
      return LazyClassKeyMap.<javax.inject.Provider<ViewModel>>of(MapBuilder.<String, javax.inject.Provider<ViewModel>>newMapBuilder(20).put(AiChatViewModel_HiltModules_BindsModule_Binds_LazyMapKey.lazyClassKeyName, ((Provider) aiChatViewModelProvider)).put(AiHistoryViewModel_HiltModules_BindsModule_Binds_LazyMapKey.lazyClassKeyName, ((Provider) aiHistoryViewModelProvider)).put(AiSettingsViewModel_HiltModules_BindsModule_Binds_LazyMapKey.lazyClassKeyName, ((Provider) aiSettingsViewModelProvider)).put(AnalyticsViewModel_HiltModules_BindsModule_Binds_LazyMapKey.lazyClassKeyName, ((Provider) analyticsViewModelProvider)).put(BackupViewModel_HiltModules_BindsModule_Binds_LazyMapKey.lazyClassKeyName, ((Provider) backupViewModelProvider)).put(DataManagementViewModel_HiltModules_BindsModule_Binds_LazyMapKey.lazyClassKeyName, ((Provider) dataManagementViewModelProvider)).put(ExamViewModel_HiltModules_BindsModule_Binds_LazyMapKey.lazyClassKeyName, ((Provider) examViewModelProvider)).put(HomeViewModel_HiltModules_BindsModule_Binds_LazyMapKey.lazyClassKeyName, ((Provider) homeViewModelProvider)).put(ImportViewModel_HiltModules_BindsModule_Binds_LazyMapKey.lazyClassKeyName, ((Provider) importViewModelProvider)).put(InventoryViewModel_HiltModules_BindsModule_Binds_LazyMapKey.lazyClassKeyName, ((Provider) inventoryViewModelProvider)).put(OnboardingViewModel_HiltModules_BindsModule_Binds_LazyMapKey.lazyClassKeyName, ((Provider) onboardingViewModelProvider)).put(PracticeViewModel_HiltModules_BindsModule_Binds_LazyMapKey.lazyClassKeyName, ((Provider) practiceViewModelProvider)).put(ProfileViewModel_HiltModules_BindsModule_Binds_LazyMapKey.lazyClassKeyName, ((Provider) profileViewModelProvider)).put(QuestionViewModel_HiltModules_BindsModule_Binds_LazyMapKey.lazyClassKeyName, ((Provider) questionViewModelProvider)).put(ReviewViewModel_HiltModules_BindsModule_Binds_LazyMapKey.lazyClassKeyName, ((Provider) reviewViewModelProvider)).put(SearchViewModel_HiltModules_BindsModule_Binds_LazyMapKey.lazyClassKeyName, ((Provider) searchViewModelProvider)).put(SettingsViewModel_HiltModules_BindsModule_Binds_LazyMapKey.lazyClassKeyName, ((Provider) settingsViewModelProvider)).put(TestListViewModel_HiltModules_BindsModule_Binds_LazyMapKey.lazyClassKeyName, ((Provider) testListViewModelProvider)).put(TestResultViewModel_HiltModules_BindsModule_Binds_LazyMapKey.lazyClassKeyName, ((Provider) testResultViewModelProvider)).put(TestReviewViewModel_HiltModules_BindsModule_Binds_LazyMapKey.lazyClassKeyName, ((Provider) testReviewViewModelProvider)).build());
    }

    @Override
    public Map<Class<?>, Object> getHiltViewModelAssistedMap() {
      return Collections.<Class<?>, Object>emptyMap();
    }

    private static final class SwitchingProvider<T> implements Provider<T> {
      private final SingletonCImpl singletonCImpl;

      private final ActivityRetainedCImpl activityRetainedCImpl;

      private final ViewModelCImpl viewModelCImpl;

      private final int id;

      SwitchingProvider(SingletonCImpl singletonCImpl, ActivityRetainedCImpl activityRetainedCImpl,
          ViewModelCImpl viewModelCImpl, int id) {
        this.singletonCImpl = singletonCImpl;
        this.activityRetainedCImpl = activityRetainedCImpl;
        this.viewModelCImpl = viewModelCImpl;
        this.id = id;
      }

      @SuppressWarnings("unchecked")
      @Override
      public T get() {
        switch (id) {
          case 0: // app.apti.villa.feature.ai.AiChatViewModel 
          return (T) new AiChatViewModel(viewModelCImpl.savedStateHandle, singletonCImpl.practiceRepositoryImplProvider.get(), singletonCImpl.aiConfigRepositoryProvider.get(), singletonCImpl.aiProviderRegistryProvider.get(), singletonCImpl.aiContextBuilderProvider.get(), singletonCImpl.aiConversationRepositoryImplProvider.get());

          case 1: // app.apti.villa.feature.ai.AiHistoryViewModel 
          return (T) new AiHistoryViewModel(singletonCImpl.aiConversationRepositoryImplProvider.get());

          case 2: // app.apti.villa.feature.ai.AiSettingsViewModel 
          return (T) new AiSettingsViewModel(singletonCImpl.aiConfigRepositoryProvider.get(), singletonCImpl.aiProviderRegistryProvider.get(), singletonCImpl.aiConversationRepositoryImplProvider.get());

          case 3: // app.apti.villa.feature.analytics.AnalyticsViewModel 
          return (T) new AnalyticsViewModel(singletonCImpl.analyticsRepositoryImplProvider.get());

          case 4: // app.apti.villa.feature.backup.BackupViewModel 
          return (T) new BackupViewModel(singletonCImpl.backupRepositoryImplProvider.get(), ApplicationContextModule_ProvideContextFactory.provideContext(singletonCImpl.applicationContextModule));

          case 5: // app.apti.villa.feature.settings.DataManagementViewModel 
          return (T) new DataManagementViewModel(singletonCImpl.backupRepositoryImplProvider.get());

          case 6: // app.apti.villa.feature.test.ExamViewModel 
          return (T) new ExamViewModel(singletonCImpl.testRepositoryImplProvider.get(), viewModelCImpl.savedStateHandle);

          case 7: // app.apti.villa.feature.home.HomeViewModel 
          return (T) new HomeViewModel(singletonCImpl.userProfileRepositoryImplProvider.get(), singletonCImpl.analyticsRepositoryImplProvider.get(), singletonCImpl.practiceRepositoryImplProvider.get(), singletonCImpl.reviewRepositoryImplProvider.get());

          case 8: // app.apti.villa.feature.importer.ImportViewModel 
          return (T) new ImportViewModel(singletonCImpl.importRepositoryImplProvider.get(), singletonCImpl.taxonomyRepositoryImplProvider.get());

          case 9: // app.apti.villa.feature.inventory.InventoryViewModel 
          return (T) new InventoryViewModel(singletonCImpl.inventoryRepositoryImplProvider.get());

          case 10: // app.apti.villa.feature.onboarding.OnboardingViewModel 
          return (T) new OnboardingViewModel(singletonCImpl.taxonomyRepositoryImplProvider.get(), singletonCImpl.userProfileRepositoryImplProvider.get(), singletonCImpl.preferencesRepositoryImplProvider.get());

          case 11: // app.apti.villa.feature.practice.PracticeViewModel 
          return (T) new PracticeViewModel(singletonCImpl.practiceRepositoryImplProvider.get());

          case 12: // app.apti.villa.feature.profile.ProfileViewModel 
          return (T) new ProfileViewModel(singletonCImpl.userProfileRepositoryImplProvider.get(), singletonCImpl.taxonomyRepositoryImplProvider.get(), singletonCImpl.preferencesRepositoryImplProvider.get());

          case 13: // app.apti.villa.feature.question.QuestionViewModel 
          return (T) new QuestionViewModel(singletonCImpl.practiceRepositoryImplProvider.get(), singletonCImpl.tagRepositoryImplProvider.get(), viewModelCImpl.savedStateHandle);

          case 14: // app.apti.villa.feature.review.ReviewViewModel 
          return (T) new ReviewViewModel(singletonCImpl.reviewRepositoryImplProvider.get());

          case 15: // app.apti.villa.feature.search.SearchViewModel 
          return (T) new SearchViewModel(singletonCImpl.searchRepositoryImplProvider.get());

          case 16: // app.apti.villa.feature.settings.SettingsViewModel 
          return (T) new SettingsViewModel(singletonCImpl.preferencesRepositoryImplProvider.get());

          case 17: // app.apti.villa.feature.test.TestListViewModel 
          return (T) new TestListViewModel(singletonCImpl.testRepositoryImplProvider.get());

          case 18: // app.apti.villa.feature.test.TestResultViewModel 
          return (T) new TestResultViewModel(singletonCImpl.testRepositoryImplProvider.get(), viewModelCImpl.savedStateHandle);

          case 19: // app.apti.villa.feature.test.TestReviewViewModel 
          return (T) new TestReviewViewModel(singletonCImpl.testRepositoryImplProvider.get(), viewModelCImpl.savedStateHandle);

          default: throw new AssertionError(id);
        }
      }
    }
  }

  private static final class ActivityRetainedCImpl extends AptiVillaApp_HiltComponents.ActivityRetainedC {
    private final SingletonCImpl singletonCImpl;

    private final ActivityRetainedCImpl activityRetainedCImpl = this;

    private Provider<ActivityRetainedLifecycle> provideActivityRetainedLifecycleProvider;

    private ActivityRetainedCImpl(SingletonCImpl singletonCImpl,
        SavedStateHandleHolder savedStateHandleHolderParam) {
      this.singletonCImpl = singletonCImpl;

      initialize(savedStateHandleHolderParam);

    }

    @SuppressWarnings("unchecked")
    private void initialize(final SavedStateHandleHolder savedStateHandleHolderParam) {
      this.provideActivityRetainedLifecycleProvider = DoubleCheck.provider(new SwitchingProvider<ActivityRetainedLifecycle>(singletonCImpl, activityRetainedCImpl, 0));
    }

    @Override
    public ActivityComponentBuilder activityComponentBuilder() {
      return new ActivityCBuilder(singletonCImpl, activityRetainedCImpl);
    }

    @Override
    public ActivityRetainedLifecycle getActivityRetainedLifecycle() {
      return provideActivityRetainedLifecycleProvider.get();
    }

    private static final class SwitchingProvider<T> implements Provider<T> {
      private final SingletonCImpl singletonCImpl;

      private final ActivityRetainedCImpl activityRetainedCImpl;

      private final int id;

      SwitchingProvider(SingletonCImpl singletonCImpl, ActivityRetainedCImpl activityRetainedCImpl,
          int id) {
        this.singletonCImpl = singletonCImpl;
        this.activityRetainedCImpl = activityRetainedCImpl;
        this.id = id;
      }

      @SuppressWarnings("unchecked")
      @Override
      public T get() {
        switch (id) {
          case 0: // dagger.hilt.android.ActivityRetainedLifecycle 
          return (T) ActivityRetainedComponentManager_LifecycleModule_ProvideActivityRetainedLifecycleFactory.provideActivityRetainedLifecycle();

          default: throw new AssertionError(id);
        }
      }
    }
  }

  private static final class ServiceCImpl extends AptiVillaApp_HiltComponents.ServiceC {
    private final SingletonCImpl singletonCImpl;

    private final ServiceCImpl serviceCImpl = this;

    private ServiceCImpl(SingletonCImpl singletonCImpl, Service serviceParam) {
      this.singletonCImpl = singletonCImpl;


    }
  }

  private static final class SingletonCImpl extends AptiVillaApp_HiltComponents.SingletonC {
    private final ApplicationContextModule applicationContextModule;

    private final SingletonCImpl singletonCImpl = this;

    private Provider<UserPreferencesDataStore> userPreferencesDataStoreProvider;

    private Provider<PreferencesRepositoryImpl> preferencesRepositoryImplProvider;

    private Provider<AppDatabase> provideDatabaseProvider;

    private Provider<PracticeRepositoryImpl> practiceRepositoryImplProvider;

    private Provider<SecureKeyStore> secureKeyStoreProvider;

    private Provider<AiConfigRepository> aiConfigRepositoryProvider;

    private Provider<OkHttpClient> provideOkHttpClientProvider;

    private Provider<OpenAiCompatibleProvider> openAiCompatibleProvider;

    private Provider<GeminiProvider> geminiProvider;

    private Provider<AnthropicProvider> anthropicProvider;

    private Provider<AiProviderRegistry> aiProviderRegistryProvider;

    private Provider<AiContextBuilder> aiContextBuilderProvider;

    private Provider<AiConversationRepositoryImpl> aiConversationRepositoryImplProvider;

    private Provider<AnalyticsRepositoryImpl> analyticsRepositoryImplProvider;

    private Provider<BackupRepositoryImpl> backupRepositoryImplProvider;

    private Provider<TestRepositoryImpl> testRepositoryImplProvider;

    private Provider<UserProfileRepositoryImpl> userProfileRepositoryImplProvider;

    private Provider<ReviewRepositoryImpl> reviewRepositoryImplProvider;

    private Provider<ImportRepositoryImpl> importRepositoryImplProvider;

    private Provider<TaxonomyRepositoryImpl> taxonomyRepositoryImplProvider;

    private Provider<InventoryRepositoryImpl> inventoryRepositoryImplProvider;

    private Provider<TagRepositoryImpl> tagRepositoryImplProvider;

    private Provider<SearchRepositoryImpl> searchRepositoryImplProvider;

    private SingletonCImpl(ApplicationContextModule applicationContextModuleParam) {
      this.applicationContextModule = applicationContextModuleParam;
      initialize(applicationContextModuleParam);

    }

    private AttemptDao attemptDao() {
      return DatabaseModule_ProvideAttemptDaoFactory.provideAttemptDao(provideDatabaseProvider.get());
    }

    private AnswerDao answerDao() {
      return DatabaseModule_ProvideAnswerDaoFactory.provideAnswerDao(provideDatabaseProvider.get());
    }

    private AnswerSubmissionDao answerSubmissionDao() {
      return DatabaseModule_ProvideAnswerSubmissionDaoFactory.provideAnswerSubmissionDao(provideDatabaseProvider.get());
    }

    private HintUsageDao hintUsageDao() {
      return DatabaseModule_ProvideHintUsageDaoFactory.provideHintUsageDao(provideDatabaseProvider.get());
    }

    private QuestionDao questionDao() {
      return DatabaseModule_ProvideQuestionDaoFactory.provideQuestionDao(provideDatabaseProvider.get());
    }

    private QuestionOptionDao questionOptionDao() {
      return DatabaseModule_ProvideQuestionOptionDaoFactory.provideQuestionOptionDao(provideDatabaseProvider.get());
    }

    private HintDao hintDao() {
      return DatabaseModule_ProvideHintDaoFactory.provideHintDao(provideDatabaseProvider.get());
    }

    private TrickDao trickDao() {
      return DatabaseModule_ProvideTrickDaoFactory.provideTrickDao(provideDatabaseProvider.get());
    }

    private SolutionStepDao solutionStepDao() {
      return DatabaseModule_ProvideSolutionStepDaoFactory.provideSolutionStepDao(provideDatabaseProvider.get());
    }

    private QuestionTagDao questionTagDao() {
      return DatabaseModule_ProvideQuestionTagDaoFactory.provideQuestionTagDao(provideDatabaseProvider.get());
    }

    private SavedTrickDao savedTrickDao() {
      return DatabaseModule_ProvideSavedTrickDaoFactory.provideSavedTrickDao(provideDatabaseProvider.get());
    }

    private TopicDao topicDao() {
      return DatabaseModule_ProvideTopicDaoFactory.provideTopicDao(provideDatabaseProvider.get());
    }

    private TagDao tagDao() {
      return DatabaseModule_ProvideTagDaoFactory.provideTagDao(provideDatabaseProvider.get());
    }

    private MistakeDao mistakeDao() {
      return DatabaseModule_ProvideMistakeDaoFactory.provideMistakeDao(provideDatabaseProvider.get());
    }

    private RevisionDao revisionDao() {
      return DatabaseModule_ProvideRevisionDaoFactory.provideRevisionDao(provideDatabaseProvider.get());
    }

    private StudyDayDao studyDayDao() {
      return DatabaseModule_ProvideStudyDayDaoFactory.provideStudyDayDao(provideDatabaseProvider.get());
    }

    private AiConversationDao aiConversationDao() {
      return DatabaseModule_ProvideAiConversationDaoFactory.provideAiConversationDao(provideDatabaseProvider.get());
    }

    private AnalyticsDao analyticsDao() {
      return DatabaseModule_ProvideAnalyticsDaoFactory.provideAnalyticsDao(provideDatabaseProvider.get());
    }

    private SubjectDao subjectDao() {
      return DatabaseModule_ProvideSubjectDaoFactory.provideSubjectDao(provideDatabaseProvider.get());
    }

    private TestDao testDao() {
      return DatabaseModule_ProvideTestDaoFactory.provideTestDao(provideDatabaseProvider.get());
    }

    private TestQuestionDao testQuestionDao() {
      return DatabaseModule_ProvideTestQuestionDaoFactory.provideTestQuestionDao(provideDatabaseProvider.get());
    }

    private UserProfileDao userProfileDao() {
      return DatabaseModule_ProvideUserProfileDaoFactory.provideUserProfileDao(provideDatabaseProvider.get());
    }

    private ExamDao examDao() {
      return DatabaseModule_ProvideExamDaoFactory.provideExamDao(provideDatabaseProvider.get());
    }

    private SourceDao sourceDao() {
      return DatabaseModule_ProvideSourceDaoFactory.provideSourceDao(provideDatabaseProvider.get());
    }

    private SubTopicDao subTopicDao() {
      return DatabaseModule_ProvideSubTopicDaoFactory.provideSubTopicDao(provideDatabaseProvider.get());
    }

    private InventoryDao inventoryDao() {
      return DatabaseModule_ProvideInventoryDaoFactory.provideInventoryDao(provideDatabaseProvider.get());
    }

    private SearchDao searchDao() {
      return DatabaseModule_ProvideSearchDaoFactory.provideSearchDao(provideDatabaseProvider.get());
    }

    @SuppressWarnings("unchecked")
    private void initialize(final ApplicationContextModule applicationContextModuleParam) {
      this.userPreferencesDataStoreProvider = DoubleCheck.provider(new SwitchingProvider<UserPreferencesDataStore>(singletonCImpl, 1));
      this.preferencesRepositoryImplProvider = DoubleCheck.provider(new SwitchingProvider<PreferencesRepositoryImpl>(singletonCImpl, 0));
      this.provideDatabaseProvider = DoubleCheck.provider(new SwitchingProvider<AppDatabase>(singletonCImpl, 3));
      this.practiceRepositoryImplProvider = DoubleCheck.provider(new SwitchingProvider<PracticeRepositoryImpl>(singletonCImpl, 2));
      this.secureKeyStoreProvider = DoubleCheck.provider(new SwitchingProvider<SecureKeyStore>(singletonCImpl, 5));
      this.aiConfigRepositoryProvider = DoubleCheck.provider(new SwitchingProvider<AiConfigRepository>(singletonCImpl, 4));
      this.provideOkHttpClientProvider = DoubleCheck.provider(new SwitchingProvider<OkHttpClient>(singletonCImpl, 8));
      this.openAiCompatibleProvider = DoubleCheck.provider(new SwitchingProvider<OpenAiCompatibleProvider>(singletonCImpl, 7));
      this.geminiProvider = DoubleCheck.provider(new SwitchingProvider<GeminiProvider>(singletonCImpl, 9));
      this.anthropicProvider = DoubleCheck.provider(new SwitchingProvider<AnthropicProvider>(singletonCImpl, 10));
      this.aiProviderRegistryProvider = DoubleCheck.provider(new SwitchingProvider<AiProviderRegistry>(singletonCImpl, 6));
      this.aiContextBuilderProvider = DoubleCheck.provider(new SwitchingProvider<AiContextBuilder>(singletonCImpl, 11));
      this.aiConversationRepositoryImplProvider = DoubleCheck.provider(new SwitchingProvider<AiConversationRepositoryImpl>(singletonCImpl, 12));
      this.analyticsRepositoryImplProvider = DoubleCheck.provider(new SwitchingProvider<AnalyticsRepositoryImpl>(singletonCImpl, 13));
      this.backupRepositoryImplProvider = DoubleCheck.provider(new SwitchingProvider<BackupRepositoryImpl>(singletonCImpl, 14));
      this.testRepositoryImplProvider = DoubleCheck.provider(new SwitchingProvider<TestRepositoryImpl>(singletonCImpl, 15));
      this.userProfileRepositoryImplProvider = DoubleCheck.provider(new SwitchingProvider<UserProfileRepositoryImpl>(singletonCImpl, 16));
      this.reviewRepositoryImplProvider = DoubleCheck.provider(new SwitchingProvider<ReviewRepositoryImpl>(singletonCImpl, 17));
      this.importRepositoryImplProvider = DoubleCheck.provider(new SwitchingProvider<ImportRepositoryImpl>(singletonCImpl, 18));
      this.taxonomyRepositoryImplProvider = DoubleCheck.provider(new SwitchingProvider<TaxonomyRepositoryImpl>(singletonCImpl, 19));
      this.inventoryRepositoryImplProvider = DoubleCheck.provider(new SwitchingProvider<InventoryRepositoryImpl>(singletonCImpl, 20));
      this.tagRepositoryImplProvider = DoubleCheck.provider(new SwitchingProvider<TagRepositoryImpl>(singletonCImpl, 21));
      this.searchRepositoryImplProvider = DoubleCheck.provider(new SwitchingProvider<SearchRepositoryImpl>(singletonCImpl, 22));
    }

    @Override
    public void injectAptiVillaApp(AptiVillaApp aptiVillaApp) {
    }

    @Override
    public Set<Boolean> getDisableFragmentGetContextFix() {
      return Collections.<Boolean>emptySet();
    }

    @Override
    public ActivityRetainedComponentBuilder retainedComponentBuilder() {
      return new ActivityRetainedCBuilder(singletonCImpl);
    }

    @Override
    public ServiceComponentBuilder serviceComponentBuilder() {
      return new ServiceCBuilder(singletonCImpl);
    }

    private static final class SwitchingProvider<T> implements Provider<T> {
      private final SingletonCImpl singletonCImpl;

      private final int id;

      SwitchingProvider(SingletonCImpl singletonCImpl, int id) {
        this.singletonCImpl = singletonCImpl;
        this.id = id;
      }

      @SuppressWarnings("unchecked")
      @Override
      public T get() {
        switch (id) {
          case 0: // app.apti.villa.data.repository.PreferencesRepositoryImpl 
          return (T) new PreferencesRepositoryImpl(singletonCImpl.userPreferencesDataStoreProvider.get());

          case 1: // app.apti.villa.data.prefs.UserPreferencesDataStore 
          return (T) new UserPreferencesDataStore(ApplicationContextModule_ProvideContextFactory.provideContext(singletonCImpl.applicationContextModule));

          case 2: // app.apti.villa.data.repository.PracticeRepositoryImpl 
          return (T) new PracticeRepositoryImpl(singletonCImpl.provideDatabaseProvider.get(), singletonCImpl.attemptDao(), singletonCImpl.answerDao(), singletonCImpl.answerSubmissionDao(), singletonCImpl.hintUsageDao(), singletonCImpl.questionDao(), singletonCImpl.questionOptionDao(), singletonCImpl.hintDao(), singletonCImpl.trickDao(), singletonCImpl.solutionStepDao(), singletonCImpl.questionTagDao(), singletonCImpl.savedTrickDao(), singletonCImpl.topicDao(), singletonCImpl.tagDao(), singletonCImpl.mistakeDao(), singletonCImpl.revisionDao(), singletonCImpl.studyDayDao(), DispatcherModule_ProvideIoDispatcherFactory.provideIoDispatcher());

          case 3: // app.apti.villa.data.local.db.AppDatabase 
          return (T) DatabaseModule_ProvideDatabaseFactory.provideDatabase(ApplicationContextModule_ProvideContextFactory.provideContext(singletonCImpl.applicationContextModule));

          case 4: // app.apti.villa.ai.config.AiConfigRepository 
          return (T) new AiConfigRepository(ApplicationContextModule_ProvideContextFactory.provideContext(singletonCImpl.applicationContextModule), singletonCImpl.secureKeyStoreProvider.get());

          case 5: // app.apti.villa.ai.security.SecureKeyStore 
          return (T) new SecureKeyStore();

          case 6: // app.apti.villa.ai.provider.AiProviderRegistry 
          return (T) new AiProviderRegistry(singletonCImpl.openAiCompatibleProvider.get(), singletonCImpl.geminiProvider.get(), singletonCImpl.anthropicProvider.get());

          case 7: // app.apti.villa.ai.provider.OpenAiCompatibleProvider 
          return (T) new OpenAiCompatibleProvider(singletonCImpl.provideOkHttpClientProvider.get());

          case 8: // okhttp3.OkHttpClient 
          return (T) AiNetworkModule_ProvideOkHttpClientFactory.provideOkHttpClient();

          case 9: // app.apti.villa.ai.provider.GeminiProvider 
          return (T) new GeminiProvider(singletonCImpl.provideOkHttpClientProvider.get());

          case 10: // app.apti.villa.ai.provider.AnthropicProvider 
          return (T) new AnthropicProvider(singletonCImpl.provideOkHttpClientProvider.get());

          case 11: // app.apti.villa.ai.context.AiContextBuilder 
          return (T) new AiContextBuilder();

          case 12: // app.apti.villa.data.repository.AiConversationRepositoryImpl 
          return (T) new AiConversationRepositoryImpl(singletonCImpl.aiConversationDao(), singletonCImpl.questionDao(), singletonCImpl.topicDao(), DispatcherModule_ProvideIoDispatcherFactory.provideIoDispatcher());

          case 13: // app.apti.villa.data.repository.AnalyticsRepositoryImpl 
          return (T) new AnalyticsRepositoryImpl(singletonCImpl.analyticsDao(), singletonCImpl.studyDayDao(), singletonCImpl.topicDao(), singletonCImpl.subjectDao(), DispatcherModule_ProvideIoDispatcherFactory.provideIoDispatcher());

          case 14: // app.apti.villa.data.repository.BackupRepositoryImpl 
          return (T) new BackupRepositoryImpl(singletonCImpl.provideDatabaseProvider.get(), singletonCImpl.questionDao(), singletonCImpl.questionOptionDao(), singletonCImpl.hintDao(), singletonCImpl.trickDao(), singletonCImpl.solutionStepDao(), singletonCImpl.questionTagDao(), singletonCImpl.testDao(), singletonCImpl.testQuestionDao(), singletonCImpl.attemptDao(), singletonCImpl.answerDao(), singletonCImpl.answerSubmissionDao(), singletonCImpl.hintUsageDao(), singletonCImpl.mistakeDao(), singletonCImpl.revisionDao(), singletonCImpl.studyDayDao(), singletonCImpl.savedTrickDao(), singletonCImpl.userProfileDao(), singletonCImpl.subjectDao(), singletonCImpl.topicDao(), singletonCImpl.examDao(), DispatcherModule_ProvideIoDispatcherFactory.provideIoDispatcher());

          case 15: // app.apti.villa.data.repository.TestRepositoryImpl 
          return (T) new TestRepositoryImpl(singletonCImpl.provideDatabaseProvider.get(), singletonCImpl.testDao(), singletonCImpl.testQuestionDao(), singletonCImpl.attemptDao(), singletonCImpl.answerDao(), singletonCImpl.answerSubmissionDao(), singletonCImpl.questionDao(), singletonCImpl.questionOptionDao(), singletonCImpl.hintDao(), singletonCImpl.trickDao(), singletonCImpl.solutionStepDao(), singletonCImpl.examDao(), singletonCImpl.mistakeDao(), singletonCImpl.revisionDao(), singletonCImpl.studyDayDao(), DispatcherModule_ProvideIoDispatcherFactory.provideIoDispatcher());

          case 16: // app.apti.villa.data.repository.UserProfileRepositoryImpl 
          return (T) new UserProfileRepositoryImpl(singletonCImpl.userProfileDao());

          case 17: // app.apti.villa.data.repository.ReviewRepositoryImpl 
          return (T) new ReviewRepositoryImpl(singletonCImpl.provideDatabaseProvider.get(), singletonCImpl.mistakeDao(), singletonCImpl.revisionDao(), singletonCImpl.questionDao(), singletonCImpl.questionOptionDao(), singletonCImpl.hintDao(), singletonCImpl.trickDao(), singletonCImpl.solutionStepDao(), singletonCImpl.questionTagDao(), singletonCImpl.savedTrickDao(), singletonCImpl.tagDao(), singletonCImpl.attemptDao(), singletonCImpl.answerDao(), DispatcherModule_ProvideIoDispatcherFactory.provideIoDispatcher());

          case 18: // app.apti.villa.data.importer.ImportRepositoryImpl 
          return (T) new ImportRepositoryImpl(singletonCImpl.provideDatabaseProvider.get(), singletonCImpl.sourceDao(), singletonCImpl.testDao(), singletonCImpl.questionDao(), singletonCImpl.questionOptionDao(), singletonCImpl.testQuestionDao(), singletonCImpl.hintDao(), singletonCImpl.trickDao(), singletonCImpl.solutionStepDao(), singletonCImpl.subjectDao(), singletonCImpl.topicDao(), singletonCImpl.subTopicDao());

          case 19: // app.apti.villa.data.repository.TaxonomyRepositoryImpl 
          return (T) new TaxonomyRepositoryImpl(singletonCImpl.examDao(), singletonCImpl.subjectDao(), singletonCImpl.topicDao(), singletonCImpl.subTopicDao());

          case 20: // app.apti.villa.data.repository.InventoryRepositoryImpl 
          return (T) new InventoryRepositoryImpl(singletonCImpl.inventoryDao(), singletonCImpl.testDao(), singletonCImpl.testQuestionDao(), singletonCImpl.examDao(), singletonCImpl.attemptDao(), DispatcherModule_ProvideIoDispatcherFactory.provideIoDispatcher());

          case 21: // app.apti.villa.data.repository.TagRepositoryImpl 
          return (T) new TagRepositoryImpl(singletonCImpl.tagDao());

          case 22: // app.apti.villa.data.repository.SearchRepositoryImpl 
          return (T) new SearchRepositoryImpl(singletonCImpl.searchDao(), DispatcherModule_ProvideIoDispatcherFactory.provideIoDispatcher());

          default: throw new AssertionError(id);
        }
      }
    }
  }
}
