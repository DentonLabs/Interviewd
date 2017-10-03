package io.github.alexdenton.interviewd.candidate.detail


import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.*
import android.widget.EditText
import com.jakewharton.rxbinding2.widget.textChanges

import io.github.alexdenton.interviewd.R
import io.github.alexdenton.interviewd.entities.Candidate
import io.github.rfonzi.rxaware.RxAwareFragment


/**
 * A simple [Fragment] subclass.
 */
class CandidateDetailEditFragment : RxAwareFragment() {

    lateinit var vm: CandidateDetailViewModel
    lateinit var firstNameEdit: EditText
    lateinit var lastNameEdit: EditText

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view = inflater!!.inflate(R.layout.fragment_candidate_detail_edit, container, false)
        (activity as CandidateDetailActivity).supportActionBar?.setDisplayHomeAsUpEnabled(false)
        vm = ViewModelProviders.of(activity).get(CandidateDetailViewModel::class.java)

        firstNameEdit = view.findViewById(R.id.candidateDetail_firstNameEdit)
        lastNameEdit = view.findViewById(R.id.candidateDetail_lastNameEdit)

        if(savedInstanceState == null){
            vm.getCandidateObservable()
                    .subscribe { setCandidate(it) }
                    .lifecycleAware()
        }

        vm.exposeFirstNameChanges(firstNameEdit.textChanges())
        vm.exposeLastNameChanges(lastNameEdit.textChanges())

        setHasOptionsMenu(true)

        return view
    }

    fun setCandidate(candidate: Candidate){
        firstNameEdit.setText(candidate.firstName)
        lastNameEdit.setText(candidate.lastName)
    }

    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        inflater?.inflate(R.menu.menu_submit_cancel, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when(item?.itemId){
            R.id.menu_submit -> vm.submitEdit()
            R.id.menu_cancel -> vm.stopEditingCandidate()
        }

        return super.onOptionsItemSelected(item)
    }

}
