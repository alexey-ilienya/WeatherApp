package ru.teacherarmy.homework1.domain.usecase

import android.os.Build
import androidx.annotation.RequiresApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import ru.teacherarmy.homework1.domain.model.Hourly
import ru.teacherarmy.homework1.domain.repository.WeatherRepository
import ru.teacherarmy.homework1.domain.usecase.results.Resource
import javax.inject.Inject

class GetHourlyWeatherUseCase @Inject constructor(
    private val weatherRepo: WeatherRepository
) {
    operator fun  invoke(lat:Double, lon:Double) :Flow<Resource<List<Hourly>>> = flow{
        try {
            emit(Resource.Success(weatherRepo.getHourlyWeather(lat , lon)))
        }catch (e:Exception){
            emit(Resource.Error(e.message))
        }
    }
}