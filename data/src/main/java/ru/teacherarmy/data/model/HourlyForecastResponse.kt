package ru.teacherarmy.data.model

data class HourlyForecastResponse(
    val city: CityRecord,
    val cnt: Int,
    val list: List<HourlyForecast>,
)
