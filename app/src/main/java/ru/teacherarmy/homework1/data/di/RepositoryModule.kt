package ru.teacherarmy.homework1.data.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import ru.teacherarmy.homework1.data.db.dao.CityDao
import ru.teacherarmy.homework1.data.network.SearchApi
import ru.teacherarmy.homework1.data.network.WeatherApi
import ru.teacherarmy.homework1.data.repositoryImpl.SearchRepoImpl
import ru.teacherarmy.homework1.data.repositoryImpl.WeatherRepoImpl
import ru.teacherarmy.homework1.domain.repository.SearchResultsRepository
import ru.teacherarmy.homework1.domain.repository.WeatherRepository
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class RepositoryModule {
    @Provides
    @Singleton
    fun provideWeatherRepository(weatherApi: WeatherApi): WeatherRepository {
        return WeatherRepoImpl(weatherApi)
    }

    @Provides
    @Singleton
    fun  provideSearchRepository(searchApi: SearchApi, cityDao: CityDao) : SearchResultsRepository {
        return SearchRepoImpl(searchApi, cityDao)
    }
}