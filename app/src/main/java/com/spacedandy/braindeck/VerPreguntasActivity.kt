package com.spacedandy.braindeck

import android.os.Bundle
import android.widget.ArrayAdapter
import androidx.appcompat.app.AlertDialog
import android.widget.Toast

import androidx.activity.enableEdgeToEdge
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class VerPreguntasActivity : AppCompatActivity() {
    private lateinit var listaPreguntas: ListView
    private lateinit var mazo: String
    private var cartas: MutableList<Carta> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ver_preguntas)

        listaPreguntas = findViewById(R.id.listaPreguntas)
        mazo = intent.getStringExtra("mazo_nombre") ?: ""

        CartaManager.cargar(this)
        cartas = CartaManager.obtenerCartasDeMazo(mazo).toMutableList()


        mostrarPreguntas()

        listaPreguntas.setOnItemClickListener { _, _, position, _ ->
            val carta = cartas[position]
            mostrarOpciones(carta, position)
        }
    }

    private fun mostrarPreguntas() {
        val preguntas = cartas.map { it.pregunta }
        val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, preguntas)
        listaPreguntas.adapter = adapter
    }

    private fun mostrarOpciones(carta: Carta, posicion: Int) {
        val opciones = arrayOf("Editar", "Eliminar")
        AlertDialog.Builder(this)
            .setTitle("Opciones")
            .setItems(opciones) { _, which ->
                when (which) {
                    0 -> editarCarta(carta, posicion)
                    1 -> eliminarCarta(posicion)
                }
            }
            .show()
    }

    private fun editarCarta(carta: Carta, posicion: Int) {
        // Abrir actividad de edición, enviando datos y posición para reemplazar al guardar
    }

    private fun eliminarCarta(posicion: Int) {
        cartas.removeAt(posicion)
        CartaManager.reemplazarCartas(mazo, cartas)
        CartaManager.guardar(this)
        mostrarPreguntas()
        Toast.makeText(this, "Carta eliminada", Toast.LENGTH_SHORT).show()
    }
}
