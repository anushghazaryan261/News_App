package com.example.newsapp.rest

import com.example.newsapp.model.ArticleResponse

class Datasource {

    suspend fun loadNews(): List<ArticleResponse> {
        return RetrofitHelper.getInstance().create(NewsApiService::class.java).fetchNews().run{
            this.body()?.articles?.map {
                ArticleResponse(it.urlToImage ?: "", it.description ?: "")
            } ?: listOf()
        }
    }
}