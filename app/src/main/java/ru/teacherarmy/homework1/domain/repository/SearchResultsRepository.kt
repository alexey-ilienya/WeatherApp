package ru.teacherarmy.homework1.domain.repository

import kotlinx.coroutines.flow.Flow
import ru.teacherarmy.homework1.domain.model.City
import ru.teacherarmy.homework1.domain.model.SearchResults

interface SearchResultsRepository {
    suspend fun GetSearchResutls(cityName : String): List<SearchResults>

    suspend fun insertCity(city: City)

    suspend fun deleteCity(city: City)

    fun getAllCities(): Flow<List<City>>
}