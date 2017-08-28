package io.github.alexdenton.interviewd.createcandidate

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.NavUtils
import android.view.MenuItem
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import io.github.alexdenton.interviewd.R
import io.github.alexdenton.interviewd.api.CandidateRetrofitRepository

class CreateCandidateActivity : AppCompatActivity() {

    lateinit var presenter: CreateCandidatePresenter

    lateinit var firstNameField: EditText
    lateinit var lastNameField: EditText
    lateinit var submitButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_candidate)
        actionBar?.setDisplayHomeAsUpEnabled(true)

        presenter = CreateCandidatePresenter(CandidateRetrofitRepository(applicationContext), this)

        firstNameField = findViewById(R.id.createCandidate_firstNameField)
        lastNameField = findViewById(R.id.createCandidate_lastNameField)
        submitButton = findViewById(R.id.createCandidate_submitButton)

        submitButton.setOnClickListener { presenter.submitCandidate() }
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

    fun onSuccessfulSubmit() {
        Toast.makeText(this, "Candidate Submitted Successfully", Toast.LENGTH_SHORT).show()
        finish()
    }
}
