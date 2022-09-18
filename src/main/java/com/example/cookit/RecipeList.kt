package com.example.cookit

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView

class RecipeList : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recipe_list)

        val btnHome = findViewById<Button>(R.id.btnHome)
        btnHome.setOnClickListener{
            cambioPantallaHome()
        }
    }



    override fun onStart() {
        super.onStart()
        val titulo = intent.extras?.getString("Titulo")
        val titulotext = findViewById<TextView>(R.id.lblTitulo)
        titulotext.text = titulo;
    }

    fun cambioPantallaHome () {
        var intent = Intent(this, Home::class.java)
        startActivity(intent)
        finish()
    }
}