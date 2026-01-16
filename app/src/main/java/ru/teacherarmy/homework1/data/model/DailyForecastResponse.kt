package ru.teacherarmy.homework1.data.model

data class DailyForecastResponse(
    val city: CityRecord,
    val cnt: Int,
    val list: List<DailyForecast>
)
