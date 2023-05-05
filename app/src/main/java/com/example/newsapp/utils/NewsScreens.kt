package com.example.newsapp.utils

sealed class NewsScreens(val route: String) {
    object HomeScreen : NewsScreens("home_screen")
    object DetailScreen : NewsScreens("detail_screen")
}
