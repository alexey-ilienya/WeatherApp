package ru.teacherarmy.data.model

data class CurrentWeatherResponse(
    val coord: Coord,
    val weather: List<Weather>,
    val main: MainWeatherInfo,
    val visibility: Int,
    val wind: WindInfo,
    val clouds: CloudsInfo,
    val dt: Long,
    val sys: Sys,
    val timezone: Int,
    val name: String
)

data class Sys(
    val country: String,
    val sunrise: Int,
    val sunset: Int
)
