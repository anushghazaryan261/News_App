package com.example.newsapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Card
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight.Companion.Bold
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.newsapp.model.ArticleResponse
import com.example.newsapp.ui.theme.NewsAppTheme

class MainActivity : ComponentActivity() {
    val viewModel by viewModels<DataLoaderViewModel>()

    var filteredSearch = false
    var searchString = false
    var filter = ""
    var searchStringFilter = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
       // viewModel.loadNews()
        setContent {
            NewsAppTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    if(filteredSearch){
                        if(searchString){
                            viewModel.searchNews(searchStringFilter)
                        }else{
                            viewModel.categoryNews(filter)
                        }
                    }else{
                        viewModel.loadNews()
                    }
                    val newsResponse:Result<List<ArticleResponse>> = viewModel.news.observeAsState(Result.loading()).value
                    Column {
                        OptionMenu()
                        NewsList(articleResult = newsResponse)
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
            Text(
                text = newsResponse.title,
                style = MaterialTheme.typography.h5,
                fontStyle = FontStyle.Italic
            )
            Text(text = newsResponse.author, fontStyle = FontStyle.Italic)

        }
        Column {
            AsyncImage(
                model = newsResponse.urlToImage,
                contentDescription = newsResponse.description,
            )
            Text(text = newsResponse.description, style = MaterialTheme.typography.body1)
            Text(text = newsResponse.source.name, fontWeight = Bold)
        }
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

    @Composable
    fun OptionMenu() {
        var showMenu by remember {
            mutableStateOf(false)
        }
        var searchText by remember { mutableStateOf("") }

        val context = LocalContext.current

        TopAppBar(
            title = { Text(text = "News") },
            actions = {
                IconButton(onClick = { showMenu = !showMenu }) {
                    Icon(Icons.Default.MoreVert, " ")
                }
                DropdownMenu(
                    expanded = showMenu,
                    onDismissRequest = { showMenu = false }
                ) {
                    DropdownMenuItem(onClick = {
                        filteredSearch = !filteredSearch
                        filter = "business"
                    }) {
                        Text(text = "Business")
                    }
                    DropdownMenuItem(onClick = {
                        filteredSearch = !filteredSearch
                        filter = "entertainment"
                    }) {
                        Text(text = "Entertainment")
                    }
                    DropdownMenuItem(onClick = {
                        filteredSearch = !filteredSearch
                        filter = "general"
                    }) {
                        Text(text = "General")
                    }
                    DropdownMenuItem(onClick = {
                        filteredSearch = !filteredSearch
                        filter = "health"
                    }) {
                        Text(text = "Health")
                    }
                }
            }
        )
        SearchView(
            modifier = Modifier.fillMaxWidth(),
            text = searchText,
            onTextChanged = { searchText = it },
            onQuerySubmitted = { query ->
                searchString = !searchString
                searchStringFilter = searchText
            }
        )
    }
}

