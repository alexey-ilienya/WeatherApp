package ru.teacherarmy.data.network

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query
import ru.teacherarmy.homework1.data.model.SearchResponse

interface SearchApi {
    @GET("/geo/1.0/direct")
    suspend fun getSearchResults(@Query("q") searchQuery: String, @Query("appid") appId: String, @Query("lang") lang: String = "ru"): Response<List<SearchResponse>>
}