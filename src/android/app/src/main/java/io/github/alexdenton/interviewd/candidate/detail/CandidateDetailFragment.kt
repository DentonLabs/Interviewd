package io.github.alexdenton.interviewd.candidate.detail


import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.*
import android.widget.TextView
import com.afollestad.materialdialogs.MaterialDialog
import com.github.salomonbrys.kodein.LazyKodein
import com.github.salomonbrys.kodein.android.appKodein

import io.github.alexdenton.interviewd.R
import io.github.alexdenton.interviewd.entities.Candidate
import io.github.rfonzi.rxaware.RxAwareFragment


/**
 * A simple [Fragment] subclass.
 */
class CandidateDetailFragment : RxAwareFragment() {

    lateinit var vm: CandidateDetailViewModel

    lateinit var firstName: TextView
    lateinit var lastName: TextView

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater!!.inflate(R.layout.fragment_candidate_detail, container, false)
        vm = ViewModelProviders.of(this).get(CandidateDetailViewModel::class.java)

        if (arguments?.containsKey("candidateId") == true) {
            vm.initWith(LazyKodein(appKodein), arguments.getLong("candidateId"))
        } else {
            throw IllegalStateException("Candidate id not found")
        }

        firstName = view.findViewById(R.id.candidateDetail_firstName)
        lastName = view.findViewById(R.id.candidateDetail_lastName)

        vm.fetchCandidate()
                .subscribe { candidate -> setupCandidate(candidate) }
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
        when (item?.itemId) {
            R.id.menu_edit -> vm.startEditingCandidate()
            R.id.menu_delete -> showDeleteConfirmation()
        }

        return super.onOptionsItemSelected(item)
    }

    fun showDeleteConfirmation() = MaterialDialog.Builder(context)
            .content("Are you sure you want to delete ${firstName.text} ${lastName.text}?")
            .positiveText("Okay")
            .negativeText("Cancel")
            .onPositive { dialog, which ->
                vm.deleteCandidate()
                        .subscribe { success -> onDeleteCandidate(success) }
                        .lifecycleAware()
            }
            .onNegative { dialog, which -> dialog.dismiss() }
            .build()
            .show()

    fun onDeleteCandidate(candidate: Candidate){
        toast("Deleted ${candidate.firstName} ${candidate.lastName}")
        navigateUp()
    }

}
