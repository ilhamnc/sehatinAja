package com.guntur.sehatinaja.ui.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.guntur.sehatinaja.R
import com.guntur.sehatinaja.ui.navigation.NavigationItem
import com.guntur.sehatinaja.ui.navigation.Screen
import com.guntur.sehatinaja.ui.theme.Poppins
import com.guntur.sehatinaja.ui.theme.SehatinAjaTheme

@Composable
fun BottomBar(
    navController: NavHostController,
) {
    BottomAppBar(
        modifier = Modifier.height(68.dp),
        containerColor = MaterialTheme.colorScheme.primary,
        contentColor = MaterialTheme.colorScheme.primary
    ) {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = navBackStackEntry?.destination?.route
        val navigationItems = listOf(
            NavigationItem(
                title = "home",
                iconOutline = painterResource(id = R.drawable.ic_home),
                iconFilled = R.raw.home_white,
                screen = Screen.Home
            ),
            NavigationItem(
                title = "history",
                iconOutline = painterResource(id = R.drawable.ic_history),
                iconFilled = R.raw.history_,
                screen = Screen.History
            ),
            NavigationItem(
                title = "profile",
                iconOutline = painterResource(id = R.drawable.ic_person),
                iconFilled = R.raw.person_white,
                screen = Screen.Profile
            )
        )
        navigationItems.map { item ->
            NavigationBarItem(
                selected = currentRoute == item.screen.route,
                onClick = {
                    navController.navigate(item.screen.route){
                        popUpTo(navController.graph.findStartDestination().id){
                            saveState = true
                        }
                        restoreState = true
                        launchSingleTop = true
                    }
                },
                icon = {
                    Column {
                        if (currentRoute == item.screen.route){
                            IconLottie(
                                jsonRes = item.iconFilled,
                                modifier = Modifier
                                    .size(26.dp)
                                    .align(CenterHorizontally)
                            )
                        }else{
                            Icon(
                                painter = item.iconOutline,
                                contentDescription = item.title,
                                tint = Color(0xFFCACACA),
                                modifier = Modifier
                                    .size(26.dp)
                                    .align(CenterHorizontally)
                            )
                        }
                        Text(
                            text = item.title,
                            fontFamily = Poppins,
                            color = Color.White,
                            fontSize = 12.sp,
                            modifier = Modifier
                                .align(CenterHorizontally)
                        )
                    }
                },
                colors = NavigationBarItemDefaults.colors(
                    indicatorColor = MaterialTheme.colorScheme.primary,)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun BottomBarPreview() {
    SehatinAjaTheme(isStatusBarBackground = true) {
        BottomBar(navController = rememberNavController())
    }
}