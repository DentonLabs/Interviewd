package io.github.alexdenton.interviewd.candidate

import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.design.widget.TextInputEditText
import android.support.design.widget.TextInputLayout
import android.view.*
import android.widget.Button
import com.github.salomonbrys.kodein.LazyKodein
import com.github.salomonbrys.kodein.android.appKodein
import com.jakewharton.rxbinding2.view.clicks
import com.jakewharton.rxbinding2.widget.textChanges
import io.github.alexdenton.interviewd.R
import io.github.alexdenton.interviewd.candidate.detail.ShowCandidateDetailFragment
import io.github.alexdenton.interviewd.entities.Candidate
import io.github.rfonzi.rxaware.RxAwareFragment
import io.reactivex.Observable
import io.reactivex.functions.BiFunction



class CandidateFormFragment : RxAwareFragment() {

    lateinit var vm: CandidateFormViewModel

    lateinit var firstNameLayout: TextInputLayout
    lateinit var lastNameLayout: TextInputLayout
    lateinit var firstNameField: TextInputEditText
    lateinit var lastNameField: TextInputEditText
    lateinit var submitButton: Button

    lateinit var firstNameValid: Observable<Boolean>
    lateinit var lastNameValid: Observable<Boolean>

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater!!.inflate(R.layout.fragment_candidate_form, container, false)
        vm = ViewModelProviders.of(this).get(CandidateFormViewModel::class.java)
        vm.withKodein(LazyKodein(appKodein))

        if(arguments?.containsKey("candidateId") == true){
            vm.editing(arguments.getLong("candidateId"))
            vm.fetchCandidate()
                    .subscribe {candidate -> setCandidate(candidate) }
                    .lifecycleAware()
        }

        firstNameLayout = view.findViewById(R.id.candidateForm_firstNameLayout)
        lastNameLayout = view.findViewById(R.id.candidateForm_lastNameLayout)
        firstNameField = view.findViewById(R.id.candidateForm_firstNameField)
        lastNameField = view.findViewById(R.id.candidateForm_lastNameField)
        submitButton = view.findViewById(R.id.candidateForm_submitButton)

        firstNameValid = firstNameField.textChanges().map { it.isValid() }.skip(1).distinctUntilChanged()

        lastNameValid = lastNameField.textChanges().map { it.isValid() }.skip(1).distinctUntilChanged()

        startFormValidityCheck()

        submitButton.clicks()
                .map { Candidate(vm.candidateId, firstNameField.getTextAsString(), lastNameField.getTextAsString()) }
                .flatMap { vm.submitCandidate(it).toObservable() }
                .subscribe({ onSuccessfulSubmit() },
                        { throwable -> throwable.printStackTrace() })
                .lifecycleAware()

        return view
    }

    fun setCandidate(candidate: Candidate){
        firstNameField.setText(candidate.firstName)
        lastNameField.setText(candidate.lastName)
    }

    fun setEnabled(bool: Boolean) {
        submitButton.isEnabled = bool
    }

    fun onSuccessfulSubmit() {
        if(vm.editing){
            toast("Candidate Updated")
            postToCurrentActivity(ShowCandidateDetailFragment)
        }
        else{
            toast("Candidate Submitted Successfully")
            navigateUp()
        }

    }

    fun CharSequence.isValid() = Regex("[a-zA-Z]+").matches(this)
    fun TextInputEditText.getTextAsString() = this.text.toString()

    fun startFormValidityCheck() = Observable.combineLatest(firstNameValid, lastNameValid,
            BiFunction<Boolean, Boolean, Boolean> { firstName, lastName ->
                if (firstName) firstNameLayout.error = null else firstNameLayout.error = "Invalid first name"
                if (lastName) lastNameLayout.error = null else lastNameLayout.error = "Invalid last name"

                firstName && lastName
            })
            .subscribe { setEnabled(it) }
            .lifecycleAware()

}
