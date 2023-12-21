package com.guntur.sehatinaja.ui.navigation

sealed class Screen(val route: String) {
    object Welcome: Screen("welcome")
    object Login: Screen("login")
    object Register: Screen("register")
    object Home: Screen("home")
    object History: Screen("history")
    object Profile: Screen("profile")
    object Detail: Screen("home/{age}/{height}/{weight}"){
        fun createRoute(age: String,height: String, weight: String) = "home/$age/$height/$weight"
    }
}