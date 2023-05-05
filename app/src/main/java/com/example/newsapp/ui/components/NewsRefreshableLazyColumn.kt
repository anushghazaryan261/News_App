package com.example.newsapp.ui.components

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import com.example.newsapp.model.ArticleResponse
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState


@Composable
fun NewsRefreshableLazyColumn(
    articles: List<ArticleResponse>,
    onRefresh: () -> Unit,
    navController: NavController,
) {
    val state = rememberSwipeRefreshState(isRefreshing = false)

    SwipeRefresh(
        state = state,
        onRefresh = {
            onRefresh()
            state.isRefreshing = false
        }
    ) {
        NewsList(navController, articles)
    }
}