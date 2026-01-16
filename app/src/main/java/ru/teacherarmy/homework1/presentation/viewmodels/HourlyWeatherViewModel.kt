package ru.teacherarmy.homework1.presentation.viewmodels

import android.annotation.SuppressLint
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import ru.teacherarmy.homework1.domain.location.LocationTracker
import ru.teacherarmy.homework1.domain.usecase.GetHourlyWeatherUseCase
import ru.teacherarmy.homework1.domain.usecase.results.Resource
import ru.teacherarmy.homework1.presentation.states.HourlyWeatherState
import javax.inject.Inject

@HiltViewModel
class HourlyWeatherViewModel @Inject constructor(
    private  val gethourlyWeather: GetHourlyWeatherUseCase,
    private  val locationTracker: LocationTracker
):ViewModel() {

    var state by mutableStateOf(HourlyWeatherState())

    @SuppressLint("SuspiciousIndentation")
    @RequiresApi(Build.VERSION_CODES.O)
    fun fetchHourlyWeather(latitude: Double? = null, longitude: Double? = null) {
        viewModelScope.launch {

            state= state.copy(isLoading = true)


            try {
                val location = if (latitude != null && longitude != null) {
                    Pair(latitude, longitude)
                } else {
                    val currentlocation = locationTracker.getLocation()
                    if (currentlocation != null) {
                        Pair(currentlocation.latitude, currentlocation.longitude)
                    } else {
                        state = state.copy(
                            isLoading = false,
                            data = null,
                            error = "Location not available"
                        )
                        return@launch
                    }
                }
                val weatherFlow = gethourlyWeather.invoke(location.first,location.second)

                weatherFlow.collect { resource->
                    when (resource) {
                        is Resource.Success -> {
                            state = state.copy(isLoading = false, data = resource.data, error = null)
                        }
                        is Resource.Error -> {
                            state = state.copy(isLoading = false, data = resource.data, error = null)
                        }
                    }
                }
            }
            catch (e: Exception) {
                state = state.copy(isLoading = false, data =null,error = e.localizedMessage)
            }
        }
    }

}