package ru.teacherarmy.homework1.presentation.composables

import android.Manifest
import android.annotation.SuppressLint
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
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.SavedStateHandle
import androidx.navigation.NavHostController
import ru.teacherarmy.homework1.presentation.navigation.NavScreen
import ru.teacherarmy.homework1.presentation.viewmodels.CurrentWeatherViewModel
import ru.teacherarmy.homework1.presentation.viewmodels.DailyWeatherViewModel
import ru.teacherarmy.homework1.presentation.viewmodels.HourlyWeatherViewModel
import ru.teacherarmy.homework1.presentation.viewmodels.SearchCityViewModel

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AllWeatherComposable(navController: NavHostController,
                         viewModel: CurrentWeatherViewModel,
                         dailyWeatherViewModel: DailyWeatherViewModel,
                         hourlyWeatherViewModel: HourlyWeatherViewModel,
                         searchCityViewModel: SearchCityViewModel,
                         handle:SavedStateHandle

) {
    lateinit var permissionLauncher: ActivityResultLauncher<Array<String>>

    val context = LocalContext.current
    val state = viewModel.state.collectAsState()
    val dailyState = dailyWeatherViewModel.state.collectAsState()
    var permissionStatus by remember { mutableStateOf(false) }

    val selectedLatitude = searchCityViewModel.selectedLatitude.collectAsState()
    val selectedLongitude = searchCityViewModel.selectedLongitude.collectAsState()
    val switchState = searchCityViewModel.switchState.collectAsState()

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
            viewModel.fetchCurrentWeather(context, selectedLatitude.value, selectedLongitude.value)
            dailyWeatherViewModel.fetchDailyWeather(selectedLatitude.value, selectedLongitude.value)
            hourlyWeatherViewModel.fetchHourlyWeather(selectedLatitude.value, selectedLongitude.value)
        } else if ( refreshWeather ||permissionStatus || switchState.value) {
            viewModel.fetchCurrentWeather(context)
            dailyWeatherViewModel.fetchDailyWeather()
            hourlyWeatherViewModel.fetchHourlyWeather()
        }
        refreshWeather = false

    }

    if (state.value.isLoading) {
        CircularProgressBar()
    } else if (state.value.error != null) {
        ErrorText(state.value.error!!)
    } else {
        AllWeatherLoadedComposable(navController = navController,
            state = state.value,
            dailyState = dailyState.value,
            viewModel = viewModel,
            hourlyWeatherViewModel = hourlyWeatherViewModel,
            onRefreshClick = { refreshWeather = true })
    }
}

@Composable
fun ErrorText(text: String) {
    Text(
        text = text,
        color = Color.Red,
        textAlign = TextAlign.Center,
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .wrapContentHeight(Alignment.CenterVertically)
    )
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
