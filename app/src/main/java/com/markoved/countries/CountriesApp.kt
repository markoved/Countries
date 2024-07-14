package com.markoved.countries

import android.app.Application
import com.markoved.countries.di.appModule
import org.koin.core.context.startKoin

class CountriesApp : Application() {
    override fun onCreate() {
        super.onCreate()

        startKoin {
            modules(appModule)
        }
    }
}