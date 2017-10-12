package io.github.alexdenton.interviewd.candidate.create

import android.os.Bundle
import io.github.alexdenton.interviewd.R
import io.github.alexdenton.interviewd.candidate.CandidateFormFragment
import io.github.rfonzi.rxaware.RxAwareActivity

class CreateCandidateActivity : RxAwareActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_candidate)
        actionBar?.setDisplayHomeAsUpEnabled(true)

        if(savedInstanceState == null) fragmentTransaction {
            add(R.id.createCandidate_fragmentContainer, CandidateFormFragment())
        }
    }

}
