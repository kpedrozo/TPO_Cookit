package com.example.cookit

import android.content.Context

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.content.res.AppCompatResources.getColorStateList
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.cookit.models.Recipe

class RecipeAdapter (var items: MutableList<Recipe>,
                     context: Context)
    : RecyclerView.Adapter<RecipeViewHolder>() {

    var onItemClick : ((Recipe) -> Unit)? = null
    var onItemFavouriteClick : ((Recipe) -> Unit)? = null
    var onItemNOTFavouriteClick : ((Recipe) -> Unit)? = null

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

        if (item.statusFav) {
            holder.btnFavourite.backgroundTintList = getColorStateList(context, android.R.color.holo_red_dark)
            Log.d("Favourite", "onBindViewHolder: title : ${item.title} || status ${item.statusFav}")
        }

        val favourite = items[position]
        var flag = true;
        holder.btnFavourite.setOnClickListener {
            Log.d("Favourite", "onBindViewHolder: aca marcaria la receta como fav")
            if (flag) {
                item.statusFav = true;
                holder.btnFavourite.backgroundTintList = getColorStateList(context, android.R.color.holo_red_dark);
                flag = false;
                onItemFavouriteClick?.invoke(favourite)
            } else {
                    Log.d("Favourite", "onBindViewHolder: aca ELIMINO la receta como fav")
                item.statusFav = false;
                holder.btnFavourite.backgroundTintList = getColorStateList(context, android.R.color.darker_gray);
                flag = true;
                onItemNOTFavouriteClick?.invoke(favourite)
            }
        }
    }

    override fun getItemCount(): Int {
        return items.size
    }

    fun update(recipes_new: MutableList<Recipe>) {
        items = recipes_new
        this.notifyDataSetChanged()
    }

}