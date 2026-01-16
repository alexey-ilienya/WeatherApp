package ru.teacherarmy.homework1.presentation.states

import ru.teacherarmy.homework1.domain.model.CurrentWeather

data class CurrentWeatherState(
    val isLoading : Boolean = false,
    val data : CurrentWeather? = null,
    val error: String? = null
)
