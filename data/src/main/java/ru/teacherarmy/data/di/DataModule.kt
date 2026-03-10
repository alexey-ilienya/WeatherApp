package ru.teacherarmy.data.di

import android.app.Application
import android.content.Context
import androidx.room.Room
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import ru.teacherarmy.data.db.CityDatabase
import ru.teacherarmy.data.db.dao.CityDao
import ru.teacherarmy.data.network.SearchApi
import ru.teacherarmy.data.network.WeatherApi
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataModule {
    @Provides
    @Singleton
    fun weatherApiService(): WeatherApi =
        Retrofit
            .Builder()
            .baseUrl("https://api.openweathermap.org/data/2.5/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(WeatherApi::class.java)

    @Provides
    @Singleton
    fun searchApiService(): SearchApi =
        Retrofit
            .Builder()
            .baseUrl("https://api.openweathermap.org/geo/1.0/direct/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(SearchApi::class.java)

    @Provides
    @Singleton
    fun provideFusedLocationClient(app: Application): FusedLocationProviderClient =
        LocationServices
            .getFusedLocationProviderClient(app)

    @Provides
    @Singleton
    fun provideSearchDatabase(
        @ApplicationContext context: Context,
    ): CityDatabase =
        Room
            .databaseBuilder(context, CityDatabase::class.java, "app_database")
            .fallbackToDestructiveMigration()
            .build()

    @Provides
    @Singleton
    fun provideCityDao(appDatabase: CityDatabase): CityDao = appDatabase.cityDao()
}
