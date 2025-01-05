package com.project.jufood.presentation.main.screens.recipes.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.project.jufood.data.local.utils.RecipeType

@Composable
fun CategoryCard(category: RecipeType, onCategoryClicked: () -> Unit) {
    val isSelected = remember { mutableStateOf(false) }

    Card(
        modifier = Modifier
            .padding(horizontal = 8.dp)
            .width(110.dp)
            .height(70.dp)
            .clickable {
                isSelected.value = !isSelected.value
                onCategoryClicked()
            },
        shape = RoundedCornerShape(10.dp),
        border = BorderStroke(3.dp, if (isSelected.value) Color.Black else Color.Transparent)
    ) {
        Box(
            modifier = Modifier.fillMaxSize()
        ) {
            Image(
                painter = painterResource(id = category.imageResourceId),
                contentDescription = null,
                modifier = Modifier
                    .fillMaxSize()
                    .alpha(0.5f),
                contentScale = ContentScale.Crop
            )
            Text(
                text = category.name,
                color = Color.White,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .align(Alignment.Center)
                    .padding(horizontal = 8.dp),
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold
            )
        }
    }
}
