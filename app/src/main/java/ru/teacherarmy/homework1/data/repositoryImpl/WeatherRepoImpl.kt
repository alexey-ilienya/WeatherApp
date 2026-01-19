package ru.teacherarmy.homework1.data.repositoryImpl

import ru.teacherarmy.homework1.BuildConfig
import ru.teacherarmy.homework1.data.mapper.mapDailyWeatherByDayOfWeek
import ru.teacherarmy.homework1.data.mapper.toDomainModel
import ru.teacherarmy.homework1.data.network.ApiRequest
import ru.teacherarmy.homework1.data.network.WeatherApi
import ru.teacherarmy.homework1.domain.model.CurrentWeather
import ru.teacherarmy.homework1.domain.model.Daily
import ru.teacherarmy.homework1.domain.model.Hourly
import ru.teacherarmy.homework1.domain.repository.WeatherRepository
import java.time.DayOfWeek
import javax.inject.Inject

class WeatherRepoImpl @Inject constructor(
    private val weatherApiService: WeatherApi
): WeatherRepository, ApiRequest() {
    override suspend fun getCurrentWeather(lat: Double, lon: Double): CurrentWeather? {
        val response = apiRequest {
            weatherApiService.getCurrentWeatherData(lat, lon, BuildConfig.WEATHER_API_KEY)
        }
        return response?.toDomainModel()
    }

    override suspend fun getHourlyWeather(lat: Double, lon: Double): List<Hourly>? {
        val response = apiRequest {
            weatherApiService.getHourlyForecastData(lat, lon, BuildConfig.WEATHER_API_KEY)
        }
        return response?.toDomainModel()
    }

    override suspend fun getDailyWeather(lat: Double, lon: Double): Map<DayOfWeek, List<Daily>> {
        val response = apiRequest {
            weatherApiService.getDailyForecastData(lat, lon, BuildConfig.WEATHER_API_KEY)
        }
        return mapDailyWeatherByDayOfWeek(response?.toDomainModel() ?: arrayListOf())
    }

    companion object {
        const val COUNTRY_CODE_RU = 643
    }
}