package com.markoved.countries.domain

import com.markoved.countries.data.entity.Country

interface CountryRepository {
    suspend fun getAllCountries(): List<Country>
    suspend fun getCountries(name: String): List<Country>
}