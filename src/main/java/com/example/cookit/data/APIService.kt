package com.example.cookit.data

import android.content.Context
import android.util.Log
import com.example.cookit.models.Recipe
import com.example.cookit.models.ResponseAPI
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class APIService {
    companion object {
        val BASE_URL = "https://api.spoonacular.com/recipes/complexSearch/"

        //suspend fun fetchData (context: Context) : ArrayList<Recipe> {
            suspend fun fetchData (context: Context) : ResponseAPI{
            val retrofit = Retrofit.Builder().baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()

            val apiEndpoint = retrofit.create(RecipesAPI::class.java)
            val result = apiEndpoint.getRecipes("").execute()


            return if (result.isSuccessful) {
                result.body()!!
            } else {
                Log.e("Api-Service", "Error al comunicar con la API")
                // ArrayList<Recipe>()
                val response = ResponseAPI(ArrayList<Recipe>(),0,0,0)
                return response
            }
        }
    }
}