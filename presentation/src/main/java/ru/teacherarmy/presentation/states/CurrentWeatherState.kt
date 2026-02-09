package ru.teacherarmy.presentation.states

import ru.teacherarmy.domain.model.CurrentWeather

data class CurrentWeatherState(
    val isLoading : Boolean = false,
    val data : CurrentWeather? = null,
    val error: String? = null
)
