package ru.teacherarmy.homework1.domain.usecase

import ru.teacherarmy.homework1.domain.model.City
import ru.teacherarmy.homework1.domain.repository.SearchResultsRepository
import javax.inject.Inject

class DeleteCityUseCase @Inject constructor(private val repo: SearchResultsRepository) {
    suspend operator fun invoke(city: City) {
        repo.deleteCity(city)
    }
}