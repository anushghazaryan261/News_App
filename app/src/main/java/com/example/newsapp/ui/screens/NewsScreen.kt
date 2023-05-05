package com.example.newsapp.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.newsapp.model.ArticleResponse
import com.example.newsapp.model.Options
import com.example.newsapp.ui.components.NewsRefreshableLazyColumn
import com.example.newsapp.ui.components.OptionMenu
import com.example.newsapp.ui.components.SearchView

@Composable
fun NewsScreen(navController: NavHostController,
                articles: List<ArticleResponse>,
                onSearch: (String) -> Unit,
                onRefresh: () -> Unit,
                onSelectFilter: (String) -> Unit) {
    Column {
        Row (
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically,
        ){
            OptionMenu(
                filters = listOf(
                    Options(1, "Business"),
                    Options(2, "Entertainment"),
                    Options(3, "General"),
                    Options(4, "Health"),
                ),
                onFilterSelected = { option ->
                    onSelectFilter(option.name)  // API Request with filter
                }
            )

            SearchView(
                onSearch = {
                    onSearch(it)
                }
            )
        }
        NewsRefreshableLazyColumn(
            articles = articles,
            onRefresh = onRefresh,
            navController = navController
        )
    }
}