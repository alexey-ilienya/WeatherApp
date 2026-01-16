package ru.teacherarmy.homework1.data.repositoryImpl

import ru.teacherarmy.homework1.data.mapper.Mapper
import ru.teacherarmy.homework1.data.mapper.Mapper.toDomainModel
import ru.teacherarmy.homework1.data.network.SafeApiRequest
import ru.teacherarmy.homework1.data.network.WeatherApi
import ru.teacherarmy.homework1.domain.model.CurrentWeather
import ru.teacherarmy.homework1.domain.model.Daily
import ru.teacherarmy.homework1.domain.model.Hourly
import ru.teacherarmy.homework1.domain.repository.WeatherRepository
import java.time.DayOfWeek
import javax.inject.Inject

class WeatherRepoImpl @Inject constructor(
    private val weatherApiService: WeatherApi
): WeatherRepository, SafeApiRequest() {
    override suspend fun getCurrentWeather(lat: Double, lon: Double): CurrentWeather {
        val response = safeApiRequest {
            weatherApiService.getCurrentWeatherData(lat, lon, API_KEY)
        }
        return response.toDomainModel()
    }

    override suspend fun getHourlyWeather(lat: Double, lon: Double): List<Hourly> {
        val response = safeApiRequest {
            weatherApiService.getHourlyForecastData(lat, lon, API_KEY)
        }
        return response.toDomainModel()
    }

    override suspend fun getDailyWeather(lat: Double, lon: Double): Map<DayOfWeek, List<Daily>> {
        val response = safeApiRequest {
            weatherApiService.getDailyForecastData(lat, lon, API_KEY)
        }
        return Mapper.mapDailyWeatherByDayOfWeek(response.toDomainModel())
    }

    companion object {
        const val API_KEY = "99dd3dd3d82d1f52f784a015f53dff8d"
        const val COUNTRY_CODE_RU = 643
    }
}