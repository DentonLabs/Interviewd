package io.github.alexdenton.interviewd.candidate.create

import com.github.salomonbrys.kodein.LazyKodein
import com.github.salomonbrys.kodein.instance
import com.jakewharton.rxbinding2.InitialValueObservable
import io.github.alexdenton.interviewd.api.repositories.CandidateRepository
import io.github.alexdenton.interviewd.entities.Candidate
import io.github.rfonzi.rxaware.RxAwareViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

/**
 * Created by ryan on 9/24/17.
 */
class CreateCandidateViewModel : RxAwareViewModel() {

    lateinit var candidateRepo: CandidateRepository

    fun withKodein(kodein: LazyKodein){
        candidateRepo = kodein.invoke().instance()
    }

    var firstName = ""
    var lastName = ""

    fun exposeFirstNameField(textChanges: InitialValueObservable<CharSequence>) = textChanges
            .map { it.toString() }
            .subscribe { firstName = it }
            .lifecycleAware()

    fun exposeLastNameField(textChanges: InitialValueObservable<CharSequence>) = textChanges
            .map { it.toString() }
            .subscribe { lastName = it }
            .lifecycleAware()

    fun submitCandidate() = candidateRepo.createCandidate(getCandidateFromFields())
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe ({onSuccessfulSubmit()},
                    {throwable -> throwable.printStackTrace()})

    private fun getCandidateFromFields() = Candidate(0, firstName, lastName)
    private fun onSuccessfulSubmit() {
        toast("Candidate Submitted Successfully")
        navigateUp()
    }
}