package ru.teacherarmy.homework1.presentation.states

import ru.teacherarmy.domain.model.Daily
import java.time.DayOfWeek

data class DailyWeatherState(
    val isLoading : Boolean= false,
    val data : Map<DayOfWeek, List<Daily>>?  = null,
    val error: String? = null
)