package com.project.jufood.presentation.main.screens.profile

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
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
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.project.jufood.presentation.createRecipe.CreateRec
import com.project.jufood.presentation.main.MainViewModel
import com.project.jufood.presentation.main.screens.profile.components.ActionChips
import com.project.jufood.presentation.main.screens.profile.content.calendar.CalendarContent
import com.project.jufood.presentation.main.screens.profile.content.favorite.FavouriteContent
import com.project.jufood.presentation.main.screens.profile.content.mine.MineContent
import com.project.jufood.presentation.main.screens.profile.content.products.ProductsContent

@Composable
fun ProfileScreen(viewModel: MainViewModel, context: Context, activity: Activity) {
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
                "Календарь" -> CalendarContent(viewModel)
                "Продукты" -> ProductsContent(viewModel, activity)
                "Избранное" -> FavouriteContent(viewModel, context)
                "Мои рецепты" -> MineContent(viewModel, context)
            }
        }
    }
}
