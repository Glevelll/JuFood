package com.project.jufood.presentation.addplan

import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.room.Room
import com.project.jufood.data.local.RecipesDatabase
import com.project.jufood.presentation.addplan.screens.main.AddPlanScreen
import com.project.jufood.ui.theme.JuFoodTheme

class AddPlanActivity : ComponentActivity() {
    private lateinit var db: RecipesDatabase
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        db = Room.databaseBuilder(applicationContext, RecipesDatabase::class.java, "recipes_db").build()
        window.setBackgroundDrawable(ColorDrawable(android.graphics.Color.parseColor("#FFF0E1")))
        setContent {
            JuFoodTheme {
                AddPlanScreen(this@AddPlanActivity, db)
            }
        }
    }
}
