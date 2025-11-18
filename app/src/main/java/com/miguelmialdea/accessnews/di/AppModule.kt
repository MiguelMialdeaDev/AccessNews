package com.miguelmialdea.accessnews.di

import com.miguelmialdea.accessnews.core.accessibility.AccessibilityPreferences
import com.miguelmialdea.accessnews.data.local.DatabaseDriverFactory
import com.miguelmialdea.accessnews.data.local.LocalDataSource
import com.miguelmialdea.accessnews.data.remote.KtorClientFactory
import com.miguelmialdea.accessnews.data.remote.RemoteDataSource
import com.miguelmialdea.accessnews.data.remote.RssParser
import com.miguelmialdea.accessnews.data.repository.ArticleRepositoryImpl
import com.miguelmialdea.accessnews.data.repository.FeedRepositoryImpl
import com.miguelmialdea.accessnews.database.AccessNewsDatabase
import com.miguelmialdea.accessnews.domain.repository.ArticleRepository
import com.miguelmialdea.accessnews.domain.repository.FeedRepository
import com.miguelmialdea.accessnews.presentation.articles.ArticlesViewModel
import com.miguelmialdea.accessnews.presentation.feeds.FeedsViewModel
import com.miguelmialdea.accessnews.presentation.reader.ReaderViewModel
import com.miguelmialdea.accessnews.presentation.settings.SettingsViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

/**
 * Koin dependency injection module
 */
val appModule = module {

    // Database
    single {
        DatabaseDriverFactory(androidContext()).createDriver()
    }

    single {
        AccessNewsDatabase(get())
    }

    single {
        LocalDataSource(get())
    }

    // Network
    single {
        KtorClientFactory.create()
    }

    single {
        RssParser()
    }

    single {
        RemoteDataSource(get(), get())
    }

    // Repositories
    single<FeedRepository> {
        FeedRepositoryImpl(get(), get())
    }

    single<ArticleRepository> {
        ArticleRepositoryImpl(get(), get())
    }

    // Accessibility
    single {
        AccessibilityPreferences(androidContext())
    }

    // Use Cases
    factory {
        com.miguelmialdea.accessnews.domain.usecase.GetSampleFeedsUseCase()
    }

    factory {
        com.miguelmialdea.accessnews.domain.usecase.GetSampleArticlesUseCase()
    }

    factory {
        com.miguelmialdea.accessnews.domain.usecase.InitializeSampleDataUseCase(get(), get(), get(), get())
    }

    // ViewModels
    viewModel {
        FeedsViewModel(get(), get())
    }

    viewModel {
        ArticlesViewModel(get(), get())
    }

    viewModel {
        ReaderViewModel(get())
    }

    viewModel {
        SettingsViewModel(get())
    }
}
