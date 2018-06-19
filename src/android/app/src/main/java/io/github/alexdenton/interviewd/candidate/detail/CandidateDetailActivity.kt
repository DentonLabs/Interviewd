package io.github.alexdenton.interviewd.candidate.detail

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.MenuItem
import io.github.alexdenton.interviewd.R
import io.github.alexdenton.interviewd.candidate.CandidateFormFragment
import io.github.rfonzi.rxaware.RxAwareActivity

class CandidateDetailActivity : RxAwareActivity() {

    var candidateId: Long = 0
    var editing = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_candidate_detail)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        candidateId = receive() as Long

        if (savedInstanceState == null) {
            fragmentTransaction {
                add(R.id.candidateDetail_fragmentContainer, CandidateDetailFragment().withId())
            }
        }

        onPost<CandidateDetailRouter>()
                .subscribe {
                    when (it) {
                        is ShowCandidateDetailFragment -> switchToDetail()
                        is ShowCandidateFormFragment -> switchToEdit()
                    }
                }
                .lifecycleAware()
    }

    fun switchToDetail(){
        fragmentTransaction {
            replace(R.id.candidateDetail_fragmentContainer, CandidateDetailFragment().withId())
        }
        editing = false
    }

    fun switchToEdit(){
        fragmentTransaction {
            replace(R.id.candidateDetail_fragmentContainer, CandidateFormFragment().withId())
        }
        editing = true
    }

    fun Fragment.withId(): Fragment{
        this.arguments = Bundle().apply { putLong("candidateId", candidateId) }
        return this
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            android.R.id.home -> return when(editing){
                true -> {
                    switchToDetail()
                    true
                }
                false -> {
                    navigateUp()
                    true
                }
            }
        }

        return super.onOptionsItemSelected(item)
    }
}
