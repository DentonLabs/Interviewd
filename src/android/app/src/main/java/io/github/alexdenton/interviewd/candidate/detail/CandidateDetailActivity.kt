package io.github.alexdenton.interviewd.candidate.detail

import android.os.Bundle
import android.widget.TextView
import io.github.alexdenton.interviewd.R
import io.github.alexdenton.interviewd.entities.Candidate
import io.github.rfonzi.rxaware.RxAwareActivity

class CandidateDetailActivity : RxAwareActivity() {

    lateinit var firstName: TextView
    lateinit var lastName: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_candidate_detail)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val candidate = receive() as Candidate

        firstName = findViewById(R.id.candidateDetail_firstName)
        lastName = findViewById(R.id.candidateDetail_lastName)

        firstName.text = candidate.firstName
        lastName.text = candidate.lastName
    }
}
