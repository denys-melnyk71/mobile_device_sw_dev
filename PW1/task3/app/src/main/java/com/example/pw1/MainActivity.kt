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
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            PW1Theme {
                App()
            }
        }
    }
}

@Composable
fun App() {

    // вибір режиму
    var scenario        by remember { mutableStateOf(Scenarios.STORE_ENERGY) }
    var BatteryStorage  by remember { mutableStateOf(100) }
    val animatedBattery by animateIntAsState(
        targetValue = BatteryStorage, // Цільове значення для анімації
        label = "BatteryAnimation" // Мітка для відлагодження
    )
    val onCalculateClick = {
        when (scenario) {
            Scenarios.USE_ENERGY -> {
                BatteryStorage = maxOf(
                    BatteryStorage - Random.nextInt(1, 10),
                    0
                )
            }
            Scenarios.STORE_ENERGY -> {
                BatteryStorage = minOf(
                    BatteryStorage + Random.nextInt(1, 10),
                    100
                )
            }
        }
    }

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {

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
                    ControlsSection(
                        scenario = scenario,
                        onScenarioChange = { scenario = it },
                        onCalculateClick = onCalculateClick,
                        isWideScreen = true,
                        modifier = Modifier.weight(1f)
                    )

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
                        onScenarioChange = { scenario = it },
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
                }
            }
        }
    }

}

