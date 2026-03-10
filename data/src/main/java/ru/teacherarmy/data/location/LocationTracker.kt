package ru.teacherarmy.data.location

import android.location.Location

interface LocationTracker {
    suspend fun getLocation(): Location?
}
