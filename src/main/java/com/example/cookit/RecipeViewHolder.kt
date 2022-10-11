package com.example.cookit

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class RecipeViewHolder (itemView: View) : RecyclerView.ViewHolder (itemView) {
    val img : ImageView = itemView.findViewById(R.id.imgRecipe)
    val title : TextView = itemView.findViewById(R.id.txtRecipeTitle)

}