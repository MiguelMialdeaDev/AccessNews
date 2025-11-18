package com.miguelmialdea.accessnews

import android.app.Application
import com.miguelmialdea.accessnews.di.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

/**
 * Application class for AccessNews
 * Initializes Koin dependency injection
 */
class AccessNewsApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        // Start Koin
        startKoin {
            androidLogger(Level.ERROR)
            androidContext(this@AccessNewsApplication)
            modules(appModule)
        }
    }
}
