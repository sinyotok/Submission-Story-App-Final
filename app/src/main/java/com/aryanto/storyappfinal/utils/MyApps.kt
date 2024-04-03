package com.aryanto.storyappfinal.utils

import android.app.Application
import com.aryanto.storyappfinal.di.appModule
import com.aryanto.storyappfinal.di.repositoryModule
import com.aryanto.storyappfinal.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class MyApps : Application() {
    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidLogger(Level.ERROR)
            androidContext(this@MyApps)
            modules(appModule, viewModelModule, repositoryModule)
        }
    }
}