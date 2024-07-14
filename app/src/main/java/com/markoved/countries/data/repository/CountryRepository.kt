package com.markoved.countries.data.repository

import com.markoved.countries.data.api.CountriesApiService
import com.markoved.countries.data.entity.Country
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

interface CountryRepository {
    suspend fun getCountries(): List<Country>
}
class CountryRepositoryImpl(
    private val countriesApiService: CountriesApiService,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
): CountryRepository {
    override suspend fun getCountries(): List<Country> = withContext(ioDispatcher) {
        return@withContext countriesApiService.getCountries()
    }
}