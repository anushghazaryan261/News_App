package com.example.newsapp.ui.components

import androidx.compose.foundation.layout.size
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.newsapp.model.Options

@Composable
fun OptionMenu(filters: List<Options>,
               onFilterSelected: (Options) -> Unit) {
    var expanded by remember { mutableStateOf(false) }

    IconButton(
        onClick = { expanded = true },
        modifier = Modifier.size(36.dp)
    ) {
        Icon(
            imageVector =  Icons.Default.Menu,
            contentDescription = "Options icon"
        )
    }

    DropdownMenu(
        expanded = expanded,
        onDismissRequest = { expanded = false }
    ) {
        filters.forEach { filter ->
            DropdownMenuItem(
                onClick = {
                    expanded = false
                    onFilterSelected(filter)
                }
            ) {
                Text(
                    text = filter.name,
                    style = MaterialTheme.typography.body1
                )
            }
        }
    }
}