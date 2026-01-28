package ru.teacherarmy.homework1.presentation.composables

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import ru.teacherarmy.homework1.presentation.navigation.NavScreen
import ru.teacherarmy.homework1.presentation.states.CurrentWeatherState
import ru.teacherarmy.homework1.presentation.states.DailyWeatherState
import ru.teacherarmy.homework1.presentation.viewmodels.CurrentWeatherViewModel
import ru.teacherarmy.homework1.presentation.viewmodels.HourlyWeatherViewModel

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AllWeatherLoadedComposable(navController: NavHostController,
                               state: CurrentWeatherState,
                               dailyState: DailyWeatherState,
                               viewModel: CurrentWeatherViewModel,
                               hourlyWeatherViewModel: HourlyWeatherViewModel,
                               onRefreshClick: () -> Unit) {
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()

    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            TopAppBar(
                title = { },
                actions = {
                    IconButton(onClick = { navController.navigate(NavScreen.Locations.route)}) {
                        Icon(
                            imageVector = Icons.Default.Settings,
                            contentDescription = "settings",
                        )
                    }
                    IconButton(onClick = onRefreshClick) {
                        Icon(
                            imageVector = Icons.Default.Refresh,
                            contentDescription = "refresh",
                        )
                    }

                },
                scrollBehavior = scrollBehavior
            )

        }
    ) {
        val hourlyWeatherState = hourlyWeatherViewModel.state.collectAsState()
        val city = viewModel.city.collectAsState()

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Transparent)
        ) {
            item {
                Spacer(modifier = Modifier.height(90.dp))
                CurrentWeatherCard(
                    currentState = state,
                    modifier = Modifier,
                    dailyState = dailyState,
                    city = city.value
                )
                Spacer(modifier = Modifier.height(10.dp))
                HourlyWeather(state = hourlyWeatherState.value, modifier = Modifier)
            }
        }
    }
}
