package ru.teacherarmy.homework1.domain.location

import android.location.Location

interface LocationTracker {
    suspend fun getLocation() : Location ?
}