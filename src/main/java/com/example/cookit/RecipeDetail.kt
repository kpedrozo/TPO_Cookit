package com.example.cookit

import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.ListView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.cookit.models.Ingredients

class RecipeDetail : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_recipe_detail)
    }

    override fun onStart() {
        super.onStart()
        val title = findViewById<TextView>(R.id.txtTituloReceta)
        val img = findViewById<ImageView>(R.id.imgRecipe)
        val ingredients = findViewById<ListView>(R.id.txtIngredients)
        val instruccions = findViewById<TextView>(R.id.txtInstruccions)


        title.text = intent.extras?.getString("title")

        Glide.with(this)
            .load(intent.extras?.getString("img"))
            .placeholder(com.google.android.material.R.drawable.ic_clock_black_24dp)
            .centerCrop()
            .into(img)


        val ingList = intent.getSerializableExtra("ingredients") as MutableList<Ingredients>

        val ingredientsName : ArrayList<String> = ArrayList()
        for (ingredient in ingList) {
            ingredientsName.add(ingredient.name)
        }

        val arrayAdapter  : ArrayAdapter<String> = ArrayAdapter(this, android.R.layout.simple_list_item_1, ingredientsName)
        ingredients.adapter = arrayAdapter

        instruccions.text = intent.extras?.getString("instruccions")


    }
}