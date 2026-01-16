package ru.teacherarmy.homework1.presentation.composables

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ru.teacherarmy.homework1.R
import ru.teacherarmy.homework1.presentation.states.CurrentWeatherState
import ru.teacherarmy.homework1.presentation.states.DailyWeatherState
import ru.teacherarmy.homework1.presentation.viewmodels.CurrentWeatherViewModel

@Composable
fun CurrentWeatherCard(
    modifier: Modifier,
    currentState: CurrentWeatherState,
    dailyState: DailyWeatherState,
    currentWeatherViewModel: CurrentWeatherViewModel

) {
    currentState.data?.let {
        Card(modifier = modifier
            .padding(14.dp)
            .fillMaxWidth()
            .height(450.dp),
            shape = RoundedCornerShape(20.dp),
            elevation = CardDefaults.outlinedCardElevation(10.dp)


        ) {
            Column(
                horizontalAlignment = Alignment.Start
            ) {
                Text(text =  currentWeatherViewModel.city.value, fontSize = 45.sp, fontStyle = FontStyle.Normal,
                    fontWeight = FontWeight.ExtraBold ,
                    modifier = Modifier.padding(start = 13.dp) )
                Text(stringResource(R.string.format_degrees, it.temperature), fontSize = 45.sp, fontStyle = FontStyle.Normal,
                    fontWeight = FontWeight.ExtraBold, modifier =
                        Modifier.padding(start = 13.dp, bottom = 13.dp))

                Spacer(modifier = Modifier.height(3.dp))

                Text(text =  it.weatherType.weatherDesc,
                    fontSize = 13.sp, fontStyle = FontStyle.Normal, fontWeight = FontWeight.Light,
                    modifier = Modifier.padding(start = 13.dp))

                Spacer(modifier = Modifier.height(0.5.dp))
                Text(text = stringResource(R.string.format_wind_speed, it.windSpeed), fontSize =
                    13.sp, fontStyle = FontStyle.Normal, fontWeight = FontWeight.Light,
                    modifier = Modifier.padding(start = 13.dp))
                Divider(
                    modifier = Modifier
                        .padding(20.dp)
                        .fillMaxWidth()
                        .height(2.dp),
                    color = Color.LightGray, thickness = 0.3.dp
                )

                DailyWeather(state = dailyState)
            }
        }
    }

}