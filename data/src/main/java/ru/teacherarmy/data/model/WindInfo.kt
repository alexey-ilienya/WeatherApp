package ru.teacherarmy.data.model

import com.google.gson.annotations.SerializedName

data class WindInfo(
    val speed: Double,
    @SerializedName("deg")
    val degrees: Double,
    val gust: Double,
)
