package com.project.jufood.presentation.main.screens.profile.content

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
import com.project.jufood.presentation.recipeInfo.RecipeActivity
import com.project.jufood.data.local.RecipesDatabase
import kotlinx.coroutines.launch
import java.util.*

@Composable
fun CalendarContent(db: RecipesDatabase) {
    var displayDate by remember { mutableStateOf(getCurrentDateDisplay()) }
    var queryDate by remember { mutableStateOf(getCurrentDateQuery()) }
    val planDao = db.planDao()
    val recipesDao = db.recipesDao()
    val createdDao = db.createdDao()
    val cardItems = remember { mutableStateListOf<Pair<String, Int>>() }
    val coroutineScope = rememberCoroutineScope()

    LaunchedEffect(queryDate) {
        val plans = planDao.getPlansByDate(queryDate)
        val newCardItems = mutableListOf<Pair<String, Int>>()
        plans.forEach { plan ->
            plan.recipesFk?.let { recipesId ->
                val recipe = recipesDao.getRecipeById(recipesId)
                recipe?.let { newCardItems.add(it.name to plan.id) }
            }
            plan.createdFk?.let { createdId ->
                val createdRecipe = createdDao.getCreatedById(createdId)
                createdRecipe?.let { newCardItems.add(it.name to plan.id) }
            }
        }
        cardItems.clear()
        cardItems.addAll(newCardItems)
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .verticalScroll(rememberScrollState())
    ) {
        AndroidView(
            factory = { context ->
                CalendarView(context).apply {
                    layoutParams = ViewGroup.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT
                    )
                    val currentDate = Calendar.getInstance()
                    displayDate = "${currentDate.get(Calendar.DAY_OF_MONTH)} ${getMonthName(currentDate.get(Calendar.MONTH))}"
                    queryDate = String.format("%02d.%02d", currentDate.get(Calendar.DAY_OF_MONTH), currentDate.get(Calendar.MONTH) + 1)
                    setDate(currentDate.timeInMillis)
                }
            },
            update = {
                it.setOnDateChangeListener { _, year, month, day ->
                    val formattedDisplayDate = "$day ${getMonthName(month)}"
                    val formattedQueryDate = String.format("%02d.%02d", day, month + 1)
                    displayDate = formattedDisplayDate
                    queryDate = formattedQueryDate
                }
            }
        )
        Text(
            text = displayDate,
            modifier = Modifier.padding(start = 15.dp, bottom = 10.dp),
            fontSize = 22.sp
        )
        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            cardItems.forEachIndexed { index, (text, planId) ->
                val context = LocalContext.current
                DateCard(
                    text = text,
                    onDeleteClick = {
                        coroutineScope.launch {
                            val plan = planDao.getPlansByDate(queryDate).find { it.id == planId }
                            if (plan != null) {
                                planDao.delete(plan)
                                cardItems.removeAt(index)
                            }
                        }
                    },
                    onClick = {
                        coroutineScope.launch {
                            val plan = planDao.getPlansByDate(queryDate).find { it.id == planId }
                            if (plan != null) {
                                val recipeId = plan.recipesFk ?: plan.createdFk ?: -1
                                val isCreated = plan.createdFk != null
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
            border = BorderStroke(1.dp, Color.Black),
            colors = CardDefaults.cardColors(
                containerColor = Color(0xFFF4DC8C)
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

private fun getCurrentDateDisplay(): String {
    val currentDate = Calendar.getInstance()
    return "${currentDate.get(Calendar.DAY_OF_MONTH)} ${getMonthName(currentDate.get(Calendar.MONTH))}"
}

private fun getCurrentDateQuery(): String {
    val currentDate = Calendar.getInstance()
    return String.format("%02d.%02d", currentDate.get(Calendar.DAY_OF_MONTH), currentDate.get(Calendar.MONTH) + 1)
}

private fun getMonthName(month: Int): String {
    return when (month) {
        Calendar.JANUARY -> "января"
        Calendar.FEBRUARY -> "февраля"
        Calendar.MARCH -> "марта"
        Calendar.APRIL -> "апреля"
        Calendar.MAY -> "мая"
        Calendar.JUNE -> "июня"
        Calendar.JULY -> "июля"
        Calendar.AUGUST -> "августа"
        Calendar.SEPTEMBER -> "сентября"
        Calendar.OCTOBER -> "октября"
        Calendar.NOVEMBER -> "ноября"
        Calendar.DECEMBER -> "декабря"
        else -> ""
    }
}