package ru.teacherarmy.homework1.presentation.composables

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import ru.teacherarmy.homework1.R
import ru.teacherarmy.domain.model.Daily
import ru.teacherarmy.homework1.presentation.mapper.getDrawableResId
import ru.teacherarmy.homework1.presentation.states.DailyWeatherState
import java.time.DayOfWeek
import java.time.format.TextStyle
import kotlin.collections.component1
import kotlin.collections.component2

@Composable
fun DailyWeather(
    state : DailyWeatherState
) {

    WeatherLazyRow(weatherData = state.data)


}

@Composable
fun WeatherLazyRow(weatherData: Map<DayOfWeek, List<Daily>>?) {
    LazyRow {
        weatherData?.entries?.let {
            items(it.toList()) { (dayOfWeek,dailyWeatherList)->
                WeatherItem(dayOfWeek, dailyWeatherList)
            }
        }
    }
}

@Composable
fun WeatherItem(dayOfWeek: DayOfWeek, dailyWeatherList: List<Daily>) {
    Column(
        modifier = Modifier.padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = dayOfWeek.getDisplayName(TextStyle.FULL, getCurrentLocale()))
        Spacer(modifier = Modifier.height(6.dp))

        dailyWeatherList.forEach { dailyWeatherItem ->

            Image(painter = painterResource(id = dailyWeatherItem.weatherType.getDrawableResId()) ,
                contentDescription =null, modifier = Modifier.size(50.dp) )

            Text(
                text = stringResource(R.string.format_degrees, dailyWeatherItem.temperature_2m_max),
                modifier = Modifier.padding(8.dp)
            )
            Spacer(modifier = Modifier.height(3.dp))
            Text(
                text = stringResource(R.string.format_degrees, dailyWeatherItem.temperature_2m_min),
                modifier = Modifier.padding(8.dp)
            )
        }
    }
}
