package ru.teacherarmy.data.model

import com.google.gson.annotations.SerializedName

data class HourlyForecast(
    val dt: Int,
    val main: MainWeatherInfo,
    val weather: List<Weather>,
    val clouds: CloudsInfo,
    val wind: WindInfo,
    val visibility: Int,
    @SerializedName("dt_txt")
    val dtTxt: String
)
