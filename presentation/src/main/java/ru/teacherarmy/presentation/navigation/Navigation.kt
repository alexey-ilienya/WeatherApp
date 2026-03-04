package ru.teacherarmy.presentation.navigation

import android.annotation.SuppressLint
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import ru.teacherarmy.presentation.composables.AllWeatherComposable
import ru.teacherarmy.presentation.composables.HorizontalCalendarPage
import ru.teacherarmy.presentation.composables.LocationsScreen
import ru.teacherarmy.presentation.composables.SearchLocation
import ru.teacherarmy.presentation.composables.VerticalCalendarPage

@SuppressLint("RememberReturnType", "SuspiciousIndentation")
@Composable
fun AppNavigation(navController: NavHostController) {

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

        composable(BottomNavItem.HORIZONTAL.route) { backStackEntry ->
            HorizontalCalendarPage(close = { navController.popBackStack() })
        }

        composable(BottomNavItem.VERTICAL.route) { backStackEntry ->
            VerticalCalendarPage (
                close = { navController.popBackStack() },
                dateSelected = { startDate, endDate ->  }
            )
        }
    }
}
