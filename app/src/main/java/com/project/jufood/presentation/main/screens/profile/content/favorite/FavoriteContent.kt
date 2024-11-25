package com.project.jufood.presentation.main.screens.profile.content.favorite

import android.content.Context
import android.content.Intent
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.project.jufood.R
import com.project.jufood.presentation.recipeInfo.RecipeActivity
import com.project.jufood.data.local.entities.Recipes
import com.project.jufood.presentation.main.MainViewModel
import kotlinx.coroutines.launch

@Composable
fun FavouriteContent(viewModel: MainViewModel, context: Context) {
    val favoriteRecipes by viewModel.favoriteRecipes.collectAsState()

    LazyColumn(
        modifier = Modifier.fillMaxWidth()
    ) {
        item {
            Spacer(modifier = Modifier.height(10.dp))
        }

        val chunkedFavoriteRecipes = favoriteRecipes.chunked(2)
        items(chunkedFavoriteRecipes) { rowItems ->
            Row(Modifier.fillMaxWidth()) {
                rowItems.forEach { recipe ->
                    FavoriteCard(recipe, context, viewModel)
                }
                repeat(2 - rowItems.size) {
                    Spacer(modifier = Modifier.weight(1f))
                }
            }
        }
    }
}


@Composable
fun FavoriteCard(recipe: Recipes, context: Context, viewModel: MainViewModel) {
    val favoriteColor = if (recipe.favorite) Color.Red else Color.White
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
            containerColor = Color(R.color.container)
        )
    ) {
        Box {
            Image(
                painter = painterResource(id = recipe.imageResId),
                contentDescription = null,
                modifier = Modifier
                    .height(160.dp)
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(10.dp)),
                contentScale = ContentScale.Crop
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
                            viewModel.toggleFavoriteStatus(recipe.id_rec, updatedFavoriteStatus)
                        }
                    },
                contentScale = ContentScale.Fit,
                colorFilter = ColorFilter.tint(favoriteColor)
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
