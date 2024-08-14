package com.markoved.countries.ui

import com.markoved.countries.domain.entity.Country

sealed class CountriesUIState {
    data object Loading: CountriesUIState()
    data class Ready(
        val countries: List<Country>,
        val searchText: String = ""
    ): CountriesUIState()
}
