package com.spacedandy.braindeck

import android.app.Activity
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.Gravity
import android.widget.*
import androidx.appcompat.app.AppCompatActivity

class crearCartaActivity : AppCompatActivity() {

    private var imagenUriSeleccionada: Uri? = null
    private val PICK_IMAGE_REQUEST = 1
    private lateinit var vistaImagen: ImageView
    private lateinit var botonBorrarImagen: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val layout = LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
            layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT
            )
            setPadding(40, 40, 40, 40)

            background = GradientDrawable(
                GradientDrawable.Orientation.TOP_BOTTOM,
                intArrayOf(
                    Color.parseColor("#1E88E5"),
                    Color.parseColor("#E3F2FD"),
                    Color.WHITE
                )
            )
        }

        val titulo = TextView(this).apply {
            text = " CREAR NUEVA CARTA "
            textSize = 24f
            setTextColor(Color.WHITE)
            gravity = Gravity.CENTER
            layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            ).apply { bottomMargin = 40 }

            background = GradientDrawable().apply {
                colors = intArrayOf(Color.parseColor("#E53935"), Color.parseColor("#D32F2F"))
                cornerRadius = 20f
                setStroke(3, Color.WHITE)
            }
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
                ).apply { bottomMargin = 24 }

                setTextColor(Color.parseColor("#0D47A1"))
                setHintTextColor(Color.parseColor("#42A5F5"))
                textSize = 16f
                background = GradientDrawable().apply {
                    colors = intArrayOf(Color.WHITE, Color.parseColor("#F3F9FF"))
                    cornerRadius = 20f
                    setStroke(4, Color.parseColor("#1976D2"))
                }
                setPadding(32, 28, 32, 28)
                elevation = 6f
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

        val botonImagen = Button(this).apply {
            text = "📷 Seleccionar Imagen"
            setOnClickListener {
                val intent = Intent(Intent.ACTION_OPEN_DOCUMENT).apply {
                    addCategory(Intent.CATEGORY_OPENABLE)
                    type = "image/*"
                    flags = Intent.FLAG_GRANT_PERSISTABLE_URI_PERMISSION or
                            Intent.FLAG_GRANT_READ_URI_PERMISSION
                }
                startActivityForResult(intent, PICK_IMAGE_REQUEST)
            }
        }
        layout.addView(botonImagen)

        // Botón para borrar imagen (se mostrará solo si hay imagen seleccionada)
        botonBorrarImagen = Button(this).apply {
            text = "🗑️ Borrar Imagen"
            layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            ).apply {
                gravity = Gravity.CENTER_HORIZONTAL
                bottomMargin = 24
            }
            visibility = Button.GONE // Oculto por defecto

            setTextColor(Color.WHITE)
            background = GradientDrawable().apply {
                colors = intArrayOf(Color.parseColor("#78909C"), Color.parseColor("#546E7A"))
                cornerRadius = 20f
                setStroke(2, Color.WHITE)
            }
            setPadding(32, 20, 32, 20)
            elevation = 6f

            setOnClickListener {
                imagenUriSeleccionada = null
                vistaImagen.setImageDrawable(null)
                visibility = Button.GONE
            }
        }
        layout.addView(botonBorrarImagen)

        vistaImagen = ImageView(this).apply {
            layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                400
            ).apply {
                topMargin = 16
                bottomMargin = 32
            }
            scaleType = ImageView.ScaleType.FIT_CENTER
        }
        layout.addView(vistaImagen)

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

            setTextColor(Color.WHITE)
            textSize = 18f
            setAllCaps(false)
            background = GradientDrawable().apply {
                colors = intArrayOf(
                    Color.parseColor("#FF5722"),
                    Color.parseColor("#D32F2F"),
                    Color.parseColor("#B71C1C")
                )
                cornerRadius = 25f
                setStroke(3, Color.WHITE)
            }
            setPadding(40, 32, 40, 32)
            elevation = 10f
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
            val imagenUri = imagenUriSeleccionada?.toString()
            val carta = Carta(pregunta, respuestas, imagenUri)

            CartaManager.cargar(this)
            CartaManager.agregarCarta(nombreMazo, carta)
            CartaManager.guardar(this)

            Toast.makeText(this, "🎉 ¡Carta guardada exitosamente! 🎉", Toast.LENGTH_SHORT).show()

            mazoEditText.text.clear()
            preguntaEditText.text.clear()
            respuesta1EditText.text.clear()
            respuesta2EditText.text.clear()
            respuesta3EditText.text.clear()
            respuesta4EditText.text.clear()
            imagenUriSeleccionada = null
            vistaImagen.setImageDrawable(null)
            botonBorrarImagen.visibility = Button.GONE
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == Activity.RESULT_OK) {
            data?.data?.let { uri ->
                contentResolver.takePersistableUriPermission(
                    uri,
                    Intent.FLAG_GRANT_READ_URI_PERMISSION
                )
                imagenUriSeleccionada = uri
                vistaImagen.setImageURI(imagenUriSeleccionada)
                botonBorrarImagen.visibility = Button.VISIBLE
            }
        }
    }
}



