package com.example.pw1


import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.ui.Modifier
import com.example.pw1.ui.theme.PW1Theme
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.ui.Alignment
import androidx.compose.ui.unit.dp
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.material3.CircularProgressIndicator

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
    // Стан для визначення, чи знаходимося ми у режимі реєстрації
    var isRegisterMode by remember { mutableStateOf(false) }
    // Стан для показу індикатора завантаження
    var isLoading by remember { mutableStateOf(false) }
    // для прокрутки форми
    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .imePadding() // Піднімає контент над клавіатурою
            .verticalScroll(scrollState) // Дозволяє прокрутити форму, коли клавіатура відкрита
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        // Якщо у режимі реєстрації, відображаємо форму реєстрації
        if (isRegisterMode) {
            RegistrationForm(
                onRegister = { name, email, password, confirmPassword ->
                    isLoading = true
                    // Імітація обробки (замість реального API)
                    isLoading = false
                    when {
                        name.isBlank() || email.isBlank() || password.isBlank() || confirmPassword.isBlank() ->
                            "Заповніть усі поля"
                        password != confirmPassword -> "Паролі не співпадають"
                        else                        -> "Реєстрація успішна!"
                    }
                },
                onSwitchToLogin = { isRegisterMode = false }
            )
        } else {
            // Якщо у режимі входу, відображаємо форму авторизації
            LoginForm(
                onLogin = { login, password ->
                    isLoading = true
                    // Імітація обробки
                    isLoading = false
                    if (login.isBlank() || password.isBlank()) "Заповніть усі поля" else "Авторизація успішна!"
                },
                onSwitchToRegister = { isRegisterMode = true }
            )
        }
        // Якщо виконується завантаження, показуємо індикатор
        if (isLoading) {
            Spacer(modifier = Modifier.height(16.dp))
            CircularProgressIndicator()
        }
    }
}

