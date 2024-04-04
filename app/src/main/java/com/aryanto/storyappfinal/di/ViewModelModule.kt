package com.aryanto.storyappfinal.di

import com.aryanto.storyappfinal.ui.activity.auth.login.LoginVM
import com.aryanto.storyappfinal.ui.activity.auth.register.RegisterVM
import com.aryanto.storyappfinal.ui.activity.detail.DetailVM
import com.aryanto.storyappfinal.ui.activity.home.HomeVM
import com.aryanto.storyappfinal.ui.activity.upload.UploadVM
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel { LoginVM(get()) }
    viewModel { RegisterVM(get()) }
    viewModel { HomeVM(get(), get()) }
    viewModel { DetailVM(get()) }
    viewModel { UploadVM(get()) }
}