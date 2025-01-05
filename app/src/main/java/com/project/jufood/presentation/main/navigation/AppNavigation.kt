package com.project.jufood.presentation.main.navigation

import android.app.Activity
import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.project.jufood.presentation.main.MainViewModel
import com.project.jufood.presentation.main.screens.main.MainScreen
import com.project.jufood.presentation.main.screens.profile.ProfileScreen
import com.project.jufood.presentation.main.screens.recipes.RecipesScreen
import com.project.jufood.ui.theme.JuFoodTheme


@Composable
fun AppNavigation(viewModel: MainViewModel, context: Context, activity: Activity) {
    val navController = rememberNavController()

    JuFoodTheme {
        Scaffold(
            bottomBar = {
                NavigationBar(
                    containerColor = MaterialTheme.colorScheme.background
                ) {
                    val navBackStackEntry by navController.currentBackStackEntryAsState()
                    val currentDestination = navBackStackEntry?.destination

                    listOfNavItems.forEach { navItem ->
                        NavigationBarItem(
                            selected = currentDestination?.hierarchy?.any { it.route == navItem.route } == true,
                            onClick = {
                                navController.navigate(navItem.route) {
                                    popUpTo(navController.graph.findStartDestination().id) {
                                        saveState = true
                                    }
                                    launchSingleTop = true
                                    restoreState = true
                                }
                            },
                            icon = {
                                Icon(
                                    imageVector = navItem.icon,
                                    contentDescription = null,
                                    tint = Color.White
                                )
                            }
                        )
                    }
                }

            }
        ) { paddingValues ->
            NavHost(
                navController = navController,
                startDestination = Screens.MainScreen.name,
                modifier = Modifier
                    .padding(paddingValues)
            ) {
                composable(route = Screens.RecipesScreen.name) {
                    RecipesScreen(viewModel, context)
                }
                composable(route = Screens.MainScreen.name) {
                    MainScreen(context)
                }
                composable(route = Screens.ProfileScreen.name) {
                    ProfileScreen(viewModel, context, activity)
                }
            }
        }
    }
}