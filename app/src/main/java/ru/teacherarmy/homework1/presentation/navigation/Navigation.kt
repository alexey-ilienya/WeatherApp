package ru.teacherarmy.homework1.presentation.navigation

import android.annotation.SuppressLint
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.SavedStateHandle
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import ru.teacherarmy.homework1.presentation.composables.AllWeatherComposable
import ru.teacherarmy.homework1.presentation.composables.LocationList
import ru.teacherarmy.homework1.presentation.composables.SearchLocation
import ru.teacherarmy.homework1.presentation.viewmodels.CurrentWeatherViewModel
import ru.teacherarmy.homework1.presentation.viewmodels.DailyWeatherViewModel
import ru.teacherarmy.homework1.presentation.viewmodels.HourlyWeatherViewModel
import ru.teacherarmy.homework1.presentation.viewmodels.SearchCityViewModel

@SuppressLint("RememberReturnType", "SuspiciousIndentation")
@Composable
fun AppNavigation(currentWeatherViewModel: CurrentWeatherViewModel,
                  dailyWeatherViewModel: DailyWeatherViewModel,
                  hourlyWeatherViewModel: HourlyWeatherViewModel,
                  searchCityViewModel: SearchCityViewModel) {

    val navController = rememberNavController()
    val savedStateHandle = remember {
        SavedStateHandle()
    }

    NavHost(navController = navController, startDestination = NavScreen.Home.route){

        composable(NavScreen.Home.route) { backStackEntry ->
            AllWeatherComposable(navController, searchCityViewModel = searchCityViewModel, handle = savedStateHandle
                , viewModel = currentWeatherViewModel, dailyWeatherViewModel = dailyWeatherViewModel,
                hourlyWeatherViewModel = hourlyWeatherViewModel)
        }

        composable(NavScreen.Locations.route) { backStackEntry ->
            LocationList(navController, searchCityViewModel)
        }


        composable(NavScreen.Search.route){ backStackEntry ->
            SearchLocation(navController, searchCityViewModel)
        }

    }



}
