package com.example.newsapp.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.newsapp.model.ArticleResponse
import com.example.newsapp.utils.NewsScreens

@Composable
fun NewsList(navController: NavController, articleResult: List<ArticleResponse>) {
    LazyColumn {
        itemsIndexed(articleResult) { index, article ->
            ArticleCard(articleResponse = article) {
                navController.navigate(NewsScreens.DetailScreen.route + "/$index")
            }
        }
    }
}

@Composable
fun ArticleCard(articleResponse: ArticleResponse, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .clickable(onClick = onClick)
            .clip(shape = RoundedCornerShape(10.dp))
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(color = Color.Gray)
        ) {
            Text(
                text = articleResponse.title,
                style = MaterialTheme.typography.h5,
                fontStyle = FontStyle.Italic
            )
            AsyncImage(
                model = articleResponse.urlToImage,
                contentDescription = articleResponse.description,
            )
            Text(text = "Author: " + articleResponse.author)
            Text(text = "Source: " + articleResponse.source.name)
        }
    }
}