package com.guntur.sehatinaja.ui.component

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import com.guntur.sehatinaja.R
import com.guntur.sehatinaja.ui.theme.SehatinAjaTheme

@Composable
fun TextSehatinAja(
    modifier: Modifier = Modifier,
    color: Color = MaterialTheme.colorScheme.primary
) {
    val kaushanScript = FontFamily(
        Font(R.font.kausah)
    )
    Text(
        text = stringResource(id = R.string.app_name),
        fontSize = 40.sp,
        color = color,
        fontStyle = FontStyle.Italic,
        fontFamily = kaushanScript,
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun TextSehatinAjaPreview() {
    SehatinAjaTheme(false) {
        TextSehatinAja()
    }
}