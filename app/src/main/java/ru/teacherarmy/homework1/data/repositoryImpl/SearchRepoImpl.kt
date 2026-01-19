package ru.teacherarmy.homework1.data.repositoryImpl

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import ru.teacherarmy.homework1.BuildConfig
import ru.teacherarmy.homework1.data.db.dao.CityDao
import ru.teacherarmy.homework1.data.mapper.toDomainModel
import ru.teacherarmy.homework1.data.mapper.toEntity
import ru.teacherarmy.homework1.data.network.ApiRequest
import ru.teacherarmy.homework1.data.network.SearchApi
import ru.teacherarmy.homework1.domain.model.City
import ru.teacherarmy.homework1.domain.model.SearchResults
import ru.teacherarmy.homework1.domain.repository.SearchResultsRepository
import javax.inject.Inject

class SearchRepoImpl @Inject constructor(
    private val searchApi: SearchApi,
    private val cityDao: CityDao
): SearchResultsRepository, ApiRequest() {
    override suspend fun GetSearchResutls(cityName: String): List<SearchResults> {
        val searchQuery = "${cityName.trim()},${WeatherRepoImpl.COUNTRY_CODE_RU}"
        val response = apiRequest {
            searchApi.getSearchResults(searchQuery, BuildConfig.WEATHER_API_KEY)
        }
        return response?.map {
            SearchResults(it.lat, it.lon, it.nameInfo?.name ?: "", it.country)
        } ?: arrayListOf()
    }

    override suspend fun insertCity(city: City) {
        cityDao.insertCity(city.toEntity())
    }

    override suspend fun deleteCity(city: City) {
        cityDao.deleteCity(city.toEntity())
    }

    override fun getAllCities(): Flow<List<City>> {
        return cityDao.getAllCities().map { entities -> entities.map { it.toDomainModel() } }
    }
}