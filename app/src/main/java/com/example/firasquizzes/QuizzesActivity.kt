package com.example.firasquizzes

import android.content.Intent
import android.graphics.Color
import android.graphics.Typeface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.View
import android.view.View.OnClickListener
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import com.example.firasquizzes.models.QuizzesResponse
import com.example.firasquizzes.models.quizAnswerResponse
import com.example.semesterproject1.api.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

var selectedAnswer:Int =0
lateinit var tempRes: QuizzesResponse
lateinit var currentQuestionId:String
lateinit var tv_question: TextView
lateinit var progressBar:ProgressBar
lateinit var tv_progress:TextView
lateinit var tv_option_one:TextView
lateinit var tv_option_two:TextView
lateinit var tv_option_three:TextView
lateinit var tv_option_four:TextView
lateinit var btn_submit:Button
lateinit var options:ArrayList<TextView>
var currentPosition = 1
var answerPosition =99
var quizLanguage: String =""
var correctAnswerNumber = 0
var quizCategory=""


class QuizzesActivity : AppCompatActivity(),OnClickListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_quizzes_activiy)
        bindAllTheUiElements()
//        getAllQuizzes()
        getIntentExtra()

        //choosing the answer
        tv_option_one.setOnClickListener (this)
        tv_option_two.setOnClickListener (this)
        tv_option_three.setOnClickListener (this)
        tv_option_four.setOnClickListener (this)

        //submitting the answer
        btn_submit.setOnClickListener {
            if (answerPosition ==99){
                Toast.makeText(this,"Please select an answer",Toast.LENGTH_SHORT).show()
            }else {
            sendAnswer()
                if (currentPosition ==10){


                Handler().postDelayed({
                    var intent = Intent(this,ResultActivity::class.java)
                    intent.putExtra("correctAnswerNumber", correctAnswerNumber)
                    startActivity(intent)
                    currentPosition=1
                    finish()
                }, 2000)}
                if (currentPosition ==10){
                    var intent = Intent(this,ResultActivity::class.java)
                    intent.putExtra("correctAnswerNumber", correctAnswerNumber)
                    startActivity(intent)
                    currentPosition=1
                    finish()
                }
//                Log.i("current answer is ", answerPosition.toString())
            }
        }


    }

    private fun getIntentExtra() {
        quizLanguage = intent.getStringExtra("selectedLanguage").toString()
        Log.i("selected Language is", quizLanguage)
        quizCategory = intent.getStringExtra("selected category").toString().toLowerCase()
        Log.i("selected category is", quizCategory)
        if(quizCategory=="") {
            when (quizLanguage) {
                "Arabic" -> {
                    getArabicQuizzes()
                }

                "English" -> {
                    getEnglishQuizzes()
                }

                "Mixed" -> {
                    getAllQuizzes()
                }
            }
        }else{
            when (quizLanguage) {
                "Arabic" -> {
                    getArabicQuizzes(quizCategory)
                }

                "English" -> {
                    getEnglishQuizzes(quizCategory)
                }

                "Mixed" -> {
                    getAllQuizzes(quizCategory)
                }
            }

        }
    }

    private fun sendAnswer() {
        RetrofitClient.instance.answerQuiz(currentQuestionId, answerPosition).enqueue(object: Callback<quizAnswerResponse>{
            override fun onResponse(
                call: Call<quizAnswerResponse>,
                response: Response<quizAnswerResponse>
            ) {
                if(response.body()!!.data.answer.toString() =="right"){
                    correctAnswerNumber+=1
                    options[answerPosition-1].background = ContextCompat.getDrawable(this@QuizzesActivity,R.drawable.correct_option_border_bg)
                    Handler().postDelayed({
                        currentPosition+=1
                        answerPosition=99
                        setQuiztionsAndProgress()
                    }, 2000)



                }else if(response.body()!!.data.answer.toString() =="wrong"){
                    options[answerPosition-1].background = ContextCompat.getDrawable(this@QuizzesActivity,R.drawable.wrong_option_border_bg)
                    options[response.body()!!.data.correctAnswerIndex].background = ContextCompat.getDrawable(this@QuizzesActivity,R.drawable.correct_option_border_bg)

                    Handler().postDelayed({
                        currentPosition+=1
                        answerPosition=99
                        setQuiztionsAndProgress()
                    }, 2000)
                }
                //Log.i("Answer Success", response.body().toString())
            }

            override fun onFailure(call: Call<quizAnswerResponse>, t: Throwable) {
                Log.i("answer Error",t.message.toString())
                Toast.makeText(this@QuizzesActivity,"sorry something went wrong",Toast.LENGTH_SHORT).show()
            }

        } )
    }




    private fun bindAllTheUiElements() {
        tv_question=findViewById(R.id.tv_question)
        btn_submit=findViewById(R.id.btn_submit)
        tv_option_four=findViewById(R.id.tv_option_four)
        tv_option_three=findViewById(R.id.tv_option_three)
        tv_option_two=findViewById(R.id.tv_option_two)
        tv_option_one=findViewById(R.id.tv_option_one)
        tv_progress=findViewById(R.id.tv_progress)
        progressBar=findViewById(R.id.progressBar)



    }

    private fun defaultOptionView() {
        options = ArrayList<TextView>()
        options.add(0, tv_option_one)
        options.add(1, tv_option_two)
        options.add(2, tv_option_three)
        options.add(3, tv_option_four)

        for (i in options){
            i.setTextColor(Color.parseColor("#414447"))
            i.typeface= Typeface.DEFAULT
            i.background=ContextCompat.getDrawable(this,R.drawable.default_option_border_bg)
        }
}
    //function for getting all quizzes without any filters
    fun getAllQuizzes(){

    RetrofitClient.instance.getAllQuizzes().enqueue(object : Callback<QuizzesResponse>{
        override fun onResponse(call: Call<QuizzesResponse>, response: Response<QuizzesResponse>) {
            tempRes = response.body()!!
            Log.i("quizResponse",tempRes.toString())
            setQuiztionsAndProgress()

        }

        override fun onFailure(call: Call<QuizzesResponse>, t: Throwable) {
            Log.i("Retrofit Error",t.message.toString())
            errorToast()
        }

    })
}

    //function for getting all quizzes with a query filter
    fun getAllQuizzes(category: String){
        RetrofitClient.instance.getAllQuizzes(category).enqueue(object : Callback<QuizzesResponse>{
            override fun onResponse(call: Call<QuizzesResponse>, response: Response<QuizzesResponse>) {
                tempRes = response.body()!!
                Log.i("quizResponse",tempRes.toString())
                setQuiztionsAndProgress()

            }

            override fun onFailure(call: Call<QuizzesResponse>, t: Throwable) {
                Log.i("Retrofit Error",t.message.toString())
                errorToast()
            }

        })
    }

    //function for getting all Arabic Quizzes using retrofit without query filter
    private fun getArabicQuizzes(){
        RetrofitClient.instance.getArabicQuizzes().enqueue(object : Callback<QuizzesResponse>{
            override fun onResponse(call: Call<QuizzesResponse>, response: Response<QuizzesResponse>) {
                tempRes = response.body()!!
                Log.i("quizResponse",tempRes.toString())
                setQuiztionsAndProgress()

            }

            override fun onFailure(call: Call<QuizzesResponse>, t: Throwable) {
                Log.i("Retrofit Error",t.message.toString())
                errorToast()
            }

        })
    }

    //function for getting all Arabic Quizzes using retrofit with query filter
    private fun getArabicQuizzes(category:String){
        RetrofitClient.instance.getArabicQuizzes(category).enqueue(object : Callback<QuizzesResponse>{
            override fun onResponse(call: Call<QuizzesResponse>, response: Response<QuizzesResponse>) {
                tempRes = response.body()!!
                Log.i("quizResponse",tempRes.toString())
                setQuiztionsAndProgress()

            }

            override fun onFailure(call: Call<QuizzesResponse>, t: Throwable) {
                Log.i("Retrofit Error",t.message.toString())
                errorToast()
            }

        })
    }

    //function for getting english Quizzes using Retrofit wihtou any filters
    private fun getEnglishQuizzes() {
        RetrofitClient.instance.getEnglishQuizzes().enqueue(object : Callback<QuizzesResponse>{
            override fun onResponse(call: Call<QuizzesResponse>, response: Response<QuizzesResponse>) {
                tempRes = response.body()!!
                Log.i("quizResponse",tempRes.toString())
                setQuiztionsAndProgress()

            }

            override fun onFailure(call: Call<QuizzesResponse>, t: Throwable) {
                Log.i("Retrofit Error",t.message.toString())
                errorToast()
            }

        })
    }

    //getting english quizzes with a category filter
    private fun getEnglishQuizzes(category: String) {
        RetrofitClient.instance.getEnglishQuizzes(category).enqueue(object : Callback<QuizzesResponse>{
            override fun onResponse(call: Call<QuizzesResponse>, response: Response<QuizzesResponse>) {
                tempRes = response.body()!!
                Log.i("quizResponse",tempRes.toString())
                setQuiztionsAndProgress()

            }

            override fun onFailure(call: Call<QuizzesResponse>, t: Throwable) {
                Log.i("Retrofit Error",t.message.toString())
                errorToast()
            }

        })
    }

    private fun setQuiztionsAndProgress() {
    progressBar.progress = currentPosition
    progressBar.max=10
    tv_progress.text =( "$currentPosition" +"/"+ progressBar.max)
    var currentQuestionIndex = tempRes.data.data.random()
    currentQuestionId= currentQuestionIndex._id
    tv_question.text=currentQuestionIndex.question.toString()
    tv_option_one.text=currentQuestionIndex.answers[0].text
    tv_option_two.text=currentQuestionIndex.answers[1].text
    tv_option_three.text=currentQuestionIndex.answers[2].text
    tv_option_four.text=currentQuestionIndex.answers[3].text
    defaultOptionView()

}

    override fun onClick(v: View?) {
        when(v?.id){
            R.id.tv_option_one->{
                selectedOptionView(tv_option_one,1)
            }
            R.id.tv_option_two->{
                selectedOptionView(tv_option_two,2)
            }
            R.id.tv_option_three->{
                selectedOptionView(tv_option_three,3)
            }
            R.id.tv_option_four->{
                selectedOptionView(tv_option_four,4)
            }
        }
    }
    private fun selectedOptionView(tv:TextView,selectedNum:Int){
        defaultOptionView()
        tv.setTextColor(Color.parseColor("#363a43"))
        tv.setTypeface(tv.typeface,Typeface.BOLD)
        tv.background=ContextCompat.getDrawable(this,R.drawable.selected_option_border_bg)
        answerPosition=selectedNum


    }
    private fun errorToast(){
        Toast.makeText(this@QuizzesActivity,"Sorry something went wrong \n please check you Internet connection",Toast.LENGTH_SHORT).show()
    }


}