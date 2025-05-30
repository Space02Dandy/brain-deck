package com.spacedandy.braindeck

import android.app.Activity
import android.content.Intent
import android.graphics.BitmapFactory
import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.net.Uri
import android.os.Bundle
import android.view.Gravity
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import java.io.InputStream

class EditarCartaActivity : AppCompatActivity() {

    private var imagenUri: Uri? = null
    private lateinit var imagenView: ImageView
    private val PICK_IMAGE_REQUEST = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val layout = LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
            setPadding(40, 40, 40, 40)
            layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT
            )
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
            text = " EDITAR CARTA "
            textSize = 24f
            setTextColor(Color.WHITE)
            gravity = Gravity.CENTER
            layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            ).apply { bottomMargin = 40 }

            background = GradientDrawable().apply {
                colors = intArrayOf(Color.parseColor("#FBC02D"), Color.parseColor("#F57F17"))
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

        val preguntaEditText = createEditText("❓ Escribe tu pregunta")
        val respuesta1EditText = createEditText("✅ Respuesta correcta")
        val respuesta2EditText = createEditText("⚪ Respuesta alternativa 2")
        val respuesta3EditText = createEditText("⚪ Respuesta alternativa 3")
        val respuesta4EditText = createEditText("⚪ Respuesta alternativa 4")

        layout.addView(preguntaEditText)
        layout.addView(respuesta1EditText)
        layout.addView(respuesta2EditText)
        layout.addView(respuesta3EditText)
        layout.addView(respuesta4EditText)

        imagenView = ImageView(this).apply {
            layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                400
            ).apply {
                bottomMargin = 24
            }
            scaleType = ImageView.ScaleType.FIT_CENTER
            background = GradientDrawable().apply {
                setStroke(3, Color.GRAY)
                cornerRadius = 20f
            }
        }
        layout.addView(imagenView)

        val cambiarImagenButton = Button(this).apply {
            text = "CAMBIAR IMAGEN"
            setTextColor(Color.WHITE)
            textSize = 16f
            background = GradientDrawable().apply {
                colors = intArrayOf(Color.parseColor("#42A5F5"), Color.parseColor("#1976D2"))
                cornerRadius = 20f
                setStroke(2, Color.WHITE)
            }
            setPadding(30, 20, 30, 20)
            layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            ).apply {
                bottomMargin = 40
            }

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
        layout.addView(cambiarImagenButton)

        val borrarImagenButton = Button(this).apply {
            text = "BORRAR IMAGEN"
            setTextColor(Color.WHITE)
            textSize = 16f
            background = GradientDrawable().apply {
                colors = intArrayOf(Color.parseColor("#EF5350"), Color.parseColor("#D32F2F"))
                cornerRadius = 20f
                setStroke(2, Color.WHITE)
            }
            setPadding(30, 20, 30, 20)
            layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            ).apply {
                bottomMargin = 40
            }

            setOnClickListener {
                imagenUri = null
                imagenView.setImageDrawable(null)
                Toast.makeText(this@EditarCartaActivity, "🗑 Imagen borrada", Toast.LENGTH_SHORT).show()
            }
        }
        layout.addView(borrarImagenButton)

        val space = Space(this).apply {
            layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, 0, 1f
            )
        }
        layout.addView(space)

        val guardarButton = Button(this).apply {
            text = " GUARDAR CAMBIOS "
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
                    Color.parseColor("#FFA000"),
                    Color.parseColor("#F57C00"),
                    Color.parseColor("#E65100")
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

        // Cargar datos del intent
        val pregunta = intent.getStringExtra("pregunta") ?: ""
        val respuestas = intent.getStringArrayListExtra("respuestas") ?: arrayListOf("", "", "", "")
        val posicion = intent.getIntExtra("posicion", -1)
        val nombreMazo = intent.getStringExtra("mazo_nombre") ?: ""
        val imagenUriString = intent.getStringExtra("imagenUriString")

        if (imagenUriString != null) {
            try {
                val uri = Uri.parse(imagenUriString)
                imagenUri = uri
                // Solicitar persistencia de permisos para este URI
                contentResolver.takePersistableUriPermission(uri, Intent.FLAG_GRANT_READ_URI_PERMISSION)
                val inputStream: InputStream? = contentResolver.openInputStream(uri)
                val bitmap = BitmapFactory.decodeStream(inputStream)
                imagenView.setImageBitmap(bitmap)
                inputStream?.close()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }

        preguntaEditText.setText(pregunta)
        if (respuestas.size > 0) respuesta1EditText.setText(respuestas.getOrNull(0) ?: "")
        if (respuestas.size > 1) respuesta2EditText.setText(respuestas.getOrNull(1) ?: "")
        if (respuestas.size > 2) respuesta3EditText.setText(respuestas.getOrNull(2) ?: "")
        if (respuestas.size > 3) respuesta4EditText.setText(respuestas.getOrNull(3) ?: "")

        guardarButton.setOnClickListener {
            val nuevaPregunta = preguntaEditText.text.toString().trim()
            val nuevasRespuestas = listOf(
                respuesta1EditText.text.toString().trim(),
                respuesta2EditText.text.toString().trim(),
                respuesta3EditText.text.toString().trim(),
                respuesta4EditText.text.toString().trim()
            ).filter { it.isNotEmpty() }

            if (nuevaPregunta.isEmpty()) {
                Toast.makeText(this, "⚠️ La pregunta no puede estar vacía", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (nuevasRespuestas.isEmpty()) {
                Toast.makeText(this, "⚠️ Debe haber al menos una respuesta", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val resultIntent = Intent().apply {
                putExtra("nuevaPregunta", nuevaPregunta)
                putStringArrayListExtra("nuevasRespuestas", ArrayList(nuevasRespuestas))
                putExtra("posicion", posicion)
                putExtra("mazo_nombre", nombreMazo)
                imagenUri?.let { uri ->
                    putExtra("imagenUriString", uri.toString())
                }
            }

            setResult(Activity.RESULT_OK, resultIntent)
            Toast.makeText(this, "💾 ¡Carta actualizada!", Toast.LENGTH_SHORT).show()
            finish()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == Activity.RESULT_OK) {
            data?.data?.let { uri ->
                imagenUri = uri

                // Solicitar persistencia de permisos
                contentResolver.takePersistableUriPermission(
                    uri,
                    Intent.FLAG_GRANT_READ_URI_PERMISSION
                )

                val inputStream = contentResolver.openInputStream(uri)
                val bitmap = BitmapFactory.decodeStream(inputStream)
                imagenView.setImageBitmap(bitmap)
                inputStream?.close()
            }
        }
    }
}



