package com.markoved.countries.ui

import com.markoved.countries.domain.GetAllCountriesUseCase
import com.markoved.countries.domain.SearchCountriesByNameUseCase
import com.markoved.countries.mockdata.getAllCountriesMockData
import com.markoved.countries.mockdata.searchCountriesMockData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.mockito.ArgumentMatchers.anyString
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify
import org.mockito.Mockito.`when`

@OptIn(ExperimentalCoroutinesApi::class)
class CountriesViewModelTest {

    private val getAllCountriesUseCaseMock = mock<GetAllCountriesUseCase>()
    private val searchCountriesByNameUseCaseMock = mock<SearchCountriesByNameUseCase>()

    private lateinit var viewModel: CountriesViewModel

    @Before
    fun setup() = runTest {
        Dispatchers.setMain(StandardTestDispatcher())
        `when`(getAllCountriesUseCaseMock.invoke()).thenReturn(getAllCountriesMockData)
        `when`(searchCountriesByNameUseCaseMock.invoke(anyString()))
            .thenReturn(searchCountriesMockData)
        viewModel = CountriesViewModel(getAllCountriesUseCaseMock, searchCountriesByNameUseCaseMock)
    }

    @Test
    fun viewModelInitialized_GetAllCountriesUseCaseCalled() = runTest {
        verify(getAllCountriesUseCaseMock).invoke()
    }

    @Test
    fun viewModelInitialized_UiStateUpdatedCorrectly() = runTest {
        assertEquals(
            CountriesUIState.Ready(getAllCountriesMockData, ""),
            viewModel.uiState.value
        )
    }

    @Test
    fun searchTextChanged_SearchCountriesByNameUseCaseCalled() = runTest {
        val searchText = "Nether"
        viewModel.onSearchTextChanged(searchText)
        advanceUntilIdle()
        verify(searchCountriesByNameUseCaseMock).invoke(searchText)
    }

    @Test
    fun searchTextChanged_UiStateUpdatedCorrectly() = runTest {
        val searchText = "Germ"
        viewModel.onSearchTextChanged(searchText)
        advanceUntilIdle()
        assertEquals(
            CountriesUIState.Ready(searchCountriesMockData, searchText),
            viewModel.uiState.value
        )
    }
}
