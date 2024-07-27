package com.project.jufood.presentation.createRecipe

import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
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
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.room.Room
import com.project.jufood.data.local.RecipesDatabase
import com.project.jufood.ui.theme.JuFoodTheme

import androidx.activity.viewModels
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.project.jufood.presentation.createRecipe.screens.main.CreateScreen

class CreateRec : ComponentActivity() {
    private lateinit var db: RecipesDatabase
    private val viewModel: CreateRecipeViewModel by viewModels {
        object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                db = Room.databaseBuilder(applicationContext, RecipesDatabase::class.java, "recipes_db").build()
                @Suppress("UNCHECKED_CAST")
                return CreateRecipeViewModel(db) as T
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        db = Room.databaseBuilder(applicationContext, RecipesDatabase::class.java, "recipes_db").build()
        window.setBackgroundDrawable(ColorDrawable(android.graphics.Color.parseColor("#FFF0E1")))
        setContent {
            JuFoodTheme {
                CreateScreen(this@CreateRec, viewModel)
            }
        }
    }
}
