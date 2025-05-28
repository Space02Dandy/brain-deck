package com.spacedandy.braindeck

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.spacedandy.braindeck.ui.theme.BraindeckTheme

class SeleccionarMazoActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        CartaManager.cargar(this) // cargar mazos

        setContent {
            BraindeckTheme {
                val mazos = remember { CartaManager.obtenerNombresDeMazos() }
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(24.dp)
                ) {
                    Text("Selecciona un mazo", style = MaterialTheme.typography.headlineMedium)

                    Spacer(modifier = Modifier.height(16.dp))

                    mazos.forEach { mazo ->
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 8.dp)
                                .clickable {
                                    // Al hacer click, abrir JugarActivity con el mazo seleccionado
                                    val intent = Intent(this@SeleccionarMazoActivity, JugarActivity::class.java)
                                    intent.putExtra("nombreMazo", mazo)
                                    startActivity(intent)
                                },
                            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
                        ) {
                            Text(
                                text = mazo,
                                modifier = Modifier.padding(16.dp),
                                style = MaterialTheme.typography.bodyLarge
                            )
                        }
                    }
                }
            }
        }
    }
}
