package com.project.jufood.presentation.main.screens.profile.content.calendar

import android.content.Intent
import android.view.ViewGroup
import android.widget.CalendarView
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import com.project.jufood.R
import com.project.jufood.presentation.recipeInfo.RecipeActivity
import com.project.jufood.domain.util.getMonthName
import com.project.jufood.presentation.main.MainViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.*

@Composable
fun CalendarContent(viewModel: MainViewModel) {
    // Подписываемся на состояния из ViewModel
    val displayDate by viewModel.displayDate.collectAsState()
    val cardItems by viewModel.cardItems.collectAsState()

    val context = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .verticalScroll(rememberScrollState())
    ) {
        // Календарь
        AndroidView(
            factory = { context ->
                CalendarView(context).apply {
                    layoutParams = ViewGroup.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT
                    )
                    val currentDate = Calendar.getInstance()
                    date = currentDate.timeInMillis
                }
            },
            update = {
                it.setOnDateChangeListener { _, year, month, day ->
                    val formattedDisplayDate = "$day ${getMonthName(month)}"
                    val formattedQueryDate = String.format("%02d.%02d", day, month + 1)
                    viewModel.updateDate(formattedDisplayDate, formattedQueryDate)
                }
            }
        )

        // Отображение текущей даты
        Text(
            text = displayDate,
            modifier = Modifier.padding(start = 15.dp, bottom = 10.dp),
            fontSize = 22.sp,
            color = Color.White
        )

        // Список карточек
        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            cardItems.forEachIndexed { index, (text, planId) ->
                DateCard(
                    text = text,
                    onDeleteClick = {
                        // Удаляем план через ViewModel
                        viewModel.deletePlan(planId)
                    },
                    onClick = {
                        // Получаем детали плана и запускаем Activity
                        CoroutineScope(Dispatchers.IO).launch {
                            val planDetails = viewModel.getPlanDetails(planId)
                            planDetails?.let { (recipeId, isCreated) ->
                                context.startActivity(
                                    Intent(context, RecipeActivity::class.java).apply {
                                        putExtra("recipe", recipeId)
                                        putExtra("isCreated", isCreated)
                                    }
                                )
                            }
                        }
                    }
                )
            }
        }
    }
}

@Composable
private fun DateCard(text: String, onDeleteClick: () -> Unit, onClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(5.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Card(
            modifier = Modifier
                .weight(1f)
                .clickable { onClick() }
                .clip(RoundedCornerShape(10.dp)),
            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.secondary
            )
        ) {
            Text(
                text = text,
                modifier = Modifier.padding(16.dp),
                fontSize = 18.sp,
                color = Color.Black
            )
        }
        IconButton(
            onClick = onDeleteClick,
            modifier = Modifier
                .padding(start = 8.dp)
        ) {
            Icon(
                imageVector = Icons.Default.Close,
                contentDescription = null,
                modifier = Modifier.size(35.dp)
            )
        }
    }
}
