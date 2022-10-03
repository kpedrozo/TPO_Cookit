package com.example.cookit

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.cookit.models.Ingredients
import com.example.cookit.models.Recipe
import com.google.gson.annotations.SerializedName

class RecipeDetailViewHolder (itemView: View) : RecyclerView.ViewHolder (itemView) {

    val title : TextView = itemView.findViewById(R.id.txtTituloReceta)
    val img : ImageView = itemView.findViewById(R.id.imgRecipe)
    val ingredients : TextView = itemView.findViewById(R.id.txtIngredients)
    val instruccions : TextView = itemView.findViewById(R.id.txtInstruccions)

}