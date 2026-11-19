package com.example.task1

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp


/**
 * Composable-екран конфігурації параметрів системи накопичення енергії: ємності,
 * потужності та проміжку часу.
 *
 * @param capacity Поточне текстове значення ємності в кВт⋅год.
 * @param onCapacityChange Callback, що викликається при зміні значення ємності.
 * @param power Поточне текстове значення потужності в кВт.
 * @param onPowerChange Callback, що викликається при зміні значення потужності.
 * @param timeDelta Поточне текстове значення часу в годинах.
 * @param onTimeDeltaChange Callback, що викликається при зміні значення часу.
 * @param isCapacityError Ознака некоректного значення в полі ємності.
 * @param isPowerError Ознака некоректного значення в полі потужності.
 * @param isTimeError Ознака некоректного значення в полі часу.
 * @param onSaveConfig Callback для збереження конфігурації перед навігацією.
 * @param onOpenResult Callback для переходу на екран результату та сценарію.
 * @param onBackToMenu Callback для повернення до головного меню.
 */
@Composable
fun ConfigScreen(
    capacity: String,
    onCapacityChange: (String) -> Unit,
    power: String,
    onPowerChange: (String) -> Unit,
    timeDelta: String,
    onTimeDeltaChange: (String) -> Unit,
    isCapacityError: Boolean,
    isPowerError: Boolean,
    isTimeError: Boolean,
    onSaveConfig: () -> Unit,
    onOpenResult: () -> Unit,
    onBackToMenu: () -> Unit
) {
    BoxWithConstraints(
        modifier = Modifier
            .fillMaxSize()
            .imePadding()
            .padding(20.dp)
    ) {
        val isWideScreen = maxWidth >= 600.dp

        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            if (isWideScreen) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(20.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column(
                        modifier = Modifier.weight(1f),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = "Конфігурація системи накопичення енергії",
                            style = MaterialTheme.typography.bodyLarge,
                            fontSize = 20.sp
                        )

                        Spacer(modifier = Modifier.height(16.dp))

                        NumericTextField(
                            value = capacity,
                            onValueChange = onCapacityChange,
                            label = "Ємність (кВт⋅год)",
                            isError = isCapacityError
                        )
                    }

                    Column(
                        modifier = Modifier.weight(1f),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        NumericTextField(
                            value = power,
                            onValueChange = onPowerChange,
                            label = "Потужність (кВт)",
                            isError = isPowerError
                        )
                        NumericTextField(
                            value = timeDelta,
                            onValueChange = onTimeDeltaChange,
                            label = "Час (год)",
                            isError = isTimeError
                        )
                    }
                }
            } else {
                Text(
                    text = "Конфігурація системи накопичення енергії",
                    style = MaterialTheme.typography.bodyLarge,
                    fontSize = 20.sp
                )

                Spacer(modifier = Modifier.height(16.dp))

                NumericTextField(
                    value = capacity,
                    onValueChange = onCapacityChange,
                    label = "Ємність (кВт⋅год)",
                    isError = isCapacityError
                )

                NumericTextField(
                    value = power,
                    onValueChange = onPowerChange,
                    label = "Потужність (кВт)",
                    isError = isPowerError
                )

                NumericTextField(
                    value = timeDelta,
                    onValueChange = onTimeDeltaChange,
                    label = "Час (год)",
                    isError = isTimeError
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = {
                    onSaveConfig()
                    onOpenResult()
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 4.dp)
            ) {
                Text(
                    text = "Результат і сценарій",
                    fontSize = 20.sp
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            OutlinedButton(
                onClick = {
                    onSaveConfig()
                    onBackToMenu()
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 4.dp)
            ) {
                Text(
                    text = "Головне меню",
                    fontSize = 20.sp
                )
            }
        }
    }
}
