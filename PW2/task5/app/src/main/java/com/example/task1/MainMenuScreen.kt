package com.example.task1

import android.view.Display
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
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp


/**
 * Composable-екран головного меню застосунку для переходу між налаштуванням
 * параметрів системи та прогнозуванням заряду.
 *
 * @param onOpenInput Callback для переходу на екран конфігурації системи.
 * @param onOpenResult Callback для переходу на екран прогнозування заряду.
 */
@Composable
fun MainMenuScreen(
    onOpenInput: () -> Unit,
    onOpenResult: () -> Unit
) {
    BoxWithConstraints(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp)
    ) {
        val isWideScreen = maxWidth >= 600.dp
        val titleFontSize = if (isWideScreen) 24.sp else 26.sp

        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = "Система накопичення енергії",
                fontSize = titleFontSize,
                textAlign = TextAlign.Center,
                color = MaterialTheme.colorScheme.primary
            )

            Spacer(modifier = Modifier.height(20.dp))

            if (isWideScreen) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(20.dp, Alignment.CenterHorizontally),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Button(
                        onClick = onOpenInput,
                        modifier = Modifier
                            .weight(1f)
                            .padding(vertical = 4.dp)
                    ) {
                        Text(
                            text = "Конфігурація системи",
                            fontSize = 20.sp
                        )
                    }

                    Button(
                        onClick = onOpenResult,
                        modifier = Modifier
                            .weight(1f)
                            .padding(vertical = 4.dp)
                    ) {
                        Text(
                            text = "Прогнозування заряду",
                            fontSize = 20.sp
                        )
                    }
                }
            } else {
                Button(
                    onClick = onOpenInput,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp)
                ) {
                    Text(
                        text = "Конфігурація системи",
                        fontSize = 20.sp
                    )
                }

                Spacer(modifier = Modifier.height(6.dp))

                Button(
                    onClick = onOpenResult,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp)
                ) {
                    Text(
                        text = "Прогнозування заряду",
                        fontSize = 20.sp
                    )
                }
            }
        }
    }
}
