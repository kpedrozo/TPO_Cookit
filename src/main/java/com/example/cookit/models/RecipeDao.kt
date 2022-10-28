package com.example.cookit.models

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface RecipeDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRecipe(item: RecipeEntity)

    @Query("SELECT * FROM recipes WHERE user = :user")
    suspend fun fetchAll(user : String) : MutableList<RecipeEntity>

    @Query("DELETE FROM recipes WHERE id = :id")
    suspend fun deleteByID(id : Int)

    @Query("SELECT * FROM recipes WHERE id = :id")
    suspend fun selectByID(id : Int) : RecipeEntity

}