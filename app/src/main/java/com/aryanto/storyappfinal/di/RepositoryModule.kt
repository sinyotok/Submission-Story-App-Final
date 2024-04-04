package com.aryanto.storyappfinal.di

import com.aryanto.storyappfinal.core.repo.AppRepository
import org.koin.dsl.module

val repositoryModule = module {
    single { AppRepository(get()) }
}