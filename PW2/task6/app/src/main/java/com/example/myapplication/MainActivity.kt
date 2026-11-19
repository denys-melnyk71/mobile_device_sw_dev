package com.example.myapplication

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.myapplication.ui.theme.MyApplicationTheme
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

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
    // Стан полів форми
    var stationName by remember { mutableStateOf(TextFieldValue("")) }
    var operatorEmail by remember { mutableStateOf(TextFieldValue("")) }
    var password by remember { mutableStateOf(TextFieldValue("")) }
    var systemCapacity by remember { mutableStateOf(TextFieldValue("")) }

    // Стан помилок валідації полів
    var stationNameError by remember { mutableStateOf(false) }
    var emailError by remember { mutableStateOf(false) }
    var passwordError by remember { mutableStateOf(false) }
    var capacityError by remember { mutableStateOf(false) }

    // Стан подання форми та асинхронного завантаження
    var isSubmitted by remember { mutableStateOf(false) }
    var isLoading by remember { mutableStateOf(false) }

    val scope = rememberCoroutineScope()
    val scrollState = rememberScrollState()

    Column(modifier = Modifier
        .fillMaxSize()
        .safeDrawingPadding()
        .imePadding()
        .padding(16.dp)
        .verticalScroll(scrollState),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        FormHeader()

        Spacer(modifier = Modifier.height(16.dp))

        SystemNameField(
            value = stationName,
            isError = stationNameError,
            onValueChange = {
                stationName = it
                stationNameError = false
            }
        )

        Spacer(modifier = Modifier.height(8.dp))

        EmailField(
            value = operatorEmail,
            isError = emailError,
            onValueChange = {
                operatorEmail = it
                emailError = false
            }
        )

        Spacer(modifier = Modifier.height(8.dp))

        PasswordField(
            value = password,
            isError = passwordError,
            onValueChange = {
                password = it
                passwordError = false
            }
        )

        Spacer(modifier = Modifier.height(8.dp))

        CapacityField(
            value = systemCapacity,
            isError = capacityError,
            onValueChange = {
                systemCapacity = it
                capacityError = false
            }
        )

        Spacer(modifier = Modifier.height(16.dp))

        SubmitButton(
            onSubmit = {
                isSubmitted = false
                var valid = true

                // Перевірка назви станції
                if (stationName.text.isBlank()) {
                    stationNameError = true
                    valid = false
                }

                // Перевірка email оператора
                if (!operatorEmail.text.contains("@") || !operatorEmail.text.contains(".")) {
                    emailError = true
                    valid = false
                }

                // Перевірка пароля: мінімум 8 символів, велика літера та цифра
                if (
                    password.text.length < 8 ||
                    !password.text.any { it.isUpperCase() } ||
                    !password.text.any { it.isDigit() }
                ) {
                    passwordError = true
                    valid = false
                }

                // Перевірка ємності: додатне ціле число
                val capacityValue = systemCapacity.text.toIntOrNull()
                if (capacityValue == null || capacityValue <= 0) {
                    capacityError = true
                    valid = false
                }

                // Запуск імітації перевірки та реєстрації
                if (valid) {
                    isLoading = true
                    scope.launch {
                        delay(2000)
                        isLoading = false
                        isSubmitted = true
                    }
                }
            }
        )

        if (isLoading) {
            Spacer(modifier = Modifier.height(16.dp))
            LoadingIndicator()
        }

        if (isSubmitted) {
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = "Станція успішно зареєстрована та перевірена!",
                color = MaterialTheme.colorScheme.primary,
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}