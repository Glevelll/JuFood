package com.project.jufood.activities

import android.app.Activity
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.room.Room
import com.project.jufood.R
import com.project.jufood.data.RecipesDatabase
import com.project.jufood.data.entities.Created
import com.project.jufood.data.entities.Plan
import com.project.jufood.data.entities.Recipes
import com.project.jufood.screens.compose.ProfileContent.convertByteArrayToImageBitmap
import com.project.jufood.ui.theme.JuFoodTheme
import kotlinx.coroutines.launch

class AddPlanActivity : ComponentActivity() {
    private lateinit var db: RecipesDatabase
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        db = Room.databaseBuilder(applicationContext, RecipesDatabase::class.java, "recipes_db").build()
        window.setBackgroundDrawable(ColorDrawable(android.graphics.Color.parseColor("#FFF0E1")))
        setContent {
            JuFoodTheme {
                AddPlanScreen(this@AddPlanActivity, db)
            }
        }
    }
}

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


@Composable
fun PlanCard(item: Any) {
    Card(
        modifier = Modifier
            .padding(horizontal = 10.dp, vertical = 5.dp)
            .size(width = 180.dp, height = 230.dp)
            .clip(RoundedCornerShape(10.dp)),
        colors = CardDefaults.cardColors(
            containerColor = Color(0xFFF4DC8C)
        )
    ) {
        Box {
            when (item) {
                is Recipes -> {
                    Image(
                        painter = painterResource(id = item.imageResId),
                        contentDescription = null,
                        modifier = Modifier
                            .height(160.dp)
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(10.dp)),
                        contentScale = ContentScale.Crop
                    )
                }
                is Created -> {
                    val imageBitmap by remember(item.id_cre) {
                        mutableStateOf(item.image?.let { convertByteArrayToImageBitmap(it) })
                    }
                    imageBitmap?.let {
                        Image(
                            bitmap = it,
                            contentDescription = null,
                            modifier = Modifier
                                .height(160.dp)
                                .fillMaxWidth()
                                .clip(RoundedCornerShape(10.dp)),
                            contentScale = ContentScale.Crop
                        )
                    }
                }
                else -> {
                    Image(
                        painter = painterResource(id = R.drawable.ic_launcher_foreground),
                        contentDescription = null,
                        modifier = Modifier
                            .height(160.dp)
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(10.dp)),
                        contentScale = ContentScale.Crop
                    )
                }
            }
        }
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = when (item) {
                    is Recipes -> item.name
                    is Created -> item.name
                    else -> ""
                },
                modifier = Modifier.padding(horizontal = 12.dp),
                fontSize = 16.sp,
                fontWeight = FontWeight.Light,
                textAlign = TextAlign.Center
            )
            Text(
                text = when (item) {
                    is Recipes -> item.time
                    is Created -> item.time
                    else -> ""
                },
                modifier = Modifier.padding(2.dp),
                fontSize = 12.sp,
                fontWeight = FontWeight.Light,
                textAlign = TextAlign.Center
            )
        }
    }
}
