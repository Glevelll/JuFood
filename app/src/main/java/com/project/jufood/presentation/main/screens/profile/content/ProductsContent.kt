package com.project.jufood.presentation.main.screens.profile.content

import android.app.Activity
import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.project.jufood.data.local.RecipesDatabase
import com.project.jufood.data.local.entities.Products
import kotlinx.coroutines.launch
import androidx.compose.runtime.livedata.observeAsState

@Composable
fun ProductsContent(db: RecipesDatabase, activity: Activity) {
    var text1 by remember { mutableStateOf(TextFieldValue("")) }
    var text2 by remember { mutableStateOf(TextFieldValue("")) }
    val products = remember { mutableStateListOf<Products>() }
    val scope = rememberCoroutineScope()

    val allProducts = db.productsDao().getAllProducts().observeAsState(emptyList())

    LaunchedEffect(allProducts.value) {
        products.clear()
        products.addAll(allProducts.value)
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 16.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .weight(2.5f)
                    .padding(horizontal = 8.dp)
            ) {
                TextField(
                    value = text1,
                    onValueChange = { text1 = it },
                    modifier = Modifier
                        .fillMaxWidth()
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
                    placeholder = { Text(text = "Название продукта") }
                )
            }

            Box(
                modifier = Modifier
                    .weight(1f)
                    .padding(horizontal = 8.dp)
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    TextField(
                        value = text2,
                        onValueChange = {
                            if (it.text.matches(Regex("^\\d{0,2}(\\.\\d{0,2})?$"))) {
                                text2 = it
                            }
                        },
                        modifier = Modifier
                            .weight(1f)
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
            IconButton(
                onClick = {
                    if (text1.text.isEmpty()) {
                        Toast.makeText(activity, "Введите название продукта", Toast.LENGTH_SHORT).show()
                        return@IconButton
                    }
                    if (text2.text.isEmpty()) {
                        Toast.makeText(activity, "Введите дату", Toast.LENGTH_SHORT).show()
                        return@IconButton
                    }
                    if (text2.text.length != 5) {
                        Toast.makeText(activity, "Введите корректную дату в формате дд.мм", Toast.LENGTH_SHORT).show()
                        return@IconButton
                    }
                    scope.launch {
                        val newProduct = Products(name = text1.text, date_prod = text2.text)
                        db.productsDao().insertProduct(newProduct)
                        text1 = TextFieldValue("")
                        text2 = TextFieldValue("")
                    }
                },
                modifier = Modifier
                    .padding(end = 8.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Check,
                    contentDescription = "Добавить",
                    modifier = Modifier.size(35.dp)
                )
            }
        }
        Spacer(modifier = Modifier.height(50.dp))
        LazyColumn(
            modifier = Modifier.fillMaxWidth(),
        ) {
            items(products) { product ->
                ProductCard(product.name, product.date_prod) {
                    scope.launch {
                        db.productsDao().deleteProduct(product)
                    }
                }
            }
        }
    }
}

@Composable
private fun ProductCard(text1: String, text2: String, onDeleteClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .weight(2.5f)
                .padding(start = 8.dp, end = 8.dp, bottom = 8.dp)
        ) {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(10.dp)),
                border = BorderStroke(1.dp, Color.Black),
                colors = CardDefaults.cardColors(
                    containerColor = Color(android.graphics.Color.parseColor("#FFF0E1"))
                )
            ) {
                Text(
                    text = text1,
                    modifier = Modifier.padding(16.dp),
                    fontSize = 18.sp,
                    color = Color.Black,
                    softWrap = false
                )
            }
        }

        Box(
            modifier = Modifier
                .weight(1f)
                .padding(start = 8.dp, end = 8.dp, bottom = 8.dp)
        ) {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(10.dp)),
                colors = CardDefaults.cardColors(
                    containerColor = Color(android.graphics.Color.parseColor("#FFF0E1"))
                ),
                border = BorderStroke(1.dp, Color.Black)
            ) {
                Text(
                    text = text2,
                    modifier = Modifier.padding(16.dp),
                    fontSize = 18.sp,
                    color = Color.Black,
                    softWrap = false
                )
            }
        }

        IconButton(
            onClick = onDeleteClick,
            modifier = Modifier
                .padding(end = 8.dp, bottom = 8.dp)
        ) {
            Icon(
                imageVector = Icons.Default.Close,
                contentDescription = null,
                modifier = Modifier.size(35.dp)
            )
        }
    }
}
