package ru.teacherarmy.homework1.domain.model

data class City (
    var id: Int?,
    var name: String?,
    val country: String?,
    val latitude: Double?,
    val longitude: Double?
)