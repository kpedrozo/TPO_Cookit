package com.example.cookit.data

import android.content.Context
import android.util.Log
import com.bumptech.glide.load.engine.Resource
import com.example.cookit.models.Recipe
import com.example.cookit.models.RecipeDetailModel
import com.example.cookit.models.RecipeEntity
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class APIService {
    companion object {

        val BASE_URL = "https://api.spoonacular.com/"
        val apiKey = "502e29b08c5b48ee9b92ebd598c8ee8b"
        val cantRecetas = 100;

        val TAG = "Favourite"

        val db = Firebase.firestore
        val myDB = db.collection("recetas")


        suspend fun getRecipes (context: Context) : ArrayList<Recipe>{
            val retrofit = Retrofit.Builder().baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()

            val apiEndpoint = retrofit.create(RecipesAPI::class.java)
            val result = apiEndpoint.getRecipes(apiKey, cantRecetas).execute()

            if (result.isSuccessful) {
                return result.body()!!.results
            } else {
                Log.e("Api-Service", "Error al comunicar con la API")
                return ArrayList<Recipe>()
            }
        }

        suspend fun insertRecipeFavourite ( context: Context, recipe : RecipeEntity) {
            Log.d(TAG, "insertRecipeFavourite: aca deberia insertarse en favoritos")
            val room = RoomDataBase.getInstance(context).recipeDao().insertRecipe(recipe)
            var recetaFavorita = recipe
            val receta = hashMapOf(
                "title" to recetaFavorita.title,
                "image" to recetaFavorita.img
            )
            myDB.document("${recetaFavorita.id}")
                .set(receta)
                .addOnSuccessListener {
                    Log.d(TAG, "insertRecipeFavourite: receta favorita guardada en Firestore")
                }
                .addOnFailureListener { e ->
                    Log.d(TAG, "insertRecipeFavourite: Error: Receta favorita sin guardar en Firestore", e)
                }
            return room;
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

        suspend fun getRecipesFavourite(context: Context): MutableList<RecipeEntity> {
            val room = RoomDataBase.getInstance(context).recipeDao().fetchAll()
            return room;
        }

        suspend fun deleteRecipeFavourite(context: Context, id: Int) {
            val room = RoomDataBase.getInstance(context).recipeDao().deleteByID(id)
            myDB.document("${id}")
                .delete()
                .addOnSuccessListener {
                    Log.d(TAG, "insertRecipeFavourite: receta favorita ELIMINADA de Firestore")
                }
                .addOnFailureListener { e ->
                    Log.d(TAG, "insertRecipeFavourite: Error: Receta favorita sin borrar de Firestore", e)
                }
            return room;
        }

        suspend fun selectRecipeFavouriteByID(context: Context, id: Int): RecipeEntity {
            val room = RoomDataBase.getInstance(context).recipeDao().selectByID(id)
            myDB.document("${id}")
                .get()
                .addOnSuccessListener {
                    Log.d(TAG, "selectRecipeFavouriteByID: get receta favorita by ID desde Firestore")
                }
                .addOnFailureListener { e ->
                    Log.d(TAG, "selectRecipeFavouriteByID: error al buscar receta por id en Firestore", e)
                }
            return room;
        }
    }
}