package com.project.jufood.presentation.createRecipe.screens.main

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asAndroidBitmap
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.project.jufood.data.local.Ingredients
import com.project.jufood.data.local.entities.Created
import com.project.jufood.domain.util.convertImageBitmapToByteArray
import com.project.jufood.presentation.createRecipe.CreateRecipeViewModel
import com.project.jufood.presentation.createRecipe.screens.main.components.IngredientCard
import java.io.ByteArrayOutputStream

@Composable
fun CreateScreen(activity: Activity, viewModel: CreateRecipeViewModel) {
    var text1 by remember { mutableStateOf(TextFieldValue("")) }
    var text2 by remember { mutableStateOf(TextFieldValue("")) }
    var text3 by remember { mutableStateOf(TextFieldValue("")) }
    var text4 by remember { mutableStateOf(TextFieldValue("")) }
    var text5 by remember { mutableStateOf(TextFieldValue("")) }

    val ingredients by viewModel.ingredients.collectAsState()

    var imageBitmap by remember { mutableStateOf<ImageBitmap?>(null) }

    val launcher = rememberLauncherForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            val selectedImageUri = result.data?.data
            selectedImageUri?.let { uri ->
                activity.contentResolver.openInputStream(uri)?.use { inputStream ->
                    val bitmap = BitmapFactory.decodeStream(inputStream)
                    imageBitmap = bitmap.asImageBitmap()
                }
            }
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
                text = "Добавление рецепта",
                fontFamily = FontFamily.Default,
                fontSize = 22.sp,
                modifier = Modifier.align(Alignment.Center)
            )
        }

        Spacer(modifier = Modifier.height(20.dp))

        Box(
            modifier = Modifier
                .size(300.dp)
                .fillMaxWidth()
                .clickable {
                    val galleryIntent = Intent(Intent.ACTION_GET_CONTENT).apply {
                        type = "image/*"
                    }
                    launcher.launch(galleryIntent)
                }
                .padding(16.dp),
            contentAlignment = Alignment.Center
        ) {
            imageBitmap?.let { bitmap ->
                Image(
                    bitmap = bitmap,
                    contentDescription = null,
                    modifier = Modifier
                        .fillMaxSize()
                        .aspectRatio(1f),
                    contentScale = ContentScale.Crop
                )
            } ?: Icon(
                imageVector = Icons.Default.Add,
                contentDescription = "Добавить изображение",
                modifier = Modifier.size(48.dp),
                tint = Color.Gray
            )
        }

        Spacer(modifier = Modifier.height(20.dp))

        Text(
            text = "Название",
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 5.dp),
            fontSize = 20.sp
        )

        Spacer(modifier = Modifier.height(20.dp))

        TextField(
            value = text1,
            onValueChange = { text1 = it },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 5.dp)
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
            keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Text),
            placeholder = { Text(text = "Введите название") }
        )

        Spacer(modifier = Modifier.height(20.dp))

        Text(
            text = "Время приготовления",
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 5.dp),
            fontSize = 20.sp
        )

        Spacer(modifier = Modifier.height(20.dp))

        TextField(
            value = text2,
            onValueChange = { text2 = it },
            modifier = Modifier
                .padding(start = 5.dp)
                .height(55.dp)
                .width(200.dp)
                .border(
                    1.dp,
                    Color(android.graphics.Color.parseColor("#333333")),
                    shape = RoundedCornerShape(10.dp)
                )
                .align(Alignment.Start),
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
            keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Text),
            placeholder = { Text(text = "Время") }
        )


        Spacer(modifier = Modifier.height(20.dp))

        Text(
            text = "Ингредиенты",
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 5.dp),
            fontSize = 20.sp
        )

        Spacer(modifier = Modifier.height(20.dp))

        ingredients.forEach { (ingredient, count) ->
            IngredientCard(ingredient, count) {
                viewModel.removeIngredient(ingredient, count)
            }
        }

        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            TextField(
                value = text3,
                onValueChange = { text3 = it },
                modifier = Modifier
                    .weight(2f)
                    .padding(horizontal = 5.dp)
                    .height(55.dp)
                    .border(
                        BorderStroke(1.dp, Color(android.graphics.Color.parseColor("#333333"))),
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
                keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Text),
                placeholder = { Text(text = "Введите ингредиент") }
            )

            Spacer(modifier = Modifier.width(8.dp))

            TextField(
                value = text4,
                onValueChange = { text4 = it },
                modifier = Modifier
                    .weight(1f)
                    .padding(horizontal = 5.dp)
                    .height(55.dp)
                    .border(
                        BorderStroke(1.dp, Color(android.graphics.Color.parseColor("#333333"))),
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
                keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Text),
                placeholder = { Text(text = "Кол-во") }
            )

            IconButton(
                onClick = {
                    if (text3.text.isNotEmpty() && text4.text.isNotEmpty()) {
                        viewModel.addIngredient(text3.text, text4.text)
                        text3 = TextFieldValue("")
                        text4 = TextFieldValue("")
                    }
                },
                modifier = Modifier
                    .padding(end = 8.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Добавить",
                    modifier = Modifier.size(35.dp)
                )
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        Text(
            text = "Описание",
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 5.dp),
            fontSize = 20.sp
        )

        Spacer(modifier = Modifier.height(20.dp))

        TextField(
            value = text5,
            onValueChange = { text5 = it },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 5.dp)
                .height(200.dp)
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
            singleLine = false,
            keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Text),
            placeholder = { Text(text = "Введите описание") }
        )


        Spacer(modifier = Modifier.height(20.dp))

        Button(
            onClick = {
                if (text1.text.isEmpty() || text2.text.isEmpty()) {
                    Toast.makeText(activity, "Укажите название и время", Toast.LENGTH_SHORT).show()
                    return@Button
                }
                if (imageBitmap == null) {
                    Toast.makeText(activity, "Добавьте изображение", Toast.LENGTH_SHORT).show()
                    return@Button
                }
                val ingredientsList = ingredients.map { Ingredients(it.first, it.second) }
                val imageByteArray = imageBitmap?.let { convertImageBitmapToByteArray(it) }
                val created = Created(name = text1.text, time = text2.text, ingredients = ingredientsList, description = text5.text, image = imageByteArray)
                viewModel.insertRecipe(created) {
                    activity.finish()
                }
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
            Text(text = "Добавить")
        }
    }
}