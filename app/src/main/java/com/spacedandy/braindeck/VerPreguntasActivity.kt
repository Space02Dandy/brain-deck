package com.spacedandy.braindeck

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.spacedandy.braindeck.ui.theme.BraindeckTheme

class VerPreguntasActivity : ComponentActivity() {
    private lateinit var mazo: String
    private var cartas: MutableList<Carta> = mutableListOf()

    companion object {
        private const val REQUEST_EDITAR_CARTA = 1001
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mazo = intent.getStringExtra("mazo_nombre") ?: ""
        CartaManager.cargar(this)
        cartas = CartaManager.obtenerCartasDeMazo(mazo).toMutableList()

        setContent {
            BraindeckTheme {
                var cartasState by remember { mutableStateOf(cartas.toList()) }
                var cartaAEliminar by remember { mutableStateOf<Pair<Carta, Int>?>(null) }
                var mostrarOpciones by remember { mutableStateOf<Pair<Carta, Int>?>(null) }

                LaunchedEffect(cartas.size) {
                    cartasState = cartas.toList()
                }

                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(
                            brush = Brush.verticalGradient(
                                colors = listOf(
                                    Color(0xFFF8F9FA),
                                    Color(0xFFE3F2FD),
                                    Color(0xFFBBDEFB)
                                )
                            )
                        )
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(24.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        // Encabezado
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .shadow(16.dp, RoundedCornerShape(24.dp)),
                            shape = RoundedCornerShape(24.dp),
                            colors = CardDefaults.cardColors(containerColor = Color.Transparent)
                        ) {
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .background(
                                        brush = Brush.linearGradient(
                                            colors = listOf(
                                                Color(0xFF1976D2),
                                                Color(0xFF2196F3),
                                                Color(0xFF42A5F5)
                                            )
                                        )
                                    )
                                    .padding(28.dp),
                                contentAlignment = Alignment.Center
                            ) {
                                Column(
                                    horizontalAlignment = Alignment.CenterHorizontally
                                ) {
                                    Text(
                                        text = "📋",
                                        fontSize = 32.sp
                                    )
                                    Spacer(modifier = Modifier.height(8.dp))
                                    Text(
                                        text = "PREGUNTAS DE MAZO",
                                        fontSize = 20.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = Color.White,
                                        textAlign = TextAlign.Center
                                    )
                                    Text(
                                        text = mazo,
                                        fontSize = 16.sp,
                                        color = Color.White.copy(alpha = 0.9f),
                                        textAlign = TextAlign.Center,
                                        maxLines = 1,
                                        overflow = TextOverflow.Ellipsis
                                    )
                                }
                            }
                        }

                        Spacer(modifier = Modifier.height(24.dp))

                        // Lista de preguntas
                        if (cartasState.isNotEmpty()) {
                            LazyColumn(
                                verticalArrangement = Arrangement.spacedBy(12.dp),
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                itemsIndexed(cartasState) { index, carta ->
                                    PreguntaCard(
                                        carta = carta,
                                        index = index,
                                        onClick = {
                                            mostrarOpciones = Pair(carta, index)
                                        }
                                    )
                                }
                            }
                        } else {
                            // Estado vacío
                            Spacer(modifier = Modifier.height(60.dp))
                            Card(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .shadow(12.dp, RoundedCornerShape(20.dp)),
                                shape = RoundedCornerShape(20.dp),
                                colors = CardDefaults.cardColors(containerColor = Color(0xFFFFF3E0))
                            ) {
                                Column(
                                    modifier = Modifier.padding(32.dp),
                                    horizontalAlignment = Alignment.CenterHorizontally
                                ) {
                                    Text(text = "📝", fontSize = 64.sp)
                                    Spacer(modifier = Modifier.height(16.dp))
                                    Text(
                                        text = "¡No hay preguntas en este mazo!",
                                        fontSize = 18.sp,
                                        fontWeight = FontWeight.Medium,
                                        color = Color(0xFF1976D2),
                                        textAlign = TextAlign.Center
                                    )
                                    Text(
                                        text = "Agrega algunas preguntas para comenzar",
                                        fontSize = 14.sp,
                                        color = Color(0xFF666666),
                                        textAlign = TextAlign.Center
                                    )
                                }
                            }
                        }
                    }

                    // Diálogo de opciones
                    mostrarOpciones?.let { (carta, posicion) ->
                        AlertDialog(
                            onDismissRequest = { mostrarOpciones = null },
                            title = {
                                Text(
                                    "Opciones",
                                    fontWeight = FontWeight.Bold,
                                    color = Color(0xFF1976D2)
                                )
                            },
                            text = {
                                Text(
                                    "¿Qué deseas hacer con esta pregunta?",
                                    color = Color(0xFF666666)
                                )
                            },
                            confirmButton = {
                                Row(
                                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                                ) {
                                    TextButton(
                                        onClick = {
                                            editarCarta(carta, posicion)
                                            mostrarOpciones = null
                                        },
                                        colors = ButtonDefaults.textButtonColors(
                                            contentColor = Color(0xFF1976D2)
                                        )
                                    ) {
                                        Text("✏️ Editar")
                                    }
                                    TextButton(
                                        onClick = {
                                            cartaAEliminar = Pair(carta, posicion)
                                            mostrarOpciones = null
                                        },
                                        colors = ButtonDefaults.textButtonColors(
                                            contentColor = Color(0xFFD32F2F)
                                        )
                                    ) {
                                        Text("🗑️ Eliminar")
                                    }
                                }
                            },
                            dismissButton = {
                                TextButton(
                                    onClick = { mostrarOpciones = null }
                                ) {
                                    Text("Cancelar")
                                }
                            }
                        )
                    }

                    // Diálogo de confirmación de eliminación
                    cartaAEliminar?.let { (carta, posicion) ->
                        AlertDialog(
                            onDismissRequest = { cartaAEliminar = null },
                            title = {
                                Text(
                                    "🗑️ Eliminar Pregunta",
                                    fontWeight = FontWeight.Bold,
                                    color = Color(0xFFD32F2F)
                                )
                            },
                            text = {
                                Column {
                                    Text(
                                        "¿Estás seguro de que deseas eliminar esta pregunta?",
                                        color = Color(0xFF666666)
                                    )
                                    Spacer(modifier = Modifier.height(8.dp))
                                    Card(
                                        colors = CardDefaults.cardColors(
                                            containerColor = Color(0xFFFFF3E0)
                                        ),
                                        modifier = Modifier.fillMaxWidth()
                                    ) {
                                        Text(
                                            text = carta.pregunta,
                                            modifier = Modifier.padding(12.dp),
                                            fontSize = 14.sp,
                                            fontStyle = androidx.compose.ui.text.font.FontStyle.Italic,
                                            maxLines = 2,
                                            overflow = TextOverflow.Ellipsis
                                        )
                                    }
                                }
                            },
                            confirmButton = {
                                TextButton(
                                    onClick = {
                                        eliminarCarta(posicion)
                                        cartaAEliminar = null
                                        cartasState = cartas.toList()
                                    },
                                    colors = ButtonDefaults.textButtonColors(
                                        contentColor = Color(0xFFD32F2F)
                                    )
                                ) {
                                    Text("Eliminar")
                                }
                            },
                            dismissButton = {
                                TextButton(
                                    onClick = { cartaAEliminar = null }
                                ) {
                                    Text("Cancelar")
                                }
                            }
                        )
                    }
                }
            }
        }
    }

    private fun editarCarta(carta: Carta, posicion: Int) {
        val intent = Intent(this, EditarCartaActivity::class.java).apply {
            putExtra("pregunta", carta.pregunta)
            putStringArrayListExtra("respuestas", ArrayList(carta.respuestas))
            putExtra("posicion", posicion)
            putExtra("mazo_nombre", mazo)
            carta.imagenUri?.let { uri ->
                putExtra("imagenUriString", uri)
            }
        }
        startActivityForResult(intent, REQUEST_EDITAR_CARTA)
    }

    private fun eliminarCarta(posicion: Int) {
        cartas.removeAt(posicion)
        CartaManager.reemplazarCartas(mazo, cartas)
        CartaManager.guardar(this)
        Toast.makeText(this, "✅ Carta eliminada", Toast.LENGTH_SHORT).show()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_EDITAR_CARTA && resultCode == RESULT_OK && data != null) {
            val nuevaPregunta = data.getStringExtra("nuevaPregunta") ?: return
            val nuevasRespuestas = data.getStringArrayListExtra("nuevasRespuestas") ?: return
            val posicion = data.getIntExtra("posicion", -1)

            if (posicion in cartas.indices) {
                val imagenUriString = data.getStringExtra("imagenUriString")
                val nuevaCarta = Carta(nuevaPregunta, nuevasRespuestas, imagenUriString)
                cartas[posicion] = nuevaCarta
                CartaManager.reemplazarCartas(mazo, cartas)
                CartaManager.guardar(this)
                // Refresh will happen automatically through LaunchedEffect
                Toast.makeText(this, "✅ Carta actualizada", Toast.LENGTH_SHORT).show()
            }
        }
    }
}

@Composable
fun PreguntaCard(
    carta: Carta,
    index: Int,
    onClick: () -> Unit
) {
    var isPressed by remember { mutableStateOf(false) }
    val scale by animateFloatAsState(
        targetValue = if (isPressed) 0.97f else 1f,
        animationSpec = tween(150)
    )

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .scale(scale)
            .shadow(8.dp, RoundedCornerShape(18.dp))
            .pointerInput(Unit) {
                detectTapGestures(
                    onPress = {
                        isPressed = true
                        tryAwaitRelease()
                        isPressed = false
                    },
                    onTap = { onClick() }
                )
            },
        shape = RoundedCornerShape(18.dp),
        colors = CardDefaults.cardColors(containerColor = Color.Transparent)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    brush = Brush.horizontalGradient(
                        colors = listOf(
                            Color.White,
                            Color(0xFFF8F9FA),
                            Color(0xFFE8F5E8)
                        )
                    )
                )
                .padding(20.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                // Número de pregunta
                Box(
                    modifier = Modifier
                        .size(48.dp)
                        .clip(RoundedCornerShape(14.dp))
                        .background(
                            brush = Brush.linearGradient(
                                colors = listOf(
                                    Color(0xFF4CAF50),
                                    Color(0xFF66BB6A)
                                )
                            )
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "${index + 1}",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                }

                // Contenido de la pregunta
                Column(
                    modifier = Modifier.weight(1f)
                ) {
                    Text(
                        text = carta.pregunta,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Medium,
                        color = Color(0xFF2E2E2E),
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = "${carta.respuestas.size} respuesta${if (carta.respuestas.size != 1) "s" else ""}",
                        fontSize = 12.sp,
                        color = Color(0xFF666666)
                    )
                    if (carta.imagenUri != null) {
                        Text(
                            text = "📷 Con imagen",
                            fontSize = 11.sp,
                            color = Color(0xFF1976D2)
                        )
                    }
                }

                // Indicador de acción
                Box(
                    modifier = Modifier
                        .size(36.dp)
                        .clip(RoundedCornerShape(10.dp))
                        .background(
                            brush = Brush.linearGradient(
                                colors = listOf(
                                    Color(0xFF2196F3),
                                    Color(0xFF42A5F5)
                                )
                            )
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    Text(text = "⚙️", fontSize = 14.sp)
                }
            }
        }
    }
}