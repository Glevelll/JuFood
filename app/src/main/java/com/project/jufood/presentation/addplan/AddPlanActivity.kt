package com.project.jufood.presentation.addplan

import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.room.Room
import com.project.jufood.data.local.RecipesDatabase
import com.project.jufood.presentation.addplan.screens.main.AddPlanScreen
import com.project.jufood.ui.theme.JuFoodTheme
import org.kodein.di.DI
import org.kodein.di.DIAware
import org.kodein.di.android.closestDI
import org.kodein.di.instance

class AddPlanActivity : ComponentActivity(), DIAware {
    override val di: DI by closestDI()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val db: RecipesDatabase by di.instance()
        window.setBackgroundDrawable(ColorDrawable(android.graphics.Color.parseColor("#FFF0E1")))
        setContent {
            JuFoodTheme {
                AddPlanScreen(this@AddPlanActivity, db)
            }
        }
    }
}
