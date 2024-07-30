package com.markoved.countries.domain

class GetAllCountriesUseCase(
    private val countryRepository: CountryRepository
) {
    suspend operator fun invoke(): List<Country> {
        return countryRepository.getAllCountries()
    }
}
