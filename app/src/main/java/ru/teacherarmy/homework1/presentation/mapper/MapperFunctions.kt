package ru.teacherarmy.homework1.presentation.mapper

import androidx.annotation.DrawableRes
import ru.teacherarmy.homework1.R
import ru.teacherarmy.domain.model.City
import ru.teacherarmy.domain.model.SearchResults
import ru.teacherarmy.domain.model.WeatherType

fun SearchResults.toDomainModel(): City {
    return City(
        id = 0,
        name = name,
        country = country,
        latitude = latitude,
        longitude = longitude
    )
}

@DrawableRes fun WeatherType.getDrawableResId(): Int {
    return when (this) {
        is WeatherType.ClearSky -> R.drawable.clear_sky
        is WeatherType.FewClouds -> R.drawable.few_clouds
        is WeatherType.ShowerRain -> R.drawable.shower_rain
        is WeatherType.Overcast -> R.drawable.scattered_clouds
        is WeatherType.ScatteredClouds -> R.drawable.scattered_clouds
        is WeatherType.Rain -> R.drawable.rain
        is WeatherType.Snow -> R.drawable.snow
        is WeatherType.Mist -> R.drawable.mist
    }
}
