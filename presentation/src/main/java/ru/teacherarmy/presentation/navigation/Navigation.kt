package ru.teacherarmy.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import ru.teacherarmy.presentation.composables.AllWeatherComposable
import ru.teacherarmy.presentation.composables.ChartPage
import ru.teacherarmy.presentation.composables.HorizontalCalendarPage
import ru.teacherarmy.presentation.composables.LocationsScreen
import ru.teacherarmy.presentation.composables.SearchLocation
import ru.teacherarmy.presentation.composables.SplashScreen
import ru.teacherarmy.presentation.composables.VerticalCalendarPage

@Composable
fun appNavigation(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = NavScreen.Splash.route,
    ) {
        composable(NavScreen.Splash.route) { _ ->
            SplashScreen(
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
            AllWeatherComposable(
                navController,
                searchCityViewModel = hiltViewModel(),
                viewModel = hiltViewModel(),
                dailyWeatherViewModel = hiltViewModel(),
                hourlyWeatherViewModel = hiltViewModel(),
            )
        }

        composable(NavScreen.Locations.route) { _ ->
            LocationsScreen(
                navController,
                hiltViewModel(),
            )
        }

        composable(NavScreen.Search.route) { _ ->
            SearchLocation(
                navController,
                hiltViewModel(),
            )
        }

        composable(BottomNavItem.HORIZONTAL.route) { _ ->
            HorizontalCalendarPage(
                close = { navController.popBackStack() },
            )
        }

        composable(BottomNavItem.VERTICAL.route) { backStackEntry ->
            VerticalCalendarPage(
                close = { navController.popBackStack() },
                dateSelected = { startDate, endDate -> },
            )
        }

        composable(BottomNavItem.CHART.route) {
            ChartPage(
                close = { navController.popBackStack() },
            )
        }
    }
}
