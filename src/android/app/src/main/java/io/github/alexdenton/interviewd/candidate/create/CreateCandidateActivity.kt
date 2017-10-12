package io.github.alexdenton.interviewd.candidate.create

import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.design.widget.TextInputLayout
import android.widget.Button
import com.github.salomonbrys.kodein.LazyKodein
import com.github.salomonbrys.kodein.android.appKodein
import com.jakewharton.rxbinding2.view.clicks
import com.jakewharton.rxbinding2.widget.textChanges
import io.github.alexdenton.interviewd.R
import io.github.alexdenton.interviewd.entities.Candidate
import io.github.rfonzi.rxaware.RxAwareActivity
import io.reactivex.Observable
import io.reactivex.functions.BiFunction

class CreateCandidateActivity : RxAwareActivity() {

    lateinit var vm: CreateCandidateViewModel

    lateinit var firstNameField: TextInputLayout
    lateinit var lastNameField: TextInputLayout
    lateinit var submitButton: Button

    lateinit var firstNameValid: Observable<Boolean>
    lateinit var lastNameValid: Observable<Boolean>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_candidate)
        actionBar?.setDisplayHomeAsUpEnabled(true)

        vm = ViewModelProviders.of(this).get(CreateCandidateViewModel::class.java)
        vm.withKodein(LazyKodein(appKodein))

        firstNameField = findViewById(R.id.createCandidate_firstNameLayout)
        lastNameField = findViewById(R.id.createCandidate_lastNameLayout)
        submitButton = findViewById(R.id.createCandidate_submitButton)

        firstNameValid = firstNameField.textChanges().map { it.isValid() }.skip(1).distinctUntilChanged()

        lastNameValid = lastNameField.textChanges().map { it.isValid() }.skip(1).distinctUntilChanged()

        startFormValidityCheck()

        submitButton.clicks()
                .map { Candidate(0, firstNameField.getTextAsString(), lastNameField.getTextAsString()) }
                .flatMap { vm.submitCandidate(it).toObservable() }
                .subscribe({ onSuccessfulSubmit() },
                        { throwable -> throwable.printStackTrace() })
                .lifecycleAware()

    }

    fun setEnabled(bool: Boolean) {
        submitButton.isEnabled = bool
    }

    fun onSuccessfulSubmit() {
        toast("Candidate Submitted Successfully")
        navigateUp()
    }

    fun CharSequence.isValid() = Regex("[a-zA-Z]+").matches(this)
    fun TextInputLayout.getTextAsString() = this.editText?.getText()?.toString() ?: ""
    fun TextInputLayout.textChanges(): Observable<CharSequence> = this.editText?.textChanges() ?: Observable.just("" as CharSequence)

    fun startFormValidityCheck() = Observable.combineLatest(firstNameValid, lastNameValid,
            BiFunction<Boolean, Boolean, Boolean> { firstName, lastName ->
                if (firstName) firstNameField.error = null else firstNameField.error = "Invalid first name"
                if (lastName) lastNameField.error = null else lastNameField.error = "Invalid last name"

                firstName && lastName
            })
            .subscribe { setEnabled(it) }
            .lifecycleAware()
}
