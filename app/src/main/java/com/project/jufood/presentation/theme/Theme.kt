package com.project.jufood.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable

private val DarkColorScheme = darkColorScheme(
    background = BackgroundColor,
    surface = SurfaceColor,
    onSurface = DarkTextColor,
    primary = PrimaryColor,
    secondary = SecondaryColor
)

private val LightColorScheme = lightColorScheme(
    background = BackgroundColor,
    surface = SurfaceColor,
    onSurface = LightTextColor,
    primary = PrimaryColor,
    secondary = SecondaryColor
)

@Composable
fun JuFoodTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val juColorScheme = if (darkTheme) DarkColorScheme else LightColorScheme

    MaterialTheme(
        content = content,
        colorScheme = juColorScheme
    )
}