package ru.teacherarmy.homework1.domain.usecase

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import ru.teacherarmy.homework1.domain.model.Daily
import ru.teacherarmy.homework1.domain.repository.WeatherRepository
import ru.teacherarmy.homework1.domain.usecase.results.Result
import java.time.DayOfWeek
import javax.inject.Inject

class GetDailyWeatherUseCase @Inject constructor(
    private val weatherRepo: WeatherRepository
) {
    operator fun invoke(lat:Double, lon:Double) :Flow<Result<Map<DayOfWeek, List<Daily>>>> = flow{
        try {
            emit(Result.Success(weatherRepo.getDailyWeather(lat , lon)))
        }catch (e:Exception){
            emit(Result.Error(e.message))
        }
    }
}