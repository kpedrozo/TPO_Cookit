package com.example.cookit.data

import com.example.cookit.models.Recipe
import com.example.cookit.models.RecipeDetail
import com.example.cookit.models.ResponseAPI
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path
import retrofit2.http.Query

interface RecipesAPI {

    @GET("/recipes/complexSearch")
    fun getRecipes (@Header("x-api-key") apiKey : String) : Call<ResponseAPI>;


    @GET("/recipes/{id}/information/")
    fun getRecipebyID (@Path("id") id : Int,
                       @Header ("x-api-key") apiKey : String)
            : Call<RecipeDetail>

}