package com.example.newsapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.newsapp.model.ArticleResponse
import com.example.newsapp.ui.theme.NewsAppTheme

class MainActivity : ComponentActivity() {
    val viewModel by viewModels<DataLoaderViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.loadNews()
        setContent {
            NewsAppTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    val newsResponse = viewModel.news.observeAsState(Result.loading()).value
                    var searchText by remember { mutableStateOf("") }

                    Column {
                        topBar()
                        SearchView(
                            modifier = Modifier.fillMaxWidth(),
                            text = searchText,
                            onTextChanged = { searchText = it },
                            onQuerySubmitted = { query ->
                                // Handle search query here
                            }
                        )
                        NewsList(articleResult = newsResponse)
                    }
                }
            }
        }
    }
}

@Composable
fun NewsList(articleResult: Result<List<ArticleResponse>>) {
    when (articleResult) {
        is Result.Success -> {
            val articles = articleResult.data
            LazyColumn {
                items(articles) { article ->
                    Card(
                        Modifier
                            .padding(8.dp)
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(10.dp)),
                        backgroundColor = Color.LightGray
                    ) {
                        Column(
                            Modifier.padding(8.dp)
                        ) {
                            ArticleCard(article)
                        }
                    }
                }
            }
        }
        is Result.Error -> {
            Text(text = "Something went wrong")
        }
        else -> {

        }
    }
}

@Composable
fun ArticleCard(newsResponse: ArticleResponse) {
    Column {
        AsyncImage(
            model = newsResponse.urlToImage,
            contentDescription = newsResponse.description,
        )
        Text(text = newsResponse.description)
    }
}

@Composable
fun topBar() {
    TopAppBar(title = {
        Text(
            text = "News",
            style = MaterialTheme.typography.h6,
            textAlign = TextAlign.Center,
            fontWeight = FontWeight.Bold
        )
    }
    )
}

@Composable
fun SearchView(
    modifier: Modifier = Modifier,
    text: String,
    onTextChanged: (String) -> Unit,
    onQuerySubmitted: (String) -> Unit
) {
    var isExpanded by remember { mutableStateOf(false) }

    Row(
        modifier = modifier
            .background(MaterialTheme.colors.surface)
            .padding(8.dp)
    ) {
        TextField(
            modifier = Modifier
                .weight(1f)
                .onFocusChanged { isExpanded = it.isFocused },
            value = text,
            onValueChange = onTextChanged,
            label = { Text("Search") },
            keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.Search
            ),
            keyboardActions = KeyboardActions(
                onSearch = { onQuerySubmitted(text) }
            ),
            leadingIcon = {
                Icon(Icons.Filled.Search, contentDescription = "Search Icon")
            },
            trailingIcon = {
                if (isExpanded && text.isNotEmpty()) {
                    IconButton(onClick = { onTextChanged("") }) {
                        Icon(Icons.Filled.Clear, contentDescription = "Clear Icon")
                    }
                }
            },
            colors = TextFieldDefaults.textFieldColors(
                backgroundColor = MaterialTheme.colors.surface
            )
        )
    }
}