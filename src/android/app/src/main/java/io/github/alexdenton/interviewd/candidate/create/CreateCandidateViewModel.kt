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

    fun submitCandidate(candidate: Candidate) = candidateRepo.createCandidate(candidate)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())

}