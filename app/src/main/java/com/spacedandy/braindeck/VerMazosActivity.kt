package com.spacedandy.braindeck

import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class VerMazosActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ver_mazos)

        val listaMazos = findViewById<ListView>(R.id.listaMazos)

        // Cargar mazos desde SharedPreferences
        CartaManager.cargar(this)
        val nombresMazos = CartaManager.obtenerNombresDeMazos()

        // Mostrar en ListView
        val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, nombresMazos)
        listaMazos.adapter = adapter

        // Mostrar mensaje al hacer clic en un mazo (puedes cambiar esto por abrir otra Activity)
        listaMazos.setOnItemClickListener { _, _, position, _ ->
            val nombreMazo = nombresMazos[position]
            Toast.makeText(this, "Mazo seleccionado: $nombreMazo", Toast.LENGTH_SHORT).show()

            // Aquí podrías abrir una nueva actividad para mostrar las cartas de ese mazo
        }
    }
}
