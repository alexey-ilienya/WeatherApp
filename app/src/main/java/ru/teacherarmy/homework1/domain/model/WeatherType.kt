package ru.teacherarmy.homework1.domain.model

import androidx.annotation.DrawableRes
import ru.teacherarmy.homework1.R

sealed class WeatherType(
    val weatherDesc: String,
    @DrawableRes val iconRes: Int,

    ) {
    object ClearSky : WeatherType(
        weatherDesc = "ясно",
        iconRes = R.drawable.clear_sky
    )
    object FewClouds : WeatherType(
        weatherDesc = "малооблачно",
        iconRes = R.drawable.few_clouds
    )
    object ShowerRain : WeatherType(
        weatherDesc = "ливень",
        iconRes = R.drawable.shower_rain
    )
    object Overcast : WeatherType(
        weatherDesc = "облачно с прояснениями",
        iconRes = R.drawable.scattered_clouds
    )
    object ScatteredClouds : WeatherType(
        weatherDesc = "пасмурно",
        iconRes = R.drawable.scattered_clouds
    )
    object Rain : WeatherType(
        weatherDesc = "дождь",
        iconRes = R.drawable.rain
    )
    object Snow : WeatherType(
        weatherDesc = "небольшой снег",
        iconRes = R.drawable.snow
    )
    object Mist : WeatherType(
        weatherDesc = "туман",
        iconRes = R.drawable.mist
    )

    companion object {
        fun fromWMO(code: Int): WeatherType {
            return when(code) {
                800 -> ClearSky
                801 -> FewClouds
                802 -> Overcast
                803 -> ScatteredClouds
                600 -> Snow
                400 -> Rain
                401 -> ShowerRain
                200 -> Mist
                else -> ClearSky
            }
        }
    }
}
