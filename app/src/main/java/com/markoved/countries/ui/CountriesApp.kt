package com.markoved.countries.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.markoved.countries.MainActivityViewModel

enum class CountriesScreen {
    List,
    Details
}

@Composable
fun CountriesApp(
    viewModel: MainActivityViewModel,
    navController: NavHostController = rememberNavController()
) {
    NavHost(
        navController = navController,
        startDestination = CountriesScreen.List.name,
        modifier = Modifier
    ) {
        composable(route = CountriesScreen.List.name) {
            CountryListScreen(
                mainActivityViewModel = viewModel,
                onCountryItemClick = { country ->
                    navController.navigate("${CountriesScreen.Details.name}/$country")
                }
            )
        }
        composable(route = "${CountriesScreen.Details.name}/{$KEY_COUNTRY_NAME}") { backStackEntry ->
            CountryDetailsScreen(backStackEntry.arguments?.getString(KEY_COUNTRY_NAME) ?: "")
        }
    }
}

@Composable
fun CountryListScreen(
    mainActivityViewModel: MainActivityViewModel,
    onCountryItemClick: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    Column {
        Spacer(Modifier.height(16.dp))
        SearchBar(
            mainActivityViewModel.searchText,
            { mainActivityViewModel.onSearchTextChanged(it) },
            Modifier.padding(horizontal = 16.dp)
        )
        Spacer(Modifier.height(16.dp))
        CountryList(
            data = mainActivityViewModel.countries,
            onItemClick = onCountryItemClick
        )
        Spacer(Modifier.height(16.dp))
    }
}

@Composable
fun CountryDetailsScreen(
    countryName: String,
    modifier: Modifier = Modifier
) {
    Text(text = countryName)
}

private const val KEY_COUNTRY_NAME = "countryName"