package ru.teacherarmy.homework1.presentation.states

import ru.teacherarmy.homework1.domain.model.SearchResults

data class SearchResultsState (
    val isLoading : Boolean = false,
    val data : List<SearchResults>? = null,
    val error : String? = null
)