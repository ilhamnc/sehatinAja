package com.guntur.sehatinaja.ui.screen.history

import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.guntur.sehatinaja.ui.component.CardItem

@Composable
fun HistoryScreen() {
//    NotFoundScreen(modifier = Modifier.fillMaxSize())
    HistoryContent(modifier = Modifier.padding(20.dp))
}

@Composable
fun HistoryContent(modifier: Modifier = Modifier) {
    CardItem(modifier = modifier)
}