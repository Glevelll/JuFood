package com.project.jufood.presentation.main.screens.recipes

import android.content.Context
import android.content.Intent
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.project.jufood.presentation.recipeInfo.RecipeActivity
import com.project.jufood.data.local.utils.RecipeType
import com.project.jufood.data.local.RecipesDatabase
import com.project.jufood.data.local.entities.Recipes
import com.project.jufood.presentation.main.screens.recipes.components.CategoryList
import com.project.jufood.presentation.main.screens.recipes.components.RecipeCard
import com.project.jufood.presentation.main.screens.recipes.components.SearchBar
import kotlinx.coroutines.launch

@Composable
fun RecipesScreen(db: RecipesDatabase, context: Context) {
    val recipeItems by db.recipesDao().getAllRecipes().observeAsState(initial = emptyList())
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
                        RecipeCard(item, context, db)
                    }
                }
            }
        }
    }
}
