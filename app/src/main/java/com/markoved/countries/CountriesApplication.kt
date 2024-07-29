package com.markoved.countries

import android.app.Application
import com.markoved.countries.di.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class CountriesApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@CountriesApplication)
            modules(appModule)
        }
    }
}