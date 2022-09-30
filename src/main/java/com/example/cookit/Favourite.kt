package com.example.cookit

import android.content.Intent
import android.os.Bundle
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.cookit.data.MainRepository
import com.example.cookit.models.Recipe
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

class Favourite : AppCompatActivity() {
    private val coroutineContext : CoroutineContext = newSingleThreadContext("favourite")
    private val  scope = CoroutineScope(coroutineContext)
    private lateinit var rvRecipes : RecyclerView
    private var recipes = ArrayList<Recipe>()
    private lateinit var adapter : RecipeAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_favourite)
        initRecyclerView()

        val btnHome = findViewById<ImageButton>(R.id.btnFavourite)
        btnHome.setOnClickListener{
            cambioPantallaHome()
        }
    }


    fun initRecyclerView() {
        rvRecipes = findViewById<RecyclerView>(R.id.rvRecipes)
        rvRecipes.layoutManager = LinearLayoutManager(this)
        adapter = RecipeAdapter(recipes, this)
        rvRecipes.adapter = adapter
    }

    override fun onStart() {
        super.onStart()
        scope.launch {
            recipes = MainRepository.getRecipes(this@Favourite)
            withContext(Dispatchers.Main) {
                adapter.update(recipes)
            }

        }
    }

    fun cambioPantallaHome () {
        var intent = Intent(this, Home::class.java)
        startActivity(intent)
        finish()
    }

}