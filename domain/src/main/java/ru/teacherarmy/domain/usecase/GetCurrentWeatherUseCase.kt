package ru.teacherarmy.domain.usecase

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import ru.teacherarmy.domain.model.CurrentWeather
import ru.teacherarmy.domain.repository.WeatherRepository
import ru.teacherarmy.domain.usecase.results.Result
import javax.inject.Inject

class GetCurrentWeatherUseCase
    @Inject
    constructor(
        private val weatherRepo: WeatherRepository,
    ) {
        operator fun invoke(
            lat: Double,
            lon: Double,
        ): Flow<Result<CurrentWeather>> =
            flow {
                try {
                    emit(Result.Success(weatherRepo.getCurrentWeather(lat, lon)))
                } catch (e: Exception) {
                    emit(Result.Error(e.message))
                }
            }
    }
