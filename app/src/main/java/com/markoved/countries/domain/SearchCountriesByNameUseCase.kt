package com.markoved.countries.domain

import com.markoved.countries.domain.entity.Country

class SearchCountriesByNameUseCase(
    private val countryRepository: CountryRepository
) {
    suspend operator fun invoke(name: String): List<Country> {
        return if (name.isBlank()) {
            countryRepository.getAllCountries()
        } else {
            countryRepository.getCountriesByName(name)
        }
    }
}
