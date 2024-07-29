package com.markoved.countries.data.network

import retrofit2.http.GET
import retrofit2.http.Path

interface CountryApiService {
    @GET("all")
    suspend fun getAllCountries(): List<RemoteCountry>

    @GET("name/{name}")
    suspend fun getCountriesByName(@Path("name") name: String): List<RemoteCountry>
}
