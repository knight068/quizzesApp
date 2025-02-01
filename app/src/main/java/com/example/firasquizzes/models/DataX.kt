package com.example.firasquizzes.models

data class DataX(
    val __v: Int,
    val _id: String,
    val answers: List<Answer>,
    val category: String,
    val difficulty: String,
    val language: String,
    val question: String,
    val slug: String
)