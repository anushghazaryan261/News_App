package com.example.newsapp.ui.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Button
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp

@Composable
fun SearchView(onSearch: (String) -> Unit) {
    var searchValue by remember { mutableStateOf(TextFieldValue()) }

    Card(
        backgroundColor = Color.White,
        elevation = 4.dp,
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp)
    ) {
        Row {
            TextField(
                value = searchValue,
                onValueChange = {
                    searchValue = it
                    onSearch(it.text)
                },
                modifier = Modifier.fillMaxWidth(),
                keyboardOptions = KeyboardOptions(
                    imeAction = ImeAction.Search
                ),
                keyboardActions = KeyboardActions(
                    onSearch = {
                        onSearch(searchValue.text)
                    }
                ),
                placeholder = {
                    Text("Search...")
                },
                singleLine = true,
                maxLines = 1,
                leadingIcon = {
                    Button(onClick = { onSearch(searchValue.text) }) {
                        Icon(
                            Icons.Filled.Search,
                            contentDescription = "Search icon"
                        )
                    }
                }
            )
        }
    }
}