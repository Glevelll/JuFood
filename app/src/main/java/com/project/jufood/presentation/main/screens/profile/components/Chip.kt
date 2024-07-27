package com.project.jufood.presentation.main.screens.profile.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

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
