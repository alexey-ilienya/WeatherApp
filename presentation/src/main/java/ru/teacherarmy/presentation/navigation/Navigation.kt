package ru.teacherarmy.presentation.navigation

import android.annotation.SuppressLint
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import ru.teacherarmy.presentation.composables.AllWeatherComposable
import ru.teacherarmy.presentation.composables.LocationsScreen
import ru.teacherarmy.presentation.composables.SearchLocation

@SuppressLint("RememberReturnType", "SuspiciousIndentation")
@Composable
fun AppNavigation() {

    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = NavScreen.Home.route){

        composable(NavScreen.Home.route) { backStackEntry ->
            AllWeatherComposable(navController, searchCityViewModel = hiltViewModel()
                , viewModel = hiltViewModel(), dailyWeatherViewModel = hiltViewModel(),
                hourlyWeatherViewModel = hiltViewModel())
        }

        composable(NavScreen.Locations.route) { backStackEntry ->
            LocationsScreen(navController, hiltViewModel())
        }


        composable(NavScreen.Search.route){ backStackEntry ->
            SearchLocation(navController, hiltViewModel())
        }

    }



}
