package com.markoved.countries.domain

class SearchCountriesByNameUseCase(
    private val countryRepository: CountryRepository
) {
    suspend operator fun invoke(name: String): List<Country> {
        if (name.isBlank()) {
            return countryRepository.getAllCountries()
        }
        return countryRepository.getCountriesByName(name)
    }
}
