package com.project.jufood.presentation.recipeInfo.screens

import android.app.Activity
import android.graphics.Color
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material3.Card
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color.Companion.Red
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.accompanist.insets.LocalWindowInsets
import com.project.jufood.R
import com.project.jufood.presentation.recipeInfo.RecipeViewModel
import kotlinx.coroutines.launch

@Composable
fun RecipeScreen(activity: Activity, recipeId: Int, viewModel: RecipeViewModel) {
    val recipe by viewModel.recipe.collectAsState()
    val coroutineScope = rememberCoroutineScope()

    LaunchedEffect(recipeId) {
        viewModel.loadRecipe(recipeId)
    }

    recipe?.let { currentRecipe ->
        val favoriteColor = if (currentRecipe.favorite) {
            Red
        } else {
            White
        }
        Box(
            modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
        ) {
            LazyColumn(
                contentPadding = PaddingValues(top = LocalWindowInsets.current.statusBars.top.dp)
            ) {
                item {
                    Card(
                        shape = RoundedCornerShape(10.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Image(
                            painter = painterResource(id = currentRecipe.imageResId),
                            contentDescription = null,
                            modifier = Modifier.fillMaxWidth(),
                            contentScale = ContentScale.Crop
                        )
                    }
                }

                item {
                    Text(
                        text = currentRecipe.name,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp, vertical = 10.dp),
                        textAlign = TextAlign.Start,
                        style = TextStyle(fontSize = 30.sp),
                        fontWeight = FontWeight.Bold

                    )
                }

                item {
                    Text(
                        text = currentRecipe.time,
                        modifier = Modifier.padding(horizontal = 16.dp),
                        textAlign = TextAlign.Start,
                        style = TextStyle(fontSize = 28.sp)
                    )
                }

                item {
                    Spacer(modifier = Modifier.height(10.dp))
                    Text(
                        text = stringResource(id = R.string.ingredients),
                        modifier = Modifier
                            .padding(horizontal = 16.dp)
                            .fillMaxWidth(),
                        textAlign = TextAlign.Start,
                        style = TextStyle(fontSize = 26.sp),
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.height(10.dp))
                }

                currentRecipe.ingredients.forEachIndexed { index, ingredient ->
                    item {
                        Row(modifier = Modifier
                            .padding(horizontal = 16.dp)
                            .fillMaxWidth()) {
                            Text(
                                text = "${ingredient.title}:",
                                style = TextStyle(fontSize = 22.sp)
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = ingredient.substring,
                                style = TextStyle(fontSize = 22.sp)
                            )
                        }
                    }
                }

                item {
                    Spacer(modifier = Modifier.height(10.dp))
                    Text(
                        text = stringResource(id = R.string.description),
                        modifier = Modifier
                            .padding(horizontal = 16.dp)
                            .fillMaxWidth(),
                        textAlign = TextAlign.Start,
                        style = TextStyle(fontSize = 26.sp),
                        fontWeight = FontWeight.Bold
                    )
                }

                item {
                    Spacer(modifier = Modifier.height(10.dp))
                    Text(
                        text = currentRecipe.description,
                        modifier = Modifier
                            .padding(horizontal = 16.dp, vertical = 10.dp)
                            .fillMaxWidth()
                            .padding(bottom = 40.dp),
                        textAlign = TextAlign.Start,
                        style = TextStyle(fontSize = 22.sp)
                    )
                }
            }

            FloatingActionButton(
                onClick = { activity.finish() },
                containerColor = MaterialTheme.colorScheme.secondary,
                modifier = Modifier
                    .padding(start = 15.dp, top = 40.dp)
                    .align(Alignment.TopStart)
                    .clip(CircleShape)
            ) {
                Image(
                    imageVector = Icons.Default.KeyboardArrowLeft,
                    contentDescription = null
                )
            }

            FloatingActionButton(
                onClick = {
                    val updatedFavoriteStatus = !currentRecipe.favorite
                    coroutineScope.launch {
                        viewModel.updateFavoriteStatus(currentRecipe.id_rec, updatedFavoriteStatus)
                    }
                },
                containerColor = MaterialTheme.colorScheme.secondary,
                modifier = Modifier
                    .padding(end = 15.dp, top = 40.dp)
                    .align(Alignment.TopEnd)
                    .clip(CircleShape)
            ) {
                Image(
                    imageVector = Icons.Default.Favorite,
                    contentDescription = null,
                    colorFilter = ColorFilter.tint(favoriteColor)
                )
            }
        }
    }
}