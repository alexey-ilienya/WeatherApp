package ru.teacherarmy.homework1.data.model

import com.google.gson.annotations.SerializedName

data class DailyForecast(
    val dt: Int,
    val sunrise: Int,
    val sunset: Int,
    val temp: TemperatureInfo,
    @SerializedName("feels_like")
    val feelsLike: TemperatureInfo,
    val pressure: Int,
    val humidity: Int,
    val weather: List<Weather>,
    val speed: Double,
    val deg: Double,
    val gust: Double,
    val clouds: Int,
    val rain: Double,
    val snow: Double
)
