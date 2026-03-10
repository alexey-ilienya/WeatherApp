package ru.teacherarmy.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import ru.teacherarmy.presentation.composables.allWeatherComposable
import ru.teacherarmy.presentation.composables.horizontalCalendarPage
import ru.teacherarmy.presentation.composables.locationsScreen
import ru.teacherarmy.presentation.composables.searchLocation
import ru.teacherarmy.presentation.composables.verticalCalendarPage

@Composable
fun appNavigation(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = NavScreen.Home.route,
    ) {
        composable(NavScreen.Home.route) { backStackEntry ->
            allWeatherComposable(
                navController,
                searchCityViewModel = hiltViewModel(),
                viewModel = hiltViewModel(),
                dailyWeatherViewModel = hiltViewModel(),
                hourlyWeatherViewModel = hiltViewModel(),
            )
        }

        composable(NavScreen.Locations.route) { backStackEntry ->
            locationsScreen(
                navController,
                hiltViewModel(),
            )
        }

        composable(NavScreen.Search.route) { backStackEntry ->
            searchLocation(
                navController,
                hiltViewModel(),
            )
        }

        composable(BottomNavItem.HORIZONTAL.route) { backStackEntry ->
            horizontalCalendarPage(
                close = { navController.popBackStack() },
            )
        }

        composable(BottomNavItem.VERTICAL.route) { backStackEntry ->
            verticalCalendarPage(
                close = { navController.popBackStack() },
                dateSelected = { startDate, endDate -> },
            )
        }
    }
}
