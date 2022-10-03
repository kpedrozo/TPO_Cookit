package com.example.cookit

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.cookit.models.Ingredients
import com.example.cookit.models.Recipe
import com.example.cookit.models.RecipeDetailModel

class RecipeDetailAdapter (var recipe : RecipeDetailModel,
                           context: Context)
    : RecyclerView.Adapter<RecipeDetailViewHolder>() {

    private val context = context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecipeDetailViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.activity_recipe_detail, parent, false)
        return RecipeDetailViewHolder(view)
    }

    override fun onBindViewHolder(holder: RecipeDetailViewHolder, position: Int) {

        holder.title.text = recipe.title

//        Glide.with(context)
//            .load(recipe.image.toString())
//            .placeholder(com.google.android.material.R.drawable.ic_clock_black_24dp)
//            .centerCrop()
//            .into(holder.img)

//        holder.ingredients.

        holder.instruccions.text = recipe.summary


    }

    override fun getItemCount(): Int {
        return recipe.ingredients.size
    }

    fun update(recipes_new: RecipeDetailModel) {
        recipe = recipes_new
        this.notifyDataSetChanged()
    }


}