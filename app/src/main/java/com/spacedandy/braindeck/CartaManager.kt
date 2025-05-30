package com.spacedandy.braindeck

import android.content.Context
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

object CartaManager {

    private const val PREFS_NAME = "CartasPrefs"
    private const val KEY_CARTAS = "mazos"

    private val gson = Gson()

    // Mapa que guarda los mazos con sus cartas
    private var mazos: MutableMap<String, MutableList<Carta>> = mutableMapOf()

    // Cargar datos desde SharedPreferences con filtro para cartas inválidas
    fun cargar(context: Context) {
        val prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        val json = prefs.getString(KEY_CARTAS, null)

        if (json != null) {
            val tipo = object : TypeToken<MutableMap<String, MutableList<Carta>>>() {}.type
            val mazosCargados: MutableMap<String, MutableList<Carta>> = gson.fromJson(json, tipo)

            // Filtramos cartas con respuestas nulas o vacías
            mazos = mazosCargados.mapValues { (_, cartas) ->
                cartas.filter {
                    it.pregunta.isNotBlank() && !it.respuestas.isNullOrEmpty()
                }.toMutableList()
            }.toMutableMap()
        }
    }

    // Guardar datos en SharedPreferences
    fun guardar(context: Context) {
        val prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        val editor = prefs.edit()
        val json = gson.toJson(mazos)
        editor.putString(KEY_CARTAS, json)
        editor.apply()
    }

    // Agregar una carta a un mazo (crea el mazo si no existe)
    fun agregarCarta(nombreMazo: String, carta: Carta) {
        val lista = mazos.getOrPut(nombreMazo) { mutableListOf() }
        lista.add(carta)
    }

    // Obtener todos los mazos con sus cartas
    fun obtenerMazos(): Map<String, List<Carta>> {
        return mazos
    }

    // Obtener nombres de todos los mazos
    fun obtenerNombresDeMazos(): List<String> {
        return mazos.keys.toList()
    }

    // Obtener cartas de un mazo específico con filtro adicional (por si acaso)
    fun obtenerCartasDeMazo(nombreMazo: String): List<Carta> {
        return mazos[nombreMazo]?.filter { !it.respuestas.isNullOrEmpty() } ?: emptyList()
    }

    // Reemplazar todas las cartas de un mazo con una nueva lista
    fun reemplazarCartas(nombreMazo: String, nuevasCartas: MutableList<Carta>) {
        mazos[nombreMazo] = nuevasCartas
    }

    // Eliminar un mazo completo
    fun eliminarMazo(nombreMazo: String) {
        mazos.remove(nombreMazo)
    }

    // Eliminar una carta específica de un mazo
    fun eliminarCarta(nombreMazo: String, carta: Carta) {
        mazos[nombreMazo]?.remove(carta)
    }

    // Limpiar todos los mazos y cartas
    fun limpiarTodo() {
        mazos.clear()
    }
}

