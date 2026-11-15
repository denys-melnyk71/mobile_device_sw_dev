package com.example.pw1

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

/**
 * Composable-функція для вибору режиму відображення.
 *
 * @param selected Поточний обраний режим відображення.
 * @param onSelectedChange Callback, який викликається при
зміні режиму відображення.
 */

@Composable
fun ScenariosSelector(
    // Поточне значення
    selected: Scenarios,
    // Callback при зміні
    onSelectedChange: (Scenarios) -> Unit,
    // Розмір шрифта
    fontSize: TextUnit = 20.sp
) {
    // Column з кнопками для перемикання режимів відображення
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(12.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Відображаємо заголовок
        Text(
            text = "Розрахунок прогнозу",
            style = MaterialTheme.typography.bodyLarge,
            fontSize = fontSize
        )

        Spacer(modifier = Modifier.height(12.dp))
        // Для кожного значення enum DisplayFor створюємо
        // кнопку вибору
        Scenarios.entries.forEach { mode ->
            Button(
                onClick = { onSelectedChange(mode) },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 4.dp),
                // Робимо кнопку "неактивною", якщо це поточний режим (вже обраний)
                enabled = mode != selected
            ) {
                Text(
                    text = when (mode) {
                        Scenarios.STORE_ENERGY -> "Накопичення"
                        Scenarios.USE_ENERGY -> "Використання"
                    },
                    fontSize = fontSize,
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}

