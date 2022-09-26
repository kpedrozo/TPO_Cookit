package com.example.cookit.models

import com.google.gson.annotations.SerializedName

data class RecipeDetail (
    val id : Int,
    val title : String,
    val image : String,
    @SerializedName("extendedIngredients")
    val ingredients : ArrayList<Ingredients>,
    val summary : String
    )
