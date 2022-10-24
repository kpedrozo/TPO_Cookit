package com.example.cookit.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "recipes")
data class RecipeEntity (
    @PrimaryKey @ColumnInfo(name = "id") val id : Int,
    @ColumnInfo(name = "title") val title : String,
    @ColumnInfo(name = "img") val img : String,
    @ColumnInfo(name = "statusFav") var statusFav : Boolean,
   // @ColumnInfo(name = "imageType") val imageType: String
)