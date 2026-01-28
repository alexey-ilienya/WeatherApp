package ru.teacherarmy.data.model

data class DailyForecastResponse(
    val city: CityRecord,
    val cnt: Int,
    val list: List<DailyForecast>
)
