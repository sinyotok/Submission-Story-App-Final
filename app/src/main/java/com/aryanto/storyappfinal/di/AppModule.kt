package com.aryanto.storyappfinal.di

import com.aryanto.storyappfinal.core.network.ApiClient
import org.koin.dsl.module

val appModule = module {
    single { ApiClient.getApiService() }
}