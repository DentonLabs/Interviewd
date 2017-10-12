package io.github.alexdenton.interviewd.candidate.detail

import com.github.salomonbrys.kodein.LazyKodein
import com.github.salomonbrys.kodein.instance
import io.github.alexdenton.interviewd.api.repositories.CandidateRepository
import io.github.rfonzi.rxaware.RxAwareViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

/**
 * Created by ryan on 10/3/17.
 */
class CandidateDetailViewModel : RxAwareViewModel() {

    lateinit var candidateRepo: CandidateRepository

    var candidateId = 0

    fun initWith(kodein: LazyKodein, id: Int){
        candidateRepo = kodein.invoke().instance()
        candidateId = id
    }

    fun fetchCandidate() = candidateRepo.getCandidate(candidateId)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())

    fun startEditingCandidate() = postToCurrentActivity(ShowCandidateFormFragment)

}