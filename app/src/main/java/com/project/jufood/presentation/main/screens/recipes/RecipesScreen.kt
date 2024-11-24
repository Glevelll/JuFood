package com.project.jufood.presentation.main.screens.recipes

import android.content.Context
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.project.jufood.data.local.utils.RecipeType
import com.project.jufood.presentation.main.MainViewModel
import com.project.jufood.presentation.main.screens.recipes.components.CategoryList
import com.project.jufood.presentation.main.screens.recipes.components.RecipeCard
import com.project.jufood.presentation.main.screens.recipes.components.SearchBar

@Composable
fun RecipesScreen(viewModel: MainViewModel, context: Context) {
    val recipeItems by viewModel.recipeItems.collectAsState(initial = emptyList())

    val selectedCategories = remember { mutableStateListOf<RecipeType>() }
    var searchQuery by remember { mutableStateOf("") }

//    LaunchedEffect(Unit) {
//        db.recipesDao().insertRecipe(recipe1)
//        db.recipesDao().insertRecipe(recipe3)
//        db.recipesDao().insertRecipe(recipe6)
//        db.recipesDao().insertRecipe(recipe9)
//        db.recipesDao().insertRecipe(recipe12)
//        db.recipesDao().insertRecipe(recipe15)
//        db.recipesDao().insertRecipe(recipe18)
//        db.recipesDao().insertRecipe(recipe21)
//        db.recipesDao().insertRecipe(recipe24)
//        db.recipesDao().insertRecipe(recipe2)
//        db.recipesDao().insertRecipe(recipe4)
//        db.recipesDao().insertRecipe(recipe8)
//        db.recipesDao().insertRecipe(recipe10)
//        db.recipesDao().insertRecipe(recipe14)
//        db.recipesDao().insertRecipe(recipe16)
//        db.recipesDao().insertRecipe(recipe20)
//        db.recipesDao().insertRecipe(recipe22)
//        db.recipesDao().insertRecipe(recipe24)
//        db.recipesDao().insertRecipe(recipe5)
//        db.recipesDao().insertRecipe(recipe7)
//        db.recipesDao().insertRecipe(recipe11)
//        db.recipesDao().insertRecipe(recipe13)
//        db.recipesDao().insertRecipe(recipe17)
//        db.recipesDao().insertRecipe(recipe19)
//        db.recipesDao().insertRecipe(recipe23)
//        db.recipesDao().insertRecipe(recipe25)
//    }

    Column {
        SearchBar(onQueryChange = { searchQuery = it })
        Text(
            text = "Категории",
            modifier = Modifier
                .padding(horizontal = 10.dp)
                .align(Alignment.Start),
            fontSize = 24.sp,
            color = Color.Black
        )
        CategoryList(categories = RecipeType.entries) { category ->
            if (selectedCategories.contains(category)) {
                selectedCategories.remove(category)
            } else {
                selectedCategories.add(category)
            }
        }
        Text(
            text = "Рецепты",
            modifier = Modifier
                .padding(10.dp)
                .align(Alignment.Start),
            fontSize = 24.sp,
            color = Color.Black
        )

        LazyColumn(
            modifier = Modifier.fillMaxWidth()
        ) {
            item {
                Spacer(modifier = Modifier.height(10.dp))
            }
            val filteredRecipes = recipeItems.filter { recipe ->
                recipe.name.contains(searchQuery, ignoreCase = true) &&
                        (selectedCategories.isEmpty() || selectedCategories.contains(recipe.type))
            }
            items(filteredRecipes.chunked(2)) { rowItems ->
                Row(Modifier.fillMaxWidth()) {
                    rowItems.forEach { item ->
                        RecipeCard(item, context, viewModel)
                    }
                }
            }
        }
    }
}
