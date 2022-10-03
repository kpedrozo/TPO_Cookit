package com.example.cookit

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ImageButton
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.cookit.data.MainRepository
import com.example.cookit.models.Recipe
import com.example.cookit.models.RecipeDetailModel
import com.example.cookit.models.ResponseAPI
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext


class Home : AppCompatActivity() {
    private val coroutineContext : CoroutineContext = newSingleThreadContext("main")
    private val  scope = CoroutineScope(coroutineContext)

//    private val coroutineContext2 : CoroutineContext = newSingleThreadContext("id")
//    private val  scope2 = CoroutineScope(coroutineContext2)

    private lateinit var rvRecipes : RecyclerView
    private var recipes = ArrayList<Recipe>()
//    private var recetas : ResponseAPI? = null
    private var recetaDetail : RecipeDetailModel? = null
    private lateinit var adapter : RecipeAdapter

//    private lateinit var adapterRecipeDetail : RecipeDetailAdapter

    // firebase auth
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var btnLogout : Button


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_main) >>>  cambio de activity principa;
        setContentView(R.layout.activity_home)
        initRecyclerView()
        onClickDetails()





        val btnFavourite = findViewById<ImageButton>(R.id.btnFavourite)
        btnFavourite.setOnClickListener{
            cambioPantallaFavoritos()
        }

        // init firebase auth
        firebaseAuth = FirebaseAuth.getInstance()
        checkUser()

        // handle click -> logout user
        btnLogout = findViewById(R.id.btnLogout)
        btnLogout.setOnClickListener {
            firebaseAuth.signOut()
            checkUser()
        }

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

    private fun checkUser() {
        // get current user
        val firebaseUser = firebaseAuth.currentUser
        if (firebaseUser == null) {
            // user not logged in
            Log.d("Login", "Usuario no registrado")
            startActivity(Intent(this@Home, Login::class.java))
            finish()
        }
    }


    private fun initRecyclerView() {
        rvRecipes = findViewById<RecyclerView>(R.id.rvRecipes)
//        rvRecipes.layoutManager = LinearLayoutManager(this)
        rvRecipes.layoutManager = GridLayoutManager(this, 2, GridLayoutManager.VERTICAL, false) // cambio a un layout GRID buscar como funciona
        adapter = RecipeAdapter(recipes, this)
        rvRecipes.adapter = adapter
    }

    private fun onClickDetails() {
        adapter.onItemClick = { recipe : Recipe ->
            scope.launch {
                var receta = MainRepository.getRecipebyID(this@Home, recipe.id)

                var intent = Intent (this@Home, RecipeDetail::class.java)
                intent.putExtra("title", receta.title)
                intent.putExtra("img", receta.image)
                intent.putExtra("ingredients", receta.ingredients)
                intent.putExtra("instruccions", receta.summary)
                startActivity(intent)

            }

//            var intent = Intent(this, RecipeDetail::class.java)
//
////            Log.d("ID", "onClickDetails: ${recipe.id}")
////            recetaDetail = getDetails()
//
////            scope.launch {
////
////                withContext(Dispatchers.Main) {
////                    Log.d("ID", "onClickDetails: aca esta el detalle de receta ${receta.title}, ${receta.image}")
////                }
////            }
//
//            intent.putExtra("title", recetaDetail!!.title)
//            intent.putExtra("instruccions", recipe.image)
//            startActivity(intent)
        }
    }
//
//    suspend fun getRecipebyId (id : Int) : RecipeDetailModel {
//        return MainRepository.getRecipebyID(this@Home, id)
//    }

    //    fun cambioPantallaFavoritos() {
//        // Cambio a pantalla favoritos
//        var intent = Intent(this, RecipeList::class.java)
//        intent.putExtra("Titulo", "Favoritos")
//        startActivity(intent)



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
//        adapterRecipeDetail = RecipeDetailAdapter(recetaDetail!!, this)

        scope.launch {
            recipes = MainRepository.getRecipes(this@Home) // hasta aca tengo las recetas > necesito mostrarlas
            Log.d("getrecetas OK", "llegamooos")
            withContext(Dispatchers.Main) {
                adapter.update(recipes)
            }
//            // adapter de receta detail! si no no lo muestra
//            recetaDetail = MainRepository.getRecipebyID(this@Home, id = 745445)
//        }
//        scope2.launch {
//            recetaDetail = MainRepository.getRecipebyID(this@Home, id = 745445)
//            adapterRecipeDetail = RecipeDetailAdapter(recetaDetail!!, this@Home)
//            Log.d("recetaDetail", "onStart: ${recetaDetail!!.id}. nombre: ${recetaDetail!!.title}")
//            withContext(Dispatchers.Main) {
//                adapterRecipeDetail.update(recetaDetail!!)
//            }
        }
    }

    private fun cambioPantallaFavoritos() {
        val intent = Intent(this, Favourite::class.java)
        intent.putExtra("Titulo", "Recetas guardadas")
        startActivity(intent)
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