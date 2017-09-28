package io.github.alexdenton.interviewd.detailquestion

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import io.github.alexdenton.interviewd.R
import io.github.alexdenton.interviewd.question.Question
import io.github.rfonzi.rxaware.BaseActivity

class QuestionDetailActivity : BaseActivity() {

    lateinit var nameText: TextView
    lateinit var descText: TextView
    lateinit var estText: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_question_detail)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val question = receive() as Question

        nameText = findViewById(R.id.questionDetail_name)
        descText = findViewById(R.id.questionDetail_description)
        estText = findViewById(R.id.questionDetail_estimate)

        nameText.text = question.name
        descText.text = question.description
        estText.text = resources.getString(R.string.est, question.timeEstimate)
    }
}
