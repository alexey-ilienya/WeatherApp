package ru.teacherarmy.presentation.states

import ru.teacherarmy.domain.model.SearchResults

data class SearchResultsState (
    val isLoading : Boolean = false,
    val data : List<SearchResults>? = null,
    val error : String? = null
)

