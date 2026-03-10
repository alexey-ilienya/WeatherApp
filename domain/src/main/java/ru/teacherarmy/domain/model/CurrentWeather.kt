package ru.teacherarmy.domain.model

data class CurrentWeather(
    val isDay: Int,
    val temperature: Double,
    val weatherType: WeatherType,
    val windDirection: Int,
    val windSpeed: Double,
)
