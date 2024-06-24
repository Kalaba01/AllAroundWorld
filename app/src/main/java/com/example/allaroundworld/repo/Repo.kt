package com.example.allaroundworld.repo

import com.example.allaroundworld.network.ApiBuilder
import com.example.allaroundworld.network.NewsModel
import retrofit2.Response

class Repo {
    suspend fun newProvider(
        country: String,
    ): Response<NewsModel> {
        return ApiBuilder.apiProvider().getNewsFromServer(country = country)
    }
}
