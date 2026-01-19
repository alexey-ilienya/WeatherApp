package ru.teacherarmy.homework1.data.di

import dagger.Binds
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
abstract class RepositoryModule {
    @Singleton
    @Binds
    abstract fun provideWeatherRepository(weatherRepoImpl: WeatherRepoImpl): WeatherRepository

    @Singleton
    @Binds
    abstract fun provideSearchRepository(searchRepoImpl: SearchRepoImpl) : SearchResultsRepository
}