package com.guntur.sehatinaja.ui.navigation

import androidx.compose.ui.graphics.painter.Painter

data class NavigationItem(
    val title: String,
    val iconOutline: Painter,
    val iconFilled: Int,
    val screen: Screen
)