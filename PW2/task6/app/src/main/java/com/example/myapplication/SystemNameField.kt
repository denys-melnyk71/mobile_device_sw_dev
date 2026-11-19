package com.example.myapplication

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.TextFieldValue

/**
 * Composable-функція для відображення поля вводу назви системи.
 * Включає валідацію та відображення повідомлень про помилки або підказок.
 *
 * @param value Поточне значення текстового поля
 * @param isError Прапорець, що вказує на наявність помилки
 * @param onValueChange Callback-функція для обробки зміни значення у полі вводу
 */
@Composable
fun SystemNameField(
    value: TextFieldValue,
    isError: Boolean,
    onValueChange: (TextFieldValue) -> Unit
) {
    // Відображення текстового поля з підписом та перевіркою
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        label = { Text("Назва системи") },
        isError = isError,
        modifier = Modifier.fillMaxWidth()
    )

    // Якщо виникла помилка, відображаємо повідомлення про неї
    if (isError) {
        Text(
            text = "Поле не може бути порожнім",
            color = MaterialTheme.colorScheme.error,
            style = MaterialTheme.typography.bodySmall
        )
    } else {
        // Якщо помилки немає, відображаємо підказку
        Text(
            text = "Введіть офіційну назву вашої СНЕ",
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}