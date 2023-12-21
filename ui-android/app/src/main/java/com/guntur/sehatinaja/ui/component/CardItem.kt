package com.guntur.sehatinaja.ui.component

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.guntur.sehatinaja.ui.theme.SehatinAjaTheme

@Composable
fun CardItem(modifier: Modifier = Modifier) {
    Card(
        modifier = modifier.fillMaxWidth()
    ){
        Column {
            Text(
                text = "Name",
                fontSize = 20.sp,
                modifier = Modifier.padding(top = 8.dp, start = 8.dp)
            )
            Box(
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(
                    modifier = Modifier.padding(8.dp)
                ) {
                    Text(
                        text = "21 th |",
                        fontSize = 12.sp
                    )
                    Text(
                        text = " 170 cm |",
                        fontSize = 12.sp
                    )
                    Text(
                        text = " 60 kg",
                        fontSize = 12.sp
                    )
                }
                Text(
                    text = "20-11-2023",
                    fontSize = 12.sp,
                    modifier = Modifier
                        .align(Alignment.BottomEnd)
                        .padding(8.dp)
                )
            }
        }
    }
}

@Preview
@Composable
fun CardItemPreview() {
    SehatinAjaTheme(isStatusBarBackground = true) {
        CardItem()
    }
}