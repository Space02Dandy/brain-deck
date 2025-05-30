package com.spacedandy.braindeck

import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity

class VerPreguntasActivity : AppCompatActivity() {
    private lateinit var listaPreguntas: ListView
    private lateinit var mazo: String
    private var cartas: MutableList<Carta> = mutableListOf()

    companion object {
        private const val REQUEST_EDITAR_CARTA = 1001
    }

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
        val intent = Intent(this, EditarCartaActivity::class.java).apply {
            putExtra("pregunta", carta.pregunta)
            putStringArrayListExtra("respuestas", ArrayList(carta.respuestas)) // importante
            putExtra("posicion", posicion)
        }
        startActivityForResult(intent, REQUEST_EDITAR_CARTA)
    }

    private fun eliminarCarta(posicion: Int) {
        cartas.removeAt(posicion)
        CartaManager.reemplazarCartas(mazo, cartas)
        CartaManager.guardar(this)
        mostrarPreguntas()
        Toast.makeText(this, "Carta eliminada", Toast.LENGTH_SHORT).show()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_EDITAR_CARTA && resultCode == RESULT_OK && data != null) {
            val nuevaPregunta = data.getStringExtra("nuevaPregunta") ?: return
            val nuevasRespuestas = data.getStringArrayListExtra("nuevasRespuestas") ?: return
            val posicion = data.getIntExtra("posicion", -1)

            if (posicion in cartas.indices) {
                val imagenUriString = data.getStringExtra("imagenUriString")
                val nuevaCarta = Carta(nuevaPregunta, nuevasRespuestas, imagenUriString)
                cartas[posicion] = nuevaCarta
                CartaManager.reemplazarCartas(mazo, cartas)
                CartaManager.guardar(this)
                mostrarPreguntas()
                Toast.makeText(this, "Carta editada", Toast.LENGTH_SHORT).show()
            }

        }
    }
}


