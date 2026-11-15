package com.example.pw1

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun ControlsSection(
    scenario: Scenarios,
    onScenarioChange: (Scenarios) -> Unit,
    onCalculateClick: () -> Unit,
    isWideScreen: Boolean,
    modifier: Modifier = Modifier
) {
    val titleFontSize = if (isWideScreen) 24.sp else 26.sp
    val itemFontSize = if (isWideScreen) 20.sp else 22.sp
    val spacing = if (isWideScreen) 12.dp else 16.dp

    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Система накопичення енергії",
            fontSize = titleFontSize,
            textAlign = TextAlign.Center,
            color = MaterialTheme.colorScheme.primary
        )

        Spacer(modifier = Modifier.height(spacing))

        ScenariosSelector(
            selected = scenario,
            onSelectedChange = onScenarioChange,
            fontSize = itemFontSize
        )

        Spacer(modifier = Modifier.height(8.dp))

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