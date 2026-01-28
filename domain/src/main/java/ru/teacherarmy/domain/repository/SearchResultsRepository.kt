package ru.teacherarmy.domain.repository

import kotlinx.coroutines.flow.Flow
import ru.teacherarmy.domain.model.City
import ru.teacherarmy.domain.model.SearchResults

interface SearchResultsRepository {
    suspend fun GetSearchResutls(cityName : String): List<SearchResults>

    suspend fun insertCity(city: City)

    suspend fun deleteCity(city: City)

    fun getAllCities(): Flow<List<City>>
}