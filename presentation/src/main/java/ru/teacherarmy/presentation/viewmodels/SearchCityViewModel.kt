package ru.teacherarmy.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import ru.teacherarmy.domain.model.City
import ru.teacherarmy.domain.usecase.DeleteCityUseCase
import ru.teacherarmy.domain.usecase.GetSearchResultsUseCase
import ru.teacherarmy.domain.usecase.results.Result
import ru.teacherarmy.presentation.states.SearchResultsState
import javax.inject.Inject

@HiltViewModel
class SearchCityViewModel
    @Inject
    constructor(
        private val getSearchResults: GetSearchResultsUseCase,
        private val deleteCityUseCase: DeleteCityUseCase,
    ) : ViewModel() {
        private val _searchText = MutableStateFlow("")
        val searchText = _searchText.asStateFlow()

        private val _state = MutableStateFlow(SearchResultsState())
        val state = _state.asStateFlow()

        private val _selectedCity = MutableStateFlow<City?>(null)
        val selectedCity = _selectedCity.asStateFlow()

        private val _switchState = MutableStateFlow(true)
        val switchState = _switchState.asStateFlow()

        private val _selectedLatitude = MutableStateFlow<Double?>(null)
        val selectedLatitude = _selectedLatitude.asStateFlow()

        private val _selectedLongitude = MutableStateFlow<Double?>(null)
        val selectedLongitude = _selectedLongitude.asStateFlow()

        fun onSearchTextChange(text: String) {
            _searchText.value = text
            fetchSearchResults(text)
        }

        fun toggleSwitchState(b: Boolean) {
            _selectedLongitude.value = null
            _selectedLatitude.value = null
            _switchState.value = b
        }

        fun clearSelectedCity() {
            _selectedCity.value = null
        }

        fun setSelectedCity(city: City) {
            _selectedCity.value = city
            _switchState.value = false
            _selectedLatitude.value = city.latitude
            _selectedLongitude.value = city.longitude
        }

        private fun fetchSearchResults(query: String) {
            viewModelScope.launch {
                try {
                    _state.value = _state.value.copy(data = null, isLoading = true, error = null)
                    val searchResultsFlow = getSearchResults(query)
                    searchResultsFlow.collect { resource ->
                        when (resource) {
                            is Result.Success -> {
                                val data = resource.data
                                _state.value = _state.value.copy(data = data, isLoading = false, error = null)
                            }
                            is Result.Error -> {
                                _state.value =
                                    _state.value.copy(
                                        data = null,
                                        isLoading = false,
                                        error = "can't fetch",
                                    )
                            }
                        }
                    }
                } catch (e: Exception) {
                    _state.value = _state.value.copy(data = null, isLoading = false, error = e.localizedMessage)
                }
            }
        }

        fun insertCity(city: City) {
            viewModelScope.launch {
                getSearchResults(city)
            }
        }

        fun deleteCity(city: City) {
            viewModelScope.launch {
                deleteCityUseCase(city)
            }
        }

        val allCities: Flow<List<City>> = getSearchResults()
    }
