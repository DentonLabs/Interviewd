package io.github.alexdenton.interviewd.candidate.detail

import com.github.salomonbrys.kodein.LazyKodein
import com.github.salomonbrys.kodein.instance
import com.jakewharton.rxbinding2.InitialValueObservable
import com.jakewharton.rxrelay2.BehaviorRelay
import io.github.alexdenton.interviewd.R
import io.github.alexdenton.interviewd.api.repositories.CandidateRepository
import io.github.alexdenton.interviewd.entities.Candidate
import io.github.rfonzi.rxaware.RxAwareViewModel
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

/**
 * Created by ryan on 10/3/17.
 */
class CandidateDetailViewModel : RxAwareViewModel() {

    lateinit var candidateRepo: CandidateRepository
    private val candidate: BehaviorRelay<Candidate> = BehaviorRelay.create()
    fun getCandidateObservable(): Observable<Candidate> = candidate
    var firstName = ""
    var lastName = ""

    fun initWith(kodein: LazyKodein, candidateId: Int){
        candidateRepo = kodein.invoke().instance()
        fetchCandidate(candidateId)
    }

    fun fetchCandidate(id: Int) = candidateRepo.getCandidate(id)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { result -> candidate.accept(result) }
            .lifecycleAware()

    fun exposeFirstNameChanges(textChanges: InitialValueObservable<CharSequence>) = textChanges
            .subscribe { firstName = it.toString() }
            .lifecycleAware()

    fun exposeLastNameChanges(textChanges: InitialValueObservable<CharSequence>) = textChanges
            .subscribe { lastName = it.toString() }
            .lifecycleAware()

    fun submitEdit() = candidateRepo.updateCandidate(Candidate(candidate.value.id, firstName, lastName))
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe ({ result -> onSuccess(result) },
                    {throwable -> throwable.printStackTrace()})

    fun onSuccess(result: Candidate) {
        candidate.accept(result)
        toast("Edit successful")
        stopEditingCandidate()
    }

    fun startEditingCandidate() = fragmentTransaction { replace(R.id.candidateDetail_fragmentContainer, CandidateDetailEditFragment()) }

    fun stopEditingCandidate() = fragmentTransaction { replace(R.id.candidateDetail_fragmentContainer, CandidateDetailShowFragment()) }


}