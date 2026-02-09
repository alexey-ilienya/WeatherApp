package ru.teacherarmy.presentation.viewmodels

import android.annotation.SuppressLint
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import ru.teacherarmy.domain.usecase.results.Result
import ru.teacherarmy.data.location.LocationTracker
import ru.teacherarmy.domain.usecase.GetHourlyWeatherUseCase
import ru.teacherarmy.presentation.states.HourlyWeatherState
import javax.inject.Inject

@HiltViewModel
class HourlyWeatherViewModel @Inject constructor(
    private val getHourlyWeather: GetHourlyWeatherUseCase,
    private val locationTracker: LocationTracker
): ViewModel() {

    private val _state = MutableStateFlow(HourlyWeatherState())
    val state = _state.asStateFlow()

    @SuppressLint("SuspiciousIndentation")
    fun fetchHourlyWeather(latitude: Double? = null, longitude: Double? = null) {
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
                val weatherFlow = getHourlyWeather.invoke(location.first,location.second)

                weatherFlow.collect { resource->
                    when (resource) {
                        is Result.Success -> {
                            _state.value = _state.value.copy(isLoading = false, data = resource.data, error = null)
                        }
                        is Result.Error -> {
                            _state.value = _state.value.copy(isLoading = false, data = resource.data, error = null)
                        }
                    }
                }
            }
            catch (e: Exception) {
                _state.value = _state.value.copy(isLoading = false, data =null, error = e.localizedMessage)
            }
        }
    }

}
