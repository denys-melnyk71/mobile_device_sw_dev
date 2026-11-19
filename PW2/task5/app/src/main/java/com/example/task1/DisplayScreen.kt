package com.example.task1

import androidx.compose.animation.core.animateIntAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp


/**
 * Composable-екран результатів розрахунку, керування сценарієм та візуалізації
 * стану системи накопичення енергії.
 *
 * @param scenario Поточний активний сценарій роботи ([Scenarios]).
 * @param onScenarioChange Callback, що викликається при перемиканні сценарію.
 * @param energyPercentage Поточне значення рівня заряду у відсотках для слайдера.
 * @param onEnergyPercentageChange Callback при зміні положення слайдера заряду.
 * @param animatedBattery Анімоване значення відсотка заряду для відображення стану сховища.
 * @param resNum Розраховане значення енергії в кВт⋅год.
 * @param resPercentage Розрахований відсоток заряду після виконання розрахунку.
 * @param errorMessage Текст помилки валідації або розрахунку; порожній рядок, якщо помилок немає.
 * @param onCalculateClick Callback для виконання розрахунку за поточними параметрами.
 * @param onBackToMenu Callback для повернення до головного меню.
 */
@Composable
fun DisplayScreen(
    scenario: Scenarios,
    onScenarioChange: (Scenarios) -> Unit,
    energyPercentage: Float,
    onEnergyPercentageChange: (Float) -> Unit,
    animatedBattery: Int,
    resNum: Float,
    resPercentage: Int,
    errorMessage: String,
    onCalculateClick: () -> Unit,
    onBackToMenu: () -> Unit
) {
    val animatedSliderBattery by animateIntAsState(
        targetValue = energyPercentage.toInt(),
        label = "SliderBatteryAnimation"
    )

    BoxWithConstraints(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp)
    ) {
        val isWideScreen = maxWidth >= 600.dp
        val imageHeight = if (isWideScreen) 200.dp else 280.dp

        if (isWideScreen) {
            Row(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState()),
                horizontalArrangement = Arrangement.spacedBy(20.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(
                    modifier = Modifier.weight(1f),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    ControlsSection(
                        scenario = scenario,
                        onScenarioChange = onScenarioChange,
                        onCalculateClick = onCalculateClick,
                        isWideScreen = true,
                        modifier = Modifier.fillMaxWidth()
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    Text(
                        text = "Рівень заряду: $animatedSliderBattery %",
                        fontSize = 20.sp,
                        color = MaterialTheme.colorScheme.onBackground
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    Slider(
                        value = energyPercentage,
                        onValueChange = onEnergyPercentageChange,
                        valueRange = 0f..100f,
                        steps = 99,
                        modifier = Modifier.fillMaxWidth()
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    Text(
                        text = "Розрахований заряд: $resNum кВт⋅год ($resPercentage%)",
                        style = MaterialTheme.typography.bodyLarge,
                        fontSize = 20.sp
                    )

                    if (errorMessage.isNotEmpty()) {
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = errorMessage,
                            color = MaterialTheme.colorScheme.error,
                            fontSize = 16.sp
                        )
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    OutlinedButton(
                        onClick = onBackToMenu,
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

                StorageStatusSection(
                    batteryStorage = animatedBattery,
                    scenario = scenario,
                    imageHeight = imageHeight,
                    isWideScreen = true,
                    modifier = Modifier.weight(1f)
                )
            }
        } else {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState()),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                ControlsSection(
                    scenario = scenario,
                    onScenarioChange = onScenarioChange,
                    onCalculateClick = onCalculateClick,
                    isWideScreen = false
                )

                Spacer(modifier = Modifier.height(12.dp))

                StorageStatusSection(
                    batteryStorage = animatedBattery,
                    scenario = scenario,
                    imageHeight = imageHeight,
                    isWideScreen = false
                )

                Spacer(modifier = Modifier.height(12.dp))

                Text(
                    text = "Рівень заряду: $animatedSliderBattery %",
                    fontSize = 20.sp,
                    color = MaterialTheme.colorScheme.onBackground
                )

                Spacer(modifier = Modifier.height(8.dp))

                Slider(
                    value = energyPercentage,
                    onValueChange = onEnergyPercentageChange,
                    valueRange = 0f..100f,
                    steps = 99,
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(12.dp))

                Text(
                    text = "Розрахований заряд: $resNum кВт⋅год ($resPercentage%)",
                    style = MaterialTheme.typography.bodyLarge,
                    fontSize = 20.sp
                )

                if (errorMessage.isNotEmpty()) {
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = errorMessage,
                        color = MaterialTheme.colorScheme.error,
                        fontSize = 16.sp
                    )
                }

                Spacer(modifier = Modifier.height(12.dp))

                OutlinedButton(
                    onClick = onBackToMenu,
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
}