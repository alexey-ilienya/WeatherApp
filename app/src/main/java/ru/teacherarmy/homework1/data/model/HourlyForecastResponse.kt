package ru.teacherarmy.homework1.data.model

data class HourlyForecastResponse(
    val city: CityRecord,
    val cnt: Int,
    val list: List<HourlyForecast>
)
