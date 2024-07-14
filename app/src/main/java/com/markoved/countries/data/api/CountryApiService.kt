package com.markoved.countries.data.api

import com.markoved.countries.data.entity.Country
import retrofit2.http.GET
import retrofit2.http.Path

interface CountryApiService {
    @GET("all")
    suspend fun getAllCountries(): List<Country>

    @GET("name/{name}")
    suspend fun getCountriesByName(@Path("name") name: String): List<Country>
}