package com.aryanto.storyappfinal.di

import com.aryanto.storyappfinal.core.data.network.ApiClient
import com.aryanto.storyappfinal.utils.TokenManager
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val appModule = module {
    single { ApiClient.getApiService() }
    single { TokenManager.getInstance(androidContext()) }
}