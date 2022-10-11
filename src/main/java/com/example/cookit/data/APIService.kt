package com.example.cookit.data

import android.content.Context
import android.util.Log
import com.example.cookit.models.Recipe
import com.example.cookit.models.RecipeDetailModel
import com.google.firebase.firestore.FirebaseFirestore
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class APIService {
    companion object {

        val BASE_URL = "https://api.spoonacular.com/"
        val apiKey = "502e29b08c5b48ee9b92ebd598c8ee8b"
        val cantRecetas = 100;


        private val db = FirebaseFirestore.getInstance()

        suspend fun getRecipes (context: Context) : ArrayList<Recipe>{
            val retrofit = Retrofit.Builder().baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()

            val apiEndpoint = retrofit.create(RecipesAPI::class.java)
            val result = apiEndpoint.getRecipes(apiKey, cantRecetas).execute()


            if (result.isSuccessful) {
                val receta = result.body()!!.results[0]
                Log.d("Receta save", "Titulo receta : ${receta.title}")
                db.collection("recetas").document(receta.id.toString()).set(
                    hashMapOf(
                        "title" to receta.title,
                        "image" to receta.image,
                        "imageType" to receta.imageType ))
                        .addOnSuccessListener { Log.d("Receta save", "DocumentSnapshot successfully written!") }
                        .addOnFailureListener { e -> Log.w("Receta save", "Error writing document", e) }

                return result.body()!!.results
            } else {
                Log.e("Api-Service", "Error al comunicar con la API")
                return ArrayList<Recipe>()
            }
        }

        suspend fun getRecipebyID (context: Context, id : Int) : RecipeDetailModel {
            val retrofit = Retrofit.Builder().baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()

            val apiEndpoint = retrofit.create(RecipesAPI::class.java)
            val result = apiEndpoint.getRecipebyID(id , apiKey).execute()

            return if (result.isSuccessful) {
                result.body()!!
            } else {
                val response = RecipeDetailModel(0,"","", ArrayList(),"")
                return response
            }
        }
    }
}