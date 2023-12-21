package com.guntur.sehatinaja.ui.screen.profile

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

@Composable
fun ProfileScreen(
) {
    Column {
        Text(text = "ProfileScreen")
        Button(onClick = { /*TODO*/ }) {
            Text(text = "LogOut")
        }
    }
}