package com.example.myapplication

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

/**
 * Composable function that displays the header text for a form.
 * This function renders a headline text indicating the purpose of the form,
 * using the MaterialTheme's headlineSmall typography style.
 */
@Composable
fun FormHeader() {
    Text(
        text = "Реєстрація системи накопичення енергії", // The title text displayed in the header
        style = MaterialTheme.typography.headlineSmall // Applies the headlineSmall typography style from MaterialTheme
    )
}