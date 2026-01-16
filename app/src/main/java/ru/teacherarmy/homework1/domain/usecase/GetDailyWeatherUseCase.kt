package ru.teacherarmy.homework1.domain.usecase

import android.os.Build
import androidx.annotation.RequiresApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import ru.teacherarmy.homework1.domain.model.Daily
import ru.teacherarmy.homework1.domain.repository.WeatherRepository
import ru.teacherarmy.homework1.domain.usecase.results.Resource
import java.time.DayOfWeek
import javax.inject.Inject

class GetDailyWeatherUseCase @Inject constructor(
    private val weatherRepo: WeatherRepository
) {
    operator fun invoke(lat:Double, lon:Double) :Flow<Resource<Map<DayOfWeek, List<Daily>>>> = flow{
        try {
            emit(Resource.Success(weatherRepo.getDailyWeather(lat , lon)))
        }catch (e:Exception){
            emit(Resource.Error(e.message))
        }
    }
}