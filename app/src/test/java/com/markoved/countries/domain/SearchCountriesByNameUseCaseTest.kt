package com.markoved.countries.domain

import kotlinx.coroutines.test.runTest
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.kotlin.verify

class SearchCountriesByNameUseCaseTest {

    private val countryRepository = mock<CountryRepository>()

    private val useCase = SearchCountriesByNameUseCase(countryRepository)

    @Test
    fun useCaseCalledWithEmptyArgument_getAllCountriesCalled() = runTest {
        useCase.invoke("")
        verify(countryRepository).getAllCountries()
    }

    @Test
    fun useCaseCalledWithBlankArgument_getAllCountriesCalled() = runTest {
        useCase.invoke("   ")
        verify(countryRepository).getAllCountries()
    }

    @Test
    fun useCaseCalledWithNotEmptyArgument_getAllCountriesCalled() = runTest {
        val argument = "Nether"
        useCase.invoke(argument)
        verify(countryRepository).getCountriesByName(argument)
    }
}
