package com.zee.amusicplayer.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider

private val darkColorPalette = darkColors(
    primary = colorPrimary,
    primaryVariant = colorPrimary700,
    secondary = colorPrimary,
    surface = DarkColorSurface,
)

private val lightColorPalette = lightColors(
    primary = colorPrimary,
    primaryVariant = colorPrimary700,
    secondary = colorPrimary,
    surface = LightColorSurface,
    background = lightBackGroundColor


    /* Other default colors to override
    background = Color.White,
    surface = Color.White,
    onPrimary = Color.White,
    onSecondary = Color.Black,
    onBackground = Color.Black,
    onSurface = Color.Black,
    */
)

@Composable
fun AMusicPlayerTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable() () -> Unit
) {
    val colors = if (darkTheme) {
        darkColorPalette
    } else {
        lightColorPalette
    }


    MaterialTheme(
        colors = colors,
        typography = Typography,
        shapes = Shapes,
    )
    {
        CompositionLocalProvider(
            content = content
        )
    }
}

