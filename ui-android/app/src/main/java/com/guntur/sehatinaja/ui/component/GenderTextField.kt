package com.guntur.sehatinaja.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
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
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.guntur.sehatinaja.ui.theme.Poppins
import com.guntur.sehatinaja.ui.theme.SehatinAjaTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GenderTextField() {
    val options = listOf("Laki-laki", "Perempuan")
    var expanded by remember {
        mutableStateOf(false)
    }
    var selectOptionText by remember {
        mutableStateOf(options[0])
    }
    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = {expanded = it}
    ) {
        BasicTextField(
            value = selectOptionText,
            onValueChange = {selectOptionText = it},
            readOnly = true,
            textStyle = TextStyle(
                fontFamily = Poppins,
                fontSize = 16.sp,
            ),
            decorationBox = { innerTextField ->
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(48.dp)
                        .background(color = Color(0xFFF2F2F2), shape = MaterialTheme.shapes.small)
                ) {
                    Box(
                        modifier = Modifier
                            .align(Alignment.CenterStart)
                            .padding(start = 26.dp)
                    ) {
                        innerTextField()
                    }
                    Box(
                        modifier = Modifier
                            .align(Alignment.CenterEnd)
                            .padding(end = 16.dp)
                    ) {
                        ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded)
                    }
                }
            },
            modifier = Modifier.menuAnchor()
        )
        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ){
            options.forEach{s ->
                DropdownMenuItem(
                    text = {
                        Text(
                            text = s,
                            fontFamily = Poppins,
                            fontSize = 16.sp,
                            )
                           },
                    onClick = {
                        selectOptionText = s
                        expanded = false
                    },
                    contentPadding = ExposedDropdownMenuDefaults.ItemContentPadding
                )
            }
        }
    }
}

@Preview
@Composable
fun GenderTextFieldPreview() {
    SehatinAjaTheme(isStatusBarBackground = true) {
        GenderTextField()
    }
}