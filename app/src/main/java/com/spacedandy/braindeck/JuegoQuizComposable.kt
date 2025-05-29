package com.spacedandy.braindeck

import android.net.Uri
import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest

@Composable
fun JuegoQuiz(cartas: List<Carta>, onSalir: () -> Unit) {
    var indiceActual by remember { mutableStateOf(0) }
    var mostrarResultado by remember { mutableStateOf(false) }
    var respuestaSeleccionada by remember { mutableStateOf<String?>(null) }
    var totalCorrectas by remember { mutableStateOf(0) }
    var juegoFinalizado by remember { mutableStateOf(false) }
    var respuestaYaProcesada by remember { mutableStateOf(false) }

    if (juegoFinalizado) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(32.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = "🎉 Has completado el mazo 🎉",
                style = MaterialTheme.typography.headlineSmall,
                modifier = Modifier.padding(bottom = 12.dp)
            )
            Text(
                text = "Respondiste correctamente $totalCorrectas de ${cartas.size} preguntas.",
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier.padding(bottom = 32.dp)
            )

            Button(
                onClick = {
                    indiceActual = 0
                    totalCorrectas = 0
                    juegoFinalizado = false
                    respuestaYaProcesada = false
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp)
            ) {
                Text("🔄 Intentar de nuevo")
            }

            OutlinedButton(
                onClick = { onSalir() },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("⬅️ Regresar")
            }
        }
        return
    }

    val carta = cartas[indiceActual]
    val respuestasBarajadas = remember(indiceActual) {
        carta.respuestas?.shuffled() ?: emptyList()
    }

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
            carta.imagenUri?.let { uriStr ->
                val context = LocalContext.current
                val uri = Uri.parse(uriStr)
                Log.d("JuegoQuiz", "Mostrando imagen URI: $uriStr")

                AsyncImage(
                    model = ImageRequest.Builder(context)
                        .data(uri)
                        .crossfade(true)
                        .build(),
                    contentDescription = "Imagen de la carta",
                    contentScale = ContentScale.Fit,
                    placeholder = painterResource(id = R.drawable.placeholder),
                    error = painterResource(id = R.drawable.error_image),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp)
                        .padding(bottom = 8.dp)
                )
            }

            Text(
                text = carta.pregunta,
                style = MaterialTheme.typography.headlineSmall,
                modifier = Modifier.padding(bottom = 16.dp)
            )

            if (respuestasBarajadas.isEmpty()) {
                Text("Esta carta no tiene respuestas.", modifier = Modifier.padding(16.dp))
            } else {
                respuestasBarajadas.forEach { respuesta ->
                    Button(
                        onClick = {
                            if (!mostrarResultado) {
                                respuestaSeleccionada = respuesta
                                mostrarResultado = true
                                respuestaYaProcesada = false
                            }
                        },
                        modifier = Modifier.fillMaxWidth(),
                        enabled = !mostrarResultado
                    ) {
                        Text(respuesta)
                    }
                }

                if (mostrarResultado && respuestaSeleccionada != null) {
                    val esCorrecta = respuestaSeleccionada == carta.respuestas?.firstOrNull()
                    if (esCorrecta && !respuestaYaProcesada) {
                        totalCorrectas++
                        respuestaYaProcesada = true
                    }

                    Text(
                        text = if (esCorrecta) "✅ ¡Correcto!" else "❌ Incorrecto. La correcta era: ${carta.respuestas?.firstOrNull() ?: "No disponible"}",
                        style = MaterialTheme.typography.bodyLarge,
                        modifier = Modifier.padding(top = 16.dp)
                    )

                    Button(
                        onClick = {
                            respuestaSeleccionada = null
                            mostrarResultado = false
                            respuestaYaProcesada = false
                            if (indiceActual < cartas.lastIndex) {
                                indiceActual++
                            } else {
                                juegoFinalizado = true
                            }
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



