package com.spacedandy.braindeck

import android.os.Bundle
import android.view.Gravity
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity

class crearCartaActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val layout = LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
            layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT
            )
            setPadding(32, 32, 32, 32)
        }

        fun createEditText(hintText: String): EditText {
            return EditText(this).apply {
                hint = hintText
                layoutParams = LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
                ).apply {
                    bottomMargin = 32 // Más separación entre campos
                }
            }
        }

        val mazoEditText = createEditText("Mazo")
        val preguntaEditText = createEditText("Pregunta")
        val respuesta1EditText = createEditText("Respuesta 1 (correcta)")
        val respuesta2EditText = createEditText("Respuesta 2")
        val respuesta3EditText = createEditText("Respuesta 3")
        val respuesta4EditText = createEditText("Respuesta 4")

        layout.addView(mazoEditText)
        layout.addView(preguntaEditText)
        layout.addView(respuesta1EditText)
        layout.addView(respuesta2EditText)
        layout.addView(respuesta3EditText)
        layout.addView(respuesta4EditText)

        // Espacio flexible para empujar el botón hacia abajo
        val space = LinearLayout(this).apply {
            layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, 0, 1f
            )
        }
        layout.addView(space)

        val guardarButton = Button(this).apply {
            text = "Guardar carta"
            layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            ).apply {
                gravity = Gravity.BOTTOM
            }
        }
        layout.addView(guardarButton)

        setContentView(layout)
    }
}
