package com.example.newsapp.model

data class NewsResponse(
    val status: String,
    val totalResults: Int,
    val articles: List<ArticleResponse>
    )

class ArticleResponse(
    val source: Source,
    val urlToImage: String,
    val title: String,
    val author: String,
    val description: String
)

class Source(
    val id: String,
    val name: String
)