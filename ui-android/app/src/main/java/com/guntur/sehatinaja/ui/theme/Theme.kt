package com.guntur.sehatinaja.ui.theme

import android.app.Activity
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

private val DarkColorScheme = darkColorScheme(
    primary = Blue80,
    secondary = YellowSun,
    tertiary = RedDark
)

private val LightColorScheme = lightColorScheme(
    primary = Blue50,
    secondary = Blue,
    tertiary = BlueSky

    /* Other default colors to override
    background = Color(0xFFFFFBFE),
    surface = Color(0xFFFFFBFE),
    onPrimary = Color.White,
    onSecondary = Color.White,
    onTertiary = Color.White,
    onBackground = Color(0xFF1C1B1F),
    onSurface = Color(0xFF1C1B1F),
    */
)

@Composable
fun SehatinAjaTheme(
    isStatusBarBackground : Boolean,
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme =
        if (!darkTheme){
            LightColorScheme
        }else{
            DarkColorScheme
        }
    val view = LocalView.current
    if (!view.isInEditMode) {
        val statusBarColor = if (isStatusBarBackground){
            if (darkTheme){
                Blue80.toArgb()
            }else{
                Blue50.toArgb()
            }
        }else {
            Color.Transparent.toArgb()
        }
        val window = (view.context as Activity).window
        window.statusBarColor = statusBarColor
        window.navigationBarColor = statusBarColor
        WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = !darkTheme
    }
    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}