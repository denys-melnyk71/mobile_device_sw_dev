package com.example.myapplication

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.OutlinedTextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.material3.Text
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.sp

@Composable
fun NumericTextField(
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    isError: Boolean = false,
    modifier: Modifier = Modifier,
    fontSize: TextUnit = 20.sp
) {
    OutlinedTextField(
        value = value,
        onValueChange = { input ->
            // Замінюємо кому на крапку та перевіряємо, чи це валідне число
            val sanitized = input.replace(',', '.')
            if (sanitized.isEmpty() || sanitized.toDoubleOrNull() != null || sanitized == ".") {
                onValueChange(sanitized)
            }
        },
        label = { Text(label) },
        singleLine = true,
        isError = isError,
        modifier = modifier.fillMaxWidth(),
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
        textStyle = TextStyle(fontSize = fontSize)
    )
}