package com.example.allaroundworld.network

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("top-headlines")

    suspend fun getNewsFromServer(
        @Query("country") country: String = "us",
        @Query("sortBy") sortBy: String = "publishedAt",
        @Query("apiKey") apiKey: String = "9c07827fb0dc485ebb79e0c088fa675a"
    ): Response<NewsModel>
}
