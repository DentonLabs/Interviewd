package io.github.alexdenton.interviewd

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.NavUtils
import android.view.MenuItem
import android.widget.Button

class CreateQuestionActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_question)

        actionBar?.setDisplayHomeAsUpEnabled(true)

        val submitButton: Button = findViewById(R.id.createQuestionSubmitButton) // TODO: Use a different naming convention for ids

        submitButton.setOnClickListener {
            // Do stuff
            finish()
        }
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when(item?.itemId){
            R.id.home -> {
                NavUtils.navigateUpFromSameTask(this)
                return true
            }
            else -> return super.onOptionsItemSelected(item)
        }
    }
}
