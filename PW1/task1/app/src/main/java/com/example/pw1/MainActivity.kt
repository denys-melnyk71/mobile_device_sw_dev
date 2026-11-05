package com.example.pw1


import kotlin.random.Random
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.material3.Button
import androidx.compose.ui.Modifier
import com.example.pw1.ui.theme.PW1Theme
import androidx.compose.material3.Surface
import androidx.compose.material3.MaterialTheme
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.Column
import androidx.compose.ui.Alignment
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.animation.core.animateIntAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            PW1Theme {
                var BatteryStorage by remember { mutableStateOf(100) }
                val animatedBattery by animateIntAsState(
                    targetValue = BatteryStorage, // Цільове значення для анімації
                    label = "BatteryAnimation" // Мітка для відлагодження
                )
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {

                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(20.dp), // Додає внутрішній відступ
                        horizontalAlignment = Alignment.CenterHorizontally, // Вирівнювання по центру горизонтально
                        verticalArrangement = Arrangement.Center
                    ) {
                        Text(
                            text = "Система накопичення енергії",
                            fontSize = 26.sp,
                            color = MaterialTheme.colorScheme.primary
                        )

                        Spacer(modifier = Modifier.height(16.dp))

                        Text(
                            text = "Заряд батареї: $animatedBattery %",
                            fontSize = 22.sp,
                            color = MaterialTheme.colorScheme.onBackground

                        )

                        Spacer(modifier = Modifier.height(24.dp))

                        Button(onClick = {
                        // При натисканні на кнопку зменшуємо заряд батареї на випадкове значення
                        // в діапазоні 1-10 %. Переконуємось що нове значення невід'ємне
                            BatteryStorage = maxOf(
                                BatteryStorage - Random.nextInt(1, 10),
                                0
                            )
                        }) {
                            Text(
                                text = "Використати заряд", // Текст кнопки
                                fontSize = 22.sp
                            )
                        }

                        Button(onClick = {
                            // При натисканні на кнопку зменшуємо заряд батареї на випадкове значення
                            // в діапазоні 1-10 %. Переконуємось що нове значення невід'ємне
                            BatteryStorage = minOf(
                                BatteryStorage + Random.nextInt(1, 10),
                                100
                            )
                        }) {
                            Text(
                                text = "Зарядити батарею", // Текст кнопки
                                fontSize = 22.sp
                            )
                        }
                    }
                }

            }
        }
    }
}
