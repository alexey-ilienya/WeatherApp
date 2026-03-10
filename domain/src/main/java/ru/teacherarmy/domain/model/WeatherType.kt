package ru.teacherarmy.domain.model

sealed class WeatherType(
    val weatherDesc: String,
) {
    object ClearSky : WeatherType(
        weatherDesc = "ясно",
    )

    object FewClouds : WeatherType(
        weatherDesc = "малооблачно",
    )

    object ShowerRain : WeatherType(
        weatherDesc = "ливень",
    )

    object Overcast : WeatherType(
        weatherDesc = "облачно с прояснениями",
    )

    object ScatteredClouds : WeatherType(
        weatherDesc = "пасмурно",
    )

    object Rain : WeatherType(
        weatherDesc = "дождь",
    )

    object Snow : WeatherType(
        weatherDesc = "небольшой снег",
    )

    object Mist : WeatherType(
        weatherDesc = "туман",
    )

    companion object {
        fun fromWMO(code: Int): WeatherType =
            when (code) {
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
