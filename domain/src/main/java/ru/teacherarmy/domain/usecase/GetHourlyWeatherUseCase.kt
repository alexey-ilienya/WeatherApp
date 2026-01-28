package ru.teacherarmy.domain.usecase

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import ru.teacherarmy.domain.model.Hourly
import ru.teacherarmy.domain.repository.WeatherRepository
import ru.teacherarmy.domain.usecase.results.Result
import javax.inject.Inject

class GetHourlyWeatherUseCase @Inject constructor(
    private val weatherRepo: WeatherRepository
) {
    operator fun  invoke(lat:Double, lon:Double) :Flow<Result<List<Hourly>>> = flow{
        try {
            emit(Result.Success(weatherRepo.getHourlyWeather(lat , lon)))
        }catch (e:Exception){
            emit(Result.Error(e.message))
        }
    }
}