package com.example.semesterproject1.api

import com.example.firasquizzes.API.Api
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


object RetrofitClient {
    private const val Base_Url ="http://10.0.2.2:3000/api/v1/"

    val instance : Api by lazy{
        val retrofit = Retrofit.Builder()
            .baseUrl(Base_Url)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        retrofit.create(Api::class.java)
    }
}