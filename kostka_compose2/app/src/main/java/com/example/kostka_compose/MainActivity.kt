package com.example.kostka_compose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
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
    val diceSymbols = listOf("⚀", "⚁", "⚂", "⚃", "⚄", "⚅")

    val backgroundColor = Color(0xFFE8F5E9)
    val primaryColor = Color(0xFF1B5E20)

    var firstDiceValue by remember { mutableStateOf(1) }
    var secondDiceValue by remember { mutableStateOf(1) }
    var isRolling by remember { mutableStateOf(false) }

    val coroutineScope = rememberCoroutineScope()

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
            text = "Hoď kostkami",
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold,
            color = primaryColor
        )

        Row(
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(vertical = 16.dp)
        ) {
            Text(
                text = diceSymbols[firstDiceValue - 1],
                fontSize = 100.sp,
                color = primaryColor
            )
            Text(
                text = diceSymbols[secondDiceValue - 1],
                fontSize = 100.sp,
                color = primaryColor
            )
        }

        Text(
            text = "Součet: ${firstDiceValue + secondDiceValue}",
            fontSize = 24.sp,
            fontWeight = FontWeight.SemiBold,
            color = primaryColor,
            modifier = Modifier.padding(bottom = 24.dp)
        )

        Button(
            enabled = !isRolling,
            colors = ButtonDefaults.buttonColors(
                containerColor = primaryColor,
                contentColor = Color.White
            ),
            onClick = {
                isRolling = true

                coroutineScope.launch {
                    repeat(10) {
                        firstDiceValue = (1..6).random()
                        secondDiceValue = (1..6).random()
                        delay(100)
                    }
                    firstDiceValue = (1..6).random()
                    secondDiceValue = (1..6).random()
                    isRolling = false
                }
            }
        ) {
            Text(
                text = if (isRolling) "Házím…" else "Hodit",
                fontSize = 24.sp
            )
        }
    }
}