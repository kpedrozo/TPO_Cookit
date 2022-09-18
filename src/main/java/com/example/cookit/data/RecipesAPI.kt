package com.example.cookit.data

import com.example.cookit.models.Recipe
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface RecipesAPI {

    @GET("/search")
    fun getRecipes(@Query("name") query : String) : Call<ArrayList<Recipe>>
}