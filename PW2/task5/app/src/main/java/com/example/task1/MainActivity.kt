package com.example.task1

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.core.animateIntAsState
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import com.example.task1.ui.theme.Task1Theme

private enum class AppScreen {
    MENU,
    INPUT,
    RESULT
}

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Task1Theme {
                App()
            }
        }
    }
}


/**
 * Кореневий Composable-компонент застосунку
 */
@Composable
fun App() {
    val context = LocalContext.current
    val configStorage = remember { ConfigStorage(context) }
    val initialConfig = remember { configStorage.loadConfig() }

    // поточний екран застосунку
    var currentScreen       by remember { mutableStateOf(AppScreen.MENU) }

    // вибір режиму / сценарію
    var scenario            by remember { mutableStateOf<Scenarios>(initialConfig.scenario) }
    // коефіцієнт, що = 1 для зарядки та -1 для розрядки
    var k                   by remember { mutableIntStateOf(1) }

    // змінні для інпутів
    var capacity            by remember { mutableStateOf(initialConfig.capacity) }          // кВт⋅год
    var energyPercentage    by remember { mutableFloatStateOf(initialConfig.energyPercentage) }   // %
    var time_delta          by remember { mutableStateOf(initialConfig.timeDelta) }          // год
    var power               by remember { mutableStateOf(initialConfig.power) }             // кВт

    // Прапорці для відстеження помилок (порожніх полів)
    var isCapacityError     by remember { mutableStateOf(false) }
    var isPowerError        by remember { mutableStateOf(false) }
    var isTimeError         by remember { mutableStateOf(false) }
    // текст помилки
    var errorMessage        by remember { mutableStateOf("") }

    // результати
    var res_num             by remember { mutableFloatStateOf(0f) }
    var res_percentage      by remember { mutableIntStateOf(initialConfig.energyPercentage.toInt()) }

    // Анімація для зміни відсотків заряду
    val animatedBattery by animateIntAsState(
        targetValue = res_percentage,
        label = "BatteryAnimation"
    )

    // визначаємо має заряд з часом збільшуватись чи зменшуватись
    k = when (scenario) {
        Scenarios.STORE_ENERGY -> 1
        Scenarios.USE_ENERGY -> -1
    }

    val saveCurrentConfig: () -> Unit = {
        configStorage.saveConfig(
            capacity = capacity,
            power = power,
            timeDelta = time_delta,
            energyPercentage = energyPercentage,
            scenario = scenario
        )
    }

    val performCalculation: () -> Unit = {
        isCapacityError = capacity.toDoubleOrNull() == null
        isPowerError = power.toDoubleOrNull() == null
        isTimeError = time_delta.toDoubleOrNull() == null

        if (isCapacityError || isPowerError || isTimeError) {
            errorMessage = "Заповніть усі поля коректними числами"
        } else {
            errorMessage = ""
            val (calculatedEnergy, calculatedPercentage) = calc_energy(
                capacity = capacity.toDouble(),
                power = power.toDouble(),
                time_delta = time_delta.toDouble(),
                energyPercentage = res_percentage,
                k = k
            )
            res_num = calculatedEnergy.toFloat()
            res_percentage = calculatedPercentage
            energyPercentage = calculatedPercentage.toFloat()
            saveCurrentConfig()
        }
    }

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        when (currentScreen) {
            AppScreen.MENU -> {
                MainMenuScreen(
                    onOpenInput = { currentScreen = AppScreen.INPUT },
                    onOpenResult = { currentScreen = AppScreen.RESULT }
                )
            }

            AppScreen.INPUT -> {
                ConfigScreen(
                    capacity = capacity,
                    onCapacityChange = {
                        capacity = it
                        saveCurrentConfig()
                    },
                    power = power,
                    onPowerChange = {
                        power = it
                        saveCurrentConfig()
                    },
                    timeDelta = time_delta,
                    onTimeDeltaChange = {
                        time_delta = it
                        saveCurrentConfig()
                    },
                    isCapacityError = isCapacityError,
                    isPowerError = isPowerError,
                    isTimeError = isTimeError,
                    onSaveConfig = saveCurrentConfig,
                    onOpenResult = { currentScreen = AppScreen.RESULT },
                    onBackToMenu = { currentScreen = AppScreen.MENU }
                )
            }

            AppScreen.RESULT -> {
                DisplayScreen(
                    scenario = scenario,
                    onScenarioChange = {
                        scenario = it
                        saveCurrentConfig()
                    },
                    energyPercentage = energyPercentage,
                    onEnergyPercentageChange = { newValue ->
                        energyPercentage = newValue
                        res_percentage = newValue.toInt()
                        saveCurrentConfig()
                    },
                    animatedBattery = animatedBattery,
                    resNum = res_num,
                    resPercentage = res_percentage,
                    errorMessage = errorMessage,
                    onCalculateClick = performCalculation,
                    onBackToMenu = { currentScreen = AppScreen.MENU }
                )
            }
        }
    }
}
