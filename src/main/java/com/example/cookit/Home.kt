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
import com.example.cookit.data.RoomDataBase
import com.example.cookit.models.Recipe
import com.example.cookit.models.RecipeDao
import com.example.cookit.models.RecipeDetailModel
import com.example.cookit.models.RecipeEntity
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
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
    private lateinit var adapterEntity : RecipeEntityAdapter
    private lateinit var btnFavourite : ImageButton
    private lateinit var tvUser : TextView

    // firebase auth
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var btnLogout : Button

    // google logout
    lateinit var mGoogleSignInClient : GoogleSignInClient
//    private var auth = FirebaseAuth.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val recycler = findViewById<RecyclerView>(R.id.rvRecipes)
        val adapter = RecipeAdapter(recipes, this)
        setContentView(R.layout.activity_home)
        initRecyclerView()
        onClickDetails()
        cargarRoom()
        eliminarFavorito()

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
            mGoogleSignInClient.signOut().addOnCompleteListener {
                firebaseAuth.signOut()
                checkUser()
            }
        }

        // Configure the Google Sign OUT
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()

        mGoogleSignInClient = GoogleSignIn.getClient(this, gso)
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
                showRecipeDetails(receta)
            }
        }
    }

    private fun showRecipeDetails(receta: RecipeDetailModel) {
        val intent = Intent (this@Home, RecipeDetail::class.java)
        intent.putExtra("title", receta.title)
        intent.putExtra("img", receta.image)
        intent.putExtra("ingredients", receta.ingredients)
        intent.putExtra("instruccions", receta.summary)
        startActivity(intent)
    }

    private fun cargarRoom() {
        adapter.onItemFavouriteClick = { recipe : Recipe ->
            scope.launch {
                val dao = RoomDataBase.getInstance(this@Home).recipeDao()
                dao.insertRecipe(RecipeEntity(recipe.id, recipe.title, recipe.image, true))
                Log.d("Favourite", "cargarRoom: la receta se guardo en room")
                MainRepository.insertRecipeFavourite(this@Home, RecipeEntity(recipe.id, recipe.title, recipe.image, true))
                Log.d("Favourite", "cargarRoom: Aca deberia insertarse en favoritos")
//                adapterEntity.update(dao.fetchAll())
                Log.d("Favourite", "cargarRoom: despues del adapter fetch all")
            }
        }
    }

    private fun eliminarFavorito() {
        adapter.onItemNOTFavouriteClick = { recipe : Recipe ->
            scope.launch{
                Log.d("Favourite", "eliminarFavorito: llamo al DELETE del MAIN")
                MainRepository.deleteRecipeFromFavourite(this@Home, recipe.id)
                Log.d("Favourite", "eliminarFavorito: delete ok")


                // ademas de cambiar el status a false, no permite cambiar el color del button fav a gris si fui a FAVORITOS y volvi a HOME.
                recipe.statusFav = false;
            }
        }
    }

    override fun onStart() {
        super.onStart()
        scope.launch {
            // me traigo las recetas de DB que son favoritas
            val recipesRoom = RoomDataBase.getInstance(this@Home).recipeDao()
            recipesRoom.fetchAll()
            Log.d("Favourite", "onStart: ${recipesRoom}")
            // get de recetas a la API
            recipes = MainRepository.getRecipes(this@Home) // hasta aca tengo las recetas > necesito mostrarlas
            Log.d("getrecetas OK", "llegamooos")
            // si las recetas coinciden, mostrarlas con el button favorito ROJO.
            recipes = verificarFavoritos(recipesRoom, recipes)
            withContext(Dispatchers.Main) {
                adapter.update(recipes)
            }
        }
    }

    private suspend fun verificarFavoritos(recipesRoom: RecipeDao, recipes: ArrayList<Recipe>): ArrayList<Recipe> {
        for (recipe in recipes){
            recipesRoom.fetchAll().toSet().forEach { re ->
                if(re.id == recipe.id) {
                    recipe.statusFav = true;
                }
            }
        }
        return recipes;
    }

    private fun cambioPantallaFavoritos() {
        val intent = Intent(this, Favourite::class.java)
        intent.putExtra("Titulo", "Recetas guardadas")
        startActivity(intent)
    }

}