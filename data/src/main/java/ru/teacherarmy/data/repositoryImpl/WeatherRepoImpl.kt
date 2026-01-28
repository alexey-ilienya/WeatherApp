package ru.teacherarmy.data.repositoryImpl

import ru.teacherarmy.data.BuildConfig
import ru.teacherarmy.data.mapper.mapDailyWeatherByDayOfWeek
import ru.teacherarmy.data.mapper.toDomainModel
import ru.teacherarmy.data.network.ApiRequest
import ru.teacherarmy.data.network.WeatherApi
import ru.teacherarmy.domain.model.CurrentWeather
import ru.teacherarmy.domain.model.Daily
import ru.teacherarmy.domain.model.Hourly
import ru.teacherarmy.domain.repository.WeatherRepository
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
        return mapDailyWeatherByDayOfWeek(response?.toDomainModel() ?: arrayListOf<Daily>())
    }

    companion object {
        const val COUNTRY_CODE_RU = 643
    }
}