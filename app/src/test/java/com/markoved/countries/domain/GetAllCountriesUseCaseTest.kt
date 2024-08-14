package com.markoved.countries.domain

import kotlinx.coroutines.test.runTest
import org.junit.Test
import org.mockito.Mockito
import org.mockito.kotlin.verify

class GetAllCountriesUseCaseTest {

    private val countryRepository = Mockito.mock<CountryRepository>()

    private val useCase = GetAllCountriesUseCase(countryRepository)

    @Test
    fun useCaseCalledWithEmptyArgument_getAllCountriesCalled() = runTest {
        useCase.invoke()
        verify(countryRepository).getAllCountries()
    }
}
