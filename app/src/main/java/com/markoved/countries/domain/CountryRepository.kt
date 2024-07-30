package com.markoved.countries.domain

interface CountryRepository {
    suspend fun getAllCountries(): List<Country>
    suspend fun getCountriesByName(name: String): List<Country>
}
