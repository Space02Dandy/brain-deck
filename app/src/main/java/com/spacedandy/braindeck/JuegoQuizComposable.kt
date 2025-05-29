package com.spacedandy.braindeck

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun JuegoQuiz(cartas: List<Carta>) {
    var indiceActual by remember { mutableStateOf(0) }
    var mostrarResultado by remember { mutableStateOf(false) }
    var respuestaSeleccionada by remember { mutableStateOf<String?>(null) }

    val carta = cartas[indiceActual]
    val respuestas = carta.respuestas ?: emptyList()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(12.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = carta.pregunta,
                style = MaterialTheme.typography.headlineSmall,
                modifier = Modifier.padding(bottom = 16.dp)
            )

            if (respuestas.isEmpty()) {
                Text("Esta carta no tiene respuestas.", modifier = Modifier.padding(16.dp))
            } else {
                respuestas.shuffled().forEach { respuesta ->
                    Button(
                        onClick = {
                            respuestaSeleccionada = respuesta
                            mostrarResultado = true
                        },
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(respuesta)
                    }
                }

                if (mostrarResultado && respuestaSeleccionada != null) {
                    val esCorrecta = respuestaSeleccionada == carta.respuestas.firstOrNull()
                    Text(
                        text = if (esCorrecta) "¡Correcto!" else "Incorrecto. La correcta era: ${carta.respuestas.firstOrNull() ?: "No disponible"}",
                        modifier = Modifier.padding(top = 16.dp)
                    )

                    Button(
                        onClick = {
                            respuestaSeleccionada = null
                            mostrarResultado = false
                            indiceActual = (indiceActual + 1) % cartas.size
                        },
                        modifier = Modifier.padding(top = 12.dp)
                    ) {
                        Text("Siguiente")
                    }
                }
            }
        }
    }
}
