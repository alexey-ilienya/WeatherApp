package ru.teacherarmy.domain.usecase

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import ru.teacherarmy.domain.model.Daily
import ru.teacherarmy.domain.repository.WeatherRepository
import ru.teacherarmy.domain.usecase.results.Result
import java.time.DayOfWeek
import javax.inject.Inject

class GetDailyWeatherUseCase
    @Inject
    constructor(
        private val weatherRepo: WeatherRepository,
    ) {
        operator fun invoke(
            lat: Double,
            lon: Double,
        ): Flow<Result<Map<DayOfWeek, List<Daily>>>> =
            flow {
                try {
                    emit(Result.Success(weatherRepo.getDailyWeather(lat, lon)))
                } catch (e: Exception) {
                    emit(Result.Error(e.message))
                }
            }
    }
