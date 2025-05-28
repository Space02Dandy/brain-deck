package com.spacedandy.braindeck

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.Text
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.layout.padding
import com.spacedandy.braindeck.ui.theme.BraindeckTheme

class JugarActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        CartaManager.cargar(this) // Cargar datos de cartas y mazos

        val nombresDeMazos = CartaManager.obtenerNombresDeMazos()
        // Obtiene todas las cartas de todos los mazos
        val cartas = nombresDeMazos.flatMap { mazo -> CartaManager.obtenerCartasDeMazo(mazo) }

        setContent {
            BraindeckTheme {
                if (cartas.isNotEmpty()) {
                    JuegoQuiz(cartas = cartas)
                } else {
                    Text("No hay cartas disponibles", modifier = Modifier.padding(16.dp))
                }
            }
        }
    }
}
