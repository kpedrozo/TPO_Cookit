package com.example.cookit.data

import android.content.Context
import com.example.cookit.models.Recipe
import com.example.cookit.models.ResponseAPI

class MainRepository {
    companion object {
        //suspend fun fetchData (context: Context) : ArrayList<Recipe> {
            suspend fun fetchData (context: Context) : ResponseAPI {

             return APIService.fetchData(context)
        }
    }
}