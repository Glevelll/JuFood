package com.project.jufood.presentation.addplan.screens.main

import android.app.Activity
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.project.jufood.data.local.RecipesDatabase
import com.project.jufood.data.local.entities.Created
import com.project.jufood.data.local.entities.Plan
import com.project.jufood.data.local.entities.Recipes
import com.project.jufood.presentation.addplan.screens.main.components.PlanCard
import kotlinx.coroutines.launch

@Composable
fun AddPlanScreen(activity: Activity, db: RecipesDatabase) {
    var text1 by remember { mutableStateOf(TextFieldValue("")) }
    var selectedRecipes by remember { mutableStateOf<List<Any>>(listOf()) }
    val filRecipes by db.recipesDao().getAllRecipes().observeAsState(listOf())
    val favoriteRecipes by db.recipesDao().getFavoriteRecipes().observeAsState(listOf())
    val createdRecipes by db.createdDao().getAllCreated().observeAsState(listOf())
    val products by db.productsDao().getAllProducts().observeAsState(listOf())
    val coroutineScope = rememberCoroutineScope()

    val filteredRecipes = filRecipes.filter { recipe ->
        recipe.ingredients.any { ingredient ->
            products.any { product ->
                product.name == ingredient.title
            }
        }
    }

    val allRecipes = favoriteRecipes + createdRecipes + filteredRecipes

    val buttonStates = remember { mutableStateListOf<Boolean>() }
    for (recipe in allRecipes) {
        buttonStates.add(false)
    }

    fun toggleButtonState(index: Int) {
        buttonStates[index] = !buttonStates[index]
        selectedRecipes = if (buttonStates[index]) {
            selectedRecipes + allRecipes[index]
        } else {
            selectedRecipes - allRecipes[index]
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier
                .padding(top = 16.dp)
                .fillMaxWidth()
        ) {
            Row(
                horizontalArrangement = Arrangement.Start,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .padding(end = 16.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.KeyboardArrowLeft,
                    contentDescription = "Назад",
                    modifier = Modifier
                        .size(50.dp)
                        .clickable {
                            activity.finish()
                        }
                )
            }
            Text(
                text = "Составить план",
                fontFamily = FontFamily.Default,
                fontSize = 22.sp,
                modifier = Modifier
                    .align(Alignment.Center)
            )
        }

        Spacer(modifier = Modifier.height(20.dp))

        Text(
            text = "Укажите дату",
            fontFamily = FontFamily.Default,
            fontSize = 22.sp,
            modifier = Modifier
                .align(Alignment.Start)
                .padding(horizontal = 16.dp)
        )

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color(android.graphics.Color.parseColor("#FFF0E1")))
                .padding(horizontal = 16.dp, vertical = 8.dp)
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                TextField(
                    value = text1,
                    onValueChange = {
                        if (it.text.matches(Regex("^\\d{0,2}(\\.\\d{0,2}(\\.\\d{0,4})?)?$"))) {
                            text1 = it
                        }
                    },
                    modifier = Modifier
                        .width(200.dp)
                        .height(55.dp)
                        .border(
                            1.dp,
                            Color(android.graphics.Color.parseColor("#333333")),
                            shape = RoundedCornerShape(10.dp)
                        ),
                    shape = RoundedCornerShape(10.dp),
                    textStyle = TextStyle(
                        textAlign = TextAlign.Start,
                        fontSize = 18.sp
                    ),
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = Color(android.graphics.Color.parseColor("#FFF0E1")),
                        unfocusedContainerColor = Color(android.graphics.Color.parseColor("#FFF0E1")),
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent,
                    ),
                    singleLine = true,
                    keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
                    placeholder = { Text(text = "дд.мм") }
                )
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = "Добавьте рецепты",
                    fontFamily = FontFamily.Default,
                    fontSize = 22.sp,
                    modifier = Modifier.weight(1f)
                )

                Icon(
                    imageVector = Icons.Default.KeyboardArrowRight,
                    contentDescription = "Добавить рецепт",
                    modifier = Modifier
                        .size(50.dp)
                )
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        LazyRow(
            modifier = Modifier.fillMaxWidth()
        ) {
            itemsIndexed(allRecipes) { index, recipe ->
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    PlanCard(item = recipe)
                    Button(
                        onClick = { toggleButtonState(index) },
                        modifier = Modifier
                            .width(180.dp)
                            .height(48.dp),
                        shape = RoundedCornerShape(10.dp)
                    ) {
                        Text(text = if (buttonStates[index]) "Убрать" else "Добавить")
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(80.dp))

        Button(
            onClick = {
                if (text1.text.isEmpty()) {
                    Toast.makeText(activity, "Введите дату", Toast.LENGTH_SHORT).show()
                    return@Button
                }
                if (text1.text.length != 5) {
                    Toast.makeText(activity, "Введите корректную дату в формате дд.мм", Toast.LENGTH_SHORT).show()
                    return@Button
                }
                val selectedDate = text1.text
                selectedRecipes.forEach { recipe ->
                    val recipeId: Int = when (recipe) {
                        is Recipes -> recipe.id_rec
                        is Created -> recipe.id_cre
                        else -> -1
                    }
                    coroutineScope.launch {
                        when (recipe) {
                            is Recipes -> {
                                val plan = Plan(date = selectedDate, recipesFk = recipeId, createdFk = null)
                                db.planDao().insert(plan)
                            }
                            is Created -> {
                                val plan = Plan(date = selectedDate, recipesFk = null, createdFk = recipeId)
                                db.planDao().insert(plan)
                            }
                        }
                    }
                }
                activity.finish()
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(55.dp)
                .padding(start = 5.dp, end = 5.dp, bottom = 5.dp),
            shape = RoundedCornerShape(10.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFFBFD05F)
            )
        ) {
            Text(text = "Составить")
        }
    }
}
