package com.example.newsapp.rest

import com.example.newsapp.model.NewsResponse
import com.example.newsapp.utils.navigation.Constants.QUERY
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsApiService {

    @GET(QUERY)
    suspend fun fetchNews(): Response<NewsResponse>

    @GET(QUERY)
    suspend fun categoryNews(@Query("category") category: String): Response<NewsResponse>

    @GET(QUERY)
    suspend fun searchNews(@Query("q") searchString: String): Response<NewsResponse>

}