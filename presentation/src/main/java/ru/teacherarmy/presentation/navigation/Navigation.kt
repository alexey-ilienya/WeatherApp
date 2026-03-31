package ru.teacherarmy.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import ru.teacherarmy.presentation.composables.allWeatherComposable
import ru.teacherarmy.presentation.composables.chartPage
import ru.teacherarmy.presentation.composables.horizontalCalendarPage
import ru.teacherarmy.presentation.composables.locationsScreen
import ru.teacherarmy.presentation.composables.searchLocation
import ru.teacherarmy.presentation.composables.splashScreen
import ru.teacherarmy.presentation.composables.verticalCalendarPage

@Composable
fun appNavigation(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = NavScreen.Splash.route,
    ) {
        composable(NavScreen.Splash.route) { _ ->
            splashScreen(
                onAnimationEnd = {
                    navController.navigate(route = NavScreen.Home.route) {
                        popUpTo(NavScreen.Splash.route) {
                            inclusive = true
                        }
                    }
                },
            )
        }

        composable(NavScreen.Home.route) { _ ->
            allWeatherComposable(
                navController,
                searchCityViewModel = hiltViewModel(),
                viewModel = hiltViewModel(),
                dailyWeatherViewModel = hiltViewModel(),
                hourlyWeatherViewModel = hiltViewModel(),
            )
        }

        composable(NavScreen.Locations.route) { _ ->
            locationsScreen(
                navController,
                hiltViewModel(),
            )
        }

        composable(NavScreen.Search.route) { _ ->
            searchLocation(
                navController,
                hiltViewModel(),
            )
        }

        composable(BottomNavItem.HORIZONTAL.route) { _ ->
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

        composable(BottomNavItem.CHART.route) {
            chartPage(
                close = { navController.popBackStack() },
            )
        }
    }
}
