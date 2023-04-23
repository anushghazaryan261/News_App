package com.example.newsapp.rest

import com.example.newsapp.model.ArticleResponse
import com.example.newsapp.model.Source

class Datasource {

    suspend fun loadNews(): List<ArticleResponse> {
        return RetrofitHelper.getInstance().create(NewsApiService::class.java).fetchNews().run{
            this.body()?.articles?.map {
                ArticleResponse(it.source?: Source("",""), it.urlToImage?: "", it.title?: "", it.author?:"", it.description?:"")
            } ?: listOf()
        }
    }
    suspend fun categoryNews(category: String): List<ArticleResponse> {
        return RetrofitHelper.getInstance().create(NewsApiService::class.java).categoryNews(category).run{
            this.body()?.articles?.map {
                ArticleResponse(it.source?: Source("",""), it.urlToImage?: "", it.title?: "", it.author?:"", it.description?:"")
            } ?: listOf()
        }
    }
    suspend fun searchNews(searchString: String): List<ArticleResponse> {
        return RetrofitHelper.getInstance().create(NewsApiService::class.java).searchNews(searchString).run{
            this.body()?.articles?.map {
                ArticleResponse(it.source?: Source("",""), it.urlToImage?: "", it.title?: "", it.author?:"", it.description?:"")
            } ?: listOf()
        }
    }
}