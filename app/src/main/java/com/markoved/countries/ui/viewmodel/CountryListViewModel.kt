package com.markoved.countries.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.markoved.countries.domain.entity.Country
import com.markoved.countries.domain.GetAllCountriesUseCase
import com.markoved.countries.domain.SearchCountriesByNameUseCase
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class CountryListUIState(
    val countries: List<Country>,
    val searchText: String
)

class CountryListViewModel(
    private val getAllCountriesUseCase: GetAllCountriesUseCase,
    private val searchCountriesByNameUseCase: SearchCountriesByNameUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(
        CountryListUIState(
            countries = emptyList(),
            searchText = ""
        )
    )
    val uiState: StateFlow<CountryListUIState> = _uiState.asStateFlow()

    private var searchJob: Job? = null

    init {
        viewModelScope.launch {
            _uiState.update {
                it.copy(
                    countries = getAllCountriesUseCase()
                )
            }
        }
    }

    fun onSearchTextChanged(text: String) {
        _uiState.update {
            it.copy(
                searchText = text
            )
        }
        searchJob?.cancel()
        searchJob = viewModelScope.launch {
            delay(SEARCH_DEBOUNCE)
            val result = searchCountriesByNameUseCase(text)
            _uiState.update {
                it.copy(
                    countries = result
                )
            }
        }
    }

    companion object {
        private const val SEARCH_DEBOUNCE = 500L
    }
}
