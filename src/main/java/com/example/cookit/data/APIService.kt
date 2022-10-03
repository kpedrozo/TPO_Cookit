package com.example.cookit.data

import android.content.Context
import android.util.Log
import com.example.cookit.models.Recipe
import com.example.cookit.models.RecipeDetailModel
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class APIService {
    companion object {

        val BASE_URL = "https://api.spoonacular.com/"
        val apiKey = "502e29b08c5b48ee9b92ebd598c8ee8b"
        //val idRecipe = 632241 // valor a cambiar!!!!!!!!!
        val cantRecetas = 100;

        suspend fun getRecipes (context: Context) : ArrayList<Recipe>{
            val retrofit = Retrofit.Builder().baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()

            val apiEndpoint = retrofit.create(RecipesAPI::class.java)
            val result = apiEndpoint.getRecipes(apiKey, cantRecetas).execute()


            return if (result.isSuccessful) {
                result.body()!!.results
            } else {
                Log.e("Api-Service", "Error al comunicar con la API")
//                val response = ResponseAPI(ArrayList<Recipe>(),0,0,0)
                return ArrayList<Recipe>()
            }


        }

        suspend fun getRecipebyID (context: Context, id : Int) : RecipeDetailModel {
            val retrofit = Retrofit.Builder().baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()

            val apiEndpoint = retrofit.create(RecipesAPI::class.java)
            Log.d("ID", "getRecipebyID: $id")
            val result = apiEndpoint.getRecipebyID(id , apiKey).execute()


            return if (result.isSuccessful) {
                result.body()!!
            } else {
                Log.e ("Api-Service", "No se encuentra la receta")
                val response = RecipeDetailModel(0,"","", ArrayList(),"")
                return response
            }
        }


    }
}