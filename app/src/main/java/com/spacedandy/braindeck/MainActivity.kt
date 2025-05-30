package com.spacedandy.braindeck

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.spacedandy.braindeck.ui.theme.BraindeckTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            BraindeckTheme {
                val context = this@MainActivity
                MainMenuScreen(
                    onCrearCarta = {
                        startActivity(Intent(context, crearCartaActivity::class.java))
                    },
                    onVerMazos = {
                        startActivity(Intent(context, VerMazosActivity::class.java))
                    },
                    onJugar = {
                        startActivity(Intent(context, SeleccionarMazoActivity::class.java))
                    },
                    onExit = { finish() }
                )
            }
        }
    }
}

@Composable
fun MainMenuScreen(
    onCrearCarta: () -> Unit = {},
    onVerMazos: () -> Unit = {},
    onJugar: () -> Unit = {},
    onExit: () -> Unit = {}
) {
    val gradientBackground = Brush.verticalGradient(
        colors = listOf(Color(0xFF1E88E5), Color(0xFFBBDEFB), Color.White)
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(gradientBackground)
            .padding(24.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 32.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {
            Text(
                text = "MENÚ PRINCIPAL",
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White,
                modifier = Modifier
                    .padding(16.dp)
                    .background(
                        color = Color(0xFFE53935),
                        shape = RoundedCornerShape(16.dp)
                    )
                    .padding(horizontal = 24.dp, vertical = 12.dp)
            )

            Spacer(modifier = Modifier.height(40.dp))

            StyledMenuButton(text = "📝 Crear carta", onClick = onCrearCarta)
            StyledMenuButton(text = "📂 Ver mazos", onClick = onVerMazos)
            StyledMenuButton(text = "🎮 Jugar", onClick = onJugar)
            StyledMenuButton(text = "🚪 Salir", onClick = onExit)
        }
    }
}

@Composable
fun StyledMenuButton(text: String, onClick: () -> Unit) {
    Button(
        onClick = onClick,
        modifier = Modifier
            .fillMaxWidth(0.85f)
            .padding(vertical = 8.dp),
        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF1976D2)),
        shape = RoundedCornerShape(20.dp),
        elevation = ButtonDefaults.buttonElevation(8.dp)
    ) {
        Text(
            text = text,
            fontSize = 18.sp,
            color = Color.White,
            modifier = Modifier.padding(vertical = 8.dp)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun MainMenuScreenPreview() {
    BraindeckTheme {
        MainMenuScreen()
    }
}


