package ru.teacherarmy.domain.usecase

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import ru.teacherarmy.domain.model.City
import ru.teacherarmy.domain.model.SearchResults
import ru.teacherarmy.domain.repository.SearchResultsRepository
import ru.teacherarmy.domain.usecase.results.Result
import javax.inject.Inject

class GetSearchResultsUseCase @Inject constructor(private val repo: SearchResultsRepository){

    operator fun invoke( cityname : String ): Flow<Result<List<SearchResults>>> = flow {

        try {
            emit(Result.Success(repo.GetSearchResutls(cityname)))
        } catch (e: Exception) {
            emit(Result.Error(e.message))
        }

    }

    suspend operator fun invoke(city: City) {
        repo.insertCity(city)
    }


    operator fun invoke(): Flow<List<City>> {
        return repo.getAllCities()
    }
}