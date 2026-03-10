package ru.teacherarmy.presentation.mapper

import androidx.annotation.DrawableRes
import ru.teacherarmy.domain.model.City
import ru.teacherarmy.domain.model.SearchResults
import ru.teacherarmy.domain.model.WeatherType
import ru.teacherarmy.presentation.R

fun SearchResults.toDomainModel(): City =
    City(
        id = 0,
        name = name,
        country = country,
        latitude = latitude,
        longitude = longitude,
    )

@DrawableRes fun WeatherType.getDrawableResId(): Int =
    when (this) {
        is WeatherType.ClearSky -> R.drawable.clear_sky
        is WeatherType.FewClouds -> R.drawable.few_clouds
        is WeatherType.ShowerRain -> R.drawable.shower_rain
        is WeatherType.Overcast -> R.drawable.scattered_clouds
        is WeatherType.ScatteredClouds -> R.drawable.scattered_clouds
        is WeatherType.Rain -> R.drawable.rain
        is WeatherType.Snow -> R.drawable.snow
        is WeatherType.Mist -> R.drawable.mist
    }
