package com.markoved.countries.ui

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.markoved.countries.data.entity.Country

@Composable
fun CountryList(
    data: List<Country>,
    onItemClick: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier
    ) {
        items(data) { item ->
            CountryElement(
                name = item.name.common,
                onClick = onItemClick
            )
        }
    }
}
