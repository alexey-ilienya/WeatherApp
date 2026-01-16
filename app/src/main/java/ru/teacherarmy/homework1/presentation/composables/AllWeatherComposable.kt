package ru.teacherarmy.homework1.presentation.composables

import android.Manifest
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.SavedStateHandle
import androidx.navigation.NavHostController
import ru.teacherarmy.homework1.presentation.navigation.NavScreen
import ru.teacherarmy.homework1.presentation.viewmodels.CurrentWeatherViewModel
import ru.teacherarmy.homework1.presentation.viewmodels.DailyWeatherViewModel
import ru.teacherarmy.homework1.presentation.viewmodels.HourlyWeatherViewModel
import ru.teacherarmy.homework1.presentation.viewmodels.SearchCityViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AllWeatherComposable(navController: NavHostController,
                         viewModel: CurrentWeatherViewModel,
                         dailyWeatherViewModel: DailyWeatherViewModel,
                         hourlyWeatherViewModel: HourlyWeatherViewModel,
                         searchCityViewModel: SearchCityViewModel,
                         handle:SavedStateHandle

) {
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()

    lateinit var permissionLauncher: ActivityResultLauncher<Array<String>>

    val state = viewModel.state
    val dailyState = dailyWeatherViewModel.state
    var permissionStatus by remember { mutableStateOf(false) }

    val selectedLatitude = searchCityViewModel.selectedLatitude.value
    val selectedLongitude = searchCityViewModel.selectedLongitude.value
    val switchState = searchCityViewModel.switchState

    var refreshWeather by remember { mutableStateOf(false) }


    permissionLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()
    ) { permissions ->
        val granted =
            permissions[Manifest.permission.ACCESS_FINE_LOCATION] == true &&
                    permissions[Manifest.permission.ACCESS_COARSE_LOCATION] == true

        if (granted) {
            permissionStatus = true
        }
    }

    LaunchedEffect(permissionStatus) {
        if (!permissionStatus) {
            permissionLauncher.launch(
                arrayOf(
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                )
            )
        }
    }
    LaunchedEffect( switchState, refreshWeather, selectedLatitude, selectedLongitude, permissionStatus) {
        if (refreshWeather || selectedLatitude != null && selectedLongitude != null) {
            viewModel.fetchCurrentWeather(selectedLatitude, selectedLongitude)
            dailyWeatherViewModel.fetchDailyWeather(selectedLatitude, selectedLongitude)
            hourlyWeatherViewModel.fetchHourlyWeather(selectedLatitude, selectedLongitude)
        } else if ( refreshWeather ||permissionStatus || switchState) {
            viewModel.fetchCurrentWeather()
            dailyWeatherViewModel.fetchDailyWeather()
            hourlyWeatherViewModel.fetchHourlyWeather()
        }
        refreshWeather = false

    }

    if (state.isLoading) {

        CircularProgressBar()

    } else if (state.error != null) {
        Text(
            text = state.error,
            color = Color.Red,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
                .wrapContentHeight(Alignment.CenterVertically)
        )
    } else {
        Scaffold(
            modifier = Modifier
                .fillMaxSize()
                .nestedScroll(scrollBehavior.nestedScrollConnection),
            topBar = {
                TopAppBar(
                    title = {

                    },
                    actions = {
                        IconButton(onClick = { navController.navigate(NavScreen.Locations.route)}) {
                            Icon(
                                imageVector = Icons.Default.Settings,
                                contentDescription = "settings",

                                )
                        }
                        IconButton(onClick = {
                            refreshWeather = true
                        }) {
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
                        currentWeatherViewModel = viewModel
                    )
                    Spacer(modifier = Modifier.height(10.dp))
                    HourlyWeather(state = hourlyWeatherViewModel.state, modifier = Modifier)
                }
            }
        }
    }
}

@Composable
fun CircularProgressBar() {
    Box(contentAlignment = Alignment.Center){
        CircularProgressIndicator(
            modifier = Modifier
                .size(70.dp)
                .padding(16.dp)
                .wrapContentSize(Alignment.Center)
        )
    }

}
