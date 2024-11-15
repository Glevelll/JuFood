package com.project.jufood.presentation.main.screens.recipes.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun SearchBar(onQueryChange: (String) -> Unit) {
    var query by remember { mutableStateOf("") }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 10.dp)
            .padding(vertical = 16.dp)
            .background(Color(android.graphics.Color.parseColor("#FFF0E1")))
    ) {
        TextField(
            value = query,
            onValueChange = {
                query = it
                onQueryChange(it)
            },
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    color = Color.White,
                    shape = RoundedCornerShape(10.dp)
                )
                .border(
                    1.dp,
                    Color(android.graphics.Color.parseColor("#333333")),
                    shape = RoundedCornerShape(10.dp)
                ),
            placeholder = { Text("Введите название") },
            shape = RoundedCornerShape(10.dp),
            singleLine = true,
            colors = TextFieldDefaults.colors(
                focusedContainerColor = Color(android.graphics.Color.parseColor("#FFF0E1")),
                unfocusedContainerColor = Color(android.graphics.Color.parseColor("#FFF0E1")),
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent
            ),
            leadingIcon = {
                Icon(
                    imageVector = Icons.Filled.Search,
                    contentDescription = "Введите название",
                    modifier = Modifier.padding(8.dp)
                )
            }
        )
    }
}