package com.example.cookit.data

import android.content.Context
import android.util.Log
import com.example.cookit.models.Recipe
import com.example.cookit.models.RecipeDetailModel
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class APIService {
    companion object {

        val BASE_URL = "https://api.spoonacular.com/"
        val apiKey = "502e29b08c5b48ee9b92ebd598c8ee8b"
        val cantRecetas = 100;


        val db = Firebase.firestore
        val myDB = db.collection("recetas")



        suspend fun getRecipes (context: Context) : ArrayList<Recipe>{
            val retrofit = Retrofit.Builder().baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()

            val apiEndpoint = retrofit.create(RecipesAPI::class.java)
            val result = apiEndpoint.getRecipes(apiKey, cantRecetas).execute()


            if (result.isSuccessful) {
                var PrimerReceta = result.body()!!.results[4]

                val receta = hashMapOf(
                    "title" to PrimerReceta.title,
                    "image" to PrimerReceta.image,
                    "imageType" to PrimerReceta.imageType
                )

                Log.d("Receta save", "Titulo receta : ${PrimerReceta.title}")
                myDB.document("${PrimerReceta.id}")
                    .set(receta)
                        .addOnSuccessListener {
                            Log.d("Receta save", "receta guardada en DB")
                        }
                        .addOnFailureListener { e ->
                            Log.w("Receta save", "Error receta sin guardar en DB", e)
                        }

                myDB
                    .get()
                    .addOnSuccessListener { result ->
                        for (document in result) {
                            Log.d("Receta save", "Receta leida de DB : ${document.id} => ${document.data} ")
                        }
                    }
                    .addOnFailureListener { exception ->
                        Log.w("Receta save", "Error: Receta no existe en DB.", exception)
                    }

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