package com.example.newsapp.rest

import com.example.newsapp.model.NewsResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsApiService {

    @GET("/v2/top-headlines?country=us&apiKey=e68b3e49ac77476fb201c213f45f7c3d")
    suspend fun fetchNews(): Response<NewsResponse>

    @GET("/v2/top-headlines?country=us&apiKey=e68b3e49ac77476fb201c213f45f7c3d")
    suspend fun categoryNews(@Query("category") category: String): Response<NewsResponse>

    @GET("/v2/top-headlines?country=us&apiKey=e68b3e49ac77476fb201c213f45f7c3d")
    suspend fun searchNews(@Query("q") searchString: String): Response<NewsResponse>

}