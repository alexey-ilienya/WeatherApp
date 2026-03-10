package ru.teacherarmy.data.repositoryImpl

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import ru.teacherarmy.data.BuildConfig
import ru.teacherarmy.data.db.dao.CityDao
import ru.teacherarmy.data.mapper.toDomainModel
import ru.teacherarmy.data.mapper.toEntity
import ru.teacherarmy.data.network.ApiRequest
import ru.teacherarmy.data.network.SearchApi
import ru.teacherarmy.domain.model.City
import ru.teacherarmy.domain.model.SearchResults
import ru.teacherarmy.domain.repository.SearchResultsRepository
import javax.inject.Inject

class SearchRepoImpl
    @Inject
    constructor(
        private val searchApi: SearchApi,
        private val cityDao: CityDao,
    ) : ApiRequest(),
        SearchResultsRepository {
        override suspend fun getSearchResults(cityName: String): List<SearchResults> {
            val searchQuery = "${cityName.trim()},${WeatherRepoImpl.COUNTRY_CODE_RU}"
            val response =
                apiRequest {
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

        override fun getAllCities(): Flow<List<City>> =
            cityDao
                .getAllCities()
                .map { entities -> entities.map { it.toDomainModel() } }
    }
