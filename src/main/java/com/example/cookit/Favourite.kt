package com.example.cookit

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.cookit.data.MainRepository
import com.example.cookit.models.Recipe
import com.example.cookit.models.RecipeDetailModel
import com.example.cookit.models.RecipeEntity
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

class Favourite : AppCompatActivity() {
    private val coroutineContext : CoroutineContext = newSingleThreadContext("favourite")
    private val  scope = CoroutineScope(coroutineContext)
    private lateinit var rvRecipes : RecyclerView
    private var recipes = ArrayList<Recipe>()
    private var recipesEntities = ArrayList<RecipeEntity>()
    private lateinit var adapter : RecipeEntityAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_favourite)
        initRecyclerView()
        onClickFavouriteDetails()
        onItemNOTFavouriteClick()

        val btnHome = findViewById<ImageButton>(R.id.btnHome)
        btnHome.setOnClickListener{
            cambioPantallaHome()
        }
    }

    fun initRecyclerView() {
        rvRecipes = findViewById<RecyclerView>(R.id.rvRecipes)
        rvRecipes.layoutManager = LinearLayoutManager(this)
        adapter = RecipeEntityAdapter(this)
        rvRecipes.adapter = adapter
    }

    override fun onStart() {
        super.onStart()
        scope.launch {
            recipesEntities =
                MainRepository.getRecipesFromRoom(this@Favourite) as ArrayList<RecipeEntity>
//                MainRepository.getRecipes(this@Favourite)
            Log.d("Favourite", "onStart: tengo las recetas desde room q son favoritas")
            withContext(Dispatchers.Main) {
                adapter.update(recipesEntities)
                //adapter.updateEntity(recipesEntities)
            }

        }
    }

    private fun onClickFavouriteDetails() {
        adapter.onItemClick = {
            scope.launch{
                val receta = MainRepository.getRecipebyID(this@Favourite, it.id)
                showRecipeDetails(receta)
            }
        }
    }

    private fun onItemNOTFavouriteClick() {
        adapter.onItemClick = {
            scope.launch {
                MainRepository.deleteRecipeFromFavourite(this@Favourite, it.id)
            }
        }
    }

    private fun showRecipeDetails(receta: RecipeDetailModel) {
        val intent = Intent (this@Favourite, RecipeDetail::class.java)
        intent.putExtra("title", receta.title)
        intent.putExtra("img", receta.image)
        intent.putExtra("ingredients", receta.ingredients)
        intent.putExtra("instruccions", receta.summary)
        startActivity(intent)
    }

    fun cambioPantallaHome () {
        val intent = Intent(this, Home::class.java)
        startActivity(intent)
        finish()
    }

}