package com.project.jufood.presentation.createRecipe.screens.main.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.project.jufood.R

@Composable
fun IngredientCard(text: String, count: String, onDeleteClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Card(
            modifier = Modifier
                .weight(2f)
                .padding(horizontal = 5.dp, vertical = 5.dp)
                .clip(RoundedCornerShape(10.dp)),
            border = BorderStroke(1.dp, Color.Black),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.background
            )
        ) {
            Text(
                text = text,
                modifier = Modifier.padding(16.dp),
                fontSize = 18.sp,
                color = Color.Black,
                softWrap = false
            )
        }

        Card(
            modifier = Modifier
                .weight(1f)
                .padding(start = 5.dp)
                .clip(RoundedCornerShape(10.dp)),
            border = BorderStroke(1.dp, Color.Black),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.background
            )
        ) {
            Text(
                text = count,
                modifier = Modifier.padding(16.dp),
                fontSize = 18.sp,
                color = Color.Black,
                softWrap = false
            )
        }

        IconButton(
            onClick = onDeleteClick,
            modifier = Modifier
                .padding(end = 8.dp)
        ) {
            Icon(
                imageVector =  Icons.Default.Close,
                contentDescription = null,
                modifier = Modifier.size(35.dp)
            )
        }
    }
}