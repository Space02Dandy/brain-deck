package com.spacedandy.braindeck

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView
import android.view.ViewGroup.LayoutParams

class crearCartaActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Crear un LinearLayout vertical
        val layout = LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
            layoutParams = LayoutParams(
                LayoutParams.MATCH_PARENT,
                LayoutParams.MATCH_PARENT
            )
            setPadding(32, 32, 32, 32)
        }

        // Título "Pregunta"
        val preguntaLabel = TextView(this).apply {
            text = "Pregunta"
            textSize = 18f
        }

        // Campo de texto para la pregunta
        val preguntaEditText = EditText(this).apply {
            hint = "Escribe tu pregunta aquí"
            layoutParams = LayoutParams(
                LayoutParams.MATCH_PARENT,
                LayoutParams.WRAP_CONTENT
            )
        }

        // Añadir vistas al layout
        layout.addView(preguntaLabel)
        layout.addView(preguntaEditText)

        setContentView(layout)
    }
}
