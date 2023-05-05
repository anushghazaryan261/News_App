package com.example.newsapp

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.newsapp.model.ArticleResponse
import com.example.newsapp.rest.NewsRepo
import kotlinx.coroutines.launch

class DataLoaderViewModel : ViewModel() {

    private val _news = MutableLiveData<List<ArticleResponse>>()
    val news: LiveData<List<ArticleResponse>> = _news

    fun loadNews() {
        viewModelScope.launch {
            try {
                val response = NewsRepo().loadNews()
                _news.postValue(response)
            } catch (e: Exception) {
                Log.d(">>>", e.message.toString())
            }
        }
    }
    fun categoryNews(category: String) {
        viewModelScope.launch {
            try {
                val response = NewsRepo().categoryNews(category = category.lowercase())
                _news.postValue(response)
            } catch (e: Exception) {
                Log.d(">>>", e.message.toString())
            }
        }
    }
    fun searchNews(searchString: String) {
        viewModelScope.launch {
            try {
                val response = NewsRepo().searchNews(searchString = searchString.lowercase())
                _news.postValue(response)
            } catch (e: Exception) {
                Log.d(">>>", e.message.toString())
            }
        }
    }
}