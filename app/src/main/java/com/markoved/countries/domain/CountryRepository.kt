package com.markoved.countries.domain

import com.markoved.countries.domain.entity.Country

interface CountryRepository {
    suspend fun getAllCountries(): List<Country>
    suspend fun getCountriesByName(name: String): List<Country>
}
