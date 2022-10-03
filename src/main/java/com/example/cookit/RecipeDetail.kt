package com.example.cookit

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.ListView
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.cookit.data.MainRepository
import com.example.cookit.models.Ingredients
import com.example.cookit.models.RecipeDetailModel
import kotlinx.coroutines.*
import java.io.Serializable
import kotlin.coroutines.CoroutineContext

class RecipeDetail : AppCompatActivity() {

    private val coroutineContext : CoroutineContext = newSingleThreadContext("main")
    private val  scope = CoroutineScope(coroutineContext)

    private lateinit var rvRecipes : RecyclerView

    private var recipe : RecipeDetailModel? = null

    private lateinit var adapter : RecipeDetailAdapter

    var ingredientsList : ArrayList<Ingredients> = recipe!!.ingredients

        override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recipe_detail)

//        initRecyclerView()

//        val recipe = intent.getParcelableExtra<Recipe>("title")
//        if (recipe != null){
//            val title : TextView = findViewById(R.id.txtTituloReceta)
//            val image : ImageView = findViewById(R.id.imgRecipe)
//
//            title.text = recipe.title
//            image.setImageResource(recipe.image.toInt())
//        }
    }

//    private fun initRecyclerView() {
//        rvRecipes = findViewById<RecyclerView>(R.id.rvIngredients)
//        rvRecipes.layoutManager = LinearLayoutManager(this)
////        rvRecipes.layoutManager = GridLayoutManager(this, 2, GridLayoutManager.VERTICAL, false) // cambio a un layout GRID buscar como funciona
//        adapter = RecipeDetailAdapter(recipe!!, this)
//        rvRecipes.adapter = adapter
//    }

    override fun onStart() {
        super.onStart()
        var title = findViewById<TextView>(R.id.txtTituloReceta)
        var img = findViewById<ImageView>(R.id.imgRecipe)
        var ingredients = findViewById<TextView>(R.id.txtIngredients)
        var instruccions = findViewById<TextView>(R.id.txtInstruccions)



        title.text = intent.extras?.getString("title")

        Glide.with(this)
            .load(intent.extras?.getString("img"))
            .placeholder(com.google.android.material.R.drawable.ic_clock_black_24dp)
            .centerCrop()
            .into(img)



        var ingList = intent.getSerializableExtra("ingredients") as ArrayList<Ingredients>

                for (ingredient in ingList) {
            Log.d("ingredients", "onStart: ${ingredient.name}")

        }

        //ingredients.text = ingList.


        instruccions.text = intent.extras?.getString("instruccions")
//
//
//
//        scope.launch {
//            var recipe = MainRepository.getRecipebyID(this@RecipeDetail, recipe?.id)
//
//                title.text = recipe.title
//                instruccions.text = recipe.summary
//        }




    }
}