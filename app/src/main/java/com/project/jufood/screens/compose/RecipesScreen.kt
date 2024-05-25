package com.project.jufood.screens.compose

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
import androidx.compose.runtime.LaunchedEffect
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
import com.project.jufood.activities.recipeInfo.RecipeActivity
import com.project.jufood.data.RecipeType
import com.project.jufood.data.RecipesDatabase
import com.project.jufood.data.entities.Recipes
import com.project.jufood.data.entities.recipe1
import com.project.jufood.data.entities.recipe10
import com.project.jufood.data.entities.recipe12
import com.project.jufood.data.entities.recipe13
import com.project.jufood.data.entities.recipe14
import com.project.jufood.data.entities.recipe15
import com.project.jufood.data.entities.recipe17
import com.project.jufood.data.entities.recipe19
import com.project.jufood.data.entities.recipe2
import com.project.jufood.data.entities.recipe21
import com.project.jufood.data.entities.recipe22
import com.project.jufood.data.entities.recipe24
import com.project.jufood.data.entities.recipe25
import com.project.jufood.data.entities.recipe3
import com.project.jufood.data.entities.recipe4
import com.project.jufood.data.entities.recipe5
import com.project.jufood.data.entities.recipe8
import com.project.jufood.data.entities.recipe20
import com.project.jufood.data.entities.recipe18
import com.project.jufood.data.entities.recipe7
import com.project.jufood.data.entities.recipe16
import com.project.jufood.data.entities.recipe11
import com.project.jufood.data.entities.recipe23
import com.project.jufood.data.entities.recipe6
import com.project.jufood.data.entities.recipe9
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

@Composable
fun CategoryCard(category: RecipeType, onCategoryClicked: () -> Unit) {
    val isSelected = remember { mutableStateOf(false) }

    Card(
        modifier = Modifier
            .padding(horizontal = 8.dp)
            .width(110.dp)
            .height(70.dp)
            .clickable {
                isSelected.value = !isSelected.value
                onCategoryClicked()
            },
        shape = RoundedCornerShape(10.dp),
        border = BorderStroke(2.dp, if (isSelected.value) Color.Black else Color.Transparent)
    ) {
        Box(
            modifier = Modifier.fillMaxSize()
        ) {
            Image(
                painter = painterResource(id = category.imageResourceId),
                contentDescription = null,
                modifier = Modifier
                    .fillMaxSize()
                    .alpha(0.5f),
                contentScale = ContentScale.Crop
            )
            Text(
                text = category.name,
                color = Color.Black,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .align(Alignment.Center)
                    .padding(horizontal = 8.dp),
                fontSize = 18.sp
            )
        }
    }
}


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

@Composable
fun RecipeCard(recipe: Recipes, context: Context, db: RecipesDatabase) {
    val favoriteIconColor = if (recipe.favorite) Color.Red else Color.White
    val coroutineScope = rememberCoroutineScope()

    Card(
        modifier = Modifier
            .padding(horizontal = 10.dp, vertical = 5.dp)
            .size(width = 180.dp, height = 230.dp)
            .clip(RoundedCornerShape(10.dp))
            .clickable {
                val intent = Intent(context, RecipeActivity::class.java)
                intent.putExtra("recipe", recipe.id_rec)
                context.startActivity(intent)
            },
        colors = CardDefaults.cardColors(
            containerColor = Color(0xFFF4DC8C)
        )
    ) {
        Box{
            Image(
                painter = painterResource(id = recipe.imageResId),
                contentDescription = null,
                modifier = Modifier
                    .height(160.dp)
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(10.dp)),
                contentScale = ContentScale.Crop,
            )
            Image(
                imageVector = Icons.Default.Favorite,
                contentDescription = null,
                modifier = Modifier
                    .size(35.dp)
                    .align(Alignment.TopEnd)
                    .padding(4.dp)
                    .clickable {
                        val updatedFavoriteStatus = !recipe.favorite
                        coroutineScope.launch {
                            db
                                .recipesDao()
                                .updateFavoriteStatus(recipe.id_rec, updatedFavoriteStatus)
                        }
                    },
                contentScale = ContentScale.Fit,
                colorFilter = ColorFilter.tint(favoriteIconColor)
            )
        }
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = recipe.name,
                modifier = Modifier.padding(horizontal = 12.dp),
                fontSize = 16.sp,
                fontWeight = FontWeight.Light,
                textAlign = TextAlign.Center
            )
            Text(
                text = recipe.time,
                modifier = Modifier.padding(2.dp),
                fontSize = 12.sp,
                fontWeight = FontWeight.Light,
                textAlign = TextAlign.Center
            )
        }
    }
}
