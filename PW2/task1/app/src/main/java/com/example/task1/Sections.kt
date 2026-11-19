package com.example.task1

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp


/**
 * Composable-секція керування сценарієм роботи та запуску розрахунку.
 *
 * Відображає перемикач сценарію ([ScenariosSelector]) і кнопку «Розрахувати»,
 * адаптуючи розміри шрифту, відступи та ширину кнопки залежно від того, чи
 * використовується широкий екран ([isWideScreen]).
 *
 * @param scenario Поточний обраний сценарій ([Scenarios]).
 * @param onScenarioChange Callback, що викликається при зміні сценарію.
 * @param onCalculateClick Callback, що викликається при натисканні кнопки «Розрахувати».
 * @param isWideScreen Ознака широкого екрана для підбору адаптивних розмірів і відступів.
 * @param modifier Додатковий [Modifier] для зовнішнього контейнера [Column].
 */
@Composable
fun ControlsSection(
    scenario: Scenarios,
    onScenarioChange: (Scenarios) -> Unit,
    onCalculateClick: () -> Unit,
    isWideScreen: Boolean,
    modifier: Modifier = Modifier
) {
    val itemFontSize = if (isWideScreen) 20.sp else 22.sp
    val spacing = if (isWideScreen) 12.dp else 16.dp

    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        ScenariosSelector(
            selected = scenario,
            onSelectedChange = onScenarioChange,
            fontSize = itemFontSize
        )

        Spacer(modifier = Modifier.height(spacing))

        Button(
            onClick = onCalculateClick,
            modifier = if (isWideScreen) Modifier.fillMaxWidth() else Modifier
        ) {
            Text(
                text = "Розрахувати",
                fontSize = itemFontSize
            )
        }
    }
}


/**
 * Composable-секція візуалізації поточного стану системи накопичення енергії.
 *
 * Відображає ілюстрацію системи накопичення (`R.drawable.storage`) із заданою
 * висотою ([imageHeight]) та інформаційну картку ([InfoCard]) із поточним рівнем
 * заряду й активним сценарієм, підбираючи вертикальний відступ залежно від
 * ширини екрана ([isWideScreen]).
 *
 * @param batteryStorage Поточний або прогнозований рівень заряду у відсотках.
 * @param scenario Поточний обраний сценарій роботи ([Scenarios]).
 * @param imageHeight Висота зображення системи накопичення енергії.
 * @param isWideScreen Ознака широкого екрана для підбору адаптивного відступу.
 * @param modifier Додатковий [Modifier] для зовнішнього контейнера [Column].
 */
@Composable
fun StorageStatusSection(
    batteryStorage: Int,
    scenario: Scenarios,
    imageHeight: Dp,
    isWideScreen: Boolean,
    modifier: Modifier = Modifier
) {
    val spacing = if (isWideScreen) 12.dp else 16.dp

    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = R.drawable.storage),
            contentDescription = "Система накопичення енергії",
            modifier = Modifier
                .height(imageHeight)
                .fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(spacing))

        InfoCard(batteryStorage, scenario)
    }
}