package ru.teacherarmy.homework1.presentation.viewmodels

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import ru.teacherarmy.homework1.domain.model.City
import ru.teacherarmy.homework1.domain.usecase.DeleteCityUseCase
import ru.teacherarmy.homework1.domain.usecase.GetSearchResultsUseCase
import ru.teacherarmy.homework1.domain.usecase.results.Resource
import ru.teacherarmy.homework1.presentation.states.SearchResultsState
import javax.inject.Inject

@HiltViewModel
class SearchCityViewModel @Inject constructor(
    private val getSearchResults: GetSearchResultsUseCase,
    private  val deleteCityUseCase: DeleteCityUseCase,
) : ViewModel() {

    private val _searchText = MutableStateFlow("")
    var searchText = _searchText.asStateFlow()

    private val _state = MutableStateFlow(SearchResultsState())
    var state = _state.asStateFlow()

    var selectedCity = mutableStateOf<City?>(null)
        private set

    var switchState by mutableStateOf(true)
        internal set

    var  selectedLatitude = mutableStateOf<Double?>(null)
        private set

    var  selectedLongitude = mutableStateOf<Double?>(null)
        private set

    fun onSearchTextChange(text: String) {
        _searchText.value = text
        fetchSearchResults(text)
    }

    fun toggleSwitchState(b: Boolean) {
        selectedLongitude.value = null
        selectedLatitude.value = null
        switchState = b

    }

    private fun fetchSearchResults(query: String) {
        viewModelScope.launch {
            try {
                _state.value = _state.value.copy(data = null, isLoading = true, error = null)
                val searchResultsFlow = getSearchResults(query)
                searchResultsFlow.collect { resource ->
                    when (resource) {
                        is Resource.Success -> {
                            val data = resource.data

                            _state.value = _state.value.copy(data = data, isLoading = false, error = null)
                        }

                        is Resource.Error -> {
                            _state.value = _state.value.copy(
                                data = null,
                                isLoading = false,
                                error = "can't fetch"
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