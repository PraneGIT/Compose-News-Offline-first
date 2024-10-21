package com.example.compose_news.data

import com.example.compose_news.Models.Article
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsApiService {
    @GET("v2/top-headlines")
    suspend fun getTopHeadlines(
        @Query("country") country: String = "us",
        @Query("apiKey") apiKey: String
    ): NewsResponse
}

data class NewsResponse(val articles: List<Article>)
