package ru.teacherarmy.homework1.domain.model

data class CurrentWeather(
    val isDay: Int,
    val temperature: Double,
    val weatherType: WeatherType,
    val windDirection: Int,
    val windSpeed: Double
)