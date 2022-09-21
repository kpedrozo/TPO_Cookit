package com.example.cookit.data

import android.content.Context
import android.util.Log
import com.example.cookit.models.Recipe
import com.example.cookit.models.RecipeDetail
import com.example.cookit.models.ResponseAPI
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class APIService {
    companion object {

        val BASE_URL = "https://api.spoonacular.com/"
        val apiKey = "502e29b08c5b48ee9b92ebd598c8ee8b"
        val idRecipe = 632241 // valor a cambiar!!!!!!!!!

        suspend fun getRecipes (context: Context) : ResponseAPI{
            val retrofit = Retrofit.Builder().baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()

            val apiEndpoint = retrofit.create(RecipesAPI::class.java)
            val result = apiEndpoint.getRecipes(apiKey).execute()


            return if (result.isSuccessful) {
                result.body()!!
            } else {
                Log.e("Api-Service", "Error al comunicar con la API")
                val response = ResponseAPI(ArrayList<Recipe>(),0,0,0)
                return response
            }


        }

        suspend fun getRecipebyID (context: Context) : RecipeDetail {
            val retrofit = Retrofit.Builder().baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()

            val apiEndpoint = retrofit.create(RecipesAPI::class.java)
            val result = apiEndpoint.getRecipebyID(idRecipe, apiKey).execute()


            return if (result.isSuccessful) {
                result.body()!!
            } else {
                Log.e ("Api-Service", "No se encuentra la receta")
                val response = RecipeDetail(0,0,0)
                return response
            }
        }

    }
}