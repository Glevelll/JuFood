package com.project.jufood.activities

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.room.Room
import com.project.jufood.data.RecipesDatabase
import com.project.jufood.navigation.AppNavigation
import com.project.jufood.ui.theme.JuFoodTheme

class MainActivity : ComponentActivity() {
    private lateinit var db: RecipesDatabase
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        db = Room.databaseBuilder(applicationContext, RecipesDatabase::class.java, "recipes_db")
            .fallbackToDestructiveMigration()
            .build()

        window.setBackgroundDrawable(ColorDrawable(Color.parseColor("#FFF0E1")))
        setContent {
            JuFoodTheme {
                AppNavigation(db, this, this@MainActivity)
            }
        }
    }
}
