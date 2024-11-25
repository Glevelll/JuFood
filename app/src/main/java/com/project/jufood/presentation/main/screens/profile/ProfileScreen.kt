package com.project.jufood.presentation.main.screens.profile

import android.app.Activity
import android.content.Context
import android.content.Intent
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.project.jufood.R
import com.project.jufood.domain.util.ProfilePage
import com.project.jufood.presentation.createRecipe.CreateRec
import com.project.jufood.presentation.main.MainViewModel
import com.project.jufood.presentation.main.screens.profile.components.ActionChips
import com.project.jufood.presentation.main.screens.profile.content.calendar.CalendarContent
import com.project.jufood.presentation.main.screens.profile.content.favorite.FavouriteContent
import com.project.jufood.presentation.main.screens.profile.content.mine.MineContent
import com.project.jufood.presentation.main.screens.profile.content.products.ProductsContent

@Composable
fun ProfileScreen(viewModel: MainViewModel, context: Context, activity: Activity) {
    var selected by remember { mutableStateOf(ProfilePage.CALENDAR) }

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
                contentDescription = null,
                modifier = Modifier
                    .size(35.dp)
                    .clickable {
                        val intent = Intent(context, CreateRec::class.java)
                        context.startActivity(intent)
                    }
            )
        }
        Text(
            text = stringResource(id = R.string.profile),
            fontFamily = FontFamily.Default,
            fontSize = 22.sp
        )
        Spacer(modifier = Modifier.height(10.dp))
        Column(
            modifier = Modifier.padding(top = 10.dp)
        ) {
            ActionChips(selected = selected) { newSelected ->
                selected = newSelected
            }
            Spacer(modifier = Modifier.height(16.dp))
            when (selected) {
                ProfilePage.CALENDAR -> CalendarContent(viewModel)
                ProfilePage.PRODUCTS -> ProductsContent(viewModel, activity)
                ProfilePage.FAVORITE -> FavouriteContent(viewModel, context)
                ProfilePage.MINE -> MineContent(viewModel, context)
            }
        }
    }
}

