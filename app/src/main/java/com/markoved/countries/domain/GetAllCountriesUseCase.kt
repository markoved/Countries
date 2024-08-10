package com.markoved.countries.domain

import com.markoved.countries.domain.entity.Country

class GetAllCountriesUseCase(
    private val countryRepository: CountryRepository
) {
    suspend operator fun invoke(): List<Country> {
        return countryRepository.getAllCountries()
    }
}
