package com.example.cookit

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper

class Splash : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
    }

    override fun onStart() {
        super.onStart()

        var usuarioLogueado = true


        if (usuarioLogueado) {
            Handler(Looper.getMainLooper()).postDelayed({
                var intent = Intent(this, Home::class.java)
                startActivity(intent)
                finish()
            },1000)
        }
    }
}