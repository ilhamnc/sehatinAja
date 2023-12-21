package com.guntur.sehatinaja

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.core.view.WindowCompat
import com.guntur.sehatinaja.ui.SehatinAjaApp
import com.guntur.sehatinaja.ui.theme.SehatinAjaTheme

class MainActivity : ComponentActivity() {
    private var isStatusBar by mutableStateOf(false)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, false)
        setContent {
            SehatinAjaTheme(isStatusBarBackground = isStatusBar) {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    SehatinAjaApp(context = this){ onStatusBar ->
                        isStatusBar = onStatusBar
                    }
                }
            }
        }
    }
}