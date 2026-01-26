package ru.teacherarmy.domain.repository

import ru.teacherarmy.domain.model.CurrentWeather
import ru.teacherarmy.domain.model.Daily
import ru.teacherarmy.domain.model.Hourly
import java.time.DayOfWeek

interface WeatherRepository {
    suspend fun getCurrentWeather( lat : Double, lon : Double): CurrentWeather?
    suspend fun getHourlyWeather( lat : Double, lon : Double): List<Hourly>?
    suspend fun getDailyWeather( lat : Double, lon : Double): Map<DayOfWeek, List<Daily>>
}