package com.guntur.sehatinaja.ui.component

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun LoadingScreen(modifier: Modifier = Modifier) {
    Box(modifier = modifier
        .fillMaxSize()) {
        CircularProgressIndicator(
            modifier = modifier
                .size(50.dp)
                .align(Alignment.Center)
        )
    }
}