package ru.teacherarmy.homework1.presentation.viewmodels

import android.annotation.SuppressLint
import android.content.Context
import android.location.Geocoder
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import ru.teacherarmy.domain.usecase.results.Result
import ru.teacherarmy.domain.location.LocationTracker
import ru.teacherarmy.domain.usecase.GetCurrentWeatherUseCase
import ru.teacherarmy.homework1.presentation.states.CurrentWeatherState
import java.util.Locale
import javax.inject.Inject

@HiltViewModel
class CurrentWeatherViewModel @Inject constructor(
    private val locationTracker: LocationTracker,
    private val getCurrentWeather: GetCurrentWeatherUseCase
) : ViewModel() {

    private val _city = MutableStateFlow("")
    val city = _city.asStateFlow()
    private val _state = MutableStateFlow(CurrentWeatherState())
    val state = _state.asStateFlow()
    @SuppressLint("SuspiciousIndentation")
    fun fetchCurrentWeather(context: Context, latitude: Double? = null, longitude: Double? = null) {
        viewModelScope.launch {

            _state.value = state.value.copy(isLoading = true)

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
                _city.value = getCityName(context, location.first, location.second).toString()
                val weatherFlow = getCurrentWeather.invoke(location.first, location.first)

                weatherFlow.collect { resource->
                    when (resource) {
                        is Result.Success -> {
                            _state.value = _state.value.copy(isLoading = false, data = resource.data,error = null)
                        }
                        is Result.Error -> {
                            _state.value = _state.value.copy(isLoading = false, data = resource.data,error = null)
                        }

                    }
                }

            } catch (e: Exception) {
                _state.value = _state.value.copy(isLoading = false, data =null,error = e.localizedMessage)
            }
        }
    }

    suspend fun getCityName(context: Context, latitude: Double, longitude: Double): String? {
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
