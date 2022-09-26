package com.example.cookit.data

import android.content.Context
import com.example.cookit.models.Recipe
import com.example.cookit.models.RecipeDetail
import com.example.cookit.models.ResponseAPI

class MainRepository {
    companion object {
        suspend fun getRecipes (context: Context) : ArrayList<Recipe> {
            return APIService.getRecipes(context)
        }

//        suspend fun getRecipebyID (context: Context) : RecipeDetail {
//            return APIService.getRecipebyID(context)
//        }
    }
}