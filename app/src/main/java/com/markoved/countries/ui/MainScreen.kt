package com.markoved.countries.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.markoved.countries.MainActivityViewModel

@Composable
fun MainScreen(
    modifier: Modifier = Modifier,
    mainActivityViewModel: MainActivityViewModel = viewModel()
) {
    Column {
        Spacer(Modifier.height(16.dp))
        SearchBar(Modifier.padding(horizontal = 16.dp))
        Spacer(Modifier.height(16.dp))
        CountryList(data = mainActivityViewModel.countries)
        Spacer(Modifier.height(16.dp))
    }
}
