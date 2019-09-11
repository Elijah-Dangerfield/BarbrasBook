package com.dangerfield.barbrasbook.di

import android.app.Application
import com.dangerfield.barbrasbook.networking.Repository
import com.dangerfield.barbrasbook.viewModel.MainViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.core.context.startKoin
import org.koin.dsl.module

class KoinApp : Application(){
    override fun onCreate() {
        super.onCreate()

        // Start Koin
        startKoin{
            androidLogger()
            androidContext(this@KoinApp)
            modules(viewModelModule)
        }
    }
}