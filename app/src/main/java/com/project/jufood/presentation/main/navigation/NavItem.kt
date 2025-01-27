package com.project.jufood.presentation.main.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Search
import androidx.compose.ui.graphics.vector.ImageVector

data class NavItem (
    val label: String,
    val icon: ImageVector,
    val route: String
)

val listOfNavItems = listOf(
    NavItem(
        label = "Рецепты",
        icon = Icons.Default.Search,
        route = Screens.RecipesScreen.name
    ),
    NavItem(
        label = "Главная",
        icon = Icons.Default.Home,
        route = Screens.MainScreen.name
    ),
    NavItem(
        label = "Профиль",
        icon = Icons.Default.AccountCircle,
        route = Screens.ProfileScreen.name
    )
)