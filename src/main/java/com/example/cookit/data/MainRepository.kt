package com.example.cookit.data

import android.content.Context
import com.example.cookit.models.Recipe

class MainRepository {
    companion object {
        suspend fun fetchData (context: Context) : ArrayList<Recipe> {
             return APIService.fetchData(context)
        }
    }
}