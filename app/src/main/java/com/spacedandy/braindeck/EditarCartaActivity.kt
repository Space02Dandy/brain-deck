package com.spacedandy.braindeck

import android.app.Activity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity

class EditarCartaActivity : AppCompatActivity() {
    private lateinit var preguntaEditText: EditText
    private lateinit var respuesta1EditText: EditText
    private lateinit var respuesta2EditText: EditText
    private lateinit var respuesta3EditText: EditText
    private lateinit var respuesta4EditText: EditText
    private lateinit var botonGuardar: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_editar_carta)

        preguntaEditText = findViewById(R.id.editTextPregunta)
        respuesta1EditText = findViewById(R.id.editTextRespuesta1)
        respuesta2EditText = findViewById(R.id.editTextRespuesta2)
        respuesta3EditText = findViewById(R.id.editTextRespuesta3)
        respuesta4EditText = findViewById(R.id.editTextRespuesta4)
        botonGuardar = findViewById(R.id.botonGuardar)

        val pregunta = intent.getStringExtra("pregunta") ?: ""
        val respuestas = intent.getStringArrayListExtra("respuestas") ?: arrayListOf("", "", "", "")
        val posicion = intent.getIntExtra("posicion", -1)

        preguntaEditText.setText(pregunta)
        if (respuestas.size > 0) respuesta1EditText.setText(respuestas.getOrNull(0))
        if (respuestas.size > 1) respuesta2EditText.setText(respuestas.getOrNull(1))
        if (respuestas.size > 2) respuesta3EditText.setText(respuestas.getOrNull(2))
        if (respuestas.size > 3) respuesta4EditText.setText(respuestas.getOrNull(3))

        botonGuardar.setOnClickListener {
            val nuevaPregunta = preguntaEditText.text.toString().trim()
            val nuevasRespuestas = listOf(
                respuesta1EditText.text.toString().trim(),
                respuesta2EditText.text.toString().trim(),
                respuesta3EditText.text.toString().trim(),
                respuesta4EditText.text.toString().trim()
            ).filter { it.isNotEmpty() }

            val resultIntent = intent.apply {
                putExtra("nuevaPregunta", nuevaPregunta)
                putStringArrayListExtra("nuevasRespuestas", ArrayList(nuevasRespuestas))
                putExtra("posicion", posicion)
            }

            setResult(Activity.RESULT_OK, resultIntent)
            finish()
        }
    }
}
