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
                    JuegoQuiz(cartas = cartas)
                } else {
                    Text(
                        "No hay cartas disponibles para el mazo seleccionado",
                        modifier = Modifier.padding(16.dp)
                    )
                }
            }
        }
    }
}
