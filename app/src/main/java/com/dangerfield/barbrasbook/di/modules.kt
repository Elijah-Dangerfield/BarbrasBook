package com.dangerfield.barbrasbook.di

import com.dangerfield.barbrasbook.api.Repository
import com.dangerfield.barbrasbook.viewModel.MainViewModel
import org.koin.android.ext.koin.androidApplication
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {

    // singleton of the barbra repository
    single { Repository("barbra streisand", androidApplication()) }

    // create the viewModel with the repository dependency
    viewModel { MainViewModel(get()) }
}