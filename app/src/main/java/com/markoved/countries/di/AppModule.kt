package com.markoved.countries.di

import com.markoved.countries.MainActivityViewModel
import com.markoved.countries.data.api.CountriesApiService
import com.markoved.countries.data.repository.CountryRepository
import com.markoved.countries.data.repository.CountryRepositoryImpl
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

private const val REST_COUNTRIES_BASE_URL = "https://restcountries.com/v3.1/"

val appModule = module {

    single<CountriesApiService> {
        val retrofit = Retrofit.Builder()
            .baseUrl(REST_COUNTRIES_BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        retrofit.create(CountriesApiService::class.java)
    }

    single<CountryRepository> { CountryRepositoryImpl(get()) }

    viewModel { MainActivityViewModel(get()) }
}