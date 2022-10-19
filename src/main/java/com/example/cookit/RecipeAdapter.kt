package com.example.cookit

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.cookit.models.Recipe
import com.example.cookit.models.RecipeEntity

class RecipeAdapter (var items: MutableList<Recipe>,
                     context: Context)
    : RecyclerView.Adapter<RecipeViewHolder>() {

    var onItemClick : ((Recipe) -> Unit)? = null
    var onItemFavouriteClick : ((Recipe) -> Unit)? = null

//    private var recipes : MutableList<RecipeEntity> = mutableListOf()

    private val context = context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecipeViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.recipe_item, parent, false)
        return RecipeViewHolder(view)
    }

    override fun onBindViewHolder(holder: RecipeViewHolder, position: Int) {
        holder.title.text = items[position].title

        Glide.with(context)
            .load(items[position].image.toString())
            .placeholder(com.google.android.material.R.drawable.ic_clock_black_24dp)
            .centerCrop()
            .into(holder.img)

        val item = items[position]
        holder.itemView.setOnClickListener {
            onItemClick?.invoke(item)
        }

        // boton favoritos deberia hacer algo !!!!
        //TODO
//        val favourite = items[position]
//        holder.itemView.setOnClickListener {
//            onItemFavouriteClick?.invoke(favourite)
//        }
    }

    override fun getItemCount(): Int {
        return items.size
    }

    fun update(recipes_new: MutableList<Recipe>) {
        items = recipes_new
        this.notifyDataSetChanged()
    }

//    fun updateEntity(recipes_new : MutableList<RecipeEntity>) {
//        recipes = recipes_new
//        this.notifyDataSetChanged()
//    }


}