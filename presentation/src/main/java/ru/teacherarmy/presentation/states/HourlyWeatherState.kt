package ru.teacherarmy.presentation.states

import ru.teacherarmy.domain.model.Hourly

data class HourlyWeatherState(
    val isLoading: Boolean = false,
    val data: List<Hourly>? = null,
    val error: String? = null,
)
