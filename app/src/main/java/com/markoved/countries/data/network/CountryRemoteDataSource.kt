package com.markoved.countries.data.network

import android.util.Log
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.lang.Exception
import kotlin.coroutines.cancellation.CancellationException

class CountryRemoteDataSource(
    private val countryApiService: CountryApiService,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
) {
    suspend fun getAllCountries(): List<RemoteCountry> = withContext(ioDispatcher) {
        try {
            countryApiService.getAllCountries()
        } catch (e: CancellationException) {
            throw e
        } catch (e: Exception) {
            Log.e(TAG, "Error fetching countries", e)
            emptyList()
        }
    }

    suspend fun getCountries(name: String): List<RemoteCountry> = withContext(ioDispatcher) {
        try {
            countryApiService.getCountriesByName(name)
        } catch (e: CancellationException) {
            throw e
        } catch (e: Exception) {
            Log.e(TAG, "Error fetching countries", e)
            emptyList()
        }
    }

    companion object {
        private const val TAG = "CountryRemoteDataSource"
    }
}
