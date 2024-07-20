package com.markoved.countries

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.markoved.countries.data.entity.Country
import com.markoved.countries.domain.GetCountriesUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
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
    private val getCountriesUseCase: GetCountriesUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(CountryListUIState(
        countries = emptyList(),
        searchText = ""
    ))
    val uiState: StateFlow<CountryListUIState> = _uiState.asStateFlow()

    private var searchJob: Job? = null

    init {
        viewModelScope.launch(Dispatchers.IO) {
            _uiState.update {
                it.copy(
                    countries = getCountriesUseCase(),
                    searchText = it.searchText
                )
            }
        }
    }

    fun onSearchTextChanged(text: String) {
        _uiState.update {
            it.copy(
                countries = it.countries,
                searchText = text
            )
        }
        searchJob?.cancel()
        searchJob = viewModelScope.launch(Dispatchers.IO) {
            val result = getCountriesUseCase(text)
            _uiState.update {
                it.copy(
                    countries = result,
                    searchText = it.searchText
                )
            }
        }
    }
}