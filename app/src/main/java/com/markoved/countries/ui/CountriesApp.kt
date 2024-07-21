package com.markoved.countries.ui

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument

private const val KEY_COUNTRY_NAME = "countryName"
private enum class CountriesScreen {
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
        modifier = Modifier.fillMaxSize()
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
