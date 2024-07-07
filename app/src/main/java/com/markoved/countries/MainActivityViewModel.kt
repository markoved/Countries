package com.markoved.countries

import androidx.compose.runtime.toMutableStateList
import androidx.lifecycle.ViewModel
import com.markoved.countries.data.entity.Country
import com.markoved.countries.data.repository.CountryRepository

class MainActivityViewModel(
    private val countryRepository: CountryRepository = CountryRepository()
) : ViewModel() {

    private val _countries = countryRepository.getCountries().toMutableStateList()
    val countries: List<Country>
        get() = _countries
}