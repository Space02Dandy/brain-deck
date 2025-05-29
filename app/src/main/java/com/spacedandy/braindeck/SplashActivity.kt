package com.spacedandy.braindeck

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.core.*
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.spacedandy.braindeck.ui.theme.BraindeckTheme
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SplashActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            BraindeckTheme {
                SplashScreen()
            }
        }
    }
}

@Composable
fun SplashScreen() {
    val context = LocalContext.current

    val scale = remember { Animatable(0.8f) }
    val alpha = remember { Animatable(0f) }
    val rotation = remember { Animatable(0f) }

    LaunchedEffect(Unit) {
        coroutineScope {
            launch {
                scale.animateTo(
                    targetValue = 1.2f,
                    animationSpec = tween(durationMillis = 600, easing = EaseOutBack)
                )
                scale.animateTo(
                    targetValue = 1f,
                    animationSpec = tween(durationMillis = 300)
                )
            }
            launch {
                alpha.animateTo(
                    targetValue = 1f,
                    animationSpec = tween(durationMillis = 800)
                )
            }
            launch {
                rotation.animateTo(
                    targetValue = 10f,
                    animationSpec = tween(durationMillis = 800, easing = LinearOutSlowInEasing)
                )
                rotation.animateTo(
                    targetValue = 0f,
                    animationSpec = tween(durationMillis = 200)
                )
            }
        }

        delay(2000)
        context.startActivity(Intent(context, MainActivity::class.java))
        if (context is ComponentActivity) context.finish()
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(32.dp),
        contentAlignment = Alignment.Center
    ) {
        Image(
            painter = painterResource(id = R.drawable.icono_braindeck),
            contentDescription = "Logo de la aplicación",
            modifier = Modifier
                .scale(scale.value)
                .alpha(alpha.value)
                .graphicsLayer {
                    rotationZ = rotation.value
                }
        )
    }
}

