package ru.teacherarmy.data.mapper

import ru.teacherarmy.data.model.CityEntity
import ru.teacherarmy.data.model.CurrentWeatherResponse
import ru.teacherarmy.data.model.DailyForecastResponse
import ru.teacherarmy.data.model.HourlyForecastResponse
import ru.teacherarmy.domain.model.City
import ru.teacherarmy.domain.model.CurrentWeather
import ru.teacherarmy.domain.model.Daily
import ru.teacherarmy.domain.model.Hourly
import ru.teacherarmy.domain.model.WeatherType
import java.time.DayOfWeek
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter
import kotlin.math.roundToInt

fun CurrentWeatherResponse.toDomainModel(): CurrentWeather {
    return CurrentWeather(
        if (dt >= sys.sunrise && dt <= sys.sunset) 1 else 0,
        main.temp - TEMP_0C,
        WeatherType.fromWMO(weather[0].id),
        wind.degrees.roundToInt(),
        wind.speed
    )
}

fun DailyForecastResponse.toDomainModel(): List<Daily> {
    val tz = city.timezone
    return list.map {
        val instant = Instant.ofEpochMilli((it.dt + tz).toLong() * 1000)

        Daily(
            temperature_2m_min = it.temp.min - TEMP_0C,
            temperature_2m_max = it.temp.max - TEMP_0C,
            time = instant.atZone(ZoneOffset.UTC).toLocalDate(),
            weatherType = WeatherType.fromWMO(it.weather[0].id)
        )
    }
}

fun HourlyForecastResponse.toDomainModel(): List<Hourly> {
    val dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
    val currentTime = LocalDateTime.now()

    return list.mapNotNull {
        val localDateTime = LocalDateTime.parse(it.dtTxt, dateTimeFormatter)

        if (localDateTime.toLocalDate() == currentTime.toLocalDate()) {
            Hourly(
                temperature_2m = it.main.temp - TEMP_0C,
                time = localDateTime,
                windspeed_120m = it.wind.speed,
                weatherType = WeatherType.fromWMO(it.weather[0].id)
            )
        } else null
    }
}

fun mapDailyWeatherByDayOfWeek(dailyWeatherList: List<Daily>): Map<DayOfWeek, List<Daily>> {
    val dailyWeatherMap = mutableMapOf<DayOfWeek, MutableList<Daily>>()

    dailyWeatherList.forEach { dailyWeather ->
        val dayOfWeek = dailyWeather.time.dayOfWeek
        dailyWeatherMap.getOrPut(dayOfWeek) { mutableListOf() }.add(dailyWeather)
    }

    return dailyWeatherMap
}

fun City.toEntity(): CityEntity {
    return CityEntity(id ?: 0, name, country, latitude, longitude)
}

fun CityEntity.toDomainModel(): City {
    return City(id, name, country, latitude, longitude)
}

const val TEMP_0C = 273.0
