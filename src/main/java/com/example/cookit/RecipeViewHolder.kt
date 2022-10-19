package com.example.cookit

import android.view.View
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class RecipeViewHolder (itemView: View) : RecyclerView.ViewHolder (itemView) {
    val img : ImageView = itemView.findViewById(R.id.imgRecipe)
    val title : TextView = itemView.findViewById(R.id.txtRecipeTitle)
//    val btnFavouriteRcp : ImageButton = itemView.findViewById(R.id.btnFavouriteRcp)

}