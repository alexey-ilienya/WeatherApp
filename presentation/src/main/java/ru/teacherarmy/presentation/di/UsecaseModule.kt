package ru.teacherarmy.presentation.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import ru.teacherarmy.domain.repository.SearchResultsRepository
import ru.teacherarmy.domain.repository.WeatherRepository
import ru.teacherarmy.domain.usecase.DeleteCityUseCase
import ru.teacherarmy.domain.usecase.GetCurrentWeatherUseCase
import ru.teacherarmy.domain.usecase.GetDailyWeatherUseCase
import ru.teacherarmy.domain.usecase.GetHourlyWeatherUseCase
import ru.teacherarmy.domain.usecase.GetSearchResultsUseCase

@Module
@InstallIn(ViewModelComponent::class)
object UsecaseModule {
    @Provides
    fun provideGetCurrentWeather(weatherRepo: WeatherRepository): GetCurrentWeatherUseCase {
        return GetCurrentWeatherUseCase(weatherRepo)
    }

    @Provides
    fun provideGetDailyWeather(weatherRepo: WeatherRepository): GetDailyWeatherUseCase {
        return GetDailyWeatherUseCase(weatherRepo)
    }

    @Provides
    fun provideGetHourlyWeather(weatherRepo: WeatherRepository): GetHourlyWeatherUseCase {
        return GetHourlyWeatherUseCase(weatherRepo)
    }

    @Provides
    fun provideSearchResults(searchRepo: SearchResultsRepository): GetSearchResultsUseCase {
        return GetSearchResultsUseCase(searchRepo)
    }

    @Provides
    fun provideDeleteCityUseCase(searchRepo: SearchResultsRepository): DeleteCityUseCase {
        return DeleteCityUseCase(searchRepo)
    }
}