package com.spacedandy.braindeck

import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity

class VerMazosActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ver_mazos)

        val listaMazos = findViewById<ListView>(R.id.listaMazos)

        // Cargar mazos desde SharedPreferences
        CartaManager.cargar(this)
        val nombresMazos = CartaManager.obtenerNombresDeMazos()

        // Mostrar los mazos en el ListView
        val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, nombresMazos)
        listaMazos.adapter = adapter

        // Al hacer clic en un mazo, abrir VerPreguntasActivity
        listaMazos.setOnItemClickListener { _, _, position, _ ->
            val nombreMazo = nombresMazos[position]

            val intent = Intent(this, VerPreguntasActivity::class.java).apply {
                putExtra("mazo_nombre", nombreMazo)
            }
            startActivity(intent)
        }
    }
}
