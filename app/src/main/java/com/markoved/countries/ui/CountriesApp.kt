package com.markoved.countries.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
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
import com.markoved.countries.data.entity.Country
import org.koin.androidx.compose.koinViewModel
import java.text.NumberFormat

enum class CountriesScreen {
    List,
    Details
}

@Composable
fun CountriesApp(
    windowSize: WindowSizeClass,
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
            CountryDetailsScreen(
                windowSize = windowSize,
                countryName = backStackEntry.arguments?.getString(KEY_COUNTRY_NAME)
            )
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

    Column(
        modifier = modifier.padding(horizontal = 8.dp)
    ) {
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
    windowSize: WindowSizeClass,
    countryName: String?,
    modifier: Modifier = Modifier,
    viewModel: CountryListViewModel = koinViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    val country = uiState.countries.find { it.name.common == countryName } ?: return

    when (windowSize.widthSizeClass) {
        WindowWidthSizeClass.Compact -> {
            CountryDetailsScreenPortrait(country = country)
        }
        WindowWidthSizeClass.Medium,
        WindowWidthSizeClass.Expanded -> {
            CountryDetailsScreenLandscape(country = country)
        }
    }
}

@Composable
fun CountryNameText(
    name: String,
    modifier: Modifier = Modifier
) {
    Text(
        text = name,
        style = MaterialTheme.typography.displayMedium,
        color = MaterialTheme.colorScheme.primary
    )
}

@Composable
fun FlagImage(
    flagUrl: String,
    modifier: Modifier = Modifier
) {
    AsyncImage(
        alignment = Alignment.TopEnd,
        model = ImageRequest.Builder(LocalContext.current)
            .data(flagUrl)
            .crossfade(true)
            .build(),
        contentDescription = stringResource(R.string.flag_image_content_description),
        modifier = modifier
    )
}

@Composable
fun OfficialNameText(
    officialName: String,
    modifier: Modifier = Modifier
) {
    Row {
        Text(
            text = "Official name:",
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.secondary
        )
        Spacer(modifier = Modifier.width(4.dp))
        Text(
            text = officialName,
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.primary
        )
    }
}

@Composable
fun CapitalText(
    capital: String,
    modifier: Modifier = Modifier
) {
    Row {
        Text(
            text = "Capital:",
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.secondary
        )
        Spacer(modifier = Modifier.width(4.dp))
        Text(
            text = capital,
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.primary
        )
    }
}

@Composable
fun PopulationText(
    population: Int,
    modifier: Modifier = Modifier
) {
    val formattedNumber = NumberFormat.getNumberInstance().format(population)
    Row {
        Text(
            text = "Population:",
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.secondary
        )
        Spacer(modifier = Modifier.width(4.dp))
        Text(
            text = "$formattedNumber people",
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.primary
        )
    }
}

@Composable
fun CountryDetailsScreenLandscape(
    country: Country,
    modifier: Modifier = Modifier
) {
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = modifier.padding(16.dp)
    ) {
        Column {
            CountryNameText(name = country.name.common)
            OfficialNameText(officialName = country.name.official)
            CapitalText(capital = country.capitals.first())
            PopulationText(population = country.population)
        }
        FlagImage(
            flagUrl = country.flag.png,
            modifier = Modifier
                .fillMaxHeight()
                .widthIn(max = 400.dp)
        )
    }
}

@Composable
fun CountryDetailsScreenPortrait(
    country: Country,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.padding(16.dp)
    ) {
        FlagImage(flagUrl = country.flag.png, modifier = Modifier.fillMaxWidth())
        Spacer(modifier = Modifier.height(8.dp))
        CountryNameText(name = country.name.common)
        OfficialNameText(officialName = country.name.official)
        CapitalText(capital = country.capitals.first())
        PopulationText(population = country.population)
    }
}

private const val KEY_COUNTRY_NAME = "countryName"