package com.guntur.sehatinaja.ui.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import com.guntur.sehatinaja.R
import com.guntur.sehatinaja.ui.theme.SehatinAjaTheme

@Composable
fun NotFoundScreen(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = R.drawable.not_found), contentDescription = ""
        )
    }

}

@Preview
@Composable
fun NotFoundScreen() {
    SehatinAjaTheme(isStatusBarBackground = true) {
        NotFoundScreen(modifier = Modifier.fillMaxSize())
    }
}