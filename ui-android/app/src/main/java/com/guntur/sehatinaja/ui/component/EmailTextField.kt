package com.guntur.sehatinaja.ui.component

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.guntur.sehatinaja.R
import com.guntur.sehatinaja.ui.state.EmailState
import com.guntur.sehatinaja.ui.state.TextFieldState
import com.guntur.sehatinaja.ui.theme.Poppins

@Composable
fun EmailTextField(
    emailState: TextFieldState = remember {
        EmailState()
    },
    imeAction: ImeAction = ImeAction.Next,
    onImeAction: () -> Unit = {}
) {
    val isError = emailState.showErrors()
    Column {
        BasicTextField(
            value = emailState.text,
            onValueChange = { emailState.text = it },
            singleLine = true,
            decorationBox = { innerTextField ->
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(48.dp)
                        .background(color = Color(0xFFF2F2F2), shape = MaterialTheme.shapes.small)
                        .border(
                            border = BorderStroke(
                                width = 1.dp,
                                color = if (!isError) Color.Transparent else MaterialTheme.colorScheme.error
                            ),
                            shape = MaterialTheme.shapes.small
                        )
                ) {
                    Box(
                        modifier = Modifier
                            .align(Alignment.CenterStart)
                            .padding(start = 26.dp)
                    ) {
                        innerTextField()
                    }
                    if (emailState.text.isEmpty()) {
                        Text(
                            text = stringResource(id = R.string.email),
                            color = Color(0x801E1E1E),
                            modifier = Modifier
                                .align(Alignment.CenterStart)
                                .padding(start = 26.dp)
                        )
                    }
                }
            },
            keyboardOptions = KeyboardOptions.Default.copy(
                keyboardType = KeyboardType.Email,
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
            modifier = Modifier
                .fillMaxWidth()
                .onFocusChanged { focusState ->
                    emailState.onFocusChange(focusState.isFocused)
                    if (!focusState.isFocused) {
                        emailState.enableShowErrors()
                    }
                }
        )
        emailState.getError()?.let { error ->
            Text(
                text = error,
                fontSize = 12.sp
            )
        }
    }
}