package com.example.pw1

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun InfoCard(BatteryStorage: Int, scenario: Scenarios, comments: String? = null) {
    Card(
        shape = RoundedCornerShape(12.dp),
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor =
            Color(0xFFE3F2FD))
    ) {
        val mode = when (scenario) {
            Scenarios.STORE_ENERGY -> "Накопичення"
            Scenarios.USE_ENERGY -> "Використання"
        }
        Column(modifier = Modifier.padding(16.dp)) {
            Text("Режим: $mode", fontSize = 22.sp)
            Text("Прогнозований заряд: $BatteryStorage %",
                fontSize = 22.sp, fontWeight = FontWeight.Bold)
            if (comments != null) Text("Коментар: $comments", fontSize = 16.sp)
        }
    }
}