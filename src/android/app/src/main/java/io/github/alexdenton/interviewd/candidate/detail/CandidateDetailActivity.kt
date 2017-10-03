package io.github.alexdenton.interviewd.candidate.detail

import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.widget.TextView
import com.github.salomonbrys.kodein.LazyKodein
import com.github.salomonbrys.kodein.android.appKodein
import io.github.alexdenton.interviewd.R
import io.github.alexdenton.interviewd.entities.Candidate
import io.github.rfonzi.rxaware.RxAwareActivity

class CandidateDetailActivity : RxAwareActivity() {

    lateinit var vm: CandidateDetailViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_candidate_detail)
        vm = ViewModelProviders.of(this).get(CandidateDetailViewModel::class.java)
        vm.initWith(LazyKodein(appKodein), receive() as Int)

        if(savedInstanceState == null){
            fragmentTransaction {
                add(R.id.candidateDetail_fragmentContainer, CandidateDetailShowFragment())
            }
        }
    }
}
