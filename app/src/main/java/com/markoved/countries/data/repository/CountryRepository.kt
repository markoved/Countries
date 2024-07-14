package com.markoved.countries.data.repository

import android.util.Log
import com.markoved.countries.data.api.CountriesApiService
import com.markoved.countries.data.entity.Country
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.lang.Exception

interface CountryRepository {
    suspend fun getAllCountries(): List<Country>
    suspend fun getCountries(name: String): List<Country>
}
class CountryRepositoryImpl(
    private val countriesApiService: CountriesApiService,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
): CountryRepository {
    override suspend fun getAllCountries(): List<Country> = withContext(ioDispatcher) {
        try {
            return@withContext countriesApiService.getAllCountries()
        } catch (e: CancellationException) {
            throw e
        } catch (e: Exception) {
            // TODO: Handle IOException and HttpException separately
            Log.w(TAG, "Error fetching countries", e)
            return@withContext emptyList()
        }
    }

    override suspend fun getCountries(name: String): List<Country> = withContext(ioDispatcher) {
        try {
            return@withContext countriesApiService.getCountries(name)
        } catch (e: CancellationException) {
            throw e
        } catch (e: Exception) {
            // TODO: Handle IOException and HttpException separately
            Log.w(TAG, "Error fetching countries", e)
            return@withContext emptyList()
        }
    }

    companion object {
        private const val TAG = "CountryRepositoryImpl"
    }
}