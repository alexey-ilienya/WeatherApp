package ru.teacherarmy.homework1.presentation.mapper

import ru.teacherarmy.homework1.domain.model.City
import ru.teacherarmy.homework1.domain.model.SearchResults

fun SearchResults.toDomainModel(): City {
    return City(
        id = 0,
        name = name,
        country = country,
        latitude = latitude,
        longitude = longitude
    )
}