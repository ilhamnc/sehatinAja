package com.guntur.sehatinaja.ui.component

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.guntur.sehatinaja.ui.theme.SehatinAjaTheme

@Composable
fun Background(
    modifier: Modifier = Modifier,
) {
    val color = MaterialTheme.colorScheme.primary
    Canvas(modifier = Modifier.fillMaxSize()) {
        val canvasQuadrantSize = size / 1f
        drawRect(
            color,
            size = canvasQuadrantSize
        )
    }
}

@Preview(showBackground = true)
@Composable
fun BackgroundPreview() {
    SehatinAjaTheme(false) {
        Background()
    }
}
