package io.github.alexdenton.interviewd.candidate.detail


import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.*
import android.widget.TextView

import io.github.alexdenton.interviewd.R
import io.github.alexdenton.interviewd.entities.Candidate
import io.github.rfonzi.rxaware.RxAwareFragment


/**
 * A simple [Fragment] subclass.
 */
class CandidateDetailShowFragment : RxAwareFragment() {

    lateinit var vm: CandidateDetailViewModel

    lateinit var firstName: TextView
    lateinit var lastName: TextView

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view = inflater!!.inflate(R.layout.fragment_candidate_detail_show, container, false)
        (activity as CandidateDetailActivity).supportActionBar?.setDisplayHomeAsUpEnabled(true)
        vm = ViewModelProviders.of(activity).get(CandidateDetailViewModel::class.java)

        firstName = view.findViewById(R.id.candidateDetail_firstName)
        lastName = view.findViewById(R.id.candidateDetail_lastName)

        vm.getCandidateObservable()
                .subscribe { setupCandidate(it) }
                .lifecycleAware()

        setHasOptionsMenu(true)

        return view
    }

    private fun setupCandidate(candidate: Candidate) {
        firstName.text = candidate.firstName
        lastName.text = candidate.lastName
    }

    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        inflater?.inflate(R.menu.menu_edit, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when(item?.itemId){
            R.id.menu_edit -> vm.startEditingCandidate()
        }

        return super.onOptionsItemSelected(item)
    }

}
