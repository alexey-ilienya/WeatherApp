package ru.teacherarmy.data.model

data class CityRecord(
    val id: Int,
    val name: String,
    val coord: Coord,
    val country: String,
    val timezone: Int,
    val sunrise: Int,
    val sunset: Int
)

data class Coord(
    val lat: Double,
    val lon: Double
)
