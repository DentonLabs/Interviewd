package io.github.alexdenton.interviewd

import android.os.Bundle
import android.support.v4.app.NavUtils
import android.support.v7.app.AppCompatActivity
import android.view.MenuItem
import android.widget.Button
import android.widget.EditText
import io.github.alexdenton.interviewd.api.QuestionRetrofitRepository

class CreateQuestionActivity : AppCompatActivity() {

    lateinit var presenter: CreateQuestionPresenter
    lateinit var nameField: EditText
    lateinit var durField: EditText
    lateinit var descField: EditText
    lateinit var submitButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_question)

        actionBar?.setDisplayHomeAsUpEnabled(true)

        presenter = CreateQuestionPresenter(this, QuestionRetrofitRepository(this))
        nameField = findViewById(R.id.createQuestionNameField)
        durField = findViewById(R.id.createQuestionDurationField)
        descField = findViewById(R.id.createQuestionDescField)
        submitButton = findViewById(R.id.createQuestionSubmitButton) // TODO: Use a different naming convention for ids

        submitButton.setOnClickListener { presenter.submitQuestion() }
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            R.id.home -> {
                NavUtils.navigateUpFromSameTask(this)
                return true
            }
            else -> return super.onOptionsItemSelected(item)
        }
    }
}
