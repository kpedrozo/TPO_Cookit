package com.example.cookit

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TableLayout
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.cookit.data.MainRepository
import com.example.cookit.models.Recipe
import com.example.cookit.models.RecipeDetail
import com.example.cookit.models.ResponseAPI
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext


class Home : AppCompatActivity() {
    private val coroutineContext : CoroutineContext = newSingleThreadContext("main")
    private val  scope = CoroutineScope(coroutineContext)

    private lateinit var rvRecipes : RecyclerView

    private var recipes = ArrayList<Recipe>()
    private var recetas : ResponseAPI? = null
//    private var recetaDetail : RecipeDetail? = null

    private lateinit var adapter : RecipeAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_main) >>>  cambio de activity principa;
        setContentView(R.layout.activity_home)
        initRecyclerView()




        /*
        val btnListadoRecetas = findViewById<Button>(R.id.btnLista)
        btnListadoRecetas.setOnClickListener{
            cambioPantallaListado()
        }

        val btnFavoritos = findViewById<Button>(R.id.btnFavoritos)
        btnFavoritos.setOnClickListener{
            cambioPantallaFavoritos()
        }

         */
    }

    fun initRecyclerView() {
        rvRecipes = findViewById<RecyclerView>(R.id.rvRecipes)
//        rvRecipes.layoutManager = LinearLayoutManager(this)


        rvRecipes.layoutManager = GridLayoutManager(this, 2, GridLayoutManager.VERTICAL, false) // cambio a un layout GRID buscar como funciona
        adapter = RecipeAdapter(recipes, this)
        rvRecipes.adapter = adapter
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
            recipes = MainRepository.getRecipes(this@Home) // hasta aca tengo las recetas > necesito mostrarlas
            Log.d("getrecetas OK", "llegamooos")
//            recetaDetail = MainRepository.getRecipebyID(this@Home)
            withContext(Dispatchers.Main) {
                adapter.update(recipes)
            }

        }
    }


//    fun cambioPantallaListado() {
//        // Cambio a pantalla Listado
//        var intent = Intent(this, RecipeList::class.java)
//        intent.putExtra("Titulo", "Listado")
//        startActivity(intent)
//
//    }
//
//
//    fun cambioPantallaFavoritos() {
//        // Cambio a pantalla favoritos
//        var intent = Intent(this, RecipeList::class.java)
//        intent.putExtra("Titulo", "Favoritos")
//        startActivity(intent)
//
//
//    }
}