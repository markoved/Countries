package com.markoved.countries.ui

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.markoved.countries.data.entity.Country
import com.markoved.countries.data.repository.CountryRepository
import com.markoved.countries.data.repository.CountryRepositoryImpl
import com.markoved.countries.ui.theme.CountriesTheme

@Composable
fun CountryList(
    data: List<Country>,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier
    ) {
        items(data) { item ->
            CountryElement(item.name.common)
        }
    }
}
