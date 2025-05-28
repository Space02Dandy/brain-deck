package com.spacedandy.braindeck

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.spacedandy.braindeck.ui.theme.BraindeckTheme

class JugarActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        CartaManager.cargar(this) // Cargar datos de cartas y mazos

        // Obtener el nombre del mazo desde el Intent
        val nombreMazo = intent.getStringExtra("nombreMazo")

        // Obtener solo las cartas del mazo seleccionado
        val cartas = if (nombreMazo != null) {
            CartaManager.obtenerCartasDeMazo(nombreMazo)
        } else {
            emptyList()
        }

        setContent {
            BraindeckTheme {
                if (cartas.isNotEmpty()) {
                    JuegoQuizConTerminar(cartas = cartas, onTerminar = { finish() })
                } else {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            "No hay cartas disponibles para el mazo seleccionado",
                            modifier = Modifier.padding(16.dp)
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun JuegoQuizConTerminar(
    cartas: List<Carta>,
    onTerminar: () -> Unit
) {
    var indiceActual by remember { mutableStateOf(0) }
    var mostrarResultado by remember { mutableStateOf(false) }
    var respuestaSeleccionada by remember { mutableStateOf<String?>(null) }

    val carta = cartas[indiceActual]

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = carta.pregunta, style = androidx.compose.material3.MaterialTheme.typography.headlineSmall)

            Spacer(modifier = Modifier.height(16.dp))

            carta.respuestas.shuffled().forEach { respuesta ->
                Button(
                    onClick = {
                        respuestaSeleccionada = respuesta
                        mostrarResultado = true
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp)
                ) {
                    Text(respuesta)
                }
            }

            if (mostrarResultado && respuestaSeleccionada != null) {
                val esCorrecta = respuestaSeleccionada == carta.respuestas.first()
                Text(
                    text = if (esCorrecta) "¡Correcto!" else "Incorrecto. La correcta era: ${carta.respuestas.first()}",
                    modifier = Modifier.padding(top = 16.dp)
                )

                Button(
                    onClick = {
                        respuestaSeleccionada = null
                        mostrarResultado = false
                        indiceActual = (indiceActual + 1) % cartas.size
                    },
                    modifier = Modifier.padding(top = 16.dp)
                ) {
                    Text("Siguiente")
                }
            }
        }

        Button(
            onClick = onTerminar,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp)
        ) {
            Text("Terminar")
        }
    }
}

