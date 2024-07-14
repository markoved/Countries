package com.markoved.countries

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.markoved.countries.data.entity.Country
import com.markoved.countries.data.repository.CountryRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class MainActivityViewModel(
    private val countryRepository: CountryRepository
) : ViewModel() {

    private val _countries: MutableList<Country> = mutableStateListOf()
    val countries: List<Country>
        get() = _countries

    private var _searchText: String by mutableStateOf("")
    val searchText: String
        get() = _searchText

    private var searchJob: Job? = null

    init {
        viewModelScope.launch(Dispatchers.IO) {
            _countries.addAll(countryRepository.getAllCountries())
        }
    }

    fun onSearchTextChanged(text: String) {
        _searchText = text
        searchJob?.cancel()
        searchJob = viewModelScope.launch(Dispatchers.IO) {
            val result = if (text.isEmpty()) {
                countryRepository.getAllCountries()
            } else {
                countryRepository.getCountries(text)
            }
            _countries.clear()
            _countries.addAll(result)
        }
    }
}