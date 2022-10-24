package com.example.cookit.data

import android.content.Context
import com.bumptech.glide.load.engine.Resource
import com.example.cookit.models.Recipe
import com.example.cookit.models.RecipeDetailModel
import com.example.cookit.models.RecipeEntity

class MainRepository {
    companion object {
        suspend fun getRecipes (context: Context) : ArrayList<Recipe> {
            return APIService.getRecipes(context)
        }

        suspend fun getRecipebyID (context: Context, id: Int?) : RecipeDetailModel {
            return APIService.getRecipebyID(context, id!!)
        }

        suspend fun getRecipesFromRoom (context: Context) : MutableList<RecipeEntity> {
            return APIService.getRecipesFavourite(context);
        }

        suspend fun deleteRecipeFromFavourite (context: Context, id: Int?) {
            return APIService.deleteRecipeFavourite(context, id!!)
        }

        suspend fun insertRecipeFavourite (context: Context, recipe: RecipeEntity) {
            return APIService.insertRecipeFavourite(context, recipe)
        }

    }
}