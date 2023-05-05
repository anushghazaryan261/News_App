package com.example.newsapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.newsapp.ui.screens.NewsDetailsScreen
import com.example.newsapp.ui.screens.NewsScreen
import com.example.newsapp.utils.NewsScreens

class MainActivity : ComponentActivity() {
    private val viewModel by viewModels<DataLoaderViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.loadNews()
        viewModel.news.observe(this) { news ->
            setContent {
                val navController = rememberNavController()

                NavHost(
                    navController = navController,
                    startDestination = NewsScreens.HomeScreen.route
                ) {
                    // Home Screen
                    composable(route = NewsScreens.HomeScreen.route) {
                        NewsScreen(
                            navController = navController,
                            articles = news,
                            onRefresh = viewModel::loadNews,
                            onSearch = {
                                viewModel.searchNews(it)
                            },
                            onSelectFilter = {
                                viewModel.categoryNews(it)
                            }
                        )
                    }

                    // Details Screen
                    composable(
                        route = NewsScreens.DetailScreen.route + "/{index}",
                        arguments = listOf(navArgument(name = "index") {
                            type = NavType.IntType
                        })
                    ) { entry ->
                        val index = entry.arguments?.getInt("index")

                        index?.let {
                            news[it]
                        }?.let {
                            NewsDetailsScreen(navController, it)
                        }
                    }
                }
            }
        }
    }
}

