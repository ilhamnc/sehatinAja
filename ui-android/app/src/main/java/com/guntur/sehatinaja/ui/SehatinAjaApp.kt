package com.guntur.sehatinaja.ui

import android.content.Context
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.guntur.sehatinaja.R
import com.guntur.sehatinaja.ui.component.BottomBar
import com.guntur.sehatinaja.ui.component.TextSehatinAja
import com.guntur.sehatinaja.ui.navigation.Screen
import com.guntur.sehatinaja.ui.screen.detail.DetailScreen
import com.guntur.sehatinaja.ui.screen.history.HistoryScreen
import com.guntur.sehatinaja.ui.screen.home.HomeScreen
import com.guntur.sehatinaja.ui.screen.login.LoginScreen
import com.guntur.sehatinaja.ui.screen.profile.ProfileScreen
import com.guntur.sehatinaja.ui.screen.register.RegisterScreen
import com.guntur.sehatinaja.ui.screen.welcome.WelcomeScreen
import com.guntur.sehatinaja.ui.theme.Poppins
import com.guntur.sehatinaja.ui.theme.SehatinAjaTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SehatinAjaApp(
    context: Context,
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
    onStatusBar : (Boolean) -> Unit
) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route
    Scaffold(
        bottomBar = {
            if (currentRoute !in listOf(
                    Screen.Login.route, Screen.Register.route, Screen.Welcome.route, Screen.Detail.route)
                ){
                BottomBar(navController = navController)
            }
        },
        topBar = {
            if (currentRoute == Screen.Home.route){
                TopAppBar(
                    title = { TextSehatinAja() }
                )
            }
            if (currentRoute == Screen.History.route){
                TopAppBar(
                    title = {
                        Text(
                            text = "HISTORY",
                            fontWeight = FontWeight.ExtraBold,
                            color = MaterialTheme.colorScheme.primary,
                            fontFamily = Poppins,
                            modifier = Modifier.padding(top = 5.dp, start = 8.dp)
                        )
                            },
                    navigationIcon = {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_home),
                            contentDescription = "Home",
                            modifier = Modifier
                                .padding(start = 20.dp)
                                .clickable(
                                    onClick = {
                                        navController.navigate(Screen.Home.route)
                                    }
                                )
                        )
                    }
                    )
            }
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = Screen.Home.route,
            modifier = modifier.padding(innerPadding)
        ) {
            composable(Screen.Welcome.route) {
                WelcomeScreen(navHostController = navController)
                onStatusBar(true)
            }
            composable(Screen.Login.route) {
                LoginScreen(
                    context = context,
                    navController = navController
                )
                onStatusBar(false)
            }
            composable(Screen.Register.route) {
                RegisterScreen(
                    context = context,
                    navController = navController
                )
            }
            composable(Screen.Home.route) {
                HomeScreen(
                    navController = {age, height, weight ->
                    navController.navigate(Screen.Detail.createRoute(age, height, weight))},
                    navHostController = navController,
                    context = context,
                )
            }
            composable(Screen.History.route) {
                HistoryScreen()
            }
            composable(Screen.Profile.route) {
                ProfileScreen()
            }
            composable(
                route = Screen.Detail.route,
                arguments = listOf(
                    navArgument("age"){ type = NavType.StringType},
                    navArgument("height"){ type = NavType.StringType},
                    navArgument("weight"){ type = NavType.StringType},
                )
            ){
                val age = it.arguments?.getString("age")
                val weight = it.arguments?.getString("weight")
                val height = it.arguments?.getString("height")
                DetailScreen(
                    context = context,
                    age = age.toString(),
                    height = height.toString(),
                    weight = weight.toString()
                )
            }
        }
    }
}

@Preview
@Composable
fun SehatinAjaAppPreview() {
    SehatinAjaTheme(isStatusBarBackground = true) {
        SehatinAjaApp(context = LocalContext.current){}
    }
}