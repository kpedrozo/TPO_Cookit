package com.example.cookit.models

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface RecipeDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRecipe(item: RecipeEntity)

    @Query("SELECT * FROM recipes")
    suspend fun fetchAll() : MutableList<RecipeEntity>
}