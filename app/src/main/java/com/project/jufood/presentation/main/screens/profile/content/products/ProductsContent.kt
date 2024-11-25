package com.project.jufood.presentation.main.screens.profile.content.products

import android.app.Activity
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
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.project.jufood.R
import com.project.jufood.presentation.main.MainViewModel

@Composable
fun ProductsContent(viewModel: MainViewModel, activity: Activity) {
    val text1 by viewModel.text1.collectAsState()
    val text2 by viewModel.text2.collectAsState()
    val products by viewModel.products.collectAsState()

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
                    onValueChange = { viewModel.updateText1(it) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(55.dp)
                        .border(
                            1.dp,
                            Color(R.color.border),
                            shape = RoundedCornerShape(10.dp)
                        ),
                    shape = RoundedCornerShape(10.dp),
                    textStyle = TextStyle(
                        textAlign = TextAlign.Start,
                        fontSize = 18.sp
                    ),
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = Color(R.color.background),
                        unfocusedContainerColor = Color(R.color.background),
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent,
                    ),
                    singleLine = true,
                    keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Text),
                    placeholder = { Text(text = stringResource(id = R.string.title_prod)) }
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
                        onValueChange = { viewModel.updateText2(it) },
                        modifier = Modifier
                            .weight(1f)
                            .height(55.dp)
                            .border(
                                1.dp,
                                Color(R.color.border),
                                shape = RoundedCornerShape(10.dp)
                            ),
                        shape = RoundedCornerShape(10.dp),
                        textStyle = TextStyle(
                            textAlign = TextAlign.Start,
                            fontSize = 18.sp
                        ),
                        colors = TextFieldDefaults.colors(
                            focusedContainerColor = Color(R.color.background),
                            unfocusedContainerColor = Color(R.color.background),
                            focusedIndicatorColor = Color.Transparent,
                            unfocusedIndicatorColor = Color.Transparent,
                        ),
                        singleLine = true,
                        keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
                        placeholder = { Text(text = stringResource(id = R.string.date_format)) }
                    )
                }
            }

            IconButton(
                onClick = { viewModel.addProduct(activity) },
                modifier = Modifier.padding(end = 8.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Check,
                    contentDescription = null,
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
                    viewModel.deleteProduct(product)
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
                    containerColor = Color(R.color.background)
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
                    containerColor = Color(R.color.background)
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
