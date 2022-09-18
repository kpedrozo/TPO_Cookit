package com.example.cookit.data

import android.content.Context
import android.util.Log
import com.example.cookit.models.Recipe
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class APIService {
    companion object {
        val BASE_URL = "http://universities.hipolabs.com/"

        suspend fun fetchData (context: Context) : ArrayList<Recipe> {
            val retrofit = Retrofit.Builder().baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()

            val apiEndpoint = retrofit.create(RecipesAPI::class.java)
            val result = apiEndpoint.getRecipes("").execute()


            return if (result.isSuccessful) {
                result.body()!!
            } else {
                Log.e("Api-Service", "Error al comunicar con la API")
                ArrayList<Recipe>()
            }
        }
    }
}