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
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class CountryRepositoryImpl(
    private val localDataSource: CountryLocalDataSource,
    private val remoteDataSource: CountryRemoteDataSource,
    private val remoteCountryToDomainMapper: RemoteCountryToDomainMapper,
    private val localCountryToDomainMapper: LocalCountryToDomainMapper,
    private val remoteCountryToLocalCountryMapper: RemoteCountryToLocalCountryMapper,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
): CountryRepository {

    private var coroutineScope = CoroutineScope(ioDispatcher)
    private var cacheJob: Job? = null

    override suspend fun getAllCountries(): List<Country> = withContext(ioDispatcher) {
        val remoteData = remoteDataSource.getAllCountries()
        if (remoteData.isNotEmpty()) {
            saveToDatabase(remoteData.map(remoteCountryToLocalCountryMapper))
            return@withContext remoteData.map(remoteCountryToDomainMapper)
        }
        localDataSource.getAllCountries().map(localCountryToDomainMapper)
    }

    override suspend fun getCountries(name: String): List<Country> = withContext(ioDispatcher) {
        val remoteData = remoteDataSource.getCountries(name).map(remoteCountryToDomainMapper)
        if (remoteData.isNotEmpty()) {
            return@withContext remoteData
        }
        localDataSource.getCountries(name).map(localCountryToDomainMapper)
    }

    private fun saveToDatabase(countries: List<LocalCountry>) {
        cacheJob?.cancel()
        cacheJob = coroutineScope.launch {
            localDataSource.clear()
            localDataSource.insertAllCountries(countries)
        }
    }

    companion object {
        private const val TAG = "CountryRepositoryImpl"
    }
}