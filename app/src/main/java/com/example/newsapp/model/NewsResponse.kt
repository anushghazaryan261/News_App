package com.example.newsapp.model

data class NewsResponse(
    val status: String,
    val totalResults: Int,
    val articles: List<ArticleResponse>
    )

class ArticleResponse(
    val urlToImage: String,
    val description: String
)