package com.example.dicerollercompose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MaterialTheme {
                DiceApp()
            }
        }
    }
}

@Composable
fun DiceApp() {
    // Definice symbolů kostek a barevného schématu
    val diceSymbols = listOf("⚀", "⚁", "⚂", "⚃", "⚄", "⚅")
    val backgroundColor = Color(0xFFF5F3FF)
    val primaryColor = Color(0xFF352060)

    // Stavové proměnné (udržují aktuální hodnotu a stav animace)
    var diceValue by remember { mutableStateOf(1) }
    var isRolling by remember { mutableStateOf(false) }

    // Coroutine Scope pro asynchronní operace v Compose
    val coroutineScope = rememberCoroutineScope()

    // Hlavní rozvržení s vycentrováním a ochranou proti překrytí lištami
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(backgroundColor)
            .safeDrawingPadding()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Hoď kostkou",
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold,
            color = primaryColor
        )

        // Zobrazení symbolu kostky podle aktuálního stavu diceValue
        Text(
            text = diceSymbols[diceValue - 1],
            fontSize = 120.sp,
            color = primaryColor,
            modifier = Modifier.padding(vertical = 24.dp)
        )

        Button(
            enabled = !isRolling, // Tlačítko je neaktivní, pokud probíhá hovoření
            colors = ButtonDefaults.buttonColors(
                containerColor = primaryColor,
                contentColor = Color.White
            ),
            onClick = {
                isRolling = true

                coroutineScope.launch {
                    // 10 náhodných změn stavu s pauzou 250 ms
                    repeat(10) {
                        diceValue = (1..6).random()
                        delay(250)
                    }
                    // Výsledný hod
                    diceValue = (1..6).random()
                    isRolling = false
                }
            }
        ) {
            Text(
                text = "Hodit",
                fontSize = 24.sp
            )
        }
    }
}