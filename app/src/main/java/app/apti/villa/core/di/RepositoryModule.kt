package app.apti.villa.core.di

import app.apti.villa.data.repository.PreferencesRepositoryImpl
import app.apti.villa.data.repository.TagRepositoryImpl
import app.apti.villa.data.repository.TaxonomyRepositoryImpl
import app.apti.villa.data.repository.UserProfileRepositoryImpl
import app.apti.villa.domain.repository.PreferencesRepository
import app.apti.villa.domain.repository.TagRepository
import app.apti.villa.domain.repository.TaxonomyRepository
import app.apti.villa.domain.repository.UserProfileRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindTaxonomyRepository(
        impl: TaxonomyRepositoryImpl,
    ): TaxonomyRepository

    @Binds
    @Singleton
    abstract fun bindTagRepository(
        impl: TagRepositoryImpl,
    ): TagRepository

    @Binds
    @Singleton
    abstract fun bindUserProfileRepository(
        impl: UserProfileRepositoryImpl,
    ): UserProfileRepository

    @Binds
    @Singleton
    abstract fun bindPreferencesRepository(
        impl: PreferencesRepositoryImpl,
    ): PreferencesRepository

    @Binds
    @Singleton
    abstract fun bindImportRepository(
        impl: app.apti.villa.data.importer.ImportRepositoryImpl,
    ): app.apti.villa.domain.importer.ImportRepository

    @Binds
    @Singleton
    abstract fun bindPracticeRepository(
        impl: app.apti.villa.data.repository.PracticeRepositoryImpl,
    ): app.apti.villa.domain.repository.PracticeRepository

    @Binds
    @Singleton
    abstract fun bindTestRepository(
        impl: app.apti.villa.data.repository.TestRepositoryImpl,
    ): app.apti.villa.domain.repository.TestRepository

    @Binds
    @Singleton
    abstract fun bindReviewRepository(
        impl: app.apti.villa.data.repository.ReviewRepositoryImpl,
    ): app.apti.villa.domain.repository.ReviewRepository

    @Binds
    @Singleton
    abstract fun bindAnalyticsRepository(
        impl: app.apti.villa.data.repository.AnalyticsRepositoryImpl,
    ): app.apti.villa.domain.repository.AnalyticsRepository

    @Binds
    @Singleton
    abstract fun bindInventoryRepository(
        impl: app.apti.villa.data.repository.InventoryRepositoryImpl,
    ): app.apti.villa.domain.repository.InventoryRepository

    @Binds
    @Singleton
    abstract fun bindSearchRepository(
        impl: app.apti.villa.data.repository.SearchRepositoryImpl,
    ): app.apti.villa.domain.repository.SearchRepository

    @Binds
    @Singleton
    abstract fun bindBackupRepository(
        impl: app.apti.villa.data.repository.BackupRepositoryImpl,
    ): app.apti.villa.domain.repository.BackupRepository

    @Binds
    @Singleton
    abstract fun bindAiConversationRepository(
        impl: app.apti.villa.data.repository.AiConversationRepositoryImpl,
    ): app.apti.villa.domain.repository.AiConversationRepository
}



