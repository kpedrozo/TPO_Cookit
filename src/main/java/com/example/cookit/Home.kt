package com.example.cookit

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.cookit.data.MainRepository
import com.example.cookit.models.Recipe
import com.example.cookit.models.RecipeDetailModel
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext


class Home : AppCompatActivity() {
    private val coroutineContext : CoroutineContext = newSingleThreadContext("main")
    private val  scope = CoroutineScope(coroutineContext)

    private lateinit var rvRecipes : RecyclerView
    private var recipes = ArrayList<Recipe>()
    private var recetaDetail : RecipeDetailModel? = null
    private lateinit var adapter : RecipeAdapter
    private lateinit var btnFavourite : ImageButton
    private lateinit var tvUser : TextView

    // firebase auth
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var btnLogout : Button


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_main) >>>  cambio de activity principa;
        setContentView(R.layout.activity_home)
        initRecyclerView()
        onClickDetails()

        btnFavourite = findViewById(R.id.btnFavourite)
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
        else {
            // user logueado
            // get user email
            val email = firebaseUser.email
            // get user
            val user = firebaseUser.displayName
            // set email
            Log.d("Login", "checkUser: ${user}")
            tvUser = findViewById(R.id.tvUser)
            tvUser.text = user;
//             set name

        }
    }

    private fun initRecyclerView() {
        rvRecipes = findViewById<RecyclerView>(R.id.rvRecipes)
        rvRecipes.layoutManager = GridLayoutManager(this, 2, GridLayoutManager.VERTICAL, false) // cambio a un layout GRID buscar como funciona
        adapter = RecipeAdapter(recipes, this)
        rvRecipes.adapter = adapter
    }

    private fun onClickDetails() {
        adapter.onItemClick = { recipe : Recipe ->
            scope.launch {
                val receta = MainRepository.getRecipebyID(this@Home, recipe.id)

                val intent = Intent (this@Home, RecipeDetail::class.java)
                intent.putExtra("title", receta.title)
                intent.putExtra("img", receta.image)
                intent.putExtra("ingredients", receta.ingredients)
                intent.putExtra("instruccions", receta.summary)
                startActivity(intent)
            }
        }
    }

    override fun onStart() {
        super.onStart()
        scope.launch {
            recipes = MainRepository.getRecipes(this@Home) // hasta aca tengo las recetas > necesito mostrarlas
            Log.d("getrecetas OK", "llegamooos")
            withContext(Dispatchers.Main) {
                adapter.update(recipes)
            }
        }
    }

    private fun cambioPantallaFavoritos() {
        val intent = Intent(this, Favourite::class.java)
        intent.putExtra("Titulo", "Recetas guardadas")
        startActivity(intent)
    }

}