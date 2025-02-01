package com.example.firasquizzes

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast

private lateinit var intent:Intent
class LanguageSelectActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_language_select)
        intent=Intent(this,QuizzesActivity::class.java)



        // Buttons init
        var btn_mixed = findViewById<Button>(R.id.btn_mixed)
        var btn_Englis = findViewById<Button>(R.id.btn_English)
        var btn_Arabic = findViewById<Button>(R.id.btn_Arabic)

        //Buttons OnClicked
        btn_Arabic.setOnClickListener {
            intent.putExtra("selectedLanguage","Arabic")
            selectCategory()
        }
        btn_Englis.setOnClickListener {
            intent.putExtra("selectedLanguage","English")
            selectCategory()
        }
        btn_mixed.setOnClickListener {
            intent.putExtra("selectedLanguage","Mixed")
            selectCategory()
        }

    }


    private fun selectCategory(){
        setContentView(R.layout.category_selector)

        //second screen button init
        var btn_Movie = findViewById<Button>(R.id.btn_Movie)
        var btn_Trivia = findViewById<Button>(R.id.btn_Trivia)
        var btn_Sports = findViewById<Button>(R.id.btn_Sports)
        var btn_Mixed = findViewById<Button>(R.id.btn_Mixed)

        //selecting the category and starting the activity
        btn_Movie.setOnClickListener {
            intent.putExtra("selected category","Movie")
            startActivity(intent)
        }
        btn_Trivia.setOnClickListener {
            intent.putExtra("selected category","Trivia")
            startActivity(intent)

        }
        btn_Sports.setOnClickListener {
            intent.putExtra("selected category","Sports")
            startActivity(intent)

        }
        btn_Mixed.setOnClickListener {
            intent.putExtra("selected category","")
            startActivity(intent)

        }

    }
}