package ru.teacherarmy.data.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import ru.teacherarmy.data.repositoryImpl.SearchRepoImpl
import ru.teacherarmy.data.repositoryImpl.WeatherRepoImpl
import ru.teacherarmy.domain.repository.SearchResultsRepository
import ru.teacherarmy.domain.repository.WeatherRepository
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {
    @Singleton
    @Binds
    abstract fun provideWeatherRepository(weatherRepoImpl: WeatherRepoImpl): WeatherRepository

    @Singleton
    @Binds
    abstract fun provideSearchRepository(searchRepoImpl: SearchRepoImpl): SearchResultsRepository
}
