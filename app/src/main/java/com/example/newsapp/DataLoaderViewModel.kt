package com.example.newsapp

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.newsapp.model.ArticleResponse
import com.example.newsapp.rest.Datasource
import kotlinx.coroutines.launch

class DataLoaderViewModel : ViewModel() {

    private val _news = MutableLiveData<Result<List<ArticleResponse>>>()
    val news: LiveData<Result<List<ArticleResponse>>> = _news

    fun loadNews() {
        viewModelScope.launch {
            try {
                val response = Datasource().loadNews()
                _news.postValue(Result.success(response))
            } catch (e: Exception) {
                _news.postValue(Result.error(e))
            }
        }
    }
}


sealed class Result<out T : Any> {
    data class Loading(val message: String = "") : Result<Nothing>()
    data class Success<out T : Any>(val data: T) : Result<T>()
    data class Error(val exception: Exception) : Result<Nothing>()

    companion object {
        fun <T : Any> loading(message: String = ""): Result<T> = Loading(message)
        fun <T : Any> success(data: T): Result<T> = Success(data)
        fun error(exception: Exception): Result<Nothing> = Error(exception)
    }
}