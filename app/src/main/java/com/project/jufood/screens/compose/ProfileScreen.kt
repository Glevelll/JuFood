package com.project.jufood.screens.compose

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.project.jufood.screens.compose.ProfileContent.CalendarContent
import com.project.jufood.activities.recipeCreate.CreateRec
import com.project.jufood.data.RecipesDatabase
import com.project.jufood.screens.compose.ProfileContent.FavouriteContent
import com.project.jufood.screens.compose.ProfileContent.MineContent
import com.project.jufood.screens.compose.ProfileContent.ProductsContent

@Composable
fun ProfileScreen(db: RecipesDatabase, context: Context, activity: Activity) {
    var selected by remember { mutableStateOf("Календарь") }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 16.dp),
        contentAlignment = Alignment.TopCenter
    ) {
        Row(
            horizontalArrangement = Arrangement.End,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .padding(end = 16.dp)
                .fillMaxWidth()
        ) {
            Icon(
                imageVector = Icons.Default.Add,
                contentDescription = "Добавить",
                modifier = Modifier
                    .size(35.dp)
                    .clickable {
                        val intent = Intent(context, CreateRec::class.java)
                        context.startActivity(intent)
                    }
            )
        }
        Text(
            text = "Профиль",
            fontFamily = FontFamily.Default,
            fontSize = 22.sp
        )
        Spacer(modifier = Modifier.height(10.dp))
        Column(
            modifier = Modifier.padding(top = 10.dp)
        ) {
            ActionChips(selected = selected) {
                selected = it
            }
            Spacer(modifier = Modifier.height(16.dp))
            when (selected) {
                "Календарь" -> CalendarContent(db)
                "Продукты" -> ProductsContent(db, activity)
                "Избранное" -> FavouriteContent(db, context)
                "Мои рецепты" -> MineContent(db, context)
            }
        }
    }
}


@Composable
private fun ActionChips(selected: String, onSelected: (String) -> Unit) {
    LazyRow(
        modifier = Modifier.padding(top = 50.dp)
    ) {
        items(pages) { it ->
            Chip(
                title = it,
                selected = selected,
                onSelected = {
                    onSelected(it)
                }
            )
        }
    }
}

@Composable
fun Chip(
    title: String,
    selected: String,
    onSelected: (String) -> Unit
) {
    val isSelected = selected == title

    val background = if (isSelected) Color(android.graphics.Color.parseColor("#BFD05F")) else Color(android.graphics.Color.parseColor("#F4DC8C"))
    val contentColor = Color.Black

    Box(
        modifier = Modifier
            .padding(start = 5.dp, end = 5.dp)
            .height(35.dp)
            .clip(CircleShape)
            .background(background)
            .clickable(onClick = { onSelected(title) })
    ) {
        Row(
            modifier = Modifier.padding(start = 10.dp, end = 10.dp, top = 5.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            Text(text = title, color = contentColor, fontSize = 16.sp)
        }
    }
}


val pages = listOf(
    "Календарь",
    "Продукты",
    "Избранное",
    "Мои рецепты"
)
