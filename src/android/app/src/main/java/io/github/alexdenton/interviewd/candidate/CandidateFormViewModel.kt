package io.github.alexdenton.interviewd.candidate

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
class CandidateFormViewModel : RxAwareViewModel() {

    lateinit var candidateRepo: CandidateRepository

    var editing = false
    var candidateId: Long = 0

    fun withKodein(kodein: LazyKodein) {
        candidateRepo = kodein.invoke().instance()
    }

    fun submitCandidate(candidate: Candidate) = when(editing){
        false -> createCandidate(candidate)
        true -> updateCandidate(candidate)
    }

    fun createCandidate(candidate: Candidate) = candidateRepo.createCandidate(candidate)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())

    fun updateCandidate(candidate: Candidate) = candidateRepo.updateCandidate(candidate)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())

    fun fetchCandidate() = candidateRepo.getCandidate(candidateId)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())

    fun editing(id: Long) {
        candidateId = id
        editing = true
    }
}