package com.example.task1

import androidx.compose.foundation.layout.Column
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

enum class Scenarios {
    STORE_ENERGY,
    USE_ENERGY
}

/**
 * Composable-функція для перемикання сценарію однією кнопкою.
 *
 * @param selected Поточний обраний сценарій.
 * @param onSelectedChange Callback, який викликається при зміні сценарію на протилежний.
 * @param fontSize Розмір шрифта.
 */
@Composable
fun ScenariosSelector(
    selected: Scenarios,
    onSelectedChange: (Scenarios) -> Unit,
    fontSize: TextUnit = 20.sp
) {
    val nextScenario = when (selected) {
        Scenarios.STORE_ENERGY -> Scenarios.USE_ENERGY
        Scenarios.USE_ENERGY -> Scenarios.STORE_ENERGY
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(12.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Розрахунок прогнозу",
            style = MaterialTheme.typography.bodyLarge,
            fontSize = fontSize
        )

        Spacer(modifier = Modifier.height(12.dp))

        Button(
            onClick = { onSelectedChange(nextScenario) },
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 4.dp)
        ) {
            Text(
                text = when (selected) {
                    Scenarios.STORE_ENERGY -> "Режим: Накопичення"
                    Scenarios.USE_ENERGY -> "Режим: Використання"
                },
                fontSize = fontSize,
                textAlign = TextAlign.Center
            )
        }
    }
}