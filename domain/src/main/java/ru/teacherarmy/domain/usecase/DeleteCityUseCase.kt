package ru.teacherarmy.domain.usecase

import ru.teacherarmy.domain.model.City
import ru.teacherarmy.domain.repository.SearchResultsRepository
import javax.inject.Inject

class DeleteCityUseCase @Inject constructor(private val repo: SearchResultsRepository) {
    suspend operator fun invoke(city: City) {
        repo.deleteCity(city)
    }
}