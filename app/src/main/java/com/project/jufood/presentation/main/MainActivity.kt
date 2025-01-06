package com.project.jufood.presentation.main

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.project.jufood.R
import com.project.jufood.data.local.RecipesDatabase
import com.project.jufood.presentation.main.navigation.AppNavigation
import com.project.jufood.ui.theme.JuFoodTheme
import org.kodein.di.DI
import org.kodein.di.DIAware
import org.kodein.di.android.closestDI
import org.kodein.di.instance

class MainActivity : ComponentActivity(), DIAware {
    override val di: DI by closestDI()

    private val viewModel: MainViewModel by viewModels {
        object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                val db: RecipesDatabase by di.instance()
                return MainViewModel(db) as T
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            JuFoodTheme {
                AppNavigation(viewModel, this, this@MainActivity)
            }
        }
    }
}
