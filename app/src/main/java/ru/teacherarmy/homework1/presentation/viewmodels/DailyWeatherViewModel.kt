package ru.teacherarmy.homework1.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import ru.teacherarmy.domain.usecase.results.Result
import ru.teacherarmy.domain.location.LocationTracker
import ru.teacherarmy.domain.usecase.GetDailyWeatherUseCase
import ru.teacherarmy.homework1.presentation.states.DailyWeatherState
import javax.inject.Inject

@HiltViewModel
class DailyWeatherViewModel @Inject constructor(
    private val locationTracker: LocationTracker,
    private val getDailyWeather: GetDailyWeatherUseCase
):ViewModel() {
    private val _state = MutableStateFlow(DailyWeatherState())
    val state = _state.asStateFlow()

    fun fetchDailyWeather(latitude: Double? = null, longitude: Double? = null) {
        viewModelScope.launch {

            _state.value = _state.value.copy(isLoading = true)

            try {
                val location = if (latitude != null && longitude != null) {
                    Pair(latitude, longitude)
                } else {
                    val currentlocation = locationTracker.getLocation()
                    if (currentlocation != null) {
                        Pair(currentlocation.latitude, currentlocation.longitude)
                    } else {
                        _state.value = _state.value.copy(
                            isLoading = false,
                            data = null,
                            error = "Location not available"
                        )
                        return@launch
                    }
                }

                val weatherFlow = getDailyWeather.invoke(location.first,location.second)

                weatherFlow.collect { resource ->
                    when (resource) {
                        is Result.Success -> {
                            _state.value = _state.value.copy(isLoading = false, data = resource.data, error = null)
                        }
                        is Result.Error -> {
                            _state.value = _state.value.copy(isLoading = false, data = resource.data, error = null)
                        }
                    }
                }

            } catch (e: Exception) {
                _state.value = _state.value.copy(isLoading = false, data = null, error = e.localizedMessage)
            }

        }
    }
}
