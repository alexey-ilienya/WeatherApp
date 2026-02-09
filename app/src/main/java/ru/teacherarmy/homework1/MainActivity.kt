package ru.teacherarmy.homework1

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import dagger.hilt.android.AndroidEntryPoint
import ru.teacherarmy.presentation.navigation.AppNavigation
import ru.teacherarmy.presentation.viewmodels.CurrentWeatherViewModel
import ru.teacherarmy.presentation.viewmodels.DailyWeatherViewModel
import ru.teacherarmy.presentation.viewmodels.HourlyWeatherViewModel
import ru.teacherarmy.presentation.viewmodels.SearchCityViewModel
import ru.teacherarmy.homework1.ui.theme.WeatherAppTheme

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            WeatherAppTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    AppNavigation()
                }
            }
        }
    }
}
