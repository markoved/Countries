package com.markoved.countries.di

import androidx.room.Room
import com.markoved.countries.CountryListViewModel
import com.markoved.countries.data.network.CountryApiService
import com.markoved.countries.data.local.CountryDao
import com.markoved.countries.data.local.CountryDatabase
import com.markoved.countries.data.local.CountryLocalDataSource
import com.markoved.countries.data.network.CountryRemoteDataSource
import com.markoved.countries.data.mapper.RemoteCountryToDomainMapper
import com.markoved.countries.data.CountryRepositoryImpl
import com.markoved.countries.data.mapper.RemoteCountryToLocalCountryMapper
import com.markoved.countries.data.mapper.LocalCountryToDomainMapper
import com.markoved.countries.domain.CountryRepository
import com.markoved.countries.domain.GetAllCountriesUseCase
import com.markoved.countries.domain.SearchCountriesByNameUseCase
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

private const val REST_COUNTRIES_BASE_URL = "https://restcountries.com/v3.1/"
private const val DATABASE_NAME = "countries"

val appModule = module {

    single<CountryApiService> {
        val retrofit = Retrofit.Builder()
            .baseUrl(REST_COUNTRIES_BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        retrofit.create(CountryApiService::class.java)
    }

    single<CountryRemoteDataSource> { CountryRemoteDataSource(get()) }

    single<CountryDatabase> {
        Room.databaseBuilder(
            androidContext(),
            CountryDatabase::class.java,
            DATABASE_NAME
        ).build()
    }

    single<CountryDao> {
        get<CountryDatabase>().countryDao()
    }

    single<CountryLocalDataSource> { CountryLocalDataSource(get()) }

    single<RemoteCountryToDomainMapper> { RemoteCountryToDomainMapper() }

    single<LocalCountryToDomainMapper> { LocalCountryToDomainMapper() }

    single<RemoteCountryToLocalCountryMapper> { RemoteCountryToLocalCountryMapper() }

    single<CountryRepository> { CountryRepositoryImpl(get(), get(), get(), get(), get()) }

    single<GetAllCountriesUseCase> { GetAllCountriesUseCase(get()) }

    single<SearchCountriesByNameUseCase> { SearchCountriesByNameUseCase(get()) }

    viewModel { CountryListViewModel(get(), get()) }
}
