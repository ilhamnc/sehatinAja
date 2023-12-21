package com.guntur.sehatinaja.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.guntur.sehatinaja.ui.state.TextFieldState
import com.guntur.sehatinaja.ui.theme.Poppins
import com.guntur.sehatinaja.ui.theme.SehatinAjaTheme

@Composable
fun AgeTextField(
    int : TextFieldState,
    modifier: Modifier = Modifier,
    imeAction: ImeAction = ImeAction.Next,
    onImeAction: () -> Unit = {},
    placeholder: String? = null
) {
    BasicTextField(
        value = int.text,
        onValueChange = {
            if (it.length <= 3){
                int.text = it
            }
                        },
        singleLine = true,
        decorationBox = { innerTextField ->
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp)
                    .background(color = Color(0xFFF2F2F2), shape = MaterialTheme.shapes.small)
            ) {
                Box(modifier = Modifier
                    .align(Alignment.CenterStart)
                    .padding(start = 26.dp, end = 30.dp)) {
                    innerTextField()
                }
                placeholder?.let {
                    Text(
                        text = it,
                        color = Color(0x801E1E1E),
                        modifier = modifier
                            .align(Alignment.CenterEnd)
                            .padding(start = 26.dp, end = 26.dp)
                    )
                }
            }
        },
        keyboardOptions = KeyboardOptions.Default.copy(
            keyboardType = KeyboardType.Number,
            imeAction = imeAction
        ),
        keyboardActions = KeyboardActions(
            onDone = {
                onImeAction()
            }
        ),
        textStyle = TextStyle(
            textAlign = TextAlign.Start,
            fontSize = 16.sp,
            fontFamily = Poppins
        ),
        modifier = modifier
            .fillMaxWidth()
    )
}

@Preview(showBackground = true)
@Composable
fun Preview() {
    SehatinAjaTheme(isStatusBarBackground = true) {
        AgeTextField(int = TextFieldState())
    }
}