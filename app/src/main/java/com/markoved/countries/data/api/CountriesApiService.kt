package com.markoved.countries.data.api

import com.markoved.countries.data.entity.Country
import retrofit2.http.GET

interface CountriesApiService {
    @GET("all")
    suspend fun getCountries(): List<Country>
}