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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.spacedandy.braindeck.ui.theme.BraindeckTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            BraindeckTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    MainMenuScreen(
                        modifier = Modifier
                            .padding(innerPadding)
                            .fillMaxSize()
                    )
                }
            }
        }
    }
}

@Composable
fun MainMenuScreen(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Button(onClick = { /* TODO: Acción crear carta */ }, modifier = Modifier.fillMaxWidth(0.7f).padding(8.dp)) {
            Text("Crear carta")
        }
        Button(onClick = { /* TODO: Acción ver mazos */ }, modifier = Modifier.fillMaxWidth(0.7f).padding(8.dp)) {
            Text("Ver mazos")
        }
        Button(onClick = { /* TODO: Acción jugar */ }, modifier = Modifier.fillMaxWidth(0.7f).padding(8.dp)) {
            Text("Jugar")
        }
        Button(onClick = { /* TODO: Acción salir */ }, modifier = Modifier.fillMaxWidth(0.7f).padding(8.dp)) {
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