package com.spacedandy.braindeck

import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.app.AppCompatActivity

class VerMazosActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Crear layout principal con gradiente
        val mainLayout = LinearLayout(this).apply {
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

        // Título súper llamativo
        val titulo = TextView(this).apply {
            text = " VER TODOS LOS MAZOS "
            textSize = 22f
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
            setPadding(32, 24, 32, 24)
            elevation = 12f
        }
        mainLayout.addView(titulo)

        // ListView personalizado
        val listaMazos = ListView(this).apply {
            layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT
            )

            // Estilo del ListView
            divider = null
            setPadding(0, 16, 0, 16)
            clipToPadding = false

            // Fondo transparente para que se vea el gradiente
            setBackgroundColor(Color.TRANSPARENT)
        }
        mainLayout.addView(listaMazos)

        setContentView(mainLayout)

        // Cargar mazos desde SharedPreferences
        CartaManager.cargar(this)
        val nombresMazos = CartaManager.obtenerNombresDeMazos()

        // Crear adaptador personalizado súper llamativo
        val adapter = object : ArrayAdapter<String>(this, 0, nombresMazos) {
            override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
                val nombreMazo = getItem(position) ?: ""

                // Crear tarjeta personalizada para cada mazo
                val cardLayout = LinearLayout(this@VerMazosActivity).apply {
                    orientation = LinearLayout.HORIZONTAL
                    layoutParams = LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT
                    ).apply {
                        bottomMargin = 16
                    }
                    setPadding(24, 20, 24, 20)
                    gravity = Gravity.CENTER_VERTICAL

                    // Fondo con gradiente y sombra
                    val cardBackground = GradientDrawable().apply {
                        colors = intArrayOf(Color.WHITE, Color.parseColor("#F3F9FF"))
                        cornerRadius = 20f
                        setStroke(4, Color.parseColor("#1976D2"))
                    }
                    background = cardBackground
                    elevation = 8f

                    // Efecto de clic
                    isClickable = true
                    isFocusable = true
                }

                // Icono llamativo
                val iconContainer = FrameLayout(this@VerMazosActivity).apply {
                    layoutParams = LinearLayout.LayoutParams(60, 60).apply {
                        rightMargin = 20
                    }

                    val iconBackground = GradientDrawable().apply {
                        colors = intArrayOf(
                            Color.parseColor("#42A5F5"),
                            Color.parseColor("#1976D2")
                        )
                        cornerRadius = 15f
                    }
                    background = iconBackground
                }

                val iconText = TextView(this@VerMazosActivity).apply {
                    text = "🎴"
                    textSize = 28f
                    gravity = Gravity.CENTER
                    layoutParams = FrameLayout.LayoutParams(
                        FrameLayout.LayoutParams.MATCH_PARENT,
                        FrameLayout.LayoutParams.MATCH_PARENT
                    )
                }
                iconContainer.addView(iconText)
                cardLayout.addView(iconContainer)

                // Contenido del mazo
                val textContainer = LinearLayout(this@VerMazosActivity).apply {
                    orientation = LinearLayout.VERTICAL
                    layoutParams = LinearLayout.LayoutParams(
                        0, LinearLayout.LayoutParams.WRAP_CONTENT, 1f
                    )
                }

                val nombreText = TextView(this@VerMazosActivity).apply {
                    text = nombreMazo
                    textSize = 18f
                    setTextColor(Color.parseColor("#0D47A1"))
                    setTypeface(null, android.graphics.Typeface.BOLD)
                }
                textContainer.addView(nombreText)

                val subtituloText = TextView(this@VerMazosActivity).apply {
                    text = "Toca para ver preguntas"
                    textSize = 12f
                    setTextColor(Color.parseColor("#42A5F5"))
                }
                textContainer.addView(subtituloText)
                cardLayout.addView(textContainer)

                // Flecha indicadora
                val arrowContainer = FrameLayout(this@VerMazosActivity).apply {
                    layoutParams = LinearLayout.LayoutParams(48, 48)

                    val arrowBackground = GradientDrawable().apply {
                        colors = intArrayOf(
                            Color.parseColor("#FF5722"),
                            Color.parseColor("#D32F2F")
                        )
                        cornerRadius = 12f
                    }
                    background = arrowBackground
                }

                val arrowText = TextView(this@VerMazosActivity).apply {
                    text = "▶️"
                    textSize = 16f
                    gravity = Gravity.CENTER
                    layoutParams = FrameLayout.LayoutParams(
                        FrameLayout.LayoutParams.MATCH_PARENT,
                        FrameLayout.LayoutParams.MATCH_PARENT
                    )
                }
                arrowContainer.addView(arrowText)
                cardLayout.addView(arrowContainer)

                return cardLayout
            }
        }

        listaMazos.adapter = adapter

        // Al hacer clic en un mazo, abrir VerPreguntasActivity con animación
        listaMazos.setOnItemClickListener { _, view, position, _ ->
            // Efecto visual de clic
            view.animate()
                .scaleX(0.95f)
                .scaleY(0.95f)
                .setDuration(100)
                .withEndAction {
                    view.animate()
                        .scaleX(1.0f)
                        .scaleY(1.0f)
                        .setDuration(100)
                        .start()
                }
                .start()

            val nombreMazo = nombresMazos[position]
            val intent = Intent(this, VerPreguntasActivity::class.java).apply {
                putExtra("mazo_nombre", nombreMazo)
            }
            startActivity(intent)
        }

        // Mostrar mensaje si no hay mazos
        if (nombresMazos.isEmpty()) {
            val emptyContainer = LinearLayout(this).apply {
                orientation = LinearLayout.VERTICAL
                gravity = Gravity.CENTER
                layoutParams = LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.MATCH_PARENT
                )
            }

            val emptyCard = LinearLayout(this).apply {
                orientation = LinearLayout.VERTICAL
                gravity = Gravity.CENTER
                layoutParams = LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
                ).apply {
                    setMargins(32, 32, 32, 32)
                }
                setPadding(40, 40, 40, 40)

                val emptyBackground = GradientDrawable().apply {
                    setColor(Color.parseColor("#FFF3E0"))
                    cornerRadius = 20f
                    setStroke(3, Color.parseColor("#FF9800"))
                }
                background = emptyBackground
                elevation = 8f
            }

            val emptyIcon = TextView(this).apply {
                text = "📚"
                textSize = 64f
                gravity = Gravity.CENTER
                layoutParams = LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
                ).apply {
                    bottomMargin = 16
                }
            }
            emptyCard.addView(emptyIcon)

            val emptyTitle = TextView(this).apply {
                text = "¡No hay mazos disponibles!"
                textSize = 20f
                setTextColor(Color.parseColor("#E65100"))
                gravity = Gravity.CENTER
                setTypeface(null, android.graphics.Typeface.BOLD)
                layoutParams = LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
                ).apply {
                    bottomMargin = 8
                }
            }
            emptyCard.addView(emptyTitle)

            val emptySubtitle = TextView(this).apply {
                text = "Crea tu primer mazo para comenzar"
                textSize = 14f
                setTextColor(Color.parseColor("#BF360C"))
                gravity = Gravity.CENTER
            }
            emptyCard.addView(emptySubtitle)

            emptyContainer.addView(emptyCard)
            mainLayout.removeView(listaMazos)
            mainLayout.addView(emptyContainer)
        }
    }
}