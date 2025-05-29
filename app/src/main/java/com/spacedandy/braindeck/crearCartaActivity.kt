package com.spacedandy.braindeck

import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.os.Bundle
import android.view.Gravity
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.Toast
import android.widget.TextView
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
            setPadding(40, 40, 40, 40)

            // Fondo con gradiente azul a blanco
            val gradientBackground = GradientDrawable(
                GradientDrawable.Orientation.TOP_BOTTOM,
                intArrayOf(
                    Color.parseColor("#1E88E5"), // Azul vibrante
                    Color.parseColor("#E3F2FD"), // Azul muy claro
                    Color.WHITE
                )
            )
            background = gradientBackground
        }

        // Título llamativo
        val titulo = TextView(this).apply {
            text = " CREAR NUEVA CARTA "
            textSize = 24f
            setTextColor(Color.WHITE)
            gravity = Gravity.CENTER
            layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            ).apply {
                bottomMargin = 40
            }

            // Fondo del título con gradiente rojo
            val titleBackground = GradientDrawable().apply {
                colors = intArrayOf(
                    Color.parseColor("#E53935"),
                    Color.parseColor("#D32F2F")
                )
                cornerRadius = 20f
                setStroke(3, Color.WHITE)
            }
            background = titleBackground
            setPadding(32, 20, 32, 20)
            elevation = 8f
        }
        layout.addView(titulo)

        fun createEditText(hintText: String): EditText {
            return EditText(this).apply {
                hint = hintText
                layoutParams = LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
                ).apply {
                    bottomMargin = 24
                }

                // Colores llamativos para los campos
                setTextColor(Color.parseColor("#0D47A1")) // Azul muy oscuro
                setHintTextColor(Color.parseColor("#42A5F5")) // Azul medio
                textSize = 16f

                // Diseño súper llamativo con gradiente y sombra
                val drawable = GradientDrawable().apply {
                    // Gradiente sutil de blanco a azul muy claro
                    colors = intArrayOf(Color.WHITE, Color.parseColor("#F3F9FF"))
                    cornerRadius = 20f
                    setStroke(4, Color.parseColor("#1976D2")) // Borde azul grueso
                }
                background = drawable
                setPadding(32, 28, 32, 28)
                elevation = 6f // Sombra para profundidad
            }
        }

        val mazoEditText = createEditText("📚 Nombre del Mazo")
        val preguntaEditText = createEditText("❓ Escribe tu pregunta")
        val respuesta1EditText = createEditText("✅ Respuesta correcta")
        val respuesta2EditText = createEditText("⚪ Respuesta alternativa 2")
        val respuesta3EditText = createEditText("⚪ Respuesta alternativa 3")
        val respuesta4EditText = createEditText("⚪ Respuesta alternativa 4")

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
            text = " GUARDAR CARTA "
            layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            ).apply {
                gravity = Gravity.BOTTOM
                topMargin = 32
            }

            // Botón súper llamativo
            setTextColor(Color.WHITE)
            textSize = 18f
            setAllCaps(false)

            val buttonDrawable = GradientDrawable().apply {
                // Gradiente rojo vibrante
                colors = intArrayOf(
                    Color.parseColor("#FF5722"), // Naranja-rojo
                    Color.parseColor("#D32F2F"), // Rojo
                    Color.parseColor("#B71C1C")  // Rojo oscuro
                )
                cornerRadius = 25f
                setStroke(3, Color.WHITE) // Borde blanco
            }
            background = buttonDrawable
            setPadding(40, 32, 40, 32)
            elevation = 10f // Sombra más pronunciada

            // Efecto de presión (simulado)
            setOnTouchListener { v, event ->
                when (event.action) {
                    android.view.MotionEvent.ACTION_DOWN -> {
                        v.scaleX = 0.95f
                        v.scaleY = 0.95f
                    }
                    android.view.MotionEvent.ACTION_UP, android.view.MotionEvent.ACTION_CANCEL -> {
                        v.scaleX = 1.0f
                        v.scaleY = 1.0f
                    }
                }
                false
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
                Toast.makeText(this, "⚠️ Completa al menos mazo, pregunta y respuesta correcta", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val respuestas = listOf(respuesta1, respuesta2, respuesta3, respuesta4).filter { it.isNotEmpty() }
            val carta = Carta(pregunta, respuestas)

            CartaManager.cargar(this)
            CartaManager.agregarCarta(nombreMazo, carta)
            CartaManager.guardar(this)

            Toast.makeText(this, "🎉 ¡Carta guardada exitosamente! 🎉", Toast.LENGTH_SHORT).show()

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