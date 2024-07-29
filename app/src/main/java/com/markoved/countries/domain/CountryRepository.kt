package com.markoved.countries.domain

interface CountryRepository {
    suspend fun getAllCountries(): List<Country>
    suspend fun getCountries(name: String): List<Country>
}