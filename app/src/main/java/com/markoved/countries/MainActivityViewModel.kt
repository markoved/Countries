package com.markoved.countries

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.toMutableStateList
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.markoved.countries.data.entity.Country
import com.markoved.countries.data.repository.CountryRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainActivityViewModel(
    private val countryRepository: CountryRepository
) : ViewModel() {

    private val _countries: MutableList<Country> = mutableStateListOf()
    val countries: List<Country>
        get() = _countries

    init {
        viewModelScope.launch(Dispatchers.IO) {
            _countries.addAll(countryRepository.getCountries())
        }
    }
}