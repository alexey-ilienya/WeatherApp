package ru.teacherarmy.homework1.presentation.viewmodels

import android.annotation.SuppressLint
import android.content.Context
import android.location.Geocoder
import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import ru.teacherarmy.homework1.domain.location.LocationTracker
import ru.teacherarmy.homework1.domain.usecase.GetCurrentWeatherUseCase
import ru.teacherarmy.homework1.domain.usecase.results.Resource
import ru.teacherarmy.homework1.presentation.states.CurrentWeatherState
import java.util.Locale
import javax.inject.Inject

@HiltViewModel
class CurrentWeatherViewModel @Inject constructor(
    private val context : Context,
    private val locationTracker: LocationTracker,
    private val getCurrentWeather: GetCurrentWeatherUseCase
) : ViewModel() {

    private val _city = mutableStateOf("")
    var city: State<String> = _city
    var state by mutableStateOf(CurrentWeatherState())
        private set
    @SuppressLint("SuspiciousIndentation")
    fun fetchCurrentWeather(latitude: Double? = null, longitude: Double? = null) {
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
                _city.value = getCityName(location.first, location.second).toString()
                val weatherFlow = getCurrentWeather.invoke(location.first, location.first)

                weatherFlow.collect { resource->
                    when (resource) {
                        is Resource.Success -> {
                            state = state.copy(isLoading = false, data = resource.data,error = null)
                        }
                        is Resource.Error -> {
                            state = state.copy(isLoading = false, data = resource.data,error = null)
                        }

                    }
                }

            } catch (e: Exception) {
                state = state.copy(isLoading = false, data =null,error = e.localizedMessage)
            }
        }
    }

    suspend fun getCityName(latitude: Double, longitude: Double): String? {
        return withContext(Dispatchers.IO) {
            val geocoder = Geocoder(  context, Locale.getDefault())
            try {
                val addresses = geocoder.getFromLocation(latitude, longitude, 1)
                if (addresses?.isNotEmpty() == true) {
                    val address = addresses[0]
                    val city = address.locality
                    city
                } else {
                    null
                }
            } catch (e: Exception) {
                null
            }
        }
    }
}
