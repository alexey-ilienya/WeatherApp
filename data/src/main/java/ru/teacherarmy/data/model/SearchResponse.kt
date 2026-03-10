package ru.teacherarmy.homework1.data.model

import com.google.gson.annotations.SerializedName

data class SearchResponse(
    @SerializedName("local_names")
    val nameInfo: NameInfo?,
    val lat: Double,
    val lon: Double,
    val country: String,
)
