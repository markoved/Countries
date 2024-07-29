package com.markoved.countries.data.local

class CountryLocalDataSource(private val dao: CountryDao) {

    fun getAllCountries(): List<LocalCountry> {
        return dao.getAll()
    }

    fun getCountries(name: String): List<LocalCountry> {
        return dao.findByName(name)
    }

    fun insertAllCountries(countries: List<LocalCountry>) {
        dao.insertAll(countries)
    }

    fun clear() {
        dao.clear()
    }
}