package com.aryanto.storyappfinal.di

import com.aryanto.storyappfinal.core.repo.AppRepository
import com.aryanto.storyappfinal.core.repo.AuthRepository
import org.koin.dsl.module

val repositoryModule = module {
    single { AuthRepository(get()) }
    single { AppRepository(get()) }
}