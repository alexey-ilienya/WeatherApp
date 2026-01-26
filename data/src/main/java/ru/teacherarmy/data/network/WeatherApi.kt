package ru.teacherarmy.data.network

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query
import ru.teacherarmy.data.model.CurrentWeatherResponse
import ru.teacherarmy.data.model.DailyForecastResponse
import ru.teacherarmy.data.model.HourlyForecastResponse

interface WeatherApi {
    @GET("/data/2.5/weather")
    suspend fun  getCurrentWeatherData(
        @Query("lat") lat: Double,
        @Query("lon") long:Double,
        @Query("appid") appId: String,
        @Query("lang") lang: String = "ru"
    ):Response<CurrentWeatherResponse>

    @GET("/data/2.5/forecast/daily")
    suspend fun  getDailyForecastData(
        @Query("lat") lat: Double,
        @Query("lon") long:Double,
        @Query("appid") appId: String,
        @Query("lang") lang: String = "ru"
    ):Response<DailyForecastResponse>

    @GET("/data/2.5/forecast")
    suspend fun  getHourlyForecastData(
        @Query("lat") lat: Double,
        @Query("lon") long:Double,
        @Query("appid") appId: String,
        @Query("lang") lang: String = "ru"
    ):Response<HourlyForecastResponse>
}