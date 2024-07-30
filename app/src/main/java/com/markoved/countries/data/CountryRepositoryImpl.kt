package com.markoved.countries.data

import com.markoved.countries.data.local.CountryLocalDataSource
import com.markoved.countries.data.local.LocalCountry
import com.markoved.countries.data.mapper.LocalCountryToDomainMapper
import com.markoved.countries.data.mapper.RemoteCountryToLocalCountryMapper
import com.markoved.countries.data.network.CountryRemoteDataSource
import com.markoved.countries.data.mapper.RemoteCountryToDomainMapper
import com.markoved.countries.domain.Country
import com.markoved.countries.domain.CountryRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class CountryRepositoryImpl(
    private val localDataSource: CountryLocalDataSource,
    private val remoteDataSource: CountryRemoteDataSource,
    private val remoteCountryToDomainMapper: RemoteCountryToDomainMapper,
    private val localCountryToDomainMapper: LocalCountryToDomainMapper,
    private val remoteCountryToLocalCountryMapper: RemoteCountryToLocalCountryMapper,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
): CountryRepository {

    override suspend fun getAllCountries(): List<Country> = withContext(ioDispatcher) {
        val remoteData = remoteDataSource.getAllCountries()
        if (remoteData.isNotEmpty()) {
            updateLocalData(remoteData.map(remoteCountryToLocalCountryMapper))
            return@withContext remoteData.map(remoteCountryToDomainMapper)
        }
        localDataSource.getAllCountries().map(localCountryToDomainMapper)
    }

    override suspend fun getCountriesByName(name: String): List<Country> = withContext(ioDispatcher) {
        val remoteData = remoteDataSource.getCountries(name).map(remoteCountryToDomainMapper)
        if (remoteData.isNotEmpty()) {
            return@withContext remoteData
        }
        localDataSource.getCountriesByName(name).map(localCountryToDomainMapper)
    }

    private suspend fun updateLocalData(countries: List<LocalCountry>) {
        localDataSource.clear()
        localDataSource.insertAllCountries(countries)
    }

    companion object {
        private const val TAG = "CountryRepositoryImpl"
    }
}
