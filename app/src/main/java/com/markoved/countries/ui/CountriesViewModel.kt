package com.markoved.countries.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.markoved.countries.domain.GetAllCountriesUseCase
import com.markoved.countries.domain.SearchCountriesByNameUseCase
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class CountriesViewModel(
    private val getAllCountriesUseCase: GetAllCountriesUseCase,
    private val searchCountriesByNameUseCase: SearchCountriesByNameUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow<CountriesUIState>(CountriesUIState.Loading)
    val uiState: StateFlow<CountriesUIState> = _uiState.asStateFlow()

    private var searchJob: Job? = null

    init {
        viewModelScope.launch {
            val allCountries = getAllCountriesUseCase()
            _uiState.value = CountriesUIState.Ready(
                countries = allCountries,
                searchText = ""
            )
        }
    }

    fun onSearchTextChanged(text: String) {
        _uiState.update {
            (it as? CountriesUIState.Ready)?.copy(
                searchText = text
            ) ?: CountriesUIState.Loading
        }
        searchJob?.cancel()
        searchJob = viewModelScope.launch {
            delay(SEARCH_DEBOUNCE)
            val result = searchCountriesByNameUseCase(text)
            _uiState.update {
                (it as? CountriesUIState.Ready)?.copy(
                    countries = result
                ) ?: CountriesUIState.Loading
            }
        }
    }

    companion object {
        private const val SEARCH_DEBOUNCE = 500L
    }
}
