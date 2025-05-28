package com.spacedandy.braindeck

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.spacedandy.braindeck.ui.theme.BraindeckTheme
import android.content.Intent

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            BraindeckTheme {
                val context = this@MainActivity
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    MainMenuScreen(
                        modifier = Modifier
                            .padding(innerPadding)
                            .fillMaxSize(),
                        onCrearCarta = {
                            startActivity(Intent(context, crearCartaActivity::class.java))
                        },
                        onVerMazos = {
                            startActivity(Intent(context, VerMazosActivity::class.java))
                        },
                        onJugar = {
                            startActivity(Intent(context, JugarActivity::class.java))
                        },
                        onExit = { finish() }
                    )
                }
            }
        }
    }
}

@Composable
fun MainMenuScreen(
    modifier: Modifier = Modifier,
    onCrearCarta: () -> Unit = {},
    onVerMazos: () -> Unit = {},
    onJugar: () -> Unit = {},
    onExit: () -> Unit = {}
) {
    val context = LocalContext.current

    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Button(
            onClick = { onCrearCarta() },
            modifier = Modifier.fillMaxWidth(0.7f).padding(8.dp)
        ) {
            Text("Crear carta")
        }
        Button(
            onClick = { onVerMazos() },
            modifier = Modifier.fillMaxWidth(0.7f).padding(8.dp)
        ) {
            Text("Ver mazos")
        }
        Button(
            onClick = { onJugar() },
            modifier = Modifier.fillMaxWidth(0.7f).padding(8.dp)
        ) {
            Text("Jugar")
        }
        Button(
            onClick = { onExit() },
            modifier = Modifier.fillMaxWidth(0.7f).padding(8.dp)
        ) {
            Text("Salir")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun MainMenuScreenPreview() {
    BraindeckTheme {
        MainMenuScreen()
    }
}
