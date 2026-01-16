package ru.teacherarmy.homework1.data.di

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import ru.teacherarmy.homework1.data.location.DefaultLocationTracker
import ru.teacherarmy.homework1.domain.location.LocationTracker
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object LocationModule {
    @Provides
    @Singleton
    fun provideLocationTracker(defaultLocationTracker: DefaultLocationTracker): LocationTracker {
        return defaultLocationTracker
    }

    @Provides
    @Singleton
    fun provideContext(@ApplicationContext context: Context): Context {
        return context
    }
}