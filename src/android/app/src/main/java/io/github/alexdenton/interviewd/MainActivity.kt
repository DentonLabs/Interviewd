package io.github.alexdenton.interviewd

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val createQuestionButton: Button = findViewById(R.id.createQuestionButton)

        createQuestionButton.setOnClickListener {
            startActivity(Intent(this, CreateQuestionActivity::class.java))
        }
    }


}
