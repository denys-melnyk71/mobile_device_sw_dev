package com.example.myapplication

import android.health.connect.datatypes.units.Percentage
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
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Slider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.setValue
import com.example.myapplication.ui.theme.MyApplicationTheme


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MyApplicationTheme {
                App()
            }
        }
    }
}


@Composable
fun App() {
    // вибір режиму
    var displayFor          by remember { mutableStateOf(DisplayFor.STORE_ENERGY) }
    // коєфіцієнт, що = 1 для зарядки та -1 для розрядки
    var k                   by remember { mutableIntStateOf(1) }

    // змінні для інпутів
    var capacity            by remember { mutableStateOf("") }          // кВт⋅год
    var energyPercentage    by remember { mutableFloatStateOf(100f) }   // кВт⋅год
    var time_delta          by remember { mutableStateOf("") }          // год
    var power               by remember { mutableStateOf("") }          // кВт

    // Прапорці для відстеження помилок (порожніх полів)
    var isCapacityError     by remember { mutableStateOf(false) }
    var isPowerError        by remember { mutableStateOf(false) }
    var isTimeError         by remember { mutableStateOf(false) }
    // текст помилки
    var errorMessage        by remember { mutableStateOf("") }

    // результати
    var res_num             by remember { mutableFloatStateOf(0f) }
    var res_percentage      by remember { mutableIntStateOf(0) }


    // Анімація для зміни відсотків заряду
    val animatedBattery by animateIntAsState(
        targetValue = energyPercentage.toInt(),
        label = "BatteryAnimation"
    )


    // ЕЛЕМЕНТИ ІНТЕРФЕЙСУ
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {

        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState()) // Дозволяє прокручувати екран
                .imePadding()   // Автоматично зміщує вміст вище клавіатури
                .padding(20.dp), // Додає внутрішній відступ
            horizontalAlignment = Alignment.CenterHorizontally, // Вирівнювання по центру горизонтально
            verticalArrangement = Arrangement.Center
        ) {
            DisplayModeSelector(
                displayFor,
                { displayFor = it },
                fontSize = 20.sp
            )

            // визначаємо має заряд з часом...
            k = when (displayFor) {
                DisplayFor.STORE_ENERGY -> 1    // збільшуватись
                DisplayFor.USE_ENERGY -> -1     // чи зменшуватись
            }

            // Поле вводу для ємності
            NumericTextField(
                value = capacity,
                onValueChange = { capacity = it },
                label = "Ємність (кВт⋅год)",
                isError = isCapacityError
            )

            // Поле вводу для потужності
            NumericTextField(
                value = power,
                onValueChange = { power = it },
                label = "Потужність (кВт)",
                isError = isPowerError
            )

            // Поле вводу для часу
            NumericTextField(
                value = time_delta,
                onValueChange = { time_delta = it },
                label = "Час (год)",
                isError = isTimeError
            )

            Spacer(modifier = Modifier.height(20.dp))

            // Відображення поточного значення анімованого заряду
            Text(
                text = "Початковий заряд: $animatedBattery %",
                fontSize = 20.sp,
                color = MaterialTheme.colorScheme.onBackground
            )

            Spacer(modifier = Modifier.height(8.dp))

            // Слайдер для вибору заряду у відсотках (від 0 до 100)
            Slider(
                value = energyPercentage,
                onValueChange = { energyPercentage = it },
                valueRange = 0f..100f,
                steps = 99, // Кроки для цілих чисел від 0 до 100
                modifier = Modifier.fillMaxWidth()
            )

            // кнопка "Розрахувати"
            Button(
                onClick = {
                    // Перевіряємо кожне поле на заповненість
                    isCapacityError = capacity.toDoubleOrNull() == null
                    isPowerError = power.toDoubleOrNull() == null
                    isTimeError = time_delta.toDoubleOrNull() == null

                    if (isCapacityError || isPowerError || isTimeError) {
                        errorMessage = "Заповніть усі поля коректними числами"
                    } else {
                        errorMessage = ""
                        // Викликаємо функцію розрахунку
                        val (calculatedEnergy, calculatedPercentage) = calc_energy(
                            capacity            = capacity.toDouble(),
                            power               = power.toDouble(),
                            time_delta          = time_delta.toDouble(),
                            energyPercentage    = energyPercentage.toInt(),
                            k = k
                        )

                        res_num = calculatedEnergy.toFloat()
                        res_percentage = calculatedPercentage
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 4.dp),
            ) {
                Text(
                    text = "Розрахувати",
                    fontSize = 20.sp
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            // Вивід результату
            Text(
                text = "Розрахований заряд: $res_num кВт⋅год ($res_percentage%)",
                style = MaterialTheme.typography.bodyLarge,
                fontSize = 20.sp
            )

        }

    }
}


