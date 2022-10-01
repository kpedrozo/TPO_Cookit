package com.example.cookit

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText

class Register : AppCompatActivity() {

    private lateinit var txtName : EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
    }
}