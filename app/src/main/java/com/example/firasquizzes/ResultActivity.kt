package com.example.firasquizzes

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView

class ResultActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_result)
        var btn_finish = findViewById<Button>(R.id.btn_finish)
        btn_finish.setOnClickListener {
            var intent = Intent(this,LanguageSelectActivity::class.java)
            startActivity(intent)
            finish()
        }
        var correctAnswerNumber= intent.getIntExtra("correctAnswerNumber",0)
        Log.i("correctAnswerNumber",correctAnswerNumber.toString())

        var tv_score = findViewById<TextView>(R.id.tv_score)
        tv_score.setText("Your Score is ${correctAnswerNumber} out of 10")
    }
}