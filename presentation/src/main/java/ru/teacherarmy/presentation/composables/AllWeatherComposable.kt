package ru.teacherarmy.presentation.composables

import android.Manifest
import android.annotation.SuppressLint
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import ru.teacherarmy.presentation.viewmodels.CurrentWeatherViewModel
import ru.teacherarmy.presentation.viewmodels.DailyWeatherViewModel
import ru.teacherarmy.presentation.viewmodels.HourlyWeatherViewModel
import ru.teacherarmy.presentation.viewmodels.SearchCityViewModel

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AllWeatherComposable(
    navController: NavHostController,
    viewModel: CurrentWeatherViewModel,
    dailyWeatherViewModel: DailyWeatherViewModel,
    hourlyWeatherViewModel: HourlyWeatherViewModel,
    searchCityViewModel: SearchCityViewModel,
) {
    var permissionStatus by remember { mutableStateOf(false) }
    val permissionLauncher: ActivityResultLauncher<Array<String>> =
        rememberLauncherForActivityResult(
            ActivityResultContracts.RequestMultiplePermissions(),
        ) { permissions ->
            val granted =
                permissions[Manifest.permission.ACCESS_FINE_LOCATION] == true &&
                    permissions[Manifest.permission.ACCESS_COARSE_LOCATION] == true

            if (granted) {
                permissionStatus = true
            }
        }

    val context = LocalContext.current
    val state = viewModel.state.collectAsState()
    val dailyState = dailyWeatherViewModel.state.collectAsState()

    val selectedLatitude = searchCityViewModel.selectedLatitude.collectAsState()
    val selectedLongitude = searchCityViewModel.selectedLongitude.collectAsState()
    val switchState = searchCityViewModel.switchState.collectAsState()

    var refreshWeather by remember { mutableStateOf(false) }

    LaunchedEffect(permissionStatus) {
        if (!permissionStatus) {
            permissionLauncher.launch(
                arrayOf(
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION,
                ),
            )
        }
    }
    LaunchedEffect(
        switchState,
        refreshWeather,
        selectedLatitude,
        selectedLongitude,
        permissionStatus,
    ) {
        if (refreshWeather || selectedLatitude != null && selectedLongitude != null) {
            viewModel.fetchCurrentWeather(context, selectedLatitude.value, selectedLongitude.value)
            dailyWeatherViewModel.fetchDailyWeather(selectedLatitude.value, selectedLongitude.value)
            hourlyWeatherViewModel.fetchHourlyWeather(
                selectedLatitude.value,
                selectedLongitude.value,
            )
        } else if (refreshWeather || permissionStatus || switchState.value) {
            viewModel.fetchCurrentWeather(context)
            dailyWeatherViewModel.fetchDailyWeather()
            hourlyWeatherViewModel.fetchHourlyWeather()
        }
        refreshWeather = false
    }

    val city = viewModel.city.collectAsState()
    val hourlyWeatherState = hourlyWeatherViewModel.state.collectAsState()

    if (state.value.isLoading) {
        CircularProgressBar()
    } else if (state.value.error != null) {
        ErrorText(state.value.error ?: "")
    } else {
        AllWeatherLoadedComposable(
            navController = navController,
            state = state.value,
            dailyState = dailyState.value,
            city = city.value,
            hourlyWeatherState = hourlyWeatherState.value,
            onRefreshClick = { refreshWeather = true },
        )
    }
}

@Composable
fun ErrorText(text: String) {
    Text(
        text = text,
        color = Color.Red,
        textAlign = TextAlign.Center,
        modifier =
            Modifier
                .fillMaxSize()
                .padding(16.dp)
                .wrapContentHeight(Alignment.CenterVertically),
    )
}

@Composable
fun CircularProgressBar() {
    Box(contentAlignment = Alignment.Center) {
        CircularProgressIndicator(
            modifier =
                Modifier
                    .size(70.dp)
                    .padding(16.dp)
                    .wrapContentSize(Alignment.Center),
        )
    }
}
