package com.example.cookit

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import com.example.cookit.data.MainRepository
import com.example.cookit.models.Recipe
import com.example.cookit.models.ResponseAPI
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext


class Home : AppCompatActivity() {
    private val coroutineContext : CoroutineContext = newSingleThreadContext("main")
    private val  scope = CoroutineScope(coroutineContext)

    private var recipes = ArrayList<Recipe>()
    private var recetas : ResponseAPI? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val btnListadoRecetas = findViewById<Button>(R.id.btnLista)
        btnListadoRecetas.setOnClickListener{
            cambioPantallaListado()
        }

        val btnFavoritos = findViewById<Button>(R.id.btnFavoritos)
        btnFavoritos.setOnClickListener{
            cambioPantallaFavoritos()
        }
    }
/*
    override fun onStart() {
        super.onStart()
        scope.launch {
            var recipes = MainRepository.fetchData(this@Home)
            Log.d("recipes - home ", recipes.size.toString())

        }
    }

 */

    override fun onStart() {
        super.onStart()
        scope.launch {
            recetas = MainRepository.fetchData(this@Home)

        }
    }


    fun cambioPantallaListado() {
        // Cambio a pantalla Listado
        var intent = Intent(this, RecipeList::class.java)
        intent.putExtra("Titulo", "Listado")
        startActivity(intent)

    }


    fun cambioPantallaFavoritos() {
        // Cambio a pantalla favoritos
        var intent = Intent(this, RecipeList::class.java)
        intent.putExtra("Titulo", "Favoritos")
        startActivity(intent)


    }
}