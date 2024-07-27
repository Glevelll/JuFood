package com.project.jufood.presentation.main.screens.recipes.components

import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.project.jufood.data.local.utils.RecipeType

@Composable
fun CategoryList(categories: List<RecipeType>, onCategoryClicked: (RecipeType) -> Unit) {
    val scrollState = rememberScrollState()

    Row(
        modifier = Modifier
            .horizontalScroll(scrollState)
            .padding(vertical = 8.dp)
    ) {
        categories.forEach { category ->
            CategoryCard(
                category = category,
                onCategoryClicked = { onCategoryClicked(category) }
            )
        }
    }
}