package com.example.trabalhocalculadora

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CalculadoraScreen()
        }
    }
}

@Composable
fun CalculadoraScreen() {
    var display by remember { mutableStateOf("0") }
    var operando1 by remember { mutableStateOf("") }
    var operador by remember { mutableStateOf("") }
    var novaOperacao by remember { mutableStateOf(true) }


    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF424242))
    ) {
        Box(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
                .background(Color.Black)
                .padding(24.dp),
            contentAlignment = Alignment.BottomEnd
        ) {
            Text(
                text = display,
                color = Color.White,
                fontSize = 64.sp
            )
        }

        Column(
            modifier = Modifier
                .weight(2f)
                .fillMaxWidth()
                .padding(8.dp)
        ) {
            val botoes = listOf(
                listOf("7", "8", "9", "/"),
                listOf("4", "5", "6", "*"),
                listOf("1", "2", "3", "-"),
                listOf("0", "C", "=", "+")
            )

            for (linha in botoes) {
                Row(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    for (textoBotao in linha) {
                        Button(
                            onClick = {
                                when (textoBotao) {
                                    "C" -> {
                                        display = "0"
                                        operando1 = ""
                                        operador = ""
                                        novaOperacao = true
                                    }
                                    "+", "-", "*", "/" -> {
                                        operando1 = display
                                        operador = textoBotao
                                        novaOperacao = true
                                    }
                                    "=" -> {
                                        if (operando1.isNotEmpty() && operador.isNotEmpty()) {
                                            val num1 = operando1.toDoubleOrNull() ?: 0.0
                                            val num2 = display.toDoubleOrNull() ?: 0.0

                                            val resultado = when (operador) {
                                                "+" -> num1 + num2
                                                "-" -> num1 - num2
                                                "*" -> num1 * num2
                                                "/" -> if (num2 != 0.0) num1 / num2 else 0.0
                                                else -> 0.0
                                            }


                                            display = if (resultado % 1 == 0.0) {
                                                resultado.toInt().toString()
                                            } else {
                                                resultado.toString()
                                            }

                                            novaOperacao = true
                                            operando1 = ""
                                            operador = ""
                                        }
                                    }
                                    else -> {
                                        if (novaOperacao || display == "0") {
                                            display = textoBotao
                                            novaOperacao = false
                                        } else {
                                            display += textoBotao
                                        }
                                    }
                                }
                            },
                            modifier = Modifier
                                .weight(1f)
                                .fillMaxHeight()
                                .padding(vertical = 4.dp),
                            shape = RoundedCornerShape(50),
                            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF4C6296))
                        ) {
                            Text(text = textoBotao, fontSize = 32.sp, color = Color.White)
                        }
                    }
                }
            }
        }
    }
}

// --- PRÉ-VISUALIZAÇÃO (Preview) ---
@Preview(showBackground = true)
@Composable
fun CalculadoraPreview() {
    CalculadoraScreen()
}