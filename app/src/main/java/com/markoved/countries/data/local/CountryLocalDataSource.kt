package com.markoved.countries.data.local

class CountryLocalDataSource(private val dao: CountryDao) {

    suspend fun getAllCountries(): List<LocalCountry> {
        return dao.getAll()
    }

    suspend fun getCountriesByName(name: String): List<LocalCountry> {
        return dao.findByName(name)
    }

    suspend fun insertAllCountries(countries: List<LocalCountry>) {
        dao.insertAll(countries)
    }

    suspend fun clear() {
        dao.clear()
    }
}
