package com.project.jufood.presentation.main.screens.profile.components

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.project.jufood.domain.util.pages

@Composable
fun ActionChips(selected: String, onSelected: (String) -> Unit) {
    LazyRow(
        modifier = Modifier.padding(top = 50.dp)
    ) {
        items(pages) { it ->
            Chip(
                title = it,
                selected = selected,
                onSelected = {
                    onSelected(it)
                }
            )
        }
    }
}