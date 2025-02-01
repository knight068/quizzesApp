package com.example.firasquizzes.API

import com.example.firasquizzes.models.ApiTest
import com.example.firasquizzes.models.QuizzesResponse
import com.example.firasquizzes.models.quizAnswerResponse
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.GET
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface Api {
// get all quizzes without any filter
    @GET("quizzes")
    fun getAllQuizzes(
    ): Call<QuizzesResponse>
//get all quizzes with a query filter
    @GET("quizzes")
    fun getAllQuizzes(
        @Query("category") category: String? = null
    ): Call<QuizzesResponse>

    @GET("quizzes/apiTest")
    fun getApiTest(
    ): Call<ApiTest>

    //get english quizzes without any filter
    @GET("quizzes/englishQuizzes")
    fun getEnglishQuizzes(
    ): Call<QuizzesResponse>

    //get english Quizzes with a category  query filter
    @GET("quizzes/englishQuizzes")
    fun getEnglishQuizzes(
        @Query("category") category: String? = null
    ): Call<QuizzesResponse>

// get all arabic quizzes without any filters
    @GET("quizzes/arabicQuizzes")
    fun getArabicQuizzes(
    ): Call<QuizzesResponse>

//get arabic Quizzes with a query filter
    @GET("quizzes/arabicQuizzes")
    fun getArabicQuizzes(
        @Query("category") category: String? = null
    ): Call<QuizzesResponse>



    @FormUrlEncoded
    @POST("quizzes/answer/{id}")
    fun answerQuiz(
        @Path("id")id:String,
        @Field("answer")answer:Int
    ): Call<quizAnswerResponse>
}