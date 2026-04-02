package ru.teacherarmy.presentation.composables

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ru.teacherarmy.domain.model.Hourly
import ru.teacherarmy.presentation.R
import ru.teacherarmy.presentation.mapper.getDrawableResId
import ru.teacherarmy.presentation.states.HourlyWeatherState
import java.time.format.DateTimeFormatter

@Composable
fun HourlyWeather(
    state: HourlyWeatherState,
    modifier: Modifier,
) {
    state.data?.let {
        val formatter = DateTimeFormatter.ofPattern("EEEE, d MMM", getCurrentLocale())
        val today =
            remember(it) {
                it[0].time.format(formatter).uppercase()
            }

        Card(
            modifier =
                modifier
                    .padding(14.dp)
                    .fillMaxWidth()
                    .height(400.dp),
            shape = RoundedCornerShape(20.dp),
            elevation = CardDefaults.outlinedCardElevation(10.dp),
        ) {
            Text(
                text = today,
                fontSize = 20.sp,
                fontStyle = FontStyle.Normal,
                fontWeight = FontWeight.ExtraBold,
                modifier = Modifier.padding(14.dp),
            )
            LazyColumn {
                items(it) { hourlyWeather ->
                    HourlyWeatherItem(hourlyWeather)
                }
            }
        }
    }
}

@Composable
fun HourlyWeatherItem(hourlyWeather: Hourly) {
    val dateTimeFormatter = DateTimeFormatter.ofPattern("HH:mm")

    Row(
        modifier = Modifier.padding(14.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text(
            text = hourlyWeather.time.format(dateTimeFormatter),
            fontSize = 15.sp,
            fontStyle = FontStyle.Normal,
            fontWeight = FontWeight.Light,
            modifier = Modifier.padding(15.dp),
        )

        Spacer(modifier = Modifier.width(50.dp))

        Image(
            painter = painterResource(id = hourlyWeather.weatherType.getDrawableResId()),
            contentDescription = null,
            modifier = Modifier.size(50.dp),
        )

        Spacer(modifier = Modifier.width(50.dp))
        Text(text = " ${stringResource(R.string.format_degrees, hourlyWeather.temperature_2m)}")

        Spacer(modifier = Modifier.width(40.dp))
        Text(text = " ${stringResource(R.string.format_wind_speed, hourlyWeather.windspeed_120m)}")
    }
}
