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
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.guntur.sehatinaja.R
import com.guntur.sehatinaja.ui.state.TextFieldState
import com.guntur.sehatinaja.ui.theme.Poppins
import com.guntur.sehatinaja.ui.theme.SehatinAjaTheme

@Composable
fun PasswordTextField(
    passwordState: TextFieldState,
    modifier: Modifier = Modifier,
    imeAction: ImeAction,
    onImeAction: () -> Unit = {},
    placeholder: String
) {
    var passwordVisibility by remember {
        mutableStateOf(false)
    }
    val isError = passwordState.showErrors()
    val icon = if (passwordVisibility)
        painterResource(id = R.drawable.ic_visibility)
    else
        painterResource(id = R.drawable.ic_visibility_off)

    Column {
        BasicTextField(
            value = passwordState.text,
            onValueChange = {
                passwordState.text = it
                passwordState.enableShowErrors()
            },
            singleLine = true,
            keyboardOptions = KeyboardOptions.Default.copy(
                keyboardType = KeyboardType.Password,
                imeAction = imeAction
            ),
            keyboardActions = KeyboardActions(
                onDone = {
                    onImeAction()
                }
            ),
            visualTransformation =
            if (passwordVisibility) VisualTransformation.None
            else PasswordVisualTransformation(),
            decorationBox = { innerTextField ->
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(48.dp)
                        .border(
                            border = BorderStroke(
                                width = 1.dp,
                                color = if (!isError) Color.Transparent else MaterialTheme.colorScheme.error
                            ),
                            shape = MaterialTheme.shapes.small
                        )
                        .background(color = Color(0xFFF2F2F2), shape = MaterialTheme.shapes.small)
                ) {
                    Box(
                        modifier = Modifier
                            .align(Alignment.CenterStart)
                            .padding(start = 26.dp)
                    ) {
                        innerTextField()
                    }
                    if (passwordState.text.isEmpty()) {
                        Text(
                            text = placeholder,
                            color = Color(0x801E1E1E),
                            modifier = Modifier
                                .align(Alignment.CenterStart)
                                .padding(start = 26.dp)
                        )
                    }
                    IconButton(
                        onClick = { passwordVisibility = !passwordVisibility },
                        modifier = Modifier.align(Alignment.CenterEnd)
                    ) {
                        Icon(painter = icon, contentDescription = null)
                    }
                }
            },
            textStyle = TextStyle(
                textAlign = TextAlign.Start,
                fontSize = 16.sp,
                fontFamily = Poppins
            ),
            modifier = Modifier
                .fillMaxWidth()
        )
        passwordState.getError()?.let { error ->
            Text(
                text = error,
                fontSize = 12.sp
            )
        }

    }
}

@Preview
@Composable
fun PasswordTextFieldPreview() {
    SehatinAjaTheme(isStatusBarBackground = true) {
        PasswordTextField(TextFieldState(), placeholder = "", imeAction = ImeAction.Next)
    }
}