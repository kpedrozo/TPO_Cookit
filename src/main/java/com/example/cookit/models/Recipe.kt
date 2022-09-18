package com.example.cookit.models

data class Recipe (
    val domains: ArrayList<String>,
    val alpha_two_code: String,
    val country: String,
    val web_pages: ArrayList<String>,
    val name: String,
)