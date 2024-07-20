package com.markoved.countries.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.markoved.countries.CountryListViewModel
import com.markoved.countries.R
import org.koin.androidx.compose.koinViewModel

enum class CountriesScreen {
    List,
    Details
}

@Composable
fun CountriesApp(
    navController: NavHostController = rememberNavController()
) {
    NavHost(
        navController = navController,
        startDestination = CountriesScreen.List.name,
        modifier = Modifier
    ) {
        composable(route = CountriesScreen.List.name) {
            CountryListScreen(
                onCountryItemClick = { country ->
                    navController.navigate("${CountriesScreen.Details.name}/$country")
                }
            )
        }
        composable(
            route = "${CountriesScreen.Details.name}/{$KEY_COUNTRY_NAME}",
            arguments = listOf(navArgument(KEY_COUNTRY_NAME) { type = NavType.StringType })
        ) { backStackEntry ->
            CountryDetailsScreen(backStackEntry.arguments?.getString(KEY_COUNTRY_NAME))
        }
    }
}

@Composable
fun CountryListScreen(
    onCountryItemClick: (String) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: CountryListViewModel = koinViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

    Column {
        Spacer(Modifier.height(16.dp))
        SearchBar(
            uiState.searchText,
            { viewModel.onSearchTextChanged(it) },
            Modifier.padding(horizontal = 16.dp)
        )
        Spacer(Modifier.height(16.dp))
        CountryList(
            data = uiState.countries,
            onItemClick = onCountryItemClick
        )
        Spacer(Modifier.height(16.dp))
    }
}

@Composable
fun CountryDetailsScreen(
    countryName: String?,
    modifier: Modifier = Modifier,
    viewModel: CountryListViewModel = koinViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    val country = uiState.countries.find { it.name.common == countryName }
    if (country != null) {
        Column(
            modifier = modifier.padding(16.dp)
        ) {
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(country.flag.png)
                    .crossfade(true)
                    .build(),
                placeholder = painterResource(id = R.drawable.flag_placeholder),
                contentDescription = "A country's flag.",
                modifier = modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = country.name.common,
                style = MaterialTheme.typography.headlineLarge,
                color = MaterialTheme.colorScheme.primary
            )
            Row {
                Text(
                    text = "Official name:",
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.secondary
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                    text = country.name.official,
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.primary
                )
            }
            Row {
                Text(
                    text = "Capital:",
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.secondary
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                    text = country.capitals.first(),
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.primary
                )
            }
        }
    }
}

private const val KEY_COUNTRY_NAME = "countryName"