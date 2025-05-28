package com.spacedandy.braindeck

import android.os.Bundle
import android.view.Gravity
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.Toast
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
                    bottomMargin = 32
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

        guardarButton.setOnClickListener {
            val nombreMazo = mazoEditText.text.toString().trim()
            val pregunta = preguntaEditText.text.toString().trim()
            val respuesta1 = respuesta1EditText.text.toString().trim()
            val respuesta2 = respuesta2EditText.text.toString().trim()
            val respuesta3 = respuesta3EditText.text.toString().trim()
            val respuesta4 = respuesta4EditText.text.toString().trim()

            if (nombreMazo.isEmpty() || pregunta.isEmpty() || respuesta1.isEmpty()) {
                Toast.makeText(this, "Completa al menos mazo, pregunta y respuesta correcta", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val respuestas = listOf(respuesta1, respuesta2, respuesta3, respuesta4).filter { it.isNotEmpty() }
            val carta = Carta(pregunta, respuestas)

            CartaManager.cargar(this)
            CartaManager.agregarCarta(nombreMazo, carta)
            CartaManager.guardar(this)

            Toast.makeText(this, "Carta guardada correctamente", Toast.LENGTH_SHORT).show()

            // Limpiar campos para nueva entrada
            mazoEditText.text.clear()
            preguntaEditText.text.clear()
            respuesta1EditText.text.clear()
            respuesta2EditText.text.clear()
            respuesta3EditText.text.clear()
            respuesta4EditText.text.clear()
        }
    }
}
