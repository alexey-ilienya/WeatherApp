package ru.teacherarmy.presentation.composables

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
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import ru.teacherarmy.domain.model.CurrentWeather
import ru.teacherarmy.domain.model.Daily
import ru.teacherarmy.domain.model.Hourly
import ru.teacherarmy.domain.model.WeatherType
import ru.teacherarmy.presentation.navigation.NavScreen
import ru.teacherarmy.presentation.states.CurrentWeatherState
import ru.teacherarmy.presentation.states.DailyWeatherState
import ru.teacherarmy.presentation.states.HourlyWeatherState
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.LocalDateTime
import java.util.EnumMap

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun allWeatherLoadedComposable(
    navController: NavHostController,
    state: CurrentWeatherState,
    dailyState: DailyWeatherState,
    city: String,
    hourlyWeatherState: HourlyWeatherState,
    onRefreshClick: () -> Unit,
) {
    allWeatherLoadedComposableContent(
        state = state,
        dailyState = dailyState,
        city = city,
        hourlyWeatherState = hourlyWeatherState,
        onRefreshClick = onRefreshClick,
        navigateToLocations = { navController.navigate(NavScreen.Locations.route) },
    )
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun allWeatherLoadedComposableContent(
    state: CurrentWeatherState,
    dailyState: DailyWeatherState,
    city: String,
    hourlyWeatherState: HourlyWeatherState,
    onRefreshClick: () -> Unit = {},
    navigateToLocations: () -> Unit = {},
) {
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()

    Scaffold(
        modifier =
            Modifier
                .fillMaxSize()
                .nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            TopAppBar(
                title = { },
                actions = {
                    IconButton(onClick = { navigateToLocations.invoke() }) {
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
                scrollBehavior = scrollBehavior,
            )
        },
    ) {
        LazyColumn(
            modifier =
                Modifier
                    .fillMaxSize()
                    .background(Color.Transparent),
        ) {
            item {
                Spacer(modifier = Modifier.height(90.dp))
                currentWeatherCard(
                    currentState = state,
                    modifier = Modifier,
                    dailyState = dailyState,
                    city = city,
                )
                Spacer(modifier = Modifier.height(10.dp))
                hourlyWeather(state = hourlyWeatherState, modifier = Modifier)
            }
        }
    }
}

@Preview
@Composable
private fun allWeatherLoadedComposablePreview() {
    allWeatherLoadedComposableContent(
        state =
            CurrentWeatherState(
                isLoading = false,
                data =
                    CurrentWeather(
                        isDay = 1,
                        temperature = -0.41,
                        weatherType = WeatherType.ClearSky,
                        windDirection = 67,
                        windSpeed = 4.22,
                    ),
                error = null,
            ),
        dailyState =
            DailyWeatherState(
                isLoading = false,
                data =
                    EnumMap<DayOfWeek, List<Daily>>(
                        DayOfWeek::class.java,
                    ).apply {
                        put(
                            DayOfWeek.WEDNESDAY,
                            arrayListOf(
                                Daily(
                                    temperature_2m_max = 2.91,
                                    temperature_2m_min = -0.13,
                                    time = LocalDate.of(2026, 3, 4),
                                    weatherType = WeatherType.ClearSky,
                                ),
                            ),
                        )
                        put(
                            DayOfWeek.THURSDAY,
                            arrayListOf(
                                Daily(
                                    temperature_2m_max = 1.47,
                                    temperature_2m_min = -2.70,
                                    time = LocalDate.of(2026, 3, 5),
                                    weatherType = WeatherType.Snow,
                                ),
                            ),
                        )
                        put(
                            DayOfWeek.FRIDAY,
                            arrayListOf(
                                Daily(
                                    temperature_2m_max = 0.75,
                                    temperature_2m_min = -4.65,
                                    time = LocalDate.of(2026, 3, 6),
                                    weatherType = WeatherType.ClearSky,
                                ),
                            ),
                        )
                        put(
                            DayOfWeek.SATURDAY,
                            arrayListOf(
                                Daily(
                                    temperature_2m_max = 2.38,
                                    temperature_2m_min = -2.63,
                                    time = LocalDate.of(2026, 3, 7),
                                    weatherType = WeatherType.ClearSky,
                                ),
                            ),
                        )
                        put(
                            DayOfWeek.SUNDAY,
                            arrayListOf(
                                Daily(
                                    temperature_2m_max = 2.41,
                                    temperature_2m_min = -4.38,
                                    time = LocalDate.of(2026, 3, 8),
                                    weatherType = WeatherType.ClearSky,
                                ),
                            ),
                        )
                        put(
                            DayOfWeek.MONDAY,
                            arrayListOf(
                                Daily(
                                    temperature_2m_max = -4.48,
                                    temperature_2m_min = -11.22,
                                    time = LocalDate.of(2026, 3, 9),
                                    weatherType = WeatherType.ScatteredClouds,
                                ),
                            ),
                        )
                        put(
                            DayOfWeek.TUESDAY,
                            arrayListOf(
                                Daily(
                                    temperature_2m_max = 4.09,
                                    temperature_2m_min = -5.11,
                                    time = LocalDate.of(2026, 3, 10),
                                    weatherType = WeatherType.ClearSky,
                                ),
                            ),
                        )
                    },
                error = null,
            ),
        city = "test",
        hourlyWeatherState =
            HourlyWeatherState(
                isLoading = false,
                data =
                    arrayListOf(
                        Hourly(
                            temperature_2m = 2.77,
                            time = LocalDateTime.of(2026, 3, 4, 15, 0, 0),
                            windspeed_120m = 4.49,
                            weatherType = WeatherType.ScatteredClouds,
                        ),
                        Hourly(
                            temperature_2m = 1.83,
                            time = LocalDateTime.of(2026, 3, 4, 18, 0),
                            windspeed_120m = 4.11,
                            weatherType = WeatherType.ClearSky,
                        ),
                        Hourly(
                            temperature_2m = 0.43,
                            time = LocalDateTime.of(2026, 3, 4, 21, 0),
                            windspeed_120m = 4.25,
                            weatherType = WeatherType.Snow,
                        ),
                    ),
                error = null,
            ),
    )
}
