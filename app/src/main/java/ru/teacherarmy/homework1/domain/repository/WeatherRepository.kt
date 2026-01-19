package ru.teacherarmy.homework1.domain.repository

import ru.teacherarmy.homework1.domain.model.CurrentWeather
import ru.teacherarmy.homework1.domain.model.Daily
import ru.teacherarmy.homework1.domain.model.Hourly
import java.time.DayOfWeek

interface WeatherRepository {
    suspend fun getCurrentWeather( lat : Double, lon : Double): CurrentWeather?
    suspend fun getHourlyWeather( lat : Double, lon : Double): List<Hourly>?
    suspend fun getDailyWeather( lat : Double, lon : Double): Map<DayOfWeek, List<Daily>>
}