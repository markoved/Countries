package com.markoved.countries.data.repository

import com.markoved.countries.data.entity.Country

class CountryRepository {
    fun getCountries(): List<Country> {
        return mockData
    }

    companion object {
        val mockData = listOf(
            Country("Sweden"),
            Country("Russia"),
            Country("Serbia"),
            Country("USA"),
            Country("Germany"),
            Country("France"),
            Country("Japan"),
            Country("South Korea"),
            Country("North Korea"),
            Country("Bulgaria"),
            Country("Canada"),
            Country("Finland"),
            Country("Norway"),
            Country("Ukraine"),
            Country("Belarus"),
            Country("China"),
            Country("India"),
            Country("Mexico"),
            Country("Belgium"),
            Country("Denmark"),
        )
    }
}