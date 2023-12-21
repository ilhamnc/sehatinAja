package com.guntur.sehatinaja.ui.screen.welcome

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Alignment.Companion.TopCenter
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.guntur.sehatinaja.R
import com.guntur.sehatinaja.ui.component.Background
import com.guntur.sehatinaja.ui.component.TextSehatinAja
import com.guntur.sehatinaja.ui.navigation.Screen
import com.guntur.sehatinaja.ui.theme.Poppins
import com.guntur.sehatinaja.ui.theme.SehatinAjaTheme

@Composable
fun WelcomeScreen(
    navHostController: NavHostController
) {
    WelcomeContent(
        navHostController = navHostController
    )
}

@Composable
fun WelcomeContent(
    navHostController: NavHostController,
    modifier: Modifier = Modifier
) {
    val colorWhite = Color.White
    val colorBlack = Color.Black
    Box(
        modifier = modifier
            .fillMaxSize()
    ) {
        Background()
        Column(
            modifier = Modifier
                .align(TopCenter)
        ) {
            TextSehatinAja(
                color = colorWhite,
                modifier = Modifier
                    .padding(top = 96.dp, bottom = 56.dp)
                    .align(CenterHorizontally)
            )
            Image(
                painter = painterResource(R.drawable.group_8),
                contentDescription = "",
                modifier = Modifier
                    .padding(16.dp)
                    .align(CenterHorizontally)
            )
            Text(
                text = stringResource(R.string.welcome_text),
                color = colorWhite,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .padding(
                        top = 36.dp,
                        bottom = 50.dp,
                        start = 45.dp,
                        end = 45.dp
                    )
            )
            Button(
                onClick = { navHostController.navigate(Screen.Login.route) },
                colors = ButtonDefaults.buttonColors(
                    containerColor = colorWhite,
                    contentColor = colorBlack
                ),
                shape = MaterialTheme.shapes.small,
                modifier = Modifier
                    .padding(top = 36.dp)
                    .size(width = 80.dp, height = 40.dp)
                    .align(CenterHorizontally)
            ) {
                Text(
                    text = stringResource(id = R.string.go),
                    fontFamily = Poppins
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun WelcomeScreenPreview() {
    SehatinAjaTheme(true) {
        val navHostController = rememberNavController()
        WelcomeScreen(navHostController = navHostController)
    }
}

